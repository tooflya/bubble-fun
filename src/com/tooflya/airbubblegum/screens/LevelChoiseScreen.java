package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Cloud;
import com.tooflya.airbubblegum.entities.Entity;
import com.tooflya.airbubblegum.entities.LevelIcon;
import com.tooflya.airbubblegum.managers.CloudsManager;
import com.tooflya.airbubblegum.managers.LevelsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelChoiseScreen extends Screen {

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

	// ===========================================================
	// Fields
	// ===========================================================

	private LevelsManager levels;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelChoiseScreen() {
		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#init()
	 */
	@Override
	public void init() {
		this.setBackground(new SpriteBackground(mBackground));

		this.levels = new LevelsManager(20, new LevelIcon(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "level_button_2star.png", 580, 0, 1, 1), Screen.CHOISE));
		this.levels.generate();
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
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas);
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