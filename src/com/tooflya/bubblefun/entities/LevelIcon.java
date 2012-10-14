package com.tooflya.bubblefun.entities;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen1;

public class LevelIcon extends Entity {

	public int id;

	public boolean blocked = false;

	private Text number;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final Screen pParentScreen) {
		super(pTiledTextureRegion, pParentScreen, true);

		this.number = new Text(0, 0, Game.mBigFont, "xx");
		this.getParent().attachChild(this.number);
	}

	public void writeNumber() {
		if (this.blocked) {
			this.number.setVisible(false);
		} else {
			this.number.setVisible(true);
			this.number.setText(this.id + "");

			this.number.setPosition(this.getCenterX(), this.getCenterY());
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
		case TouchEvent.ACTION_UP:
			if (!this.blocked) {
				Options.levelNumber = this.id;
				LevelScreen1.reInit();
				Game.screens.set(Screen.LOAD);
			}
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
		return new LevelIcon(getTextureRegion(), this.mParentScreen);
	}

}
