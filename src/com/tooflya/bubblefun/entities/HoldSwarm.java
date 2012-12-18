package com.tooflya.bubblefun.entities;

import java.util.ArrayList;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class HoldSwarm extends BaseSwarm {

	private float mBlinkSecondsElapsed = 0;

	private int mBlinkPeriod = 3;

	protected static final long[] pFrameDuration = new long[] { 100, 100, 100, 100, 100, 100 };
	protected static final int[] pNormalMoveFrames = new int[] { 1, 2, 3, 2, 1, 0 };

	public HoldSwarm(TiledTextureRegion pTiledTextureRegion, Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mBlinkSecondsElapsed += pSecondsElapsed;

		if (this.mBlinkSecondsElapsed >= this.mBlinkPeriod) {
			this.mBlinkSecondsElapsed = 0;
			this.mBlinkPeriod = Game.random.nextInt(5);

			this.animate(pFrameDuration, pNormalMoveFrames, 0);
		}

	}
}
