package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public class BubbleBase extends Entity {
	protected float pScaleStepX, pScaleStepY;

	public BubbleBase(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity create() {
		this.mScaleX = Options.bubbleBaseMinScale;
		this.mScaleY = Options.bubbleBaseMaxScale;

		this.pScaleStepX = Options.bubbleBaseStepScale;
		this.pScaleStepY = -Options.bubbleBaseStepScale;

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

		this.setScaleCenter(this.mWidth / 2, this.mHeight / 2);

		this.mScaleX += this.pScaleStepX;
		this.mScaleY += this.pScaleStepY;

		if (this.mScaleX + this.pScaleStepX < Options.bubbleBaseMinScale) {
			this.pScaleStepX = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleX + this.pScaleStepX) {
			this.pScaleStepX = -Options.bubbleBaseStepScale;
		}
		if (this.mScaleY + this.pScaleStepY < Options.bubbleBaseMinScale) {
			this.pScaleStepY = Options.bubbleBaseStepScale;
		}
		if (Options.bubbleBaseMaxScale < this.mScaleY + this.pScaleStepY) {
			this.pScaleStepY = -Options.bubbleBaseStepScale;
		}
	}

}
