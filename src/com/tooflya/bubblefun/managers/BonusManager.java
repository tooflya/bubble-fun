package com.tooflya.bubblefun.managers;

import java.util.ArrayList;

import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Entity;

public class BonusManager {

	private ArrayList<Entity> mElements;

	public BonusManager(final int capacity) {
		this.mElements = new ArrayList<Entity>(capacity);
	}

	public void add(final Entity pEntity) {
		this.mElements.add(pEntity);

		pEntity.create().setPosition(Options.cameraWidth - (50f * this.mElements.size()) - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2, Options.cameraHeight - 50f + (Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2, true);
	}

	public void update() {
		for (Entity entity : this.mElements) {
		/*	if (entity.mCount <= 0) {

			}*/
		}
	}
}
