package com.tooflya.bubblefun.screens;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Text;

/**
 * @author Tooflya.com
 * @since
 */
public class CreditsScreen extends ReflectionScreen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mTopPanel;
	private final Entity mSmallLogo;

	private final ButtonScaleable mBackButton;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CreditsScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses2.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass1.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mTopPanel = new Entity(Resources.mTopPanelTextureRegion, this.mBackground);
		this.mSmallLogo = new Entity(Resources.mSmallLogoTextureRegion, this.mBackground);

		this.mBackButton = new ButtonScaleable(Resources.mBackButtonTextureRegion, this.mBackground) {

			/* (non-Javadoc)
			 * @see com.tooflya.bubblefun.entities.Button#onClick()
			 */
			@Override
			public void onClick() {
				Game.mScreens.set(Screen.MORE);
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mTopPanel.create().setPosition(0, 0);
		this.mSmallLogo.create().setCenterPosition(Options.cameraCenterX, 100f);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 150f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_1")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 120f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_2")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 90f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_3")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 60f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_4")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 30f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_5")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY, Resources.mFont, Game.getString("credits_6")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 30f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_7")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 60f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_8")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 90f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_9")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 120f * Options.cameraRatioFactor, Resources.mFont, Game.getString("credits_10")));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 180f * Options.cameraRatioFactor, Resources.mFont, Game.getString("www.tooflya.com")));

		try {
			final PackageInfo pInfo = Game.mInstance.getPackageManager().getPackageInfo(Game.mInstance.getPackageName(), 0);

			this.attachChild(new Text(Options.screenWidth - 70f * Options.cameraRatioFactor, Options.screenHeight - 50f * Options.cameraRatioFactor, Resources.mFont, Game.getString("version") + pInfo.versionName));
			this.attachChild(new Text(Options.screenWidth - 80f * Options.cameraRatioFactor, Options.screenHeight - 25f * Options.cameraRatioFactor, Resources.mFont, Game.getString("build") + pInfo.versionCode));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		Game.mAdvertisementManager.hideSmall();
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		this.clearUpdateHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		Game.mScreens.set(Screen.MORE);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}