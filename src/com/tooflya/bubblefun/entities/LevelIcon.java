package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;

public class LevelIcon extends Entity {

	public int id;

	public boolean blocked = false;

	private ChangeableText number;

	private int mAddToScreen;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		this.mAddToScreen = pScreen;

		Game.screens.get(pScreen).attachChild(this);
		Game.screens.get(pScreen).registerTouchArea(this);

		this.number = new ChangeableText(0, 0, Game.font, "xx");
		this.attachChild(this.number);
	}

	public void writeNumber() {
		this.number.setText(this.id + "");
		this.number.setPosition(this.getCenterX() - this.number.getWidthScaled() / 2, this.getCenterY() - this.number.getHeightScaled() / 2);
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
			Game.screens.set(Screen.LOAD);
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
