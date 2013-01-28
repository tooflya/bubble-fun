package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class ExitScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mIsActionConfirmed;

	private final Entity mPanel = new Entity(Resources.mPopupBackgroundTextureRegion, this);

	private final Entity mText = new Entity(Resources.mExitTextTextureRegion, this.mPanel);

	private final ButtonScaleable mYIcon = new ButtonScaleable(Resources.mExitYesbuttonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			ExitScreen.this.mIsActionConfirmed = true;

			modifier4.reset();
		}
	};

	private final ButtonScaleable mNIcon = new ButtonScaleable(Resources.mExitNobuttonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * 
	 */
	public ExitScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mText.create();
		this.mText.setScaleCenter(this.mText.getWidth() / 2, this.mText.getHeight() / 2);
		this.mText.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);

		this.mYIcon.create();
		this.mYIcon.setScaleCenter(this.mYIcon.getWidth() / 2, this.mYIcon.getHeight() / 2);
		this.mYIcon.setCenterPosition(70, this.mPanel.getHeight() - 5f);

		this.mNIcon.create();
		this.mNIcon.setScaleCenter(this.mNIcon.getWidth() / 2, this.mNIcon.getHeight() / 2);
		this.mNIcon.setCenterPosition(this.mPanel.getWidth() - 70, this.mPanel.getHeight() - 5f);

		this.registerTouchArea(this.mYIcon);
		this.registerTouchArea(this.mNIcon);

		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOff);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.PopupScreen#onClose()
	 */
	@Override
	public void onClose() {
		if (this.mIsActionConfirmed) {
			Game.close();
		} else {
			Game.mScreens.get(Screen.MENU).clearChildScene();
		}
	}

}
