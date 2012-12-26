package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.scene.background.ColorBackground;

import com.tooflya.bubblefun.Game;

public class AdvertisimentScreen extends Screen {

	public AdvertisimentScreen() {
		this.setBackground(new ColorBackground(1f, 1f, 1f, 1f));
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
