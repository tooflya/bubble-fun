package com.tooflya.bubblefun.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class Application {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public static void main(String[] args) {
		new LwjglApplication(new Game(), "", Options.cameraWidth, Options.cameraHeight, Options.needsGLES2);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Classes
	// ===========================================================

}
