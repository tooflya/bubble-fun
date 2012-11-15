package com.planetbattle.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class Camera extends OrthographicCamera {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public Camera() {
		super(Options.cameraWidth, Options.cameraHeight);
		setToOrtho(true, Options.cameraWidth, Options.cameraHeight);
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
