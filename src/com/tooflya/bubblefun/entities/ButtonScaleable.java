package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class ButtonScaleable extends Button {

	private ScaleModifier mScaleModifier;

	private float mWaitBeforeAction = 0.3f;
	private boolean mDoAction = false;

	private float mBaseScale = -1;

	private boolean mModalTouch;

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen, final boolean isModalTouch) {
		super(pTiledTextureRegion, pParentScreen);

		this.mModalTouch = isModalTouch;
	}

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, pParentScreen, false);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#create()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		if (this.mBaseScale == -1) {
			this.mBaseScale = this.getScaleX();

			this.mScaleModifier = new ScaleModifier(0.1f, this.getScaleX(), this.getScaleX() + 0.1f);

			this.setScaleCenter(this.getBaseWidth() / 2, this.getBaseHeight() / 2);

			this.registerEntityModifier(this.mScaleModifier);
		}
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

			return this.mModalTouch;

		case TouchEvent.ACTION_UP:
			if (this.isClicked) {
				if (this.mWaitBeforeAction == 0.3f) {
					this.mScaleModifier.reset();

					this.mDoAction = true;
				}
			}

			isClicked = false;
			return this.mModalTouch;
		case TouchEvent.ACTION_MOVE:
			if (Math.abs(this.mLastClickedX - pTouchAreaLocalX) > 10 || Math.abs(this.mLastClickedY - pTouchAreaLocalY) > 10) {
				this.isClicked = false;
			}

			return this.mModalTouch;
		}

		return this.mModalTouch;
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
			this.mWaitBeforeAction -= pSecondsElapsed;
			if (this.mWaitBeforeAction <= 0) {
				this.onClickStandartActions();
				this.mDoAction = false;
				this.mWaitBeforeAction = 0.3f;
				this.setScale(this.mBaseScale);

				this.onClick();
			}
		}
	}

	protected boolean prepare() {
		return true;
	}

	@Override
	public void onClick() {
	}

}
