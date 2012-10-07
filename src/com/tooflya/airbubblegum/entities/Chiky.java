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
	private float timeStep = Options.PI / 180 / 10; // TODO: (R) Find right step.
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

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		
		this.animate(new long[] { 300, 300 }, 0, 1, true);
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
		this.timeToFlyaAway = 40; // TODO: (R) Change later.
		this.airgumScale = airgumScale;
	}

	// ===========================================================
	// Getters
	// ===========================================================

	private float getCalculatedX() {
		return Options.cameraCenterX + FloatMath.sin(this.koefX * this.time + this.offsetTime) * (Options.cameraCenterX - this.getWidth());
	}

	private float getCalculatedY() {
		return Options.cameraCenterY + FloatMath.cos(this.koefY * this.time + this.offsetTime) * (Options.cameraCenterY - this.getHeight() - Options.constHeight) - Options.constHeight;
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
			this.getTextureRegion().setFlippedHorizontal(false);
		}
		else {
			this.getTextureRegion().setFlippedHorizontal(true);
		}

		if (this.isNeedToFlyAway) {
			this.timeToFlyaAway--;
			if (this.timeToFlyaAway < 0) {
				this.isNeedToFlyAway = false;
				Airgum airgum = (Airgum) Game.world.airgums.create();
				airgum.setCenterPosition(this.getCenterX() + Game.random.nextInt(50) - 25, this.getCenterY() + Game.random.nextInt(50) - 25); // TODO: Correct.
				airgum.setScale(this.airgumScale);
				final int particlesCount = 7; // TODO: Correct later. Maybe need to make another function.
				Particle particle;
				for (int i = 0; i < particlesCount; i++) {
					particle = ((Particle) Game.world.feathers.create());
					if (particle != null) {
						particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
					}
				}
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
