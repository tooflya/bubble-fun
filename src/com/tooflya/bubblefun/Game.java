package com.tooflya.bubblefun;

import java.util.Random;

import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.FixedStepEngine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.WakeLockOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.tooflya.bubblefun.background.AsyncTaskLoader;
import com.tooflya.bubblefun.background.IAsyncCallback;
import com.tooflya.bubblefun.database.LevelsStorage;
import com.tooflya.bubblefun.managers.ScreenManager;
import com.tooflya.bubblefun.screens.SplashScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class Game extends BaseGameActivity implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	/** Random instance for all application */
	public final static Random random = new Random();

	/** Instance of engine */
	public static Engine engine;

	/**  */
	public static Activity instance;

	/** Context of main activity */
	public static Context context;

	/** Camera of the game */
	public static Camera camera;

	/** */
	public static LevelsStorage db;

	/**  */
	public static boolean isGameLoaded = false;

	/**  */
	public static ScreenManager screens;

	// ===========================================================
	// Fields
	// ===========================================================

	/**  */
	private long screenChangeTime = 0;

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadComplete()
	 */
	@Override
	public void onLoadComplete() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadEngine()
	 */
	@Override
	public Engine onLoadEngine() {

		/** Let's remember Context of this activity */
		context = getApplicationContext();

		/** Set the position and resolution of camera */
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		Options.DPI = displayMetrics.densityDpi;
		Options.SPEED = Options.targetDPI / Options.DPI;

		/** Initialize camera parameters */
		Options.screenWidth = displayMetrics.widthPixels;
		Options.screenHeight = displayMetrics.heightPixels;

		/** */
		if (Options.screenWidth > Options.cameraRatioCenter && false) {
			Options.cameraWidth = 640;
			Options.cameraHeight = 1024;

			Options.CR = "HD/";
		} else {
			Options.cameraWidth = 380;
			Options.cameraHeight = 610;

			Options.CR = "SD/";
		}

		Options.cameraCenterX = Options.cameraWidth / 2;
		Options.cameraCenterY = Options.cameraHeight / 2;

		Options.screenCenterX = (int) (Options.screenWidth / 2);
		Options.screenCenterY = (int) (Options.screenHeight / 2);

		Options.touchHeight = Options.cameraHeight * Options.touchHeightPercents;
		Options.chikyOffsetY = Options.cameraHeight * Options.chikyOffsetYPercent;

		Options.cameraRatioFactor = Options.screenWidth / Options.cameraWidth > Options.screenHeight / Options.cameraHeight ? Options.screenWidth / Options.cameraWidth : Options.screenHeight / Options.cameraHeight;

		/** Initialize camera instance */
		camera = new Camera(0, 0, Options.screenWidth, Options.screenHeight);

		/** Initialize the configuration of engine */
		final EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT, new FillResolutionPolicy(), camera)
				.setWakeLockOptions(WakeLockOptions.SCREEN_BRIGHT)
				.setWakeLockOptions(WakeLockOptions.SCREEN_ON)
				.setNeedsMusic(true)
				.setNeedsSound(true);

		/**
		 * Disable extension vertex buffer objects. This extension usually has a problems with HTC phones
		 */
		options.getRenderOptions().disableExtensionVertexBufferObjects();

		/** Auto setRunOnUpdateThread for touch events */
		options.getTouchOptions().setRunOnUpdateThread(true);

		/** Try to init our engine */
		engine = new FixedStepEngine(options, 80);

		/** */
		db = new LevelsStorage();

		/**  */
		instance = this;

		return engine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadResources()
	 */
	@Override
	public void onLoadResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/" + Options.CR);
		SoundFactory.setAssetBasePath("mfx/");
		MusicFactory.setAssetBasePath("mfx/");

		try {
			Options.mMainSound = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "W1.ogg");
			Options.mLevelSound = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "W2.ogg");
			Options.mButtonSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "W3.ogg");

			Options.mMainSound.setLooping(true);
			Options.mLevelSound.setLooping(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#workToDo()
	 */
	@Override
	public void workToDo() {

		/** Create screen manager */
		screens = new ScreenManager();

		/** White while progressbar is running */
		while (!isGameLoaded) {
		} // TODO: synchronized?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#onComplete()
	 */
	@Override
	public void onComplete() {
		screens.set(Screen.MENU);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadScene()
	 */
	@Override
	public Scene onLoadScene() {
		/** Start background loader */
		new AsyncTaskLoader().execute(this);

		/**
		 * Create loading screen and return her scene for attaching to the activity
		 */
		return new SplashScreen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		// TODO: Here we need to correctly shutdown our application and unload all resources
		getTextureManager().unloadTextures();

		/**
		 * Notify the system to finalize and collect all objects of the application on exit so that the process running the application can be killed by the system without causing issues. NOTE: If this is set to true then the process will not be killed until all of its threads have closed.
		 */
		System.runFinalizersOnExit(true);

		/**
		 * Force the system to close the application down completely instead of retaining it in the background. The process that runs the application will be killed. The application will be completely created as a new application in a new process if the user starts the application again.
		 */
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onResumeGame()
	 */
	@Override
	public void onResumeGame() {
		super.onResumeGame();

		if (Options.mLastPlayedMusic != null) {
			Options.mLastPlayedMusic.resume();
		}

		//screens.getCurrent().setIgnoreUpdate(false);
		//screens.getCurrent().setChildrenIgnoreUpdate(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onPauseGame()
	 */
	@Override
	public void onPauseGame() {
		super.onPauseGame();

		Options.mLastPlayedMusic = null;

		if (Options.mMainSound.isPlaying()) {
			Options.mMainSound.pause();
			Options.mLastPlayedMusic = Options.mMainSound;
		}
		if (Options.mLevelSound.isPlaying()) {
			Options.mLevelSound.pause();
			Options.mLastPlayedMusic = Options.mLevelSound;
		}

		//screens.getCurrent().setIgnoreUpdate(true);
		//screens.getCurrent().setChildrenIgnoreUpdate(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (Screen.screen < 0) {
			return false;
		}

		if (System.currentTimeMillis() - screenChangeTime < 500) {
			return false;
		}

		screenChangeTime = System.currentTimeMillis();

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Options.mButtonSound.play();
			return screens.get(Screen.screen).onBackPressed();
		}

		return super.onKeyDown(keyCode, event);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public static void loadTextures(final BitmapTextureAtlas... textures) {
		engine.getTextureManager().loadTextures(textures);
	}

	public static void unloadTextures(final BitmapTextureAtlas... textures) {
		engine.getTextureManager().unloadTextures(textures);
	}

	public static void close() {
		instance.finish();
	}
}
