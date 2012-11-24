package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Sprite;

/**
 * @author Tooflya.com
 * @since
 */
public class CreditsScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public static final BitmapTextureAtlas mCommonTextureAtlas = new BitmapTextureAtlas(256, 64, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	/** Declare the entity that acts as a background image of the screen. */
	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "sb.png", 0, 0, 1, 1), this);

	private final Sprite mTopPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "lvl-panel.png", 630, 900, 1, 1), this.mBackground);

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelChoiseScreen.mBackgroundTextureAtlas, Game.context, "back-btn.png", 100, 900, 1, 1), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MORE);
		}
	};

	private final Rectangle mBaseRectangle = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight);
	private final Rectangle mFrontRectangle = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight);

	private final Sprite mCredits = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mCommonTextureAtlas, Game.context, "logo_small_zero.png", 0, 0, 1, 1), this.mFrontRectangle);

	// ===========================================================
	// Constructors
	// ===========================================================

	public CreditsScreen() {
		this.loadResources();

		this.mBackground.create().setBackgroundCenterPosition();

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBaseRectangle.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mFrontRectangle.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		this.mBaseRectangle.setAlpha(0f);
		this.mFrontRectangle.setAlpha(0f);

		this.attachChild(this.mBaseRectangle);

		this.mBaseRectangle.attachChild(this.mFrontRectangle);

		this.mCredits.create().setCenterPosition(this.mFrontRectangle.getWidth() / 2, 50);
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
		Game.screens.set(Screen.MORE);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}