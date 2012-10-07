package com.tooflya.airbubblegum.managers;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.screens.LevelChoiseScreen;
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

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static Entity splash = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "splash.png", 0, 0, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}

	};

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
		Game.loadTextures(mBackgroundTextureAtlas);

		splash.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		Game.camera.setHUD(hud);

		hud.attachChild(splash);
		splash.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		splash.setAlpha(0f);
	}

	public void init() {
		screens = new Screen[Screen.SCREENS_COUNT];

		/** Create all scenes */
		screens[Screen.MENU] = new MenuScreen();
		((MenuScreen) screens[Screen.MENU]).init();
		screens[Screen.CHOISE] = new LevelChoiseScreen();
		((LevelChoiseScreen) screens[Screen.CHOISE]).init();
		screens[Screen.LEVEL] = new LevelScreen();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void set(final int pScreen) {
		// splash.
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
