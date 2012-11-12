package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class LevelEndScreen extends PopupScreen {

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-lvl-popup.png", 0, 0, 1, 1), this);

	public final ButtonScaleable mMenu = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-menu.png", 0, 615, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.screens.l();
			
			modifier4.reset();
		}
	};

	public final ButtonScaleable mRePlay = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-replay.png", 65, 615, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();
			
			modifier4.reset();
		}
	};

	public final ButtonScaleable mPlayNext = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-next.png", 130, 615, 1, 2), this.mPanel) {
		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;
			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();
			
			modifier4.reset();
		}
	};

	private final TimerHandler mTimer = new TimerHandler(1f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (mStarsAnimationCount < mStarsCount + 1) {
				if (mStarsAnimationCount < mStarsCount) {
					mStarsAnimationCount++;

					Star star = (Star) mLevelStars.getByIndex(mStarsAnimationCount - 1);
					star.setCurrentTileIndex(0);

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

	public EntityManager<Star> stars = new EntityManager<Star>(100, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-star.png", 230, 615, 1, 1), this.mPanel));
	public EntityManager<Star> mLevelStars = new EntityManager<Star>(3, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end_lvl_bg_star.png", 195, 615, 1, 2), this.mPanel));

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
		this.mMenu.setCenterPosition(50, this.mPanel.getHeight());

		this.mRePlay.create();
		this.mRePlay.setScaleCenter(this.mRePlay.getWidth() / 2, this.mRePlay.getHeight() / 2);
		this.mRePlay.setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight());

		this.mPlayNext.create();
		this.mPlayNext.setScaleCenter(this.mRePlay.getWidth() / 2, this.mRePlay.getHeight() / 2);
		this.mPlayNext.setCenterPosition(this.mPanel.getWidth() - 50, this.mPanel.getHeight());

		Star star;
		for (int i = 0; i < 3; i++) {
			star = mLevelStars.create();
			star.setCenterPosition(this.mPanel.getWidth() / 2 + 80f * (i - 1), 300f);
			star.setCurrentTileIndex(1);
		}

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);
	}

	@Override
	public void onAttached() {
		super.onAttached();

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

		Game.db.updateLevel(Options.levelNumber, 1, mStarsCount);
		Game.db.updateLevel(Options.levelNumber + 1, 1); // TODO: Remove stars reset

		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);
	}

	@Override
	public void onDetached() {
		super.onDetached();

		/** Register timer of loading progressbar changes */
		this.unregisterUpdateHandler(mTimer);

		stars.clear();

		for (int i = 0; i < 3; i++) {
			Star star = (Star) mLevelStars.getByIndex(i);
			star.setCurrentTileIndex(1);
		}
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
	public void onClose() {
		Game.screens.get(Screen.LEVEL).clearChildScene();
	}

}
