package com.tooflya.bubblefun;

import android.os.Build;

public class Beta {
	public static String mail;
	public static String device;
	public static float deltaFPS = 0;
	public static int countFPS = 0;

	public static void update() {
		countFPS++;
		deltaFPS = (deltaFPS + Game.fps) / countFPS;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}
}
