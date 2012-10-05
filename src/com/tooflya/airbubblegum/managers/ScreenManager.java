package com.tooflya.airbubblegum.managers;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.screens.LevelScreen;
import com.tooflya.airbubblegum.screens.LoadingScreen;
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
		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.LOADING] = new LoadingScreen();
		screens[Screen.MENU] = new MenuScreen();
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
