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
	private float lastY = 0;

	private int timeToFall = 0;
	private float airgumScale = 1;

	private float fallStepX = 0;
	private float fallStepY = 0;
	private int fallSign = 1;

	private int state = 0; // 0 - fly; 1 - flyWithGum; 2 - fall;

	// Temp
	private float x = 0;
	private float y = 0;

	private float startX = 0;
	private float startY = 0;

	private float stepX = 0;

	private float sizeY = 0;

	private Chiky leftNeighbour = null;
	private Chiky rightNeighbour = null;

	private int type = Game.random.nextInt(3);

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Chiky(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);

		this.animate(new long[] { 300, 300 }, 0, 1, true);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void init() {
		this.startX = Game.random.nextFloat() * (Options.cameraWidth - this.getWidthScaled()) + this.getWidthScaled() / 2; // From this.getWidthScaled() / 2 to Options.cameraWidth - this.getWidthScaled() / 2.
		this.startY = Game.random.nextFloat() * (Options.cameraHeight - this.getHeightScaled() - Options.constHeight) + this.getHeightScaled() / 2; // From this.getHeightScaled() / 2 to Options.cameraHeight - Options.constHeight - this.getHeightScaled() / 2.

		this.stepX = Game.random.nextFloat() * 6f - 3f;

		this.sizeY = Game.random.nextFloat() * Options.cameraHeight / 10;

		this.type = Options.levelNumber % 5; // Game.random.nextInt(5); // TODO: (R) 5 is not work yet. Ellipse. 6 - near and far.

		this.x = this.startX;
		this.y = this.startY;
	}

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
		this.state = 1;
		this.timeToFall = 40; // TODO: (R) Change number later.
		this.airgumScale = airgumScale;

		this.animate(new long[] { 300, 300 }, 2, 3, true);
	}

	// ===========================================================
	// Getters
	// ===========================================================

	private float getCalculatedX() {
		switch (this.type) {
		case 1:
			this.x += this.stepX;
			if (this.x - this.getWidthScaled() / 2 < 0) {
				this.stepX = +Math.abs(this.stepX);
			}
			if (this.x + this.getWidthScaled() / 2 > Options.cameraWidth) {
				this.stepX = -Math.abs(this.stepX);
			}
			return this.x;
		case 2:
			this.x += this.stepX;
			if (this.x + this.getWidthScaled() / 2 < 0) {
				this.x = Options.cameraWidth + this.getWidthScaled() / 2;
			}
			if (this.x - this.getWidthScaled() / 2 > Options.cameraWidth) {
				this.x = -this.getWidthScaled() / 2;
			}
			return this.x;
		case 3:
			this.x += this.stepX;
			if (this.x - this.getWidthScaled() / 2 < 0) {
				this.stepX = +Math.abs(this.stepX);
			}
			if (this.x + this.getWidthScaled() / 2 > Options.cameraWidth) {
				this.stepX = -Math.abs(this.stepX);
			}
			return this.x;
		case 4:
			this.x += this.stepX;
			if (this.x + this.getWidthScaled() / 2 < 0) {
				this.x = Options.cameraWidth + this.getWidthScaled() / 2;
			}
			if (this.x - this.getWidthScaled() / 2 > Options.cameraWidth) {
				this.x = -this.getWidthScaled() / 2;
			}
			return this.x;
		case 5:
			this.x += this.stepX;
			if (this.x - this.getWidthScaled() / 2 < 0) {
				this.stepX = +Math.abs(this.stepX);
			}
			if (this.x + this.getWidthScaled() / 2 > Options.cameraWidth) {
				this.stepX = -Math.abs(this.stepX);
			}
			return this.startX + FloatMath.cos(Options.PI / 180 * this.x) * (Options.cameraWidth - this.getWidthScaled()) / 2;
		}

		return Options.cameraCenterX + FloatMath.sin(this.koefX * this.time + this.offsetTime) * (Options.cameraCenterX - this.getWidth()); // Variant 0.
	}

	private float getCalculatedY() {
		switch (this.type) {
		case 1:
			return this.y;
		case 2:
			return this.y;
		case 3:
			return this.startY + FloatMath.sin(Options.PI / 180 * this.x) * this.sizeY;
		case 4:
			return this.startY + FloatMath.sin(Options.PI / 180 * this.x) * this.sizeY;
		case 5:
			return this.startY + FloatMath.sin(Options.PI / 180 * this.x) * this.sizeY;
		}

		return Options.cameraCenterY + FloatMath.cos(this.koefY * this.time + this.offsetTime) * (Options.cameraCenterY - this.getHeight() - Options.constHeight) - Options.constHeight; // Variant 0.
	}

	public boolean getIsFly() {
		return this.state == 0;
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

		this.time += this.timeStep;

		this.lastX = this.getCenterX();

		switch (this.state) {
		case 0:
			this.setCenterPosition(this.getCalculatedX(), this.getCalculatedY());

			break;
		case 1:
			this.setCenterPosition(this.getCalculatedX(), this.getCalculatedY());

			this.timeToFall--;
			if (this.timeToFall <= 0) {
				if (this.airgumScale > Bubble.minScale) {
					Bubble airgum = (Bubble) Game.world.airgums.create();
					airgum.setCenterPosition(this.getCenterX() + Game.random.nextInt(50) - 25, this.getCenterY() + Game.random.nextInt(50) - 25); // TODO: Correct.
					airgum.setScale(this.airgumScale);
					airgum.setIsScale(false);
				}

				Particle particle;
				for (int i = 0; i < Options.particlesCount; i++) {
					particle = ((Particle) Game.world.feathers.create());
					if (particle != null) {
						particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
					}
				}

				this.state = 2;
				this.fallStepX = 10f; // TODO: (R) Correct magic number.
				this.fallStepY = Game.random.nextFloat() * 5 + 1; // TODO: (R) Correct magic number.
				this.fallSign = 1; // TODO: (R) Some strange code.
				if (Game.random.nextInt(2) != 0) {
					this.fallSign = -1; // TODO: (R) Some strange code.
				}

				this.time = 0; // TODO: (R) Try make a initialize function?
			}
			break;
		case 2:

			this.time -= this.timeStep; // TODO: (R) Some strange code.
			this.time += this.fallStepX; // TODO: (R) Some strange code.
			this.fallStepX = 0.1f;
			this.setCenterPosition(this.getCenterX() + this.fallSign * this.fallStepX * 5, this.getCenterY() + (this.time - this.fallStepY) * (this.time - this.fallStepY) - this.fallStepY * this.fallStepY - this.lastY); // TODO: (R) Some strange code.
			this.lastY = (this.time - this.fallStepY) * (this.time - this.fallStepY) - this.fallStepY * this.fallStepY;

			if (this.time > this.fallStepY) {
				this.setRotation(this.getRotation() + 10);
			}

			if (this.getY() > Options.cameraHeight) {
				this.state = 0;
				this.animate(new long[] { 300, 300 }, 0, 1, true);
				this.setRotation(0);
				this.lastY = 0;
				this.destroy();
			}
			break;
		}

		if (this.getCenterX() - lastX > 0) {
			this.getTextureRegion().setFlippedHorizontal(false);
		}
		else {
			this.getTextureRegion().setFlippedHorizontal(true);
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
