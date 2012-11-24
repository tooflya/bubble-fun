package com.tooflya.bubblefun.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class MoreScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public static final BitmapTextureAtlas mCommonTextureAtlas = new BitmapTextureAtlas(256, 64, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	/** Declare the entity that acts as a background image of the screen. */
	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "sb.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(Screen.cloudTextureRegion, this.mBackground));

	private final Sprite mTopPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "lvl-panel.png", 630, 900, 1, 1), this.mBackground);

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "back-btn.png", 100, 900, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MENU);
		}
	};

	private final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mCommonTextureAtlas, Game.context, "menu-big-btn.png", 0, 0, 1, 1);

	private final ButtonScaleable b1 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
		}
	};

	private final ButtonScaleable b2 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.CREDITS);
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public MoreScreen() {
		this.loadResources();

		this.clouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.b1.create().setCenterPosition(this.mBackground.getWidth() / 2, this.mBackground.getHeight() / 2 - 30f);
		this.b2.create().setCenterPosition(this.mBackground.getWidth() / 2, this.mBackground.getHeight() / 2 + 30f);
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

		this.clearUpdateHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mCommonTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mCommonTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.screens.set(Screen.MENU);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}