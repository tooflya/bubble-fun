package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.entities.Entity;

public class BubblesManager extends EntityManager {

	public BubblesManager(int capacity, Entity element) {
		super(capacity, element);
	}

	public void update() {
		if (this.getCount() < 3) {
			this.create();
		}
	}
}
