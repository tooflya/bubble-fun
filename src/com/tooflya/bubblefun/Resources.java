package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator.LinearGradientDirection;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecoratorExtended;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.RectangleBitmapTextureAtlasSourceDecoratorShape;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;

import com.tooflya.bubblefun.entities.Entity;

public class Resources {

	// ===========================================================
	// Texture Atlases
	// ===========================================================

	public static final BitmapTextureAtlas mBackgroundGradientTextureAtlas = new BitmapTextureAtlas(2, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundGradientTextureAtlas2 = new BitmapTextureAtlas(2, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mBackgroundElementsTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundCommonTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mElementsTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas3 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas7 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas8 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas9 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mTutorialFontTextureAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);

	public static final Font mFont = FontFactory.createFromAsset(mTutorialFontTextureAtlas, Game.mContext, "font/Lobster.ttf", 16f * Options.cameraRatioFactor, true, Color.BLACK);

	// ===========================================================
	// Blank PNG 1024x1024
	// ===========================================================

	public static final TiledTextureRegion mBlank1024Png1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png4 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png5 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png6 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas8, Game.mContext, "blank1024.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBlank1024Png7 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas9, Game.mContext, "blank1024.png", 0, 0, 1, 1);

	// ===========================================================
	// Texture Regions
	// ===========================================================

	public static final TiledTextureRegion mBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundGradientTextureAtlas, Game.mContext, "bg-gr.png", 0, 0, 1, 1);

	public static final TiledTextureRegion mBackgroundGrassTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-grass.png", 2, 349, 1, 1);
	public static final TiledTextureRegion mBackgroundGrassTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-grass-2.png", 2, 601, 1, 1);
	public static final TiledTextureRegion mBackgroundGrassTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-grass-3.png", 2, 750, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-house-1.png", 388, 341, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-house-2.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-house-3.png", 388, 2, 1, 1);
	public static final TiledTextureRegion mBackgroundWaterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "bg-down.png", 2, 502, 1, 1);
	public static final TiledTextureRegion mBackgroundBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "blue-bird-menu-01.png", 388, 668, 1, 1);
	public static final TiledTextureRegion mBackgroundParachuteBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "yb-4.png", 493, 668, 1, 1);
	public static final TiledTextureRegion mBackgroundBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "yb-5.png", 561, 672, 1, 1);
	public static final TiledTextureRegion mGetMoreCoinsTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-get-text.png", 774, 2, 1, 1);
	public static final TiledTextureRegion mAnyPurchaseTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-ads-text.png", 774, 45, 1, 1);
	public static final TiledTextureRegion mGetCoinsButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-btn-500.png", 774, 166, 1, 1);
	public static final TiledTextureRegion mGetCoinsButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-btn-1000.png", 774, 90, 1, 1);
	public static final TiledTextureRegion mGetCoinsButton3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-btn-2200.png", 774, 242, 1, 1);
	public static final TiledTextureRegion mGetCoinsButton4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "popup-shop-btn-5000.png", 774, 318, 1, 1);
	public static final TiledTextureRegion mBuyBonusButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "shop-menu-btn-100.png", 900, 90, 1, 1);
	public static final TiledTextureRegion mBuyBonusButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "shop-menu-btn-150.png", 900, 133, 1, 1);
	public static final TiledTextureRegion mBuyBonusButton3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "shop-menu-btn-200.png", 900, 176, 1, 1);
	public static final TiledTextureRegion mBuyBonusButton4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.mContext, "shop-menu-btn-250.png", 900, 219, 1, 1);

	public static final TiledTextureRegion mBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "cloud.png", 388, 2, 1, 4);
	public static final TiledTextureRegion mNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "numbers-sprite.png", 2, 618, 1, 11);
	public static final TiledTextureRegion mSmallNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "numbers-small.png", 388, 516, 12, 1);
	public static final TiledTextureRegion mStarsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "end-stars.png", 854, 2, 1, 2);
	public static final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "menu-big-btn.png", 48, 978, 1, 1);
	public static final TiledTextureRegion mPreloadBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "preload-bg.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mPreloadBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "preload-bg-cloud.png", 648, 31, 1, 1);
	public static final TiledTextureRegion mMusicIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "melody-btn.png", 515, 549, 1, 2);
	public static final TiledTextureRegion mPreloadTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, Game.getString("preload_text"), 388, 582, 1, 1);
	public static final TiledTextureRegion mLevelEndTotalScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "end-text-total-score.png", 388, 549, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "end-text-numbers.png", 206, 978, 13, 1);
	public static final TiledTextureRegion mBoxNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "numbers-sprite-world-2.png", 648, 2, 11, 1);
	public static final TiledTextureRegion mPopupBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "popup.png", 48, 618, 1, 1);
	public static final TiledTextureRegion mStaticCoinTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "shop-coins.png", 552, 549, 1, 1);
	public static final TiledTextureRegion mBonus1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "bonus-1-button.png", 978, 978, 1, 1);
	public static final TiledTextureRegion mBonus2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "bonus-2-button.png", 932, 978, 1, 1);
	public static final TiledTextureRegion mBonus3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "bonus-3-button.png", 886, 978, 1, 1);
	public static final TiledTextureRegion mBonus4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "bonus-4-button.png", 840, 978, 1, 1);
	public static final TiledTextureRegion mShopAvailableTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.mContext, "shop-num-sprite.png", 998, 846, 1, 5);

	public static final TiledTextureRegion mBackgroundLogoNameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "main-name.png", 401, 67, 1, 1);
	public static final TiledTextureRegion mTwitterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "tw-btn.png", 950, 139, 1, 1);
	public static final TiledTextureRegion mFacebookTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "fb-btn.png", 948, 189, 1, 1);
	public static final TiledTextureRegion mPlayIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "play.png", 256, 60, 1, 1);
	public static final TiledTextureRegion mMoreIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "more-btn.png", 945, 239, 1, 1);
	public static final TiledTextureRegion mSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "sound-btn.png", 356, 202, 1, 2);
	public static final TiledTextureRegion mSettingsIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "set.png", 128, 785, 1, 1);
	public static final TiledTextureRegion mBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "back-btn.png", 341, 2, 1, 1);
	public static final TiledTextureRegion mTopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "lvl-panel.png", 401, 2, 1, 1);
	public static final TiledTextureRegion mLevelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "level-btn.png", 2, 504, 1, 5);
	public static final TiledTextureRegion mBoxesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box.png", 2, 2, 1, 2);
	public static final TiledTextureRegion mCreditsTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "text-more-credits.png", 339, 504, 1, 1);
	public static final TiledTextureRegion mGameResetTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "text-more-reset-process.png", 557, 624, 1, 1);
	public static final TiledTextureRegion mBoxesPicture1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-picture-1.png", 401, 504, 1, 1);
	public static final TiledTextureRegion mBoxesPicture2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-picture-2.png", 564, 405, 1, 1);
	public static final TiledTextureRegion mBoxesPicture3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-picture-3.png", 2, 845, 1, 1);
	public static final TiledTextureRegion mBoxesLockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "lock.png", 341, 660, 1, 1);
	public static final TiledTextureRegion mBoxesNavigationTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "navi.png", 943, 280, 1, 2);
	public static final TiledTextureRegion mBoxesComingSoonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "coming-soon.png", 780, 273, 1, 1);
	public static final TiledTextureRegion mExitYesbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "accept-btn.png", 883, 513, 1, 2);
	public static final TiledTextureRegion mExitNobuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "decline-btn.png", 256, 202, 1, 2);
	public static final TiledTextureRegion mBoxesLabel1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-name-1.png", 780, 71, 1, 1);
	public static final TiledTextureRegion mBoxesLabel2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-name-2.png", 780, 160, 1, 1);
	public static final TiledTextureRegion mBoxesLabel3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "box-name-3.png", 780, 113, 1, 1);
	public static final TiledTextureRegion mChainTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "chain.png", 186, 785, 1, 1);
	public static final TiledTextureRegion mLockStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "text-box-2.png", 256, 2, 1, 1);
	public static final TiledTextureRegion mLockStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "text-box-3.png", 720, 513, 1, 1);
	public static final TiledTextureRegion mCollectTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "collect.png", 787, 2, 1, 1);
	public static final TiledTextureRegion mExitTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "text-exit.png", 722, 363, 1, 1);
	public static final TiledTextureRegion mOkButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "bigbtn-yes.png", 564, 311, 1, 1);
	public static final TiledTextureRegion mLockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "popup-lock-box.png", 68, 504, 1, 1);
	public static final TiledTextureRegion mPopupStarsNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "numbers-sprite-popup.png", 557, 561, 10, 1);
	public static final TiledTextureRegion mBoxUnlockPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "unlock-bg.png", 564, 358, 1, 1);
	public static final TiledTextureRegion mBoxUnlockButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "unlock-btn.png", 68, 785, 1, 1);
	public static final TiledTextureRegion mBoxPurchaseButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "btn-unlock-bye.png", 401, 782, 1, 1);
	public static final TiledTextureRegion mBoxCollect50TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "50-collect.png", 356, 278, 1, 1);
	public static final TiledTextureRegion mBoxCollect100TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "100-collect.png", 950, 113, 1, 1);
	public static final TiledTextureRegion mSmallLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "logo_small_zero.png", 780, 207, 1, 1);
	public static final TiledTextureRegion mBuyButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "shop-btn.png", 401, 836, 1, 1);
	public static final TiledTextureRegion mPlayButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "shop-menu-play-btn.png", 856, 611, 1, 1);
	public static final TiledTextureRegion mShopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "shop-panel.png", 454, 660, 1, 1);
	public static final TiledTextureRegion mGetCoinsButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "get-coins-btn.png", 401, 908, 1, 1);
	public static final TiledTextureRegion mRatePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "popup-rate-me.png", 523, 721, 1, 1);
	public static final TiledTextureRegion mRateLaterButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "popup-rate-me-later.png", 533, 871, 1, 1);
	public static final TiledTextureRegion mRateNowButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "popup-rate-me-rate.png", 670, 871, 1, 1);
	public static final TiledTextureRegion mResetTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "reset-text.png", 533, 951, 1, 1);
	public static final TiledTextureRegion mResetHoldTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "reset-text-hold.png", 557, 606, 1, 1);
	public static final TiledTextureRegion mShopMarkersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.mContext, "shop-menu-marker.png", 726, 311, 1, 1);

	public static final TiledTextureRegion mStorePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.mContext, "shop-menu.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mStoreTreesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.mContext, "shop-menu-tree.png", 340, 2, 1, 1);
	public static final TiledTextureRegion mStorePanelTopTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.mContext, "shop-menu-up.png", 2, 614, 1, 1);
	public static final TiledTextureRegion mStorePanelDownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.mContext, "shop-menu-down.png", 2, 720, 1, 1);

	public static final EmptyBitmapTextureAtlasSource mLevelGradientBitmap = new EmptyBitmapTextureAtlasSource(2, 512);
	public static final LinearGradientFillBitmapTextureAtlasSourceDecoratorExtended mLevelBackgroundGradientSource = new LinearGradientFillBitmapTextureAtlasSourceDecoratorExtended(mLevelGradientBitmap,
			new RectangleBitmapTextureAtlasSourceDecoratorShape(), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), LinearGradientDirection.BOTTOM_TO_TOP);
	public static final TiledTextureRegion mLevelBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(mBackgroundGradientTextureAtlas2, mLevelBackgroundGradientSource, 0, 0, 1, 1);

	public static final TiledTextureRegion mRegularBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "small-bird.png", 206, 598, 6, 4);
	public static final TiledTextureRegion mTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "game-panel.png", 476, 106, 1, 1);
	public static final TiledTextureRegion mDottedLineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "dash-line.png", 458, 216, 1, 1);
	public static final TiledTextureRegion mRestartTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "text-restart.png", 740, 669, 1, 1);
	public static final TiledTextureRegion mBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "gum-animation.png", 767, 363, 1, 6);
	public static final TiledTextureRegion mFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "feather.png", 338, 406, 1, 2);
	public static final TiledTextureRegion mGlintsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "blesk.png", 692, 808, 1, 3);
	public static final TiledTextureRegion mBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "blue-bird.png", 452, 445, 6, 1);
	public static final TiledTextureRegion mBlueFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "feather_new_blue.png", 338, 360, 1, 2);
	public static final TiledTextureRegion mScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "score.png", 388, 108, 1, 1);
	public static final TiledTextureRegion mAcceleratorsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "speed-wind.png", 740, 530, 6, 1);
	public static final TiledTextureRegion mCoinsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "coin-sprite.png", 388, 191, 4, 4);
	public static final TiledTextureRegion mMenuButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "menu-btn.png", 944, 2, 1, 1);
	public static final TiledTextureRegion mRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "replay-btn.png", 338, 528, 1, 1);
	public static final TiledTextureRegion mLevelSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "sound-btn.png", 338, 452, 1, 2);
	public static final TiledTextureRegion mButtonsLabelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "text-big-btn.png", 862, 44, 1, 4);
	public static final TiledTextureRegion mSnowFlakesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "snowflake.png", 1009, 202, 1, 1);
	public static final TiledTextureRegion mSnowyBirdsHatTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "snow-hat.png", 326, 987, 1, 1);
	public static final TiledTextureRegion mSnowyBallSpeedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "snowball-speed.png", 2, 780, 1, 1);
	public static final TiledTextureRegion mSnowyBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "snowball.png", 400, 531, 1, 1);
	public static final TiledTextureRegion mSnowyTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "snowy-game-panel.png", 476, 41, 1, 1);
	public static final TiledTextureRegion mLevelWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "text-level.png", 2, 827, 1, 1);
	public static final TiledTextureRegion mLevelSnowyWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "text-level-snow.png", 326, 780, 1, 1);
	public static final TiledTextureRegion mSpecialNumbers1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "numbers-sprite-world.png", 453, 363, 11, 1);
	public static final TiledTextureRegion mSpecialNumbers2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "numbers-sprite-snow.png", 452, 404, 11, 1);
	public static final TiledTextureRegion mSnowyBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "small-bird-snow.png", 68, 890, 6, 2);
	public static final TiledTextureRegion mLevelEndPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "end-lvl-popup.png", 447, 508, 1, 1);
	public static final TiledTextureRegion mLevelEndReturnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "buttons-end-menu.png", 384, 397, 1, 1);
	public static final TiledTextureRegion mLevelEndRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "buttons-end-replay.png", 388, 261, 1, 1);
	public static final TiledTextureRegion mStoreButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "buttons-end-shop.png", 824, 462, 1, 1);
	public static final TiledTextureRegion mLevelEndNextButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "buttons-end-next.png", 385, 329, 1, 1);
	public static final TiledTextureRegion mLevelEndCompleteCaptureTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "end_lvl_text_complete.png", 824, 338, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "end-text-score.png", 379, 498, 1, 1);
	public static final TiledTextureRegion mLevelEndStarsScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "end-text-star.png", 379, 465, 1, 1);
	public static final TiledTextureRegion mLevelEndTimeTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "end-text-time.png", 824, 434, 1, 1);
	public static final TiledTextureRegion mAirRegularOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "air-bar.png", 466, 171, 1, 1);
	public static final TiledTextureRegion mAngryCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "anrgy-cloud.png", 2, 266, 4, 1);
	public static final TiledTextureRegion mLighingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "light.png", 740, 551, 1, 1);
	public static final TiledTextureRegion mHoldCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "angry-cloud-hold.png", 456, 266, 4, 1);
	public static final TiledTextureRegion mHoldCloudWindTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "angry-cloud-hold-wind.png", 458, 227, 4, 1);
	public static final TiledTextureRegion mMarkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "wind.png", 1007, 1007, 1, 1);
	public static final TiledTextureRegion mSpaceBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "small-bird-space.png", 830, 236, 4, 2);
	public static final TiledTextureRegion mGlassesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "glass.png", 356, 296, 3, 1);
	public static final TiledTextureRegion mSpaceBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "blue-bird-space.png", 446, 808, 4, 1);
	public static final TiledTextureRegion mSpaceAcceleratorsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "space-fast.png", 2, 987, 6, 1);
	public static final TiledTextureRegion mSpaceTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "game-panel-space.png", 2, 108, 1, 1);
	public static final TiledTextureRegion mSpaceBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "space-asteroid.png", 356, 266, 1, 1);
	public static final TiledTextureRegion mSpaceBallSpeedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "asteroid-fast.png", 356, 316, 1, 1);
	public static final TiledTextureRegion mSpacePlanetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "planet.png", 740, 711, 1, 1);
	public static final TiledTextureRegion mLevelSpaceWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "text-level-space.png", 844, 202, 1, 1);
	public static final TiledTextureRegion mSpecialNumbers3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "numbers-sprite-space.png", 476, 2, 11, 1);
	public static final TiledTextureRegion mUfoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "ufo.png", 2, 2, 6, 1);
	public static final TiledTextureRegion mMeteoritTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "meteorit.png", 2, 337, 3, 2);
	public static final TiledTextureRegion mGreenLaserTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "lazer-light.png", 740, 519, 1, 1);
	public static final TiledTextureRegion mRedLaserTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "lazer-light-sheep.png", 740, 508, 1, 1);
	public static final TiledTextureRegion mAsteroidBrokenTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "space-asteroid-broke.png", 379, 531, 1, 2);
	public static final TiledTextureRegion mAirplaneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "space-sheep.png", 388, 141, 1, 1);
	public static final TiledTextureRegion mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "ads-btn-red.png", 290, 912, 1, 1);
	public static final TiledTextureRegion mAdsButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "ads-btn-blue.png", 2, 571, 1, 1);
	public static final TiledTextureRegion mAdsPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "ads-panel.png", 348, 888, 1, 1);
	public static final TiledTextureRegion mAimTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "aim.png", 72, 571, 1, 1);
	public static final TiledTextureRegion mAimArrowsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "arrow-target.png", 115, 571, 1, 1);
	public static final TiledTextureRegion mTimerBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "timer-bar.png", 180, 571, 1, 1);
	public static final TiledTextureRegion mTimerNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.mContext, "timer-numbers-sprite.png", 72, 611, 5, 1);

	public static final TiledTextureRegion mSpaceStarsBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas8, Game.mContext, "space-stars.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mAwesomePointsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas8, Game.mContext, "points.png", 402, 2, 1, 4);
	public static final TiledTextureRegion mAwesomeTextsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas8, Game.mContext, "awesome.png", 532, 2, 1, 4);

	public static final TiledTextureRegion mBoxesUnlockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas9, Game.mContext, "text-popup-unlock-bye.png", 2, 2, 1, 1);

	// ===========================================================
	// Entities
	// ===========================================================

	public static final Entity mBackgroundGradient = new Entity(mBackgroundGradientTextureRegion) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(mBackgroundGradientTextureRegion);

			sprite.setWidth(380);
			sprite.setHeight(610);

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundGrass1 = new Entity(mBackgroundGrassTextureRegion1) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundGrass2 = new Entity(mBackgroundGrassTextureRegion2) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundGrass3 = new Entity(mBackgroundGrassTextureRegion3) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundWater = new Entity(mBackgroundWaterTextureRegion) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses1 = new Entity(mBackgroundHouseTextureRegion1) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses2 = new Entity(mBackgroundHouseTextureRegion2) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	public static final Entity mBackgroundHouses3 = new Entity(mBackgroundHouseTextureRegion3) {
		@Override
		public Entity deepCopy(final org.anddev.andengine.entity.Entity pEntity) {
			final Entity sprite = new Entity(getTextureRegion());

			pEntity.attachChild(sprite);

			return sprite;
		}
	};

	// ===========================================================
	// Methods
	// ===========================================================

	public static final void loadCommonResources() {
		Game.loadTextures(mBackgroundCommonTextureAtlas, mTutorialFontTextureAtlas);
		Game.mEngine.getFontManager().loadFonts(mFont);
	}

	public static final void loadFirstResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas7, mElementsTextureAtlas9);
	}

	public static final void unloadFirstResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas7, mElementsTextureAtlas9);
	}

	public static final void loadSecondResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas8);
	}

	public static final void unloadSecondResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas8);
	}
}
