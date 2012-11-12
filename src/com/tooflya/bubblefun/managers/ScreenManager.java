package com.tooflya.bubblefun.managers;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.util.modifier.IModifier;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.background.AsyncTaskLoader;
import com.tooflya.bubblefun.background.IAsyncCallback;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.screens.BoxScreen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelEndScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.PauseScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mChangeAction = 0;

	private final HUD hud = new HUD();

	private final AlphaModifier modifierOn = new AlphaModifier(0.2f, 0f, 1f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			Game.engine.getScene().onDetached();
			screens[Z].setScene(Game.engine);
			screens[Z].onAttached();
			Screen.screen = Z;

			if (ao) {
				ScreenManager.this.modifierOff.reset();
			} else {
				ScreenManager.this.rectangle.registerEntityModifier(ScreenManager.this.modifierOff);
				ao = true;
			}
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final AlphaModifier modifierOff = new AlphaModifier(0.2f, 1f, 0f, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			ScreenManager.this.rectangle.setAlpha(0f);
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	public final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Screen.mCommonTextureAtlas2, Game.context, "preload-bg-2-01.png", 0, 0, 1, 1), this.hud) {

		private boolean cu = false, cd = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			if (mMoveUp) {
				this.setHeight(this.getHeight() - 15);
				this.getTextureRegion().setHeight(this.getTextureRegion().getHeight() - 15);

				r.setPosition(r.getX(), r.getY() - 15);

				if (this.mY <= -mBackground.getHeightScaled()) {
					mMoveUp = false;
				}

				if (!cu) {
					cu = true;

					this.mY = 0;
					this.setVisible(true);
				}
			}

			if (mMoveDown) {
				if (!cd) {
					cd = true;

					this.mY = 0;
					this.setHeight(0);
					this.getTextureRegion().setHeight(0);
					this.setVisible(true);

					r.setPosition(r.getX(), -r.getHeight());
				}

				this.setHeight(this.getHeight() + 15);
				this.getTextureRegion().setHeight(this.getTextureRegion().getHeight() + 15);

				r.setPosition(r.getX(), r.getY() + 15);

				if (this.getHeightScaled() >= this.getBaseHeight() * Options.cameraRatioFactor) {
					mMoveDown = false;

					this.mY = 0;

					updates = 0;

					for (int i = 10; i > 5; i--) {
						final Sprite sprite = circles.getByIndex(i);
						sprite.setScale(0f);
					}

					if (Options.mLevelSound.isPlaying())
						Options.mLevelSound.pause();
					if (Options.mMainSound.isPlaying())
						Options.mMainSound.pause();

					/** Register timer of loading progressbar changes */
					hud.registerUpdateHandler(mTimer);
				}
			}
		}
	};

	private Rectangle r = new Rectangle(0, 0, mBackground.getWidth(), mBackground.getHeight());

	public final Sprite mTextBar = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Screen.mCommonTextureAtlas2, Game.context, "preload-text.png", 400, 0, 1, 1), this.r);

	public EntityManager<Sprite> lines = new EntityManager<Sprite>(2, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Screen.mCommonTextureAtlas2, Game.context, "preload-line.png", 1000, 0, 1, 1), this.r));
	public EntityManager<Sprite> circles = new EntityManager<Sprite>(11, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Screen.mCommonTextureAtlas2, Game.context, "preload-bg-bar.png", 400, 40, 1, 2), this.r));

	private Sprite d = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Screen.mCommonTextureAtlas2, Game.context, "preload-bg-down-01.png", 0, 700, 1, 1), this.r);

	public int updates = 0;
	private boolean loaded = false;
	private final TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			updates++;

			/** Changing size of progressbar */
			if (updates < 70) {

				if (updates == 5) {
					/** Start background loader */
					new AsyncTaskLoader().execute(ScreenManager.this);
				}

				for (int i = 10; i > 5; i--) {
					final Sprite sprite = circles.getByIndex(i);

					if (sprite.getScaleX() < 1f) {
						sprite.setScale(sprite.getScaleX() + 0.16f);
					}
					else {
						sprite.setScale(1f);
					}

					if (sprite.getScaleX() < 1f) {
						break;
					}
				}
			} else {
				if (loaded) {
					switch (mChangeAction) {
					case 0:
						Game.screens.set(Screen.LEVEL, true);
						break;
					case 1:
						Game.screens.set(Screen.MENU, true);
						break;
					default:
						Game.screens.set(Screen.CHOISE, true);
					}
					/** Register timer of loading progressbar changes */
					hud.unregisterUpdateHandler(mTimer);
					u();

					loaded = false;
				}
			}
		}
	});

	private boolean mMoveUp = false;
	private boolean mMoveDown = false;

	// ===========================================================
	// Fields
	// ===========================================================

	/** List of available screens */
	public Screen[] screens;

	private Rectangle rectangle;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScreenManager() {
		this.rectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

		Game.camera.setHUD(this.hud);

		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		screens[Screen.CHOISE] = new LevelChoiseScreen();
		screens[Screen.LEVEL] = new LevelScreen();
		screens[Screen.LEVELEND] = new LevelEndScreen();
		screens[Screen.EXIT] = new ExitScreen();
		screens[Screen.PAUSE] = new PauseScreen();
		screens[Screen.BOX] = new BoxScreen();

		Game.loadTextures(Screen.mCommonTextureAtlas, Screen.mCommonTextureAtlas2);

		this.mBackground.create();
		this.mBackground.setScaleCenter(0, 0);
		this.mBackground.setScale(Options.cameraRatioFactor);
		this.mBackground.setBackgroundCenterPosition();

		this.mBackground.setVisible(false);

		mTextBar.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);

		this.circles.create().setVisible(false);
		for (int i = 0; i < 5; i++) {
			this.circles.create().setCenterPosition(Options.cameraCenterX - (35f * (i - 2)), Options.cameraCenterY);
		}

		for (int i = 0; i < 5; i++) {
			final Sprite sprite = this.circles.create();
			sprite.setCenterPosition(Options.cameraCenterX - (35f * (i - 2)), Options.cameraCenterY);
			sprite.setCurrentTileIndex(1);
			sprite.setScale(0f);
		}

		Sprite line;

		line = this.lines.create();
		line.setPosition(0, 0);

		line = this.lines.create();
		line.setPosition(this.mBackground.getWidth() - 7f, 0);

		r.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		r.setAlpha(0);

		mBackground.attachChild(r);

		d.create().setPosition(0, r.getHeight() - d.getHeight());
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void l() {
		this.mMoveDown = true;
	}

	public void u() {
		this.mMoveUp = true;
	}

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.screenWidth, Options.screenHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.modifierOn.setRemoveWhenFinished(false);
		this.modifierOff.setRemoveWhenFinished(false);

		coloredRect.setAlpha(0f);

		this.hud.attachChild(coloredRect);

		return coloredRect;
	}

	private static int Z = 666;

	private boolean ai = false;
	private boolean ao = false;

	public void set(final int pScreen) {
		if (Z == pScreen)
			return;

		Z = pScreen;

		if (ai) {
			this.modifierOn.reset();
		} else {
			this.rectangle.registerEntityModifier(this.modifierOn);
			ai = true;
		}
	}

	public void set(final int pScreen, final boolean pNoAnimation) {
		if (Z == pScreen)
			return;

		Z = pScreen;

		Game.engine.getScene().onDetached();
		screens[Z].setScene(Game.engine);
		screens[Z].onAttached();
		Screen.screen = Z;
	}

	public void setChildScreen(final Screen pScreen, final boolean pModalDraw, final boolean pModalUpdate, final boolean pModalTouch) {
		this.getCurrent().setChildScene(pScreen, pModalDraw, pModalUpdate, pModalTouch);
		pScreen.onAttached();
	}

	public void clearChildScreens() {
		this.getCurrent().getChildScene().onDetached();
	}

	public Screen get(final int pScreen) {
		return screens[pScreen];
	}

	public Screen getCurrent() {
		return screens[Screen.screen];
	}

	@Override
	public void workToDo() {
		if (mChangeAction == 0) {
			Game.screens.get(Screen.EXIT).unloadResources();
			Game.screens.get(Screen.MENU).unloadResources();
			Game.screens.get(Screen.BOX).unloadResources();
			Game.screens.get(Screen.CHOISE).unloadResources();
			Game.screens.get(Screen.LEVEL).loadResources();
			Game.screens.get(Screen.LEVELEND).loadResources();
			Game.screens.get(Screen.PAUSE).loadResources();
		} else {
			Game.screens.get(Screen.PAUSE).unloadResources();
			Game.screens.get(Screen.LEVEL).unloadResources();
			Game.screens.get(Screen.LEVELEND).unloadResources();
			Game.screens.get(Screen.MENU).loadResources();
			Game.screens.get(Screen.BOX).loadResources();
			Game.screens.get(Screen.CHOISE).loadResources();
			Game.screens.get(Screen.EXIT).loadResources();
		}
	}

	@Override
	public void onComplete() {
		loaded = true;
	}
}
