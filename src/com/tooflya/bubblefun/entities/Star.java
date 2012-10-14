package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class Star extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mAddToScreen;

	private boolean isParticle;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pScreen
	 */
	public Star(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		Game.screens.get(pScreen).attachChild(this);

		this.mAddToScreen = pScreen;
	}

	@Override
	public Entity deepCopy() {
		return new Star(getTextureRegion(), this.mAddToScreen);
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
		this.setSpeedX(3f * FloatMath.sin(i * 2 * Options.PI / 7));
		this.setSpeedY(3f * FloatMath.cos(i * 2 * Options.PI / 7));

		this.mRotation = (float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI);

		this.mScaleX = 0.1f;
		this.mScaleY = 0.1f;

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
			this.mX += this.getSpeedX();
			this.mY += this.getSpeedY();

			this.mScaleX += 0.03f;
			this.mScaleY += 0.03f;

			this.mAlpha -= 0.01f;

			if (this.mAlpha < 0) {
				this.destroy();
			}
		}
	}
}
