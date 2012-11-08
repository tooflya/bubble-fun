package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class ButtonScaleable extends Button {

	private ScaleModifier mScaleModifier;

	private boolean mModifierAttached = false;

	private int mWaitBeforeAction = 20;
	private boolean mDoAction = false;

	private float mBaseScale = -1;

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen, final boolean isTouchArea) {
		super(pTiledTextureRegion, pParentScreen, false);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#create()
	 */
	@Override
	public Entity create() {
		if (this.mBaseScale == -1) {
			this.mBaseScale = this.getScaleX();

			this.mScaleModifier = new ScaleModifier(0.1f, this.getScaleX(), this.getScaleX() + 0.3f);
			this.mScaleModifier.setRemoveWhenFinished(false);

			this.setScaleCenter(this.getBaseWidth() / 2, this.getBaseHeight() / 2);
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
			this.isClicked = true;
			this.mLastClickedX = pTouchAreaLocalX;
			this.mLastClickedY = pTouchAreaLocalY;
			break;
		case TouchEvent.ACTION_UP:
			if (this.isClicked) {
				if (this.mWaitBeforeAction == 20) {
					if (this.mModifierAttached) {
						this.mScaleModifier.reset();
					} else {
						this.registerEntityModifier(this.mScaleModifier);
						this.mModifierAttached = true;
					}

					this.mDoAction = true;
				}

				isClicked = false;
			}
			break;
		case TouchEvent.ACTION_MOVE:
			if (Math.abs(this.mLastClickedX - pTouchAreaLocalX) > 10 || Math.abs(this.mLastClickedY - pTouchAreaLocalY) > 10) {
				this.isClicked = false;
			}
			break;
		}

		return false;
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
				this.onClickStandartActions();
				this.mDoAction = false;
				this.mWaitBeforeAction = 20;
				this.setScale(this.mBaseScale);

				this.onClick();
			}
		}
	}

	@Override
	public void onClick() {
	}

}
