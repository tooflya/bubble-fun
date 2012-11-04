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
	// < Math options.

	// > Camera options.
	public final static float cameraRatioCenter = 520f;
	public static float cameraRatioFactor;

	public static int cameraWidth;
	public static int cameraHeight;
	public static int cameraCenterX;
	public static int cameraCenterY;

	public static float cameraOriginRatioX;
	public static float cameraOriginRatioY;

	public static float cameraOriginRatioCenterX;
	public static float cameraOriginRatioCenterY;
	// < Camera options.

	public static float DPI;
	public final static float targetDPI = 300f;

	public static float SPEED;

	public static String CR;

	public static int levelNumber = 1;

	public static float touchHeight;

	public static float scalePower;

	public final static int particlesCount = 7;

	// > Chiky options.
	public final static int chikyEtalonSize = 64;

	public final static float chikyMinStepX = 1f; // Pixels by frame.
	public final static float chikyMaxStepX = 2f; // Pixels by frame.

	public final static float chikyMaxTimeNormal = 4f; // Seconds.
	public final static float chikyMaxTimeSpeedy = 1f; // Seconds.
	public final static float chikyMaxTimeWithGum = 2f; // Seconds.

	public final static float chikySpeedCoeficient = 2.5f;

	public final static float chikyOffsetX = 3 * Options.chikyEtalonSize;
	public static float chikyOffsetY;
	// < Chiky options.
	
	public static Music mMainSound, mLevelSound, mLastPlayedMusic; 
	public static Sound mButtonSound;
}
