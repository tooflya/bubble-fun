package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class Ufo extends EntityBezier {

	private float mTime;

	public Ufo(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.animate(100);

		this.addControlPoint((short) 10, (short) 50);
		this.addControlPoint((short) 50, (short) 0);
		this.addControlPoint((short) 90, (short) 100);
		this.addControlPoint((short) 90, (short) 50);

		this.initMinTime(0.1f);
		this.initMaxTime(1.1f);
		this.initSpeedTime(0.1f);
		this.initOffsetTime(0.3f);
		this.initIsReverseTime(true);
	}


	public void isCollide(final float pX, final float pY) {
		if (this.mTime <= 0) {
			((LevelScreen) Game.mScreens.get(Screen.LEVEL)).mGreenLasers.create().init(this.getCenterX(), this.getCenterY(), pX, pY);
			this.mTime = 0.2f;

			if (Options.isSoundEnabled) {
				Options.mLaser.play();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mTime -= pSecondsElapsed;
	}
}
