package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class ChikySnow extends Chiky {

	private CristmasHeat heat;

	public ChikySnow(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public Entity create() {
		this.heat = ((LevelScreen) Game.screens.get(Screen.LEVEL)).mCristmasHeats.create();
		
		return super.create();
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if(!this.heat.mIsParticle)
		this.heat.setCenterPosition(this.getCenterX(), this.getCenterY() - 20f);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Chiky#onManagedUpdateWithGum(float)
	 */
	@Override
	protected void onManagedUpdateWithGum(final float pSecondsElapsed) {
		this.mTime = 0;
		if (this.mState == States.SpeedyMove) {
			this.mWind.destroy();
		}
		this.mState = States.Fall;
		this.mStartX = this.getCenterX();
		this.mStartY = this.getCenterY();
		if (this.getCenterX() < Options.cameraWidth / 2) {
			this.setSpeedX(this.mNormalStepX);
		}
		else {
			this.setSpeedX(-this.mNormalStepX);
		}

		for (int i = 0; i < 1; i++) {
			final Bubble airgum = ((LevelScreen) Game.screens.get(Screen.LEVEL)).airgums.create();
			if (airgum != null) {
				airgum.setParent(mAirgum);
				airgum.initStartPosition(this.getCenterX(), this.getCenterY());
				airgum.initFinishPosition(airgum.getCenterX(), airgum.getCenterY());

				final int a = Game.random.nextInt(10);
				airgum.setSpeedX(mAirgum.getSpeedY() * FloatMath.sin(i * a * Options.PI / 4));
				airgum.setSpeedY(mAirgum.getSpeedY() * FloatMath.cos(i * a * Options.PI / 4));

				airgum.setRotation((float) (Math.atan2(this.getSpeedY(), this.getSpeedX()) * 180 / Math.PI));
			}
		}
/**
		Feather particle;
		for (int i = 0; i < Options.particlesCount; i++) {
			particle = ((LevelScreen) Game.screens.get(Screen.LEVEL)).feathers.create();
			if (particle != null) {
				particle.Init().setCenterPosition(this.getCenterX(), this.getCenterY());
			}
		}
**/
		this.heat.Init();
		
		this.stopAnimation(0);
	}
}
