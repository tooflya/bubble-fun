package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class SmallMeteorit extends Meteorit {

	public SmallMeteorit(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		this.setCurrentTileIndex(Game.random.nextInt(5) + 1);

		final float speed = Game.random.nextInt(this.getCurrentTileIndex() + 1) + 1;
		
		this.setSpeedX(speed);
		this.setSpeedY(speed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mX > Options.cameraWidth || this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}
}
