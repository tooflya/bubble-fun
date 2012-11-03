package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

/**
 * @author Tooflya.com
 * @since
 */
public class Particle extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float stepRotation = 0;
	private float stepScale = 0;
	private float stepAlpha = 0;
	private float time = 0;
	private float maxTime = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Particle(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public Particle Init() {

		this.setSpeedX(Game.random.nextFloat() * 2 - 1);
		this.setSpeedY(Game.random.nextFloat() * 2 - 1);

		stepRotation = Game.random.nextFloat() * 10;

		time = 0;
		maxTime = Game.random.nextInt(400);

		final float scale = Game.random.nextFloat() * (1f - 0.6f) + 0.6f;
		this.setScale(scale);
		stepScale = -Game.random.nextFloat() / maxTime;

		this.setAlpha(1);
		stepAlpha = -Game.random.nextFloat() / maxTime;

		maxTime += 30;

		return this;
	}

	// ===========================================================
	// Setters
	// ===========================================================

	// ===========================================================
	// Getters
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public Entity create() {
		this.setCurrentTileIndex(Game.random.nextInt(3));

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.getScaleX() + this.stepScale < 0) {
			this.destroy();
			return;
		}
		this.setScale(this.getScaleX() + this.stepScale);

		this.setRotation(this.getRotation() + this.stepRotation);

		if (this.getAlpha() + this.stepAlpha < 0 && this.getAlpha() + this.stepAlpha > 1) {
			this.destroy();
			return;
		}
		this.setAlpha(this.getAlpha() + this.stepAlpha);

		this.time++;
		if (this.time > this.maxTime) {
			this.destroy();
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Particle(getTextureRegion(), this.mParentScreen);
	}

}
