package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import android.content.Intent;
import android.net.Uri;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.ScreenManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MenuScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ICONS_SIZE = 44f;
	private static final float ICONS_PADDING = 10f;
	private static final float ICONS_PADDING_BETWEEN = 10f;

	// ===========================================================
	// Fields
	// ===========================================================

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final RotationModifier mRotateOn = new RotationModifier(0.3f, 0f, 405f);
	private final RotationModifier mRotateOff = new RotationModifier(0.3f, 405f, 0f);

	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "mb.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(Screen.cloudTextureRegion, this.mBackground));

	private final Sprite mLogoBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "main-name.png", 0, 620, 1, 1), this.mBackground);

	private final ButtonScaleable mTwitterIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "tw-btn.png", 400, 0, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("twitter://user?screen_name=tooflya"));
				Game.instance.startActivity(intent);

			} catch (Exception e) {
				Game.instance.startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://twitter.com/#!/tooflya")));
			}
		}

	};

	private final ButtonScaleable mFacebookIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "fb-btn.png", 400, 90, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			try {
				Game.context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
				Game.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/386292514777918")));
			} catch (Exception e) {
				Game.instance.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/386292514777918")));
			}
		}
	};

	private final ButtonScaleable mPlayIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "play.png", 0, 860, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.BOX);
		}
	};

	private final ButtonScaleable mMoreIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "more-btn.png", 400, 180, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MORE);
		}
	};

	private final ButtonScaleable mSoundIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "sound-btn.png", 400, 250, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Options.isMusicEnabled = !Options.isMusicEnabled;

			if (Options.isMusicEnabled) {
				this.setCurrentTileIndex(0);
				Options.mMainSound.play();
			} else {
				this.setCurrentTileIndex(1);
				Options.mMainSound.pause();
			}
		}
	};

	private final MoveModifier mMoreMoveOn = new MoveModifier(0.3f, ICONS_PADDING * 2, 53f + ICONS_PADDING * 2, Options.cameraHeight - 50f, Options.cameraHeight - 50f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.MoveModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			registerTouchArea(mMoreIcon);
		}
	};
	private final MoveModifier mMoreMoveOff = new MoveModifier(0.3f, ICONS_PADDING * 2 + 53f, ICONS_PADDING, Options.cameraHeight - 50f, Options.cameraHeight - 50f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.MoveModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			unregisterTouchArea(mMoreIcon);
		}
	};
	private final MoveModifier mSoundMoveOn = new MoveModifier(0.3f, ICONS_PADDING * 2, 90f + ICONS_PADDING * 2, Options.cameraHeight - 50f, Options.cameraHeight - 50f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.MoveModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			registerTouchArea(mSoundIcon);
		}
	};
	private final MoveModifier mSoundMoveOff = new MoveModifier(0.3f, ICONS_PADDING * 2 + 90f, ICONS_PADDING, Options.cameraHeight - 50f, Options.cameraHeight - 50f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.MoveModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			unregisterTouchArea(mSoundIcon);
		}
	};

	private final Sprite mSettingsIcon = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "set.png", 400, 350, 1, 1), this.mBackground, true) {

		private boolean rotation = false;

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
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen() {
		this.loadResources();

		this.clouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mLogoBackground.create().setCenterPosition(Options.cameraCenterX, mBackground.getY() + 170f);

		this.mTwitterIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING - ICONS_SIZE, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);
		this.mFacebookIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING_BETWEEN - ICONS_PADDING - ICONS_SIZE * 2, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);

		this.mPlayIcon.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);

		this.mSettingsIcon.create().setPosition(10f, Options.cameraHeight - 60f);
		this.mMoreIcon.create().setPosition(ICONS_PADDING, Options.cameraHeight - 50f);
		this.mSoundIcon.create().setPosition(ICONS_PADDING, Options.cameraHeight - 50f);

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

		this.unregisterTouchArea(this.mMoreIcon);
		this.unregisterTouchArea(this.mSoundIcon);
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
	public void onAttached() {
		super.onAttached();

		ScreenManager.mChangeAction = 0;

		if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
			Options.mMainSound.play();
		}
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
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (this.hasChildScene()) {
			Game.screens.get(Screen.EXIT).onDetached();
		} else {
			this.setChildScene(Game.screens.get(Screen.EXIT), false, false, true);
			Game.screens.get(Screen.EXIT).onAttached();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}