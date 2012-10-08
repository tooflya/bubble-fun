package com.tooflya.airbubblegum.managers;

import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.entities.LevelIcon;

public class LevelsManager extends EntityManager {

	private float PADDING, PADDING_B;
	private float X, Y;

	public LevelsManager(int capacity, Entity element) {
		super(capacity, element);

		PADDING = 116f * Options.CAMERA_RATIO_FACTOR;
		PADDING_B = 23.2f * Options.CAMERA_RATIO_FACTOR;

		X = PADDING_B;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;
	}

	public void generate() {
		for (int i = 0; i < this.getCapacity(); i++) {

			if (i % 4 == 0) {
				X += 0;
			} else {
				X += PADDING + PADDING_B;
			}

			if (i % 4 == 0 && i != 0) {
				Y += PADDING + PADDING_B;
				X = PADDING_B;
			}

			LevelIcon icon = ((LevelIcon) this.create());
			icon.setPosition(X, Y);
			icon.id = i + 1;
		}
	}
}
