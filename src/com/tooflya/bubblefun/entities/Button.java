package com.tooflya.bubblefun.entities;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public abstract class Button extends Entity {

	protected boolean isClicked = false;
	protected float mLastClickedX, mLastClickedY;

	public Button(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen, true);
	}

	public Button(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen, final boolean isTouchArea) {
		super(pTiledTextureRegion, pParentScreen, false);
	}

	public abstract void onClick();

	protected void onClickStandartActions() {
		if (Options.isSoundEnabled) {
			Options.mButtonSound.play();
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
			//this.setCurrentTileIndex(1);
			this.isClicked = true;
			this.mLastClickedX = pTouchAreaLocalX;
			this.mLastClickedY = pTouchAreaLocalY;
			break;
		case TouchEvent.ACTION_UP:
			if (this.isClicked) {
				//this.setCurrentTileIndex(0);

				this.onClickStandartActions();
				this.onClick();

				isClicked = false;
			}
			break;
		case TouchEvent.ACTION_MOVE:
			if (Math.abs(this.mLastClickedX - pTouchAreaLocalX) > 10 || Math.abs(this.mLastClickedY - pTouchAreaLocalY) > 10) {
				this.isClicked = false;
			}
			break;
		}

		return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	};
}
