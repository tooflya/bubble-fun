package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

public class BaseSwarm extends Entity {

	private float mAnimationSecondsElapsed = 0;
	private int mAnimationsPeriod = 3;

	private boolean mMoveReverse = false;

	private ScaleModifier modifier1 = new ScaleModifier(0.2f, 1f, 0.9f, 1f, 1.1f) {
		@Override
		public void onFinished() {
			modifier2.reset();
		}
	};

	private ScaleModifier modifier2 = new ScaleModifier(0.2f, 0.9f, 1.1f, 1.1f, 0.9f) {
		@Override
		public void onFinished() {
			modifier3.reset();
		}
	};

	private ScaleModifier modifier3 = new ScaleModifier(0.2f, 1.1f, 1f, 0.9f, 1f);

	public BaseSwarm(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.enableFullBlendFunction();

		this.registerEntityModifier(modifier1);
		this.registerEntityModifier(modifier2);
		this.registerEntityModifier(modifier3);
	}

	public void animation() {
		modifier1.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
	 * (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mAnimationSecondsElapsed += pSecondsElapsed;

		if (this.mAnimationSecondsElapsed >= this.mAnimationsPeriod) {
			this.mAnimationSecondsElapsed = 0;
			this.mAnimationsPeriod = Game.random.nextInt(5);

			this.animation();
		}

		if (this.mMoveReverse) {
			this.mX -= 1f;

			if (this.mX <= this.getWidth()) {
				this.mMoveReverse = false;
			}
		} else {
			this.mX += 1f;

			if (this.mX >= Options.cameraWidth + this.getWidth()) {
				this.mMoveReverse = true;
			}
		}
	}
}
