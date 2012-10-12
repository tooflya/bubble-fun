package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen;

public class LevelIcon extends Entity {

	public int id;

	public boolean blocked = false;

	private Text number;

	private int mAddToScreen;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final int pScreen) {
		super(pTiledTextureRegion, false);

		this.mAddToScreen = pScreen;

		Game.screens.get(pScreen).attachChild(this);
		Game.screens.get(pScreen).registerTouchArea(this);

		this.number = new Text(0, 0, Game.mBigFont, "xx");
		this.getParent().attachChild(this.number);
	}

	public void writeNumber() {
		if (this.blocked) {
			this.number.setVisible(false);
		} else {
			this.number.setVisible(true);
			this.number.setText(this.id + "");
			float x = 0;
			if (this.id < 2) {
				x = 40 * Options.CAMERA_RATIO_FACTOR;
			} else if (this.id < 10) {
				x = 30 * Options.CAMERA_RATIO_FACTOR;
			} else if (this.id < 20) {
				x = 20 * Options.CAMERA_RATIO_FACTOR;
			} else {
				x = 10 * Options.CAMERA_RATIO_FACTOR;
			}
			// this.number.setPosition(x, 5 * Options.CAMERA_RATIO_FACTOR);
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
				LevelScreen.reInit();
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
		return new LevelIcon(getTextureRegion(), mAddToScreen);
	}

}
