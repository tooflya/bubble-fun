package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.screens.LevelScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class Airgum extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean isScale = false;

	private float stepY = 2f;

	private static float minScale = 0.1f;
	private static float maxScale = 3;
	private static float scaleStep = 0.05f;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Airgum(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Setters
	// ===========================================================

	public void setIsScale(final boolean isScale) {
		if (!this.isScale && isScale) {
			this.setScale(Airgum.minScale);
		}
		this.isScale = isScale;
	}

	// ===========================================================
	// Getters
	// ===========================================================

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

		if (this.isScale) {
			if (this.getScaleX() + Airgum.scaleStep < Math.min(Airgum.maxScale, Options.scalePower)) {
				this.setScale(this.getScaleX() + Airgum.scaleStep);
				Options.scalePower -= Airgum.scaleStep;
			}
		}
		else {
			this.setCenterY(this.getCenterY() - this.stepY);
			if (this.getCenterY() + this.getHeightScaled() < 0) {
				this.destroy();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Airgum(getTextureRegion());
	}
}
