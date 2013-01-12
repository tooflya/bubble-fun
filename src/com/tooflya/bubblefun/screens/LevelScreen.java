package com.tooflya.bubblefun.screens;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Acceleration;
import com.tooflya.bubblefun.entities.Airplane;
import com.tooflya.bubblefun.entities.AwesomeText;
import com.tooflya.bubblefun.entities.BlueBird;
import com.tooflya.bubblefun.entities.Bonus;
import com.tooflya.bubblefun.entities.BonusIcon;
import com.tooflya.bubblefun.entities.BonusText;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.BubbleBrokes;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Coin;
import com.tooflya.bubblefun.entities.CristmasHat;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.EntityBezier;
import com.tooflya.bubblefun.entities.Feather;
import com.tooflya.bubblefun.entities.Glass;
import com.tooflya.bubblefun.entities.Glint;
import com.tooflya.bubblefun.entities.HoldSwarm;
import com.tooflya.bubblefun.entities.Laser;
import com.tooflya.bubblefun.entities.LightingSwarm;
import com.tooflya.bubblefun.entities.Mark;
import com.tooflya.bubblefun.entities.Meteorit;
import com.tooflya.bubblefun.entities.SmallMeteorit;
import com.tooflya.bubblefun.entities.Snowflake;
import com.tooflya.bubblefun.entities.Text;
import com.tooflya.bubblefun.entities.TutorialText;
import com.tooflya.bubblefun.entities.Ufo;
import com.tooflya.bubblefun.factories.BubbleFactory;
import com.tooflya.bubblefun.managers.BonusManager;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.LevelsManager;
import com.tooflya.bubblefun.managers.SnowManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public static JSONArray mBirdsNames;

	private final Entity mSpaceBackground;
	private final Entity mSpacePlanet;

	protected static final EntityManager<Cloud> birds = null;

	public static boolean isTutorialNeeded = false;

	public static int mBubblesCount;
	public static int AIR;
	public static int mPicupedCoins;
	public static boolean running;
	public static int deadBirds;
	private static boolean isResetAnimationRunning;
	private boolean mLevelEndRunning;

	public static int Score;

	private Entity mBackground;

	private final CloudsManager<Cloud> mClouds;
	private final SnowManager<Snowflake> mSnowflakes;

	public EntityManager<AwesomeText> mAwesomeKillText;
	public EntityManager<AwesomeText> mDoubleKillText;
	public EntityManager<AwesomeText> mTripleKillText;
	public EntityManager<BonusText> mBonusesText;

	private final Rectangle mRectangle;

	private final Entity mSolidLine;
	private final Entity mSolidLineAir;

	private final Rectangle shape;

	private final Entity mLevelWord;
	private final EntityManager<Entity> numbers;

	private Entity mResetText;

	public final MoveModifier restartMove1;

	private final AlphaModifier rectangleAlphaModifierOn;
	private final AlphaModifier rectangleAlphaModifierOff;

	private final MoveModifier restartMove2;

	private final MoveModifier restartMove3;

	public final EntityManager<Bonus> bonuses;

	public final EntityManager<Acceleration> accelerators;

	public EntityManager<Mark> mMarks;

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	private final Entity mCoin;

	public EntityManager<Coin> coins;

	public EntityManager<Chiky> chikies;
	public EntityManager<Bubble> airgums;
	public EntityManager<Feather> feathers;
	public EntityManager<Glass> glasses;
	public EntityManager<Glint> glints;
	public EntityManager<Laser> mGreenLasers;
	public EntityManager<Laser> mRedLasers;
	public EntityManager<BubbleBrokes> mBubbleBrokes;

	public BlueBird mBlueBird;
	public Airplane mAirplane;

	private final AlphaModifier mAllFallDownModifier;
	private final AlphaModifier mAllFallUpModifier;

	private final AlphaModifier mDotterAirLineOn;
	private final AlphaModifier mDotterAirLineOff;

	private Entity mPanel;

	private final ButtonScaleable mMenuButton;

	private final ButtonScaleable mResetButton;

	private final Entity mScoreText;

	private final EntityManager<Entity> numbersSmall;

	public EntityManager<CristmasHat> mCristmasHats;
	public EntityManager<Entity> mSnowBallSpeed;

	private int mBonusType = 0;

	private final BonusManager mBonusManager;

	// ===========================================================
	// Tutorial
	// ===========================================================

	public final TiledTextureRegion[] mTutorialTextureRegion = new TiledTextureRegion[10];

	public final ArrayList<TutorialText> mTutorialEntitys = new ArrayList<TutorialText>();

	// ===========================================================
	// Constructors
	// ===========================================================

	private boolean asr = false;

	public LevelScreen() {
		this.mBackground = new Entity(Options.cameraWidth, Options.cameraHeight, Resources.mLevelBackgroundGradientTextureRegion, this);

		this.mSpaceBackground = new Entity(Resources.mSpaceStarsBackgroundTextureRegion, this.mBackground);
		this.mSpaceBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mSpaceBackground.enableFullBlendFunction();

		this.mSmallMeteorits = new EntityManager<SmallMeteorit>(10, new SmallMeteorit(Resources.mMeteoritTextureRegion, this.mBackground));

		this.mSpacePlanet = new Entity(Resources.mSpacePlanetTextureRegion, this.mBackground);
		this.mSpacePlanet.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mSpacePlanet.enableFullBlendFunction();

		this.mMeteorits = new EntityManager<Meteorit>(10, new Meteorit(Resources.mMeteoritTextureRegion, this.mBackground));

		this.mSnowflakes = new SnowManager<Snowflake>(100, new Snowflake(Resources.mSnowFlakesTextureRegion, this.mBackground));
		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mAwesomeKillText = new EntityManager<AwesomeText>(220, new AwesomeText(Resources.mAwesomeText1TextureRegion, this.mBackground));
		this.mDoubleKillText = new EntityManager<AwesomeText>(220, new AwesomeText(Resources.mAwesomeText2TextureRegion, this.mBackground));
		this.mTripleKillText = new EntityManager<AwesomeText>(220, new AwesomeText(Resources.mAwesomeText3TextureRegion, this.mBackground));
		this.mBonusesText = new EntityManager<BonusText>(220, new BonusText(Resources.mScoreBonusesTextTextureRegion, this.mBackground));

		this.mRectangle = this.makeColoredRectangle(0, 0, 1f, 1f, 1f);
		this.mSolidLine = new Entity(Resources.mAirRegularOneTextureRegion, this.mBackground);
		this.mSolidLineAir = new Entity(Resources.mAirTwoTextureRegion, this.mBackground);

		shape = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight) {

			private float s = 0.035f;
			private float l = 1f;

			private boolean modifier = false;

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.anddev.andengine.entity.Entity.AnimatedEntity#onManagedUpdate (float)
			 */
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);

				l -= pSecondsElapsed;

				if (l <= 0) {
					boolean update = false;
					for (int i = 0; i < this.getChildCount(); i++) {
						if (this.getChild(i).getAlpha() > 0) {
							this.getChild(i).setAlpha(this.getChild(i).getAlpha() - s);
							update = true;
						} else {
							this.getChild(i).setVisible(false);
						}
					}

					if (!update && !modifier) {
						modifier = true;
					}
				}
			}

			public void reset() {

				for (int i = 0; i < this.getChildCount(); i++) {
					this.getChild(i).setVisible(true);
					this.getChild(i).setAlpha(1f);
				}
				modifier = false;
				l = 1f;
			}
		};

		mLevelWord = new Entity(Resources.mLevelWordTextureRegion, this.shape);
		numbers = new EntityManager<Entity>(4, new Entity(Resources.mNumbersTextureRegion, this.shape));

		mResetText = new Entity(Resources.mRestartTextTextureRegion, this.mRectangle);

		bonuses = new EntityManager<Bonus>(10, new Bonus(Resources.mBonusTextureRegion, this.mBackground));

		accelerators = new EntityManager<Acceleration>(50, new Acceleration(Resources.mAcceleratorsTextureRegion, this.mBackground));

		mMarks = new EntityManager<Mark>(200, new Mark(Resources.mMarkTextureRegion, this.mBackground));

		coins = new EntityManager<Coin>(100, new Coin(Resources.mCoinsTextureRegion, this.mBackground));

		airgums = new EntityManager<Bubble>(300, new Bubble(Resources.mBubbleTextureRegion, this.mBackground));
		feathers = new EntityManager<Feather>(200, new Feather(Resources.mFeathersTextureRegion, this.mBackground));
		glasses = new EntityManager<Glass>(200, new Glass(Resources.mGlassesTextureRegion, this.mBackground));
		glints = new EntityManager<Glint>(200, new Glint(Resources.mGlintsTextureRegion, this.mBackground));
		this.chikies = new EntityManager<Chiky>(200, new Chiky(Resources.mRegularBirdsTextureRegion, this.mBackground));
		mCristmasHats = new EntityManager<CristmasHat>(100, new CristmasHat(Resources.mSnowyBirdsHatTextureRegion, this.mBackground));

		this.mBubbleBrokes = new EntityManager<BubbleBrokes>(100, new BubbleBrokes(Resources.mAsteroidBrokenTextureRegion, this.mBackground));

		mSnowBallSpeed = new EntityManager<Entity>(100, new Entity(Resources.mSpaceBallSpeedTextureRegion, this.mBackground));

		mBlueBird = new BlueBird(Resources.mBlueBirdTextureRegion, new EntityManager<Feather>(100, new Feather(Resources.mBlueFeathersTextureRegion, this.mBackground)), this.mBackground);
		mAirplane = new Airplane(Resources.mAirplaneTextureRegion, this.mBackground);

		mPanel = new Entity(Resources.mTopGamePanelTextureRegion, this.mBackground);

		mScoreText = new Entity(Resources.mScoreTextTextureRegion, this.mBackground);

		numbersSmall = new EntityManager<Entity>(4, new Entity(Resources.mSmallNumbersTextureRegion, this.mBackground));

		mMenuButton = new ButtonScaleable(Resources.mMenuButtonTextureRegion, this.mBackground) {

			@Override
			public void onClick() {
				Game.screens.setChildScreen(Game.screens.get(Screen.PAUSE), false, true, true);
			}
		};

		mResetButton = new ButtonScaleable(Resources.mRestartButtonTextureRegion, this.mBackground) {

			@Override
			public void onClick() {
				if (!isResetAnimationRunning) {
					reInit();
				}
			}
		};

		restartMove1 = new MoveModifier(0.5f, -mResetText.getWidth(), Options.cameraWidth / 8, Options.cameraCenterY, Options.cameraCenterY) {

			@Override
			public void onStarted() {
				rectangleAlphaModifierOn.reset();
			}

			@Override
			public void onFinished() {
				restartMove2.reset();
			}
		};

		rectangleAlphaModifierOn = new AlphaModifier(1f, 0f, 0.7f);
		rectangleAlphaModifierOff = new AlphaModifier(1f, 0.7f, 0f);

		restartMove2 = new MoveModifier(1f, Options.cameraWidth / 8, Options.cameraWidth / 8 * 2, Options.cameraCenterY, Options.cameraCenterY) {
			@Override
			public void onFinished() {
				restartMove3.reset();
			}
		};

		restartMove3 = new MoveModifier(0.5f, Options.cameraWidth / 8 * 2, Options.cameraWidth, Options.cameraCenterY,
				Options.cameraCenterY) {
			@Override
			public void onFinished() {
				reInit();
			}
		};

		mAllFallDownModifier = new AlphaModifier(13.4f, 1f, 0f);
		mAllFallUpModifier = new AlphaModifier(13.4f, 0f, 1f);

		mDotterAirLineOn = new AlphaModifier(1f, 0f, 1f) {
			@Override
			public void onFinished() {
				mDotterAirLineOff.reset();
			}
		};
		mDotterAirLineOff = new AlphaModifier(1f, 1f, 0f) {
			@Override
			public void onFinished() {
				mDotterAirLineOn.reset();
			}
		};

		this.mCoin = new Entity(Resources.mStaticCoinTextureRegion, this.mBackground);

		this.mBackground.setBackgroundCenterPosition();

		this.mCoin.create().setPosition(10f, Options.cameraHeight - 40f);

		this.mPanel.create().setPosition(0, 0);

		this.setOnSceneTouchListener(this);

		mSolidLine.create().setPosition(0, Options.cameraHeight - Options.touchHeight);
		mSolidLineAir.create().setPosition(0, Options.cameraHeight - Options.touchHeight);
		mSolidLine.enableFullBlendFunction();

		this.attachChild(shape);
		this.shape.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.shape.setAlpha(0f);

		this.mLevelWord.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX - 38f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX + 18f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX + 43f, Options.cameraCenterY);

		this.numbersSmall.create().setPosition(97, 8);
		this.numbersSmall.create().setPosition(111, 8);
		this.numbersSmall.create().setPosition(125, 8);
		this.numbersSmall.create().setPosition(138, 8);

		this.numbersSmall.getByIndex(0).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(1).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(2).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(3).setCurrentTileIndex(0);

		this.mMenuButton.create().setPosition(Options.cameraWidth - (0 + this.mMenuButton.getWidth()), 3f);
		this.mResetButton.create().setPosition(Options.cameraWidth - (5 + this.mMenuButton.getWidth() + this.mResetButton.getWidth()), 3f);

		this.mResetText.create().setPosition(-mResetText.getWidth(), Options.cameraCenterY - mResetText.getHeight() / 2f);
		this.mResetText.registerEntityModifier(restartMove1);
		this.mResetText.registerEntityModifier(restartMove2);
		this.mResetText.registerEntityModifier(restartMove3);

		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOff);

		mScoreText.create().setPosition(10, 5);

		this.mSolidLine.enableBlendFunction();
		this.mSolidLineAir.enableBlendFunction();
		this.mResetButton.enableBlendFunction();
		this.mMenuButton.enableBlendFunction();
		this.mScoreText.enableBlendFunction();
		this.mPanel.enableBlendFunction();

		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).enableBlendFunction();
		}

		this.mSolidLine.registerEntityModifier(this.mAllFallUpModifier);
		this.mSolidLineAir.registerEntityModifier(this.mAllFallUpModifier);
		this.mResetButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallUpModifier);
		this.mPanel.registerEntityModifier(this.mAllFallUpModifier);
		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallUpModifier);
		}

		this.mSolidLine.registerEntityModifier(this.mAllFallDownModifier);
		this.mSolidLineAir.registerEntityModifier(this.mAllFallDownModifier);
		this.mResetButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallDownModifier);
		this.mPanel.registerEntityModifier(this.mAllFallDownModifier);

		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallDownModifier);
		}

		this.mLightings = new EntityManager<Entity>(10, new Entity(Resources.mLighingTextureRegion, this.mBackground));

		this.mLightingSwarms = new EntityManager<LightingSwarm>(3, new LightingSwarm(Resources.mAngryCloudTextureRegion, this.mBackground));

		this.mHoldSwarms = new EntityManager<HoldSwarm>(3, new HoldSwarm(Resources.mHoldCloudTextureRegion, this.mBackground));

		this.mGreenLasers = new EntityManager<Laser>(100, new Laser(Resources.mGreenLaserTextureRegion, this.mBackground));
		this.mRedLasers = new EntityManager<Laser>(100, new Laser(Resources.mRedLaserTextureRegion, this.mBackground));

		this.mUfos = new EntityManager<Ufo>(10, new Ufo(Resources.mUfoTextureRegion, this.mBackground));
		//this.mUfos.create().setCenterPosition(150, 150);

		for (int i = 0; i < shape.getChildCount(); i++) {
			((Entity) shape.getChild(i)).enableBlendFunction();
		}

		this.mBonusManager = new BonusManager(10);
		for (int i = 0; i < 4; i++) {
			this.mBonusManager.add(new BonusIcon(Resources.mBonus1TextureRegion, this.mBackground) {
				@Override
				public void onClick() {
					this.init(31);
					super.onClick();
				}
			});
		}
		
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 300f, Resources.mTutorialFont, "Hi! This is fucking tutorial! (;"));
	}

	private final EntityManager<LightingSwarm> mLightingSwarms;
	private final EntityManager<HoldSwarm> mHoldSwarms;
	public final EntityManager<Entity> mLightings;

	public final EntityManager<Ufo> mUfos;

	public final EntityManager<Meteorit> mMeteorits;
	public final EntityManager<SmallMeteorit> mSmallMeteorits;

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public void reInit() {
		running = true;

		this.mAllFallUpModifier.reset();

		Score = 0;
		mPicupedCoins = 0;
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

		mBlueBird.create();
		mBlueBird.clear();
		airgums.clear();
		chikies.clear();
		this.mCristmasHats.clear();
		glints.clear();
		accelerators.clear();
		mMarks.clear();
		coins.clear();
		glasses.clear();

		this.mLightingSwarms.clear();
		this.mHoldSwarms.clear();

		this.mBonusType = 0;

		// this.mLightingSwarms.create().setCenterPosition(150, 150);
		// this.mHoldSwarms.create().setCenterPosition(350, 350);

		this.mMeteorits.clear();
		this.mSmallMeteorits.clear();
		this.mUfos.clear();

		generateChikies();

		feathers.clear();

		for (TutorialText Entity : mTutorialEntitys) {
			if (Entity.getAlpha() > 0)
				Entity.destroy();
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

		numbers.getByIndex(1).setCurrentTileIndex(10);

		EntityBezier.mKoefSpeedTime = 1;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies() {
		LevelsManager.generateLevel(Options.levelNumber);

		try {
			for (int i = 0; i < this.chikies.getCount(); i++) {
				final Chiky chiky = this.chikies.getByIndex(i);

				try {
					chiky.initName(mBirdsNames.optJSONArray(Options.levelNumber - 1).getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
		} catch (NullPointerException ex) {
		}
	}

	private void checkCollision() {
		Chiky chiky;
		Bubble airgum;

		for (int k = 0; k < this.mRedLasers.getCount(); k++) {
			final Laser laser = this.mRedLasers.getByIndex(k);

			for (int h = chikies.getCount() - 1; h >= 0; --h) {
				chiky = chikies.getByIndex(h);
				if (chiky.isCanCollide()) {

					if (this.isCollide(chiky, laser)) {
						chiky.collide();
						deadBirds++;
					}
				}
			}
		}

		for (int i = airgums.getCount() - 1; i >= 0; --i) {
			airgum = airgums.getByIndex(i);
			if (airgum.isCanCollide()) {

				for (int h = chikies.getCount() - 1; h >= 0; --h) {
					chiky = chikies.getByIndex(h);
					if (chiky.isCanCollide()) {
						if (this.isCollide(chiky, airgum)) {
							airgum.addBirdsKills();
							chiky.collide(airgum);
							deadBirds++;
						}
					}
				}

				for (int j = bonuses.getCount() - 1; j >= 0; --j) {
					final Bonus bonus = bonuses.getByIndex(j);
					if (this.isCollide(airgum, bonus, true)) {
						airgum.collide();
						bonus.collide();
					}
				}

				for (int k = 0; k < this.coins.getCount(); k++) {
					final Coin air = this.coins.getByIndex(k);

					if (this.isCollide(airgum, air)) {
						air.remove();
					}

					/*
					 * if (!air.mIsAlreadyFollow) { if (air.getX() <= (airgum.getX() + airgum.getWidthScaled() * 1.1f) && (airgum.getX() - airgum.getWidthScaled()) <= (air.getX() + air.getWidthScaled()) && air.getY() <= (airgum.getY() + airgum.getHeightScaled() * 1.1f) && airgum.getY() - airgum.getHeightScaled() <= (air.getY() + air.getHeightScaled())) { air.follow(airgum); } }
					 */
				}

				for (int k = 0; k < this.mLightingSwarms.getCount(); k++) {
					final LightingSwarm swarm = this.mLightingSwarms.getByIndex(k);

					if (swarm.getX() <= (airgum.getX() + airgum.getWidth() * 1.1f) &&
							(airgum.getX() - airgum.getWidth()) <= (swarm.getX() + swarm.getWidthScaled()) &&
							swarm.getY() <= (airgum.getY() + airgum.getHeight() * 1.1f) &&
							airgum.getY() - airgum.getHeight() <= (swarm.getY() + swarm.getHeightScaled())) {
						airgum.collide();
					}
				}

				for (int k = 0; k < this.mUfos.getCount(); k++) {
					final Ufo ufo = this.mUfos.getByIndex(k);

					if (this.isCollide(airgum, ufo, 5f)) {
						ufo.isCollide(airgum.getCenterX(), airgum.getCenterY());
					}
				}

				for (int k = 0; k < this.mGreenLasers.getCount(); k++) {
					final Laser laser = this.mGreenLasers.getByIndex(k);

					if (this.isCollide(airgum, laser)) {
						airgum.collide();

						if (Options.isMusicEnabled) {
							Options.mAsteroidDeath.play();
						}
					}
				}

				if (!mBlueBird.isSleep() && this.isCollide(mBlueBird, airgum)) {
					mBlueBird.particles();
					if (!airgum.isAnimationRunning()) {
						airgum.collide();
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

	private boolean isCollide(Entity entity1, Entity entity2, final float pRadius) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < (d * d) * pRadius;
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

		Game.isAlreadyPlayed = true;

		switch (Options.boxNumber) {
		case 0:
			this.onWOCBoxAttached();
			break;
		case 1:
			this.onSDBoxAttached();
			break;
		case 2:
			this.onSTBoxAttached();
			break;
		}

		this.reInit();

		if (Options.isMusicEnabled) {
			if (!Options.mLevelSound.isPlaying()) {
				Options.mLevelSound.play();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
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

		Game.screens.get(Screen.LEVEL).clearChildScene();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity.AnimatedEntity#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mBonusManager.update();

		switch (Options.boxNumber) {
		case 0:
			this.onManagedUpdateWOCBox();
			break;
		case 1:
			this.onManagedUpdateSDBox();
			break;
		case 2:
			this.onManagedUpdateSTBox();
			break;
		}

		this.checkCollision();

		if (chikies.getCount() <= 0 && !this.mLevelEndRunning) {
			Game.screens.setChildScreen(Game.screens.get(Screen.LEVELEND), false, false, true);
			this.mLevelEndRunning = true;

			airgums.clear();
			coins.clear();
			feathers.clear();

			this.mLightings.clear();
			this.mLightingSwarms.clear();
			this.mHoldSwarms.clear();

			this.lastAirgum = null;

			for (TutorialText Entity : mTutorialEntitys) {
				Entity.destroy();
			}

			AIR = 0;
			this.mAllFallDownModifier.reset();

			running = false;
		} else {

			if (airgums.getCount() <= 0 && deadBirds <= 0 && AIR <= 0
					&& !isResetAnimationRunning && !this.mLevelEndRunning) {
				restartMove1.reset();
				isResetAnimationRunning = true;

				this.mLightings.clear();
				this.mLightingSwarms.clear();
				this.mHoldSwarms.clear();

				running = false;
			}
		}

		/* Score */
		if (Score < 10) {
			numbersSmall.getByIndex(0).setCurrentTileIndex(Score);
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(false);
			numbersSmall.getByIndex(2).setVisible(false);
			numbersSmall.getByIndex(3).setVisible(false);
		} else if (Score < 100) {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 10));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(false);
			numbersSmall.getByIndex(3).setVisible(false);
		} else if (Score < 1000) {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 100));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			numbersSmall.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(true);
			numbersSmall.getByIndex(3).setVisible(false);
		} else {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 1000));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 1000) * 1000) / 100));
			numbersSmall.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			numbersSmall.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(true);
			numbersSmall.getByIndex(3).setVisible(true);
		}

		/* AIR LINE */
		final float baseWidth = this.mSolidLineAir.getBaseWidth();
		this.mSolidLineAir.setWidth((int) (baseWidth / 100 * AIR));
		this.mSolidLineAir.getTextureRegion().setWidth((int) (baseWidth / 100 * AIR));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener#onSceneTouchEvent(org.anddev.andengine.entity.scene.Scene, org.anddev.andengine.input.touch.TouchEvent)
	 */
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		final float pTouchX = pTouchEvent.getX() / Options.cameraRatioFactor;
		final float pTouchY = pTouchEvent.getY() / Options.cameraRatioFactor;

		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (this.mBonusType == 0) {
				if (AIR > 0 && chikies.getCount() > 0 && this.lastAirgum == null && pTouchY > Options.cameraHeight - Options.touchHeight) {
					this.lastAirgum = (Bubble) airgums.create();
					this.lastAirgum.initStartPosition(pTouchX, pTouchY);
				}
			}
			else {
				BubbleFactory.BubbleBonus(pTouchX, pTouchY, this.mBonusType);
				this.mBonusType = 0;
			}
			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				this.lastAirgum.initFinishPositionWithCorrection(pTouchX, pTouchY);

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

					float x = this.lastAirgum.getCenterX(), y = this.lastAirgum.getCenterY();
					while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
						Entity w = mMarks.create();
						w.setSpeed(dx, dy);
						if (w != null)
							w.setCenterPosition(x, y);

						x += dx * 35;
						y += dy * 35;
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

	private void onWOCBoxAttached() {
		this.mPanel.changeTextureRegion(Resources.mTopGamePanelTextureRegion);
		this.mLevelWord.changeTextureRegion(Resources.mLevelWordTextureRegion);
		this.numbers.changeTextureRegion(Resources.mSpecialNumbers1TextureRegion);

		this.mClouds.clear();
		this.mClouds.generateStartClouds();

		this.airgums.clear();
		this.airgums.changeTextureRegion(Resources.mBubbleTextureRegion);

		this.chikies.clear();
		this.chikies.changeTextureRegion(Resources.mRegularBirdsTextureRegion);

		this.mSolidLine.changeTextureRegion(Resources.mAirRegularOneTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mBlueBirdTextureRegion);

		this.accelerators.changeTextureRegion(Resources.mAcceleratorsTextureRegion);

		this.mSpaceBackground.destroy();
		this.mSpacePlanet.destroy();

		this.mSolidLineAir.changeTextureRegion(Resources.mAirTwoTextureRegion);
	}

	private void onSDBoxAttached() {
		this.mPanel.changeTextureRegion(Resources.mSnowyTopGamePanelTextureRegion);
		this.mLevelWord.changeTextureRegion(Resources.mLevelSnowyWordTextureRegion);
		this.numbers.changeTextureRegion(Resources.mSpecialNumbers2TextureRegion);

		this.mClouds.clear();
		this.mSnowflakes.generateStartSnow();

		this.airgums.clear();
		this.airgums.changeTextureRegion(Resources.mSnowyBubbleTextureRegion);

		this.chikies.clear();
		this.chikies.changeTextureRegion(Resources.mSnowyBirdsTextureRegion);

		this.mSolidLine.changeTextureRegion(Resources.mAirSnowOneTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mBlueBirdTextureRegion);

		this.accelerators.changeTextureRegion(Resources.mAcceleratorsTextureRegion);

		this.mSnowBallSpeed.changeTextureRegion(Resources.mSnowyBallSpeedTextureRegion);

		this.mSpaceBackground.destroy();
		this.mSpacePlanet.destroy();
	}

	private void onSTBoxAttached() {
		this.mPanel.changeTextureRegion(Resources.mSpaceTopGamePanelTextureRegion);
		this.mLevelWord.changeTextureRegion(Resources.mLevelSpaceWordTextureRegion);
		this.numbers.changeTextureRegion(Resources.mSpecialNumbers3TextureRegion);

		this.chikies.clear();
		this.chikies.changeTextureRegion(Resources.mSpaceBirdsTextureRegion);

		this.airgums.clear();
		this.airgums.changeTextureRegion(Resources.mSpaceBubbleTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mSpaceBlueBirdTextureRegion);

		this.accelerators.changeTextureRegion(Resources.mSpaceAcceleratorsTextureRegion);

		this.mSnowBallSpeed.changeTextureRegion(Resources.mSpaceBallSpeedTextureRegion);

		this.mSpaceBackground.create();
		this.mSpacePlanet.create();

		this.mSolidLine.changeTextureRegion(Resources.mAirSpaceOneTextureRegion);

		this.mClouds.clear();
		this.mSnowflakes.clear();

		this.mSolidLineAir.changeTextureRegion(Resources.mSpaceIndicatorFillTextureRegion);
	}

	private void onManagedUpdateWOCBox() {
		this.mClouds.update();
	}

	private void onManagedUpdateSDBox() {
		this.mClouds.update();
		this.mSnowflakes.update();
	}

	private void onManagedUpdateSTBox() {
		this.mSpacePlanet.setRotation(this.mSpacePlanet.getRotation() + 0.1f);

		if (this.asr) {
			this.mSpaceBackground.setAlpha(this.mSpaceBackground.getAlpha() + 0.001f);
			if (this.mSpaceBackground.getAlpha() >= 1f) {
				this.asr = false;
			}
		} else {
			this.mSpaceBackground.setAlpha(this.mSpaceBackground.getAlpha() - 0.001f);
			if (this.mSpaceBackground.getAlpha() <= 0.3f) {
				this.asr = true;
			}
		}

		if (this.mMeteorits.getCount() <= 1) {
			if (Game.random.nextInt(20) == 2) {
				final Meteorit m = this.mMeteorits.create();
				if (m != null) {
					m.setCenterPosition(Game.random.nextInt(Options.cameraWidth * 3) - Options.cameraWidth, Game.random.nextInt(Options.cameraHeight * 2) - Options.cameraHeight);
					if (m.getCenterX() > 0) {
						m.setCenterPosition(-m.getWidth(), m.getCenterY());
					}
				}
			}
		}

		if (this.mSmallMeteorits.getCount() <= 5) {
			if (Game.random.nextInt(10) == 2) {
				final SmallMeteorit m = this.mSmallMeteorits.create();
				if (m != null) {
					m.setCenterPosition(Game.random.nextInt(Options.cameraWidth * 3) - Options.cameraWidth, Game.random.nextInt(Options.cameraHeight * 2) - Options.cameraHeight);
					if (m.getCenterX() > 0) {
						m.setCenterPosition(-m.getWidth(), m.getCenterY());
					}
				}
			}
		}
	}

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0f);

		this.attachChild(coloredRect);

		return coloredRect;
	}
}
