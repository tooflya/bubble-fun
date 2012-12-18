package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public class Bonus extends Entity {

	private float mWaitingTime;
	private boolean reverseRotation = false;

	public Bonus(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setRotationCenter(0, this.mHeight / 2);
	}

	public void initTime(final float pWaitingTime, final float x, final float speed) {
		this.mWaitingTime = pWaitingTime;

		this.setSpeedY(speed);

		this.setPosition(x, -this.getHeight());
	}

	public void isCollide() {
		this.destroy();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		this.mRotation = 0f;
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mWaitingTime-- <= 0) {
			this.mY += this.getSpeedY();

			if (this.reverseRotation) {
				this.mRotation += 0.2f;
				if (this.mRotation > 10) {
					this.reverseRotation = false;
				}
			} else {
				this.mRotation -= 0.2f;
				if (this.mRotation < -10) {
					this.reverseRotation = true;
				}
			}
		}

		if (this.getY() > Options.cameraHeight) {
			this.destroy();
		}
	}
}
