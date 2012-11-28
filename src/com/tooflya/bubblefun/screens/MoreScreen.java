package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MoreScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Sprite mTopPanel;

	private final ButtonScaleable mBackButton;

	private final ButtonScaleable b1;
	private final ButtonScaleable b2;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MoreScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mTopPanel = new Sprite(Resources.mTopPanelTextureRegion, this.mBackground);
		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MENU);
			}
		};

		this.b1 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
			 */
			@Override
			public void onClick() {
			}
		};

		this.b2 = new ButtonScaleable(Resources.mButtonsTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.CREDITS);
			}
		};
		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.b1.create().setCenterPosition(this.mBackground.getWidth() / 2, this.mBackground.getHeight() / 2 - 30f);
		this.b2.create().setCenterPosition(this.mBackground.getWidth() / 2, this.mBackground.getHeight() / 2 + 30f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

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
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.screens.set(Screen.MENU);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}