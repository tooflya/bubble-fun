package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.os.Vibrator;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.managers.EntityManager;

public class BigBird extends Entity {

	private final static float mSpeed = 2f;

	private float mSleepTime = 0f;
	public boolean mIsSleep = true;

	private EntityManager mFeathersManager;

	public BigBird(TiledTextureRegion pTiledTextureRegion, final boolean pNeedParent, final EntityManager pFeathersManager) {
		super(pTiledTextureRegion, false);

		Game.screens.get(Screen.LEVEL).attachChild(this);

		this.animate(500);

		this.mFeathersManager = pFeathersManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#create()
	 */
	@Override
	public Entity create() {
		final int i = Game.random.nextInt(2);
		this.mX = i * Options.cameraWidth + this.getWidthScaled() * i;
		this.mY = Game.random.nextInt(Options.cameraHeight / 3 * 2);

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
					this.mX -= mSpeed;
				}
			} else {
				if (this.mX > Options.cameraWidth) {
					this.getTextureRegion().setFlippedHorizontal(true);
					mSleepTime = 300f;
					this.mY = Game.random.nextInt(Options.cameraHeight / 3 * 2);
				} else {
					this.mX += mSpeed;
				}
			}
		}
	}

	public void particles() {
		((Vibrator) Game.instance.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(10);
		Particle particle;
		for (int i = 0; i < Options.particlesCount; i++) {
			particle = ((Particle) this.mFeathersManager.create());
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
		return new BigBird(getTextureRegion(), false, this.mFeathersManager);
	}
}
