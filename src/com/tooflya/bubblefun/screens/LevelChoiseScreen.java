package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.ArrayEntityManager;
import com.tooflya.bubblefun.managers.LevelsManager;
import com.tooflya.bubblefun.managers.ScreenManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelChoiseScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int starsCollected;

	// ===========================================================
	// Fields
	// ===========================================================

	private final ButtonScaleable mBackButton;

	private LevelsManager<LevelIcon> levels;
	private ArrayEntityManager<Entity> numbers;

	private final Entity mTopPanel;

	private final ArrayEntityManager<Entity> mSmallnumbers;
	private final ArrayEntityManager<Entity> mStars;

	// ===========================================================
	// Constructors
	// ===========================================================

	int g = -1;

	public LevelChoiseScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass1.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mTopPanel = new Entity(Resources.mTopPanelTextureRegion, this.mBackground);

		this.levels = new LevelsManager<LevelIcon>(25, new LevelIcon(Resources.mLevelsTextureRegion, this.mBackground));
		this.numbers = new ArrayEntityManager<Entity>(100, new Entity(Resources.mNumbersTextureRegion));

		this.mSmallnumbers = new ArrayEntityManager<Entity>(5, new Entity(Resources.mSmallNumbersTextureRegion, this.mBackground));

		this.mStars = new ArrayEntityManager<Entity>(2, new Entity(Resources.mStarsTextureRegion, this.mBackground));

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.mScreens.set(Screen.BOX);
			}
		};

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f - Screen.ADS_PADDING);

		for (int i = 0; i < this.levels.getCapacity(); i++) {
			if (i < 9) {
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
			} else {
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
				this.levels.getByIndex(i).attachChild(this.numbers.getByIndex(++g));
			}
		}

		this.levels.generate(this.numbers);

		for (int i = 1; i >= 0; i--) {
			final Entity Entity = this.mStars.create();

			Entity.setCurrentTileIndex(i);
			Entity.setScale(this.getScaleX() / 2f);
			Entity.setScaleCenter(Entity.getWidthScaled() / 2, Entity.getHeightScaled() / 2);
			Entity.setPosition(Options.cameraWidth - 142f, -10f);
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
		if (Options.isMusicEnabled) {
			if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
				Options.mMainSound.play();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		starsCollected = 0;

		ScreenManager.mChangeAction = 0;

		this.levels.clear();
		this.numbers.clear();
		this.mSmallnumbers.clear();

		this.levels.generate();

		Entity Entity;

		Entity = (Entity) this.mSmallnumbers.create();
		if (starsCollected < 10) {
			Entity.destroy();
			for (int i = 1; i >= 0; i--) {
				this.mStars.getByIndex(i).setPosition(Options.cameraWidth - 130f, 0f);
			}
		} else {
			Entity.setPosition(Options.cameraWidth - 95f, 10f);
			for (int i = 1; i >= 0; i--) {
				this.mStars.getByIndex(i).setPosition(Options.cameraWidth - 142f, 0f);
			}
		}

		Entity.setCurrentTileIndex((int) Math.floor(starsCollected / 10));

		Entity = (Entity) this.mSmallnumbers.create();
		Entity.setPosition(Options.cameraWidth - 80f, 10f);
		Entity.setCurrentTileIndex((int) Math.floor(starsCollected % 10));

		Entity = (Entity) this.mSmallnumbers.create();
		Entity.setPosition(Options.cameraWidth - 65f, 10f);
		Entity.setCurrentTileIndex(10);

		Entity = (Entity) this.mSmallnumbers.create();
		Entity.setPosition(Options.cameraWidth - 50f, 10f);
		Entity.setCurrentTileIndex(7);

		Entity = (Entity) this.mSmallnumbers.create();
		Entity.setPosition(Options.cameraWidth - 35f, 10f);
		Entity.setCurrentTileIndex(5);

		Game.mAdvertisementManager.showSmall();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.mScreens.set(Screen.BOX);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}