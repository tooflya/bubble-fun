package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.util.user.AsyncTaskLoader;
import org.anddev.andengine.util.user.IAsyncCallback;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.ScreenManager;

public class PreloadScreen extends Screen implements IAsyncCallback {

	private final Sprite mBackground;
	public final Sprite mTextBar;

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
						Game.screens.set(Screen.LEVEL);
						break;
					case 1:
						Game.screens.set(Screen.MENU);
						break;
					case 3:
						Game.screens.set(Screen.BOX);
						break;
					default:
						Game.screens.set(Screen.CHOISE);
					}

					/** Register timer of loading progressbar changes */
					unregisterUpdateHandler(mTimer);

					loaded = false;
				}
			}
		}
	});

	public PreloadScreen() {
		this.mBackground = new Sprite(Resources.mPreloadBackgroundTextureRegion, this);
		this.mTextBar = new Sprite(Resources.mPreloadTextTextureRegion, this.mBackground);

		this.mBackground.create().setBackgroundCenterPosition();

		mTextBar.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);

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
		if (ScreenManager.mChangeAction > 0) {
			Resources.unloadSecondResources();
			Resources.loadFirstResources();
		} else {
			Resources.unloadFirstResources();
			Resources.loadSecondResources();
		}
	}
}
