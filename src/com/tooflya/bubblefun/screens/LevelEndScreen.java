package com.tooflya.bubblefun.screens;

import java.io.IOException;

import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.entities.Star;
import com.tooflya.bubblefun.managers.EntityManager;

public class LevelEndScreen extends Screen {

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public EntityManager stars;
	public EntityManager mLevelStars;

	private static int mStarsCount;

	private static int mStarsAnimationCount = 0;

	public final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end_lvl_bg.png", 0, 0, 1, 1), this);

	public final ButtonScaleable mMenu = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-menu.png", 0, 615, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			PreloaderScreen.mChangeAction = 2;
			Game.screens.set(Screen.LOAD);
		}
	};

	public final ButtonScaleable mRePlay = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-replay.png", 65, 615, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			((LevelScreen)Game.screens.get(Screen.LEVEL)).reInit();
			Game.screens.set(Screen.LEVEL);
		}
	};

	public final ButtonScaleable mPlayNext = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "buttons-end-next.png", 130, 615, 1, 2), this.mBackground) {
		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.ButtonScaleable#onClick()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;
			((LevelScreen)Game.screens.get(Screen.LEVEL)).reInit();
			Game.screens.set(Screen.LEVEL);
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

					LevelEndScreen.this.mBlampSound.play();

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

	private Sound mExplosionSound, mBlampSound;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelEndScreen() {
		mBackground.create();

		mMenu.create().setCenterPosition(Options.cameraOriginRatioCenterX - 100f, Options.cameraOriginRatioCenterY + 170f);
		mRePlay.create().setCenterPosition(Options.cameraOriginRatioCenterX, Options.cameraOriginRatioCenterY + 170f);
		mPlayNext.create().setCenterPosition(Options.cameraOriginRatioCenterX + 100f, Options.cameraOriginRatioCenterY + 170f);

		stars = new EntityManager(100, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end-star.png", 230, 615, 1, 1), this.mBackground));

		mLevelStars = new EntityManager(3, new Star(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "end_lvl_bg_star.png", 195, 615, 1, 2), this.mBackground));

		Star star;
		for (int i = 0; i < 3; i++) {
			(star = (Star) mLevelStars.create()).setCenterPosition(Options.cameraOriginRatioCenterX + 80f * (i - 1), Options.cameraOriginRatioCenterY + 80f);
			star.setCurrentTileIndex(1);
		}

		try {
			this.mExplosionSound = SoundFactory.createSoundFromAsset(Game.engine.getSoundManager(), Game.instance, "cheers.mp3");
			this.mBlampSound = SoundFactory.createSoundFromAsset(Game.engine.getSoundManager(), Game.instance, "blamp.mp3");
		} catch (final IOException e) {
		}
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
	public boolean onBackPressed() {
		return false;
	}

}
