package com.tooflya.bubblefun.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Button;
import com.tooflya.bubblefun.entities.Sprite;

/**
 * @author Tooflya.com
 * @since
 */
public class ExitScreen extends PopupScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "popup-exit.png", 0, 0, 1, 1), this);

	private final Button mYIcon = new Button(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "accept-btn.png", 350, 0, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.close();
		}
	};

	private final Button mNIcon = new Button(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "decline-btn.png", 350, 150, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public ExitScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mYIcon.create();
		this.mYIcon.setScaleCenter(this.mYIcon.getWidth() / 2, this.mYIcon.getHeight() / 2);
		this.mYIcon.setCenterPosition(70, this.mPanel.getHeight() - 5f);

		this.mNIcon.create();
		this.mNIcon.setScaleCenter(this.mNIcon.getWidth() / 2, this.mNIcon.getHeight() / 2);
		this.mNIcon.setCenterPosition(this.mPanel.getWidth() - 70, this.mPanel.getHeight() - 5f);

		this.registerTouchArea(this.mYIcon);
		this.registerTouchArea(this.mNIcon);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
	}

	@Override
	public void onBackPressed() {
	}

}
