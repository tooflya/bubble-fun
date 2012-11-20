package com.tooflya.bubblefun.screens;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class PauseScreen extends PopupScreen {

	// ===========================================================
	// Fields
	// ===========================================================

	private final BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(512, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final Sprite mPanel = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "popup-menu.png", 0, 0, 1, 1), this);

	private final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "menu-big-btn.png", 335, 0, 1, 1);

	private final ButtonScaleable b1 = new ButtonScaleable(mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			modifier4.reset();
		}
	};

	private final ButtonScaleable b2 = new ButtonScaleable(mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			Options.levelNumber++;
			((LevelScreen) Game.screens.get(Screen.LEVEL)).reInit();

			modifier4.reset();
		}
	};

	private final ButtonScaleable b3 = new ButtonScaleable(mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 2;
			Game.screens.l();

			modifier4.reset();
		}
	};

	private final ButtonScaleable b4 = new ButtonScaleable(mButtonsTextureRegion, this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onClick() {
			ScreenManager.mChangeAction = 1;
			Game.screens.l();

			modifier4.reset();
		}
	};

	private final ButtonScaleable mSoundIcon = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "sound-btn.png", 335, 80, 1, 2), this.mPanel) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Options.isMusicEnabled = !Options.isMusicEnabled;

			if (Options.isMusicEnabled) {
				this.setCurrentTileIndex(0);
				Options.mLevelSound.play();
			} else {
				this.setCurrentTileIndex(1);
				Options.mLevelSound.pause();
			}
		}
	};

	private final EntityManager<Sprite> mLables = new EntityManager<Sprite>(4, new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "text-big-btn.png", 0, 350, 1, 4)));

	// ===========================================================
	// Constructors
	// ===========================================================

	public PauseScreen() {
		this.b1.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 - 90f);
		this.b2.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 - 30f);
		this.b3.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 + 30f);
		this.b4.create().setCenterPosition(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2 + 90f);

		this.mSoundIcon.create().setCenterPosition(0 + 35f, this.mPanel.getHeight() - 40f);

		Entity sprite;

		sprite = mLables.create();
		sprite.setCenterPosition(this.b1.getWidth() / 2, this.b1.getHeight() / 2);
		sprite.setCurrentTileIndex(0);
		this.b1.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b2.getWidth() / 2, this.b2.getHeight() / 2);
		sprite.setCurrentTileIndex(1);
		this.b2.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b3.getWidth() / 2, this.b3.getHeight() / 2);
		sprite.setCurrentTileIndex(2);
		this.b3.attachChild(sprite);

		sprite = mLables.create();
		sprite.setCenterPosition(this.b4.getWidth() / 2, this.b4.getHeight() / 2);
		sprite.setCurrentTileIndex(3);
		this.b4.attachChild(sprite);

		this.setBackgroundEnabled(false);

		this.mPanel.create();
		this.mPanel.setScaleCenter(this.mPanel.getWidth() / 2, this.mPanel.getHeight() / 2);
		this.mPanel.setCenterPosition(Options.screenCenterX, Options.screenCenterY);

		this.mPanel.registerEntityModifier(modifier1);
		this.mPanel.registerEntityModifier(modifier2);
		this.mPanel.registerEntityModifier(modifier3);
		this.mPanel.registerEntityModifier(modifier4);

		this.mPanel.setScale(0f);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas);

	}

	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas);

	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onClose() {
		Game.screens.get(Screen.LEVEL).clearChildScene();
	}
}
