package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.CloudsManager;

public abstract class ReflectionScreen extends Screen {

	protected CloudsManager<Cloud> mClouds;

	protected Entity mBackground;
	protected Entity mBackgroundGrass;
	protected Entity mBackgroundHouses;
	protected Entity mBackgroundWater;

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mClouds != null) {
			this.mClouds.update();
		}
	}

}
