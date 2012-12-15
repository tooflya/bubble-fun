package com.tooflya.bubblefun.screens;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
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
	private EntityManager<Sprite> numbers;

	private final Sprite mTopPanel;

	private final EntityManager<Sprite> mSmallnumbers;
	private final EntityManager<Sprite> mStars;

	// ===========================================================
	// Constructors
	// ===========================================================
	int g = -1;

	public LevelChoiseScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses3.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mTopPanel = new Sprite(Resources.mTopPanelTextureRegion, this.mBackground);

		this.levels = new LevelsManager<LevelIcon>(25, new LevelIcon(Resources.mLevelsTextureRegion, this.mBackground));
		this.numbers = new EntityManager<Sprite>(100, new Sprite(Resources.mNumbersTextureRegion));

		this.mSmallnumbers = new EntityManager<Sprite>(5, new Sprite(Resources.mSmallNumbersTextureRegion, this.mBackground));

		this.mStars = new EntityManager<Sprite>(2, new Sprite(Resources.mStarsTextureRegion, this.mBackground));

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.BOX);
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

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
			final Sprite sprite = this.mStars.create();

			sprite.setCurrentTileIndex(i);
			sprite.setScale(this.getScaleX() / 2f);
			sprite.setScaleCenter(sprite.getWidthScaled() / 2, sprite.getHeightScaled() / 2);
			sprite.setPosition(Options.cameraWidth - 142f, -10f);
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
		if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
			Options.mMainSound.play();
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

		Sprite sprite;

		sprite = (Sprite) this.mSmallnumbers.create();
		if (starsCollected < 10) {
			sprite.destroy();
			for (int i = 1; i >= 0; i--) {
				this.mStars.getByIndex(i).setPosition(Options.cameraWidth - 130f, 0f);
			}
		} else {
			sprite.setPosition(Options.cameraWidth - 95f, 10f);
			for (int i = 1; i >= 0; i--) {
				this.mStars.getByIndex(i).setPosition(Options.cameraWidth - 142f, 0f);
			}
		}

		sprite.setCurrentTileIndex((int) Math.floor(starsCollected / 10));

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 80f, 10f);
		sprite.setCurrentTileIndex((int) Math.floor(starsCollected % 10));

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 65f, 10f);
		sprite.setCurrentTileIndex(10);

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 50f, 10f);
		sprite.setCurrentTileIndex(7);

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 35f, 10f);
		sprite.setCurrentTileIndex(5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.screens.set(Screen.BOX);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}