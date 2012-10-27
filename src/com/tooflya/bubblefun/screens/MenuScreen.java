package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import android.content.Intent;
import android.net.Uri;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MenuScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ICONS_SIZE = 44 * Options.cameraRatioFactor;
	private static final float ICONS_PADDING = 10 * Options.cameraRatioFactor;
	private static final float ICONS_PADDING_BETWEEN = 10 * Options.cameraRatioFactor;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private final RotationModifier mRotateOn = new RotationModifier(0.3f, 0f, 405f);
	private final RotationModifier mRotateOff = new RotationModifier(0.3f, 405f, 0f);

	private final MoveModifier mMoreMoveOn = new MoveModifier(0.3f,
			ICONS_PADDING,
			53f * Options.cameraRatioFactor + ICONS_PADDING * 2,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor);
	private final MoveModifier mMoreMoveOff = new MoveModifier(0.3f,
			53f * Options.cameraRatioFactor + ICONS_PADDING * 2,
			ICONS_PADDING,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor);

	private final MoveModifier mSoundMoveOn = new MoveModifier(0.3f,
			ICONS_PADDING,
			90f * Options.cameraRatioFactor + ICONS_PADDING * 2,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor);
	private final MoveModifier mSoundMoveOff = new MoveModifier(0.3f,
			90f * Options.cameraRatioFactor + ICONS_PADDING * 2,
			ICONS_PADDING,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor,
			Options.cameraHeight - ICONS_PADDING * 3 - 32 * Options.cameraRatioFactor);

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

	private final CloudsManager mClouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/cloud.png", 400, 0, 1, 3), this));

	private final Entity mLogoBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/main-name.png", 550, 0, 1, 1), this) {
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

	private final Entity mTwitterIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/tw-btn.png", 0, 700, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("twitter://user?screen_name=tooflya"));
					Game.instance.startActivity(intent);

				} catch (Exception e) {
					Game.instance.startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("https://twitter.com/#!/tooflya")));
				}
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

	private final Entity mFacebookIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/fb-btn.png", 100, 700, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);
				try {
					Game.context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
					Game.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/386292514777918")));
				} catch (Exception e) {
					Game.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/386292514777918")));
				}
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

	private final Entity mPlayIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/play-btn.png", 200, 700, 1, 2), this, true) {

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

			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);

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
				//this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				//this.setCurrentTileIndex(0);

				if (this.mModifierAttached) {
					this.mScaleModifier.reset();
				} else {
					this.registerEntityModifier(this.mScaleModifier);
					this.mModifierAttached = true;
				}

				this.mDoAction = true;

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

					Game.screens.set(Screen.CHOISE);
				}
			}
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

	private final Entity mMoreIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/more-btn.png", 350, 700, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final Entity mSoundIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/sound-btn.png", 450, 700, 1, 2), this, true) {

		private boolean disable = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				//this.setCurrentTileIndex(this.disable ? 0 : 1);
				this.disable = !this.disable;
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Bubble#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final Entity mSettingsIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/set-btn.png", 550, 700, 1, 2), this, true) {

		private boolean rotation = true;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				if (this.rotation) {
					MenuScreen.this.mRotateOff.reset();
					MenuScreen.this.mMoreMoveOff.reset();
					MenuScreen.this.mSoundMoveOff.reset();
				} else {
					MenuScreen.this.mRotateOn.reset();
					MenuScreen.this.mMoreMoveOn.reset();
					MenuScreen.this.mSoundMoveOn.reset();
				}
				this.rotation = !this.rotation;
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

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen() {
		this.loadResources();

		this.mClouds.generateStartClouds();

		this.mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mLogoBackground.create().setCenterPosition(Options.cameraCenterX, mBackground.getY() + 120 * Options.cameraRatioFactor);

		this.mTwitterIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING - ICONS_SIZE, Options.cameraHeight - ICONS_PADDING * 2 - ICONS_SIZE);
		this.mFacebookIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING - ICONS_PADDING_BETWEEN - ICONS_SIZE * 2, Options.cameraHeight - ICONS_PADDING * 2 - ICONS_SIZE);

		this.mPlayIcon.create().setCenterPosition(Options.cameraCenterX + (this.mPlayIcon.getWidthScaled() - this.mPlayIcon.getBaseWidth()) / 2, Options.cameraCenterY + (this.mPlayIcon.getHeightScaled() - this.mPlayIcon.getBaseHeight()) / 2);

		this.mSettingsIcon.create().setPosition(0 + ICONS_PADDING, Options.cameraHeight - ICONS_PADDING * 2 - mSettingsIcon.getHeightScaled());
		this.mMoreIcon.create().setPosition(ICONS_PADDING, Options.cameraHeight - ICONS_PADDING * 3 - mMoreIcon.getHeightScaled());
		this.mSoundIcon.create().setPosition(ICONS_PADDING, Options.cameraHeight - ICONS_PADDING * 3 - mSoundIcon.getHeightScaled());

		this.mSettingsIcon.setRotationCenter(this.mSettingsIcon.getWidthScaled() / 2, this.mSettingsIcon.getHeightScaled() / 2);

		this.mRotateOn.setRemoveWhenFinished(false);
		this.mRotateOff.setRemoveWhenFinished(false);

		this.mMoreMoveOn.setRemoveWhenFinished(false);
		this.mMoreMoveOff.setRemoveWhenFinished(false);

		this.mSoundMoveOn.setRemoveWhenFinished(false);
		this.mSoundMoveOff.setRemoveWhenFinished(false);

		this.mSettingsIcon.registerEntityModifier(this.mRotateOn);
		this.mSettingsIcon.registerEntityModifier(this.mRotateOff);

		this.mMoreIcon.registerEntityModifier(this.mMoreMoveOn);
		this.mMoreIcon.registerEntityModifier(this.mMoreMoveOff);

		this.mSoundIcon.registerEntityModifier(this.mSoundMoveOn);
		this.mSoundIcon.registerEntityModifier(this.mSoundMoveOff);

		this.mSoundIcon.setCurrentTileIndex(1);
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
		if (this.hasChildScene()) {
			this.clearChildScene();
		} else {
			this.setChildScene(Game.screens.get(Screen.EXIT), false, false, true);
		}

		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}