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

	protected float mMinScaleX;
	protected float mMaxScaleX;
	protected float mSpeedScaleX = 0.003f * Options.CAMERA_RATIO_FACTOR;

	protected float mMinScaleY;
	protected float mMaxScaleY;
	protected float mSpeedScaleY = 0.006f * Options.CAMERA_RATIO_FACTOR;

	protected boolean mIsYReverse = false;
	protected boolean mIsXReverse = true;

	protected float mScaleY = mMinScaleY;
	protected float mScaleX = mMaxScaleX;

	private boolean mIsAnimationReverse;

	private final float mMaxOffsetY = 1.0f, mMinOffsetY = -1.0f;
	private float mOffsetY;

	protected boolean isScaleAction = false;
	protected boolean isFlyAction = true;
	protected boolean isScaleDefined = false;

	private float stepY = 0f; // TODO: (R) Find right step.

	private static float minScale = 0.1f; // TODO: (R) Find right minimal scale.
	private static float maxScale = 1.3f; // TODO: (R) Find right maximal scale.
	private static float scaleStep = 0.05f; // TODO: (R) Find right step of scale.

	private int timeToDeath = 0;
	private int maxTimeToDeath = 100;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pNeedParent
	 */
	public Bubble(TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent) {
		super(pTiledTextureRegion, pNeedParent);

		if (pNeedParent) {
			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		} else {
			this.setScaleCenter(this.getWidthScaled() / 2, this.getHeightScaled() / 2);
		}
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

		this.mMinScaleX = this.getScaleX() - (0.2f * Options.CAMERA_RATIO_FACTOR);
		this.mMinScaleY = this.getScaleY() - (0.2f * Options.CAMERA_RATIO_FACTOR);

		this.mMaxScaleX = this.getScaleX();
		this.mMaxScaleY = this.getScaleY() + (0.2f * Options.CAMERA_RATIO_FACTOR);

		this.mScaleY = this.mMinScaleY;
		this.mScaleX = this.mMaxScaleX;

		this.isScaleDefined = true;

		this.timeToDeath = (int) (this.maxTimeToDeath / this.mScaleX); // TODO: (R) Check.
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
			this.timeToDeath--;
			if (this.timeToDeath <= 0) {
				// TODO: (R) Make a boom!
				this.destroy();
			}

			if (this.isFlyAction) {
				this.setCenterY(this.getCenterY() - this.stepY);
				if (this.getCenterY() + this.getHeightScaled() < 0) {
					this.destroy();
				}
			}

			if (this.isScaleDefined) {
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
