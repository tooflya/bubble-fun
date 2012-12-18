package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * @author Tooflya.com
 * @since
 */
public class Cloud extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Cloud(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);
	}

	/**
	 * @param pTiledTextureRegion
	 * @param pParentScreen
	 */
	public Cloud(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mX -= this.getSpeedX();
		if (this.mX + this.getWidth() < 0) {
			this.destroy();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}
