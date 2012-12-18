package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class CreditsScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mTopPanel;

	private final ButtonScaleable mBackButton;

	private final Rectangle mBaseRectangle = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight);
	private final Rectangle mFrontRectangle = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight);

	//private final Entity mCredits = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mCommonTextureAtlas, Game.context, "logo_small_zero.png", 0, 0, 1, 1), this.mFrontRectangle);

	// ===========================================================
	// Constructors
	// ===========================================================

	public CreditsScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses2.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		mTopPanel = new Entity(Resources.mTopPanelTextureRegion, this.mBackground);

		mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MORE);
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBaseRectangle.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mFrontRectangle.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.mBaseRectangle.setAlpha(0f);
		this.mFrontRectangle.setAlpha(0f);

		this.attachChild(this.mBaseRectangle);

		this.mBaseRectangle.attachChild(this.mFrontRectangle);

		//this.mCredits.enableBlendFunction();
		//this.mCredits.setAlpha(1f);
		//this.mCredits.create().setCenterPosition(this.mFrontRectangle.getWidth() / 2, this.mFrontRectangle.getHeight() / 2);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		this.clearUpdateHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.screens.set(Screen.MORE);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}