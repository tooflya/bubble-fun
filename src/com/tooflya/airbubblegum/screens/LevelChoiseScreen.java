package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Cloud;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelChoiseScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main_menu_bg.png", 0, 0, 1, 1), false) {

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

	private CloudsManager clouds;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelChoiseScreen() {
		Game.loadTextures(mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
	}

	public void init() {
		this.attachChild(mBackground);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "clouds.png", 0, 0, 1, 4), Screen.CHOISE));
		this.clouds.generateStartClouds();
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.clouds.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.screens.set(Screen.MENU);

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}