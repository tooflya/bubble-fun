package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class AwesomeText extends Entity {
	private static float mScaleStep = 0.3f;

	protected boolean mIsAnimationScaleRunning;

	public boolean shake = false;
	private float minS = -5, maxS = 5;
	private boolean reverse = false;
	private float step = 3f;
	private int mi = 40, i = 0;

	private boolean mNeedDeath;

	public AwesomeText(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
	}

	protected void onAnimationFinished() {
		this.mRotation = -5;
	}

	@Override
	public Entity create() {
		mIsAnimationScaleRunning = true;
		mNeedDeath = false;
		shake = false;

		this.setScale(10f);
		this.setAlpha(0f);

		this.setRotation(-5 + Game.random.nextInt(10));

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

		if (mIsAnimationScaleRunning) {
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

		if (this.mNeedDeath) {
			if (this.getAlpha() >= 0) {
				this.setAlpha(this.getAlpha() - 0.005f);
			} else {
				this.destroy();
			}
		}

	}

	@Override
	public void setCenterPosition(final float pX, final float pY) {
		final float x = pX;// > Options.cameraOriginRatioX - (this.getWidth() * 2) ? Options.cameraOriginRatioX - (this.getWidth() * 2) : pX;

		super.setCenterPosition(x, pY);
	}

	@Override
	public Entity deepCopy() {
		return new AwesomeText(getTextureRegion(), this.mParentScreen);
	}

}
