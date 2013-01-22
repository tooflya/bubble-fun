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
public class GetCoinsScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mPanel = new Entity(Resources.mPopupBackgroundTextureRegion, this);

	private final Entity mText = new Entity(Resources.mGetMoreCoinsTextTextureRegion, this.mPanel);

	private final Entity mAnyPurchaseText = new Entity(Resources.mAnyPurchaseTextTextureRegion, this.mPanel);

	private final ButtonScaleable mOkIcon = new ButtonScaleable(Resources.mOkButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final ButtonScaleable mBuy1Button = new ButtonScaleable(Resources.mGetCoinsButton1TextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
		}
	};

	private final ButtonScaleable mBuy2Button = new ButtonScaleable(Resources.mGetCoinsButton2TextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
		}
	};

	private final ButtonScaleable mBuy3Button = new ButtonScaleable(Resources.mGetCoinsButton3TextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
		}
	};

	private final ButtonScaleable mBuy4Button = new ButtonScaleable(Resources.mGetCoinsButton4TextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public GetCoinsScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mText.create();
		this.mText.setScaleCenter(this.mText.getWidth() / 2, this.mText.getHeight() / 2);
		this.mText.setCenterPosition(this.mPanel.getWidth() / 2, 50f);

		this.mAnyPurchaseText.create();
		this.mAnyPurchaseText.setScaleCenter(this.mText.getWidth() / 2, this.mText.getHeight() / 2);
		this.mAnyPurchaseText.setCenterPosition(this.mPanel.getWidth() / 2, 100f);

		this.mOkIcon.create();
		this.mOkIcon.setScaleCenter(this.mOkIcon.getWidth() / 2, this.mOkIcon.getHeight() / 2);
		this.mOkIcon.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() - 5f);

		this.mBuy1Button.create();
		this.mBuy1Button.setScaleCenter(this.mBuy1Button.getWidth() / 2, this.mBuy1Button.getHeight() / 2);
		this.mBuy1Button.setCenterPosition(this.mPanel.getWidth() / 2 - 70f, this.mPanel.getHeight() / 2 + 10f);

		this.mBuy2Button.create();
		this.mBuy2Button.setScaleCenter(this.mBuy2Button.getWidth() / 2, this.mBuy2Button.getHeight() / 2);
		this.mBuy2Button.setCenterPosition(this.mPanel.getWidth() / 2 + 70f, this.mPanel.getHeight() / 2 + 10f);

		this.mBuy3Button.create();
		this.mBuy3Button.setScaleCenter(this.mBuy3Button.getWidth() / 2, this.mBuy3Button.getHeight() / 2);
		this.mBuy3Button.setCenterPosition(this.mPanel.getWidth() / 2 - 70f, this.mPanel.getHeight() / 2 + 100f);

		this.mBuy4Button.create();
		this.mBuy4Button.setScaleCenter(this.mBuy4Button.getWidth() / 2, this.mBuy4Button.getHeight() / 2);
		this.mBuy4Button.setCenterPosition(this.mPanel.getWidth() / 2 + 70f, this.mPanel.getHeight() / 2 + 100f);

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

	@Override
	public void onClose() {
		Game.screens.get(Screen.STORE).clearChildScene();
	}

}
