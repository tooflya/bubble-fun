package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Bubble;
import com.tooflya.airbubblegum.entities.Cloud;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MenuScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ICONS_SIZE = 71 * Options.CAMERA_RATIO_FACTOR;
	private static final float ICONS_PADDING = 16 * Options.CAMERA_RATIO_FACTOR;
	private static final float ICONS_PADDING_BETWEEN = 16 * Options.CAMERA_RATIO_FACTOR;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main_menu_bg.png", 0, 0, 1, 1), false) {

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

	private final static Entity mTwitterIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "twitter-icon.png", 580, 0, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final static Entity mFacebookIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "facebook-icon.png", 660, 0, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final static Bubble mPlayIcon = new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "bubble-play-animation.png", 581, 100, 1, 1)) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.mIsYReverse = false;
			this.mIsXReverse = true;

			this.mScaleY = mMinScaleY;
			this.mScaleX = mMaxScaleX;

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
				Game.screens.set(Screen.CHOISE);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final static Bubble mMoreIcon = new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "bubble-more-animation.png", 581, 400, 1, 1)) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.mIsYReverse = true;
			this.mIsXReverse = false;

			this.mScaleY = mMaxScaleY;
			this.mScaleX = mMinScaleX;

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
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private final static Bubble mSoundIcon = new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "sound-icon.png", 581, 600, 1, 1)) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.mIsYReverse = false;
			this.mIsXReverse = true;

			this.mScaleY = mMinScaleY;
			this.mScaleX = mMaxScaleX;

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
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private CloudsManager clouds;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen() {
		Game.loadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mTwitterIcon.create().setPosition(0 + ICONS_PADDING, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);
		mFacebookIcon.create().setPosition(0 + ICONS_PADDING + ICONS_PADDING_BETWEEN + ICONS_SIZE, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);

		mPlayIcon.create().setCenterPosition(Options.cameraCenterX - 170 * Options.CAMERA_RATIO_FACTOR, Options.cameraCenterY - 90 * Options.CAMERA_RATIO_FACTOR);
		mMoreIcon.create().setCenterPosition(Options.cameraCenterX + 40 * Options.CAMERA_RATIO_FACTOR, Options.cameraCenterY + 130 * Options.CAMERA_RATIO_FACTOR);
		mSoundIcon.create().setCenterPosition(Options.cameraCenterX + 150 * Options.CAMERA_RATIO_FACTOR, Options.cameraCenterY + 300 * Options.CAMERA_RATIO_FACTOR);
	}

	public void init() {
		this.attachChild(mBackground);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "clouds.png", 0, 0, 1, 4), Screen.MENU));
		this.clouds.generateStartClouds();

		this.attachChild(mTwitterIcon);
		this.attachChild(mFacebookIcon);
		this.attachChild(mPlayIcon);
		this.attachChild(mMoreIcon);
		this.attachChild(mSoundIcon);

		this.registerTouchArea(mTwitterIcon);
		this.registerTouchArea(mFacebookIcon);
		this.registerTouchArea(mPlayIcon);
		this.registerTouchArea(mMoreIcon);
		this.registerTouchArea(mSoundIcon);
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

		this.clouds.update();
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
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.close();

		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}