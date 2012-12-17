package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

public class BubbleBrokes extends Entity {

	private float mNormalTime;

	public BubbleBrokes(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();
	}

	public void init(final float pX, final float pY) {
		this.setAlpha(1f);
		this.setCenterPosition(pX, pY);
		this.setCurrentTileIndex(Game.random.nextInt(3));

		final float speed = Game.random.nextFloat() * 2 - 1;

		this.setSpeed(speed / 2, speed / 2);

		this.mNormalTime = 2f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mNormalTime -= pSecondsElapsed;

		this.mRotation += Game.random.nextInt(3);

		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mNormalTime <= 0) {
			this.mAlpha -= 0.01;

			if (this.mAlpha <= 0) {
				this.destroy();
			}
		}
	}
}
