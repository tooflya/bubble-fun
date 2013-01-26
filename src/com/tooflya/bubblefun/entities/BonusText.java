package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.screens.LevelScreen;

public class BonusText extends AwesomeText {
	private float mSleepTime;

	protected boolean mIsAnimationRunning;

	private int mScoreIncrement;

	public BonusText(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	protected void onAnimationFinished() {
		super.onAnimationFinished();

		this.mRotation = 5;

		this.shake = true;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		this.mIsAnimationScaleRunning = false;
		this.mIsAnimationUp = true;
		this.mSleepTime = 50f;
		this.mDisappearSpeed = 0.005f;
	}

	@Override
	public void setCurrentTileIndex(final int pTileIndex) {
		super.setCurrentTileIndex(pTileIndex);

		switch (pTileIndex) {
		case 0:
			this.mScoreIncrement = 600;
			break;
		case 1:
			this.mScoreIncrement = 300;
			break;
		case 2:
			this.mScoreIncrement = -150;
			break;
		case 3:
			this.mScoreIncrement = -100;
			break;
		}

		LevelScreen.mCurrentScore += this.mScoreIncrement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (--mSleepTime == 0) {
			this.mIsAnimationScaleRunning = true;
		}

	}

	/**
	 * @Override public void setCenterPosition(final float pX, final float pY) { super.setCenterPosition(pX, pY + this.getHeight() * 2); }
	 */

}
