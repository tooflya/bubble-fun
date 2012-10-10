package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public final static BitmapTextureAtlas mBackgroundTextureAtlas0 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main_par1.png", 0, 0, 1, 1), false) {

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

	private final static Entity mDottedLine = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "line.png", 0, 1016, 1, 1), false) {

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

	private final static Entity mResetButton = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "reset.png", 0, 950, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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
			case TouchEvent.ACTION_UP:
				Game.world.init();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
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

	private final static Entity mBirdsCounterBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-bird.png", 0, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static Entity mAirCounterBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-balon.png", 100, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static Entity mAirCounterBackgroundFill = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-balon-fill.png", 200, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static ChangeableText mScoreText = new ChangeableText(0, 0, Game.mSmallFont, "Score: xxxxx");
	private final static ChangeableText mBirdsCountText = new ChangeableText(65 * Options.CAMERA_RATIO_FACTOR, 31 * Options.CAMERA_RATIO_FACTOR, Game.mSmallFont, "31");
	private final static ChangeableText mTapHelpText = new ChangeableText(Options.cameraCenterX - 200 * Options.CAMERA_RATIO_FACTOR, Options.cameraHeight / 3 * 2 + 120 * Options.CAMERA_RATIO_FACTOR, Game.mSmallFont, "Tap here to pop chicks!!!");

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	private CloudsManager clouds;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
		this.setOnSceneTouchListener(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#init()
	 */
	@Override
	public void init() {
		this.attachChild(mBackground);

		mResetButton.create().setPosition(Options.cameraWidth - mResetButton.getWidthScaled() - 100 * Options.CAMERA_RATIO_FACTOR, 20 * Options.CAMERA_RATIO_FACTOR);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mDottedLine.create().setPosition(0, Options.cameraHeight / 3 * 2);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "clouds.png", 0, 0, 1, 4), Screen.LEVEL));
		this.clouds.generateStartClouds();

		this.attachChild(mDottedLine);

		mBirdsCounterBackground.create().setPosition(20 * Options.CAMERA_RATIO_FACTOR, 20 * Options.CAMERA_RATIO_FACTOR);
		mAirCounterBackgroundFill.create().setPosition(20 * Options.CAMERA_RATIO_FACTOR, 85 * Options.CAMERA_RATIO_FACTOR + mAirCounterBackground.getHeightScaled() - mAirCounterBackgroundFill.getHeightScaled());
		mAirCounterBackground.create().setPosition(20 * Options.CAMERA_RATIO_FACTOR, 85 * Options.CAMERA_RATIO_FACTOR);

		mBirdsCountText.setColor(0f, 0f, 0f);
		this.attachChild(mBirdsCountText);
		// this.attachChild(mScoreText);
		this.attachChild(mTapHelpText);

		mTapHelpText.setRotationCenter(mTapHelpText.getWidthScaled() / 2, mTapHelpText.getHeightScaled() / 2);
		mTapHelpText.setRotation(-15);

		this.registerTouchArea(mResetButton);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();
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
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.clouds.update();

		mBirdsCountText.setText(Game.world.chikies.getCount() + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		PreloaderScreen.isTheGame = false;
		Game.screens.set(Screen.LOAD);

		return true;
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (this.lastAirgum == null && pTouchEvent.getY() > Options.cameraHeight - Options.cameraHeight / 3)
			{
				this.lastAirgum = (Bubble) Game.world.airgums.create();
				this.lastAirgum.setCenterPosition(pTouchEvent.getX(), pTouchEvent.getY());
				this.lastAirgum.setScaleCenterY(0);
			}
			if (this.lastAirgum != null) {
				this.lastAirgum.setIsScale(true);
			}

			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				final float koef = 10f;
				if (this.lastAirgum.getCenterY() - pTouchEvent.getY() > 10f) {
					this.lastAirgum.setSpeedY(this.lastAirgum.getSpeedY() + (this.lastAirgum.getCenterY() - pTouchEvent.getY()) / koef);
					this.lastAirgum.setSpeedX((pTouchEvent.getX() - this.lastAirgum.getCenterX()) / (koef * 5));

					Particle particle;
					for (int i = 0; i < Options.particlesCount / 2; i++) {
						particle = ((Particle) Game.world.stars.create());
						if (particle != null) {
							particle.Init().setCenterPosition(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY());
						}
					}
				}
				this.lastAirgum.setIsScale(false);
				this.lastAirgum = null;
			}
			break;
		}

		return false;
	}
	// ===========================================================
	// Methods
	// ===========================================================

}
