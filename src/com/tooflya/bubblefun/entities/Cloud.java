package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;

/**
 * @author Tooflya.com
 * @since
 */
public class Cloud extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public Cloud(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	public Cloud(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.setScaleY(Options.CAMERA_RATIO_FACTOR);
		this.setScaleX(Options.CAMERA_RATIO_FACTOR);

		this.setAlpha(1f);

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

		this.mX -= this.getSpeedX();
		if (this.mX + this.getWidthScaled() < 0) {
			this.destroy();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Cloud(getTextureRegion(), this.mParentScreen);
	}
}
