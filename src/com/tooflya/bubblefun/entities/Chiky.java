package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
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

	private int type = 0;

	private boolean mAnimationOfSpeed;
	private boolean mAnimationDefiad;

	private Wind wind;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Chiky(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);

		this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 }, 9999);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void init(final int type, final float startX, final float startY, final int stepSign) {
		this.mAnimationOfSpeed = false;
		this.mAnimationDefiad = false;

		this.type = type;

		this.startX = startX;
		this.startY = startY;

		final float minStepX = 1f; // TODO:
		final float maxStepX = 2f; // TODO:
		if (this.type == 0) {
			this.stepX = (minStepX + maxStepX) / 2;
		}
		else {
			this.stepX = Math.signum(stepSign) * (Game.random.nextFloat() * (maxStepX - minStepX) + minStepX);
		}

		this.stepX *= Options.cameraRatioFactor;

		this.x = this.startX;
		this.y = this.startY;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	public void setIsNeedToFlyAway(final float airgumScale) {
		LevelScreen1.deadBirds++;
		this.state = 1;
		this.timeToFall = 100; // TODO: (R) Change number later.
		this.airgumScale = airgumScale;

		if (!this.mAnimationDefiad) {
			if (this.mAnimationOfSpeed) {
				this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 }, 9999);
			} else {
				this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 }, 9999);
			}

			this.mAnimationDefiad = true;
		}
	}

	// ===========================================================
	// Getters
	// ===========================================================

	private float getCalculatedX() {
		this.x += this.stepX;
		if ((this.type == 2 || this.type == 5) && this.normalStepTime < this.time) {
			this.x += 1.3f * this.stepX;

			if (!this.mAnimationOfSpeed) {
				this.wind = ((Wind) ((LevelScreen1) Game.screens.get(Screen.LEVEL)).winds.create());
				this.wind.mFollowEntity = this;
			}

			if (this.mAnimationDefiad) {
				if (!this.mAnimationOfSpeed) {
					this.mAnimationOfSpeed = true;
					this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 18, 19, 20, 21, 22, 23, 22, 21, 20, 19 }, 9999);
				}
			} else {
				if (!this.mAnimationOfSpeed) {
					this.mAnimationOfSpeed = true;
					this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 12, 13, 14, 15, 16, 17, 16, 15, 14, 13 }, 9999);
				}
			}
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
			if (this.mAnimationOfSpeed) {
				if (this.mAnimationDefiad) {
					this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 }, 9999);
				} else {
					this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 }, 9999);
				}
				this.mAnimationOfSpeed = false;
				this.wind.destroy();
				this.wind = null;
			}
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
			this.fallStepX = 0.1f * Options.cameraRatioFactor;
			this.setCenterPosition(this.getCenterX() + this.fallSign * this.fallStepX * 5, this.getCenterY() + (this.time - this.fallStepY) * (this.time - this.fallStepY) - this.fallStepY * this.fallStepY - this.lastY); // TODO: (R) Some strange code.
			this.lastY = (this.time - this.fallStepY) * (this.time - this.fallStepY) - this.fallStepY * this.fallStepY;

			if (this.time > this.fallStepY) {
				this.setRotation(this.getRotation() + 10);
			}

			if (this.getY() > Options.cameraHeight) {
				LevelScreen1.deadBirds--;
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

	@Override
	public void destroy() {
		super.destroy();

		this.state = 0;
		this.animate(new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 }, new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 }, 9999);
		this.setRotation(0);
		this.lastY = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Chiky(getTextureRegion(), this.mParentScreen);
	}
}
