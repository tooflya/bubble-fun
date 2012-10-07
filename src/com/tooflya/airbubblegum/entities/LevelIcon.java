package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;

public class LevelIcon extends Entity {

	private int mAddToScreen;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		this.mAddToScreen = pScreen;

		Game.screens.get(pScreen).attachChild(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new LevelIcon(getTextureRegion(), mAddToScreen);
	}

}
