package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public class BubbleBase extends Entity {
	private float pScaleStepX = Options.bubbleBaseScaleStep;
	private float pScaleStepY = -Options.bubbleBaseScaleStep;

	public BubbleBase(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity create() {
		this.mScaleX = Options.bubbleBaseMinScale;
		this.pScaleStepX = Options.bubbleBaseScaleStep;
		this.mScaleY = Options.bubbleBaseMaxScale;
		this.pScaleStepY = -Options.bubbleBaseScaleStep;

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

		this.mScaleX += this.pScaleStepX;
		this.mScaleY += this.pScaleStepY;

		if (this.mScaleX + this.pScaleStepX < Options.bubbleBaseMinScale) {
			this.pScaleStepX = Options.bubbleBaseScaleStep;
		}
		if (Options.bubbleBaseMinScale < this.mScaleX + this.pScaleStepX) {
			this.pScaleStepX = -Options.bubbleBaseScaleStep;
		}
		if (this.mScaleY + this.pScaleStepY < Options.bubbleBaseMinScale) {
			this.pScaleStepY = Options.bubbleBaseScaleStep;
		}
		if (Options.bubbleBaseMinScale < this.mScaleY + this.pScaleStepY) {
			this.pScaleStepY = -Options.bubbleBaseScaleStep;
		}
	}
}
