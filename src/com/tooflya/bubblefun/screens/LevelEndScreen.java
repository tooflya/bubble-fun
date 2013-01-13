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
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class LevelEndScreen extends PopupScreen {

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	private final Entity mPanel = new Entity(Resources.mLevelEndPanelTextureRegion, this);

	public final ButtonScaleable mMenu = new ButtonScaleable(Resources.mLevelEndReturnButtonTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.screens.set(Screen.PRELOAD);

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
			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();

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
					Game.screens.set(Screen.PRELOAD);
				} else {
					Options.levelNumber++;
					((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();

					modifier4.reset();
					mRectangleAlphaModifier.reset();
				}
			} else {
				Options.levelNumber++;
				Game.screens.set(Screen.ADS);
			}
		}
	};

	private final TimerHandler mTimer = new TimerHandler(1f, true, new ITimerCallback() {

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
				}
			}
		}
	});

	public EntityManager<Star> stars = new EntityManager<Star>(100, new Star(Resources.mStarsTextureRegion, this.mPanel));

	private AlphaModifier mRectangleAlphaModifier = new AlphaModifier(0.1f, 0.4f, 0f);

	private final Entity mTotalScoreText = new Entity(Resources.mLevelEndTotalScoreTextTextureRegion, this.mPanel);
	private final Entity mScoreText = new Entity(Resources.mLevelEndScoreTextTextureRegion, this.mPanel);
	private final Entity mStarsText = new Entity(Resources.mLevelEndStarsScoreTextTextureRegion, this.mPanel);

	private final EntityManager<Entity> mStarsCountText = new EntityManager<Entity>(1, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));
	private final EntityManager<Entity> mScoreCountText = new EntityManager<Entity>(4, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));
	private final EntityManager<Entity> mTotalScoreCountText = new EntityManager<Entity>(4, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mPanel));

	private final AwesomeText mLevelCompleteCapture = new AwesomeText(Resources.mLevelEndCompleteCaptureTextureRegion, false, this.mPanel);

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

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);

		this.mTotalScoreText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 235f);
		this.mStarsText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 155f);
		this.mScoreText.create().setCenterPosition(this.mPanel.getWidth() / 2 - 30f, 185f);

		this.mStarsCountText.create().setCenterPosition(this.mPanel.getWidth() / 2 + 10f, 155f);

		for (int i = 0; i < 4; i++) {
			this.mScoreCountText.create().setCenterPosition(this.mPanel.getWidth() / 2 + 10f + 18f * i, 185f);
		}

		for (int i = 0; i < 4; i++) {
			this.mTotalScoreCountText.create().setCenterPosition(this.mPanel.getWidth() / 2 + 40f + 18f * i, 235f);
		}

		this.mRectangle.setWidth(200f);
		this.mRectangle.setPosition(Options.screenWidth / 2 - this.mRectangle.getWidthScaled() / 2, this.mRectangle.getY());

		this.mRectangle.registerEntityModifier(this.mRectangleAlphaModifier);
	}

	@Override
	public void onAttached() {
		super.onAttached();

		this.mRectangleAlphaModifier.stop();
		this.mRectangle.setAlpha(0.4f);

		//mExplosionSound.play();

		mStarsAnimationCount = 0;

		if (LevelScreen.mBubblesCount <= 2) {
			mStarsCount = 4 - LevelScreen.mBubblesCount;
		} else {
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

		Game.db.updateLevel(Options.levelNumber, 1, mStarsCount, LevelScreen.Score);
		Game.db.updateLevel(Options.levelNumber + 1, 1, 0, 0);

		mLevelCompleteCapture.create().setPosition(this.mPanel.getWidth() / 2 - this.mLevelCompleteCapture.getWidth() / 2 - 5f, this.mPanel.getHeight() / 2 - 200 - this.mLevelCompleteCapture.getHeight() / 2, true);
	}

	@Override
	public void onShow() {
		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);
	}

	@Override
	public void onClose() {
		Game.screens.get(Screen.LEVEL).clearChildScene();

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
		if (score < LevelScreen.Score) {
			if (LevelScreen.Score - score > 10) {
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
		if (totalscore < LevelScreen.Score) {
			if (LevelScreen.Score - totalscore > 10) {
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
	}
}
