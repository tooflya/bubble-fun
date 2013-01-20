package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Cloud;

/**
 * @author Tooflya.com
 * @since
 */
public class CloudsManager<T> extends EntityManager<Cloud> {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static int mCloudsAlwaysCount = 8;
	private final static int mCloudsFramesCount = 4;

	private final static float mCloudMaxSpeed = 2f;
	private final static float mCloudMinSpeed = 0.2f;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param capacity
	 * @param element
	 */
	public CloudsManager(int capacity, Cloud element) {
		super(capacity, element);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 */
	public void generateStartClouds() {
		for (int i = 0; i < mCloudsAlwaysCount; i++) {
			this.generateCloud(true);
		}
	}

	/**
	 * @param pIsStart
	 */
	public void generateCloud(final boolean pIsStart) {
		final Cloud cloud = this.createElement();

		cloud.setPosition(pIsStart ? Game.random.nextInt((int) (Options.cameraWidth)) : Options.cameraWidth, Game.random.nextInt((int) (Options.cameraHeight / 3 * 2 - cloud.getHeight())));
		cloud.setSpeedX(mCloudMinSpeed + Game.random.nextFloat() * (mCloudMaxSpeed - mCloudMinSpeed));
		cloud.setCurrentTileIndex(Game.random.nextInt(mCloudsFramesCount));
	}

	/**
	 * 
	 */
	public void update() {
		if (this.getCount() < mCloudsAlwaysCount) {
			this.generateCloud(false);
		}
	}
}
