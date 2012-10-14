package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen1;

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

	private float time = 0f;
	private float timeStep = 1f;
	private final float normalStepTime = 200f; // TODO: Find correct number.
	private final float doubleStepTime = 50f; // TODO: Find correct number.

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

	private Chiky leftNeighbour = null;
	private Chiky rightNeighbour = null;

	private int type = 0;

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

	public void init(final int type, final float startX, final float startY, final int stepSign) {
		this.type = type;

		this.startX = startX;
		this.startY = startY;

		final float minStepX = 2f; // TODO:
		final float maxStepX = 4f; // TODO:
		if (this.type == 0) {
			this.stepX = (minStepX + maxStepX) / 2;
		}
		else {
			this.stepX = Math.signum(stepSign) * (Game.random.nextFloat() * (maxStepX - minStepX) + minStepX);
		}

		this.x = this.startX;
		this.y = this.startY;
	}

	// ===========================================================
	// Setters
	// ===========================================================

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
		this.x += this.stepX;
		if ((this.type == 2 || this.type == 5) && this.normalStepTime < this.time) {
			this.x += 3 * this.stepX;
		}

		float offsetX = 0;
		if (this.type == 4 || this.type == 5) {
			offsetX = 3 * Options.chikySize;
		}

		if (this.x - this.getWidthScaled() / 2 < -offsetX) {
			this.x = -offsetX + (-offsetX - (this.x - this.getWidthScaled() / 2)) + this.getWidthScaled() / 2;
			this.stepX = +Math.abs(this.stepX);
			if (this.type == 4) {
				this.y = Options.chikySize / 2 + Game.random.nextFloat() * (Options.cameraHeight - Options.touchHeight - Options.chikySize);
			}
		}
		if (this.x + this.getWidthScaled() / 2 > Options.cameraWidth + offsetX) {
			this.x = Options.cameraWidth + offsetX - ((this.x + this.getWidthScaled() / 2) - (Options.cameraWidth + offsetX)) - this.getWidthScaled() / 2;
			this.stepX = -Math.abs(this.stepX);
			if (this.type == 4) {
				this.y = Options.chikySize / 2 + Game.random.nextFloat() * (Options.cameraHeight - Options.touchHeight - Options.chikySize);
			}
		}
		return this.x;
	}

	private float getCalculatedY() {
		if (this.type != 3) {
			return this.y;
		}
		else {
			return this.startY + FloatMath.sin(this.time * Options.PI * Math.abs(this.stepX) / Options.cameraWidth) * Options.ellipseHeight;
		}
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
		if ((this.type == 2 || this.type == 5) && this.time >= this.normalStepTime + this.doubleStepTime) {
			this.time -= this.normalStepTime + this.doubleStepTime;
		}

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
					Bubble airgum = (Bubble) LevelScreen1.airgums.create();
					airgum.setCenterPosition(this.getCenterX() + Game.random.nextInt(50) - 25, this.getCenterY() + Game.random.nextInt(50) - 25); // TODO: Correct.
					airgum.setScale(this.airgumScale);
					airgum.setIsScale(false);
				}

				Particle particle;
				for (int i = 0; i < Options.particlesCount; i++) {
					particle = ((Particle) LevelScreen1.feathers.create());
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
