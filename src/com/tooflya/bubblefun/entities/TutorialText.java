package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.font.Font;

public class TutorialText extends Text {

	private float mWaitTime;
	private float mShowTime;

	private boolean isAlreadyShowed;

	public TutorialText(float pX, float pY, Font pFont, String pText) {
		super(pX, pY, pFont, pText);

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.text.ChangeableText#setText(java.lang.String)
	 */
	@Override
	public void setText(final String pText) {
		super.setText(pText);

		this.setAlpha(0f);

		this.isAlreadyShowed = false;
	}

	public void setWaitTime(final float pTime) {
		this.mWaitTime = pTime;
	}

	public void setShowTime(final float pTime) {
		this.mShowTime = pTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mWaitTime -= pSecondsElapsed;

		if (this.mWaitTime <= 0) {
			if (this.mAlpha < 1f && !this.isAlreadyShowed) {
				this.mAlpha += 0.01f;
			} else {
				this.mShowTime -= pSecondsElapsed;

				this.isAlreadyShowed = true;

				if (this.mShowTime <= 0) {
					this.mAlpha -= 0.01f;
				}
			}
		}
	}
}
