package com.tooflya.bubblefun.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
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
public class LevelChoiseScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int starsCollected;

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "sb.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(Screen.cloudTextureRegion, this.mBackground));

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "back-btn.png", 100, 900, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.BOX);
		}
	};

	private final LevelsManager<LevelIcon> levels = new LevelsManager<LevelIcon>(25, new LevelIcon(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "level-btn.png", 0, 612, 1, 5), this.mBackground));
	private final EntityManager<Sprite> numbers = new EntityManager<Sprite>(100, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-sprite.png", 400, 600, 1, 11)));

	private final Sprite mPinkCloud = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "pink-cloud.png", 800, 800, 1, 1), this.mBackground);

	private final EntityManager<Sprite> mSmallnumbers = new EntityManager<Sprite>(5, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-small.png", 450, 900, 11, 1), this.mBackground));

	private final Sprite mStar = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end_lvl_bg_star.png", 200, 900, 1, 2), this.mBackground);

	// ===========================================================
	// Constructors
	// ===========================================================
	int g = -1;

	public LevelChoiseScreen() {
		this.loadResources();

		this.clouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();

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

		this.mStar.create();
		this.mStar.setPosition(Options.cameraWidth - 145f, 15f);
		this.mStar.setScaleCenter(this.mStar.getWidth() / 2, this.mStar.getHeight() / 2);
		this.mStar.setScale(this.getScaleX() / 1.3f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.clouds.update();
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

		if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
			Options.mMainSound.play();
		}

		Sprite sprite;

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 110f, 20f);
		sprite.setCurrentTileIndex((int) Math.floor(starsCollected / 10));

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 95f, 20f);
		sprite.setCurrentTileIndex((int) Math.floor(starsCollected % 10));

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 80f, 20f);
		sprite.setCurrentTileIndex(10);

		mPinkCloud.create().setCenterPosition(sprite.getCenterX(), sprite.getCenterY());

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 65f, 20f);
		sprite.setCurrentTileIndex(7);

		sprite = (Sprite) this.mSmallnumbers.create();
		sprite.setPosition(Options.cameraWidth - 50f, 20f);
		sprite.setCurrentTileIndex(5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
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