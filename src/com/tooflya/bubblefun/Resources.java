package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.font.StrokeFont;
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

	public static final BitmapTextureAtlas mFontTextureAtlas1 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR);
	public static final BitmapTextureAtlas mFontTextureAtlas2 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR);

	public static final Font mFont = FontFactory.createFromAsset(mFontTextureAtlas1, Game.context, "font/casual.ttf", 8f * Options.cameraRatioFactor, true, Color.BLACK);
	public static final StrokeFont mWhiteFont = FontFactory.createStrokeFromAsset(mFontTextureAtlas2, Game.context, "font/JOINTBYPIZZADUDE.ttf", 17f * Options.cameraRatioFactor, true, Color.WHITE, 1f, Color.BLACK);

	// ===========================================================
	// Texture Regions
	// ===========================================================

	public static final TiledTextureRegion mBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundGradientTextureAtlas, Game.context, "bg-gr.png", 0, 0, 1, 1);

	public static final TiledTextureRegion mBackgroundGrassTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-grass.png", 2, 349, 1, 1);
	public static final TiledTextureRegion mBackgroundGrassTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-grass-2.png", 2, 601, 1, 1);
	public static final TiledTextureRegion mBackgroundGrassTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-grass-3.png", 2, 750, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-1.png", 388, 341, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-2.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-3.png", 388, 2, 1, 1);
	public static final TiledTextureRegion mBackgroundWaterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-down.png", 2, 502, 1, 1);
	public static final TiledTextureRegion mBackgroundBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "blue-bird-menu-01.png", 388, 668, 1, 1);
	public static final TiledTextureRegion mBackgroundParachuteBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "yb-4.png", 493, 668, 1, 1);
	public static final TiledTextureRegion mBackgroundBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "yb-5.png", 561, 672, 1, 1);

	public static final TiledTextureRegion mBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "cloud.png", 388, 2, 1, 4);
	public static final TiledTextureRegion mNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-sprite.png", 2, 618, 1, 11);
	public static final TiledTextureRegion mSmallNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-small.png", 388, 516, 11, 1);
	public static final TiledTextureRegion mStarsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "end-stars.png", 854, 2, 1, 2);
	public static final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "menu-big-btn.png", 48, 978, 1, 1);
	public static final TiledTextureRegion mPreloadBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "preload-bg.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mPreloadBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "preload-bg-cloud.png", 648, 31, 1, 1);
	public static final TiledTextureRegion mPreloadTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "preload-text.png", 388, 582, 1, 1);
	public static final TiledTextureRegion mLevelEndTotalScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "end-text-total-score.png", 388, 549, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "end-text-numbers.png", 206, 978, 10, 1);
	public static final TiledTextureRegion mBoxNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-sprite-world-2.png", 648, 2, 11, 1);
	public static final TiledTextureRegion mPopupBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "popup.png", 48, 618, 1, 1);

	public static final TiledTextureRegion mBackgroundLogoNameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "main-name.png", 401, 67, 1, 1);
	public static final TiledTextureRegion mTwitterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "tw-btn.png", 950, 139, 1, 1);
	public static final TiledTextureRegion mFacebookTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "fb-btn.png", 948, 189, 1, 1);
	public static final TiledTextureRegion mPlayIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "play.png", 256, 60, 1, 1);
	public static final TiledTextureRegion mMoreIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "more-btn.png", 945, 239, 1, 1);
	public static final TiledTextureRegion mSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "sound-btn.png", 356, 202, 1, 2);
	public static final TiledTextureRegion mSettingsIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "set.png", 128, 785, 1, 1);
	public static final TiledTextureRegion mBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "back-btn.png", 341, 2, 1, 1);
	public static final TiledTextureRegion mTopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lvl-panel.png", 401, 2, 1, 1);
	public static final TiledTextureRegion mLevelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "level-btn.png", 2, 504, 1, 5);
	public static final TiledTextureRegion mBoxesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box.png", 2, 2, 1, 2);
	public static final TiledTextureRegion mBoxesPicture1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-1.png", 401, 504, 1, 1);
	public static final TiledTextureRegion mBoxesPicture2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-2.png", 564, 405, 1, 1);
	public static final TiledTextureRegion mBoxesPicture3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-3.png", 2, 845, 1, 1);
	public static final TiledTextureRegion mBoxesLockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lock.png", 341, 660, 1, 1);
	public static final TiledTextureRegion mBoxesNavigationTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "navi.png", 943, 280, 1, 2);
	public static final TiledTextureRegion mBoxesComingSoonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "coming-soon.png", 780, 273, 1, 1);
	public static final TiledTextureRegion mExitYesbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "accept-btn.png", 883, 513, 1, 2);
	public static final TiledTextureRegion mExitNobuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "decline-btn.png", 256, 202, 1, 2);
	public static final TiledTextureRegion mBoxesLabel1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-1.png", 780, 71, 1, 1);
	public static final TiledTextureRegion mBoxesLabel2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-2.png", 780, 160, 1, 1);
	public static final TiledTextureRegion mBoxesLabel3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-name-3.png", 780, 113, 1, 1);
	public static final TiledTextureRegion mStaticCoinTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "static_coin.jpg", 986, 239, 1, 1);
	public static final TiledTextureRegion mChainTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "chain.png", 186, 785, 1, 1);
	public static final TiledTextureRegion mLockStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "text-box-2.png", 256, 2, 1, 1);
	public static final TiledTextureRegion mLockStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "text-box-3.png", 720, 513, 1, 1);
	public static final TiledTextureRegion mCollectTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "collect.png", 787, 2, 1, 1);
	public static final TiledTextureRegion mExitTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "text-exit.png", 722, 363, 1, 1);
	public static final TiledTextureRegion mOkButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "bigbtn-yes.png", 564, 311, 1, 1);
	public static final TiledTextureRegion mLockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "popup-lock-box.png", 68, 504, 1, 1);
	public static final TiledTextureRegion mPopupStarsNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "numbers-sprite-popup.png", 557, 561, 10, 1);
	public static final TiledTextureRegion mBoxUnlockPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "unlock-bg.png", 564, 358, 1, 1);
	public static final TiledTextureRegion mBoxUnlockButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "unlock-btn.png", 68, 785, 1, 1);
	public static final TiledTextureRegion mBoxesUnlockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "text-popup-unlock-bye.png", 256, 311, 1, 1);
	public static final TiledTextureRegion mBoxPurchaseButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "btn-unlock-bye.png", 401, 782, 1, 1);
	public static final TiledTextureRegion mBoxCollect50TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "50-collect.png", 356, 278, 1, 1);
	public static final TiledTextureRegion mBoxCollect100TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "100-collect.png", 950, 113, 1, 1);
	public static final TiledTextureRegion mSmallLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "logo_small_zero.png", 780, 207, 1, 1);
	public static final TiledTextureRegion mBuyButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "shop-btn.png", 401, 836, 1, 1);
	public static final TiledTextureRegion mShopAvailableTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "shop-num-sprite.png", 985, 280, 1, 5);
	public static final TiledTextureRegion mShopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "shop-panel.png", 454, 660, 1, 1);
	public static final TiledTextureRegion mGetCoinsButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "get-coins-btn.png", 401, 908, 1, 1);

	public static final TiledTextureRegion mStorePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.context, "shop-menu.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mStorePanelTopTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.context, "shop-menu-up.png", 265, 2, 1, 1);
	public static final TiledTextureRegion mStorePanelDownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.context, "shop-menu-down.png", 265, 52, 1, 1);
	public static final TiledTextureRegion mStoreTreesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas7, Game.context, "shop-menu-tree.png", 2, 400, 1, 1);

	public static final EmptyBitmapTextureAtlasSource mLevelGradientBitmap = new EmptyBitmapTextureAtlasSource(2, 512);
	public static final LinearGradientFillBitmapTextureAtlasSourceDecoratorExtended mLevelBackgroundGradientSource = new LinearGradientFillBitmapTextureAtlasSourceDecoratorExtended(mLevelGradientBitmap,
			new RectangleBitmapTextureAtlasSourceDecoratorShape(), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), LinearGradientDirection.BOTTOM_TO_TOP);
	public static final TiledTextureRegion mLevelBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(mBackgroundGradientTextureAtlas2, mLevelBackgroundGradientSource, 0, 0, 1, 1);

	public static final TiledTextureRegion mRegularBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "small-bird.png", 206, 598, 6, 4);
	public static final TiledTextureRegion mTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "game-panel.png", 476, 106, 1, 1);
	public static final TiledTextureRegion mAwesomeText1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "awesome-kill.png", 206, 571, 1, 1);
	public static final TiledTextureRegion mAwesomeText2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "double-hit.png", 206, 987, 1, 1);
	public static final TiledTextureRegion mAwesomeText3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "triple-hit.png", 338, 569, 1, 1);
	public static final TiledTextureRegion mScoreBonusesTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "scores_bonuses.png", 265, 780, 1, 4);
	public static final TiledTextureRegion mDottedLineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "dash-line.png", 458, 216, 1, 1);
	public static final TiledTextureRegion mRestartTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-restart.png", 740, 669, 1, 1);
	public static final TiledTextureRegion mBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "gum-animation.png", 767, 363, 1, 6);
	public static final TiledTextureRegion mFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "feather.png", 338, 406, 1, 2);
	public static final TiledTextureRegion mGlintsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "blesk.png", 692, 808, 1, 3);
	public static final TiledTextureRegion mBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "blue-bird.png", 452, 445, 6, 1);
	public static final TiledTextureRegion mBlueFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "feather_new_blue.png", 338, 360, 1, 2);
	public static final TiledTextureRegion mScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "score.png", 388, 108, 1, 1);
	public static final TiledTextureRegion mBonusTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus.png", 2, 876, 1, 1);
	public static final TiledTextureRegion mAcceleratorsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "speed-wind.png", 740, 530, 6, 1);
	public static final TiledTextureRegion mCoinsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "coin-sprite.png", 388, 191, 4, 4);
	public static final TiledTextureRegion mMenuButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "menu-btn.png", 944, 2, 1, 1);
	public static final TiledTextureRegion mRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "replay-btn.png", 338, 528, 1, 1);
	public static final TiledTextureRegion mLevelSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "sound-btn.png", 338, 452, 1, 2);
	public static final TiledTextureRegion mButtonsLabelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-big-btn.png", 862, 44, 1, 4);
	public static final TiledTextureRegion mSnowFlakesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowflake.png", 1009, 202, 1, 1);
	public static final TiledTextureRegion mSnowyBirdsHatTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snow-hat.png", 326, 987, 1, 1);
	public static final TiledTextureRegion mSnowyBallSpeedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowball-speed.png", 2, 780, 1, 1);
	public static final TiledTextureRegion mSnowyBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowball.png", 400, 531, 1, 1);
	public static final TiledTextureRegion mSnowyTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowy-game-panel.png", 476, 41, 1, 1);
	public static final TiledTextureRegion mLevelWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-level.png", 2, 827, 1, 1);
	public static final TiledTextureRegion mLevelSnowyWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-level-snow.png", 326, 780, 1, 1);
	public static final TiledTextureRegion mSpecialNumbers1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "numbers-sprite-world.png", 453, 363, 11, 1);
	public static final TiledTextureRegion mSpecialNumbers2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "numbers-sprite-snow.png", 452, 404, 11, 1);
	public static final TiledTextureRegion mSnowyBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "small-bird-snow.png", 68, 890, 6, 2);
	public static final TiledTextureRegion mLevelEndPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "end-lvl-popup.png", 447, 508, 1, 1);
	public static final TiledTextureRegion mLevelEndReturnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "buttons-end-menu.png", 384, 397, 1, 1);
	public static final TiledTextureRegion mLevelEndRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "buttons-end-replay.png", 388, 261, 1, 1);
	public static final TiledTextureRegion mLevelEndNextButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "buttons-end-next.png", 385, 329, 1, 1);
	public static final TiledTextureRegion mLevelEndCompleteCaptureTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "end_lvl_text_complete.png", 824, 338, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "end-text-score.png", 379, 498, 1, 1);
	public static final TiledTextureRegion mLevelEndStarsScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "end-text-star.png", 379, 465, 1, 1);
	public static final TiledTextureRegion mAirRegularOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "air-bar.png", 466, 171, 1, 1);
	public static final TiledTextureRegion mAirTwoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "air-fill.png", 458, 202, 1, 1);
	public static final TiledTextureRegion mAngryCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "anrgy-cloud.png", 2, 266, 4, 1);
	public static final TiledTextureRegion mLighingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "light.png", 740, 551, 1, 1);
	public static final TiledTextureRegion mHoldCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "angry-cloud-hold.png", 456, 266, 4, 1);
	public static final TiledTextureRegion mHoldCloudWindTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "angry-cloud-hold-wind.png", 458, 227, 4, 1);
	public static final TiledTextureRegion mAirSnowOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "air-indicator-snow.png", 2, 172, 1, 1);
	public static final TiledTextureRegion mMarkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "wind.png", 740, 530, 1, 1);
	public static final TiledTextureRegion mSpaceBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "small-bird-space.png", 830, 236, 4, 2);
	public static final TiledTextureRegion mGlassesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "glass.png", 356, 296, 3, 1);
	public static final TiledTextureRegion mSpaceBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "blue-bird-space.png", 446, 808, 4, 1);
	public static final TiledTextureRegion mSpaceAcceleratorsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-fast.png", 2, 987, 6, 1);
	public static final TiledTextureRegion mSpaceTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "game-panel-space.png", 2, 108, 1, 1);
	public static final TiledTextureRegion mSpaceBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-asteroid.png", 356, 266, 1, 1);
	public static final TiledTextureRegion mSpaceBallSpeedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "asteroid-fast.png", 356, 316, 1, 1);
	public static final TiledTextureRegion mSpacePlanetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "planet.png", 740, 711, 1, 1);
	public static final TiledTextureRegion mAirSpaceOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-indicator.png", 2, 236, 1, 1);
	public static final TiledTextureRegion mLevelSpaceWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-level-space.png", 844, 202, 1, 1);
	public static final TiledTextureRegion mSpecialNumbers3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "numbers-sprite-space.png", 476, 2, 11, 1);
	public static final TiledTextureRegion mUfoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "ufo.png", 2, 2, 6, 1);
	public static final TiledTextureRegion mMeteoritTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "meteorit.png", 2, 337, 3, 2);
	public static final TiledTextureRegion mBonus1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus-1-button.png", 290, 862, 1, 1);
	public static final TiledTextureRegion mBonus2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus-2-button.png", 359, 827, 1, 1);
	public static final TiledTextureRegion mBonus3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus-3-button.png", 172, 821, 1, 1);
	public static final TiledTextureRegion mBonus4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus-4-button.png", 122, 821, 1, 1);
	public static final TiledTextureRegion mSpaceIndicatorFillTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-indicator-fill.png", 2, 206, 1, 1);
	public static final TiledTextureRegion mGreenLaserTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "lazer-light.png", 740, 519, 1, 1);
	public static final TiledTextureRegion mRedLaserTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "lazer-light-sheep.png", 740, 508, 1, 1);
	public static final TiledTextureRegion mAsteroidBrokenTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-asteroid-broke.png", 379, 531, 1, 2);
	public static final TiledTextureRegion mAirplaneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "space-sheep.png", 388, 141, 1, 1);
	public static final TiledTextureRegion mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "cute_ball_stop.png", 290, 912, 1, 1);

	public static final TiledTextureRegion mSpaceStarsBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas8, Game.context, "space-stars.png", 2, 2, 1, 1);

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
		Game.loadTextures(mBackgroundCommonTextureAtlas, mFontTextureAtlas1, mFontTextureAtlas2);
		Game.engine.getFontManager().loadFonts(mFont, mWhiteFont);
	}

	public static final void loadFirstResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas7);
	}

	public static final void unloadFirstResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas7);
	}

	public static final void loadSecondResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas8);
	}

	public static final void unloadSecondResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas8);
	}
}
