package com.tooflya.bubblefun;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.sound.Sound;

/**
 * @author Tooflya.com
 * @since
 */
public class Options {

	// ===========================================================
	// Compile options
	// ===========================================================

	public static final boolean DEBUG = false;

	// ===========================================================
	// Math options
	// ===========================================================

	public static float PI = (float) (2 * Math.asin(1));
	public static float eps = 5f;

	// ===========================================================
	// Camera options
	// ===========================================================

	public final static float cameraRatioCenter = 520f;

	public static int cameraWidth;
	public static int cameraHeight;
	public static int cameraCenterX;
	public static int cameraCenterY;

	public static float screenWidth;
	public static float screenHeight;
	public static int screenCenterX;
	public static int screenCenterY;

	public static float cameraRatioFactor;

	public final static float framesPerSeconds = 0.0125f;

	public static boolean isMusicEnabled;
	public static boolean isSoundEnabled;

	public static String CR;

	public static int levelNumber = 1;
	public static int boxNumber = 1;

	public final static float touchHeightPercents = 0.3f;
	public static float touchHeight;

	public final static float menuHeightPercents = 0.1f;
	public static float menuHeight;

	// ===========================================================
	// Elements options
	// ===========================================================

	public final static int particlesCount = 14;

	public final static int chikyEtalonSize = 64; // Pixels.

	public final static float chikyMinStepX = 1f; // Pixels by frame.
	public final static float chikyMaxStepX = 2f; // Pixels by frame.

	public final static float chikyFallSpeed = 4f; // Pixels by frame.

	public final static float chikyMaxTimeNormal = 4f; // Seconds.
	public final static float chikyMaxTimeSpeedy = 1f; // Seconds.
	public final static float chikyMaxTimeWithGum = 2f; // Seconds.
	public final static float chickyMaxTimeParachute = 1f; // Seconds

	public final static float chikySpeedCoeficient = 2.5f;

	public final static float chikyAngleStep = 3f;
	public final static float chikyOffsetYPercent = 0.1f;
	public static float chikyOffsetY; // For initialize use setCameraOriginRatioY.

	public final static float bubbleBaseMinScale = 0.8f;
	public final static float bubbleBaseMaxScale = 1.2f;
	public final static float bubbleBaseStepScale = 0.005f;

	public final static float bubbleAlpha = 0.8f;

	public static float bubbleSizePower;

	public final static float bubbleMaxSizePower = 10000;
	public final static float bubbleMinSize = 25f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleMaxSize = 100f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleStepSize = 1f; // Pixels by frame.

	public final static float bubbleSpeedMaxSpeed = 5f; // Pixels by frame.
	public final static float bubbleSpeedMinSpeed = 1f; // Pixels by frame.
	public final static float bubbleMinSpeed = 0.8f; // Pixels by frame.
	public final static float bubbleMaxSpeed = 2f; // Pixels by frame.	
	public final static float bubbleStepSpeed = 0.05f; // Pixels by frame.

	public final static float bubbleMaxTimeMove = 2f; // Seconds.

	// ===========================================================
	// Count of elements which will be created  immediately
	// ===========================================================

	public final static int COUNT_POINTS = 5;
	public final static int COUNT_AWESOME = 5;
	public final static int COUNT_MARKS = 20;
	public final static int COUNT_BUBBLES = 10;
	public final static int COUNT_FEATHERS = 100;
	public final static int COUNT_GLASSES = 100;
	public final static int COUNT_GLINTS = 50;
	public final static int COUNT_AIMS = 10;
	public final static int COUNT_ACCELERATORS = 0;
	public final static int COUNT_ARROWS = 10;
	public final static int COUNT_TIMER_BARS = 5;
	public final static int COUNT_TIMER_NUMBERS = 5;
	public final static int COUNT_CHIKIES = 10;
	public final static int COUNT_HATS = 10;
	public final static int COUNT_BROKES = 10;
	public final static int COUNT_BLUE_FEATHERS = 30;

	// ===========================================================
	// Sound elements
	// ===========================================================

	public static Music mMainSound, mLevelSound, mLastPlayedMusic;

	public static Sound mButtonSound, mAndEngineSound, mBirdsDeath1, mBirdsDeath2, mBirdsDeath3, mBirdsShotted1, mBirdsShotted2;
	public static Sound mBubbleDeath;
	public static Sound mBubbleFastCreate1, mBubbleFastCreate2;
	public static Sound mLaser;
	public static Sound mGlassBroke;
	public static Sound mAsteroidDeath;
	public static Sound mCoinPickup;
	public static Sound mShopSound;

	public static class Debug {
		public static float deltaFPS = 0;
		public static float sumFPS = 0;
		public static int countFPS = 0;
	}
}
