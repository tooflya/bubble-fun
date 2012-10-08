package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Entity;

public class PreloaderScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "bg_level.png", 0, 0, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadResources() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unloadResources() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}
