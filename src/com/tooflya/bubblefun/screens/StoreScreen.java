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
	private final Entity mAnyPurchaseText;

	private final ButtonScaleable mBackButton;

	private final EntityManager<Entity> mStorePanels;
	private final EntityManager<Entity> mCoinsNumbers;

	// ===========================================================
	// Constructors
	// ===========================================================

	public StoreScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass1.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mStorePanels = new EntityManager<Entity>(5, new Entity(Resources.mStorePanelTextureRegion, this.mBackground));

		this.mTopPanel = new Entity(Resources.mShopPanelTextureRegion, this.mBackground);
		this.mCoin = new Entity(Resources.mStaticCoinTextureRegion, this.mBackground);
		this.mAnyPurchaseText = new Entity(Resources.mAnyPurchaseTextTextureRegion, this.mBackground);

		this.mCoinsNumbers = new EntityManager<Entity>(5, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mBackground));

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MENU);
			}
		};

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);
		this.mCoin.create().setPosition(Options.cameraWidth - 130f, 5f);
		this.mAnyPurchaseText.create().setCenterPosition(Options.cameraCenterX, 500f);
		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		for (int i = 0; i < 5; i++) {
			final Entity panel = this.mStorePanels.create();

			panel.setCenterPosition(Options.cameraCenterX, 100 + 70f * i);
			panel.setCurrentTileIndex(i);
		}
		for (int a = 0; a < 4; a++) {
			this.mCoinsNumbers.create().setCenterPosition(this.mCoin.getX() + this.mCoin.getWidth() + 13f + 18f * a, 23f);
		}
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