package com.tooflya.airbubblegum.screens;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
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