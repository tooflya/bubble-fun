package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class ButtonScaleable extends Button {

	private float mWaitBeforeAction = 0.3f;
	private boolean mDoAction = false;

	private float mBaseScale = -1;

	private boolean mModalTouch;

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

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen, final boolean isModalTouch) {
		super(pTiledTextureRegion, pParentScreen);

		this.mModalTouch = isModalTouch;

		this.registerEntityModifier(modifier1);
		this.registerEntityModifier(modifier2);
		this.registerEntityModifier(modifier3);
	}

	public ButtonScaleable(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		this(pTiledTextureRegion, pParentScreen, false);
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		if (this.mBaseScale == -1) {
			this.mBaseScale = this.getScaleX();

			this.setScaleCenter(this.getBaseWidth() / 2, this.getBaseHeight() / 2);
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
					this.onAnimationStarted();
					this.modifier1.reset();

					this.mDoAction = true;
				}
			}

			isClicked = false;
			return this.mModalTouch;
		case TouchEvent.ACTION_MOVE:
			if (Math.abs(this.mLastClickedX - pTouchAreaLocalX) > 5f || Math.abs(this.mLastClickedY - pTouchAreaLocalY) > 5f) {
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

	public void onAnimationStarted() {
	}

}
