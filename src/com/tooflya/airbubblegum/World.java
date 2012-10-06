package com.tooflya.airbubblegum;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import org.anddev.andengine.engine.camera.Camera;

import com.tooflya.airbubblegum.entities.Airgum;
import com.tooflya.airbubblegum.entities.Chiky;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.entities.Particle;
import com.tooflya.airbubblegum.managers.EntityManager;

public class World extends org.anddev.andengine.entity.Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas texture;

	public EntityManager chikies;
	public EntityManager airgums;
	public EntityManager feathers;

	// ===========================================================
	// Constructors
	// ===========================================================

	public World() {
		super();

		Game.screens.get(Screen.LEVEL).attachChild(this);

		this.texture = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Game.loadTextures(texture);

		this.chikies = new EntityManager(31, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "chiky.png", 0, 0, 1, 1)));
		this.airgums = new EntityManager(100, new Airgum(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "airgum.png", 65, 0, 1, 1)));
		this.feathers = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "chiky.png", 130, 0, 1, 1)));

		this.init();
	}

	public void init() {
		this.airgums.clear();
		this.chikies.clear();
		this.generateChikies(30); // TODO: Change count depending to level number.

		this.feathers.clear();

		Options.scalePower = 20; // TODO: Change count of scale power.
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies(final int count) {
		Chiky chiky;
		for (int i = 0; i < count; i++) {
			chiky = (Chiky) this.chikies.create();
			chiky.setOffsetTime(Options.PI / count * 10 * i); // TODO: Set step between chikies on screen.
		}
	}

	private void checkCollision() {
		Chiky chiky;
		Airgum airgum;
		for (int i = this.chikies.getCount() - 1; i >= 0; --i) {
			chiky = (Chiky) this.chikies.getByIndex(i);
			if (!chiky.getIsNeedToFlyAway()) {
				for (int j = this.airgums.getCount(); j >= 0; --j) {
					airgum = (Airgum) this.airgums.getByIndex(j);
					if (this.isCollide(chiky, airgum)) {
						chiky.setIsNeedToFlyAway(airgum.getScaleX() * 0.75f);
						// TODO: (R) Maybe later it will be needed.
						// chiky.destroy();
						// airgum.destroy();
						break;
					}
				}
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < d * d;
	}

	public void update() {
		this.checkCollision();

		Options.time++;

		// TODO: Experiment.
		final float levelTime = 60;
		if (Options.time > 60 * levelTime || this.chikies.getCount() == 0) {
			Options.time = 0;
			Options.levelNumber++;
			Game.world.init();
		}
	}
}
