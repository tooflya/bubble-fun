package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Options;

public class Star extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float mStepX = 0;
	private float mStepY = 0;

	private boolean isParticle;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Star(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity deepCopy() {
		return new Star(getTextureRegion(), this.mParentScreen);
	}

	@Override
	public Entity create() {
		this.isParticle = false;

		return super.create();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public Star Init(final int i) {
		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.setScaleCenter(this.getWidth()/2, this.getHeight()/2);
		
		this.mStepX = 5f * FloatMath.sin(i * 2 * Options.PI / 7);
		this.mStepY = 5f * FloatMath.cos(i * 2 * Options.PI / 7);

		this.mRotation = (float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI);

		this.mScaleX = 0.02f * Options.cameraRatioFactor;
		this.mScaleY = 0.02f * Options.cameraRatioFactor;

		this.mAlpha = 1f;

		this.isParticle = true;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.isParticle) {
			this.mX += this.mStepX;
			this.mY += this.mStepY;

			this.mScaleX += 0.03f * Options.cameraRatioFactor;
			this.mScaleY += 0.03f * Options.cameraRatioFactor;

			this.mAlpha -= 0.01f;

			if (this.mAlpha < 0) {
				this.destroy();
			}
		}
	}
}
