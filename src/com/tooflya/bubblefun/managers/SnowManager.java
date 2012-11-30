package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Snowflake;

public class SnowManager<T> extends EntityManager<Snowflake> {

	public SnowManager(int capacity, Snowflake element) {
		super(capacity, element);
	}

	public void generateStartSnow() {
		for (int i = 0; i < 50; i++) {
			this.generateCloud(true);
		}
	}

	public void generateCloud(final boolean isStart) {
		final int frame = Game.random.nextInt(4);
		final Snowflake cloud = this.create();

		cloud.setPosition(
				Game.random.nextInt(Options.cameraWidth),
				isStart ? Game.random.nextInt((int) (Options.cameraHeight / 3 * 2 - cloud.getHeight())) : -cloud.getHeight()
				);
		cloud.setAlpha(0.4f + Game.random.nextFloat() * (1f - 0.4f));
		cloud.setSpeedX(0.2f + Game.random.nextFloat() * (2f - 0.2f));
		cloud.setScale(0.2f + Game.random.nextFloat() * (1.2f - 0.2f));
		//cloud.setCurrentTileIndex(frame);
	}

	public void update() {
		if (this.getCount() < 50) {
			this.generateCloud(false);
		}
	}
}
