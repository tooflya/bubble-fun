package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.modifier.IModifier;

import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.BlueBird;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Glint;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.modifiers.MoveModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen1 extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int mBubblesCount;
	public static int AIR;
	public static boolean running;
	public static int deadBirds;
	private static boolean isResetAnimationRunning;

	public final static BitmapTextureAtlas mBackgroundTextureAtlas0 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static BitmapTextureAtlas mBackgroundTextureAtlas4 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, Options.CR + "/bg-game.png", 0, 0, 1, 1), this) {

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

	private CloudsManager clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, Options.CR + "/cloud.png", 0, 0, 1, 3), this));

	private final Entity mDottedLine = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, Options.CR + "/dash-line.png", 0, 1016, 1, 1), this) {

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

	private final Entity mMenuButton = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/menu-btn.png", 0, 0, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				LevelScreen1.this.setChildScene(Game.screens.get(Screen.PAUSE), false, true, true);
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

	private final Entity mResetButton = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/replay-btn.png", 80, 0, 1, 2), this, true) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.shape.Shape#onAreaTouched(org.anddev.andengine.input.touch.TouchEvent, float, float)
		 */
		@Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch (pAreaTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setCurrentTileIndex(1);
				break;
			case TouchEvent.ACTION_UP:
				this.setCurrentTileIndex(0);

				reInit();
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

	private final static Sprite mTextTapHere = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/text-tap.png", 140, 980, 1, 1)) {
		public boolean shake = false;
		private float minS = -15, maxS = 15;
		private boolean reverse = false;
		private float step = 5f;
		private int mi = 100, i = 0;

		@Override
		public Entity create() {
			this.setRotationCenter(this.getWidthScaled() / 2, this.getHeightScaled() / 2);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			if (shake) {
				i++;
				if (reverse) {
					this.mRotation += step;
					if (this.mRotation >= maxS) {
						this.reverse = false;
					}
				}
				else {
					this.mRotation -= step;
					if (this.mRotation <= minS) {
						this.reverse = true;
					}
				}
				if (i >= mi) {
					this.i = 0;
					this.shake = false;
					this.reverse = false;
					this.mRotation = -15;
				}
			}
		}

		@Override
		public void reset() {
			this.shake = true;
		}
	};

	private final static Rectangle shape = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight) {

		private float s = 0.005f;

		private boolean modifier = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
		 */
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);

			boolean update = false;
			for (int i = 0; i < this.getChildCount(); i++) {
				if (this.getChild(i).getAlpha() > 0) {
					this.getChild(i).setAlpha(this.getChild(i).getAlpha() - s);
					update = true;
				}
			}

			if (!update && !modifier) {
				modifier = true;
			}
		}

		public void reset() {
			move.reset();

			for (int i = 0; i < this.getChildCount(); i++) {
				this.getChild(i).setAlpha(1f);
			}
			modifier = false;
		}
	};

	private final static Sprite mLevelWord = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/text-level.png", 700, 0, 1, 1));
	private final static EntityManager numbers = new EntityManager(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/numbers-sprite.png", 815, 0, 1, 11)));

	private final static MoveModifier move = new MoveModifier(0.5f, Options.cameraCenterX - mTextTapHere.getWidthScaled() / 2, Options.cameraCenterX - mTextTapHere.getWidthScaled() / 2, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			mTextTapHere.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final static MoveModifier moveDown = new MoveModifier(0.5f, Options.cameraCenterX - mTextTapHere.getWidthScaled() / 2, Options.cameraCenterX - mTextTapHere.getWidthScaled() / 2, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final static Sprite mResetText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/text-restart.png", 440, 980, 1, 1));

	private final static MoveModifier restartMove1 = new MoveModifier(0.5f, -mResetText.getWidthScaled(), Options.cameraWidth / 6, Options.cameraCenterY, Options.cameraCenterY, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			restartMove2.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final static MoveModifier restartMove2 = new MoveModifier(1.5f, Options.cameraWidth / 6, Options.cameraWidth / 6 * 2, Options.cameraCenterY, Options.cameraCenterY, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			restartMove3.reset();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	private final static MoveModifier restartMove3 = new MoveModifier(0.5f, Options.cameraCenterX + Options.cameraCenterX / 2, Options.cameraWidth, Options.cameraCenterY, Options.cameraCenterY, new IEntityModifierListener() {

		/**
		 * @param pEntityModifier
		 * @param pEntity
		 */
		@Override
		public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
			reInit();
		}

		/**
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {

		}
	});

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	public static EntityManager chikies;
	public static EntityManager airgums;
	public static EntityManager feathers;
	public static EntityManager glints;

	private static BlueBird mBlueBird;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen1() {
		this.setOnSceneTouchListener(this);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.clouds.generateStartClouds();
		mDottedLine.create().setPosition(0, Options.cameraHeight / 3 * 2);

		this.attachChild(shape);
		this.shape.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.shape.setAlpha(0f);

		mLevelWord.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40f * Options.cameraRatioFactor);

		numbers.create().setCenterPosition(Options.cameraCenterX - 30f * Options.cameraRatioFactor, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX - 10f * Options.cameraRatioFactor, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 10f * Options.cameraRatioFactor, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 30f * Options.cameraRatioFactor, Options.cameraCenterY);

		shape.attachChild(mLevelWord);
		this.shape.attachChild(numbers.getByIndex(0));
		this.shape.attachChild(numbers.getByIndex(1));
		this.shape.attachChild(numbers.getByIndex(2));
		this.shape.attachChild(numbers.getByIndex(3));

		for (int i = 0; i < shape.getChildCount(); i++) {
			((Shape) shape.getChild(i)).setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}

		numbers.getByIndex(0).setCurrentTileIndex(1);
		numbers.getByIndex(1).setCurrentTileIndex(10);

		this.attachChild(mTextTapHere); //mTextTapHere.create().setCenterPosition(Options.cameraCenterX, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2);
		mTextTapHere.setRotation(-15f);
		mTextTapHere.registerEntityModifier(move);
		mTextTapHere.registerEntityModifier(moveDown);

		chikies = new EntityManager(31, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/small-bird.png", 0, 780, 6, 2), this));
		airgums = new EntityManager(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/gum-animation.png", 900, 0, 1, 6), this));
		feathers = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/feather.png", 530, 500, 1, 2), this));

		mBlueBird = new BlueBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/blue-bird.png", 250, 0, 6, 1), new EntityManager(1000, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/feather_new_blue.png", 530, 300, 1, 2), this)), this);

		glints = new EntityManager(100, new Glint(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/blesk.png", 100, 0, 1, 3), this));

		this.mMenuButton.create().setPosition(Options.cameraWidth - (10 * Options.cameraRatioFactor + this.mMenuButton.getWidthScaled()), 10 * Options.cameraRatioFactor);
		this.mResetButton.create().setPosition(Options.cameraWidth - (15 * Options.cameraRatioFactor + this.mMenuButton.getWidthScaled() + this.mResetButton.getWidthScaled()), 10 * Options.cameraRatioFactor);

		mResetText.create().setCenterPosition(-Options.cameraWidth, Options.cameraCenterY);
		this.attachChild(mResetText);
		mResetText.registerEntityModifier(restartMove1);
		mResetText.registerEntityModifier(restartMove2);
		mResetText.registerEntityModifier(restartMove3);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public static void reInit() {
		running = true;
		deadBirds = 0;
		isResetAnimationRunning = false;

		mBlueBird.create();
		mBlueBird.clear();
		airgums.clear();
		chikies.clear();
		glints.clear();
		generateChikies(30); // TODO: Change count depending to level number.

		feathers.clear();

		Options.scalePower = 20; // TODO: Change count of scale power.

		AIR = 10;//100;

		mBubblesCount = 0;

		mTextTapHere.create().setCenterPosition(Options.cameraCenterX, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3);

		shape.reset();

		if (Options.levelNumber < 10) {
			mLevelWord.setCenterPosition(Options.cameraCenterX - 10 * Options.cameraRatioFactor, Options.cameraCenterY - 40f * Options.cameraRatioFactor);
			numbers.getByIndex(3).setVisible(false);

			numbers.getByIndex(2).setCurrentTileIndex(Options.levelNumber);
		} else {
			mLevelWord.setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40f * Options.cameraRatioFactor);
			numbers.getByIndex(3).setVisible(true);

			numbers.getByIndex(2).setCurrentTileIndex((int) Math.floor(Options.levelNumber / 10));
			numbers.getByIndex(3).setCurrentTileIndex(Options.levelNumber % 10);
		}

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
			if (this.isCollide(this.mBlueBird, airgum)) {
				if (!this.mBlueBird.isSleep()) {
					this.mBlueBird.particles();
					if (!airgum.isAnimationRunning()) {
						airgum.animate(40, 0, airgum);
					}
				}
				break;
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
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

		Game.screens.get(Screen.LEVEL).clearChildScene();
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

		this.clouds.update();

		if (chikies.getCount() == 0) {
			Game.screens.set(Screen.LEVELEND);
		} else {

			if (AIR <= 0 && running) {
				moveDown.reset();
				running = false;
			}

			if (airgums.getCount() <= 0 && deadBirds <= 0 && !running && !isResetAnimationRunning) {
				restartMove1.reset();
				isResetAnimationRunning = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2, mBackgroundTextureAtlas4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2, mBackgroundTextureAtlas4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		PreloaderScreen.mChangeAction = 1; // TODO: WTF? LOL d:

		if (this.hasChildScene()) {
			this.clearChildScene();
		} else {
			this.setChildScene(Game.screens.get(Screen.PAUSE), false, true, true);
		}

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
				final float koef = 20f * Options.cameraRatioFactor;
				if (this.lastAirgum.getCenterY() - pTouchEvent.getY() > 40f * Options.cameraRatioFactor) {

					this.lastAirgum.setIsScale(false);

					float angle = (float) Math.atan2(pTouchEvent.getY() - this.lastAirgum.getCenterY(), pTouchEvent.getX() - this.lastAirgum.getCenterX());

					float distance = MathUtils.distance(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY(), pTouchEvent.getX(), pTouchEvent.getY());

					distance = distance > 5 ? 5 : distance;

					this.lastAirgum.setSpeedX(-((distance) * FloatMath.cos(angle)));
					this.lastAirgum.setSpeedYB((this.lastAirgum.getSpeedY() - distance * FloatMath.sin(angle)) * 1.5f);

					Glint particle;
					for (int i = 0; i < 15; i++) {
						particle = ((Glint) glints.create());
						if (particle != null) {
							particle.Init(i, this.lastAirgum);
						}
					}
				} else {

					this.lastAirgum.setIsScale(false);
				}
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
