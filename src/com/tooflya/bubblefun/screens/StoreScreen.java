package com.tooflya.bubblefun.screens;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class StoreScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mTopPanel;
	private final Entity mCoin;

	private final Entity mStoreBackground;
	private final Entity mStoreBackgroundTop;
	private final Entity mStoreBackgroundDown;
	private final Entity mStoreTrees;

	private final ButtonScaleable mBackButton;
	private final ButtonScaleable mGetCoinsButton;

	private final EntityManager<Entity> mCoinsNumbers;

	// ===========================================================
	// Constructors
	// ===========================================================

	public StoreScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass3.deepCopy(this.mBackground);
		this.mStoreTrees = new Entity(Resources.mStoreTreesTextureRegion, this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mTopPanel = new Entity(Resources.mShopPanelTextureRegion, this.mBackground);
		this.mCoin = new Entity(Resources.mStaticCoinTextureRegion, this.mBackground);

		this.mCoinsNumbers = new EntityManager<Entity>(5, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mBackground));

		this.mGetCoinsButton = new ButtonScaleable(Resources.mGetCoinsButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.setChildScreen(Game.screens.get(Screen.COINS), false, false, true);
			}
		};

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MENU);
			}
		};

		this.mStoreBackground = new Entity(Resources.mStorePanelTextureRegion, this.mBackground);
		this.mStoreBackgroundTop = new Entity(Resources.mStorePanelTopTextureRegion, this.mStoreBackground);
		this.mStoreBackgroundDown = new Entity(Resources.mStorePanelDownTextureRegion, this.mStoreBackground);

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mStoreTrees.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);
		this.mCoin.create().setPosition(Options.cameraWidth - 130f, 5f);
		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);
		this.mGetCoinsButton.create().setPosition(Options.cameraWidth - 5f - this.mGetCoinsButton.getWidth(), Options.cameraHeight - 75f);

		for (int a = 0; a < 4; a++) {
			this.mCoinsNumbers.create().setCenterPosition(this.mCoin.getX() + this.mCoin.getWidth() + 13f + 18f * a, 23f);
		}

		this.mStoreBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mStoreBackgroundTop.create().setCenterPosition(this.mStoreBackground.getWidth() / 2 - 10f, 45f);
		this.mStoreBackgroundDown.create().setCenterPosition(this.mStoreBackground.getWidth() / 2, 361f);
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

	@Override
	public void onAttached() {
		super.onAttached();

		final int Score = 1511;

		if (Score < 10) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex(Score);
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(false);
			this.mCoinsNumbers.getByIndex(2).setVisible(false);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);
		} else if (Score < 100) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 10));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(false);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);
		} else if (Score < 1000) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 100));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			this.mCoinsNumbers.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(true);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);
		} else {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 1000));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 1000) * 1000) / 100));
			this.mCoinsNumbers.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			this.mCoinsNumbers.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(true);
			this.mCoinsNumbers.getByIndex(3).setVisible(true);
		}

		Game.mAdvertisementManager.hideSmall();
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
		Game.screens.set(Screen.MENU);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}