package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class LoadingScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload_screen.png", 0, 0, 1, 1), false) {

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

	private final static Entity mProgressBar = new Entity(228, 421, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "", 0, 0, 1, 1), false) {

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

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public LoadingScreen() {
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		clearUpdateHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}