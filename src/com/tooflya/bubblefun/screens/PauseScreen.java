package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.EntityManager;

public class PauseScreen extends Screen {

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(256, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	@SuppressWarnings("unused")
	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	private final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/menu-big-btn.png", 0, 256, 1, 2);

	private final Sprite b1 = new Sprite(mButtonsTextureRegion, this, true) {

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

				Game.screens.get(Screen.LEVEL).clearChildScene();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private final Sprite b2 = new Sprite(mButtonsTextureRegion, this, true) {

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
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private final Sprite b3 = new Sprite(mButtonsTextureRegion, this, true) {

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

				PreloaderScreen.mChangeAction = 2;
				Game.screens.set(Screen.LOAD);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private final Sprite b4 = new Sprite(mButtonsTextureRegion, this, true) {

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

				PreloaderScreen.mChangeAction = 1;
				Game.screens.set(Screen.LOAD);
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		}
	};

	private final EntityManager mLables = new EntityManager(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/text-big-btn.png", 0, 0, 1, 4), this));

	// ===========================================================
	// Constructors
	// ===========================================================

	public PauseScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.b1.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 120 * Options.cameraRatioFactor);
		this.b2.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40 * Options.cameraRatioFactor);
		this.b3.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 40 * Options.cameraRatioFactor);
		this.b4.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 120 * Options.cameraRatioFactor);

		Entity sprite;

		sprite = mLables.create();
		sprite.setCenterPosition(this.b1.getCenterX(), this.b1.getCenterY());
		sprite.setCurrentTileIndex(0);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b2.getCenterX(), this.b2.getCenterY());
		sprite.setCurrentTileIndex(1);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b3.getCenterX(), this.b3.getCenterY());
		sprite.setCurrentTileIndex(2);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b4.getCenterX(), this.b4.getCenterY());
		sprite.setCurrentTileIndex(3);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		Game.screens.get(Screen.LEVEL).clearChildScene();
	}
}
