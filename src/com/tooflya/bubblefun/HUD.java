package com.tooflya.bubblefun;

public class HUD extends org.anddev.andengine.engine.camera.hud.HUD {

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		// ====================================================================================
		// FOR THE BETA VERSION ONLY
		// TODO: Remove this before relise 
		Beta.update();
		// ====================================================================================
	}

}
