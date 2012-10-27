package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.os.Vibrator;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BlueBird extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static float mSpeed = 2f;

	private final static int mAnimationTime = 50;
	private final static int mParticlesCount = 10;

	// ===========================================================
	// Fields
	// ===========================================================

	private float mSleepTime = 0f;
	private float mSleepTimeOrigin = 300f;

	private boolean mIsSleep;

	private EntityManager mFeathersManager;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 * @param pFeathersManager
	 * @param pParentScreen
	 */
	public BlueBird(final TiledTextureRegion pTiledTextureRegion, final EntityManager pFeathersManager, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.mFeathersManager = pFeathersManager;

		this.animate(mAnimationTime);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 */
	public void particles() {
		((Vibrator) Game.instance.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(10);

		Particle particle;
		for (int i = 0; i < mParticlesCount; i++) {
			particle = ((Particle) mFeathersManager.create());
			if (particle != null) {
				particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
			}
		}
	}

	/**
	 * @return
	 */
	public boolean isSleep() {
		return this.mIsSleep;
	}

	/**
	 * 
	 */
	public void clear() {
		this.mFeathersManager.clear();
	}

	/**
	 * @return
	 */
	private float generateNewHeight() {
		return Game.random.nextInt(Options.cameraHeight / 3 * 2) - this.getHeightScaled();
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#create()
	 */
	@Override
	public Entity create() {
		this.setSpeedX(mSpeed);

		this.mX = Game.random.nextInt(2) == 0 ? 0 - this.getWidthScaled() : Options.cameraWidth + this.getWidthScaled();
		this.mY = this.generateNewHeight();

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mSleepTime <= 0) {
			this.mIsSleep = false;
		} else {
			this.mIsSleep = true;
		}

		if (this.mIsSleep) {
			this.mSleepTime--;
		} else {
			if (this.getTextureRegion().isFlippedHorizontal()) {
				if (this.mX + this.getWidthScaled() < 0) {
					this.getTextureRegion().setFlippedHorizontal(false);
					this.mSleepTime = this.mSleepTimeOrigin;
					this.mY = this.generateNewHeight();
				} else {
					this.mX -= this.getSpeedX();
				}
			} else {
				if (this.mX > Options.cameraWidth) {
					this.getTextureRegion().setFlippedHorizontal(true);
					this.mSleepTime = this.mSleepTimeOrigin;
					this.mY = this.generateNewHeight();
				} else {
					this.mX += this.getSpeedX();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new BlueBird(getTextureRegion(), this.mFeathersManager, this.mParentScreen);
	}
}