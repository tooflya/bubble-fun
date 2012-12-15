package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class Meteorit extends Sprite {

	public Meteorit(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity create() {
		this.setSpeedX(3f);
		this.setSpeedY(3f);

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

		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mX > Options.cameraWidth || this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}
}
