package com.tooflya.bubblefun.screens;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

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
import com.tooflya.bubblefun.entities.BoxUnlockPanel;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
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

	public static int BOX_INDEX;

	// ===========================================================
	// Fields
	// ===========================================================

	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private boolean mPostScroll = false;

	private final ButtonScaleable mBackButton;

	private final Rectangle rectangle = new Rectangle(0, 0, Options.cameraWidth * MENUITEMS, Options.cameraHeight);
	private final Rectangle mScoreHolder;
	private final EntityManager<Entity> mPoints, mPoints2;

	private Entity mTopPanel;

	private ArrayList<Box> boxes = new ArrayList<Box>();
	private ArrayList<Entity> pictures = new ArrayList<Entity>();
	private ArrayList<BoxLabel> labels = new ArrayList<BoxLabel>();
	private ArrayList<Entity> locks = new ArrayList<Entity>();
	private ArrayList<Entity> chains = new ArrayList<Entity>();
	private ArrayList<BoxLabel> collects = new ArrayList<BoxLabel>();

	private boolean mTimeToUnlockBox;

	private Entity mTotalScoreText;
	private final EntityManager<Entity> mTotalScoreCountText;

	private int totalscore = 0;

	private int SC = 0;

	private final BoxUnlockPanel mBoxUnlockPanel;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses2.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass2.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mClouds = new CloudsManager<Cloud>(10, new Cloud(Resources.mBackgroundCloudTextureRegion, this.mBackground));

		this.mTopPanel = new Entity(Resources.mTopPanelTextureRegion, this.mBackground);

		this.mScoreHolder = new Rectangle(0, 0, Options.cameraWidth, 100);
		this.mScoreHolder.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mScoreHolder.setAlpha(0f);
		this.mTopPanel.attachChild(this.mScoreHolder);

		this.mTotalScoreText = new Entity(Resources.mLevelEndTotalScoreTextTextureRegion, this.mScoreHolder);

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.screens.set(Screen.MENU);
			}
		};

		mPoints = new EntityManager<Entity>(MENUITEMS, new Entity(Resources.mBoxesNavigationTextureRegion, this.mBackground));
		mPoints2 = new EntityManager<Entity>(MENUITEMS, new Entity(Resources.mBoxesNavigationTextureRegion, this.mBackground));

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mTopPanel.create().setPosition(0, 0);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBackground.attachChild(rectangle);
		this.rectangle.setAlpha(0);

		this.mBoxUnlockPanel = new BoxUnlockPanel(Options.cameraWidth - 200f, Options.cameraHeight - 70f, this.mBackground);
		this.mBoxUnlockPanel.down();

		for (int i = 0; i < MENUITEMS; i++) {
			final int bi = i;

			BoxLabel picture1 = null;

			if (bi == 0) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 100, Resources.mBoxesLabel1TextureRegion, this.rectangle).create();
				labels.add(picture1);
			} else if (bi == 1) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 100, Resources.mBoxesLabel2TextureRegion, this.rectangle).create();
				labels.add(picture1);
			} else if (bi == 2) {
				picture1 = (BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 100, Resources.mBoxesLabel3TextureRegion, this.rectangle).create();
				labels.add(picture1);
			}

			if (!Game.db.getBox(bi + 1).isOpen() && bi != 3) {
				collects.add((BoxLabel) new BoxLabel(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, 400, Resources.mCollectTextTextureRegion, this.rectangle, true).create());
				if (bi == 1) {
					final Entity n = new Entity(Resources.mBoxCollect50TextureRegion, collects.get(bi));
					n.create().setPosition(105f, 8f, true);
				}
				if (bi == 2) {
					final Entity n = new Entity(Resources.mBoxCollect100TextureRegion, collects.get(bi));
					n.create().setPosition(98f, 8f, true);
				}
			} else {
				collects.add(bi, null);
			}

			final Box Entity = new Box(Resources.mBoxesTextureRegion, this.rectangle) {

				@Override
				public void onClick() {
					if (mPostScroll || bi == 3)
						return;

					BOX_INDEX = bi;

					if (!this.prepare()) {

						boxes.get(bi).animation();

						Game.screens.setChildScreen(Game.screens.get(Screen.BL), false, false, true);

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

			Entity.create().setCenterPosition(Options.cameraWidth * i + Options.cameraCenterX - Options.cameraCenterX / 1.5f * i, Options.cameraCenterY - 50f);
			Entity.setCurrentTileIndex(0);

			Entity picture = null;
			if (bi == 0) {
				picture = (Entity) new Entity(Resources.mBoxesPicture1TextureRegion, Entity).create();
				picture.setCenterPosition(Entity.getWidth() / 2, Entity.getHeight() / 2);
				picture.enableFullBlendFunction();
			} else if (bi == 1) {
				picture = (Entity) new Entity(Resources.mBoxesPicture2TextureRegion, Entity).create();
				picture.setCenterPosition(Entity.getWidth() / 2, Entity.getHeight() / 2);
				picture.enableFullBlendFunction();
			} else if (bi == 2) {
				picture = (Entity) new Entity(Resources.mBoxesPicture3TextureRegion, Entity).create();
				picture.setCenterPosition(Entity.getWidth() / 2, Entity.getHeight() / 2);
				picture.enableFullBlendFunction();
			}
			else if (bi == 3) {
				picture = (Entity) new Entity(Resources.mBoxesComingSoonTextureRegion, Entity).create();
				picture.setCenterPosition(Entity.getWidth() / 2, Entity.getHeight() / 2);
				picture.enableFullBlendFunction();
			}

			pictures.add(picture);

			if (!Game.db.getBox(bi + 1).isOpen() && bi != 3) {
				final Entity chain = (Entity) new Entity(Resources.mChainTextureRegion, picture).create();
				chain.setCenterPosition(picture.getWidth() / 2 + 5f, picture.getHeight() / 2 - 8f);
				chain.enableFullBlendFunction();

				final Entity lock = (Entity) new Entity(Resources.mBoxesLockTextureRegion, picture) {
					@Override
					public void setAlpha(final float pAlpha) {
						super.setAlpha(pAlpha);
						for (int i = 0; i < getChildCount(); i++) {
							getChild(i).setAlpha(pAlpha);
						}
					}
				}.create();
				lock.setCenterPosition(picture.getWidth() / 2, picture.getHeight() / 2 + 14f);
				lock.enableFullBlendFunction();

				locks.add(bi, lock);
				chains.add(bi, chain);

				if (bi == 1) {
					Entity l = new Entity(Resources.mLockStars1TextureRegion, lock);
					l.setCenterPosition(lock.getWidth() / 2, lock.getHeight() / 2 + 17f);
					l.create();
				} else if (bi == 2) {
					Entity l = new Entity(Resources.mLockStars2TextureRegion, lock);
					l.setCenterPosition(lock.getWidth() / 2, lock.getHeight() / 2 + 17f);
					l.create();
				}
			} else {
				locks.add(bi, null);
				chains.add(bi, null);
			}

			boxes.add(Entity);
		}

		int i = -1;
		for (BoxLabel e : labels) {
			i++;
			if (i == 0)
				continue;
			e.down();
		}

		for (BoxLabel e : collects) {
			if (e == null)
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

		this.mTotalScoreText.create().setPosition(0, 7f, true);

		this.mTotalScoreCountText = new EntityManager<Entity>(10, new Entity(Resources.mLevelEndScoreNumbersTextureRegion, this.mScoreHolder));
		for (int a = 0; a < 5; a++) {
			this.mTotalScoreCountText.create().setCenterPosition(this.mTotalScoreText.getX() + this.mTotalScoreText.getWidth() + 13f + 18f * a, 21f);
		}

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

		if (Options.isMusicEnabled) {
			if (!Options.mMainSound.isPlaying() && Options.isMusicEnabled) {
				Options.mMainSound.play();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity.AnimatedEntity#onManagedUpdate (float)
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
						collects.get(G).up();
					} catch (ArrayIndexOutOfBoundsException e) {
						boxes.get(0).animation();
						labels.get(0).up();
						G = 0;
					} catch (IndexOutOfBoundsException e) {

					} catch (NullPointerException e) {

					}

					final int A = G;

					int l = 0;
					switch (A) {
					case 1:
						l = 50;
						break;
					case 2:
						l = 100;
						break;
					}

					int mStarsNeeded = l - Game.db.getTotalStars();

					if (!Game.db.getBox(G + 1).isOpen()) {
						try {
							if (mStarsNeeded <= 0) {
								final ScaleModifier lockAnimationOutScale = new ScaleModifier(1f, 1f, 2f);
								final AlphaModifier lockAnimationOutAlpha = new AlphaModifier(1f, 1f, 0f) {
									@Override
									public void onFinished() {
										Game.db.updateBox(A + 1, 1);
										locks.get(A).destroy();

										mBoxUnlockPanel.down();
									}
								};
								final AlphaModifier chainAnimationOutAlpha = new AlphaModifier(0.2f, 1f, 0f) {
									@Override
									public void onFinished() {
										chains.get(A).destroy();
									}
								};

								locks.get(G).registerEntityModifier(lockAnimationOutScale);
								locks.get(G).registerEntityModifier(lockAnimationOutAlpha);

								collects.get(G).destroy();

								for (int i = 0; i < locks.get(G).getChildCount(); i++) {
									((Entity) locks.get(G).getChild(i)).detachSelf();
								}

								chains.get(G).registerEntityModifier(chainAnimationOutAlpha);

								lockAnimationOutScale.reset();
								lockAnimationOutAlpha.reset();

								chainAnimationOutAlpha.reset();
							}
						} catch (NullPointerException ex) {
						}
					}

					if (!Game.db.getBox(G + 1).isOpen() && G != 3 && !this.mTimeToUnlockBox) {
						this.mBoxUnlockPanel.up();
					} else {
						this.mBoxUnlockPanel.down();
					}

					try {
						if (this.mTimeToUnlockBox) {
							this.mTimeToUnlockBox = false;

							l = 0;
							switch (A) {
							case 1:
								l = 50;
								break;
							case 2:
								l = 100;
								break;
							}

							mStarsNeeded = l - Game.db.getTotalStars();

							if (mStarsNeeded <= 0) {
								final ScaleModifier lockAnimationOutScale = new ScaleModifier(1f, 1f, 2f);
								final AlphaModifier lockAnimationOutAlpha = new AlphaModifier(1f, 1f, 0f) {
									@Override
									public void onFinished() {
										Game.db.updateBox(A + 1, 1);
										locks.get(A).destroy();
									}
								};
								final AlphaModifier chainAnimationOutAlpha = new AlphaModifier(0.2f, 1f, 0f) {
									@Override
									public void onFinished() {
										chains.get(A).destroy();
									}
								};

								locks.get(G).registerEntityModifier(lockAnimationOutScale);
								locks.get(G).registerEntityModifier(lockAnimationOutAlpha);

								collects.get(G).destroy();

								for (int i = 0; i < locks.get(G).getChildCount(); i++) {
									((Entity) locks.get(G).getChild(i)).detachSelf();
								}

								chains.get(G).registerEntityModifier(chainAnimationOutAlpha);

								lockAnimationOutScale.reset();
								lockAnimationOutAlpha.reset();

								chainAnimationOutAlpha.reset();
							} else {
								this.mBoxUnlockPanel.up();

								BOX_INDEX = A;
								boxes.get(A).animation();

								Game.screens.setChildScreen(Game.screens.get(Screen.BL), false, false, true);
							}
						}
					} catch (NullPointerException ex) {
					}

					int y = -1;
					for (BoxLabel e : labels) {
						y++;
						if (G != y)
							e.down();
					}

					y = -1;
					for (BoxLabel e : collects) {
						y++;
						if (e == null)
							continue;
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
			pictures.get(y).setAlpha(alpha);

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
			this.mScoreHolder.setPosition(Options.cameraWidth - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2 - 165f, this.mScoreHolder.getY());
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex(totalscore);
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(false);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 100) {
			this.mScoreHolder.setPosition(Options.cameraWidth - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2 - 180f, this.mScoreHolder.getY());
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 10));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(false);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 1000) {
			this.mScoreHolder.setPosition(Options.cameraWidth - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2 - 200f, this.mScoreHolder.getY());
			this.mTotalScoreCountText.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(totalscore / 100));
			this.mTotalScoreCountText.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((totalscore - FloatMath.floor(totalscore / 100) * 100) / 10));
			this.mTotalScoreCountText.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(totalscore % 10));
			this.mTotalScoreCountText.getByIndex(0).setVisible(true);
			this.mTotalScoreCountText.getByIndex(1).setVisible(true);
			this.mTotalScoreCountText.getByIndex(2).setVisible(true);
			this.mTotalScoreCountText.getByIndex(3).setVisible(false);
			this.mTotalScoreCountText.getByIndex(4).setVisible(false);
		} else if (totalscore < 10000) {
			this.mScoreHolder.setPosition(Options.cameraWidth - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2 - 220f, this.mScoreHolder.getY());
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
			this.mScoreHolder.setPosition(Options.cameraWidth - (Options.cameraWidth * Options.cameraRatioFactor - Options.screenWidth) / 2 - 240f, this.mScoreHolder.getY());
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
			Game.screens.clearChildScreens();
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