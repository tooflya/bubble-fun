package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Options;

public class Bubble extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static float TIME_TO_ANIMATION = 0.02f;

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mMinScaleX = 0.8f * Options.CAMERA_RATIO_FACTOR;
	protected float mMaxScaleX = 1f * Options.CAMERA_RATIO_FACTOR;
	protected float mSpeedScaleX = 0.003f * Options.CAMERA_RATIO_FACTOR;

	protected float mMinScaleY = 1f * Options.CAMERA_RATIO_FACTOR;
	protected float mMaxScaleY = 1.2f * Options.CAMERA_RATIO_FACTOR;
	protected float mSpeedScaleY = 0.003f * Options.CAMERA_RATIO_FACTOR;

	protected boolean mIsYReverse = false;
	protected boolean mIsXReverse = true;

	protected float mScaleY = mMinScaleY;
	protected float mScaleX = mMaxScaleX;

	private boolean mIsAnimationReverse;

	private final float mMaxOffsetY = 1.0f, mMinOffsetY = -1.0f;
	private float mOffsetY;

	protected boolean isScaleAction = false;
	protected boolean isFlyAction = true;

	private float stepY = 2f; // TODO: (R) Find right step.

	private static float minScale = 0.1f; // TODO: (R) Find right minimal scale.
	private static float maxScale = 2; // TODO: (R) Find right maximal scale.
	private static float scaleStep = 0.05f; // TODO: (R) Find right step of scale.

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pNeedParent
	 */
	public Bubble(TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent) {
		super(pTiledTextureRegion, pNeedParent);

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
	}

	/**
	 * @param pTiledTextureRegion
	 */
	public Bubble(TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, true);
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void setIsScale(final boolean isScale) {
		if (!this.isScaleAction && isScale) {
			this.setScale(minScale);
		}
		this.isScaleAction = isScale;
		this.mScaleY = this.getScaleY();
	}

	// ===========================================================
	// Getters
	// ===========================================================

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

		if (this.isScaleAction) {
			if (this.getScaleX() + scaleStep < Math.min(maxScale, Options.scalePower)) {
				this.setScale(this.getScaleX() + scaleStep);
				Options.scalePower -= scaleStep;
			}
		}
		else {
			if (this.isFlyAction) {
				this.setCenterY(this.getCenterY() - this.stepY);
				if (this.getCenterY() + this.getHeightScaled() < 0) {
					this.destroy();
				}
			}

			if (this.mIsYReverse) {
				this.mScaleY -= mSpeedScaleY;
				if (this.mScaleY < mMinScaleY) {
					this.mIsYReverse = !this.mIsYReverse;
				}
			} else {
				this.mScaleY += mSpeedScaleY;
				if (this.mScaleY > mMaxScaleY) {
					this.mIsYReverse = !this.mIsYReverse;
				}
			}

			if (this.mIsXReverse) {
				this.mScaleX -= mSpeedScaleX;
				if (this.mScaleX < mMinScaleX) {
					this.mIsXReverse = !this.mIsXReverse;
				}
			} else {
				this.mScaleX += mSpeedScaleX;
				if (this.mScaleX > mMaxScaleX) {
					this.mIsXReverse = !this.mIsXReverse;
				}
			}

			this.setScaleY(this.mScaleY);
			this.setScaleX(this.mScaleX);

			if (this.mIsAnimationReverse) {
				this.mOffsetY += TIME_TO_ANIMATION;
				if (this.mOffsetY > this.mMaxOffsetY) {
					this.mIsAnimationReverse = false;
				}
			} else {
				this.mOffsetY -= TIME_TO_ANIMATION;
				if (this.mOffsetY < this.mMinOffsetY) {
					this.mIsAnimationReverse = true;
				}
			}

			this.mY += mOffsetY;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Bubble(getTextureRegion());
	}

}
