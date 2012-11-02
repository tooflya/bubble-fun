package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;

public class Bubble extends Entity implements IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public final static float mMaxSpeed = 2f;
	public final static float mMaxFastSpeed = 4f;

	public final static float minScale = 0.4f; // TODO: (R) Find right minimal scale.
	public final static float maxScale = 1.7f; // TODO: (R) Find right maximal scale.
	public final static float scaleStep = 0.05f; // TODO: (R) Find right step of scale.

	private final static float mDecrementStep = 0.05f / Options.SPEED;
	private final static float mFastDecrementStep = 0.1f / Options.SPEED;

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mMinScaleX;
	protected float mMaxScaleX;
	protected float mSpeedScaleX = 0.003f;

	protected float mMinScaleY;
	protected float mMaxScaleY;
	protected float mSpeedScaleY = 0.006f;

	protected boolean mIsYReverse = false;
	protected boolean mIsXReverse = true;

	protected float mScaleY = mMinScaleY;
	protected float mScaleX = mMaxScaleX;

	protected boolean isScaleAction = false;
	protected boolean isFlyAction = true;
	protected boolean isScaleDefined = false;

	protected float mSpeedDecrement;
	protected float mFastSpeedDecrement;
	protected float mDeathTime;

	public int birdsKills;
	private boolean mShowsLabel;

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

		this.mMinScaleX = this.getScaleX() - 0.2f;
		this.mMinScaleY = this.getScaleY() - 0.2f;

		this.mMaxScaleX = this.getScaleX();
		this.mMaxScaleY = this.getScaleY() + 0.2f;

		this.mScaleY = this.mMinScaleY;
		this.mScaleX = this.mMaxScaleX;

		this.isScaleDefined = true;

		this.setSpeedYA(this.getSpeedY() * Options.SPEED - this.mSpeedDecrement);
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

	public void setSpeedYA(final float pSpeedY) {
		super.setSpeedY(pSpeedY > mMaxSpeed ? mMaxSpeed - this.mSpeedDecrement : pSpeedY);
	}

	public void setSpeedYB(final float pSpeedY) {
		super.setSpeedY((pSpeedY > mMaxFastSpeed ? mMaxFastSpeed : pSpeedY) / this.mFastSpeedDecrement);
	}

	public void setSpeedXB(final float pSpeedX) {
		super.setSpeedX((pSpeedX > mMaxFastSpeed ? mMaxFastSpeed : pSpeedX) / this.mFastSpeedDecrement);
	}

	@Override
	public Entity create() {
		this.setSpeedX(0f);
		this.setSpeedYA(mMaxSpeed);

		this.mDeathTime = 200f;
		this.mSpeedDecrement = 0f;
		this.mFastSpeedDecrement = 0f;

		this.stopAnimation();
		this.setCurrentTileIndex(0);

		this.birdsKills = 0;
		this.mShowsLabel = false;

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
				this.mFastSpeedDecrement += mFastDecrementStep;
				this.setScale(this.getScaleX() + scaleStep);
				Options.scalePower -= scaleStep;
				LevelScreen.AIR--;
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
			}
		}

		if (this.birdsKills > 1 && !this.mShowsLabel) {

			boolean hasTopChiks = false;

			for (int n = LevelScreen.chikies.getCount() - 1; n >= 0; --n) {
				if (LevelScreen.chikies.getByIndex(n).getY() < this.getY()) {
					hasTopChiks = true;
				}
			}

			if (!hasTopChiks) {
				this.mShowsLabel = true;
				LevelScreen.mDoubleKillText.create().setCenterPosition(this.getCenterX(), this.getCenterY());
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
