package com.tooflya.bubblefun.screens;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.ArrayEntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxLockedScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mPanel = new Entity(Resources.mPopupBackgroundTextureRegion, this);

	private final Entity mText = new Entity(Resources.mLockTextTextureRegion, this.mPanel);

	private final ButtonScaleable mOkIcon = new ButtonScaleable(Resources.mOkButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final ArrayEntityManager<Entity> mStarsCountText;

	private int mStarsNeeded;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxLockedScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mText.create();
		this.mText.setScaleCenter(this.mText.getWidth() / 2, this.mText.getHeight() / 2);
		this.mText.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);

		this.mOkIcon.create();
		this.mOkIcon.setScaleCenter(this.mOkIcon.getWidth() / 2, this.mOkIcon.getHeight() / 2);
		this.mOkIcon.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() - 5f);

		this.registerTouchArea(this.mOkIcon);

		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifierOff);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);

		this.mStarsCountText = new ArrayEntityManager<Entity>(3, new Entity(Resources.mPopupStarsNumbersTextureRegion, this.mPanel));

		Entity star;

		star = this.mStarsCountText.create();
		star.setPosition(95f, 86f, true);

		star = this.mStarsCountText.create();
		star.setPosition(125f, 86f, true);

		star = this.mStarsCountText.create();
		star.setPosition(155f, 86f, true);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onAttached() {
		super.onAttached();

		int l = 0;
		switch (BoxScreen.BOX_INDEX) {
		case 1:
			l = 50;
			break;
		case 2:
			l = 100;
			break;
		}

		this.mStarsNeeded = l - Game.mDatabase.getTotalStars();

		if (this.mStarsNeeded < 10) {
			this.mStarsCountText.getByIndex(2).setCurrentTileIndex(this.mStarsNeeded);
			this.mStarsCountText.getByIndex(0).setVisible(false);
			this.mStarsCountText.getByIndex(1).setVisible(false);
			this.mStarsCountText.getByIndex(2).setVisible(true);
		} else if (this.mStarsNeeded < 100) {
			this.mStarsCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(this.mStarsNeeded / 10));
			this.mStarsCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(this.mStarsNeeded % 10));
			this.mStarsCountText.getByIndex(0).setVisible(false);
			this.mStarsCountText.getByIndex(1).setVisible(true);
			this.mStarsCountText.getByIndex(2).setVisible(true);
		} else {
			this.mStarsCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(this.mStarsNeeded / 100));
			this.mStarsCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((this.mStarsNeeded - FloatMath.floor(this.mStarsNeeded / 100) * 100) / 10));
			this.mStarsCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(this.mStarsNeeded % 10));
			this.mStarsCountText.getByIndex(0).setVisible(true);
			this.mStarsCountText.getByIndex(1).setVisible(true);
			this.mStarsCountText.getByIndex(2).setVisible(true);
		}
	}

	@Override
	public void onClose() {
		Game.mScreens.get(Screen.BOX).clearChildScene();
	}

}
