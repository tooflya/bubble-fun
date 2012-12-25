package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.input.touch.TouchEvent;

import android.content.Intent;
import android.net.Uri;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.ScreenManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MenuScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float ICONS_SIZE = 44f;
	private static final float ICONS_PADDING = 10f;
	private static final float ICONS_PADDING_BETWEEN = 10f;

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mLogoBackground;
	private final Entity mSettingsIcon;
	private final Entity mBlueBird;
	private final Entity mParachuteBird1;
	private final Entity mParachuteBird2;
	private final Entity mBird1;
	private final Entity mBird2;

	private final ButtonScaleable mTwitterIcon;
	private final ButtonScaleable mFacebookIcon;
	private final ButtonScaleable mPlayIcon;
	private final ButtonScaleable mMoreIcon;
	private final ButtonScaleable mSoundIcon;
	private final ButtonScaleable mBuyButton;

	private final Entity mShopAvailableItemsCount;

	private final RotationModifier mRotateOn = new RotationModifier(0.3f, 0f, 405f);
	private final RotationModifier mRotateOff = new RotationModifier(0.3f, 405f, 0f);

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

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses1.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass3.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mBlueBird = new Entity(Resources.mBackgroundBlueBirdTextureRegion, this.mBackground);
		this.mParachuteBird1 = new Entity(Resources.mBackgroundParachuteBirdTextureRegion, this.mBackground);
		this.mParachuteBird2 = new Entity(Resources.mBackgroundParachuteBirdTextureRegion, this.mBackground);
		this.mBird1 = new Entity(Resources.mBackgroundBirdTextureRegion, this.mBackground);
		this.mBird2 = new Entity(Resources.mBackgroundBirdTextureRegion, this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mLogoBackground = new Entity(Resources.mBackgroundLogoNameTextureRegion, this.mBackground);

		this.mTwitterIcon = new ButtonScaleable(Resources.mTwitterTextureRegion, this.mBackground) {

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

		this.mFacebookIcon = new ButtonScaleable(Resources.mFacebookTextureRegion, this.mBackground) {

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

		this.mPlayIcon = new ButtonScaleable(Resources.mPlayIconTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.BOX);
			}
		};

		this.mBuyButton = new ButtonScaleable(Resources.mBuyButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.STORE);
			}
		};

		this.mMoreIcon = new ButtonScaleable(Resources.mMoreIconTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MORE);
			}
		};

		this.mSoundIcon = new ButtonScaleable(Resources.mSoundIconTextureRegion, this.mBackground) {

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

		this.mSettingsIcon = new Entity(Resources.mSettingsIconTextureRegion, this.mBackground, true) {

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

		this.mShopAvailableItemsCount = new Entity(Resources.mShopAvailableTextureRegion, this.mBuyButton);

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mLogoBackground.create().setCenterPosition(Options.cameraCenterX, mBackground.getY() + 170f);

		this.mBlueBird.create().setPosition(Options.cameraWidth - ICONS_PADDING_BETWEEN - ICONS_PADDING - ICONS_SIZE * 2, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE * 3);
		//this.mParachuteBird1.create().setPosition(Options.cameraCenterX - 100f, Options.cameraCenterY + 150f);
		this.mParachuteBird2.create().setPosition(Options.cameraCenterX + 100f, Options.cameraCenterY + 50f);
		this.mBird1.create().setPosition(50f, Options.cameraCenterY);
		this.mBird2.create().setPosition(30f, Options.cameraCenterY + 50f);

		this.mBird1.setScale(1f);
		this.mBird2.setScale(0.8f);

		this.mParachuteBird1.setRotation(-5f);
		this.mParachuteBird2.setRotation(15f);

		this.mParachuteBird2.getTextureRegion().setFlippedHorizontal(true);

		this.mTwitterIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING - ICONS_SIZE, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);
		this.mFacebookIcon.create().setPosition(Options.cameraWidth - ICONS_PADDING_BETWEEN - ICONS_PADDING - ICONS_SIZE * 2, Options.cameraHeight - ICONS_PADDING - ICONS_SIZE);

		this.mPlayIcon.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);
		this.mBuyButton.create().setCenterPosition(Options.cameraCenterX - 100f, Options.cameraCenterY + 150f);

		this.mShopAvailableItemsCount.create().setCenterPosition(this.mBuyButton.getWidth() - 10f, 10f);

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
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		ScreenManager.mChangeAction = 0;

		if (Options.isMusicEnabled) {
			if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
				Options.mMainSound.play();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
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
	public void onBackPressed() {
		if (this.hasChildScene()) {
			Game.screens.clearChildScreens();
		} else {
			Game.screens.setChildScreen(Game.screens.get(Screen.EXIT), false, false, true);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}