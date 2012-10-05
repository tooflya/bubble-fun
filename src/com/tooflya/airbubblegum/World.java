package com.tooflya.airbubblegum;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.entities.Airgum;
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

		this.chikies = new EntityManager(100, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "chiky.png", 0, 0, 1, 1)));
		this.airgums = new EntityManager(10, new Airgum(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texture, Game.context, "airgum.png", 0, 0, 1, 1)));

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
			chiky.setOffsetTime(Options.PI / count * 10 * i); // TODO: Set step between chikies on screen.
		}
	}

	private void checkCollision() {
		Chiky chiky;
		Airgum airgum;
		for (int i = 0; i < this.chikies.getCount(); i++) {
			chiky = (Chiky)this.chikies.getByIndex(i);
			for (int j = 0; j < this.airgums.getCount(); j++) {
				airgum = (Airgum)this.airgums.getByIndex(j);
				if (this.isCollide(chiky, airgum)) {
					chiky.destroy();
					airgum.destroy();
					i--;
					break;
				}
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidthScaled() + entity1.getWidthScaled();
		return x * x + y * y < d * d;
	}

	public void update() {
		// Add code of update chikies.
	}
}
