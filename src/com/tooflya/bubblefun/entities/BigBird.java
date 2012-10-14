package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.managers.EntityManager;

public class BigBird extends Entity {

	private final static float SPEED = 2f;

	private float mSleepTime = 0f;
	public boolean mIsSleep = true;

	public static EntityManager mFeathersManager;

	public BigBird(TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent, final EntityManager pFeathersManager) {
		super(pTiledTextureRegion, false);

		Game.screens.get(Screen.LEVEL).attachChild(this);

		this.animate(500);

		mFeathersManager = pFeathersManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#create()
	 */
	@Override
	public Entity create() {
		this.setSpeedX(SPEED);

		final int i = Game.random.nextInt(2);
		this.mX = i == 0 ? 0 - this.getWidthScaled() : Options.cameraWidth + this.getWidthScaled();
		this.mY = Game.random.nextInt(Options.cameraHeight / 3 * 2) - this.getHeightScaled();

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
					mSleepTime = 300f;
					this.mY = Game.random.nextInt(Options.cameraHeight / 3 * 2);
				} else {
					this.mX -= this.getSpeedX();
				}
			} else {
				if (this.mX > Options.cameraWidth) {
					this.getTextureRegion().setFlippedHorizontal(true);
					mSleepTime = 300f;
					this.mY = Game.random.nextInt(Options.cameraHeight / 3 * 2);
				} else {
					this.mX += this.getSpeedX();
				}
			}
		}
	}

	public void particles() {
		// ((Vibrator) Game.instance.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(10);
		Particle particle;
		for (int i = 0; i < Options.particlesCount; i++) {
			particle = ((Particle) mFeathersManager.create());
			if (particle != null) {
				particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
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
		return new BigBird(getTextureRegion(), false, mFeathersManager);
	}
}
