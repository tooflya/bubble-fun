package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen1;

public class Bubble extends Entity implements IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static float TIME_TO_ANIMATION = 0.02f;

	public final static float mMaxSpeedY = 10f;
	public final static float mMaxSpeedX = 10f;

	public final static float minScale = 0.3f * Options.CAMERA_RATIO_FACTOR; // TODO: (R) Find right minimal scale.
	public final static float maxScale = 1.7f * Options.CAMERA_RATIO_FACTOR; // TODO: (R) Find right maximal scale.
	public final static float scaleStep = 0.05f * Options.CAMERA_RATIO_FACTOR;; // TODO: (R) Find right step of scale.

	private final static float mMaxOffsetY = 1.0f, mMinOffsetY = -1.0f;

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
	private float mOffsetY;

	protected boolean isScaleAction = false;
	protected boolean isFlyAction = true;
	protected boolean isScaleDefined = false;

	protected float mSpeedDecrement;
	protected float mDeathTime;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Bubble(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen, final boolean pNeedAlpha, final boolean pRegisterTouchArea) {
		super(pTiledTextureRegion, pParentScreen, pRegisterTouchArea);

		if (pNeedAlpha) {
			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
			this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
			this.setAlpha(0.8f);
		}
	}


	public Bubble(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen, final boolean pNeedAlpha) {
		this(pTiledTextureRegion, pParentScreen, pNeedAlpha, false);
	}


	public Bubble(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
		this(pTiledTextureRegion, pParentScreen, true);
	}


	public Bubble(TiledTextureRegion pTiledTextureRegion) {
		this(pTiledTextureRegion, null, true);
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

	@Override
	public void setSpeedX(final float pSpeedX) {
		super.setSpeedX(this.getSpeedX() - pSpeedX * Options.CAMERA_RATIO_FACTOR);
	}

	@Override
	public void setSpeedY(final float pSpeedY) {
		super.setSpeedY(pSpeedY > mMaxSpeedY ? mMaxSpeedY - this.mSpeedDecrement * 5 : pSpeedY - this.mSpeedDecrement);
	}

	@Override
	public Entity create() {
		this.setSpeedX(0f);
		this.setSpeedY(2f);

		this.mDeathTime = 200f;
		this.mSpeedDecrement = 0f;

		this.stopAnimation();
		this.setCurrentTileIndex(0);

		return super.create();
	}

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
				this.setSpeedY(this.getSpeedY() - 0.05f);
				this.mSpeedDecrement += 0.05f;
				this.setScale(this.getScaleX() + scaleStep);
				Options.scalePower -= scaleStep;
				LevelScreen1.AIR--;
			}
		}
		else {
			this.mDeathTime--;
			if (this.mDeathTime <= 0 && this.isFlyAction) {
				if (!this.isAnimationRunning()) {
					System.out.println("EVENT");
					this.animate(40, 0, this);
				}
			}

			if (this.isFlyAction) {
				this.mY -= this.getSpeedY();
				this.mX -= this.getSpeedX();
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
					if (this.mOffsetY > mMaxOffsetY) {
						this.mIsAnimationReverse = false;
					}
				} else {
					this.mOffsetY -= TIME_TO_ANIMATION;
					if (this.mOffsetY < mMinOffsetY) {
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
		return new Bubble(getTextureRegion(), this.mParentScreen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener#onAnimationEnd(org.anddev.andengine.entity.sprite.AnimatedSprite)
	 */
	@Override
	public void onAnimationEnd(AnimatedSprite arg0) {
		this.destroy();
	}

}
