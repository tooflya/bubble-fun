package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.util.user.AsyncTaskLoader;
import org.anddev.andengine.util.user.IAsyncCallback;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.managers.ScreenManager;

/**
 * @author Tooflya.com
 * @since
 */
public class PreloadScreen extends Screen implements IAsyncCallback {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Entity mBackground;
	private final Entity mBackgroundCloud;
	public final Entity mTextBar;

	public int updates = 0;
	public int temp = 0;
	private boolean loaded = false;
	private final TimerHandler mTimer = new TimerHandler(0.2f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			updates++;

			/** Changing size of progressbar */
			if (updates < 15) {
				switch (temp) {
				case 0:
					mTextBar.setWidth(85f);
					mTextBar.getTextureRegion().setWidth(85);
					break;
				case 1:
					mTextBar.setWidth(94f);
					mTextBar.getTextureRegion().setWidth(94);
					break;
				case 2:
					mTextBar.setWidth(102f);
					mTextBar.getTextureRegion().setWidth(102);
					break;
				case 3:
					mTextBar.setWidth(112f);
					mTextBar.getTextureRegion().setWidth(112);

					temp = -1;
					break;
				}

				temp++;
			} else {
				if (loaded) {
					switch (ScreenManager.mChangeAction) {
					case 0:
						Game.mScreens.set(Screen.LEVEL);
						break;
					case 1:
						Game.mScreens.set(Screen.MENU);
						break;
					case 3:
						Game.mScreens.set(Screen.BOX);
						break;
					default:
						Game.mScreens.set(Screen.CHOISE);
					}

					/** Register timer of loading progressbar changes */
					unregisterUpdateHandler(mTimer);

					loaded = false;
				}
			}
		}
	});

	// ===========================================================
	// Constructors
	// ===========================================================

	public PreloadScreen() {
		this.mBackground = new Entity(Resources.mPreloadBackgroundTextureRegion, this);
		this.mBackgroundCloud = new Entity(Resources.mPreloadBackgroundCloudTextureRegion, this.mBackground);
		this.mTextBar = new Entity(Resources.mPreloadTextTextureRegion, this.mBackground);

		this.mBackground.create().setBackgroundCenterPosition();
		this.mBackgroundCloud.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY - 50f);
		this.mTextBar.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		updates = 0;
		temp = 0;

		mTextBar.setWidth(112f);
		mTextBar.getTextureRegion().setWidth(112);

		Game.mAdvertisementManager.hideSmall();
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
		if (Options.isMusicEnabled) {
			if (Options.mMainSound.isPlaying()) {
				Options.mMainSound.pause();
			}
			if (Options.mLevelSound.isPlaying()) {
				Options.mLevelSound.pause();
			}
		}

		this.registerUpdateHandler(mTimer);

		/** Start background loader */
		new AsyncTaskLoader().execute(PreloadScreen.this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public void onComplete() {
		loaded = true;
	}

	@Override
	public void workToDo() {
		/**
		 * try { LevelScreen.mBirdsNames = Beta.readJsonFromUrl("http://bubblefun.tooflya.com/birds.php"); } catch (IOException e) { e.printStackTrace(); } catch (JSONException e) { e.printStackTrace(); }
		 **/

		if (ScreenManager.mChangeAction > 0) {
			Resources.unloadSecondResources();
			Resources.loadFirstResources();
		} else {
			Resources.unloadFirstResources();
			Resources.loadSecondResources();
		}
	}
}
