package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen1;

public class Bubble extends Entity implements IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public final static float mMaxSpeedY = 10f * Options.cameraRatioFactor;
	public final static float mMaxSpeedX = 10f * Options.cameraRatioFactor;

	public final static float minScale = 0.3f * Options.cameraRatioFactor; // TODO: (R) Find right minimal scale.
	public final static float maxScale = 1.7f * Options.cameraRatioFactor; // TODO: (R) Find right maximal scale.
	public final static float scaleStep = 0.05f * Options.cameraRatioFactor;; // TODO: (R) Find right step of scale.

	private final static float mDecrementStep = 0.04f * Options.cameraRatioFactor;

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mMinScaleX;
	protected float mMaxScaleX;
	protected float mSpeedScaleX = 0.003f * Options.cameraRatioFactor;

	protected float mMinScaleY;
	protected float mMaxScaleY;
	protected float mSpeedScaleY = 0.006f * Options.cameraRatioFactor;

	protected boolean mIsYReverse = false;
	protected boolean mIsXReverse = true;

	protected float mScaleY = mMinScaleY;
	protected float mScaleX = mMaxScaleX;

	protected boolean isScaleAction = false;
	protected boolean isFlyAction = true;
	protected boolean isScaleDefined = false;

	protected float mSpeedDecrement;
	protected float mDeathTime;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen, final boolean pNeedAlpha, final boolean pRegisterTouchArea) {
		super(pTiledTextureRegion, pParentScreen, pRegisterTouchArea);

		if (pNeedAlpha) {
			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
			this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
			this.setAlpha(0.8f);
		}
	}

	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen, final boolean pNeedAlpha) {
		this(pTiledTextureRegion, pParentScreen, pNeedAlpha, false);
	}

	public Bubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
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

		this.mMinScaleX = this.getScaleX() - (0.2f * Options.cameraRatioFactor);
		this.mMinScaleY = this.getScaleY() - (0.2f * Options.cameraRatioFactor);

		this.mMaxScaleX = this.getScaleX();
		this.mMaxScaleY = this.getScaleY() + (0.2f * Options.cameraRatioFactor);

		this.mScaleY = this.mMinScaleY;
		this.mScaleX = this.mMaxScaleX;

		this.isScaleDefined = true;

		this.setSpeedY(-this.getSpeedY() + this.mSpeedDecrement);
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
	public Entity create() {
		this.setSpeedX(0f);
		this.setSpeedY(4f * Options.cameraRatioFactor);

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
				this.mSpeedDecrement += mDecrementStep;
				this.setScale(this.getScaleX() + scaleStep);
				Options.scalePower -= scaleStep;
				LevelScreen1.AIR--;
			}
		}
		else {
			this.mDeathTime--;
			if (this.mDeathTime <= 0 && this.isFlyAction) {
				if (!this.isAnimationRunning()) {
					this.animate(40, 0, this);
				}
			}

			if (this.isFlyAction) {
				this.mY += this.getSpeedY();
				this.mX += this.getSpeedX();
				if (this.getCenterY() + this.getHeightScaled() < 0) {
					this.destroy();
				}
			}

			float angle = (float)Math.atan2(this.getSpeedY(), this.getSpeedX());
			
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

				this.setScaleY(this.mScaleY * FloatMath.sin(angle));
				this.setScaleX(this.mScaleX * FloatMath.cos(angle));
			}
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Bubble(getTextureRegion(), this.mParentScreen);
	}

}
