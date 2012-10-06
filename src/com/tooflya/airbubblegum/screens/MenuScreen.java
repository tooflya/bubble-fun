package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
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

	private static final float ICONS_SIZE = 51 * Options.CAMERA_RATIO_FACTOR;
	private static final float ICONS_PADDING = 16 * Options.CAMERA_RATIO_FACTOR;
	private static final float ICONS_PADDING_BETWEEN = 8 * Options.CAMERA_RATIO_FACTOR;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "main_menu_bg.png", 0, 0, 1, 1), false) {

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

	private final static Entity mTwitterIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "twitter-icon.png", 580, 800, 1, 1), false) {

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

	private final static Entity mFacebookIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "facebook-icon.png", 640, 800, 1, 1), false) {

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

	private CloudsManager clouds;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen() {
		Game.loadTextures(mBackgroundTextureAtlas);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mTwitterIcon.create().setPosition(0 + ICONS_PADDING, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);
		mFacebookIcon.create().setPosition(0 + ICONS_PADDING + ICONS_PADDING_BETWEEN + ICONS_SIZE, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);
	}

	public void init() {
		this.attachChild(mBackground);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "clouds.png", 581, 0, 1, 4), Screen.MENU));
		this.clouds.generateStartClouds();

		this.attachChild(mTwitterIcon);
		this.attachChild(mFacebookIcon);
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