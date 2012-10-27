package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen1;

public class LevelIcon extends Entity {

	public int id;

	public boolean blocked = false;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen, true);
	}

	private ScaleModifier mScaleModifier;

	private boolean mModifierAttached = false;

	private int mWaitBeforeAction = 20;
	private boolean mDoAction = false;

	private float mBaseScale = -1;

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#create()
	 */
	@Override
	public Entity create() {
		if (this.mBaseScale == -1) {
			this.mBaseScale = this.getScaleX();

			this.mScaleModifier = new ScaleModifier(0.1f, this.getScaleX(), this.getScaleX() + 0.3f * Options.cameraRatioFactor);
			this.mScaleModifier.setRemoveWhenFinished(false);

			this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
		}

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
	 */
	@Override
	public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		switch (pAreaTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			break;
		case TouchEvent.ACTION_UP:
			if (this.mWaitBeforeAction == 20) {
				if (this.mModifierAttached) {
					this.mScaleModifier.reset();
				} else {
					this.registerEntityModifier(this.mScaleModifier);
					this.mModifierAttached = true;
				}

				this.mDoAction = true;
			}
			break;
		}

		return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mDoAction) {
			if (this.mWaitBeforeAction-- <= 0) {
				this.mDoAction = false;
				this.mWaitBeforeAction = 20;
				this.setScale(this.mBaseScale);

				if (!this.blocked) {
					Options.levelNumber = this.id;
					LevelScreen1.reInit();
					Game.screens.set(Screen.LOAD);
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
		return new LevelIcon(getTextureRegion(), this.mParentScreen);
	}

}
