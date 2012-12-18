package com.tooflya.bubblefun;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.sound.Sound;

/**
 * @author Tooflya.com
 * @since
 */
public class Options {
	// > Compile options.
	public static final boolean DEBUG = true;
	// < Compile options.

	// > Math options.
	public static float PI = (float) (2 * Math.asin(1));
	public static float eps = 5f;
	// < Math options.

	// > Camera options.
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

	public static String CR;

	public static int levelNumber = 1;
	public static int boxNumber = 1;

	final static float touchHeightPercents = 0.3f;
	public static float touchHeight;

	final static float menuHeightPercents = 0.1f;
	public static float menuHeight;
	// < Camera options.

	public final static int particlesCount = 14;

	// > Chiky options.
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
	// < Chiky options.

	// > BubbleBase options.
	public final static float bubbleBaseMinScale = 0.8f;
	public final static float bubbleBaseMaxScale = 1.2f;
	public final static float bubbleBaseStepScale = 0.005f;
	// < BubbleBase options.

	// > Bubble options.
	public final static float bubbleAlpha = 0.8f;

	public static float bubbleSizePower;

	public static float bubbleMaxSizePower = 10000;
	public final static float bubbleMinSize = 25f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleMaxSize = 100f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleStepSize = 1f; // Pixels by frame.

	public final static float bubbleSpeedMaxSpeed = 5f; // Pixels by frame.
	public final static float bubbleSpeedMinSpeed = 1f; // Pixels by frame.
	public final static float bubbleMinSpeed = 0.8f; // Pixels by frame.
	public final static float bubbleMaxSpeed = 2f; // Pixels by frame.	
	public final static float bubbleStepSpeed = 0.05f; // Pixels by frame.

	public final static float bubbleMaxTimeMove = 2f; // Seconds.
	// < Bubble options.

	public static Music mMainSound, mLevelSound, mLastPlayedMusic;

	public static Sound mButtonSound, mAndEngineSound, mBirdsDeath1, mBirdsDeath2, mBirdsDeath3, mBirdsShotted1, mBirdsShotted2;
	public static Sound mBubbleDeath;
	public static Sound mBubbleFastCreate1, mBubbleFastCreate2;
	public static Sound mLaser;
	public static Sound mGlassBroke;
	public static Sound mAsteroidDeath;
}
