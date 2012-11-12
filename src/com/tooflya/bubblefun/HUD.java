package com.tooflya.bubblefun;

public class HUD extends org.anddev.andengine.engine.camera.hud.HUD {

	private float mDeltaTiming = 0;

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.mDeltaTiming += pSecondsElapsed;
		if (this.mDeltaTiming < 0.0125) {
			return;
		} else {
			super.onManagedUpdate(pSecondsElapsed);
			this.mDeltaTiming -= 0.0125;
			while (this.mDeltaTiming >= 0.0125) {
				super.onManagedUpdate(0);
				this.mDeltaTiming -= 0.0125;
			}
		}
	}

}
