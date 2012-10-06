package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class Chiky extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float time = 0;
	private float timeStep = Options.PI / 180 / 10;
	private float offsetTime = 0;

	private float koefX = 0;
	private float koefY = 0;

	private float lastX = 0;

	private boolean isNeedToFlyAway = false;
	private int timeToFlyaAway = 0;

	private float airgumScale = 1;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Chiky(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);

		this.setScale(0.25f);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Setters
	// ===========================================================

	public void setOffsetTime(final float offsetTime) {
		this.offsetTime = offsetTime;
		this.setCenterPosition(this.getCalculatedX(), this.getCalculatedY());
		this.koefX = Game.random.nextInt(Options.levelNumber) + 1;
		this.koefY = Game.random.nextInt(Options.levelNumber) + 1;
	}

	public void setIsNeedToFlyAway(final float airgumScale) {
		this.isNeedToFlyAway = true;
		this.timeToFlyaAway = 40;
		this.airgumScale = airgumScale;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	private float getCalculatedX() {
		return Options.cameraWidth / 2 + FloatMath.sin(this.koefX * this.time + this.offsetTime) * (Options.cameraWidth - 2 * this.getWidth()) / 2;
	}

	private float getCalculatedY() {
		return Options.cameraHeight / 2 - Options.constHeight + FloatMath.cos(this.koefY * this.time + this.offsetTime) * (Options.cameraHeight - 2 * this.getHeight() - 2 * Options.constHeight) / 2;
	}

	public boolean getIsNeedToFlyAway() {
		return this.isNeedToFlyAway;
	}

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

		this.lastX = this.getCenterX();

		this.time += this.timeStep;

		this.setCenterPosition(this.getCalculatedX(), this.getCalculatedY());

		if (this.getCenterX() - lastX > 0) {
			this.getTextureRegion().setFlippedHorizontal(true);
		}
		else {
			this.getTextureRegion().setFlippedHorizontal(false);
		}

		if (this.isNeedToFlyAway) {
			this.timeToFlyaAway--;
			if (this.timeToFlyaAway < 0) {
				this.isNeedToFlyAway = false;
				Airgum airgum = (Airgum) Game.world.airgums.create();
				airgum.setCenterPosition(this.getCenterX() + Game.random.nextInt(50) - 25, this.getCenterY() + Game.random.nextInt(50) - 25);
				airgum.setScale(this.airgumScale);
				this.destroy();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Chiky(getTextureRegion());
	}
}
