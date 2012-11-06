package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Entity;

public class ChikiesManager<T> extends EntityManager<Chiky> {

	public ChikiesManager(int capacity, Entity element) {
		super(capacity, element);
	}

	public void generateStart() {
		for (int i = 0; i < 10; i++) {
			this.generate();
		}
	}

	public void generate() {
		final Chiky chiky = this.create();

		chiky.initStartX(Game.random.nextInt(Options.cameraWidth));
		chiky.initStartY(Game.random.nextInt(Options.cameraHeight));

		chiky.initIsWavely();
	}

	public void update() {
	}
}
