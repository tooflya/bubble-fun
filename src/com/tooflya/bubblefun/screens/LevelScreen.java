package com.tooflya.bubblefun.screens;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator.LinearGradientDirection;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.RectangleBitmapTextureAtlasSourceDecoratorShape;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.graphics.Color;
import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.LinearGradientFillBitmapTextureAtlasSourceDecorator;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Acceleration;
import com.tooflya.bubblefun.entities.AwesomeText;
import com.tooflya.bubblefun.entities.BlueBird;
import com.tooflya.bubblefun.entities.Bonus;
import com.tooflya.bubblefun.entities.BonusText;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Coin;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Feather;
import com.tooflya.bubblefun.entities.Glint;
import com.tooflya.bubblefun.entities.Gradient;
import com.tooflya.bubblefun.entities.Mark;
import com.tooflya.bubblefun.entities.Parachute;
import com.tooflya.bubblefun.entities.Sprike;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.entities.TutorialText;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.LevelsManager;
import com.tooflya.bubblefun.modifiers.AlphaModifier;
import com.tooflya.bubblefun.modifiers.MoveModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public static boolean isTutorialNeeded = false;

	public static int mBubblesCount;
	public static int AIR;
	public static boolean running;
	public static int deadBirds;
	private static boolean isResetAnimationRunning;
	private boolean mLevelEndRunning;

	public static int Score;

	public final BitmapTextureAtlas mBackgroundGradientTexture = new BitmapTextureAtlas(2, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mBackgroundTextureAtlas3 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mTutorialTextureAtlas = new BitmapTextureAtlas(512, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public final EmptyBitmapTextureAtlasSource bitmap = new EmptyBitmapTextureAtlasSource(2, 512);

	public final LinearGradientFillBitmapTextureAtlasSourceDecorator gradientSource = new LinearGradientFillBitmapTextureAtlasSourceDecorator(bitmap,
			new RectangleBitmapTextureAtlasSourceDecoratorShape(), Color.rgb(0, 139, 69), Color.rgb(84, 255, 159), LinearGradientDirection.BOTTOM_TO_TOP);

	private Gradient mBackground = new Gradient(0, 0, Options.cameraWidth, Options.cameraHeight, BitmapTextureAtlasTextureRegionFactory.createFromSource(mBackgroundGradientTexture, gradientSource, 0, 0), this);

	private final CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(Screen.cloudTextureRegion, this.mBackground));

	public EntityManager<AwesomeText> mAwesomeKillText = new EntityManager<AwesomeText>(20, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "awesome-kill.png", 800, 710, 1,
			1), this.mBackground));
	public EntityManager<AwesomeText> mDoubleKillText = new EntityManager<AwesomeText>(20, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "double-hit.png", 800, 740, 1, 1),
			this.mBackground));
	public EntityManager<AwesomeText> mTripleKillText = new EntityManager<AwesomeText>(20, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "triple-hit.png", 0, 0, 1, 1),
			this.mBackground));
	public EntityManager<BonusText> mBonusesText = new EntityManager<BonusText>(20, new BonusText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "scores_bonuses.png", 0, 30, 1, 4),
			this.mBackground));

	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 1f, 1f, 1f);

	private final TiledTextureRegion mDottedLineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "dash-line.png", 0, 1019, 1, 1);
	private final Sprite mDottedLine = new Sprite(mDottedLineTextureRegion, this.mBackground);
	private final Sprite mDottedLineAir = new Sprite(mDottedLineTextureRegion, this.mBackground);

	private final Rectangle shape = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight) {

		private float s = 0.005f;

		private boolean modifier = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
		 * (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			boolean update = false;
			for (int i = 0; i < this.getChildCount(); i++) {
				if (this.getChild(i).getAlpha() > 0) {
					this.getChild(i).setAlpha(this.getChild(i).getAlpha() - s);
					update = true;
				}
			}

			if (!update && !modifier) {
				modifier = true;
			}
		}

		public void reset() {

			for (int i = 0; i < this.getChildCount(); i++) {
				this.getChild(i).setAlpha(1f);
			}
			modifier = false;
		}
	};

	private final Sprite mLevelWord = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "text-level.png", 0, 980, 1, 1), this.shape);
	private final EntityManager<Sprite> numbers = new EntityManager<Sprite>(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-sprite.png", 385, 0, 1, 11), this.shape));

	private final Sprite mResetText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context,
			"text-restart.png", 430, 0, 1, 1), this.mRectangle);

	private final MoveModifier restartMove1 = new MoveModifier(0.5f, -mResetText.getWidth(), Options.cameraWidth / 8, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onStarted() {
			rectangleAlphaModifierOn.reset();
		}

		@Override
		public void onFinished() {
			restartMove2.reset();
		}
	};

	private final AlphaModifier rectangleAlphaModifierOn = new AlphaModifier(1f, 0f, 0.7f);
	private final AlphaModifier rectangleAlphaModifierOff = new AlphaModifier(1f, 0.7f, 0f);

	private final MoveModifier restartMove2 = new MoveModifier(1f, Options.cameraWidth / 8, Options.cameraWidth / 8 * 2, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onFinished() {
			restartMove3.reset();
		}
	};

	private final MoveModifier restartMove3 = new MoveModifier(0.5f, Options.cameraWidth / 8 * 2, Options.cameraWidth, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onFinished() {
			reInit();
		}
	};

	private final EntityManager<Sprite> thorns = new EntityManager<Sprite>(10, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "wind.png", 430, 40, 1, 1), this.mBackground));
	/*public final EntityManager<Electrod> electrods = new EntityManager<Electrod>(10, new Electrod(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "electrodes.png", 800, 800, 1, 5),
			this.mBackground));*/

	public final EntityManager<Bonus> bonuses = new EntityManager<Bonus>(10, new Bonus(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas3, Game.context, "bonus.png", 0, 0, 6, 1), this.mBackground));

	public final EntityManager<AwesomeText> splashBonusesPicture = new EntityManager<AwesomeText>(10, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "wind.png", 980, 800, 1, 1), this.mBackground));

	public final EntityManager<Acceleration> accelerators = new EntityManager<Acceleration>(10, new Acceleration(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "speed-wind.png", 430, 65,
			6, 1), this.mBackground));

	public EntityManager<Mark> mMarks = new EntityManager<Mark>(100, new Mark(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "wind.png", 800, 700, 1, 1), this.mBackground));

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	public EntityManager<Parachute> parachutes = new EntityManager<Parachute>(5, new Parachute(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "parachute.png", 200, 200, 1, 1), this.mBackground));
	public EntityManager<Chiky> chikies = new EntityManager<Chiky>(30, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "small-bird.png", 430, 100, 6, 4), this.mBackground));
	public EntityManager<Bubble> airgums = new EntityManager<Bubble>(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "gum-animation.png", 430, 280, 1, 6), this.mBackground));
	public EntityManager<Feather> feathers = new EntityManager<Feather>(100, new Feather(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "feather.png", 730, 585, 1, 2), this.mBackground));
	public EntityManager<Glint> glints = new EntityManager<Glint>(100, new Glint(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "blesk.png", 730, 900, 1, 3), this.mBackground));

	public BlueBird mBlueBird = new BlueBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "blue-bird.png", 430, 600, 6, 1),
			new EntityManager<Feather>(100, new Feather(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "feather_new_blue.png", 430, 890, 1, 2), this.mBackground)), this.mBackground);

	private final AlphaModifier mAllFallDownModifier = new AlphaModifier(13.4f, 1f, 0f);
	private final AlphaModifier mAllFallUpModifier = new AlphaModifier(13.4f, 0f, 1f);

	private final AlphaModifier mDotterAirLineOn = new AlphaModifier(1f, 0f, 1f) {
		@Override
		public void onFinished() {
			mDotterAirLineOff.reset();
		}
	};
	private final AlphaModifier mDotterAirLineOff = new AlphaModifier(1f, 1f, 0f) {
		@Override
		public void onFinished() {
			mDotterAirLineOn.reset();
		}
	};

	private Sprite mSpecialButton = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas3, Game.context, "bb-btn.png", 0, 100, 2, 1), this.mBackground);

	public EntityManager<Coin> airs = new EntityManager<Coin>(100, new Coin(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas3, Game.context, "coin-sprite.png", 0, 200, 4, 4), this.mBackground));

	public EntityManager<Sprike> sprikes = new EntityManager<Sprike>(100, new Sprike(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas3, Game.context, "sprike.png", 0, 300, 1, 1), this.mBackground));

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas3, Game.context, "game-panel.png", 630, 900, 1, 1), this.mBackground);

	private final ButtonScaleable mMenuButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "menu-btn.png", 0, 805, 1, 1), this.mBackground) {

		@Override
		public void onClick() {
			Game.screens.setChildScreen(Game.screens.get(Screen.PAUSE), false, true, true);
		}
	};

	private final ButtonScaleable mResetButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "replay-btn.png", 0, 880, 1, 1), this.mBackground) {

		@Override
		public void onClick() {
			if (!isResetAnimationRunning) {
				reInit();
			}
		}
	};

	private final Sprite mScoreText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "score.png", 800, 770, 1, 1), this.mBackground);

	private final EntityManager<Sprite> numbersSmall = new EntityManager<Sprite>(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-small.png", 800, 600, 11, 1), this.mBackground));

	// ===========================================================
	// Tutorial
	// ===========================================================

	public final TiledTextureRegion[] mTutorialTextureRegion = new TiledTextureRegion[10];

	public final ArrayList<TutorialText> mTutorialSprites = new ArrayList<TutorialText>();

	public final Sprite mTutorialAlert = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mTutorialTextureAtlas, Game.context, "tutorial/alert.png", 400, 400, 1, 1), this);
	public final Sprite mTutorialHand = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mTutorialTextureAtlas, Game.context, "tutorial/hand.png", 200, 400, 2, 1), this);

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {

		this.mBackground.setBackgroundCenterPosition();

		// ===========================================================
		// Tutorial

		this.mTutorialTextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mTutorialTextureAtlas, Game.context, "tutorial/tired.png", 0, 0, 1, 1);
		this.mTutorialTextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mTutorialTextureAtlas, Game.context, "tutorial/tap.png", 0, 40, 1, 1);

		this.mTutorialHand.create().animate(new long[] { 500, 2000 }, new int[] { 0, 1 }, 9999);
		this.mTutorialHand.enableBlendFunction();
		this.mTutorialHand.setAlpha(0f);

		// ===========================================================

		this.mPanel.create().setPosition(0, 0);
		this.setOnSceneTouchListener(this);

		this.clouds.generateStartClouds();

		mDottedLine.create().setCenterPosition(Options.cameraWidth - this.mDottedLine.getWidth() / 2, Options.cameraHeight - Options.touchHeight);
		mDottedLineAir.create().setCenterPosition(Options.cameraWidth - this.mDottedLine.getWidth() / 2, Options.cameraHeight - Options.touchHeight);

		this.mDottedLineAir.registerEntityModifier(this.mDotterAirLineOn);
		this.mDottedLineAir.registerEntityModifier(this.mDotterAirLineOff);

		this.mDotterAirLineOff.reset();

		this.attachChild(shape);
		this.shape.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.shape.setAlpha(0f);

		mLevelWord.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX - 30f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 10f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 30f, Options.cameraCenterY);

		for (int i = 0; i < shape.getChildCount(); i++) {
			((Shape) shape.getChild(i)).setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}

		numbers.getByIndex(0).setCurrentTileIndex(1);
		numbers.getByIndex(1).setCurrentTileIndex(10);

		numbersSmall.create().setPosition(97, 8);
		numbersSmall.create().setPosition(111, 8);
		numbersSmall.create().setPosition(125, 8);

		numbersSmall.getByIndex(0).setCurrentTileIndex(0);
		numbersSmall.getByIndex(1).setCurrentTileIndex(0);
		numbersSmall.getByIndex(2).setCurrentTileIndex(0);

		this.mMenuButton.create().setPosition(Options.cameraWidth - (0 + this.mMenuButton.getWidth()), 3f);
		this.mResetButton.create().setPosition(Options.cameraWidth - (5 + this.mMenuButton.getWidth() + this.mResetButton.getWidth()), 3f);

		mResetText.create().setPosition(-mResetText.getWidth(), Options.cameraCenterY - mResetText.getHeight() / 2f);
		mResetText.registerEntityModifier(restartMove1);
		mResetText.registerEntityModifier(restartMove2);
		mResetText.registerEntityModifier(restartMove3);

		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOff);

		mScoreText.create().setPosition(10, 5);

		this.mDottedLine.enableBlendFunction();
		this.mDottedLineAir.enableBlendFunction();
		this.mResetButton.enableBlendFunction();
		this.mMenuButton.enableBlendFunction();
		this.mScoreText.enableBlendFunction();
		this.mPanel.enableBlendFunction();
		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).enableBlendFunction();
		}

		this.mDottedLine.registerEntityModifier(this.mAllFallUpModifier);
		this.mDottedLineAir.registerEntityModifier(this.mAllFallUpModifier);
		this.mResetButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallUpModifier);
		this.mPanel.registerEntityModifier(this.mAllFallUpModifier);
		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallUpModifier);
		}

		this.mDottedLine.registerEntityModifier(this.mAllFallDownModifier);
		this.mDottedLineAir.registerEntityModifier(this.mAllFallDownModifier);
		this.mResetButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallDownModifier);
		this.mPanel.registerEntityModifier(this.mAllFallDownModifier);
		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallDownModifier);
		}

		//this.mSpecialButton.create().setPosition(Options.cameraWidth - 60, Options.cameraHeight - 60);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public void reInit() {
		this.mAllFallUpModifier.reset();

		Score = 0;
		rectangleAlphaModifierOff.reset();

		running = true;
		deadBirds = 0;
		isResetAnimationRunning = false;
		this.mLevelEndRunning = false;

		mAwesomeKillText.clear();
		mDoubleKillText.clear();
		mTripleKillText.clear();
		mBonusesText.clear();
		bonuses.clear();
		parachutes.clear();

		mBlueBird.create();
		mBlueBird.clear();
		airgums.clear();
		chikies.clear();
		glints.clear();
		accelerators.clear();
		thorns.clear();
		//electrods.clear();
		sprikes.clear();
		mMarks.clear();
		airs.clear();

		generateChikies();

		feathers.clear();

		for (TutorialText sprite : mTutorialSprites) {
			if (sprite.getAlpha() > 0)
				sprite.destroy();
		}

		Options.bubbleSizePower = Options.bubbleMaxSizePower;

		AIR = 100;

		mBubblesCount = 0;

		shape.reset();

		numbers.getByIndex(0).setCurrentTileIndex(Options.boxNumber + 1);

		if (Options.levelNumber < 10) {
			mLevelWord.setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY - 40f);
			numbers.getByIndex(3).setVisible(false);

			numbers.getByIndex(2).setCurrentTileIndex(Options.levelNumber);
		} else {
			mLevelWord.setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40f);
			numbers.getByIndex(3).setVisible(true);

			numbers.getByIndex(2).setCurrentTileIndex((int) Math.floor(Options.levelNumber / 10));
			numbers.getByIndex(3).setCurrentTileIndex(Options.levelNumber % 10);
		}

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies() {

		LevelsManager.generateLevel(Options.levelNumber);
	}

	private void checkCollision() {
		Chiky chiky;
		Bubble airgum;
		for (int i = chikies.getCount() - 1; i >= 0; --i) {
			chiky = chikies.getByIndex(i);
			if (chiky.isCanCollide()) {
				for (int j = airgums.getCount() - 1; j >= 0; --j) {
					airgum = (Bubble) airgums.getByIndex(j);
					if (airgum.isCanCollide() && this.isCollide(chiky, airgum)) {
						airgum.addBirdsKills();
						chiky.setCollide(airgum);
						deadBirds++;
					}
				}
			}
		}
		for (int i = airgums.getCount() - 1; i >= 0; --i) {
			airgum = airgums.getByIndex(i);
			if (airgum.isCanCollide()) {
				for (int j = thorns.getCount() - 1; j >= 0; --j) {
					if (this.isCollide(airgum, thorns.getByIndex(j), true)) {
						airgum.isCollide();
					}
				}
				/*for (int j = electrods.getCount() - 1; j >= 0; --j) {
					Electrod electrod = ((Electrod) electrods.getByIndex(j));
					if (electrod.isAnimationRunning() && this.isCollide(airgum, electrod, true)) {
						airgum.isCollide();
					}
				}*/
				for (int j = sprikes.getCount() - 1; j >= 0; --j) {
					final Sprike sprike = sprikes.getByIndex(j);
					if (this.isCollide(airgum, sprike, true)) {
						airgum.isCollide();
					}
				}
				for (int j = bonuses.getCount() - 1; j >= 0; --j) {
					final Bonus bonus = bonuses.getByIndex(j);
					if (this.isCollide(airgum, bonus, true)) {
						final AwesomeText boom = splashBonusesPicture.create();
						boom.setCenterPosition(bonus.getCenterX(), bonus.getCenterY());
						boom.setReverse();

						airgum.isCollide();
						bonus.isCollide();

						this.mSpecialButton.setCurrentTileIndex(1);
					}
				}

				for (int k = 0; k < this.airs.getCount(); k++) {
					final Coin air = this.airs.getByIndex(k);

					if (air.getX() <= (airgum.getX() + airgum.getWidthScaled()) &&
							airgum.getX() <= (air.getX() + air.getWidthScaled()) &&
							air.getY() <= (airgum.getY() + airgum.getHeightScaled()) &&
							airgum.getY() <= (air.getY() + air.getHeightScaled())) {
						air.remove();
					}

					if (!air.mIsAlreadyFollow) {
						if (air.getX() <= (airgum.getX() + airgum.getWidthScaled() * 1.1f) &&
								(airgum.getX() - airgum.getWidthScaled()) <= (air.getX() + air.getWidthScaled()) &&
								air.getY() <= (airgum.getY() + airgum.getHeightScaled() * 1.1f) &&
								airgum.getY() - airgum.getHeightScaled() <= (air.getY() + air.getHeightScaled())) {
							air.follow(airgum);
						}
					}
				}

				if (!mBlueBird.isSleep() && this.isCollide(mBlueBird, airgum)) {
					mBlueBird.particles();
					if (!airgum.isAnimationRunning()) {
						airgum.isCollide();
					}
				}
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < d * d;
	}

	private boolean isCollide(Entity entity1, Entity entity2, final boolean rectangle) {
		return !((entity1.getX() + entity1.getWidth() <= entity2.getX()) ||
				(entity2.getX() + entity2.getWidth() <= entity1.getX()) ||
				(entity2.getY() + entity2.getHeight() <= entity1.getY()) || (entity1.getY() + entity1.getHeight() <= entity2.getY()));
		// TODO: (R) What to do with scaledSize and various rotationCenter?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		if (!Options.mLevelSound.isPlaying() && Options.isMusicEnabled)
			Options.mLevelSound.play();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		Game.screens.get(Screen.LEVEL).clearChildScene();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
	 * (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.checkCollision();

		this.clouds.update();

		/** AIR */
		if (AIR >= 75) {
			this.mDottedLineAir.setColor(0f, 1f, 0f);
		} else if (AIR >= 25) {
			this.mDottedLineAir.setColor(1f, 1f, 0f);
		} else {
			this.mDottedLineAir.setColor(1f, 0f, 0f);
		}

		if (chikies.getCount() == 0 && mAwesomeKillText.getCount() == 0 && mDoubleKillText.getCount() == 0 && mTripleKillText.getCount() == 0 && !this.mLevelEndRunning) {
			Game.screens.setChildScreen(Game.screens.get(Screen.LEVELEND), false, false, true);
			this.mLevelEndRunning = true;

			airgums.clear();
			airs.clear();
			feathers.clear();

			this.lastAirgum = null;

			for (TutorialText sprite : mTutorialSprites) {
				sprite.destroy();
			}

			AIR = 0;
			this.mAllFallDownModifier.reset();
		} else {

			if (AIR <= 0 && running) {
				running = false;
			}

			if (airgums.getCount() <= 0 && deadBirds <= 0 && !running && !isResetAnimationRunning && !this.mLevelEndRunning) {
				restartMove1.reset();
				isResetAnimationRunning = true;
			}
		}

		/* Score */
		if (Score < 10) {
			numbersSmall.getByIndex(0).setCurrentTileIndex(Score);
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(false);
			numbersSmall.getByIndex(2).setVisible(false);
		} else if (Score < 100) {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 10));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(false);
		} else {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 100));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			numbersSmall.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(true);
		}

		/* AIR LINE */
		final float baseWidth = this.mDottedLineAir.getBaseWidth();
		this.mDottedLineAir.setWidth((int) (baseWidth / 100 * AIR));
		this.mDottedLineAir.getTextureRegion().setWidth((int) (baseWidth / 100 * AIR));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundGradientTexture, mBackgroundTextureAtlas, mBackgroundTextureAtlas2, mBackgroundTextureAtlas3, mTutorialTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundGradientTexture, mBackgroundTextureAtlas, mBackgroundTextureAtlas2, mBackgroundTextureAtlas3, mTutorialTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (this.hasChildScene()) {
			if (this.getChildScene() != Game.screens.get(Screen.LEVELEND)) {
				Game.screens.clearChildScreens();
			}
		} else {
			Game.screens.setChildScreen(Game.screens.get(Screen.PAUSE), false, true, true);
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener#onSceneTouchEvent(org.anddev.andengine.entity.scene.Scene, org.anddev.andengine.input.touch.TouchEvent)
	 */
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		final float pTouchX = pTouchEvent.getX() / Options.cameraRatioFactor;
		final float pTouchY = pTouchEvent.getY() / Options.cameraRatioFactor;

		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (AIR > 0 && chikies.getCount() > 0 && this.lastAirgum == null && pTouchY > Options.cameraHeight - Options.touchHeight) {
				this.lastAirgum = (Bubble) airgums.create();
				this.lastAirgum.initStartPosition(pTouchX, pTouchY);
				this.lastAirgum.setScaleCenter(this.lastAirgum.getWidth() / 2, this.lastAirgum.getHeight() / 2);
			}
			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				this.lastAirgum.initFinishPosition(pTouchX, pTouchY);

				this.lastAirgum = null;

				mBubblesCount++;
			}
			break;
		case TouchEvent.ACTION_MOVE:
			if (this.lastAirgum != null) {
				mMarks.clear();

				float xr = pTouchX, yr = pTouchY;

				float mSpeedX, mSpeedY;

				float angle = (float) Math.atan2(yr - this.lastAirgum.getCenterY(), xr - this.lastAirgum.getCenterX());
				float distance = MathUtils.distance(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY(), xr, yr);
				if (distance < Options.eps) {
					mSpeedX = 0;
					mSpeedY = (Options.bubbleMinSpeed + Options.bubbleMaxSpeed) / 2 - this.lastAirgum.mLostedSpeed;
					mSpeedY = mSpeedY < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : mSpeedY;
					mSpeedY = -mSpeedY;
				}
				else {
					distance -= this.lastAirgum.mLostedSpeed;
					distance = distance < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : distance;
					distance = distance > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : distance;

					if (0 < angle) {
						angle -= Options.PI;
					}

					mSpeedX = distance * FloatMath.cos(angle);
					mSpeedY = distance * FloatMath.sin(angle);

					float dx = mSpeedX / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));
					float dy = mSpeedY / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));

					float x = this.lastAirgum.getX() + this.lastAirgum.getWidthScaled() / 2, y = this.lastAirgum.getY() + this.lastAirgum.getHeightScaled() / 2;
					while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
						Entity w = mMarks.create();
						w.setSpeed(dx, dy);
						if (w != null)
							w.setCenterPosition(x, y);

						x += dx * 35;
						y += dy * 35;
					}

					x = this.lastAirgum.getX() + this.lastAirgum.getWidthScaled() / 2;
					y = this.lastAirgum.getY() + this.lastAirgum.getHeightScaled() / 2;
					while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
						Entity w = mMarks.create();
						if (w != null)
							w.setCenterPosition(x, y);
						w.setSpeed(dx, dy);
						x -= dx * 35;
						y -= dy * 35;
					}

				}
			}
			break;
		}

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0f);

		this.attachChild(coloredRect);

		return coloredRect;
	}
}
