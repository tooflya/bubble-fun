package com.tooflya.bubblefun.screens;

import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;
import com.tooflya.bubblefun.handlers.UpdateHandler;

public class SplashScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	@SuppressWarnings("unused")
	private final Entity mBackground = new Entity(Options.cameraCenterX, Options.cameraCenterY, "assets/gfx/preload-screen.png", this);

	// ===========================================================
	// Constructors
	// ===========================================================

	public SplashScreen(final ScreensManager pScreensManager, final Camera pCamera) {
		super(pScreensManager, pCamera);

		this.registerUpdateHandler(new UpdateHandler<Screen>(1f) {

			@Override
			protected void onUpdate(final Screen screen) {
				screen.mScreensManager.set(Screen.LOADING);
			}
		});
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
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