package com.tooflya.bubblefun.screens;

import com.badlogic.gdx.Gdx;
import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;

public class ExitScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground = new Entity(Options.cameraCenterX, Options.cameraCenterY, "assets/gfx/popup-exit.png", this) {
		@Override
		public void onTouch() {
			super.onTouch();

			this.setScale(this.getScaleX() - 0.01f);
		}
	};

	@SuppressWarnings("unused")
	private final Entity mYesButton = new Entity(0, 0,/*this.mBackground.getCenterX() - 90f, this.mBackground.getHeight() - 5f,*/1, 2, "assets/gfx/accept-btn.png", this.mBackground) {
		@Override
		public void onTouch() {
			super.onTouch();

			Gdx.app.exit();
		}
	};

	@SuppressWarnings("unused")
	private final Entity mNoButton = new Entity(this.mBackground.getCenterX() + 90f, this.mBackground.getHeight() - 5f, 1, 2, "assets/gfx/decline-btn.png", this.mBackground) {
		@Override
		public void onTouch() {
			super.onTouch();

			mScreensManager.get(Screen.MENU).clearChildScreen();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public ExitScreen(final ScreensManager pScreensManager, final Camera pCamera) {
		super(pScreensManager, pCamera);

		//this.setScale(0.4f);
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