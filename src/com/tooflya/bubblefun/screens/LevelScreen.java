package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.BigBird;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.entities.Text;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mBubblesCount;
	public static int AIR;

	public final static BitmapTextureAtlas mBackgroundTextureAtlas0 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main_par1.png", 0, 0, 1, 1), false) {

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

	private final static Entity mDottedLine = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "line.png", 0, 1016, 1, 1), false) {

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

	private final static Entity mResetButton = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "reset.png", 0, 950, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_UP:
				LevelScreen.reInit();
				break;
			}

			return super.onAreaTouched(pAreaTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
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

	private final static Entity mBirdsCounterBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-bird.png", 0, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static Entity mAirCounterBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-balon.png", 105, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static Entity mAirCounterBackgroundFill = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "counter-balon-fill.png", 230, 850, 1, 1), false) {

		@Override
		public Entity create() {
			if (!this.hasParent()) {
				Game.screens.get(Screen.LEVEL).attachChild(this);
			}

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

	private final static Text mScoreText = new Text(0, 0, Game.mSmallFont, "Score: xxxxx");
	private final static Text mBirdsCountText = new Text(68 * Options.CAMERA_RATIO_FACTOR, 30 * Options.CAMERA_RATIO_FACTOR, Game.mSmallFont, "31");
	private final static Text mAirCountText = new Text(Options.cameraWidth - 125 * Options.CAMERA_RATIO_FACTOR, 61 * Options.CAMERA_RATIO_FACTOR, Game.mSmallFont, "1234567890-");
	private final static Text mTapHelpText = new Text(Options.cameraCenterX - 210 * Options.CAMERA_RATIO_FACTOR, Options.cameraHeight / 3 * 2 + 130 * Options.CAMERA_RATIO_FACTOR, Game.mSmallFont, "Tap here to pop chicks!!!");

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	private CloudsManager clouds;

	public static EntityManager chikies;
	public static EntityManager airgums;
	public static EntityManager feathers;
	public static EntityManager stars;

	private static BigBird mBigBird;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
		this.setOnSceneTouchListener(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#init()
	 */
	@Override
	public void init() {
		this.attachChild(mBackground);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "clouds.png", 0, 0, 1, 4), Screen.LEVEL));
		this.clouds.generateStartClouds();
		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mDottedLine.create().setPosition(0, Options.cameraHeight / 3 * 2);

		this.attachChild(mDottedLine);

		chikies = new EntityManager(31, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "chiky.png", 1, 1, 1, 4)));
		airgums = new EntityManager(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "bubble_blow.png", 900, 0, 1, 6)));
		feathers = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "feather.png", 530, 0, 1, 2), Screen.LEVEL));

		mBigBird = new BigBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "bird_big_animation.png", 60, 200, 1, 2), false, new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "feather_new_blue.png", 530, 300, 1, 2), Screen.LEVEL)));

		mBirdsCounterBackground.create().setPosition(20 * Options.CAMERA_RATIO_FACTOR, 20 * Options.CAMERA_RATIO_FACTOR);
		mAirCounterBackgroundFill.create().setPosition(Options.cameraWidth - 20 * Options.CAMERA_RATIO_FACTOR - mAirCounterBackgroundFill.getWidthScaled(), 20 * Options.CAMERA_RATIO_FACTOR + mAirCounterBackground.getHeightScaled() - mAirCounterBackgroundFill.getHeightScaled());
		mAirCounterBackground.create().setPosition(Options.cameraWidth - 20 * Options.CAMERA_RATIO_FACTOR - mAirCounterBackground.getWidthScaled(), 20 * Options.CAMERA_RATIO_FACTOR);

		mBirdsCountText.setColor(0f, 0f, 0f);
		mAirCountText.setColor(0f, 0f, 0f);
		this.attachChild(mBirdsCountText);
		this.attachChild(mAirCountText);
		// this.attachChild(mScoreText);
		this.attachChild(mTapHelpText);

		mTapHelpText.setRotationCenter(mTapHelpText.getWidthScaled() / 2, mTapHelpText.getHeightScaled() / 2);
		mTapHelpText.setRotation(-15);

		// mResetButton.create().setPosition(Options.cameraWidth - mResetButton.getWidthScaled() - 100 * Options.CAMERA_RATIO_FACTOR, 20 * Options.CAMERA_RATIO_FACTOR);
		// this.registerTouchArea(mResetButton);
		stars = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen.mBackgroundTextureAtlas0, Game.context, "star.png", 900, 900, 1, 1), Screen.LEVEL));
	}

	public static void reInit() {
		mBigBird.create();
		mBigBird.mFeathersManager.clear();
		airgums.clear();
		chikies.clear();
		stars.clear();
		generateChikies(30); // TODO: Change count depending to level number.

		feathers.clear();

		Options.scalePower = 20; // TODO: Change count of scale power.

		AIR = 100;

		mBubblesCount = 0;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private static void generateChikies(final int count) {
		Chiky chiky;
		
		final float minHeight = Options.chikySize / 2 + Options.ellipseHeight;
		final float sizeHeight2 = (Options.cameraHeight - Options.touchHeight - Options.chikySize - 2 * Options.ellipseHeight) / 2;
		
		final int stepSign = 1;
		switch (Options.levelNumber) {
		case 1:
			chiky = (Chiky) chikies.create();
			chiky.init(0, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 2:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * 0.5f, -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(0, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			return;
		case 3:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(0, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			return;
		case 4:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(0, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			return;
		case 5:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(0, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 6:
			chiky = (Chiky) chikies.create();
			chiky.init(2, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 7:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * 0.5f, -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 8:
			chiky = (Chiky) chikies.create();
			chiky.init(2, Options.cameraWidth / 2, minHeight + sizeHeight2 * 0.5f, -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 9:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			return;
		case 10:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(0, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 11:
			chiky = (Chiky) chikies.create();
			chiky.init(3, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 12:
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			return;
		case 13:
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			return;
		case 14:
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 15:
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			return;
		case 16:
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * 0.5f, -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, 0, minHeight + sizeHeight2, stepSign);
			return;
		case 17:
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 18:
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 19:
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		case 20:
			chiky = (Chiky) chikies.create();
			chiky.init(1, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(2, Options.cameraWidth / 2, minHeight + sizeHeight2 * Game.random.nextFloat(), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(3, Options.cameraWidth / 2, minHeight + sizeHeight2, stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(4, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), -stepSign);
			chiky = (Chiky) chikies.create();
			chiky.init(5, Options.cameraWidth / 2, minHeight + sizeHeight2 * (Game.random.nextFloat() + 1), stepSign);
			return;
		}
	}

	private void checkCollision() {
		Chiky chiky;
		Bubble airgum;
		for (int i = this.chikies.getCount() - 1; i >= 0; --i) {
			chiky = (Chiky) this.chikies.getByIndex(i);

			if (chiky.getIsFly()) {
				for (int j = this.airgums.getCount() - 1; j >= 0; --j) {
					airgum = (Bubble) this.airgums.getByIndex(j);
					if (this.isCollide(chiky, airgum)) {
						chiky.setIsNeedToFlyAway(airgum.getScaleX() * 0.75f);
					}
				}
			}
		}

		for (int j = this.airgums.getCount() - 1; j >= 0; --j) {
			airgum = (Bubble) this.airgums.getByIndex(j);
			if (this.isCollide(this.mBigBird, airgum)) {
				if (!this.mBigBird.mIsSleep) {
					this.mBigBird.particles();
					if (!airgum.isAnimationRunning()) {
						airgum.animate(40, 0, airgum);
					}
				}
				break;
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = (entity2.getX() + entity2.getWidth() / 2) - (entity1.getX() + entity1.getWidth() / 2);
		final float y = (entity2.getY() + entity2.getHeight() / 2) - (entity1.getY() + entity1.getHeight() / 2);
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < d * d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();
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
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		this.checkCollision();

		if (chikies.getCount() == 0) {
			Game.screens.set(Screen.LEVELEND);
		}

		this.clouds.update();

		mBirdsCountText.setText(chikies.getCount() + "");
		mAirCountText.setText(AIR + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		PreloaderScreen.mChangeAction = 1;
		Game.screens.set(Screen.LOAD);

		return true;
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (AIR > 0) {
				if (this.lastAirgum == null && pTouchEvent.getY() > Options.cameraHeight - Options.cameraHeight / 3)
				{
					this.lastAirgum = (Bubble) airgums.create();
					this.lastAirgum.setCenterPosition(pTouchEvent.getX(), pTouchEvent.getY());
					this.lastAirgum.setScaleCenterY(0);
				}
				if (this.lastAirgum != null) {
					this.lastAirgum.setIsScale(true);
				}
			}
			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				final float koef = 10f;
				if (this.lastAirgum.getCenterY() - pTouchEvent.getY() > 10f) {
					this.lastAirgum.setSpeedY(this.lastAirgum.getSpeedY() + (this.lastAirgum.getCenterY() - pTouchEvent.getY()) / koef);
					this.lastAirgum.setSpeedX((pTouchEvent.getX() - this.lastAirgum.getCenterX()) / (koef * 5));

					Particle particle;
					for (int i = 0; i < Options.particlesCount / 2; i++) {
						particle = ((Particle) stars.create());
						if (particle != null) {
							particle.Init().setCenterPosition(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY());
						}
					}
				}
				this.lastAirgum.setIsScale(false);
				this.lastAirgum = null;

				mBubblesCount++;
			}
			break;
		}

		return false;
	}
	// ===========================================================
	// Methods
	// ===========================================================

}
