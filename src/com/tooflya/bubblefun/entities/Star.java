package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

public class Star extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mAddToScreen;

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

}
