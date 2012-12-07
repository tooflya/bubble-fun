package com.tooflya.bubblefun.screens;

import java.util.ArrayList;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
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
import com.tooflya.bubblefun.entities.BoxLabel;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxScreen extends ReflectionScreen implements IOnSceneTouchListener, IScrollDetectorListener, IClickDetectorListener {

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

	private final EntityManager<Sprite> mPoints, mPoints2;

	private Sprite mTopPanel;

	private ArrayList<Box> boxes = new ArrayList<Box>();
	private ArrayList<BoxLabel> labels = new ArrayList<BoxLabel>();
	private ArrayList<Sprite> locks = new ArrayList<Sprite>();

	private boolean mTimeToUnlockBox;

	private Sprite mTotalScoreText;
	private final EntityManager<Sprite> mTotalScoreCountText;

	private int totalscore = 0;

	private int SC = 0;

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

		this.mTotalScoreText = new Sprite(Resources.mLevelEndTotalScoreTextTextureRegion, this.mBackground);

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
		mPoints2 = new EntityManager<Sprite>(MENUITEMS, new Sprite(Resources.mBoxesNavigationTextureRegion, this.mBackground));

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

			BoxLabel picture1 = null;

			if (bi == 0) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 150, Resources.mBoxesLabel1TextureRegion, this.rectangle).create();
				labels.add(picture1);
			} else if (bi == 1) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 150, Resources.mBoxesLabel2TextureRegion, this.rectangle).create();
				labels.add(picture1);
			} else if (bi == 2) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 150, Resources.mBoxesLabel3TextureRegion, this.rectangle).create();
				labels.add(picture1);
			}

			final Box sprite = new Box(Resources.mBoxesTextureRegion, this.rectangle) {

				@Override
				public void onClick() {
					if (mPostScroll)
						return;

					if (!this.prepare()) {
						boxes.get(bi).animation();

						Game.screens.get(Screen.BOX).setChildScene(Game.screens.get(Screen.BL), false, false, true);
						Game.screens.get(Screen.BL).onAttached();

						return;
					}
					Options.boxNumber = bi;
					Game.screens.set(Screen.CHOISE);
				}

				@Override
				protected boolean prepare() {
					return Game.db.getBox(bi + 1).isOpen();
				}
			};

			sprite.create().setCenterPosition(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, Options.cameraCenterY);
			sprite.setCurrentTileIndex(0);
			sprite.enableBlendFunction();

			Sprite picture = null;
			if (bi == 0) {
				picture = (Sprite) new Sprite(Resources.mBoxesPicture1TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
			} else if (bi == 1) {
				picture = (Sprite) new Sprite(Resources.mBoxesPicture2TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
			} else if (bi == 2) {
				picture = (Sprite) new Sprite(Resources.mBoxesPicture3TextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
			}
			else if (bi == 3) {
				picture = (Sprite) new Sprite(Resources.mBoxesComingSoonTextureRegion, sprite).create();
				picture.setCenterPosition(sprite.getWidth() / 2, sprite.getHeight() / 2);
			}

			if (!Game.db.getBox(bi + 1).isOpen() && bi != 3) {
				final Sprite chain = (Sprite) new Sprite(Resources.mChainTextureRegion, picture).create();
				chain.setCenterPosition(picture.getWidth() / 2 + 5f, picture.getHeight() / 2 - 8f);
				//chain.enableBlendFunction();

				final Sprite lock = (Sprite) new Sprite(Resources.mBoxesLockTextureRegion, picture).create();
				lock.setCenterPosition(picture.getWidth() / 2, picture.getHeight() / 2 + 14f);
				//lock.enableBlendFunction();

				locks.add(bi, lock);

				if (bi == 1) {
					Sprite l = new Sprite(Resources.mLockStars1TextureRegion, lock);
					l.setCenterPosition(lock.getWidth() / 2, lock.getHeight() / 2 + 17f);
					l.create();
				} else if (bi == 2) {
					Sprite l = new Sprite(Resources.mLockStars2TextureRegion, lock);
					l.setCenterPosition(lock.getWidth() / 2, lock.getHeight() / 2 + 17f);
					l.create();
				}
			} else {
				locks.add(bi, null);
			}

			boxes.add(sprite);
		}

		int i = -1;
		for (BoxLabel e : labels) {
			i++;
			if (i == 0)
				continue;
			e.down();
		}

		mPoints.create().setCenterPosition(Options.cameraCenterX - 60, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX - 20, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 20, Options.cameraCenterY + 205f);
		mPoints.create().setCenterPosition(Options.cameraCenterX + 60, Options.cameraCenterY + 205f);

		Entity point;
		point = mPoints2.create();
		point.setCenterPosition(Options.cameraCenterX - 60, Options.cameraCenterY + 205f);
		point.setCurrentTileIndex(1);
		point.setScaleCenter(point.getWidth() / 2, point.getHeight() / 2);

		point = mPoints2.create();
		point.setCenterPosition(Options.cameraCenterX - 20, Options.cameraCenterY + 205f);
		point.setCurrentTileIndex(1);
		point.setScaleCenter(point.getWidth() / 2, point.getHeight() / 2);
		point.setScale(0f);

		point = mPoints2.create();
		point.setCenterPosition(Options.cameraCenterX + 20, Options.cameraCenterY + 205f);
		point.setCurrentTileIndex(1);
		point.setScaleCenter(point.getWidth() / 2, point.getHeight() / 2);
		point.setScale(0f);

		point = mPoints2.create();
		point.setCenterPosition(Options.cameraCenterX + 60, Options.cameraCenterY + 205f);
		point.setCurrentTileIndex(1);
		point.setScaleCenter(point.getWidth() / 2, point.getHeight() / 2);
		point.setScale(0f);

		for (int j = 0; j < mPoints2.getCount(); j++) {
			mPoints.getByIndex(j).setScaleCenter(mPoints.getByIndex(j).getWidth() / 2, mPoints.getByIndex(j).getHeight() / 2);
		}

		this.mTotalScoreCountText = new EntityManager<Sprite>(10, new Sprite(Resources.mLevelEndScoreNumbersTextureRegion, this.mBackground));

		for (int a = 0; a < 5; a++) {
			this.mTotalScoreCountText.create().setPosition(Options.cameraWidth - 100f + 18f * a, 8f);
		}

		this.mTotalScoreText.create().setCenterPosition(this.mTotalScoreCountText.getByIndex(0).getCenterX() - this.mTotalScoreText.getWidth() / 2 - 10f, this.mTotalScoreCountText.getByIndex(0).getCenterY());

		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);

		this.setOnSceneTouchListener(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onAttached() {
		super.onAttached();

		SC = Game.db.getTotalCore();
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
		if (ScreenManager.mChangeAction == 3) {
			this.rectangle.setPosition(this.rectangle.getX() - 10f, 0);
			sx = -8;

			this.mPostScroll = true;
			this.mTimeToUnlockBox = true;
		}

		if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
			Options.mMainSound.play();
		}
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

					int G = ((int) Math.abs(FloatMath.floor(this.rectangle.getX() / 254)) - 1);

					try {
						boxes.get(G).animation();
						labels.get(G).up();
					} catch (ArrayIndexOutOfBoundsException e) {
						boxes.get(0).animation();
						labels.get(0).up();
						G = 0;
					} catch (IndexOutOfBoundsException e) {

					}

					final int A = G;

					try {
						if (this.mTimeToUnlockBox) {
							this.mTimeToUnlockBox = false;

							final ScaleModifier lockAnimationOutScale = new ScaleModifier(1f, 1f, 2f);
							final AlphaModifier lockAnimationOutAlpha = new AlphaModifier(1f, 1f, 0f) {
								@Override
								public void onFinished() {
									Game.db.updateBox(A + 1, 1);
									locks.get(A).destroy();
								}
							};

							locks.get(G).registerEntityModifier(lockAnimationOutScale);
							locks.get(G).registerEntityModifier(lockAnimationOutAlpha);

							lockAnimationOutScale.reset();
							lockAnimationOutAlpha.reset();
						}
					} catch (NullPointerException ex) {
					}

					int y = -1;
					for (BoxLabel e : labels) {
						y++;
						if (G != y)
							e.down();
					}
				}
			}
		}

		int y = -1;
		for (Box box : boxes) {
			y++;

			final float x = box.getX() + box.getParent().getX();
			final float alpha = Math.max(0f, 66 / Math.abs(x));

			float scale = Math.min(1f, 66 / Math.abs(x));
			scale = scale < 0.4f ? 0f : scale;

			box.setAlpha(alpha);

			mPoints2.getByIndex(y).setScale(scale);
		}

		/* TOTAL Score */
		if (totalscore < SC) {

			if (SC - totalscore > 100) {
				totalscore += 111;
			} else if (SC - totalscore > 10) {
				totalscore += 11;
			} else {
				totalscore++;
			}
		}

		if (totalscore < 10) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex(totalscore);
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(false);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 100) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 10));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 1000) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 100));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((totalscore - FloatMath.floor(totalscore / 100) * 100) / 10));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 10000) {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 1000));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 1000) * 1000)) / 100)));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 100) * 100)) / 10)));
			this.mTotalScoreCountText.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(true);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else {
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 10000));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 10000) * 10000)) / 1000)));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 1000) * 1000)) / 100)));
			this.mTotalScoreCountText.getByIndex(3).setCurrentTileIndex((int) FloatMath.floor(((totalscore - ((int) FloatMath.floor(totalscore / 100) * 100)) / 10)));
			this.mTotalScoreCountText.getByIndex(4).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(true);
			this.mTotalScoreCountText.getByIndex(4).setVisible(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (this.hasChildScene()) {
			Game.screens.get(Screen.BL).onDetached();
		} else {
			Game.screens.set(Screen.MENU);
		}
	}

	@Override
	public void onClick(ClickDetector arg0, TouchEvent arg1) {
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {

		this.mClickDetector.onTouchEvent(pSceneTouchEvent);
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);

		return true;
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