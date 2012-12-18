package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public class BubbleBase extends Entity {
	protected float mScaleStepX, mScaleStepY;

	public BubbleBase(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity create() {
		this.mScaleStepX = Options.bubbleBaseStepScale;
		this.mScaleStepY = -Options.bubbleBaseStepScale;

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

		this.mScaleX += this.mScaleStepX;
		this.mScaleY += this.mScaleStepY;

		if (this.mScaleX + this.mScaleStepX < Options.bubbleBaseMinScale) {
			this.mScaleStepX = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleX + this.mScaleStepX) {
			this.mScaleStepX = -Options.bubbleBaseStepScale;
		}

		if (this.mScaleY + this.mScaleStepY < Options.bubbleBaseMinScale) {
			this.mScaleStepY = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleY + this.mScaleStepY) {
			this.mScaleStepY = -Options.bubbleBaseStepScale;
		}

	}

}
