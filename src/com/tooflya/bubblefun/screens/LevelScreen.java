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

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Acceleration;
import com.tooflya.bubblefun.entities.Aim;
import com.tooflya.bubblefun.entities.AimArrow;
import com.tooflya.bubblefun.entities.AwesomeText;
import com.tooflya.bubblefun.entities.BlueBird;
import com.tooflya.bubblefun.entities.BonusIcon;
import com.tooflya.bubblefun.entities.BonusText;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.BubbleBrokes;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.CristmasHat;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Feather;
import com.tooflya.bubblefun.entities.Glass;
import com.tooflya.bubblefun.entities.Glint;
import com.tooflya.bubblefun.entities.Mark;
import com.tooflya.bubblefun.entities.Meteorit;
import com.tooflya.bubblefun.entities.SmallMeteorit;
import com.tooflya.bubblefun.entities.Snowflake;
import com.tooflya.bubblefun.entities.TimerNumber;
import com.tooflya.bubblefun.entities.TutorialText;
import com.tooflya.bubblefun.factories.BubbleFactory;
import com.tooflya.bubblefun.managers.ArrayEntityManager;
import com.tooflya.bubblefun.managers.BonusManager;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.LevelsManager;
import com.tooflya.bubblefun.managers.ListEntityManager;
import com.tooflya.bubblefun.managers.SnowManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public static int mCurrentScore;

	public static boolean mLevelRunning;

	private boolean mIsResetAnimationRunning;
	private boolean mIsLevelEndAnimationRunning;

	public static float mCurrentLevelTime;
	public static float mLevelTimeEtalon = 10;

	public static int mBirdsCount;
	public static int mKillCount;
	public static int mDeadBirdsCount;

	// O_o
	private int mBonusType = 0;
	private Bubble lastBubble = null;

	private boolean mIsSpaceBackgroundAnimationReverse = false;

	// ===========================================================
	// Entities
	// ===========================================================

	public final ListEntityManager<Acceleration> mAccelerators;
	public final ListEntityManager<Aim> mAims;
	public final ListEntityManager<AimArrow> mArrows;
	public final ListEntityManager<Entity> mTimerBars;
	public final ListEntityManager<TimerNumber> mTimerNumbers;
	public final ListEntityManager<Chiky> mChikies;
	public final ListEntityManager<Bubble> mBubbles;
	public final ListEntityManager<Feather> mFeathers;
	public final ListEntityManager<Glass> mGlasses;
	public final ListEntityManager<Glint> mGlints;
	public final ListEntityManager<BubbleBrokes> mBubbleBrokes;
	public final ListEntityManager<AwesomeText> mAwesome;
	public final ListEntityManager<BonusText> mPoints;
	public final ListEntityManager<Mark> mMarks;
	public final ListEntityManager<CristmasHat> mCristmasHats;
	public final ListEntityManager<Entity> mSnowBallSpeed;

	public final CloudsManager<Cloud> mClouds;
	public final SnowManager<Snowflake> mSnowflakes;

	public final ArrayEntityManager<Meteorit> mMeteorits;
	public final ArrayEntityManager<SmallMeteorit> mSmallMeteorits;

	public final BlueBird mBlueBird;

	// ===========================================================
	// UI
	// ===========================================================

	private final Entity mBackground;
	private final Entity mSpaceBackground;
	private final Entity mSpacePlanet;
	private final Entity mLevelWord;
	private final Entity mPanel;
	private final Entity mResetText;
	private final Entity mSolidLine;
	private final Entity mScoreText;

	private final ButtonScaleable mMenuButton;
	private final ButtonScaleable mResetButton;

	private final Rectangle mResetAnimationHolder;
	private final Rectangle mLevelNumberHolder;

	private final ArrayEntityManager<Entity> numbers;
	private final ArrayEntityManager<Entity> numbersSmall;

	private final BonusManager mBonusManager;

	public final ArrayList<TutorialText> mTutorialTexts;

	// ===========================================================
	// Modifiers
	// ===========================================================

	private final MoveModifier restartMove1;
	private final MoveModifier restartMove2;
	private final MoveModifier restartMove3;

	private final AlphaModifier mAllFallUpModifier = new AlphaModifier(13.4f, 0f, 1f);
	private final AlphaModifier mAllFallDownModifier = new AlphaModifier(13.4f, 1f, 0f);

	private final AlphaModifier rectangleAlphaModifierOn = new AlphaModifier(1f, 0f, 0.7f);
	private final AlphaModifier rectangleAlphaModifierOff = new AlphaModifier(1f, 0.7f, 0f);

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {

		// ===========================================================
		// Background and UI
		// ===========================================================

		this.mBackground = new Entity(Options.cameraWidth, Options.cameraHeight, Resources.mLevelBackgroundGradientTextureRegion, this);
		this.mBackground.setBackgroundCenterPosition();

		this.mSpaceBackground = new Entity(Resources.mSpaceStarsBackgroundTextureRegion, this.mBackground);
		this.mSpaceBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mSpaceBackground.enableFullBlendFunction();

		this.mSmallMeteorits = new ArrayEntityManager<SmallMeteorit>(10, new SmallMeteorit(Resources.mMeteoritTextureRegion, this.mBackground));

		this.mSpacePlanet = new Entity(Resources.mSpacePlanetTextureRegion, this.mBackground);
		this.mSpacePlanet.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.mSpacePlanet.enableFullBlendFunction();

		this.mMeteorits = new ArrayEntityManager<Meteorit>(10, new Meteorit(Resources.mMeteoritTextureRegion, this.mBackground));

		this.mSnowflakes = new SnowManager<Snowflake>(30, new Snowflake(Resources.mSnowFlakesTextureRegion, this.mBackground));
		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mResetAnimationHolder = this.makeColoredRectangle(0, 0, 1f, 1f, 1f);

		this.mSolidLine = new Entity(Resources.mAirRegularOneTextureRegion, this.mBackground);
		this.mSolidLine.create().setPosition(0, Options.cameraHeight - Options.touchHeight);
		this.mSolidLine.enableFullBlendFunction();

		this.mLevelNumberHolder = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight) {

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

			@Override
			public void reset() {

				for (int i = 0; i < this.getChildCount(); i++) {
					this.getChild(i).setVisible(true);
					this.getChild(i).setAlpha(1f);
				}
				modifier = false;
				l = 1f;
			}
		};
		this.mLevelNumberHolder.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mLevelNumberHolder.setAlpha(0f);
		this.attachChild(mLevelNumberHolder);

		this.mLevelWord = new Entity(Resources.mLevelWordTextureRegion, this.mLevelNumberHolder);
		this.mLevelWord.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		this.numbers = new ArrayEntityManager<Entity>(4, new Entity(Resources.mNumbersTextureRegion, this.mLevelNumberHolder));
		this.numbers.create().setCenterPosition(Options.cameraCenterX - 38f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX + 18f, Options.cameraCenterY);
		this.numbers.create().setCenterPosition(Options.cameraCenterX + 43f, Options.cameraCenterY);

		this.mResetText = new Entity(Resources.mRestartTextTextureRegion, this.mResetAnimationHolder);
		this.mResetText.create().setPosition(-this.mResetText.getWidth(), Options.cameraCenterY - this.mResetText.getHeight() / 2f);

		// ===========================================================
		// Entities
		// ===========================================================

		this.mPoints = new ListEntityManager<BonusText>(Options.COUNT_POINTS, new BonusText(Resources.mAwesomePointsTextureRegion, this.mBackground));
		this.mAwesome = new ListEntityManager<AwesomeText>(Options.COUNT_AWESOME, new AwesomeText(Resources.mAwesomeTextsTextureRegion, this.mBackground));
		this.mAccelerators = new ListEntityManager<Acceleration>(Options.COUNT_ACCELERATORS, new Acceleration(Resources.mAcceleratorsTextureRegion, this.mBackground));
		this.mMarks = new ListEntityManager<Mark>(Options.COUNT_MARKS, new Mark(Resources.mMarkTextureRegion, this.mBackground));
		this.mBubbles = new ListEntityManager<Bubble>(Options.COUNT_BUBBLES, new Bubble(Resources.mBubbleTextureRegion, this.mBackground));
		this.mFeathers = new ListEntityManager<Feather>(Options.COUNT_FEATHERS, new Feather(Resources.mFeathersTextureRegion, this.mBackground));
		this.mGlasses = new ListEntityManager<Glass>(Options.COUNT_GLASSES, new Glass(Resources.mGlassesTextureRegion, this.mBackground));
		this.mGlints = new ListEntityManager<Glint>(Options.COUNT_GLINTS, new Glint(Resources.mGlintsTextureRegion, this.mBackground));
		this.mAims = new ListEntityManager<Aim>(Options.COUNT_AIMS, new Aim(Resources.mAimTextureRegion, this.mBackground));
		this.mArrows = new ListEntityManager<AimArrow>(Options.COUNT_ARROWS, new AimArrow(Resources.mAimArrowsTextureRegion, this.mBackground));
		this.mTimerBars = new ListEntityManager<Entity>(Options.COUNT_TIMER_BARS, new Entity(Resources.mTimerBarTextureRegion, this.mBackground));
		this.mTimerNumbers = new ListEntityManager<TimerNumber>(Options.COUNT_TIMER_NUMBERS, new TimerNumber(Resources.mTimerNumbersTextureRegion, this.mBackground));
		this.mChikies = new ListEntityManager<Chiky>(Options.COUNT_CHIKIES, new Chiky(Resources.mRegularBirdsTextureRegion, this.mBackground));
		this.mCristmasHats = new ListEntityManager<CristmasHat>(Options.COUNT_HATS, new CristmasHat(Resources.mSnowyBirdsHatTextureRegion, this.mBackground));
		this.mBubbleBrokes = new ListEntityManager<BubbleBrokes>(Options.COUNT_BROKES, new BubbleBrokes(Resources.mAsteroidBrokenTextureRegion, this.mBackground));
		this.mSnowBallSpeed = new ListEntityManager<Entity>(10, new Entity(Resources.mSpaceBallSpeedTextureRegion, this.mBackground));
		this.mBlueBird = new BlueBird(Resources.mBlueBirdTextureRegion, new ListEntityManager<Feather>(Options.COUNT_BLUE_FEATHERS, new Feather(Resources.mBlueFeathersTextureRegion, this.mBackground)), this.mBackground);

		// ===========================================================
		// UI
		// ===========================================================

		this.mPanel = new Entity(Resources.mTopGamePanelTextureRegion, this.mBackground);
		this.mPanel.create().setPosition(0, 0);

		this.mScoreText = new Entity(Resources.mScoreTextTextureRegion, this.mBackground);
		this.mScoreText.create().setPosition(10, 5);

		this.numbersSmall = new ArrayEntityManager<Entity>(5, new Entity(Resources.mSmallNumbersTextureRegion, this.mBackground));

		this.mMenuButton = new ButtonScaleable(Resources.mMenuButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
			 */
			@Override
			public void onClick() {
				Game.mScreens.setChildScreen(Game.mScreens.get(Screen.PAUSE), false, true, true);
			}
		};
		this.mMenuButton.create().setPosition(Options.cameraWidth - (0 + this.mMenuButton.getWidth()), 3f);

		this.mResetButton = new ButtonScaleable(Resources.mRestartButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
			 */
			@Override
			public void onClick() {
				if (!mIsResetAnimationRunning) {
					reInit();
				}
			}
		};
		this.mResetButton.create().setPosition(Options.cameraWidth - (5 + this.mMenuButton.getWidth() + this.mResetButton.getWidth()), 3f);

		// ===========================================================
		// Some modifiers
		// ===========================================================

		this.restartMove1 = new MoveModifier(0.5f, -mResetText.getWidth(), Options.cameraWidth / 8, Options.cameraCenterY, Options.cameraCenterY) {

			/* (non-Javadoc)
			 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onStarted()
			 */
			@Override
			public void onStarted() {
				rectangleAlphaModifierOn.reset();
			}

			/* (non-Javadoc)
			 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onFinished()
			 */
			@Override
			public void onFinished() {
				restartMove2.reset();
			}
		};

		this.restartMove2 = new MoveModifier(1f, Options.cameraWidth / 8, Options.cameraWidth / 8 * 2, Options.cameraCenterY, Options.cameraCenterY) {

			/* (non-Javadoc)
			 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onFinished()
			 */
			@Override
			public void onFinished() {
				restartMove3.reset();
			}
		};

		this.restartMove3 = new MoveModifier(0.5f, Options.cameraWidth / 8 * 2, Options.cameraWidth, Options.cameraCenterY, Options.cameraCenterY) {

			/* (non-Javadoc)
			 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#onFinished()
			 */
			@Override
			public void onFinished() {
				reInit();
			}
		};

		this.numbersSmall.create().setPosition(97, 8);
		this.numbersSmall.create().setPosition(111, 8);
		this.numbersSmall.create().setPosition(125, 8);
		this.numbersSmall.create().setPosition(138, 8);
		this.numbersSmall.create().setPosition(151, 8);

		this.numbersSmall.getByIndex(0).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(1).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(2).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(3).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(4).setCurrentTileIndex(0);
		this.numbersSmall.getByIndex(4).setVisible(false);

		// ===========================================================
		// Some modifiers
		// ===========================================================

		this.mResetText.registerEntityModifier(this.restartMove1);
		this.mResetText.registerEntityModifier(this.restartMove2);
		this.mResetText.registerEntityModifier(this.restartMove3);

		this.mResetAnimationHolder.registerEntityModifier(this.rectangleAlphaModifierOn);
		this.mResetAnimationHolder.registerEntityModifier(this.rectangleAlphaModifierOff);

		this.mResetButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallUpModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallUpModifier);
		this.mSolidLine.registerEntityModifier(this.mAllFallUpModifier);
		this.mPanel.registerEntityModifier(this.mAllFallUpModifier);

		this.mResetButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mMenuButton.registerEntityModifier(this.mAllFallDownModifier);
		this.mScoreText.registerEntityModifier(this.mAllFallDownModifier);
		this.mSolidLine.registerEntityModifier(this.mAllFallDownModifier);
		this.mPanel.registerEntityModifier(this.mAllFallDownModifier);

		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallDownModifier);
			this.numbersSmall.getByIndex(i).registerEntityModifier(this.mAllFallUpModifier);
		}

		// ===========================================================
		// Enable Blend Function
		// ===========================================================

		this.mResetButton.enableBlendFunction();
		this.mMenuButton.enableBlendFunction();
		this.mScoreText.enableBlendFunction();
		this.mSolidLine.enableBlendFunction();
		this.mPanel.enableBlendFunction();

		for (int i = 0; i < this.numbersSmall.getCount(); i++) {
			this.numbersSmall.getByIndex(i).enableBlendFunction();
		}

		for (int i = 0; i < mLevelNumberHolder.getChildCount(); i++) {
			((Entity) mLevelNumberHolder.getChild(i)).enableBlendFunction();
		}

		this.mBonusManager = new BonusManager(10);

		for (int i = 0; i < 4; i++) {
			TiledTextureRegion k = null;
			switch (i) {
			case 0:
				k = Resources.mBonus1TextureRegion;
				break;
			case 1:
				k = Resources.mBonus2TextureRegion;
				break;
			case 2:
				k = Resources.mBonus3TextureRegion;
				break;
			case 3:
				k = Resources.mBonus4TextureRegion;
				break;
			}

			final BonusIcon h = new BonusIcon(k, this.mBackground) {

				@Override
				public boolean onAreaTouched(final TouchEvent pTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					final float pTouchX = pTouchEvent.getX() / Options.cameraRatioFactor;
					final float pTouchY = pTouchEvent.getY() / Options.cameraRatioFactor;

					switch (pTouchEvent.getAction()) {
					case TouchEvent.ACTION_UP:
						if (LevelScreen.this.lastBubble != null) {
							LevelScreen.this.lastBubble.initFinishPositionWithCorrection(pTouchX, pTouchY);
							LevelScreen.this.lastBubble = null;
						}
						break;
					}

					return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}

				@Override
				public void onClick() {
					super.onClick();

					this.init(48);
				}
			};
			this.mBonusManager.add(h);
			h.enableBlendFunction();
			h.registerEntityModifier(this.mAllFallUpModifier);
			h.registerEntityModifier(this.mAllFallDownModifier);
		}

		// ===========================================================
		// Tutorial
		// ===========================================================

		this.mTutorialTexts = new ArrayList<TutorialText>();
		for (int i = 0; i < 5; i++) {
			this.mTutorialTexts.add(new TutorialText(0, 0, Resources.mFont, "TUTORIAL TEXT TUTORIAL TEXT TUTORIAL TEXT"));
			this.mTutorialTexts.get(i).setText("");
			this.attachChild(this.mTutorialTexts.get(i));
		}

		// ===========================================================
		// Other
		// ===========================================================

		this.setOnSceneTouchListener(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public void reInit() {
		this.mAllFallUpModifier.reset();
		this.rectangleAlphaModifierOff.reset();

		this.mBlueBird.clear();
		this.mBubbles.clear();
		this.mAwesome.clear();
		this.mPoints.clear();
		this.mAims.clear();
		this.mBubbleBrokes.clear();
		this.mArrows.clear();
		this.mChikies.clear();
		this.mCristmasHats.clear();
		this.mGlints.clear();
		this.mAccelerators.clear();
		this.mMarks.clear();
		this.mFeathers.clear();
		this.mGlasses.clear();
		this.mTimerBars.clear();
		this.mTimerNumbers.clear();
		this.mMeteorits.clear();
		this.mSmallMeteorits.clear();

		for (TutorialText text : this.mTutorialTexts) {
			text.setVisible(false);
		}

		this.mBonusType = 0;

		this.generateChikies();

		this.mLevelNumberHolder.reset();

		this.numbers.getByIndex(0).setCurrentTileIndex(Options.boxNumber + 1);

		if (Options.levelNumber < 10) {
			this.mLevelWord.setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY - 40f);
			this.numbers.getByIndex(3).setVisible(false);

			this.numbers.getByIndex(2).setCurrentTileIndex(Options.levelNumber);
		} else {
			this.mLevelWord.setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40f);
			this.numbers.getByIndex(3).setVisible(true);

			this.numbers.getByIndex(2).setCurrentTileIndex((int) Math.floor(Options.levelNumber / 10));
			this.numbers.getByIndex(3).setCurrentTileIndex(Options.levelNumber % 10);
		}

		this.numbers.getByIndex(1).setCurrentTileIndex(10);

		this.mIsResetAnimationRunning = false;
		this.mIsLevelEndAnimationRunning = false;

		mCurrentLevelTime = 0;
		mDeadBirdsCount = 0;
		mCurrentScore = 0;
		mKillCount = 0;

		mLevelRunning = true;

		Options.bubbleSizePower = Options.bubbleMaxSizePower;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies() {
		LevelsManager.generateLevel(Options.levelNumber);
	}

	private void checkCollision() {
		for (Bubble bubble : this.mBubbles.getElements()) {
			if (bubble.isCanCollide()) {
				for (final Chiky chiky : this.mChikies.getElements()) {
					if (chiky.isCanCollide() && bubble.isCanCollide()) {
						if (this.isCirclesCollide(chiky, bubble)) {
							bubble.addBirdsKills();
							chiky.collide(bubble);
							bubble.collide();
							mDeadBirdsCount++;
						}
					}
				}

				if (!mBlueBird.isSleep() && this.isCirclesCollide(mBlueBird, bubble)) {
					mBlueBird.particles();
					if (!bubble.isAnimationRunning()) {
						bubble.collide();
					}
				}
			}
		}
	}

	private boolean isCirclesCollide(Entity entity1, Entity entity2) {
		// Don't use scale.
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidth() / 2 + entity1.getWidth() / 2;
		return x * x + y * y < d * d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		Game.mIsAlreadyPlayed = true;

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

		Game.mScreens.get(Screen.LEVEL).clearChildScene();
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

		if (mChikies.getCount() <= 0 && !this.mIsLevelEndAnimationRunning && !this.mIsResetAnimationRunning) {
			if (mCurrentScore < LevelScreen.mBirdsCount * 100) {
				this.restart();
			} else {
				for (TutorialText text : this.mTutorialTexts) {
					text.setVisible(false);
				}

				Game.mScreens.setChildScreen(Game.mScreens.get(Screen.LEVELEND), false, false, true);
				this.mIsLevelEndAnimationRunning = true;

				mBubbles.clear();
				mFeathers.clear();

				this.lastBubble = null;

				this.mAllFallDownModifier.reset();
			}

			mLevelRunning = false;
		}

		/* Score */
		int side;
		int score = Math.abs(mCurrentScore);
		if (mCurrentScore < 0) {
			side = 1;
			numbersSmall.getByIndex(0).setCurrentTileIndex(11);
		} else {
			side = 0;
		}

		if (score < 10) {
			numbersSmall.getByIndex(0 + side).setCurrentTileIndex(score);
			numbersSmall.getByIndex(0 + side).setVisible(true);
			numbersSmall.getByIndex(1 + side).setVisible(false);
			numbersSmall.getByIndex(2 + side).setVisible(false);
			numbersSmall.getByIndex(3 + side).setVisible(false);
		} else if (score < 100) {
			numbersSmall.getByIndex(0 + side).setCurrentTileIndex((int) FloatMath.floor(score / 10));
			numbersSmall.getByIndex(1 + side).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			numbersSmall.getByIndex(0 + side).setVisible(true);
			numbersSmall.getByIndex(1 + side).setVisible(true);
			numbersSmall.getByIndex(2 + side).setVisible(false);
			numbersSmall.getByIndex(3 + side).setVisible(false);
		} else if (score < 1000) {
			numbersSmall.getByIndex(0 + side).setCurrentTileIndex((int) FloatMath.floor(score / 100));
			numbersSmall.getByIndex(1 + side).setCurrentTileIndex((int) FloatMath.floor((score - FloatMath.floor(score / 100) * 100) / 10));
			numbersSmall.getByIndex(2 + side).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			numbersSmall.getByIndex(0 + side).setVisible(true);
			numbersSmall.getByIndex(1 + side).setVisible(true);
			numbersSmall.getByIndex(2 + side).setVisible(true);
			numbersSmall.getByIndex(3 + side).setVisible(false);
		} else {
			numbersSmall.getByIndex(0 + side).setCurrentTileIndex((int) FloatMath.floor(score / 1000));
			numbersSmall.getByIndex(1 + side).setCurrentTileIndex((int) FloatMath.floor((score - FloatMath.floor(score / 1000) * 1000) / 100));
			numbersSmall.getByIndex(2 + side).setCurrentTileIndex((int) FloatMath.floor((score - FloatMath.floor(score / 100) * 100) / 10));
			numbersSmall.getByIndex(3 + side).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			numbersSmall.getByIndex(0 + side).setVisible(true);
			numbersSmall.getByIndex(1 + side).setVisible(true);
			numbersSmall.getByIndex(2 + side).setVisible(true);
			numbersSmall.getByIndex(3 + side).setVisible(true);
		}

		if (mLevelRunning) {
			mCurrentLevelTime += pSecondsElapsed;
		}
	}

	public void restart() {
		restartMove1.reset();
		mIsResetAnimationRunning = true;
		mLevelRunning = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (this.hasChildScene()) {
			if (this.getChildScene() != Game.mScreens.get(Screen.LEVELEND)) {
				Game.mScreens.clearChildScreens();
			}
		} else {
			if (!this.mIsResetAnimationRunning) {
				Game.mScreens.setChildScreen(Game.mScreens.get(Screen.PAUSE), false, true, true);
			}
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
				if (this.mChikies.getCount() > 0 && this.lastBubble == null && pTouchY > Options.cameraHeight - Options.touchHeight) {
					this.lastBubble = (Bubble) mBubbles.create();
					if (this.lastBubble != null) {
						this.lastBubble.initStartPosition(pTouchX, pTouchY);
					}
				}
			}
			else {
				BubbleFactory.BubbleBonus(pTouchX, pTouchY, this.mBonusType);
				this.mBonusType = 0;
			}
			break;
		case TouchEvent.ACTION_UP:
			if (this.lastBubble != null) {
				this.lastBubble.initFinishPositionWithCorrection(pTouchX, pTouchY);
				this.lastBubble = null;
			}
			break;
		case TouchEvent.ACTION_MOVE:
			if (this.lastBubble != null) {
				// TODO: (R) Horror. :-( This code can be lighter by using one texture without many entities.
				mMarks.clear();

				float xr = pTouchX, yr = pTouchY;

				float mSpeedX, mSpeedY;

				float angle = (float) Math.atan2(yr - this.lastBubble.getCenterY(), xr - this.lastBubble.getCenterX());
				float distance = MathUtils.distance(this.lastBubble.getCenterX(), this.lastBubble.getCenterY(), xr, yr);
				if (distance < Options.eps) {
					mSpeedX = 0;
					mSpeedY = (Options.bubbleMinSpeed + Options.bubbleMaxSpeed) / 2 - this.lastBubble.mLostedSpeed;
					mSpeedY = mSpeedY < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : mSpeedY;
					mSpeedY = -mSpeedY;
				}
				else {
					distance -= this.lastBubble.mLostedSpeed;
					distance = distance < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : distance;
					distance = distance > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : distance;

					if (0 < angle) {
						angle -= Options.PI;
					}

					mSpeedX = distance * FloatMath.cos(angle);
					mSpeedY = distance * FloatMath.sin(angle);

					float dx = mSpeedX / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));
					float dy = mSpeedY / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));

					float x = this.lastBubble.getCenterX(), y = this.lastBubble.getCenterY();
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

	@Override
	public void sortChildren() {
		this.mBackground.sortChildren();
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

		this.mBubbles.clear();
		this.mBubbles.changeTextureRegion(Resources.mBubbleTextureRegion);

		this.mChikies.clear();
		this.mChikies.changeTextureRegion(Resources.mRegularBirdsTextureRegion);

		this.mSolidLine.changeTextureRegion(Resources.mAirRegularOneTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mBlueBirdTextureRegion);

		this.mAccelerators.changeTextureRegion(Resources.mAcceleratorsTextureRegion);

		this.mSpaceBackground.destroy();
		this.mSpacePlanet.destroy();
	}

	private void onSDBoxAttached() {
		this.mPanel.changeTextureRegion(Resources.mSnowyTopGamePanelTextureRegion);
		this.mLevelWord.changeTextureRegion(Resources.mLevelSnowyWordTextureRegion);
		this.numbers.changeTextureRegion(Resources.mSpecialNumbers2TextureRegion);

		this.mClouds.clear();
		this.mSnowflakes.generateStartSnow();

		this.mBubbles.clear();
		this.mBubbles.changeTextureRegion(Resources.mSnowyBubbleTextureRegion);

		this.mChikies.clear();
		this.mChikies.changeTextureRegion(Resources.mSnowyBirdsTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mBlueBirdTextureRegion);

		this.mAccelerators.changeTextureRegion(Resources.mAcceleratorsTextureRegion);

		this.mSnowBallSpeed.changeTextureRegion(Resources.mSnowyBallSpeedTextureRegion);

		this.mSpaceBackground.destroy();
		this.mSpacePlanet.destroy();
	}

	private void onSTBoxAttached() {
		this.mPanel.changeTextureRegion(Resources.mSpaceTopGamePanelTextureRegion);
		this.mLevelWord.changeTextureRegion(Resources.mLevelSpaceWordTextureRegion);
		this.numbers.changeTextureRegion(Resources.mSpecialNumbers3TextureRegion);

		this.mChikies.clear();
		this.mChikies.changeTextureRegion(Resources.mSpaceBirdsTextureRegion);

		this.mBubbles.clear();
		this.mBubbles.changeTextureRegion(Resources.mSpaceBubbleTextureRegion);

		this.mBlueBird.changeTextureRegion(Resources.mSpaceBlueBirdTextureRegion);

		this.mAccelerators.changeTextureRegion(Resources.mSpaceAcceleratorsTextureRegion);

		this.mSnowBallSpeed.changeTextureRegion(Resources.mSpaceBallSpeedTextureRegion);

		this.mSpaceBackground.create();
		this.mSpacePlanet.create();

		this.mClouds.clear();
		this.mSnowflakes.clear();
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

		if (this.mIsSpaceBackgroundAnimationReverse) {
			this.mSpaceBackground.setAlpha(this.mSpaceBackground.getAlpha() + 0.001f);
			if (this.mSpaceBackground.getAlpha() >= 1f) {
				this.mIsSpaceBackgroundAnimationReverse = false;
			}
		} else {
			this.mSpaceBackground.setAlpha(this.mSpaceBackground.getAlpha() - 0.001f);
			if (this.mSpaceBackground.getAlpha() <= 0.3f) {
				this.mIsSpaceBackgroundAnimationReverse = true;
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
