package com.tooflya.bubblefun.screens;

import org.anddev.andengine.util.user.AsyncTaskLoader;
import org.anddev.andengine.util.user.IAsyncCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Network;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Text;

/**
 * @author Tooflya.com
 * @since
 */
public class RatingScreen extends ReflectionScreen implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mTopPanel;

	private final ButtonScaleable mBackButton;

	public static JSONObject mRatingObject;

	// ===========================================================
	// Constructors
	// ===========================================================

	public RatingScreen() {
		this.mBackground = Resources.mBackgroundGradient.deepCopy(this);
		this.mBackgroundHouses = Resources.mBackgroundHouses2.deepCopy(this.mBackground);
		this.mBackgroundGrass = Resources.mBackgroundGrass2.deepCopy(this.mBackground);
		this.mBackgroundWater = Resources.mBackgroundWater.deepCopy(this.mBackground);

		this.mTopPanel = new Entity(Resources.mTopPanelTextureRegion, this.mBackground);

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

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f - Screen.ADS_PADDING);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	ProgressDialog progress;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		Game.mAdvertisementManager.showSmall();

		Game.mInstance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progress = ProgressDialog.show(Game.mInstance, "Please wait a moment...", "Please wait a moment while loaded rating results...", false);
			}
		});

		Game.mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				RatingScreen.this.detachChildren();

				RatingScreen.this.attachChild(RatingScreen.this.mBackground);
				RatingScreen.this.mBackground.create().setBackgroundCenterPosition();
			}
		});

		new AsyncTaskLoader().execute(RatingScreen.this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#workToDo()
	 */
	@Override
	public void workToDo() {
		if (Network.isNetworkAvailable()) {
			Network.getRating();
		}

		this.initRating();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.background.IAsyncCallback#onComplete()
	 */
	@Override
	public void onComplete() {

		progress.cancel();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void initRating() {

		boolean visible = false;

		int z = 0;
		boolean u = false;

		final String name = Game.mDatabase.getRatingName();

		JSONArray data = null;
		try {
			data = mRatingObject.getJSONArray("response");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < data.length() && z < 13; i++) {
				JSONObject a = null;
				try {
					a = data.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				final Text t1 = new Text(50f * Options.cameraRatioFactor, (100f + (30f * z)) * Options.cameraRatioFactor, Resources.mFont, (i + 1) + ". " + a.getString("name"), true);
				final Text t2 = new Text(Options.screenCenterX + 100f * Options.cameraRatioFactor, (100f + (30f * z)) * Options.cameraRatioFactor, Resources.mFont, a.getString("score"), true);

				if (a.getString("name").equals(name)) {
					t1.setColor(1, 0, 0);
					t2.setColor(1, 0, 0);

					visible = true;
				} else {
					if (!visible && i >= 7) {
						u = true;

						continue;
					}
				}

				if (u) {
					this.attachChild(new Text(50f * Options.cameraRatioFactor, (100f + (30f * z)) * Options.cameraRatioFactor, Resources.mFont, ".....", true));
					z += 2;
					this.attachChild(new Text(50f * Options.cameraRatioFactor, (100f + (30f * (z))) * Options.cameraRatioFactor, Resources.mFont, ".....", true));

					t1.setPosition(t1.getX(), t1.getY() + 30 * Options.cameraRatioFactor, true);
					t2.setPosition(t2.getX(), t2.getY() + 30 * Options.cameraRatioFactor, true);

					u = false;
					visible = true;
				}

				this.attachChild(t1);
				this.attachChild(t2);

				z++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}