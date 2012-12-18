package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class AwesomeText extends Entity {
	private float mScaleStep = 0.3f;

	protected boolean mIsAnimationScaleRunning;
	protected boolean mIsAnimationReverse;

	public boolean shake = false;
	private float minS = -5, maxS = 5;
	private boolean reverse = false;
	private float step = 3f;
	private int mi = 40, i = 0;

	private boolean mDisappear;

	private boolean mNeedDeath;

	public AwesomeText(final TiledTextureRegion pTiledTextureRegion, final boolean pDisappear, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.mDisappear = pDisappear;

		this.enableBlendFunction();

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
	}

	public AwesomeText(final TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, true, pParentScreen);
	}

	protected void onAnimationFinished() {
		this.mRotation = -5;
	}

	public void setReverse() {
		this.setScale(0f);

		this.mScaleStep = 0.03f;
		this.mIsAnimationReverse = true;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mIsAnimationScaleRunning = true;
		this.mIsAnimationReverse = false;
		mNeedDeath = false;
		shake = false;

		this.setScale(10f);
		this.setAlpha(0f);

		this.setRotation(-5 + Game.random.nextInt(10));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (mIsAnimationScaleRunning) {
			if (this.mIsAnimationReverse) {
				if (this.getAlpha() < 1f) {
					this.setAlpha(this.getAlpha() + 0.05f);
				}

				this.setScale(this.getScaleX() + mScaleStep);
				if (this.getScaleX() >= 1f) {
					this.setScale(1f);
					this.mIsAnimationScaleRunning = false;
					this.shake = true;
				}
			} else {
				if (this.getAlpha() < 1f) {
					this.setAlpha(this.getAlpha() + 0.05f);
				}

				this.setScale(this.getScaleX() - mScaleStep);
				if (this.getScaleX() <= 1f) {
					this.setScale(1f);
					this.mIsAnimationScaleRunning = false;
					this.shake = true;
				}
			}
		}

		if (shake) {
			i++;
			if (reverse) {
				this.mRotation += step;
				if (this.mRotation >= maxS) {
					this.reverse = false;
				}
			}
			else {
				this.mRotation -= step;
				if (this.mRotation <= minS) {
					this.reverse = true;
				}
			}
			if (i >= mi) {
				this.i = 0;
				this.shake = false;
				this.mNeedDeath = true;
				this.reverse = false;
				this.onAnimationFinished();
			}
		}

		if (this.mNeedDeath && this.mDisappear) {
			if (this.getAlpha() >= 0) {
				this.setAlpha(this.getAlpha() - 0.005f);
			} else {
				this.destroy();
			}
		}

	}

	@Override
	public void setCenterPosition(final float pX, final float pY) {
		float x;

		if (pX > Options.cameraWidth - this.getWidth()) {
			x = Options.cameraWidth - (this.getWidth() * 2);
		} else if (pX < this.getWidth()) {
			x = this.getWidth() * 2;
		} else {
			x = pX;
		}

		super.setCenterPosition(x, pY);
	}

}
