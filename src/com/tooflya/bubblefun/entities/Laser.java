package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import com.tooflya.bubblefun.Options;

public class Laser extends Entity {

	public Laser(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public void init(final float pX, final float pY, final float xr, final float yr) {
		this.setCenterPosition(pX, pY);

		final float angle = (float) Math.atan2(yr - pY, xr - pX);

		float distance = MathUtils.distance(pX, pY, xr, yr);

		float dx = xr-pX; // need change variable
		float dy = yr-pY;

		dx = dx /distance;
		dy = dy /distance;

		this.setSpeed(dx * 15f, dy * 15f);

		this.mRotation = (float) (angle* 180 / Math.PI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mX > Options.cameraWidth || this.mX < -this.getWidth() || this.mY > Options.cameraHeight || this.mY < -this.getHeight()) {
			this.destroy();
		}
	}
}
