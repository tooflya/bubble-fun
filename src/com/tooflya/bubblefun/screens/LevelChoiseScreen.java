package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.LevelsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelChoiseScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ICONS_PADDING = 10 * Options.cameraRatioFactor;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/main-bg.png", 0, 0, 1, 1), this) {

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

	private final CloudsManager mClouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/cloud.png", 382, 0, 1, 3), this));

	private final Entity mBackButton = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/back-btn.png", 100, 900, 1, 2), this, true) {

		private ScaleModifier mScaleModifier;

		private boolean mModifierAttached = false;

		private int mWaitBeforeAction = 20;
		private boolean mDoAction = false;

		private float mBaseScale;

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.mBaseScale = this.getScaleX();

			this.mScaleModifier = new ScaleModifier(0.1f, this.getScaleX(), this.getScaleX() + 0.3f * Options.cameraRatioFactor);
			this.mScaleModifier.setRemoveWhenFinished(false);

			this.setScaleCenter(this.getBaseWidth() / 2, this.getBaseHeight() / 2);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				break;
			case TouchEvent.ACTION_UP:
				if (this.mWaitBeforeAction == 20) {
					if (this.mModifierAttached) {
						this.mScaleModifier.reset();
					} else {
						this.registerEntityModifier(this.mScaleModifier);
						this.mModifierAttached = true;
					}

					this.mDoAction = true;
				}
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			if (this.mDoAction) {
				if (this.mWaitBeforeAction-- <= 0) {
					this.mDoAction = false;
					this.mWaitBeforeAction = 20;
					this.setScale(this.mBaseScale);

					Game.screens.set(Screen.MENU);
				}
			}
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

	private final LevelsManager levels = new LevelsManager(25, new LevelIcon(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/level-btn.png", 0, 612, 1, 5), this));
	private final EntityManager numbers = new EntityManager(100, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/numbers-sprite.png", 370, 650, 1, 10)));

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelChoiseScreen() {
		this.loadResources();

		this.mClouds.generateStartClouds();

		this.mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		this.mBackButton.create().setPosition(ICONS_PADDING + (this.mBackButton.getWidthScaled() - this.mBackButton.getBaseWidth()) / 2, Options.cameraHeight - ICONS_PADDING * 2 - this.mBackButton.getHeightScaled() + (this.mBackButton.getHeightScaled() - this.mBackButton.getBaseHeight()) / 2);

		int g = -1;
		for (int i = 0; i < this.levels.getCapacity(); i++) {
			if (i < 9) {
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
			} else {
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
			}
		}

		this.levels.generate(this.numbers);

	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mClouds.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		PreloaderScreen.mChangeAction = 0;

		this.levels.clear();
		this.levels.generate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.screens.set(Screen.MENU);

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}