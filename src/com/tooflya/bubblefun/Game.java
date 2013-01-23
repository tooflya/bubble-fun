package com.tooflya.bubblefun;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.WakeLockOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSCounter;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.ui.activity.LayoutGameActivity;
import org.anddev.andengine.util.user.IAsyncCallback;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.opengl.GLException;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.tooflya.bubblefun.database.DataStorage;
import com.tooflya.bubblefun.managers.AdvertisementManager;
import com.tooflya.bubblefun.managers.ScreenManager;
import com.tooflya.bubblefun.screens.AndEngineScreen;
import com.tooflya.bubblefun.screens.Screen;

/**
 * That's simple main activity of game chich extends LayoutGameActivity for correct display of advertisiment.
 * Also it implements IAsyncCallback because we need do some loading operations on background.
 * 
 * @author Tooflya.com
 * @since
 */
public class Game extends LayoutGameActivity implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	/**
	 * Random instance for all application.
	 * Using a one copy of the entire application, we obtain a more normal distribution.
	 */
	public final static Random random = new Random();

	/** Instance of engine */
	public static Engine mEngine;

	/**  */
	public static Activity mInstance;

	/** Context of main activity */
	public static Context mContext;

	/**
	 * An instance of the class to work with the camera.
	 * The camera has various methods to display, rotation and displacement all display area.
	 */
	public static Camera mCamera;

	/**
	 * Instance of the class to work with SQLite.
	 */
	public static DataStorage mDatabase;

	/**  */
	public static ScreenManager mScreens;

	/** */
	public static AdvertisementManager mAdvertisementManager;

	/**
	 * Variable is used to determine the player's return after the game.
	 * Typically, after such return we offer an various actions associated with an introduction to the game.
	 */
	public static boolean mIsAlreadyPlayed = false;

	/** 
	 * Flag knowing the extent of the congestion state of the game at the moment.
	 */
	public static boolean mIsGameLoaded = false;

	// ===========================================================
	// Fields
	// ===========================================================

	/**
	 * This variable contains the time elapsed since the last switch the screen.
	 * Used to avoid double-clicking on the back button. 
	 */
	private long mScreenChangeTime = 0;

	/**
	 * Frames Per Second. Serve as information data,
	 * but is sometimes used to remove the unnecessary elements in lost productivity.
	 */
	private  float mCurrentFramesPerSecond;

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
	@SuppressWarnings("unused")
	@Override
	public Engine onLoadEngine() {
		/** Let's remember Context of this activity */
		mContext = getApplicationContext();

		/** Set the position and resolution of camera */
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

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

		Options.menuHeight = Options.cameraHeight * Options.menuHeightPercents;
		Options.touchHeight = Options.cameraHeight * Options.touchHeightPercents;
		Options.chikyOffsetY = Options.cameraHeight * Options.chikyOffsetYPercent;

		Options.cameraRatioFactor = Options.screenWidth / Options.cameraWidth > Options.screenHeight / Options.cameraHeight ? Options.screenWidth / Options.cameraWidth : Options.screenHeight / Options.cameraHeight;

		/** Initialize camera instance */
		mCamera = new Camera(0, 0, Options.screenWidth, Options.screenHeight);

		/** Initialize the configuration of engine */
		final EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT, new FillResolutionPolicy(), mCamera)
				.setWakeLockOptions(WakeLockOptions.SCREEN_ON)
				.setNeedsMusic(true)
				.setNeedsSound(true);

		/** Disable extension vertex buffer objects. This extension usually has a problems with HTC phones. */
		options.getRenderOptions().disableExtensionVertexBufferObjects();

		/** Auto setRunOnUpdateThread for touch events. */
		options.getTouchOptions().setRunOnUpdateThread(true);

		/** Try to init our engine. */
		mEngine = new Engine(options) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.anddev.andengine.engine.Engine#onDrawFrame(javax.microedition.khronos.opengles.GL10)
			 */
			@Override
			public void onDrawFrame(GL10 pGL) throws InterruptedException {
				super.onDrawFrame(pGL);

				final int error = pGL.glGetError();

				/**
				 * 1280 GL_INVALID_ENUM
				 * 1281 GL_INVALID_VALUE
				 * 1282 GL_INVALID_OPERATION
				 * 1283 GL_STACK_OVERFLOW
				 * 1284 GL_STACK_UNDERFLOW
				 * 1285 GL_OUT_OF_MEMORY
				 */
				if (error != GL10.GL_NO_ERROR) {
					throw new GLException(error, "OpenGL ES has error occurred: " + error);
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.anddev.andengine.engine.Engine#onDrawScene(javax.microedition.khronos.opengles.GL10)
			 */
			@Override
			protected void onDrawScene(GL10 pGL) {
				super.onDrawScene(pGL);
				mCamera.setRotation(mCamera.getRotation()+0.01f);
				GLHelper.enableDither(pGL);
			}
		};

		/** */
		mDatabase = new DataStorage();

		/** Create screen manager */
		mScreens = new ScreenManager();

		/**  */
		mInstance = this;

		return mEngine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadResources()
	 */
	@Override
	public void onLoadResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/" + Options.CR);

		if (Options.DEBUG) {
			Options.isMusicEnabled = false;
			Options.isSoundEnabled = false;
		} else {
			Options.isMusicEnabled = true;
			Options.isSoundEnabled = true;
		}

		if (Options.isMusicEnabled) {
			SoundFactory.setAssetBasePath("mfx/");
			MusicFactory.setAssetBasePath("mfx/");

			try {
				Options.mMainSound = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "W1.ogg");
				Options.mLevelSound = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "W2.ogg");
				Options.mButtonSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "button_click.wav");
				Options.mAndEngineSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "W4.ogg");
				Options.mBirdsDeath1 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "birds_death_1.wav");
				Options.mBirdsDeath2 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "birds_death_2.wav");
				Options.mBirdsDeath3 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "birds_death_3.wav");
				Options.mBirdsShotted1 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "birds_shooted_1.wav");
				Options.mBirdsShotted2 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "birds_shooted_2.wav");
				Options.mBubbleDeath = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "bubble_death.wav");
				Options.mBubbleFastCreate1 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "bubble_fast_create1.wav");
				Options.mBubbleFastCreate2 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "bubble_fast_create2.wav");
				Options.mLaser = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "laser.ogg");
				Options.mGlassBroke = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "glass-broke.ogg");
				Options.mAsteroidDeath = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "asteroid-boom.ogg");
				Options.mCoinPickup = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "coin.ogg");

				Options.mMainSound.setLooping(true);
				Options.mLevelSound.setLooping(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#workToDo()
	 */
	@Override
	public void workToDo() {
		Resources.loadCommonResources();
		Resources.loadFirstResources();

		mScreens.createSurfaces();

		/** Wait while progressbar is running */
		while (!mIsGameLoaded) {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#onComplete()
	 */
	@Override
	public void onComplete() {
		mScreens.set(Screen.MENU);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadScene()
	 */
	@Override
	public Scene onLoadScene() {
		if (Options.DEBUG) {
			this.getEngine().registerUpdateHandler(new FPSCounter() {
				
				/* (non-Javadoc)
				 * @see org.anddev.andengine.entity.util.FPSCounter#onUpdate(float)
				 */
				@Override
				public void onUpdate(float pSecondsElapsed) {
					super.onUpdate(pSecondsElapsed);

					mCurrentFramesPerSecond = getFPS();
				}
			});

			this.getEngine().registerUpdateHandler(new IUpdateHandler() {

				private float mTime;

				/* (non-Javadoc)
				 * @see org.anddev.andengine.engine.handler.IUpdateHandler#onUpdate(float)
				 */
				@Override
				public void onUpdate(float pSecondsElapsed) {
					this.mTime += pSecondsElapsed;

					if (this.mTime > 5f) {
						this.mTime = 0;

						System.out.println("Average FPS: " + Debug.deltaFPS);
						System.out.println("Current FPS: " + mCurrentFramesPerSecond);
						System.out.println("=========================================================");
					}
				}

				/* (non-Javadoc)
				 * @see org.anddev.andengine.engine.handler.IUpdateHandler#reset()
				 */
				@Override
				public void reset() {
				}
			});
		}

		/** */
		mAdvertisementManager = new AdvertisementManager();

		/** Create loading screen and return her scene for attaching to the activity. */
		return new AndEngineScreen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		/**
		 * Notify the system to finalize and collect all objects of the application on exit so that the process running the application can be killed by the system without causing issues.
		 * NOTE: If this is set to true then the process will not be killed until all of its threads have closed.
		 */
		System.runFinalizersOnExit(true);

		/**
		 * Force the system to close the application down completely instead of retaining it in the background.
		 * The process that runs the application will be killed.
		 * The application will be completely created as a new application in a new process if the user starts the application again.
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

		try {
			mScreens.getCurrent().setIgnoreUpdate(false);
			mScreens.getCurrent().setChildrenIgnoreUpdate(false);
		} catch (NullPointerException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.activity.BaseGameActivity#onPauseGame()
	 */
	@Override
	public void onPauseGame() {
		super.onPauseGame();

		if (Options.isMusicEnabled) {
			Options.mLastPlayedMusic = null;

			if (Options.mMainSound.isPlaying()) {
				Options.mMainSound.pause();
				Options.mLastPlayedMusic = Options.mMainSound;
			}
			if (Options.mLevelSound.isPlaying()) {
				Options.mLevelSound.pause();
				Options.mLastPlayedMusic = Options.mLevelSound;
			}
		}

		try {
			mScreens.getCurrent().setIgnoreUpdate(true);
			mScreens.getCurrent().setChildrenIgnoreUpdate(true);
		} catch (NullPointerException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Screen.screen < 0) {
				return true;
			}

			if (System.currentTimeMillis() - mScreenChangeTime < 500) {
				return true;
			}

			mScreenChangeTime = System.currentTimeMillis();

			if (Options.isSoundEnabled) {
				Options.mButtonSound.play();
			}

			mScreens.get(Screen.screen).onBackPressed();

			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.LayoutGameActivity#getLayoutID()
	 */
	@Override
	protected int getLayoutID() {
		return R.layout.advertisiment;
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.ui.activity.LayoutGameActivity#getRenderSurfaceViewID()
	 */
	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.xmllayoutRenderSurfaceView;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @param textures
	 */
	public static void loadTextures(final BitmapTextureAtlas... textures) {
		mEngine.getTextureManager().loadTextures(textures);
	}

	/**
	 * @param textures
	 */
	public static void unloadTextures(final BitmapTextureAtlas... textures) {
		mEngine.getTextureManager().unloadTextures(textures);
	}

	/**
	 * 
	 */
	public static void close() {
		mInstance.finish();
	}

	/**
	 * @param pString
	 * @return
	 */
	public static String getString(final String pString) {
		try {
			return Game.mContext.getString(Game.mContext.getResources().getIdentifier(pString, "string", Game.mContext.getPackageName()));
		} catch (NotFoundException ex) {
			return pString;
		}
	}
}
