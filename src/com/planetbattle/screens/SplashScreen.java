package com.planetbattle.screens;

import com.planetbattle.ui.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;

public class SplashScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public SplashScreen(final Camera pCamera) {
		super(pCamera);

		final Entity a = new Entity("assets/gfx/preload-screen.png");
		a.setVisible(true);
		a.setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.attachChild(a);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onManagedUpdate(final float pSecondsElapsed, final Camera pCamera) {
		super.onManagedUpdate(pSecondsElapsed, pCamera);
	}

	@Override
	public void onAttached() {
	}

	@Override
	public void onDetached() {
	}

	@Override
	public void onBackPressed() {
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Classes
	// ===========================================================

}