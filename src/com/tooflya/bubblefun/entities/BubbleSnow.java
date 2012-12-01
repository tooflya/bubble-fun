package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class BubbleSnow extends Bubble {

	public BubbleSnow(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Bubble#create()
	 */
	@Override
	public Entity create() {
		final Entity bubble = super.create();

		this.pScaleStepX = 0;
		this.pScaleStepY = 0;

		this.mScaleX = 1f;
		this.mScaleY = 1f;

		return bubble;

	}

	@Override
	public AnimatedSprite animate(final long pCount, final int pTime) {
		this.destroy();

		return this;
	}

	@Override
	public void initFinishPosition(final float x, final float y) {
		super.initFinishPosition(x, y);

		((LevelScreen) Game.screens.get(Screen.LEVEL)).glints.clear();
	}

	@Override
	protected void onManagedUpdateCreating(final float pSecondsElapsed) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mRotation += 5;
	}

}
