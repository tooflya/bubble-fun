package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.background.AsyncTaskLoader;
import com.tooflya.bubblefun.background.IAsyncCallback;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.BubblesManager;

public class PreloaderScreen extends Screen implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mChangeAction = 0;

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public final Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/preload-bg.png", 0, 0, 1, 1), this) {

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

	public final Entity mBalon = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, Options.CR + "/preload-bg-fill.png", 0, 612, 1, 1), this) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Entity#create()
		 */
		@Override
		public Entity create() {
			this.setHeight(1);
			this.getTextureRegion().setHeight(1);

			return super.create();
		}

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

	private final TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			/** Changing size of progressbar */
			if (mBalon.getHeightScaled() < mBalon.getBaseHeight() * Options.cameraRatioFactor) {
				mBalon.getTextureRegion().setHeight((int) (mBalon.getTextureRegion().getHeight() + 3));
				mBalon.setHeight(mBalon.getHeight() + 3);
			} else {
				if (true) {
					if (mChangeAction == 0) {
						Game.screens.set(Screen.LEVEL);
					} else if (mChangeAction == 1) {
						Game.screens.set(Screen.MENU);
					}
					else {
						Game.screens.set(Screen.CHOISE);
					}
				}
			}
		}
	});

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public PreloaderScreen() {
		this.loadResources();

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mBalon.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - (mBalon.getBaseHeight() * Options.cameraRatioFactor) / 2);
	}

	@Override
	public void onAttached() {
		super.onAttached();

		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);

		mBalon.create();

		/** Start background loader */
		new AsyncTaskLoader().execute(this);
	}

	@Override
	public void onDetached() {
		super.onDetached();

		/** Register timer of loading progressbar changes */
		this.unregisterUpdateHandler(mTimer);
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

	@Override
	public void workToDo() {
		if (mChangeAction == 0) {
			Game.screens.get(Screen.MENU).unloadResources();
			Game.screens.get(Screen.CHOISE).unloadResources();
			Game.screens.get(Screen.LEVEL).loadResources();
			Game.screens.get(Screen.LEVELEND).loadResources();
		} else {
			Game.screens.get(Screen.LEVEL).unloadResources();
			Game.screens.get(Screen.LEVELEND).unloadResources();
			Game.screens.get(Screen.MENU).loadResources();
			Game.screens.get(Screen.CHOISE).loadResources();
		}
	}

	@Override
	public void onComplete() {
	}

}
