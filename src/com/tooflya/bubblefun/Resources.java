package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;

public class Resources {

	// ===========================================================
	// Texture Atlases
	// ===========================================================

	public static final BitmapTextureAtlas mBackgroundGradientTextureAtlas = new BitmapTextureAtlas(2, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mBackgroundElementsTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundCommonTextureAtlas = new BitmapTextureAtlas(256, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mElementsTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Texture Regions
	// ===========================================================

	public static final TiledTextureRegion mBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundGradientTextureAtlas, Game.context, "bg-gr.png", 0, 0, 1, 1);

	public static final TiledTextureRegion mBackgroundGrassTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-grass.png", 381, 0, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-1.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-2.png", 0, 320, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-3.png", 0, 662, 1, 1);
	public static final TiledTextureRegion mBackgroundWaterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-down.png", 381, 150, 1, 1);

	public static final TiledTextureRegion mBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "cloud.png", 0, 0, 1, 4);
	public static final TiledTextureRegion mNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-sprite.png", 0, 512, 1, 11);
	public static final TiledTextureRegion mSmallNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-small.png", 50, 512, 11, 1);
	public static final TiledTextureRegion mStarsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "end-stars.png", 50, 700, 1, 2);

	public static final TiledTextureRegion mBackgroundLogoNameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "main-name.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mTwitterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "tw-btn.png", 0, 240, 1, 1);
	public static final TiledTextureRegion mFacebookTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "fb-btn.png", 45, 240, 1, 1);
	public static final TiledTextureRegion mPlayIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "play.png", 90, 240, 1, 1);
	public static final TiledTextureRegion mMoreIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "more-btn.png", 230, 240, 1, 1);
	public static final TiledTextureRegion mSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "sound-btn.png", 270, 240, 1, 2);
	public static final TiledTextureRegion mSettingsIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "set.png", 310, 240, 1, 1);
	public static final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "menu-big-btn.png", 0, 380, 1, 1);
	public static final TiledTextureRegion mBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "back-btn.png", 153, 380, 1, 1);
	public static final TiledTextureRegion mTopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lvl-panel.png", 0, 586, 1, 1);
	public static final TiledTextureRegion mLevelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "level-btn.png", 0, 672, 1, 5);
	public static final TiledTextureRegion mBoxesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box.png", 400, 0, 1, 2);
	public static final TiledTextureRegion mBoxesPicture1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-1.png", 400, 500, 1, 1);
	public static final TiledTextureRegion mBoxesPicture2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-2.png", 400, 651, 1, 1);
	public static final TiledTextureRegion mBoxesPicture3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-3.png", 400, 802, 1, 1);
	public static final TiledTextureRegion mBoxesLabel1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-1.png", 400, 953, 1, 1);
	public static final TiledTextureRegion mBoxesLabel2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-2.png", 400, 978, 1, 1);
	public static final TiledTextureRegion mBoxesLabel3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-3.png", 520, 988, 1, 1);
	public static final TiledTextureRegion mBoxesLockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lock.png", 620, 881, 1, 1);
	public static final TiledTextureRegion mBoxesNavigationTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "navi.png", 720, 881, 1, 2);
	public static final TiledTextureRegion mBoxesComingSoonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "coming-soon.png", 800, 881, 1, 1);

	// ===========================================================
	// Entities
	// ===========================================================

	public static final Entity mBackgroundGradient = new Entity(mBackgroundGradientTextureRegion) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(mBackgroundGradientTextureRegion);

			sprite.setWidth(380);
			sprite.setHeight(610);

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundGrass = new Entity(mBackgroundGrassTextureRegion) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundWater = new Entity(mBackgroundWaterTextureRegion) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses1 = new Entity(mBackgroundHouseTextureRegion1) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses2 = new Entity(mBackgroundHouseTextureRegion2) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses3 = new Entity(mBackgroundHouseTextureRegion3) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Sprite sprite = new Sprite(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	// ===========================================================
	// Methods
	// ===========================================================

	public static final void loadFirstResources() {
		Game.loadTextures(mBackgroundCommonTextureAtlas);
		Game.loadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1);
	}

	public static final void unloadFirstResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas);
	}

	public static final void loadSecondResources() {
		Game.loadTextures();
	}

	public static final void unloadSecondResources() {
		Game.unloadTextures();
	}
}
