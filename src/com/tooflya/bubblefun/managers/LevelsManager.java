package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.database.Level;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;

public class LevelsManager extends EntityManager {

	private float PADDING, PADDING_B;
	private float X, Y;

	public LevelsManager(int capacity, Entity element) {
		super(capacity, element);

		PADDING = 59f * Options.cameraRatioFactor;
		PADDING_B = 14f * Options.cameraRatioFactor;

		X = PADDING_B;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bubblefun.managers.EntityManager#clear()
	 */
	@Override
	public void clear() {
		super.clear();

		PADDING = 59f * Options.cameraRatioFactor;
		PADDING_B = 14f * Options.cameraRatioFactor;

		X = PADDING_B;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;

	}

	public void generate() {
		for (int i = 0; i < this.getCapacity(); i++) {

			if (i % 5 == 0) {
				X += 0;
			} else {
				X += PADDING + PADDING_B;
			}

			if (i % 5 == 0 && i != 0) {
				Y += PADDING + PADDING_B;
				X = PADDING_B;
			}

			LevelIcon icon = ((LevelIcon) this.create());

			icon.setPosition(X, Y);
			icon.id = i + 1;

			Level level = Game.db.getLevel(icon.id);

			if (icon.id == 1 || level.isOpen()) {
				icon.blocked = false;
				icon.setCurrentTileIndex(level.getStarsCount());
			} else {
				icon.setCurrentTileIndex(4);
				icon.blocked = true;
			}

			icon.writeNumber();
		}
	}
}
