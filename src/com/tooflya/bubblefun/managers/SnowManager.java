package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Snowflake;

/**
 * @author Tooflya.com
 * @since
 */
public class SnowManager<T> extends EntityManager<Snowflake> {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static int mSnowflakesAlwaysCount = 8;

	private final static float mSnowflakesMaxSpeed = 2f;
	private final static float mSnowflakesMinSpeed = 0.2f;

	private final static float mSnowflakesMaxAlpha = 1f;
	private final static float mSnowflakesMinAlpha = 0.4f;

	private final static float mSnowflakesMaxScale = 1.2f;
	private final static float mSnowflakesMinScale = 0.2f;

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
	public SnowManager(int capacity, Snowflake element) {
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
	public void generateStartSnow() {
		for (int i = 0; i < mSnowflakesAlwaysCount; i++) {
			this.generateCloud(true);
		}
	}

	/**
	 * @param pIsStart
	 */
	public void generateCloud(final boolean pIsStart) {
		final Snowflake snowflake = this.create();

		snowflake.setPosition(Game.random.nextInt(Options.cameraWidth), pIsStart ? Game.random.nextInt((int) (Options.cameraHeight / 3 * 2 - snowflake.getHeight())) : -snowflake.getHeight());
		snowflake.setAlpha(mSnowflakesMinAlpha + Game.random.nextFloat() * (mSnowflakesMaxAlpha - mSnowflakesMinAlpha));
		snowflake.setScale(mSnowflakesMinScale + Game.random.nextFloat() * (mSnowflakesMaxScale - mSnowflakesMinScale));
		snowflake.setSpeedX(mSnowflakesMinSpeed + Game.random.nextFloat() * (mSnowflakesMaxSpeed - mSnowflakesMinSpeed));
	}

	/**
	 * 
	 */
	public void update() {
		if (this.getCount() < mSnowflakesAlwaysCount) {
			this.generateCloud(false);
		}
	}
}
