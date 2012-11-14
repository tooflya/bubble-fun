package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Screen extends Scene implements IScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final BitmapTextureAtlas mCommonTextureAtlas = new BitmapTextureAtlas(256, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mCommonTextureAtlas2 = new BitmapTextureAtlas(512, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static TiledTextureRegion cloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mCommonTextureAtlas, Game.context, "cloud.png", 0, 0, 1, 4);

	public static final int SCREENS_COUNT = 8;

	public static final int NO_SCREEN = -1;

	public static final int MENU = 0;
	public static final int CHOISE = 1;
	public static final int LEVEL = 2;
	public static final int LEVELEND = 3;
	public static final int EXIT = 4;
	public static final int PAUSE = 5;
	public static final int BOX = 7;

	// ===========================================================
	// Fields
	// ===========================================================

	public static int screen = -1;

	private float mDeltaTiming = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		this.setIgnoreUpdate(false);
		this.setChildrenIgnoreUpdate(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		this.setIgnoreUpdate(true);
		this.setChildrenIgnoreUpdate(true);
	}

	@Override
	public void attachChild(final IEntity pEntity) {
		super.attachChild(pEntity);

		/**
		 * This section is scale object to the real size for adapt size of
		 * entity to the screen resolution.
		 */
		pEntity.setScaleCenter(0, 0);
		pEntity.setScale(Options.cameraRatioFactor);

		/** After scale action we need to find center of entity position. */
		if (((Shape) pEntity).getWidthScaled() > Options.cameraWidth || ((Shape) pEntity).getHeightScaled() > Options.cameraHeight) {
			pEntity.setPosition((Options.screenWidth - ((Shape) pEntity).getWidthScaled()) / 2, (Options.screenHeight - ((Shape) pEntity).getHeightScaled()) / 2);
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.mDeltaTiming += pSecondsElapsed;
		if (this.mDeltaTiming < Options.framesPerSeconds) {
			return;
		} else {
			super.onManagedUpdate(Options.framesPerSeconds);
			this.mDeltaTiming -= Options.framesPerSeconds;
			while (this.mDeltaTiming >= Options.framesPerSeconds) {
				super.onManagedUpdate(Options.framesPerSeconds);
				this.mDeltaTiming -= Options.framesPerSeconds;
			}
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setScene(final Engine pEngine) {
		pEngine.setScene(this);
	}
}
