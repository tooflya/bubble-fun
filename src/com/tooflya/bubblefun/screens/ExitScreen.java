package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class ExitScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	@SuppressWarnings("unused")
	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	private final Entity mPanel = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/popup-exit.png", 0, 0, 1, 1), this) {
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

	private final Entity mYIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/accept-btn.png", 0, 150, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				Game.close();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};
	private final Entity mNIcon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/decline-btn.png", 55, 150, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {

			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				Game.screens.get(Screen.MENU).clearChildScene();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.airbubblegum.entities.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	public ExitScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.mPanel.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);

		this.mYIcon.create().setCenterPosition(Options.cameraCenterX - 70 * Options.cameraRatioFactor, Options.cameraCenterY + 70 * Options.cameraRatioFactor);
		this.mNIcon.create().setCenterPosition(Options.cameraCenterX + 70 * Options.cameraRatioFactor, Options.cameraCenterY + 70 * Options.cameraRatioFactor);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0.7f);

		this.attachChild(coloredRect);

		return coloredRect;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

}
