package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.modifier.AlphaModifier;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.AwesomeText;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.ShopIndicator;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.ArrayEntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class LevelEndScreen extends PopupScreen {

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;
	private boolean mTimeAnimationRunning;

	private final ShopIndicator mShopIndicator;

	private final Entity mPanel = new Entity(Resources.mLevelEndPanelTextureRegion, this);

	private final AlphaModifier mScoreToTimeModifier = new AlphaModifier(1f, 1f, 0f) {
		@Override
		public void onFinished() {
			mTimeToScoreModifier.reset();
		}
	};

	private final AlphaModifier mTimeToScoreModifier = new AlphaModifier(1f, 0f, 1f) {
		@Override
		public void onFinished() {

		}
	};

	public final ButtonScaleable mMenu = new ButtonScaleable(Resources.mLevelEndReturnButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.mScreens.set(Screen.PRELOAD);

			modifier4.reset();
			mRectangleAlphaModifier.reset();
		}
	};

	public final ButtonScaleable mRePlay = new ButtonScaleable(Resources.mLevelEndRestartButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			((LevelScreen) Game.mScreens.get(Screen.LEVEL)).reInit();

			modifier4.reset();
			mRectangleAlphaModifier.reset();
		}
	};

	public final ButtonScaleable mPlayNext = new ButtonScaleable(Resources.mLevelEndNextButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			if (Game.mAdvertisementManager.isAdvertisementDisabled()) {
				if (Options.levelNumber % 25 == 0) {
					ScreenManager.mChangeAction = 3;
					Game.mScreens.set(Screen.PRELOAD);
				} else {
					Options.levelNumber++;
					((LevelScreen) Game.mScreens.get(Screen.LEVEL)).reInit();

					modifier4.reset();
					mRectangleAlphaModifier.reset();
				}
			} else {
				modifier4.reset();
				Options.levelNumber++;
				Game.mScreens.set(Screen.ADS);
			}
		}
	};

	public final ButtonScaleable mStore = new ButtonScaleable(Resources.mStoreButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;
			ScreenManager.mChangeAction = 5;
			Game.mScreens.set(Screen.PRELOAD);
		}
	};

	private final TimerHandler mTimer = new TimerHandler(0.5f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (mStarsAnimationCount < mStarsCount + 1) {
				if (mStarsAnimationCount < mStarsCount) {
					mStarsAnimationCount++;

					Star star = (Star) stars.getByIndex(mStarsAnimationCount);
					star.setVisible(true);

					Star particle;
					for (int i = 0; i < 7; i++) {
						particle = ((Star) stars.create());
						if (particle != null) {
							particle.Init(i).setCenterPosition(star.getCenterX(), star.getCenterY());
						}
					}
				} else {
					registerUpdateHandler(mTimer2);
					unregisterUpdateHandler(mTimer);
				}
			}
		}
	});

	private final TimerHandler mTimer2 = new TimerHandler(0.2f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (!mTimeAnimationRunning) {
				mTimeAnimationRunning = true;

				mScoreToTimeModifier.reset();
			} else {
				if (mScoreToTimeModifier.isFinished() && mTimeToScoreModifier.isFinished()) {
					if (LevelScreen.mCurrentLevelTime < LevelScreen.mLevelTimeEtalon) {
						LevelScreen.mCurrentLevelTime++;
						LevelScreen.mCurrentScore += 100;

						Game.mDatabase.addCoins(Options.levelNumber);

						if (Options.isSoundEnabled) {
							Options.mCoinPickup.play();
						}
					} else {
						mShopIndicator.update();

						unregisterUpdateHandler(mTimer2);
					}
				}
			}
		}
	});

	public ArrayEntityManager<Star> stars = new ArrayEntityManager<Star>(100, new Star(Resources.mStarsTextureRegion, this.mPanel));

	private AlphaModifier mRectangleAlphaModifier = new AlphaModifier(0.1f, 0.4f, 0f);

	private final Entity mTotalScoreText = new Entity(Resources.mLevelEndTotalScoreTextTextureRegion, this.mPanel);
	private final Entity mScoreText = new Entity(Resources.mLevelEndScoreTextTextureRegion, this.mPanel);
	private final Entity mTimeText = new Entity(Resources.mLevelEndTimeTextTextureRegion, this.mPanel);
	private final Entity mStarsText = new Entity(Resources.mLevelEndStarsScoreTextTextureRegion, this.mPanel);

	private final ArrayEntityManager<Entity> mStarsCountText = new ArrayEntityManager<Entity>(1, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));
	private final ArrayEntityManager<Entity> mScoreCountText = new ArrayEntityManager<Entity>(4, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));
	private final ArrayEntityManager<Entity> mTimeCountText = new ArrayEntityManager<Entity>(4, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));
	private final ArrayEntityManager<Entity> mTotalScoreCountText = new ArrayEntityManager<Entity>(4, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));

	private final AwesomeText mLevelCompleteCapture = new AwesomeText(Resources.mLevelEndCompleteCaptureTextureRegion, false, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.AwesomeText#onCreate()
		 */
		@Override
		public void onCreate() {
			super.onCreate();

			this.mAlpha = 1f;
		}

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.AwesomeText#onAnimationFinished()
		 */
		@Override
		public void onAnimationFinished() {
			super.onAnimationFinished();

			this.shake = false;
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelEndScreen() {
		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mMenu.create();
		this.mMenu.setScaleCenter(this.mMenu.getWidth() / 2, this.mMenu.getHeight() / 2);
		this.mMenu.setCenterPosition(80, this.mPanel.getHeight() + 50f);

		this.mRePlay.create();
		this.mRePlay.setScaleCenter(this.mRePlay.getWidth() / 2, this.mRePlay.getHeight() / 2);
		this.mRePlay.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() + 50f);

		this.mPlayNext.create();
		this.mPlayNext.setScaleCenter(this.mRePlay.getWidth() / 2, this.mRePlay.getHeight() / 2);
		this.mPlayNext.setCenterPosition(this.mPanel.getWidth() - 80, this.mPanel.getHeight() + 50f);

		this.mStore.create();
		this.mStore.setScaleCenter(this.mStore.getWidth() / 2, this.mStore.getHeight() / 2);
		this.mStore.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() + 120f);

		this.mShopIndicator = new ShopIndicator(Resources.mShopAvailableTextureRegion, this.mStore);
		this.mShopIndicator.setCenterPosition(50f, 5f);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);

		this.mTotalScoreText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 235f);
		this.mStarsText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 155f);
		this.mScoreText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 185f);
		this.mTimeText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 184f);

		this.mScoreText.registerEntityModifier(this.mScoreToTimeModifier);
		this.mTimeText.registerEntityModifier(this.mTimeToScoreModifier);

		this.mScoreText.enableBlendFunction();
		this.mTimeText.enableBlendFunction();

		this.mStarsCountText.create().setCenterPosition(this.mPanel.getWidth() / 2 + 10f, 155f);

		for (int i = 0; i < 4; i++) {
			final Entity entity = this.mScoreCountText.create();
			entity.setCenterPosition(this.mPanel.getWidth() / 2 + 10f + 18f * i, 185f);
			entity.registerEntityModifier(this.mScoreToTimeModifier);
			entity.enableBlendFunction();
		}

		for (int i = 0; i < 4; i++) {
			this.mTotalScoreCountText.create().setCenterPosition(this.mPanel.getWidth() / 2 + 40f + 18f * i, 235f);
		}

		for (int i = 0; i < 4; i++) {
			float h = 0;
			switch (i) {
			case 1:
				h = 13;
				break;
			case 2:
				h = 26;
				break;
			case 3:
				h = 44;
				break;
			}
			final Entity entity = this.mTimeCountText.create();
			entity.setCenterPosition(this.mPanel.getWidth() / 2 + 10f + h, 185f);
			entity.registerEntityModifier(this.mTimeToScoreModifier);
			entity.enableBlendFunction();
		}

		this.mRectangle.setWidth(200f);
		this.mRectangle.setPosition(Options.screenWidth / 2 - this.mRectangle.getWidthScaled() / 2, this.mRectangle.getY());

		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifier);

		this.mScoreText.registerEntityModifier(this.mScoreToTimeModifier);
	}

	@Override
	public void onAttached() {
		super.onAttached();

		mTimeAnimationRunning = false;

		this.mTimeText.setAlpha(0);
		for (int i = 0; i < 4; i++) {
			final Entity entity = this.mTimeCountText.getByIndex(i);
			entity.setAlpha(0);
		}

		this.mScoreText.setAlpha(1f);
		for (int i = 0; i < 4; i++) {
			final Entity entity = this.mScoreCountText.getByIndex(i);
			entity.setAlpha(1f);
		}

		this.mRectangleAlphaModifier.stop();
		this.mRectangle.setAlpha(0.4f);

		mStarsAnimationCount = 0;

		if (LevelScreen.mCurrentScore == LevelScreen.mBirdsCount * 300) {
			mStarsCount = 3;
		} else if (LevelScreen.mCurrentScore >= LevelScreen.mBirdsCount * 150) {
			mStarsCount = 2;
		} else if (LevelScreen.mCurrentScore >= LevelScreen.mBirdsCount * 100) {
			mStarsCount = 1;
		}

		Star star;
		stars.create().setVisible(false);
		for (int i = 0; i < 3; i++) {
			star = stars.create();
			star.setCenterPosition(this.mPanel.getWidth() / 2 + 80f * (i - 1), 100f);
			star.setCurrentTileIndex(0);
			star.setVisible(false);
		}
		for (int i = 0; i < 3; i++) {
			star = stars.create();
			star.setCenterPosition(this.mPanel.getWidth() / 2 + 80f * (i - 1), 100f);
			star.setCurrentTileIndex(1);
		}

		mStarsCountText.getByIndex(0).setCurrentTileIndex(0);

		score = 0;
		totalscore = 0;

		Game.mDatabase.updateLevel(Options.levelNumber, 1, mStarsCount, LevelScreen.mCurrentScore);
		Game.mDatabase.updateLevel(Options.levelNumber + 1, 1, 0, 0);

		mLevelCompleteCapture.create().setPosition(this.mPanel.getWidth() / 2 - this.mLevelCompleteCapture.getWidth() / 2 - 5f, this.mPanel.getHeight() / 2 - 200 - this.mLevelCompleteCapture.getHeight() / 2, true);
	}

	@Override
	public void onShow() {
		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);
	}

	@Override
	public void onClose() {
		Game.mScreens.get(Screen.LEVEL).clearChildScene();

		/** Register timer of loading progressbar changes */
		this.unregisterUpdateHandler(mTimer);

		stars.clear();

		mLevelCompleteCapture.destroy();
	}

	private int score, totalscore;

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		mStarsCountText.getByIndex(0).setCurrentTileIndex(mStarsAnimationCount);

		/* Score */
		if (score < LevelScreen.mCurrentScore) {
			if (LevelScreen.mCurrentScore - score > 10) {
				score += 11;
			} else {
				score++;
			}
		}

		if (score < 10) {
			this.mScoreCountText.getByIndex(0).setCurrentTileIndex(score);
			this.mScoreCountText.getByIndex(0).setVisible(true);
			this.mScoreCountText.getByIndex(1).setVisible(false);
			this.mScoreCountText.getByIndex(2).setVisible(false);
			this.mScoreCountText.getByIndex(3).setVisible(false);
		} else if (score < 100) {
			this.mScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(score / 10));
			this.mScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			this.mScoreCountText.getByIndex(0).setVisible(true);
			this.mScoreCountText.getByIndex(1).setVisible(true);
			this.mScoreCountText.getByIndex(2).setVisible(false);
			this.mScoreCountText.getByIndex(3).setVisible(false);
		} else if (score < 1000) {
			this.mScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(score / 100));
			this.mScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((score - FloatMath.floor(score / 100) * 100) / 10));
			this.mScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			this.mScoreCountText.getByIndex(0).setVisible(true);
			this.mScoreCountText.getByIndex(1).setVisible(true);
			this.mScoreCountText.getByIndex(2).setVisible(true);
			this.mScoreCountText.getByIndex(3).setVisible(false);
		} else {
			this.mScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(score / 1000));
			this.mScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(((score - ((int) FloatMath.floor(score / 1000) * 1000)) / 100)));
			this.mScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(((score - ((int) FloatMath.floor(score / 100) * 100)) / 10)));
			this.mScoreCountText.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(score % 10));
			this.mScoreCountText.getByIndex(0).setVisible(true);
			this.mScoreCountText.getByIndex(1).setVisible(true);
			this.mScoreCountText.getByIndex(2).setVisible(true);
			this.mScoreCountText.getByIndex(3).setVisible(true);
		}

		/* TOTAL Score */
		if (totalscore < LevelScreen.mCurrentScore) {
			if (LevelScreen.mCurrentScore - totalscore > 10) {
				totalscore += 11;
			} else {
				totalscore++;
			}
		}

		if (totalscore < 10) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex(totalscore);
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(false);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
		} else if (totalscore < 100) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 10));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
		} else if (totalscore < 1000) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 100));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((totalscore - FloatMath.floor(totalscore / 100) * 100) / 10));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
		} else {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 1000));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 1000) * 1000)) / 100)));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 100) * 100)) / 10)));
			this.mTotalScoreCountText.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(true);
		}

		/* TIME */
		this.mTimeCountText.getByIndex(0).setCurrentTileIndex(0);
		this.mTimeCountText.getByIndex(1).setCurrentTileIndex(11);

		final int time = (int) ((int) LevelScreen.mLevelTimeEtalon - LevelScreen.mCurrentLevelTime);

		if (time > 0) {
			this.mTimeCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(time / 10));
			this.mTimeCountText.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(time % 10));
		} else {
			this.mTimeCountText.getByIndex(2).setCurrentTileIndex(0);
			this.mTimeCountText.getByIndex(3).setCurrentTileIndex(0);
		}
	}
}
