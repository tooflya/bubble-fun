package com.tooflya.airbubblegum.managers;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.entities.Cloud;
import com.tooflya.airbubblegum.entities.Entity;

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
		final Cloud cloud = ((Cloud) this.create());

		cloud.setScale(Options.CAMERA_RATIO_FACTOR + (-0.5f + Game.random.nextFloat() * (1f + 0.5f)) + (-0.5f + Game.random.nextFloat() * (1f + 0.5f)) * Options.CAMERA_RATIO_FACTOR);
		cloud.setPosition(isStart ? Game.random.nextInt((int) (Options.cameraWidth + cloud.getWidthScaled())) - cloud.getWidthScaled() : Options.cameraWidth, Game.random.nextInt((int) (Options.cameraHeight - Options.cameraHeight / 3 + cloud.getHeightScaled())) - cloud.getHeightScaled());
		cloud.setAlpha(0.4f + Game.random.nextFloat() * (1f - 0.4f));
		cloud.setSpeed(0.2f + Game.random.nextFloat() * (2f - 0.2f));
		cloud.setCurrentTileIndex(Game.random.nextInt(4));
	}

	public void update() {
		if (this.getCount() < 5) {
			this.generateCloud(false);
		}
	}
}
