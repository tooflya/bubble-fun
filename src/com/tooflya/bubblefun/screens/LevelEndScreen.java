package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;
import com.tooflya.bubblefun.modifiers.AlphaModifier;

public class LevelEndScreen extends PopupScreen {

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-lvl-popup.png", 0, 0, 1, 1), this);

	public final ButtonScaleable mMenu = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-menu.png", 0, 615, 1, 1), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.screens.l();

			modifier4.reset();
			mRectangleAlphaModifier.reset();
		}
	};

	public final ButtonScaleable mRePlay = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-replay.png", 65, 615, 1, 1), this.mPanel) {

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

	public final ButtonScaleable mPlayNext = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-next.png", 130, 615, 1, 1), this.mPanel) {
		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;
			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();

			modifier4.reset();
			mRectangleAlphaModifier.reset();
		}
	};

	private final TimerHandler mTimer = new TimerHandler(1f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (mStarsAnimationCount < mStarsCount + 1) {
				if (mStarsAnimationCount < mStarsCount) {
					mStarsAnimationCount++;

					Star star = (Star) stars.getByIndex(mStarsAnimationCount);
					star.show();

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

	public EntityManager<Star> stars = new EntityManager<Star>(100, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-stars.png", 230, 615, 1, 2), this.mPanel));

	private AlphaModifier mRectangleAlphaModifier = new AlphaModifier(0.1f, 0.4f, 0f);

	private final Sprite mTotalScoreText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-text-total-score.png", 512, 0, 1, 1), this.mPanel);
	private final Sprite mScoreText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-text-score.png", 512, 30, 1, 1), this.mPanel);
	private final Sprite mStarsText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-text-star.png", 512, 60, 1, 1), this.mPanel);

	private final TiledTextureRegion numbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-text-numbers.png", 512, 512, 10, 1);

	private final EntityManager<Sprite> mStarsCountText = new EntityManager<Sprite>(1, new Sprite(numbersTextureRegion, this.mPanel));
	private final EntityManager<Sprite> mScoreCountText = new EntityManager<Sprite>(4, new Sprite(numbersTextureRegion, this.mPanel));
	private final EntityManager<Sprite> mTotalScoreCountText = new EntityManager<Sprite>(4, new Sprite(numbersTextureRegion, this.mPanel));

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
			if (LevelScreen.AIR < 0) {
				mStarsCount = 0;
			}
		}

		Star star;
		stars.create().hide();
		for (int i = 0; i < 3; i++) {
			star = stars.create();
			star.setCenterPosition(this.mPanel.getWidth() / 2 + 80f * (i - 1), 100f);
			star.setCurrentTileIndex(0);
			star.hide();
		}
		for (int i = 0; i < 3; i++) {
			star = stars.create();
			star.setCenterPosition(this.mPanel.getWidth() / 2 + 80f * (i - 1), 100f);
			star.setCurrentTileIndex(1);
		}

		mStarsCountText.getByIndex(0).setCurrentTileIndex(0);

		score = 0;
		totalscore = 0;

		Game.db.updateLevel(Options.levelNumber, 1, mStarsCount);
		Game.db.updateLevel(Options.levelNumber + 1, 1); // TODO: Remove stars reset
	}

	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public void onBackPressed() {
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
