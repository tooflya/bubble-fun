package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.EntityManager;

public class PauseScreen extends Screen {

	// ===========================================================
	// Fields
	// ===========================================================

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(256, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final Rectangle mBackground = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	private final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "menu-big-btn.png", 0, 256, 1, 2);

	private final ButtonScaleable b1 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			Game.screens.get(Screen.LEVEL).clearChildScene();
		}
	};

	private final ButtonScaleable b2 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;

			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();
			Game.screens.get(Screen.LEVEL).clearChildScene();
		}
	};

	private final ButtonScaleable b3 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			PreloaderScreen.mChangeAction = 2;
			Game.screens.set(Screen.LOAD);
		}
	};

	private final ButtonScaleable b4 = new ButtonScaleable(mButtonsTextureRegion, this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			PreloaderScreen.mChangeAction = 1;
			Game.screens.set(Screen.LOAD);
		}
	};

	private final EntityManager mLables = new EntityManager(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "text-big-btn.png", 0, 0, 1, 4)));

	// ===========================================================
	// Constructors
	// ===========================================================

	public PauseScreen() {
		this.loadResources();

		this.setBackgroundEnabled(false);

		this.b1.create().setCenterPosition(Options.cameraOriginRatioCenterX, Options.cameraOriginRatioCenterY - 120f);
		this.b2.create().setCenterPosition(Options.cameraOriginRatioCenterX, Options.cameraOriginRatioCenterY - 40f);
		this.b3.create().setCenterPosition(Options.cameraOriginRatioCenterX, Options.cameraOriginRatioCenterY + 40f);
		this.b4.create().setCenterPosition(Options.cameraOriginRatioCenterX, Options.cameraOriginRatioCenterY + 120f);

		Entity sprite;

		sprite = mLables.create();
		sprite.setCenterPosition(this.b1.getWidth() / 2, this.b1.getHeight() / 2);
		sprite.setCurrentTileIndex(0);
		this.b1.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b2.getWidth() / 2, this.b2.getHeight() / 2);
		sprite.setCurrentTileIndex(1);
		this.b2.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b3.getWidth() / 2, this.b3.getHeight() / 2);
		sprite.setCurrentTileIndex(2);
		this.b3.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b4.getWidth() / 2, this.b4.getHeight() / 2);
		sprite.setCurrentTileIndex(3);
		this.b4.attachChild(sprite);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraOriginRatioX, Options.cameraOriginRatioY);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0.7f);

		this.attachChild(coloredRect);

		return coloredRect;
	}

	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);

	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);

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
