package com.tooflya.bubblefun.screens;

import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entity.Entity;

public class MenuScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground = new Entity(Options.cameraCenterX, Options.cameraCenterY, "assets/gfx/mb.png", this);

	private final Entity mGameName = new Entity(Options.cameraCenterX, 140f, "assets/gfx/main-name.png", this.mBackground);

	private final Entity mPlayButton = new Entity(Options.cameraCenterX, Options.cameraCenterY + 30f, "assets/gfx/play.png", this.mBackground) {
		@Override
		public void onTouch() {
			super.onTouch();

			mScreensManager.set(Screen.BOX);
		}
	};

	private final Entity mSettingsButton = new Entity(30f, Options.cameraHeight - 35f, 1, 2, "assets/gfx/set-btn.png", this.mBackground);

	private final Entity mMoreButton = new Entity(80f, Options.cameraHeight - 33f, 1, 2, "assets/gfx/more-btn.png", this.mBackground);
	private final Entity mMusicButton = new Entity(120f, Options.cameraHeight - 33f, 1, 2, "assets/gfx/sound-btn.png", this.mBackground);

	private final Entity mFacebookButton = new Entity(Options.cameraWidth - 80f, Options.cameraHeight - 33f, 1, 2, "assets/gfx/fb-btn.png", this.mBackground);
	private final Entity mTwitterButton = new Entity(Options.cameraWidth - 30f, Options.cameraHeight - 33f, 1, 2, "assets/gfx/tw-btn.png", this.mBackground);

	// ===========================================================
	// Constructors
	// ===========================================================

	public MenuScreen(final ScreensManager pScreensManager, final Camera pCamera) {
		super(pScreensManager, pCamera);

		this.mPlayButton.setClickable(true);
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
		if (this.hasChildScreen()) {
			this.clearChildScreen();
		} else {
			this.setChildScreen(this.mScreensManager.get(Screen.EXIT), false, false, true);
		}
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Classes
	// ===========================================================

}