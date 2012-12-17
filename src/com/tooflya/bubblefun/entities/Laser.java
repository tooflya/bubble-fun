package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import com.tooflya.bubblefun.Options;

import android.util.FloatMath;

public class Laser extends Entity {

	public Laser(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public void init(final float pX, final float pY, final float xr, final float yr) {
		this.setCenterPosition(pX, pY);

		final float angle = (float) Math.atan2(yr - pY, xr - pX);

		float distance = MathUtils.distance(pX, pY, xr, yr);

		float dx = distance * FloatMath.cos(angle);
		float dy = distance * FloatMath.sin(angle);

		dx = dx / FloatMath.sqrt((float) (Math.pow(dx, 2) + Math.pow(dy, 2)));
		dy = dy / FloatMath.sqrt((float) (Math.pow(dx, 2) + Math.pow(dy, 2)));

		this.setSpeed(dx * 15f, dy * 15f);

		this.mRotation = (float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI);
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
