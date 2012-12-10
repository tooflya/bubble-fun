package com.tooflya.bubblefun;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.LinearGradientFillBitmapTextureAtlasSourceDecorator.LinearGradientDirection;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.decorator.shape.RectangleBitmapTextureAtlasSourceDecoratorShape;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;

import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Sprite;

public class Resources {

	// ===========================================================
	// Texture Atlases
	// ===========================================================

	public static final BitmapTextureAtlas mBackgroundGradientTextureAtlas = new BitmapTextureAtlas(2, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundGradientTextureAtlas2 = new BitmapTextureAtlas(2, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mBackgroundElementsTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundCommonTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mBackgroundCommonTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	public static final BitmapTextureAtlas mElementsTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas3 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas4 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	public static final BitmapTextureAtlas mElementsTextureAtlas5 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Texture Regions
	// ===========================================================

	public static final TiledTextureRegion mBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundGradientTextureAtlas, Game.context, "bg-gr.png", 0, 0, 1, 1);

	public static final TiledTextureRegion mBackgroundGrassTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-grass.png", 381, 0, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-1.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-2.png", 0, 320, 1, 1);
	public static final TiledTextureRegion mBackgroundHouseTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-house-3.png", 0, 662, 1, 1);
	public static final TiledTextureRegion mBackgroundWaterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundElementsTextureAtlas, Game.context, "bg-down.png", 381, 150, 1, 1);

	public static final TiledTextureRegion mBackgroundCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "cloud.png", 1, 1, 1, 4);
	public static final TiledTextureRegion mNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-sprite.png", 0, 512, 1, 11);
	public static final TiledTextureRegion mSmallNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "numbers-small.png", 50, 512, 11, 1);
	public static final TiledTextureRegion mStarsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "end-stars.png", 50, 700, 1, 2);
	public static final TiledTextureRegion mButtonsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "menu-big-btn.png", 737, 390, 1, 1);

	public static final TiledTextureRegion mPreloadBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "preload-bg.png", 256, 0, 1, 1);
	public static final TiledTextureRegion mPreloadTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas, Game.context, "preload-text.png", 737, 350, 1, 1);

	public static final TiledTextureRegion mLevelEndTotalScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas2, Game.context, "end-text-total-score.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas2, Game.context, "end-text-numbers.png", 2, 30, 10, 1);
	public static final TiledTextureRegion mBoxNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas2, Game.context, "numbers-sprite-world-2.png", 2, 52, 11, 1);
	public static final TiledTextureRegion mPopupBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundCommonTextureAtlas2, Game.context, "popup.png", 0, 100, 1, 1);

	public static final TiledTextureRegion mBackgroundLogoNameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "main-name.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mTwitterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "tw-btn.png", 0, 240, 1, 1);
	public static final TiledTextureRegion mFacebookTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "fb-btn.png", 45, 240, 1, 1);
	public static final TiledTextureRegion mPlayIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "play.png", 90, 240, 1, 1);
	public static final TiledTextureRegion mMoreIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "more-btn.png", 230, 240, 1, 1);
	public static final TiledTextureRegion mSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "sound-btn.png", 270, 240, 1, 2);
	public static final TiledTextureRegion mSettingsIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "set.png", 310, 240, 1, 1);
	public static final TiledTextureRegion mBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "back-btn.png", 153, 380, 1, 1);
	public static final TiledTextureRegion mTopPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lvl-panel.png", 0, 586, 1, 1);
	public static final TiledTextureRegion mLevelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "level-btn.png", 0, 672, 1, 5);
	public static final TiledTextureRegion mBoxesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box.png", 400, 0, 1, 2);
	public static final TiledTextureRegion mBoxesPicture1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-1.png", 400, 500, 1, 1);
	public static final TiledTextureRegion mBoxesPicture2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-2.png", 400, 651, 1, 1);
	public static final TiledTextureRegion mBoxesPicture3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "box-picture-3.png", 400, 802, 1, 1);
	public static final TiledTextureRegion mBoxesLockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "lock.png", 620, 900, 1, 1);
	public static final TiledTextureRegion mBoxesNavigationTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "navi.png", 740, 900, 1, 2);
	public static final TiledTextureRegion mBoxesComingSoonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas1, Game.context, "coming-soon.png", 800, 900, 1, 1);

	public static final TiledTextureRegion mChainTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "chain.png", 2, 2, 1, 1);
	public static final TiledTextureRegion mLockStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "text-box-2.png", 2, 220, 1, 1);
	public static final TiledTextureRegion mLockStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "text-box-3.png", 2, 260, 1, 1);
	public static final TiledTextureRegion mCollectTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "collect.png", 2, 310, 1, 1);
	public static final TiledTextureRegion mExitTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "text-exit.png", 2, 380, 1, 1);
	public static final TiledTextureRegion mOkButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "bigbtn-yes.png", 340, 380, 1, 1);
	public static final TiledTextureRegion mLockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "popup-lock-box.png", 340, 430, 1, 1);
	public static final TiledTextureRegion mPopupStarsNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "numbers-sprite-popup.png", 340, 730, 10, 1);
	public static final TiledTextureRegion mBoxUnlockPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "unlock-bg.png", 20, 830, 1, 1);
	public static final TiledTextureRegion mBoxUnlockButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "unlock-btn.png", 180, 830, 1, 1);
	public static final TiledTextureRegion mBoxesUnlockTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "text-popup-unlock-bye.png", 680, 730, 1, 1);
	public static final TiledTextureRegion mBoxPurchaseButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "btn-unlock-bye.png", 680, 930, 1, 1);
	public static final TiledTextureRegion mBoxCollect50TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "50-collect.png", 880, 930, 1, 1);
	public static final TiledTextureRegion mBoxCollect100TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas5, Game.context, "100-collect.png", 960, 930, 1, 1);

	public static final TiledTextureRegion mExitYesbuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas2, Game.context, "accept-btn.png", 350, 0, 1, 2);
	public static final TiledTextureRegion mExitNobuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas2, Game.context, "decline-btn.png", 350, 150, 1, 2);

	public static final TiledTextureRegion mBoxesLabel1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas2, Game.context, "box-name-1.png", 350, 242, 1, 1);
	public static final TiledTextureRegion mBoxesLabel2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas2, Game.context, "box-name-2.png", 350, 279, 1, 1);
	public static final TiledTextureRegion mBoxesLabel3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas2, Game.context, "box-name-3.png", 350, 321, 1, 1);

	public static final EmptyBitmapTextureAtlasSource mLevelGradientBitmap = new EmptyBitmapTextureAtlasSource(2, 512);
	public static final LinearGradientFillBitmapTextureAtlasSourceDecorator mLevelBackgroundGradientSource = new LinearGradientFillBitmapTextureAtlasSourceDecorator(mLevelGradientBitmap,
			new RectangleBitmapTextureAtlasSourceDecoratorShape(), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255), LinearGradientDirection.BOTTOM_TO_TOP);

	public static final TextureRegion mLevelBackgroundGradientTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(mBackgroundGradientTextureAtlas2, mLevelBackgroundGradientSource, 0, 0);
	public static final TiledTextureRegion mRegularBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "small-bird.png", 0, 0, 6, 4);
	public static final TiledTextureRegion mTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "game-panel.png", 0, 177, 1, 1);
	public static final TiledTextureRegion mAwesomeText1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "awesome-kill.png", 0, 237, 1, 1);
	public static final TiledTextureRegion mAwesomeText2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "double-hit.png", 0, 259, 1, 1);
	public static final TiledTextureRegion mAwesomeText3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "triple-hit.png", 0, 279, 1, 1);
	public static final TiledTextureRegion mScoreBonusesTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "scores_bonuses.png", 0, 303, 1, 4);
	public static final TiledTextureRegion mDottedLineTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "dash-line.png", 0, 1019, 1, 1);
	public static final TiledTextureRegion mRestartTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-restart.png", 0, 411, 1, 1);
	public static final TiledTextureRegion mBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "gum-animation.png", 0, 448, 1, 6);
	public static final TiledTextureRegion mFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "feather.png", 51, 448, 1, 2);
	public static final TiledTextureRegion mGlintsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "blesk.png", 51, 489, 1, 3);
	public static final TiledTextureRegion mBlueBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "blue-bird.png", 51, 513, 6, 1);
	public static final TiledTextureRegion mBlueFeathersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "feather_new_blue.png", 51, 572, 1, 2);
	public static final TiledTextureRegion mScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "score.png", 51, 613, 1, 1);
	public static final TiledTextureRegion mMarkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "wind.png", 51, 641, 1, 1);
	public static final TiledTextureRegion mBonusTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "bonus.png", 51, 651, 1, 1);
	public static final TiledTextureRegion mAcceleratorsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "speed-wind.png", 51, 747, 6, 1);
	public static final TiledTextureRegion mCoinsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "coin-sprite.png", 51, 773, 4, 4);
	public static final TiledTextureRegion mSprikeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "sprike.png", 51, 838, 1, 1);
	public static final TiledTextureRegion mBonusSplasheTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "splash-bonus.png", 126, 838, 1, 1);
	public static final TiledTextureRegion mMenuButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "menu-btn.png", 222, 838, 1, 1);
	public static final TiledTextureRegion mRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "replay-btn.png", 298, 838, 1, 1);
	public static final TiledTextureRegion mLevelSoundIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "sound-btn.png", 350, 838, 1, 2);
	public static final TiledTextureRegion mButtonsLabelsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-big-btn.png", 512, 351, 1, 4);
	public static final TiledTextureRegion mSnowFlakesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowflake.png", 512, 504, 1, 1);
	public static final TiledTextureRegion mSnowyBirdsHatTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snow-hat.png", 512, 526, 1, 1);
	public static final TiledTextureRegion mSnowyBallSpeedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowball-speed.png", 512, 541, 1, 1);
	public static final TiledTextureRegion mSnowyBubbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowball.png", 512, 635, 1, 1);
	public static final TiledTextureRegion mSnowyTopGamePanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "snowy-game-panel.png", 512, 665, 1, 1);
	public static final TiledTextureRegion mLevelWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-level.png", 512, 725, 1, 1);
	public static final TiledTextureRegion mLevelSnowyWordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "text-level-snow.png", 512, 764, 1, 1);
	public static final TiledTextureRegion mSpecialNumbers1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "numbers-sprite-world.png", 512, 806, 11, 1);
	public static final TiledTextureRegion mSpecialNumbers2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "numbers-sprite-snow.png", 512, 842, 11, 1);
	public static final TiledTextureRegion mSnowyBirdsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas3, Game.context, "small-bird-snow.png", 512, 920, 6, 2);

	public static final TiledTextureRegion mLevelEndPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "end-lvl-popup.png", 0, 0, 1, 1);
	public static final TiledTextureRegion mLevelEndReturnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "buttons-end-menu.png", 286, 63, 1, 1);
	public static final TiledTextureRegion mLevelEndRestartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "buttons-end-replay.png", 286, 126, 1, 1);
	public static final TiledTextureRegion mLevelEndNextButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "buttons-end-next.png", 286, 189, 1, 1);
	public static final TiledTextureRegion mLevelEndCompleteCaptureTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "end_lvl_text_complete.png", 0, 295, 1, 1);
	public static final TiledTextureRegion mLevelEndScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "end-text-score.png", 0, 414, 1, 1);
	public static final TiledTextureRegion mLevelEndStarsScoreTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "end-text-star.png", 0, 442, 1, 1);
	public static final TiledTextureRegion mAirOneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "air-bar.png", 512, 0, 1, 1);
	public static final TiledTextureRegion mAirTwoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "air-fill.png", 512, 26, 1, 1);
	public static final TiledTextureRegion mRegularBonusPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "bonus-panel.png", 512, 60, 1, 1);
	public static final TiledTextureRegion mSnowBonusPanelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "bonus-panel-snow.png", 512, 161, 1, 1);
	public static final TiledTextureRegion mBonusButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "bonus-1-button.png", 512, 262, 1, 1);
	public static final TiledTextureRegion mBonusButton1SnowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "bonus-1-button-snow.png", 512, 294, 1, 1);
	public static final TiledTextureRegion mAngryCloudTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "anrgy-cloud.png", 512, 400, 4, 1);
	public static final TiledTextureRegion mLighingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mElementsTextureAtlas4, Game.context, "light.png", 512, 500, 1, 1);

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

	public static final void loadCommonResources() {
		Game.loadTextures(mBackgroundCommonTextureAtlas, mBackgroundCommonTextureAtlas2);
	}

	public static final void loadFirstResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas2, mElementsTextureAtlas5);
	}

	public static final void unloadFirstResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas, mBackgroundElementsTextureAtlas, mElementsTextureAtlas1, mElementsTextureAtlas2, mElementsTextureAtlas5);
	}

	public static final void loadSecondResources() {
		Game.loadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas4);
	}

	public static final void unloadSecondResources() {
		Game.unloadTextures(mBackgroundGradientTextureAtlas2, mElementsTextureAtlas3, mElementsTextureAtlas4);
	}
}
