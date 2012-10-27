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

	private final Sprite mTextTapHere = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas4, Game.context, Options.CR + "/text-tap.png", 140, 980, 1, 1), this);

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

		mTextTapHere.create().setCenterPosition(Options.cameraCenterX, (Options.cameraHeight / 3 * 2) + Options.cameraHeight / 3 / 2);
		mTextTapHere.setRotation(-15f);

		chikies = new EntityManager(31, new Chiky(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/small-bird.png", 0, 780, 6, 2), this));
		airgums = new EntityManager(100, new Bubble(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/gum-animation.png", 900, 0, 1, 6), this));
		feathers = new EntityManager(100, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/feather.png", 530, 500, 1, 2), this));

		mBlueBird = new BlueBird(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/blue-bird.png", 250, 0, 6, 1), new EntityManager(1000, new Particle(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/feather_new_blue.png", 530, 300, 1, 2), this)), this);

		glints = new EntityManager(100, new Glint(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelScreen1.mBackgroundTextureAtlas0, Game.context, Options.CR + "/blesk.png", 100, 0, 1, 3), this));

		this.mMenuButton.create().setPosition(Options.cameraWidth - (10 * Options.cameraRatioFactor + this.mMenuButton.getWidthScaled()), 10 * Options.cameraRatioFactor);
		this.mResetButton.create().setPosition(Options.cameraWidth - (15 * Options.cameraRatioFactor + this.mMenuButton.getWidthScaled() + this.mResetButton.getWidthScaled()), 10 * Options.cameraRatioFactor);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	public static void reInit() {
		mBlueBird.create();
		mBlueBird.clear();
		airgums.clear();
		chikies.clear();
		glints.clear();
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
		//		final float x = (entity2.getX() + entity2.getWidth() / 2) - (entity1.getX() + entity1.getWidth() / 2);
		//		final float y = (entity2.getY() + entity2.getHeight() / 2) - (entity1.getY() + entity1.getHeight() / 2);
		//		final float d = entity2.getWidthScaled() / 2 + entity1.getWidthScaled() / 2;
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

		if (chikies.getCount() == 0) {
			Game.screens.set(Screen.LEVELEND);
		}

		this.clouds.update();

		//mBirdsCountText.setText(chikies.getCount() + "");
		//mAirCountText.setText(AIR + "");
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
				if (this.lastAirgum.getCenterY() - pTouchEvent.getY() > 100f * Options.cameraRatioFactor) {

					this.lastAirgum.setSpeedY(this.lastAirgum.getSpeedY() + (this.lastAirgum.getCenterY() - pTouchEvent.getY()) / koef);
					//this.lastAirgum.setSpeedX((pTouchEvent.getX() - this.lastAirgum.getCenterX()) / (koef));

					Glint particle;
					for (int i = 0; i < 30; i++) {
						particle = ((Glint) glints.create());
						if (particle != null) {
							particle.Init(i, this.lastAirgum);
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
