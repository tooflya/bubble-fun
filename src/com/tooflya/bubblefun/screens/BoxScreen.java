package com.tooflya.bubblefun.screens;

import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;

public class BoxScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground = new Entity(Options.cameraCenterX, Options.cameraCenterY, "assets/gfx/sb.png", this);

	@SuppressWarnings("unused")
	private final Entity mBackButton = new Entity(30f, Options.cameraHeight - 35f, 1, 2, "assets/gfx/back-btn.png", this.mBackground) {
		@Override
		public void onTouch() {
			super.onTouch();

			mScreensManager.set(Screen.MENU);
		}
	};

	@SuppressWarnings("unused")
	private final Entity mPinkCloud = new Entity(300f, 40f, "assets/gfx/pink-cloud.png", this.mBackground);

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen(final ScreensManager pScreensManager, final Camera pCamera) {
		super(pScreensManager, pCamera);
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
		mScreensManager.set(Screen.MENU);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Classes
	// ===========================================================

}