package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator.LinearGradientDirection;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.RectangleBitmapTextureAtlasSourceDecoratorShape;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.util.MathUtils;

import android.graphics.Color;
import android.util.FloatMath;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.LinearGradientFillBitmapTextureAtlasSourceDecorator;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Acceleration;
import com.tooflya.bubblefun.entities.AwesomeText;
import com.tooflya.bubblefun.entities.BlueBird;
import com.tooflya.bubblefun.entities.BonusText;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Chiky;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Electrod;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Feather;
import com.tooflya.bubblefun.entities.Glint;
import com.tooflya.bubblefun.entities.Gradient;
import com.tooflya.bubblefun.entities.Mark;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.LevelsManager;
import com.tooflya.bubblefun.modifiers.AlphaModifier;
import com.tooflya.bubblefun.modifiers.MoveModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public static boolean isTutorialNeeded = false;

	public static int mBubblesCount;
	public static int AIR;
	public static boolean running;
	public static int deadBirds;
	private static boolean isResetAnimationRunning;

	public static int Score;

	public final BitmapTextureAtlas mBackgroundGradientTexture = new BitmapTextureAtlas(2, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public final EmptyBitmapTextureAtlasSource bitmap = new EmptyBitmapTextureAtlasSource(2, 512);

	public final LinearGradientFillBitmapTextureAtlasSourceDecorator gradientSource = new LinearGradientFillBitmapTextureAtlasSourceDecorator(bitmap,
			new RectangleBitmapTextureAtlasSourceDecoratorShape(), Color.rgb(0, 139, 69), Color.rgb(84, 255, 159), LinearGradientDirection.BOTTOM_TO_TOP);

	private Gradient mBackground = new Gradient(0, 0, Options.cameraWidth, Options.cameraHeight, BitmapTextureAtlasTextureRegionFactory.createFromSource(mBackgroundGradientTexture, gradientSource, 0, 0), this);
	
	private CloudsManager<Cloud> clouds = new CloudsManager<Cloud>(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "cloud.png", 600, 300, 1, 3), this.mBackground));

	public EntityManager<AwesomeText> mAwesomeKillText = new EntityManager<AwesomeText>(5, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "awesome-kill.png", 800, 710, 1, 1), this.mBackground));
	public EntityManager<AwesomeText> mDoubleKillText = new EntityManager<AwesomeText>(5, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "double-hit.png", 800, 740, 1, 1), this.mBackground));
	public EntityManager<AwesomeText> mTripleKillText = new EntityManager<AwesomeText>(5, new AwesomeText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "triple-hit.png", 0, 0, 1, 1), this.mBackground));
	public EntityManager<BonusText> mBonusesText = new EntityManager<BonusText>(5, new BonusText(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "scores_bonuses.png", 0, 30, 1, 4), this.mBackground));

	private final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 1f, 1f, 1f);

	private final Sprite mDottedLine = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "dash-line.png", 0, 1020, 1, 1), this.mBackground);

	private final ButtonScaleable mMenuButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "menu-btn.png", 0, 805, 1, 2), this.mBackground) {

		@Override
		public void onClick() {
			LevelScreen.this.setChildScene(Game.screens.get(Screen.PAUSE), false, true, true);
		}
	};

	private final ButtonScaleable mResetButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "replay-btn.png", 0, 880, 1, 2), this.mBackground) {

		@Override
		public void onClick() {
			if (!isResetAnimationRunning) {
				reInit();
			}
		}
	};

	private final Sprite mTextTapHere = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "text-tap.png", 0, 950, 1, 1), this.mBackground) {
		public boolean shake = false;
		private float minS = -15, maxS = 15;
		private boolean reverse = false;
		private float step = 5f;
		private int mi = 100, i = 0;

		@Override
		public Entity create() {
			this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);

			return super.create();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
		 * (float)
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

	private final Rectangle shape = new Rectangle(0, 0, Options.cameraWidth, Options.cameraHeight) {

		private float s = 0.005f;

		private boolean modifier = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
		 * (float)
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

	private final Sprite mLevelWord = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "text-level.png", 0, 980, 1, 1), this.shape);
	private final EntityManager<Sprite> numbers = new EntityManager<Sprite>(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-sprite.png", 385, 0, 1, 11), this.shape));
	private final EntityManager<Sprite> numbersSmall = new EntityManager<Sprite>(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "numbers-small.png", 800, 600, 10, 1), this.mBackground));

	private final MoveModifier move = new MoveModifier(0.5f, Options.cameraCenterX - mTextTapHere.getWidth() / 2, Options.cameraCenterX
			- mTextTapHere.getWidth() / 2, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3,
			(Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2) {
		@Override
		public void onFinished() {
			mTextTapHere.reset();
		}
	};

	private final MoveModifier moveDown = new MoveModifier(0.5f, Options.cameraCenterX - mTextTapHere.getWidth() / 2, Options.cameraCenterX
			- mTextTapHere.getWidth() / 2, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2, (Options.cameraHeight / 3 * 2)
			+ Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3);

	private final Sprite mResetText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context,
			"text-restart.png", 430, 0, 1, 1), this.mRectangle);

	private final MoveModifier restartMove1 = new MoveModifier(0.5f, -mResetText.getWidth(), Options.cameraWidth / 8, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onStarted() {
			rectangleAlphaModifierOn.reset();
		}

		@Override
		public void onFinished() {
			restartMove2.reset();
		}
	};

	private final AlphaModifier rectangleAlphaModifierOn = new AlphaModifier(1f, 0f, 0.7f);
	private final AlphaModifier rectangleAlphaModifierOff = new AlphaModifier(1f, 0.7f, 0f);

	private final MoveModifier restartMove2 = new MoveModifier(1f, Options.cameraWidth / 8, Options.cameraWidth / 8 * 2, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onFinished() {
			restartMove3.reset();
		}
	};

	private final MoveModifier restartMove3 = new MoveModifier(0.5f, Options.cameraWidth / 8 * 2, Options.cameraWidth, Options.cameraCenterY,
			Options.cameraCenterY) {
		@Override
		public void onFinished() {
			reInit();
		}
	};

	private final EntityManager<Sprite> thorns = new EntityManager<Sprite>(10, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "thorn.png", 430, 40, 1, 1), this.mBackground));
	public final EntityManager<Electrod> electrods = new EntityManager<Electrod>(10, new Electrod(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "electrodes.png", 800, 800, 1, 5), this.mBackground));

	public final EntityManager<Acceleration> accelerators = new EntityManager<Acceleration>(10, new Acceleration(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "speed-wind.png", 430, 65, 6, 1), this.mBackground));

	public EntityManager<Mark> mMarks = new EntityManager<Mark>(100, new Mark(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "wind.png", 800, 700, 1, 1), this.mBackground));

	private final Sprite mScoreText = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "score.png", 800, 770, 1, 1), this.mBackground);

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	public EntityManager<Sprite> parachutes = new EntityManager<Sprite>(30, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "parachute.png", 200, 200, 1, 1), this.mBackground));
	public EntityManager<Chiky> chikies = new EntityManager<Chiky>(30, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "small-bird.png", 430, 100, 6, 4), this.mBackground));
	public EntityManager<Bubble> airgums = new EntityManager<Bubble>(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "gum-animation.png", 430, 280, 1, 6), this.mBackground));
	public EntityManager<Feather> feathers = new EntityManager<Feather>(100, new Feather(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "feather.png", 730, 585, 1, 2), this.mBackground));
	public EntityManager<Glint> glints = new EntityManager<Glint>(100, new Glint(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas,Game.context, "blesk.png", 730, 900, 1, 3), this.mBackground));

	public BlueBird mBlueBird = new BlueBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "blue-bird.png", 430, 600, 6, 1),
			new EntityManager<Feather>(100, new Feather(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "feather_new_blue.png", 430, 890, 1, 2), this.mBackground)), this.mBackground);

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
		this.setOnSceneTouchListener(this);

		this.clouds.generateStartClouds();

		mDottedLine.create().setPosition(0, Options.cameraHeight / 3 * 2);

		this.attachChild(shape);
		this.shape.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.shape.setAlpha(0f);

		mLevelWord.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX - 30f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 10f, Options.cameraCenterY);
		numbers.create().setCenterPosition(Options.cameraCenterX + 30f, Options.cameraCenterY);

		for (int i = 0; i < shape.getChildCount(); i++) {
			((Shape) shape.getChild(i)).setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}

		numbers.getByIndex(0).setCurrentTileIndex(1);
		numbers.getByIndex(1).setCurrentTileIndex(10);

		numbersSmall.create().setPosition(67, 16);
		numbersSmall.create().setPosition(81, 16);
		numbersSmall.create().setPosition(95, 16);

		numbersSmall.getByIndex(0).setCurrentTileIndex(0);
		numbersSmall.getByIndex(1).setCurrentTileIndex(0);
		numbersSmall.getByIndex(2).setCurrentTileIndex(0);

		mTextTapHere.setRotation(-15f);
		mTextTapHere.registerEntityModifier(move);
		mTextTapHere.registerEntityModifier(moveDown);

		this.mMenuButton.create().setPosition(Options.cameraWidth - (10 + this.mMenuButton.getWidth()), 10);
		this.mResetButton.create().setPosition(Options.cameraWidth - (15 + this.mMenuButton.getWidth() + this.mResetButton.getWidth()), 10);

		mResetText.create().setPosition(-mResetText.getWidth(), Options.cameraCenterY - mResetText.getHeight() / 2);
		mResetText.registerEntityModifier(restartMove1);
		mResetText.registerEntityModifier(restartMove2);
		mResetText.registerEntityModifier(restartMove3);

		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOn);
		this.mRectangle.registerEntityModifier(rectangleAlphaModifierOff);

		mScoreText.create().setPosition(10, 10);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public void reInit() {
		Score = 0;
		rectangleAlphaModifierOff.reset();

		running = true;
		deadBirds = 0;
		isResetAnimationRunning = false;

		mAwesomeKillText.clear();
		mDoubleKillText.clear();
		mTripleKillText.clear();
		mBonusesText.clear();
		parachutes.clear();

		mBlueBird.create();
		mBlueBird.clear();
		airgums.clear();
		chikies.clear();
		glints.clear();
		accelerators.clear();
		thorns.clear();
		electrods.clear();
		mMarks.clear();
		generateChikies();

		feathers.clear();

		Options.bubbleSizePower = Options.bubbleMaxSizePower;

		AIR = 100;

		mBubblesCount = 0;

		mTextTapHere.create().setCenterPosition(Options.cameraCenterX, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2 + Options.cameraHeight / 3);

		shape.reset();

		if (Options.levelNumber < 10) {
			mLevelWord.setCenterPosition(Options.cameraCenterX - 10f, Options.cameraCenterY - 40f);
			numbers.getByIndex(3).setVisible(false);

			numbers.getByIndex(2).setCurrentTileIndex(Options.levelNumber);
		} else {
			mLevelWord.setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 40f);
			numbers.getByIndex(3).setVisible(true);

			numbers.getByIndex(2).setCurrentTileIndex((int) Math.floor(Options.levelNumber / 10));
			numbers.getByIndex(3).setCurrentTileIndex(Options.levelNumber % 10);
		}

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void generateChikies() {
		

		LevelsManager.generateLevel(Options.levelNumber);
	}

	private void checkCollision() {
		Chiky chiky;
		Bubble airgum;
		for (int i = chikies.getCount() - 1; i >= 0; --i) {
			chiky = chikies.getByIndex(i);
			if (chiky.isCanCollide()) {
				for (int j = airgums.getCount() - 1; j >= 0; --j) {
					airgum = (Bubble) airgums.getByIndex(j);
					if (airgum.isCanCollide() && this.isCollide(chiky, airgum)) {
						airgum.addBirdsKills();
						chiky.isCollide(airgum);
						deadBirds++;
					}
				}
			}
		}
		for (int i = airgums.getCount() - 1; i >= 0; --i) {
			airgum = (Bubble) airgums.getByIndex(i);
			if (airgum.isCanCollide()) {
				for (int j = thorns.getCount() - 1; j >= 0; --j) {
					if (this.isCollide(airgum, thorns.getByIndex(j), true)) {
						airgum.isCollide();
					}
				}
				for (int j = electrods.getCount() - 1; j >= 0; --j) {
					Electrod electrod = ((Electrod) electrods.getByIndex(j));
					if (electrod.isAnimationRunning() && this.isCollide(airgum, electrod, true)) {
						airgum.isCollide();
					}
				}
			}
			if (!mBlueBird.isSleep() && this.isCollide(mBlueBird, airgum)) {
				mBlueBird.particles();
				if (!airgum.isAnimationRunning()) {
					airgum.isCollide();
				}
			}
		}
	}

	private boolean isCollide(Entity entity1, Entity entity2) {
		final float x = entity2.getCenterX() - entity1.getCenterX();
		final float y = entity2.getCenterY() - entity1.getCenterY();
		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
		return x * x + y * y < d * d;
	}

	private boolean isCollide(Entity entity1, Entity entity2, final boolean rectangle) {
		return !((entity1.getX() + entity1.getWidth() <= entity2.getX()) ||
				(entity2.getX() + entity2.getWidth() <= entity1.getX()) ||
				(entity2.getY() + entity2.getHeight() <= entity1.getY()) || (entity1.getY() + entity1.getHeight() <= entity2.getY()));
		// TODO: (R) What to do with scaledSize and various rotationCenter?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		if (!Options.mLevelSound.isPlaying())
			Options.mLevelSound.play();
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
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate
	 * (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.checkCollision();

		this.clouds.update();

		if (chikies.getCount() == 0 && airgums.getCount() == 0 && mAwesomeKillText.getCount() == 0 && mDoubleKillText.getCount() == 0 && mTripleKillText.getCount() == 0) {
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

		/* Score */
		if (Score < 10) {
			numbersSmall.getByIndex(0).setCurrentTileIndex(Score);
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(false);
			numbersSmall.getByIndex(2).setVisible(false);
		} else if (Score < 100) {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 10));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(false);
		} else {
			numbersSmall.getByIndex(0).setCurrentTileIndex((int) FloatMath.floor(Score / 100));
			numbersSmall.getByIndex(1).setCurrentTileIndex((int) FloatMath.floor((Score - FloatMath.floor(Score / 100) * 100) / 10));
			numbersSmall.getByIndex(2).setCurrentTileIndex((int) FloatMath.floor(Score % 10));
			numbersSmall.getByIndex(0).setVisible(true);
			numbersSmall.getByIndex(1).setVisible(true);
			numbersSmall.getByIndex(2).setVisible(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundGradientTexture, mBackgroundTextureAtlas, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundGradientTexture, mBackgroundTextureAtlas, mBackgroundTextureAtlas2);
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

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener#onSceneTouchEvent(org.anddev.andengine.entity.scene.Scene, org.anddev.andengine.input.touch.TouchEvent)
	 */
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		final float pTouchX = pTouchEvent.getX() / Options.cameraRatioFactor;
		final float pTouchY = pTouchEvent.getY() / Options.cameraRatioFactor;

		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (AIR > 0 && chikies.getCount() > 0 && this.lastAirgum == null && pTouchY > Options.cameraHeight - Options.touchHeight) {
				this.lastAirgum = (Bubble) airgums.create();
				this.lastAirgum.initStartPosition(pTouchX, pTouchY);
				this.lastAirgum.setScaleCenter(this.lastAirgum.getWidth() / 2, this.lastAirgum.getHeight() / 2);
			}
			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				this.lastAirgum.initFinishPosition(pTouchX, pTouchY);

				float dx = this.lastAirgum.getSpeedX() / FloatMath.sqrt((float) (Math.pow(this.lastAirgum.getSpeedX(), 2) + Math.pow(this.lastAirgum.getSpeedY(), 2)));
				float dy = this.lastAirgum.getSpeedY() / FloatMath.sqrt((float) (Math.pow(this.lastAirgum.getSpeedX(), 2) + Math.pow(this.lastAirgum.getSpeedY(), 2)));

				float x = this.lastAirgum.getCenterX(), y = this.lastAirgum.getCenterY();
				while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
					x += dx * 35;
					y += dy * 35;
					Entity w = mMarks.create();
					if (w != null)
						w.setCenterPosition(x, y);
				}
				x = this.lastAirgum.getCenterX();
				y = this.lastAirgum.getCenterY();
				while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
					Entity w = mMarks.create();
					if (w != null)
						w.setCenterPosition(x, y);
					x -= dx * 35;
					y -= dy * 35;
				}

				this.lastAirgum = null;

				mBubblesCount++;
			}
			break;
		case TouchEvent.ACTION_MOVE:
			if (this.lastAirgum != null) {
				mMarks.clear();

				float xr = pTouchX, yr = pTouchY;

				float mSpeedX, mSpeedY;

				float angle = (float) Math.atan2(yr - this.lastAirgum.getCenterY(), xr - this.lastAirgum.getCenterX());
				float distance = MathUtils.distance(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY(), xr, yr);
				if (distance < Options.eps) {
					mSpeedX = 0;
					mSpeedY = (Options.bubbleMinSpeed + Options.bubbleMaxSpeed) / 2 - this.lastAirgum.mLostedSpeed;
					mSpeedY = mSpeedY < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : mSpeedY;
					mSpeedY = -mSpeedY;
				}
				else {
					distance -= this.lastAirgum.mLostedSpeed;
					distance = distance < Options.bubbleMinSpeed ? Options.bubbleMinSpeed : distance;
					distance = distance > Options.bubbleMaxSpeed ? Options.bubbleMaxSpeed : distance;

					if (0 < angle) {
						angle -= Options.PI;
					}

					mSpeedX = distance * FloatMath.cos(angle);
					mSpeedY = distance * FloatMath.sin(angle);

					float dx = mSpeedX / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));
					float dy = mSpeedY / FloatMath.sqrt((float) (Math.pow(mSpeedX, 2) + Math.pow(mSpeedY, 2)));

					float x = this.lastAirgum.getCenterX(), y = this.lastAirgum.getCenterY();
					while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
						x += dx * 35;
						y += dy * 35;
						Entity w = mMarks.create();
						if (w != null)
							w.setCenterPosition(x, y);
					}
					x = this.lastAirgum.getCenterX();
					y = this.lastAirgum.getCenterY();
					while (0 < x && x < Options.cameraWidth && 0 < y && y < Options.cameraHeight) {
						Entity w = mMarks.create();
						if (w != null)
							w.setCenterPosition(x, y);
						x -= dx * 35;
						y -= dy * 35;
					}

				}
			}
			break;
		}

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0f);

		this.attachChild(coloredRect);

		return coloredRect;
	}
}
