package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.background.AsyncTaskLoader;
import com.tooflya.bubblefun.background.IAsyncCallback;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.GraphicsBubble;
import com.tooflya.bubblefun.managers.BubblesManager;

public class PreloaderScreen extends Screen implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mChangeAction = 0;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(512, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "bg_level.png", 0, 0, 1, 1)) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mBalonFull = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "preload_air_fill.png", 85, 0, 1, 1), this) {

		@Override
		public Entity create() {
			if (!this.hasParent())
				Game.screens.get(Screen.LOAD).attachChild(this);

			this.getTextureRegion().setHeight(0);
			this.setHeight(0);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mBalon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "preload_air_balon.png", 0, 0, 1, 1), this) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mLoadingText = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "preload_loading_text.png", 170, 0, 1, 1), this) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			/** Changing size of progressbar */
			if (mBalonFull.getHeightScaled() < mBalonFull.getBaseHeight() * Options.CAMERA_RATIO_FACTOR) {
				mBalonFull.getTextureRegion().setHeight((int) (mBalonFull.getTextureRegion().getHeight() + 3));
				mBalonFull.setHeight(mBalonFull.getHeight() + 3);
				mBalonFull.setPosition(mBalonFull.getX(), mBalon.getY() + mBalon.getHeightScaled() - mBalonFull.getHeightScaled() - 3 * Options.CAMERA_RATIO_FACTOR);
			} else {
				if (true) {
					if (mChangeAction == 0) {
						Game.screens.set(Screen.LEVEL);
					} else if (mChangeAction == 1) {
						Game.screens.set(Screen.MENU);
					}
					else {
						Game.screens.set(Screen.CHOISE);
					}
				}
			}
		}
	});

	// ===========================================================
	// Fields
	// ===========================================================

	private BubblesManager bubbles;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PreloaderScreen() {
		this.loadResources();

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.setBackground(new SpriteBackground(mBackground));

		mBalonFull.create().setPosition(375 * Options.CAMERA_RATIO_FACTOR, mBackground.getY() + 292 * Options.CAMERA_RATIO_FACTOR + mBalon.getHeightScaled());
		mBalon.create().setPosition(375 * Options.CAMERA_RATIO_FACTOR, mBackground.getY() + 292 * Options.CAMERA_RATIO_FACTOR);
		mLoadingText.create().setPosition(175 * Options.CAMERA_RATIO_FACTOR, mBackground.getY() + 292 * Options.CAMERA_RATIO_FACTOR);

		this.bubbles = new BubblesManager(10, new GraphicsBubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "lvl_bubble.png", 170, 60, 1, 3), this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.bubbles.update();
	}

	@Override
	public void onAttached() {
		super.onAttached();

		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);

		mBalonFull.create();

		/** Start background loader */
		new AsyncTaskLoader().execute(this);
	}

	@Override
	public void onDetached() {
		super.onDetached();

		/** Register timer of loading progressbar changes */
		this.unregisterUpdateHandler(mTimer);
	}

	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public void workToDo() {
		if (mChangeAction == 0) {
			Game.screens.get(Screen.MENU).unloadResources();
			Game.screens.get(Screen.CHOISE).unloadResources();
			Game.screens.get(Screen.LEVEL).loadResources();
			Game.screens.get(Screen.LEVELEND).loadResources();
		} else {
			Game.screens.get(Screen.LEVEL).unloadResources();
			Game.screens.get(Screen.LEVELEND).unloadResources();
			Game.screens.get(Screen.MENU).loadResources();
			Game.screens.get(Screen.CHOISE).loadResources();
		}
	}

	@Override
	public void onComplete() {
	}

}
