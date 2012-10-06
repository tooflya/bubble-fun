package com.tooflya.airbubblegum.managers;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.screens.LevelScreen;
import com.tooflya.airbubblegum.screens.MenuScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** List of available screens */
	public Screen[] screens;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScreenManager() {
	}

	public void init() {
		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		((MenuScreen) screens[Screen.MENU]).init();
		screens[Screen.LEVEL] = new LevelScreen();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final int pScreen) {
		screens[pScreen].setScene(Game.engine);
		screens[pScreen].onAttached();
		Screen.screen = pScreen;
	}

	public Screen get(final int pScreen) {
		return screens[pScreen];
	}

	public Screen getCurrent() {
		return screens[Screen.screen];
	}
}
