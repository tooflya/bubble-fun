package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;

public class GraphicsBubble extends Bubble {

	public GraphicsBubble(TiledTextureRegion pTiledTextureRegion, boolean pNeedParent) {
		super(pTiledTextureRegion, pNeedParent, false);

		Game.screens.get(Screen.LOAD).attachChild(this);
	}

	@Override
	public Entity create() {
		this.setPosition((Options.cameraWidth / 3) * 2 + Game.random.nextInt(Options.cameraWidth / 3) - this.getHeightScaled(), Options.cameraHeight);
		this.mSpeedX = 0f;
		this.mSpeedY = Game.random.nextInt(10);
		this.mDeathTime = 200f;
		this.mSpeedDecrement = 0f;

		this.isScaleAction = false;

		this.show();

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new GraphicsBubble(getTextureRegion(), false);
	}

}
