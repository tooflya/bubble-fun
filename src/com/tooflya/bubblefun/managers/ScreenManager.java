package com.tooflya.bubblefun.managers;

import org.anddev.andengine.engine.camera.hud.HUD;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.ExitScreen;
import com.tooflya.bubblefun.screens.LevelChoiseScreen;
import com.tooflya.bubblefun.screens.LevelEndScreen;
import com.tooflya.bubblefun.screens.LevelScreen1;
import com.tooflya.bubblefun.screens.MenuScreen;
import com.tooflya.bubblefun.screens.PreloaderScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class ScreenManager {

	// ===========================================================
	// Constants
	// ===========================================================

	// private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static HUD hud = new HUD();

	// ===========================================================
	// Fields
	// ===========================================================

	/** List of available screens */
	public Screen[] screens;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ScreenManager() {
		// Game.loadTextures(mBackgroundTextureAtlas);

		Game.camera.setHUD(hud);

		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		screens[Screen.CHOISE] = new LevelChoiseScreen();
		screens[Screen.LOAD] = new PreloaderScreen();
		screens[Screen.LEVEL] = new LevelScreen1();
		screens[Screen.LEVELEND] = new LevelEndScreen();
		screens[Screen.EXIT] = new ExitScreen();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final int pScreen) {
		Game.engine.getScene().onDetached();
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
