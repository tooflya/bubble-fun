package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Aim extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mIsGoningToDeath;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Aim(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#create()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		this.setAlpha(1f);

		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotation(0f);

		this.mIsGoningToDeath = false;
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		//this.mIsGoningToDeath = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mRotation += 3f;

		if (this.mIsGoningToDeath) {
			this.mAlpha -= 0.01f;

			if (this.mAlpha <= 0f) {
				super.onDestroy();
			}
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}
