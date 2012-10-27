package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;

public class LevelEndScreen extends Screen {

	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static EntityManager stars;
	public static EntityManager mLevelStars;

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	public final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, Options.CR + "/end_lvl_bg.png", 0, 0, 1, 1)) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mMenu = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "menu_hd.png", 200, 0, 1, 2), this) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				PreloaderScreen.mChangeAction = 2;
				Game.screens.set(Screen.LOAD);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mRePlay = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "replay_hd.png", 300, 0, 1, 2), this) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				LevelScreen1.reInit();
				Game.screens.set(Screen.LEVEL);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	public final Entity mPlayNext = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "next_hd.png", 400, 0, 1, 2), this) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				Options.levelNumber++;
				LevelScreen1.reInit();
				Game.screens.set(Screen.LEVEL);

				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final TimerHandler mTimer = new TimerHandler(1f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (mStarsAnimationCount < mStarsCount + 1) {
				if (mStarsAnimationCount < mStarsCount) {
					mStarsAnimationCount++;

					Star star;
					(star = (Star) mLevelStars.create()).setCenterPosition(Game.reduceCoordinates(120f) + Game.reduceCoordinates(95f * mStarsAnimationCount), mBackground.getY() + Game.reduceCoordinates(620));

					Star particle;
					for (int i = 0; i < 7; i++) {
						particle = ((Star) stars.create());
						if (particle != null) {
							particle.Init(i).setCenterPosition(star.getCenterX() - 10f, star.getCenterY() - 10f);
						}
					}
				} else {
					mMenu.show();
					mRePlay.show();
					mPlayNext.show();
				}
			}
		}
	});

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelEndScreen() {
		this.setBackground(new SpriteBackground(mBackground.create()));

		mMenu.create().setPosition(Game.reduceCoordinates(105f), mBackground.getY() + Game.reduceCoordinates(720));
		mMenu.hide();

		mRePlay.create().setPosition(Game.reduceCoordinates(253f), mBackground.getY() + Game.reduceCoordinates(720));
		mRePlay.hide();

		mPlayNext.create().setPosition(Game.reduceCoordinates(405f), mBackground.getY() + Game.reduceCoordinates(720));
		mPlayNext.hide();

		this.registerTouchArea(mMenu);
		this.registerTouchArea(mRePlay);
		this.registerTouchArea(mPlayNext);

		stars = new EntityManager(100, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, "star.png", 0, 0, 1, 1), this));

		mLevelStars = new EntityManager(3, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, Options.CR + "/end-star.png", 0, 35, 1, 1), this));
	}

	@Override
	public void onAttached() {
		super.onAttached();

		mStarsAnimationCount = 0;

		if (LevelScreen1.mBubblesCount <= 2) {
			mStarsCount = 4 - LevelScreen1.mBubblesCount;
		} else {
			mStarsCount = 1;
			if (LevelScreen1.AIR < 0) {
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
		mLevelStars.clear();

		mMenu.hide();
		mRePlay.hide();
		mPlayNext.hide();
	}

	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

}
