package com.planetbattle.managers;

import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.screens.BoxScreen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.LoadingScreen;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.Screen;
import com.tooflya.bubblefun.screens.SplashScreen;

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
		screens[Screen.SPLASH] = new SplashScreen(this, pCamera);
		screens[Screen.LOADING] = new LoadingScreen(this, pCamera);
		screens[Screen.MENU] = new MenuScreen(this, pCamera);
		screens[Screen.BOX] = new BoxScreen(this, pCamera);
		screens[Screen.EXIT] = new ExitScreen(this, pCamera);

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

	public void onManagedUpdate(final float pSecondsElapsed) {
		this.getCurrent().onManagedUpdate(pSecondsElapsed);
	}

	public void onManagedDraw(final float pSecondsElapsed) {
		this.getCurrent().onManagedDraw(pSecondsElapsed);
	}

	// ===========================================================
	// Classes
	// ===========================================================

}
