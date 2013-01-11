package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Button;
import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class RateScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mPanel = new Entity(Resources.mRatePanelTextureRegion, this);

	private final Button mLaterIcon = new Button(Resources.mRateLaterButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final Button mNowIcon = new Button(Resources.mRateNowButtonTextureRegion, this.mPanel) {

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

	public RateScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mLaterIcon.create();
		this.mLaterIcon.setScaleCenter(this.mLaterIcon.getWidth() / 2, this.mLaterIcon.getHeight() / 2);
		this.mLaterIcon.setCenterPosition(70, this.mPanel.getHeight() - 5f);

		this.mNowIcon.create();
		this.mNowIcon.setScaleCenter(this.mNowIcon.getWidth() / 2, this.mNowIcon.getHeight() / 2);
		this.mNowIcon.setCenterPosition(this.mPanel.getWidth() - 70, this.mPanel.getHeight() - 5f);

		this.registerTouchArea(this.mLaterIcon);
		this.registerTouchArea(this.mNowIcon);

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
	 * @see com.tooflya.bubblefun.screens.PopupScreen#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		Game.isAlreadyPlayed = false;
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.PopupScreen#onClose()
	 */
	@Override
	public void onClose() {
		Game.screens.get(Screen.MENU).clearChildScene();
	}

}
