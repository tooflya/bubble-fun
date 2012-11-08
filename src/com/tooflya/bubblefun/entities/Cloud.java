package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

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

	public Cloud(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);
	}

	public Cloud(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.setScaleY(Options.cameraRatioFactor);
		this.setScaleX(Options.cameraRatioFactor);

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mX -= this.getSpeedX();
		if (this.mX + this.getWidthScaled() < 0) {
			this.destroy();
		}
	}
}
