package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.database.Level;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.LevelIcon;
import com.tooflya.bubblefun.entities.Sprite;

public class LevelsManager<T> extends EntityManager<Entity> {

	private float PADDING, PADDING_B;
	private float X, Y;

	private EntityManager<Sprite> mNumbers;

	public LevelsManager(int capacity, Entity element) {
		super(capacity, element);

		PADDING = 59f;
		PADDING_B = 14f;

		X = PADDING_B + 5f;
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

		PADDING = 59f;
		PADDING_B = 14f;

		X = PADDING_B + 5f;
		Y = (Options.cameraHeight - PADDING * 5 - PADDING_B * 4) / 2;

	}

	public void generate(final EntityManager<Sprite> pNumbers) {
		this.mNumbers = pNumbers;

		this.generate();
	}

	public void generate() {
		this.mNumbers.clear();

		for (int i = 0; i < this.getCapacity(); i++) {

			if (i % 5 == 0) {
				X += 0;
			} else {
				X += PADDING + PADDING_B;
			}

			if (i % 5 == 0 && i != 0) {
				Y += PADDING + PADDING_B;
				X = PADDING_B + 5f;
			}

			LevelIcon icon = ((LevelIcon) this.create());

			icon.setCenterPosition(25f + X, 25f + Y);
			icon.id = i + 1;

			Level level = Game.db.getLevel(icon.id);

			if (icon.id == 1 || level.isOpen()) {
				icon.blocked = false;
				icon.setCurrentTileIndex(level.getStarsCount());
				if (icon.id < 10) {
					final Entity text = this.mNumbers.create();
					text.setCurrentTileIndex(icon.id);
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2, icon.getHeight() / 2 - 2f);
				} else {

					float a;
					if ((int) Math.floor(icon.id / 10) == 1) {
						a = 1 * Options.cameraRatioFactor;
					} else {
						a = 0;
					}

					Entity text = this.mNumbers.create();
					text.setCurrentTileIndex((int) Math.floor(icon.id / 10));
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2 - text.getWidth() / 4 + a, icon.getHeight() / 2 - 2f);
					text = this.mNumbers.create();
					text.setCurrentTileIndex(icon.id % 10);
					text.setScaleCenter(0, 0);
					text.setScale(1f);
					text.setCenterPosition(icon.getWidth() / 2 + text.getWidth() / 4 - a, icon.getHeight() / 2 - 2f);
				}
			} else {
				icon.setCurrentTileIndex(4);
				icon.blocked = true;
			}
		}
	}
}
