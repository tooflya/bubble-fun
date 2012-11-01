package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Sprite extends Entity {

	public Sprite(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public Sprite(TiledTextureRegion pTiledTextureRegion, final Sprite pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public Sprite(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen, final boolean pTouchArea) {
		super(pTiledTextureRegion, pParentScreen, pTouchArea);
	}

	public Sprite(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);
	}

	@Override
	public Entity deepCopy() {
		return new Sprite(getTextureRegion(), this.mParentScreen);
	}
}
