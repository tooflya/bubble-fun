package com.tooflya.bubblefun;

import java.util.Random;
import java.util.regex.Pattern;

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
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.user.IAsyncCallback;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.KeyEvent;

import com.tooflya.bubblefun.database.DataStorage;
import com.tooflya.bubblefun.managers.ScreenManager;
import com.tooflya.bubblefun.screens.AndEngineScreen;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

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
	public static DataStorage db;

	/**  */
	public static boolean isGameLoaded = false;

	/**  */
	public static ScreenManager screens;

	/** */
	public static float mCurrentFramesPerSecond;

	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;

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
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();

		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

			public void onShake() {
				if (LevelScreen.running) {
					((LevelScreen) Game.screens.get(Screen.LEVEL)).restartMove1.reset();
				}
			}
		});
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
		context = getApplicationContext();

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
		camera = new Camera(0, 0, Options.screenWidth, Options.screenHeight);

		/** Initialize the configuration of engine */
		final EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT, new FillResolutionPolicy(), camera)
				.setWakeLockOptions(WakeLockOptions.SCREEN_BRIGHT)
				.setWakeLockOptions(WakeLockOptions.SCREEN_ON)
				.setNeedsMusic(true)
				.setNeedsSound(true);

		/** Disable extension vertex buffer objects. This extension usually has a problems with HTC phones. */
		options.getRenderOptions().disableExtensionVertexBufferObjects();

		/** Auto setRunOnUpdateThread for touch events. */
		options.getTouchOptions().setRunOnUpdateThread(true);

		/** Try to init our engine. */
		engine = new Engine(options) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.anddev.andengine.engine.Engine#onDrawFrame(javax.microedition.khronos.opengles.GL10)
			 */
			@Override
			public void onDrawFrame(GL10 pGL) throws InterruptedException {
				super.onDrawFrame(pGL);
				pGL.glFlush();

				int error = pGL.glGetError();

				/**
				 * 1280 GL_INVALID_ENUM 1281 GL_INVALID_VALUE 1282 GL_INVALID_OPERATION 1283 GL_STACK_OVERFLOW 1284 GL_STACK_UNDERFLOW 1285 GL_OUT_OF_MEMORY
				 */
				if (error != GL10.GL_NO_ERROR) {
					throw new GLException(error, "OpenGL ES has error occurred: " + error);
				}

				pGL.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, Debug.mGLMaxTextureParams, 0);
				pGL.glGetIntegerv(GL10.GL_MAX_TEXTURE_UNITS, Debug.mGLMaxTextureParams, 1);

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.anddev.andengine.engine.Engine#onDrawScene(javax.microedition.khronos.opengles.GL10)
			 */
			@Override
			protected void onDrawScene(GL10 pGL) {
				super.onDrawScene(pGL);
			}
		};

		/** */
		db = new DataStorage();

		/** Create screen manager */
		screens = new ScreenManager();

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

		if (!Options.DEBUG) {
			SoundFactory.setAssetBasePath("mfx/");
			MusicFactory.setAssetBasePath("mfx/");

			try {
				Options.mMainSound = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "W1.ogg");
				Options.mLevelSound = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "W2.ogg");
				Options.mButtonSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "button_click.wav");
				Options.mBirdsDeath1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "birds_death_1.wav");
				Options.mBirdsDeath2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "birds_death_2.wav");
				Options.mBirdsDeath3 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "birds_death_3.wav");
				Options.mBirdsShotted1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "birds_shooted_1.wav");
				Options.mBirdsShotted2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "birds_shooted_2.wav");
				Options.mBubbleDeath = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "bubble_death.wav");
				Options.mBubbleFastCreate1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "bubble_fast_create1.wav");
				Options.mBubbleFastCreate2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "bubble_fast_create2.wav");

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

		screens.createSurfaces();

		/** Wait while progressbar is running */
		while (!isGameLoaded) {
		} // TODO: synchronized?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#onComplete()
	 */
	@SuppressWarnings("unused")
	@TargetApi(8)
	@Override
	public void onComplete() {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 8 && false) {
			// ====================================================================================
			// FOR THE BETA VERSION ONLY
			// TODO: Remove this before relise
			Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
			Account[] accounts = AccountManager.get(context).getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					Beta.mail = account.name;
					break;
				}
			}

			Beta.device = Beta.getDeviceName();

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.beta_testers_text).setTitle(R.string.beta_testers_title);
			builder.setPositiveButton(R.string.beta_testers_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();

					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							Beta.sendFirstInformation();
						}
					};
					new Thread(runnable).start();
				}
			});
			builder.setNegativeButton(R.string.beta_testers_no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Game.close();
				}
			});
			builder.create().show();
			// ====================================================================================
		}

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
		if (Options.DEBUG) {
			this.getEngine().registerUpdateHandler(new FPSCounter() {
				@Override
				public void onUpdate(float pSecondsElapsed) {
					super.onUpdate(pSecondsElapsed);

					mCurrentFramesPerSecond = getFPS();
				}
			});

			this.getEngine().registerUpdateHandler(new IUpdateHandler() {

				private float mTime;

				@Override
				public void onUpdate(float pSecondsElapsed) {
					this.mTime += pSecondsElapsed;

					if (this.mTime > 5) {
						this.mTime = 0;

						System.out.println("Avg FPS: " + Debug.deltaFPS);
						System.out.println("Current FPS: " + mCurrentFramesPerSecond);
						System.out.println("GPU Memory Allocated: " + Debug.mGraphicsHeapAllocation);
						System.out.println("GL Max Textures Size: " + Debug.mGLMaxTextureParams[0]);
						System.out.println("GL Max TexturesUnits: " + Debug.mGLMaxTextureParams[1]);
						System.out.println("=========================================================");
					}
				}

				@Override
				public void reset() {
				}
			});
		}

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

		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);

		if (Options.mLastPlayedMusic != null) {
			Options.mLastPlayedMusic.resume();
		}

		try {
			screens.getCurrent().setIgnoreUpdate(false);
			screens.getCurrent().setChildrenIgnoreUpdate(false);
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

		if (!Options.DEBUG) {
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
			screens.getCurrent().setIgnoreUpdate(true);
			screens.getCurrent().setChildrenIgnoreUpdate(true);
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

			if (System.currentTimeMillis() - screenChangeTime < 500) {
				return true;
			}

			screenChangeTime = System.currentTimeMillis();

			if (!Options.DEBUG) {
				Options.mButtonSound.play();
			}
			
			screens.get(Screen.screen).onBackPressed();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public static void loadTextures(final BitmapTextureAtlas... textures) {
		if (Options.DEBUG) {
			final int count = textures.length;
			for (int i = 0; i < count; i++) {
				final int width = textures[i].getWidth();
				final int height = textures[i].getHeight();

				Debug.mGraphicsHeapAllocation += width * height * 4 / 1024 / 1024;
			}
		}

		engine.getTextureManager().loadTextures(textures);
	}

	public static void unloadTextures(final BitmapTextureAtlas... textures) {
		if (Options.DEBUG) {
			final int count = textures.length;
			for (int i = 0; i < count; i++) {
				final int width = textures[i].getWidth();
				final int height = textures[i].getHeight();

				Debug.mGraphicsHeapAllocation -= width * height * 4 / 1024 / 1024;
			}
		}

		engine.getTextureManager().unloadTextures(textures);
	}

	public static void close() {
		instance.finish();
	}
}
