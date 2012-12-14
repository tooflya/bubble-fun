package com.tooflya.bubblefun.entities;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import android.opengl.GLES10;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class LightingSwarm extends BaseSwarm {

	private float mBlinkSecondsElapsed = 0;
	private float mLightSecondsElapsed = 0;

	private int mBlinkPeriod = 3;

	protected static final long[] pFrameDuration = new long[] { 100, 100, 100, 100, 100, 100 };
	protected static final int[] pNormalMoveFrames = new int[] { 1, 2, 3, 2, 1, 0 };

	private ArrayList<Sprite> mLights = new ArrayList<Sprite>();

	public LightingSwarm(TiledTextureRegion pTiledTextureRegion, Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public void animation() {
		super.animation();

		final LevelScreen screen = (LevelScreen) Game.screens.get(Screen.LEVEL);

		screen.mLightings.clear();
		mLights.clear();

		final int count = Game.random.nextInt(7);
		for (int i = 0; i < count; i++) {
			final Sprite light = screen.mLightings.create();

			if (light != null) {
				light.setRotationCenter(light.getWidth() / 2, -10f);
				light.setPosition(this.getCenterX() - 10f, this.getCenterY() + 10f, true);
				light.setRotation(360f / count * i);
				//light.enableBlendFunction();
				//light.setAlpha(0.5f);

				mLights.add(light);
			}
		}

		this.mLightSecondsElapsed = 0;
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
		this.mLightSecondsElapsed += pSecondsElapsed;

		if (this.mBlinkSecondsElapsed >= this.mBlinkPeriod) {
			this.mBlinkSecondsElapsed = 0;
			this.mBlinkPeriod = Game.random.nextInt(5);

			this.animate(pFrameDuration, pNormalMoveFrames, 0);
		}

		for (Sprite light : mLights) {
			light.setPosition(this.getCenterX() - 10f, this.getCenterY() + 10f, true);
		}

		if (this.mLightSecondsElapsed >= 0.5f) {
			this.mLightSecondsElapsed = 0;

			((LevelScreen) Game.screens.get(Screen.LEVEL)).mLightings.clear();
			mLights.clear();
		}
	}
}
