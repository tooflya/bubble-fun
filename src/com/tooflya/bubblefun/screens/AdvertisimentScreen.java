package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;

public class AdvertisimentScreen extends Screen {

	private final Entity mBackground;

	public final ButtonScaleable mClose;

	public AdvertisimentScreen() {
		this.mBackground = new Entity(Resources.mPreloadBackgroundTextureRegion, this);

		this.mClose = new ButtonScaleable(Resources.mCloseTextureRegion, this.mBackground) {
			@Override
			public void onClick() {
				AdvertisimentScreen.this.onBackPressed();
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mClose.create().setCenterPosition(50f, 40f);
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

}
