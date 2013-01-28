package com.tooflya.bubblefun.screens;

import org.anddev.andengine.util.user.AsyncTaskLoader;
import org.anddev.andengine.util.user.IAsyncCallback;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class ResetScreen extends PopupScreen implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mIsActionCorfimed;

	private final Entity mPanel = new Entity(Resources.mPopupBackgroundTextureRegion, this);

	private final Entity mText = new Entity(Resources.mResetTextTextureRegion, this.mPanel);
	private final Entity mResetText = new Entity(Resources.mResetHoldTextTextureRegion, this.mPanel);

	private final ButtonScaleable mNIcon = new ButtonScaleable(Resources.mExitNobuttonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final ButtonScaleable mYIcon = new ButtonScaleable(Resources.mExitYesbuttonTextureRegion, this.mPanel) {

		private float mTime;

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			if (this.isClicked) {
				this.mTime += pSecondsElapsed;
			} else {
				this.mTime = 0f;
			}

			if (this.mTime >= 3f) {
				modifier4.reset();

				mIsActionCorfimed = true;
			}
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public ResetScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mText.create();
		this.mText.setScaleCenter(this.mText.getWidth() / 2, this.mText.getHeight() / 2);
		this.mText.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 - 30f);

		this.mResetText.create();
		this.mResetText.setScaleCenter(this.mResetText.getWidth() / 2, this.mResetText.getHeight() / 2);
		this.mResetText.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 + 80f);

		this.mYIcon.create();
		this.mYIcon.setScaleCenter(this.mYIcon.getWidth() / 2, this.mYIcon.getHeight() / 2);
		this.mYIcon.setCenterPosition(70, this.mPanel.getHeight() - 5f);

		this.mNIcon.create();
		this.mNIcon.setScaleCenter(this.mNIcon.getWidth() / 2, this.mNIcon.getHeight() / 2);
		this.mNIcon.setCenterPosition(this.mPanel.getWidth() - 70, this.mPanel.getHeight() - 5f);

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
		Game.mScreens.get(Screen.MORE).clearChildScene();

		if (this.mIsActionCorfimed) {
			this.mIsActionCorfimed = false;

			/** Start background loader */
			new AsyncTaskLoader().execute(ResetScreen.this);
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.util.user.IAsyncCallback#onComplete()
	 */
	@Override
	public void onComplete() {
		Game.mScreens.set(Screen.MENU);
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.util.user.IAsyncCallback#workToDo()
	 */
	@Override
	public void workToDo() {
		Game.mDatabase.onUpgrade(Game.mDatabase.getReadableDatabase(), 1, 1);
		Game.mScreens.createSurfaces();
	}

}
