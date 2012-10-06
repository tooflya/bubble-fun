package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Options;

public class Bubble extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static float MIN_SCALE_Y = 1f * Options.CAMERA_RATIO_FACTOR;
	private final static float MAX_SCALE_Y = 1.2f * Options.CAMERA_RATIO_FACTOR;
	private final static float SPEED_SCALE_Y = 0.003f * Options.CAMERA_RATIO_FACTOR;

	private final static float MIN_SCALE_X = 0.8f * Options.CAMERA_RATIO_FACTOR;
	private final static float MAX_SCALE_X = 1f * Options.CAMERA_RATIO_FACTOR;
	private final static float SPEED_SCALE_X = 0.003f * Options.CAMERA_RATIO_FACTOR;

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mIsYReverse = false;
	private boolean mIsXReverse = true;

	private float mScaleY = MIN_SCALE_Y;
	private float mScaleX = MAX_SCALE_X;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Bubble(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion, false);

		this.setScaleCenter(this.getWidthScaled() / 2, this.getHeightScaled());

		this.mScaleY = this.getScaleY();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Virtual Methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mIsYReverse) {
			this.mScaleY -= SPEED_SCALE_Y;
			if (this.mScaleY < MIN_SCALE_Y) {
				this.mIsYReverse = !this.mIsYReverse;
			}
		} else {
			this.mScaleY += SPEED_SCALE_Y;
			if (this.mScaleY > MAX_SCALE_Y) {
				this.mIsYReverse = !this.mIsYReverse;
			}
		}

		if (this.mIsXReverse) {
			this.mScaleX -= SPEED_SCALE_X;
			if (this.mScaleX < MIN_SCALE_X) {
				this.mIsXReverse = !this.mIsXReverse;
			}
		} else {
			this.mScaleX += SPEED_SCALE_X;
			if (this.mScaleX > MAX_SCALE_X) {
				this.mIsXReverse = !this.mIsXReverse;
			}
		}

		this.setScaleY(this.mScaleY);
		this.setScaleX(this.mScaleX);
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

}
