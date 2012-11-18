package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.Screen;

public class Gradient extends org.anddev.andengine.entity.sprite.Sprite {

	public Gradient(float pX, float pY, float pWidth, float pHeight, TextureRegion pTextureRegion, final Screen mParent) {
		super(pX, pY, pWidth, pHeight, pTextureRegion);

		mParent.attachChild(this);

		this.setPosition(pX, pY);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/**
	 * 
	 */
	public void setBackgroundCenterPosition() {
		this.mX = Options.screenWidth / 2 - this.mScaleCenterX - (this.mWidth / 2 - this.mScaleCenterX) * this.mScaleX;
		this.mY = Options.screenHeight / 2 - this.mScaleCenterY - (this.mHeight / 2 - this.mScaleCenterY) * this.mScaleY;
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#setPosition(float, float)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		super.setPosition(pX, pY);

		float factorX, factorY;

		if (this.mX > Options.cameraCenterX) {
			factorX = (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2;
		} else {
			factorX = -(Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2;
		}

		if (this.mY > Options.cameraCenterY) {
			factorY = (Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2;
		} else {
			factorY = -(Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2;
		}

		this.mX += factorX / Options.cameraRatioFactor;
		this.mY += factorY / Options.cameraRatioFactor;
	}

	@Override
	protected void onInitDraw(final GL10 pGL) {
		super.onInitDraw(pGL);

		GLHelper.enableDither(pGL);
	}
}
