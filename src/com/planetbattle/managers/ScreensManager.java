package com.planetbattle.managers;

import com.planetbattle.screens.Screen;
import com.planetbattle.screens.SplashScreen;
import com.planetbattle.ui.Camera;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreensManager {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** List of available screens */
	private Screen[] screens;

	/** First available screen */
	private int screen;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScreensManager(final Camera pCamera) {
		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all screens */
		screens[Screen.SPLASH] = new SplashScreen(pCamera);

		this.screen = Screen.SPLASH;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final int screen) {
		this.get(this.screen).onDetached();
		this.screen = screen;
		this.get(this.screen).onAttached();
	}

	public Screen get(final int screen) {
		return screens[screen];
	}

	public Screen getCurrent() {
		return this.get(screen);
	}

	public void update(final float pSecondsElapsed, final Camera pCamera) {
		screens[screen].onManagedUpdate(pSecondsElapsed, pCamera);
	}

	public void draw() {
		screens[screen].onManagedDraw();
	}

	// ===========================================================
	// Classes
	// ===========================================================

}
