package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class GraphicsBubble extends Bubble {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pParentScreen
	 */
	public GraphicsBubble(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen, false);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Bubble#create()
	 */
	@Override
	public Entity create() {
		this.setPosition((Options.cameraWidth / 3) * 2 + Game.random.nextInt(Options.cameraWidth / 3) - this.getHeightScaled(), Options.cameraHeight);

		this.setSpeedX(0f);
		this.setSpeedY(Game.random.nextInt(10));

		this.mDeathTime = 200f;
		this.mSpeedDecrement = 0f;

		this.isScaleAction = false;

		return super.create();
	}
}
