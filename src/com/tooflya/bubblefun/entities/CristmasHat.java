package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

/**
 * @author Tooflya.com
 * @since
 */
public class CristmasHat extends Entity {

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

	public boolean mIsParticle = false;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CristmasHat(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableBlendFunction();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public CristmasHat Init() {
		this.setSpeedX(Game.random.nextFloat() * 2 - 1);
		this.setSpeedY(Game.random.nextFloat() * 2 - 1);

		stepRotation = Game.random.nextFloat() * 10;

		time = 0;
		maxTime = Game.random.nextInt(400);



		this.setAlpha(1);
		stepAlpha = -Game.random.nextFloat() / maxTime;

		maxTime += 130;

		this.mIsParticle = true;

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
		this.setRotation(0);
		this.setAlpha(1);
		
		this.mIsParticle = false;

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

		if (this.mIsParticle) {
			this.mX += this.getSpeedX();
			this.mY += this.getSpeedY();

			if (this.getScaleX() + this.stepScale < 0) {
				this.destroy();
				return;
			}

			this.setRotation(this.getRotation() + this.stepRotation);

			if (this.getAlpha() + this.stepAlpha < 0 && this.getAlpha() + this.stepAlpha > 1) {
				this.destroy();
				return;
			}
			this.setAlpha(this.getAlpha() + this.stepAlpha);

			this.time++;
			if (this.time > this.maxTime) {
				this.destroy();
			}
		}
	}
}
