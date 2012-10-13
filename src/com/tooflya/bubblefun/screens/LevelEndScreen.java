package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;

public class LevelEndScreen extends Screen {

	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static EntityManager stars;
	public static EntityManager mLevelStars;

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	public final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "end_lvl_bg.png", 0, 0, 1, 1), false) {

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

	public final static Entity mMenu = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "menu_hd.png", 200, 0, 1, 2), false) {

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

	public final static Entity mRePlay = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "replay_hd.png", 300, 0, 1, 2), false) {

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

	public final static Entity mPlayNext = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "next_hd.png", 400, 0, 1, 2), false) {

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

	private final static TimerHandler mTimer = new TimerHandler(1f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (mStarsAnimationCount < mStarsCount + 1) {
				if (mStarsAnimationCount < mStarsCount) {
					mStarsAnimationCount++;

					mLevelStars.create().setCenterPosition(((96f + 96f * mStarsAnimationCount) * Options.CAMERA_RATIO_FACTOR), mBackground.getY() + 620 * Options.CAMERA_RATIO_FACTOR);

					Particle particle;
					for (int i = 0; i < Options.particlesCount * 2; i++) {
						particle = ((Particle) stars.create());
						if (particle != null) {
							particle.Init().setCenterPosition(((96f + 96f * mStarsAnimationCount) * Options.CAMERA_RATIO_FACTOR), mBackground.getY() + 620 * Options.CAMERA_RATIO_FACTOR);
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
		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		mMenu.create().setPosition(120f, mBackground.getY() + 720 * Options.CAMERA_RATIO_FACTOR);
		mMenu.hide();

		mRePlay.create().setPosition(320f, mBackground.getY() + 720 * Options.CAMERA_RATIO_FACTOR);
		mRePlay.hide();

		mPlayNext.create().setPosition(520f, mBackground.getY() + 720 * Options.CAMERA_RATIO_FACTOR);
		mPlayNext.hide();

		this.attachChild(mMenu);
		this.attachChild(mRePlay);
		this.attachChild(mPlayNext);

		this.registerTouchArea(mMenu);
		this.registerTouchArea(mRePlay);
		this.registerTouchArea(mPlayNext);
	}

	@Override
	public void init() {
		this.setBackground(new SpriteBackground(mBackground));

		mLevelStars = new EntityManager(3, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "star-lvl-01.png", 0, 35, 1, 1), Screen.LEVELEND));

		stars = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "star.png", 900, 900, 1, 1), Screen.LEVELEND));

	}

	@Override
	public void onAttached() {
		super.onAttached();

		mStarsAnimationCount = 0;

		if (LevelScreen.mBubblesCount <= 2) {
			mStarsCount = 4 - LevelScreen.mBubblesCount;
		} else {
			mStarsCount = 1;
			if (LevelScreen.AIR < 0) {
				mStarsCount = 0;
			}
		}

		Game.db.updateLevel(Options.levelNumber - 1, 1, mStarsCount);
		Game.db.updateLevel(Options.levelNumber, 1, 0);

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
		LevelScreen.reInit();
		Game.screens.set(Screen.LEVEL);

		return false;
	}

}
