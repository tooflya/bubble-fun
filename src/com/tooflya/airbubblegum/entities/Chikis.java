package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;


/**
 * @author Tooflya.com
 * @since
 */
public class Chikis extends Entity {

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
	 */
	public Chikis(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

// TODO: Add code.
//		this.setPosition(this.mX - Game.world.personage.runStep * (Options.fps / Game.fps), this.mY);
//		if (this.mX + this.getWidthScaled() < 0) {
//			this.destroy();
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Chikis(getTextureRegion());
	}
}
