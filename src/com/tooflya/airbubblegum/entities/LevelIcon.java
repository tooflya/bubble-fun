package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;

public class LevelIcon extends Entity {

	public int id;

	private int mAddToScreen;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		this.mAddToScreen = pScreen;

		Game.screens.get(pScreen).attachChild(this);
		Game.screens.get(pScreen).registerTouchArea(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
	 */
	@Override
	public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		switch (pAreaTouchEvent.getAction()) {
		case TouchEvent.ACTION_UP:
			Options.levelNumber = this.id;
			Game.world.init();
			Game.screens.set(Screen.LEVEL);
			break;
		}

		return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new LevelIcon(getTextureRegion(), mAddToScreen);
	}

}
