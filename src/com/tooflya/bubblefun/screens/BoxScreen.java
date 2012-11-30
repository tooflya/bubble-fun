package com.tooflya.bubblefun.screens;

import java.util.ArrayList;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Box;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxScreen extends ReflectionScreen implements IScrollDetectorListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================

	protected static int FONT_SIZE = 28;
	protected static int PADDING = 50;

	protected static int MENUITEMS = 4;

	// ===========================================================
	// Fields
	// ===========================================================

	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private boolean mPostScroll = false;

	private final ButtonScaleable mBackButton;

	private final Rectangle rectangle = new Rectangle(0, 0, Options.cameraWidth * MENUITEMS, Options.cameraHeight);

	private final EntityManager<Sprite> mPoints;

	private Sprite mTopPanel;

	private ArrayList<Box> boxes = new ArrayList<Box>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses2.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mTopPanel = new Sprite(Resources.mTopPanelTextureRegion, this.mBackground);
		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MENU);
			}
		};

		mPoints = new EntityManager<Sprite>(MENUITEMS, new Sprite(Resources.mBoxesNavigationTextureRegion, this.mBackground));

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBackground.attachChild(rectangle);
		this.rectangle.setAlpha(0);

		for (int i = 0; i < MENUITEMS; i++) {
			final int bi = i;
			final Box sprite = new Box(Resources.mBoxesTextureRegion, this.rectangle) {

				@Override
				public void onClick() {
					if (mPostScroll)
						return;

					if (bi > 2) {
						boxes.get(bi).animation();
						return;
					}
					Options.boxNumber = bi;
					Game.screens.set(Screen.CHOISE);
				}
			};
			sprite.create().setCenterPosition(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, Options.cameraCenterY);
			sprite.setCurrentTileIndex(0);

			if (bi != 3) {

				final Sprite lock = (Sprite) new Sprite(Resources.mBoxesTextureRegion, sprite).create();
				lock.setCurrentTileIndex(2);
				lock.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
			}

			if (bi == 0) {
				Sprite picture = (Sprite) new Sprite(Resources.mBoxesPicture1TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);

				picture = (Sprite) new Sprite(Resources.mBoxesLabel1TextureRegion, this.rectangle).create();
				picture.setCenterPosition(sprite.getX() + sprite.getWidth() / 2, 170);
			} else if (bi == 1) {
				Sprite picture = (Sprite) new Sprite(Resources.mBoxesPicture2TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);

				picture = (Sprite) new Sprite(Resources.mBoxesLabel2TextureRegion, this.rectangle).create();
				picture.setCenterPosition(sprite.getX() + sprite.getWidth() / 2, 170);
			} else if (bi == 2) {
				Sprite picture = (Sprite) new Sprite(Resources.mBoxesPicture3TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);

				picture = (Sprite) new Sprite(Resources.mBoxesLabel3TextureRegion, this.rectangle).create();
				picture.setCenterPosition(sprite.getX() + sprite.getWidth() / 2, 170);
			}
			else if (bi == 3) {
				final Sprite picture = (Sprite) new Sprite(Resources.mBoxesComingSoonTextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
				sprite.setCurrentTileIndex(4);
			}

			boxes.add(sprite);
		}

		mPoints.create().setCenterPosition(Options.cameraCenterX - 60, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX - 20, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 20, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 60, Options.cameraCenterY + 205f);

		mPoints.getByIndex(0).setCurrentTileIndex(1);

		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

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
						for (Box box : boxes) {
							//if (box != boxes.get((int) Math.abs(FloatMath.floor(this.rectangle.getX() / 254)) - 1))
							//box.modifier5.reset();
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						for (Box box : boxes) {
							//if (box != boxes.get(0))
							//box.modifier5.reset();
						}
					}

					try {
						mPoints.getByIndex((int) Math.abs(FloatMath.floor(this.rectangle.getX() / 254)) - 1).setCurrentTileIndex(1);
						boxes.get((int) Math.abs(FloatMath.floor(this.rectangle.getX() / 254)) - 1).animation();
					} catch (ArrayIndexOutOfBoundsException e) {
						mPoints.getByIndex(0).setCurrentTileIndex(1);
						boxes.get(0).animation();
					}
				}
			}
		}
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

		return super.onSceneTouchEvent(arg0, pSceneTouchEvent);
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