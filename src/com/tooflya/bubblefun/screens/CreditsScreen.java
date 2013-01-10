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
				Game.screens.set(Screen.MORE);
			}
		};

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundHouses.create().setPosition(0, Options.cameraHeight - this.mBackgroundHouses.getHeight());
		this.mBackgroundGrass.create().setPosition(0, Options.cameraHeight - this.mBackgroundGrass.getHeight());
		this.mBackgroundWater.create().setPosition(0, Options.cameraHeight - this.mBackgroundWater.getHeight());

		this.mTopPanel.create().setPosition(0, 0);
		this.mSmallLogo.create().setCenterPosition(Options.cameraCenterX, 100f);

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 300f, Resources.mWhiteFont, "The premier debugging environemnt for"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 250f, Resources.mWhiteFont, "Opera Presto-based browsers. Opera Drag-"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 200f, Resources.mWhiteFont, "onfly is available directly from the Opera"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 150f, Resources.mWhiteFont, "browser, no extra download requierd."));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY - 50f, Resources.mWhiteFont, "Opera Dragonfly contains a full suite of"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY, Resources.mWhiteFont, "tools including DOM, CSS and Network"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 50f, Resources.mWhiteFont, "Inspectors, a JavaScript Debugger, Com-"));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 100f, Resources.mWhiteFont, "mand Line and Error Console."));
		this.attachChild(new Text(Options.screenCenterX, Options.screenCenterY + 200f, Resources.mWhiteFont, "Tooflya Inc."));

		try {
			final PackageInfo pInfo = Game.instance.getPackageManager().getPackageInfo(Game.instance.getPackageName(), 0);

			this.attachChild(new Text(Options.screenWidth - 140f, Options.screenHeight - 90f, Resources.mWhiteFont, "Version: " + pInfo.versionName));
			this.attachChild(new Text(Options.screenWidth - 160f, Options.screenHeight - 40f, Resources.mWhiteFont, "Build: " + pInfo.versionCode));
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
		Game.screens.set(Screen.MORE);
	}

	// ===========================================================
	// Methods
	// ===========================================================

}