package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;

public class PlayIcon extends ButtonScaleable {

	private final ScaleModifier mModifier1 = new ScaleModifier(1.3f, 1f, 0.95f) {
		@Override
		public void onFinished() {
			mModifier2.reset();
		}
	};

	private final ScaleModifier mModifier2 = new ScaleModifier(1.3f, 0.95f, 1.05f) {
		@Override
		public void onFinished() {
			mModifier3.reset();
		}
	};

	private final ScaleModifier mModifier3 = new ScaleModifier(1.3f, 1.05f, 1f) {
		@Override
		public void onFinished() {
			mModifier1.reset();
		}
	};

	private final RotationModifier mModifier4 = new RotationModifier(0.7f, 0f, 360f);

	public PlayIcon(TiledTextureRegion pTiledTextureRegion, Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.registerEntityModifier(this.mModifier1);
		this.registerEntityModifier(this.mModifier2);
		this.registerEntityModifier(this.mModifier3);
		this.registerEntityModifier(this.mModifier4);

		this.mModifier1.reset();
	}

	@Override
	public void onClick() {
		this.registerEntityModifier(this.mModifier1);
		this.registerEntityModifier(this.mModifier2);
		this.registerEntityModifier(this.mModifier3);

		this.mModifier1.reset();
	}

	@Override
	public void onAnimationStarted() {
		this.mModifier1.stop();
		this.mModifier2.stop();
		this.mModifier3.stop();

		this.unregisterEntityModifier(this.mModifier1);
		this.unregisterEntityModifier(this.mModifier2);
		this.unregisterEntityModifier(this.mModifier3);

		this.setScale(1f);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onManagedUpdate(float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (Game.random.nextInt(2000) == 10) {
			if (this.mModifier4.isFinished()) {
				this.mModifier4.reset();
			}
		}
	}
}
