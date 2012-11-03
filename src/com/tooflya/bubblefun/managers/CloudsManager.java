package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Cloud;

public class CloudsManager<T> extends EntityManager<Cloud> {

	public CloudsManager(int capacity, Cloud element) {
		super(capacity, element);
	}

	public void generateStartClouds() {
		for (int i = 0; i < 5; i++) {
			this.generateCloud(true);
		}
	}

	public void generateCloud(final boolean isStart) {
		final int frame = Game.random.nextInt(4);
		final Cloud cloud = this.create();

		switch (frame) {
		case 0:
			cloud.setScale(0.5f);
			break;
		case 1:
			cloud.setScale(0.75f);
			break;
		case 2:
			cloud.setScale(1f);
			break;
		case 3:
			cloud.setScale(1.25f);
			break;
		}

		cloud.setPosition(isStart ? Game.random.nextInt((int) (Options.cameraOriginRatioX + cloud.getWidth())) - cloud.getWidth() : Options.cameraOriginRatioX,
				Game.random.nextInt((int) (Options.cameraOriginRatioY / 3 * 2 - cloud.getHeight())));
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
