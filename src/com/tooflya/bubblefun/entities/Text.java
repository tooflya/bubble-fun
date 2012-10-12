package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.HorizontalAlign;

import com.tooflya.bubblefun.Options;

public class Text extends ChangeableText {

	private float mCenterX;
	private float mCenterY;

	public Text(float pX, float pY, Font pFont, String pText) {
		super(pX, pY, pFont, pText);

		this.mCenterX = pX;
		this.mCenterY = pY;

		// this.setScaleCenter(0, 0);
		// this.setScale(Options.CAMERA_RATIO_FACTOR);

		// this.mX = this.mCenterX - this.getWidthScaled() / 2;
		// this.mY = this.mCenterY - this.getHeightScaled() / 2;
	}

	/**
	 * @see org.anddev.andengine.entity.text.ChangeableText#setText(java.lang.String)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		this.mX = pX - this.getWidthScaled() / 2;
		this.mY = pY - this.getHeightScaled() / 2;
	}
}
