package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.entities.Sprite;

/**
 * @author Tooflya.com
 * @since
 */
public class SplashScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** Declare the necessary canvas in graphics memory, which then will be used to download images. */
	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	/** Declare the entity that acts as a background image of the screen. */
	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload-screen.png", 0, 0, 1, 1), this);

	/** Set the timer, which will change the size of the loading bar, depending on the load time. */
	private final TimerHandler mTimer = new TimerHandler(2f, true, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			Game.engine.setScene(new LoadingScreen());
			onDetached();
		}
	});

	// ===========================================================
	// Constructors
	// ===========================================================

	public SplashScreen() {
		this.loadResources();

		this.setBackground(new ColorBackground(1f, 1f, 1f, 1f));

		this.mBackground.create().setBackgroundCenterPosition();

		/** Register timer of loading progressbar changes */
		this.registerUpdateHandler(mTimer);
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
		this.unloadResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);
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

}