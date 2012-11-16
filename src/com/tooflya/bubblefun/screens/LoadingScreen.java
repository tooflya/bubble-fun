package com.tooflya.bubblefun.screens;

import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;
import com.tooflya.bubblefun.handlers.UpdateHandler;

public class LoadingScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground = new Entity(Options.cameraCenterX, Options.cameraCenterY, "assets/gfx/preload-screen-second.png", this);

	private final Entity mProgressBar = new Entity(188f, 496f, "assets/gfx/preload-screen-fill.png", this.mBackground);

	// ===========================================================
	// Constructors
	// ===========================================================

	public LoadingScreen(final ScreensManager pScreensManager, final Camera pCamera) {
		super(pScreensManager, pCamera);

		this.mProgressBar.setWidth(0f);
		this.mProgressBar.getTextureRegion().setRegionWidth(0);

		this.registerUpdateHandler(new UpdateHandler<Screen>(0.003f) {

			@Override
			protected void onUpdate(final Screen screen) {

				final float width = mProgressBar.getWidth();
				final float baseWidth = mProgressBar.getBaseWidth();

				final int textureRegionWidth = mProgressBar.getTextureRegion().getRegionWidth();

				if (width < baseWidth) {
					mProgressBar.setWidth(width + 1f);
					mProgressBar.getTextureRegion().setRegionWidth(textureRegionWidth + 1);
				} else {
					screen.mScreensManager.set(Screen.MENU);
				}
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