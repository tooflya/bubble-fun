package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Button;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;

public class AdvertisimentScreen extends Screen {

	private final Entity mBackground;

	private final Button mAdsPanel;
	private final ButtonScaleable mAdsButton;

	public final ButtonScaleable mClose;

	public AdvertisimentScreen() {
		this.mBackground = new Entity(Resources.mPreloadBackgroundTextureRegion, this);

		this.mAdsPanel = new Button(Resources.mAdsPanelTextureRegion, this.mBackground, true) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				removeAds();
			}
		};

		this.mAdsButton = new ButtonScaleable(Resources.mAdsButtonTextureRegion, this.mAdsPanel, true) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
			 */
			@Override
			public void onClick() {
				removeAds();
			}
		};

		this.mClose = new ButtonScaleable(Resources.mCloseTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
			 */
			@Override
			public void onClick() {
				AdvertisimentScreen.this.onBackPressed();
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mClose.create().setPosition(15f, 15f);
		this.mAdsPanel.create().setPosition(0, Options.cameraHeight - this.mAdsPanel.getHeight());
		this.mAdsButton.create().setCenterPosition(320f, 15f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		this.mClose.setVisible(false);

		Game.mAdvertisementManager.showBig();

		if (Options.isMusicEnabled) {
			if (Options.mLevelSound.isPlaying()) {
				Options.mLevelSound.pause();
			}
		}
	}

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

		Game.mAdvertisementManager.hideBig();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.screens.set(Screen.LEVEL);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private final void removeAds() {

	}

}
