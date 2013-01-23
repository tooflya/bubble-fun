package com.tooflya.bubblefun.factories;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Airplane;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.screens.LevelScreen;
import com.tooflya.bubblefun.screens.Screen;

public class BubbleFactory {
	private static void BubblesBoom(final float pX, final float pY, final int pCount, final float pSpeed) {
		Bubble bubble;
		final float startAngle = 2 * Options.PI * Game.random.nextFloat();
		final float stepAngle = 2 * Options.PI / pCount;
		for (int i = 0; i < pCount; i++) {
			bubble = (Bubble) ((LevelScreen) Game.mScreens.get(Screen.LEVEL)).bubbles.create();
			if (bubble != null) {
				bubble.initStartPosition(pX, pY);
				bubble.initFinishPosition(pX + pSpeed * FloatMath.cos(startAngle + i * stepAngle), pY + pSpeed * FloatMath.sin(startAngle + i * stepAngle));
			}
		}
	}

	private static void BubblesRainRandom(final float pX, final float pY, final int pCount) {
		Bubble bubble;
		for (int i = 0; i < pCount; i++) {
			bubble = (Bubble) ((LevelScreen) Game.mScreens.get(Screen.LEVEL)).bubbles.create();
			if (bubble != null) {
				final float x = pX + (pCount * bubble.getWidth()) * (0.5f - Game.random.nextFloat());
				final float y = pY + (pCount * bubble.getWidth()) * (0.5f - Game.random.nextFloat());
				final float speed = Options.bubbleMinSpeed + (Options.bubbleMaxSpeed - Options.bubbleMinSpeed) * Game.random.nextFloat();
				bubble.initStartPosition(x, y);
				bubble.initFinishPosition(x, y - 2 * speed);
			}
		}
	}

	private static void BubblesRainControl(final float pX, final float pY, final int pCount) {
		Bubble bubble;
		for (int i = 0; i < pCount; i++) {
			bubble = (Bubble) ((LevelScreen) Game.mScreens.get(Screen.LEVEL)).bubbles.create();
			if (bubble != null) {
				final float x = pX + (i - pCount / 2) * 2 * bubble.getWidth();
				final float speed = Options.bubbleMaxSpeed;
				bubble.initStartPosition(x, pY);
				bubble.initFinishPosition(x, pY - 2 * speed);
			}
		}
	}

	private static void ChikiesSpeedUpDown(final float pSpeedKoef) {
		final EntityManager<Chiky> chikies = ((LevelScreen) Game.mScreens.get(Screen.LEVEL)).chikies;
		for (int i = 0; i < chikies.getCount(); i++) {
			if (chikies.getByIndex(i).isCanCollide()) {
				chikies.getByIndex(i).mKoefSpeedTime = pSpeedKoef;
			}
		}
	}

	public static void BubbleBonus(final int type) {
		BubbleBonus(Game.random.nextInt(Options.cameraWidth), Game.random.nextInt(Options.cameraHeight), type);
	}

	public static void BonusAirplane() {
		((Airplane) ((LevelScreen) Game.mScreens.get(Screen.LEVEL)).mAirplane.create()).init();
	}

	public static void BubbleBonus(final float pX, final float pY, final int type) {
		switch (type) {
		case 1:
			BubblesBoom(pX, pY, 3, 5);
			break;
		case 2:
			BubblesBoom(pX, pY, 3, 1);
			break;
		case 3:
			BubblesBoom(pX, pY, 5, 5);
			break;
		case 4:
			BubblesBoom(pX, pY, 5, 1);
			break;
		case 5:
			BubblesBoom(pX, pY, 7, 5);
			break;
		case 6:
			BubblesBoom(pX, pY, 7, 1);
			break;
		case 7:
			BubblesBoom(pX, pY, 9, 5);
			break;
		case 8:
			BubblesBoom(pX, pY, 9, 1);
			break;

		case 11:
			BubblesRainRandom(pX, pY, 3);
			break;
		case 12:
			BubblesRainRandom(pX, pY, 5);
			break;
		case 13:
			BubblesRainRandom(pX, pY, 7);
			break;
		case 14:
			BubblesRainRandom(pX, pY, 9);
			break;
		case 15:
			BubblesRainRandom(pX, pY, 11);
			break;
		case 16:
			BubblesRainRandom(pX, pY, 13);
			break;
		case 17:
			BubblesRainRandom(pX, pY, 15);
			break;
		case 18:
			BubblesRainRandom(pX, pY, 17);
			break;

		case 21:
			BubblesRainControl(pX, pY, 2);
			break;
		case 22:
			BubblesRainControl(pX, pY, 3);
			break;
		case 23:
			BubblesRainControl(pX, pY, 4);
			break;
		case 24:
			BubblesRainControl(pX, pY, 5);
			break;
		case 25:
			BubblesRainControl(pX, pY, 6);
			break;
		case 26:
			BubblesRainControl(pX, pY, 7);
			break;
		case 27:
			BubblesRainControl(pX, pY, 8);
			break;
		case 28:
			BubblesRainControl(pX, pY, 9);
			break;

		case 31:
			BonusAirplane();
			break;

		case 41:
			ChikiesSpeedUpDown(0.9f);
			break;
		case 42:
			ChikiesSpeedUpDown(0.8f);
			break;
		case 43:
			ChikiesSpeedUpDown(0.7f);
			break;
		case 44:
			ChikiesSpeedUpDown(0.6f);
			break;
		case 45:
			ChikiesSpeedUpDown(0.5f);
			break;
		case 46:
			ChikiesSpeedUpDown(0.4f);
			break;
		case 47:
			ChikiesSpeedUpDown(0.3f);
			break;
		case 48:
			ChikiesSpeedUpDown(0.2f);
			break;
		}
	}
}
