package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.primitive.Rectangle;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Text;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class StoreScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int ATTACH_TYPE;

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
	private final ButtonScaleable mPlayButton;

	private final EntityManager<Entity> mCoinsNumbers;
	private final EntityManager<Entity> mMarkers;

	private final Rectangle mNumbersHolder;

	private final ButtonScaleable mBuy1Button, mBuy2Button, mBuy3Button, mBuy4Button;

	// ===========================================================
	// Constructors
	// ===========================================================

	public StoreScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass3.deepCopy(this.mBackground);
		this.mStoreTrees = new Entity(Resources.mStoreTreesTextureRegion, this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mTopPanel = new Entity(Resources.mShopPanelTextureRegion, this.mBackground);
		this.mCoin = new Entity(Resources.mStaticCoinTextureRegion, this.mBackground);

		this.mNumbersHolder = new Rectangle(0, 0, 0, 0);
		this.mBackground.attachChild(this.mNumbersHolder);

		this.mCoinsNumbers = new EntityManager<Entity>(5, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mNumbersHolder));

		this.mPlayButton = new ButtonScaleable(Resources.mPlayButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				((LevelScreen) Game.mScreens.get(Screen.LEVEL)).reInit();
				Game.mScreens.set(Screen.PRELOAD);
			}
		};

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				onBackPressed();
			}
		};

		this.mStoreBackground = new Entity(Resources.mStorePanelTextureRegion, this.mBackground);
		this.mStoreBackgroundTop = new Entity(Resources.mStorePanelTopTextureRegion, this.mBackground);
		this.mStoreBackgroundDown = new Entity(Resources.mStorePanelDownTextureRegion, this.mBackground);

		this.mMarkers = new EntityManager<Entity>(4, new Entity(Resources.mShopMarkersTextureRegion, this.mBackground));

		this.mGetCoinsButton = new ButtonScaleable(Resources.mGetCoinsButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.mScreens.setChildScreen(Game.mScreens.get(Screen.COINS), false, false, true);
			}
		};

		this.mBuy1Button = new ButtonScaleable(Resources.mBuyBonusButton1TextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				if (0 < 100) {
					mGetCoinsButton.onClick();
				}
			}
		};

		this.mBuy2Button = new ButtonScaleable(Resources.mBuyBonusButton2TextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				if (0 < 150) {
					mGetCoinsButton.onClick();
				}
			}
		};

		this.mBuy3Button = new ButtonScaleable(Resources.mBuyBonusButton3TextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				if (0 < 200) {
					mGetCoinsButton.onClick();
				}
			}
		};

		this.mBuy4Button = new ButtonScaleable(Resources.mBuyBonusButton4TextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				if (0 < 250) {
					mGetCoinsButton.onClick();
				}
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mStoreTrees.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);
		this.mCoin.create().setPosition(Options.cameraWidth - 40f, 5f);
		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);
		this.mGetCoinsButton.create().setPosition(Options.cameraWidth - 55f - this.mGetCoinsButton.getWidth(), Options.cameraHeight - 220f);
		this.mPlayButton.create().setPosition(Options.cameraWidth - 5f - this.mGetCoinsButton.getWidth(), Options.cameraHeight - 75f);

		for (int a = 0; a < 4; a++) {
			this.mCoinsNumbers.create().setCenterPosition(13f + 18f * a, 23f);
		}

		this.mStoreBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mStoreBackgroundTop.create().setCenterPosition(Options.cameraCenterX, this.mStoreBackground.getY() - this.mStoreBackgroundTop.getHeight() / 2 + 1);
		this.mStoreBackgroundDown.create().setCenterPosition(Options.cameraCenterX, this.mStoreBackground.getY() + this.mStoreBackground.getHeight());

		new Entity(Resources.mBonus1TextureRegion, this.mBackground).create().setCenterPosition(80f, 160f);
		new Entity(Resources.mBonus2TextureRegion, this.mBackground).create().setCenterPosition(80f, 220f);
		new Entity(Resources.mBonus3TextureRegion, this.mBackground).create().setCenterPosition(80f, 280f);
		new Entity(Resources.mBonus4TextureRegion, this.mBackground).create().setCenterPosition(80f, 340f);

		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 165f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_1"), true));
		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 145f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_2"), true));

		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 105f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_3"), true));
		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 85f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_4"), true));

		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 45f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_5"), true));
		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY - 25f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_6"), true));

		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY + 15f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_7"), true));
		this.attachChild(new Text(Options.screenCenterX - 80f * Options.cameraRatioFactor, Options.screenCenterY + 35f * Options.cameraRatioFactor, Resources.mFont, Game.getString("shop_8"), true));

		this.mMarkers.create().setCenterPosition(this.mStoreBackground.getCenterX(), 190f);
		this.mMarkers.create().setCenterPosition(this.mStoreBackground.getCenterX(), 250f);
		this.mMarkers.create().setCenterPosition(this.mStoreBackground.getCenterX(), 310f);
		this.mMarkers.create().setCenterPosition(this.mStoreBackground.getCenterX(), 370f);

		this.mBuy1Button.create().setCenterPosition(290f, 160f);
		this.mBuy2Button.create().setCenterPosition(290f, 220f);
		this.mBuy3Button.create().setCenterPosition(290f, 280f);
		this.mBuy4Button.create().setCenterPosition(290f, 340f);
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

		final int Score = 0;

		if (Score < 10) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex(Score);
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(false);
			this.mCoinsNumbers.getByIndex(2).setVisible(false);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);

			this.mNumbersHolder.setPosition(this.mCoin.getX() - 30f, 0);
		} else if (Score < 100) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 10));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(false);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);

			this.mNumbersHolder.setPosition(this.mCoin.getX() - 50f, 0);
		} else if (Score < 1000) {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 100));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			this.mCoinsNumbers.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(true);
			this.mCoinsNumbers.getByIndex(3).setVisible(false);

			this.mNumbersHolder.setPosition(this.mCoin.getX() - 70f, 0);
		} else {
			this.mCoinsNumbers.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 1000));
			this.mCoinsNumbers.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 1000) * 1000) / 100));
			this.mCoinsNumbers.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			this.mCoinsNumbers.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			this.mCoinsNumbers.getByIndex(0).setVisible(true);
			this.mCoinsNumbers.getByIndex(1).setVisible(true);
			this.mCoinsNumbers.getByIndex(2).setVisible(true);
			this.mCoinsNumbers.getByIndex(3).setVisible(true);

			this.mNumbersHolder.setPosition(this.mCoin.getX() - 90f, 0);
		}

		Game.mAdvertisementManager.hideSmall();

		switch (ATTACH_TYPE) {
		case 0:
			this.mPlayButton.destroy();
			break;
		case 1:
			this.mPlayButton.create();
			break;
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
		if (this.hasChildScene()) {
			Game.mScreens.clearChildScreens();
		} else {
			switch (ATTACH_TYPE) {
			case 0:
				Game.mScreens.set(Screen.MENU);
				break;
			case 1:
				Game.mScreens.set(Screen.CHOISE);
				break;
			}
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}