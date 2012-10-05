package com.tooflya.airbubblegum;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.entities.Chiky;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.managers.EntityManager;

public class World extends org.anddev.andengine.entity.Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas texture;

	private EntityManager chikies;
	private EntityManager airgums;

	// ===========================================================
	// Constructors
	// ===========================================================

	public World() {
		super();

		Game.screens.get(Screen.LEVEL).attachChild(this);

		this.texture = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Game.loadTextures(texture);

		this.chikies = new EntityManager(10, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "chiky.png", 0, 0, 1, 1)));
		this.airgums = new EntityManager(10, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "airgum.png", 0, 0, 1, 1)));

		this.init();
	}

	public void init() {
		this.chikies.clear();
		this.generateChikies(10); // TODO: Change count depending to level number.
		this.airgums.clear();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies(final int count) {
		Chiky chiky;
		for (int i = 0; i < count; i++) {
			chiky = (Chiky) this.chikies.create();
			chiky.setOffsetTime(10 * i); // TODO: Set step between chikies on screen.
		}
	}

	private void checkCollision() {
		// TODO: Add code.
	}

	private boolean isCollide(Entity upEntity, Entity downEntity) {
		// TODO: Add code of circles collision.
		return false;
	}

	public void update() {
		// Add code of update chikies.
	}
}
