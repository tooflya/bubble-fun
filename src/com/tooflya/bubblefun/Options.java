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

	public static float cameraOriginRatioX;

	public static void setCameraOriginRatioX(final float pCameraOriginRatioX) {
		cameraOriginRatioX = pCameraOriginRatioX;
		cameraOriginRatioCenterX = pCameraOriginRatioX / 2;
	}

	public static float cameraOriginRatioY;

	public static void setCameraOriginRatioY(final float pCameraOriginRatioY) {
		cameraOriginRatioY = pCameraOriginRatioY;
		cameraOriginRatioCenterY = pCameraOriginRatioY / 2;

		touchHeight = Options.cameraOriginRatioY * touchHeightPercents;
		chikyOffsetY = Options.cameraOriginRatioY * chikyOffsetYPercent;
	}

	public static float cameraRatioFactor;

	public static float cameraOriginRatioCenterX; // For initialize use setCameraOriginRatioX. 
	public static float cameraOriginRatioCenterY; // For initialize use setCameraOriginRatioY.
	// < Camera options.

	public static float DPI;
	public final static float targetDPI = 300f;

	public static float SPEED;

	public static String CR;

	public static int levelNumber = 1;

	private final static float touchHeightPercents = 0.3f;
	public static float touchHeight; // For initialize use setCameraOriginRatioY.

	public final static int particlesCount = 7;

	// > Chiky options.
	public final static int chikyEtalonSize = 64; // Pixels.

	public final static float chikyMinStepX = 1f; // Pixels by frame.
	public final static float chikyMaxStepX = 2f; // Pixels by frame.

	public final static float chikyMaxTimeNormal = 4f; // Seconds.
	public final static float chikyMaxTimeSpeedy = 1f; // Seconds.
	public final static float chikyMaxTimeWithGum = 2f; // Seconds.
	public final static float chickyMaxTimeParachute = 1f; // Seconds

	public final static float chikySpeedCoeficient = 2.5f;

	public final static float chikyOffsetX = 3 * Options.chikyEtalonSize;

	private final static float chikyOffsetYPercent = 0.1f;;
	public static float chikyOffsetY;
	// < Chiky options.

	// > BubbleBase options.
	public final static float bubbleBaseMinScale = 0.8f;
	public final static float bubbleBaseMaxScale = 1.2f;
	public final static float bubbleBaseStepScale = 0.005f;
	// < BubbleBase options.

	// > Bubble options.
	public final static float bubbleAlpha = 0.8f;

	public static float bubbleSizePower;
	public final static float bubbleMinSize = 10f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleMaxSize = 200f; // TODO: (R) Auto set value. // Pixels.
	public final static float bubbleStepSize = 1f; // Pixels by frame.

	public final static float bubbleMinSpeed = 1f; // Pixels by frame.
	public final static float bubbleMaxSpeed = 2f; // Pixels by frame.	
	public final static float bubbleStepSpeed = 0.05f; // Pixels by frame.

	public final static float bubbleMaxTimeMove = 4f; // Seconds.
	// < Bubble options.

	public static Music mMainSound, mLevelSound, mLastPlayedMusic;
	public static Sound mButtonSound;
}
