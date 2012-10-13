package com.tooflya.bubblefun;

/**
 * @author Tooflya.com
 * @since
 */
public class Options {
	public static final boolean DEBUG = true;

	public static float PI = (float) (2 * Math.asin(1));

	/** Camera parameters */
	public static int cameraWidth;
	public static int cameraHeight;
	public static int cameraCenterX;
	public static int cameraCenterY;

	public static float CAMERA_RATIO_FACTOR;

	public final static float cameraOriginRatioX = 580.0f;
	public final static float cameraOriginRatioY = 1024.0f;

	public static int levelNumber = 1;

	public static int touchHeight;
	public static int ellipseHeight;

	public static float scalePower;

	public final static int particlesCount = 7;

	public final static int chikySize = 64;
}
