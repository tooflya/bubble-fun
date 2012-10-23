package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Screen;

public class Sprite extends Entity {

	public Sprite(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public Sprite(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen, final boolean pTouchArea) {
		super(pTiledTextureRegion, pParentScreen, pTouchArea);
	}

	@Override
	public Entity deepCopy() {
		return new Sprite(getTextureRegion(), this.mParentScreen);
	}
}
