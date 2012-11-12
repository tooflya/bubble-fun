package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxScreen extends Screen implements IOnSceneTouchListener, IScrollDetectorListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================

	protected static int FONT_SIZE = 28;
	protected static int PADDING = 50;

	protected static int MENUITEMS = 4;

	private final BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================

	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private boolean mPostScroll = false;

	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "lb.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(Screen.cloudTextureRegion, this.mBackground));

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "back-btn.png", 100, 900, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MENU);
		}
	};

	private final Rectangle rectangle = new Rectangle(0, 0, Options.cameraWidth * MENUITEMS, Options.cameraHeight);

	private final EntityManager<Sprite> mPoints = new EntityManager<Sprite>(MENUITEMS, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "navi.png", 400, 900, 1, 2), this.mBackground));

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen() {
		this.loadResources();

		this.clouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBackground.attachChild(rectangle);
		this.rectangle.setAlpha(0);

		final TiledTextureRegion textureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "box.png", 0, 0, 2, 3);

		for (int i = 0; i < MENUITEMS; i++) {
			final int bi = i;
			final ButtonScaleable sprite = new ButtonScaleable(textureRegion, this.rectangle) {

				@Override
				public void onClick() {
					if (mPostScroll)
						return;
					
					if(bi > 0) return;
					Options.boxNumber = bi;
					Game.screens.set(Screen.CHOISE);
				}
			};
			sprite.create().setCenterPosition(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, Options.cameraCenterY);
			sprite.setCurrentTileIndex(0);

			if(bi > 0) sprite.setCurrentTileIndex(4);
		}

		mPoints.create().setCenterPosition(Options.cameraCenterX - 60, Options.cameraCenterY + 200f);
		mPoints.create().setCenterPosition(Options.cameraCenterX - 20, Options.cameraCenterY + 200f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 20, Options.cameraCenterY + 200f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 60, Options.cameraCenterY + 200f);

		mPoints.getByIndex(0).setCurrentTileIndex(1);

		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);

		this.setOnSceneTouchListener(this);
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

		if (this.mPostScroll) {
			this.rectangle.setPosition(this.rectangle.getX() + sx, 0);

			if (sx > 0) {

				if (sx <= 0) {
					this.mPostScroll = false;
				}
			}

			if (sx < 0) {

				if (sx >= 0) {
					this.mPostScroll = false;
				}
			}

			if (this.rectangle.getX() > 5 || this.rectangle.getX() < -254 * 3.5f) {
				if (this.rectangle.getX() > 5) {
					sx = -Math.abs(sx);
				} else if (this.rectangle.getX() < -5) {
					sx = +Math.abs(sx);
				}
			}
			else {
				if (Math.abs(this.rectangle.getX() % 254) <= 10) {
					this.mPostScroll = false;

					for (int i = mPoints.getCount() - 1; i >= 0; i--) {
						mPoints.getByIndex(i).setCurrentTileIndex(0);
					}

					try {
						mPoints.getByIndex((int) Math.abs(FloatMath.floor(this.rectangle.getX() / 254)) - 1).setCurrentTileIndex(1);
					} catch (ArrayIndexOutOfBoundsException e) {
						mPoints.getByIndex(0).setCurrentTileIndex(1);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas1, this.mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas1, this.mBackgroundTextureAtlas2);
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

	@Override
	public void onClick(ClickDetector arg0, TouchEvent arg1) {
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {

		this.mClickDetector.onTouchEvent(pSceneTouchEvent);
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return false;
	}

	private float sx;

	@Override
	public void onScroll(ScrollDetector arg0, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
		if (pTouchEvent.isActionMove()) {
			sx = pDistanceX > 0 ? 8 : -8;

			if (this.rectangle.getX() < Options.cameraCenterX / 2 && this.rectangle.getX() > -254 * 3.2f)
				this.rectangle.setPosition(this.rectangle.getX() + pDistanceX / 2, 0);
		} else if (pTouchEvent.isActionUp()) {
			this.mPostScroll = true;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}