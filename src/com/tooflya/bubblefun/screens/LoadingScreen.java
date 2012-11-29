package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.util.user.AsyncTaskLoader;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.entities.Sprite;

/**
 * @author Tooflya.com
 * @since
 */
public class LoadingScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** Declare the necessary canvas in graphics memory, which then will be used to download images. */
	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	/** Declare the entity that acts as a background image of the screen. */
	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload-screen-second.png", 0, 0, 1, 1), this);

	/** Declare the entity that acts as a loading bar. */
	private final Sprite mProgressBar = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload-screen-fill.png", 0, 660, 1, 1), this.mBackground);

	/** Set the timer, which will change the size of the loading bar, depending on the load time. */
	private final TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			/** Changing size of progressbar. */
			if (mProgressBar.getWidthScaled() < mProgressBar.getBaseWidth()) {
				mProgressBar.getTextureRegion().setWidth(mProgressBar.getTextureRegion().getWidth() + 3);
				mProgressBar.setWidth(mProgressBar.getWidth() + 3);
			} else {
				/** If progressbar is full. */
				Game.isGameLoaded = true;
			}
		}
	});

	// ===========================================================
	// Constructors
	// ===========================================================

	public LoadingScreen() {
		this.loadResources();

		this.setBackground(new ColorBackground(1f, 1f, 1f, 1f));

		this.mBackground.create().setBackgroundCenterPosition();

		this.mProgressBar.create().setCenterPosition(188f, 496f);
		this.mProgressBar.setWidth(1);
		this.mProgressBar.getTextureRegion().setWidth(1);

		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();
	}

	@Override
	public void onPostAttached() {

		Game.instance.runOnUiThread(new Runnable() {
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				/** Start background loader */
				new AsyncTaskLoader().execute((Game) Game.instance);
			}

		});
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
		this.unloadResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
	}

}