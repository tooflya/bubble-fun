package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;

public class CloudsManager extends EntityManager {

	public CloudsManager(int capacity, Entity element) {
		super(capacity, element);
	}

	public void generateStartClouds() {
		for (int i = 0; i < 5; i++) {
			this.generateCloud(true);
		}
	}

	public void generateCloud(final boolean isStart) {
		final int frame = Game.random.nextInt(4);
		final Cloud cloud = ((Cloud) this.create());

		switch (frame) {
		case 0:
			cloud.setScale(0.5f * Options.cameraRatioFactor);
			break;
		case 1:
			cloud.setScale(0.75f * Options.cameraRatioFactor);
			break;
		case 2:
			cloud.setScale(1f * Options.cameraRatioFactor);
			break;
		case 3:
			cloud.setScale(1.25f * Options.cameraRatioFactor);
			break;
		}

		cloud.setPosition(isStart ? Game.random.nextInt((int) (Options.cameraWidth + cloud.getWidthScaled())) - cloud.getWidthScaled() : Options.cameraWidth, Game.random.nextInt((int) (Options.cameraHeight / 3 * 2 - cloud.getHeightScaled())));
		cloud.setAlpha(0.4f + Game.random.nextFloat() * (1f - 0.4f));
		cloud.setSpeedX(0.2f + Game.random.nextFloat() * (2f - 0.2f));
		cloud.setCurrentTileIndex(frame);
	}

	public void update() {
		if (this.getCount() < 5) {
			this.generateCloud(false);
		}
	}
}
