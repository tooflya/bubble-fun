package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Electrod extends Entity {

	private final static int mTime = 300;

	private int mCurrentTime;
	public boolean mIsAnimation;

	public Electrod(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.mIsAnimation = false;

		this.stopAnimation(0);

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mCurrentTime++;
		if (mTime == this.mCurrentTime) {
			if (this.mIsAnimation) {
				this.mCurrentTime = 0;
				this.mIsAnimation = false;
				this.stopAnimation(0);
			} else {
				this.mCurrentTime = 0;
				this.mIsAnimation = true;
				this.animate(new long[] { 10, 10, 10, 10 }, new int[] { 1, 2, 3, 4 }, 9999);
			}
		}
	}

	@Override
	public Entity deepCopy() {
		return new Electrod(getTextureRegion(), this.mParentScreen);
	}
}
