package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class Star extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mAddToScreen;

	private float stepX = 0;
	private float stepY = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pScreen
	 */
	public Star(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		Game.screens.get(pScreen).attachChild(this);

		this.mAddToScreen = pScreen;
	}

	@Override
	public Entity deepCopy() {
		return new Star(getTextureRegion(), this.mAddToScreen);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public Star Init(final int i) {
		stepX = 5f * FloatMath.sin(i * 2 * Options.PI / 5);
		stepY = 5f * FloatMath.cos(i * 2*Options.PI / 5);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.setPosition(this.getX() + this.stepX, this.getY() + this.stepY);
	}

}
