package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class Button extends Sprite {

	public Button(TiledTextureRegion pTiledTextureRegion, Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen, true);
	}

	public abstract void onClick();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
	 */
	@Override
	public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		switch (pAreaTouchEvent.getAction()) {

		case TouchEvent.ACTION_DOWN:
			this.setCurrentTileIndex(1);
			break;
		case TouchEvent.ACTION_UP:
			this.setCurrentTileIndex(0);

			this.onClick();
			break;
		}

		return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	};
}
