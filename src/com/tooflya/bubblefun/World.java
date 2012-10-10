package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import com.tooflya.bubblefun.entities.BigBird;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.screens.LevelScreen;

public class World extends org.anddev.andengine.entity.Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public EntityManager chikies;
	public EntityManager airgums;
	public EntityManager feathers;
	public EntityManager stars;

	private BigBird mBigBird;

	// ===========================================================
	// Constructors
	// ===========================================================

	public World() {
		super();

		Game.screens.get(Screen.LEVEL).attachChild(this);

		this.chikies = new EntityManager(31, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "chiky.png", 1, 1, 1, 4)));
		this.airgums = new EntityManager(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "bubble_blow.png", 900, 0, 1, 6)));
		this.feathers = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "feather.png", 530, 0, 1, 2)));
		this.stars = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "star.png", 900, 900, 1, 1)));

		this.mBigBird = new BigBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "bird_big_animation.png", 60, 200, 1, 2), false, new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "feather_new_blue.png", 530, 300, 1, 2))));

		this.init();
	}

	public void init() {
		this.mBigBird.create();
		this.mBigBird.mFeathersManager.clear();
		this.airgums.clear();
		this.chikies.clear();
		this.stars.clear();
		this.generateChikies(30); // TODO: Change count depending to level number.

		this.feathers.clear();

		Options.scalePower = 20; // TODO: Change count of scale power.
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies(final int count) {
		Chiky chiky;
		final float stepX = 2f; // Normal speed.
		switch (Options.levelNumber) {
		case 1:
			chiky = (Chiky) this.chikies.create();
			chiky.init(0, 0, (Options.cameraHeight - Options.constHeight) / 2, stepX);
			return;
		case 2:
			chiky = (Chiky) this.chikies.create();
			chiky.init(0, Options.cameraWidth / 2, (Options.cameraHeight - Options.constHeight) * 0.50f, stepX);
			chiky = (Chiky) this.chikies.create();
			chiky.init(1, Options.cameraWidth / 2, (Options.cameraHeight - Options.constHeight) * 0.25f, -stepX);
			return;
		case 3:
			chiky = (Chiky) this.chikies.create();
			chiky.init(1, Options.cameraWidth / 2, (Options.cameraHeight - Options.constHeight) * 0.75f, -stepX);
			chiky = (Chiky) this.chikies.create();
			chiky.init(0, Options.cameraWidth / 2, (Options.cameraHeight - Options.constHeight) * 0.50f, stepX);
			chiky = (Chiky) this.chikies.create();
			chiky.init(1, Options.cameraWidth / 2, (Options.cameraHeight - Options.constHeight) * 0.25f, -stepX);
			return;
		}
		for (int i = 0; i < count; i++) {
			chiky = (Chiky) this.chikies.create();
			chiky.setOffsetTime(Options.PI / 180 * 10 * i); // TODO: Set step between chikies on screen.
		}
	}

	private void checkCollision() {
		Chiky chiky;
		Bubble airgum;
		for (int i = this.chikies.getCount() - 1; i >= 0; --i) {
			chiky = (Chiky) this.chikies.getByIndex(i);

			if (chiky.getIsFly()) {
				for (int j = this.airgums.getCount() - 1; j >= 0; --j) {
					airgum = (Bubble) this.airgums.getByIndex(j);
					if (this.isCollide(chiky, airgum)) {
						chiky.setIsNeedToFlyAway(airgum.getScaleX() * 0.75f);
					}
				}
			}
		}

		for (int j = this.airgums.getCount() - 1; j >= 0; --j) {
			airgum = (Bubble) this.airgums.getByIndex(j);
			if (this.isCollide(this.mBigBird, airgum)) {
				if (!this.mBigBird.mIsSleep) {
					this.mBigBird.particles();
					if (!airgum.isAnimationRunning()) {
						airgum.animate(40, 0, airgum);
					}
				}
				break;
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = (entity2.getX() + entity2.getWidth() / 2) - (entity1.getX() + entity1.getWidth() / 2);
		final float y = (entity2.getY() + entity2.getHeight() / 2) - (entity1.getY() + entity1.getHeight() / 2);
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < d * d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		this.checkCollision();

		if (this.chikies.getCount() == 0) {
			Options.levelNumber++;
			Game.world.init();
		}
	}
}
