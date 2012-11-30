package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.util.user.AsyncTaskLoader;
import org.anddev.andengine.util.user.IAsyncCallback;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.EntityManager;
import com.tooflya.bubblefun.managers.ScreenManager;

public class PreloadScreen extends Screen implements IAsyncCallback {

	private final Sprite mBackground;
	public final Sprite mTextBar;

	public EntityManager<Sprite> lines;
	public EntityManager<Sprite> circles;

	public int updates = 0;
	private boolean loaded = false;
	private final TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			updates++;

			/** Changing size of progressbar */
			if (updates < 70) {

				if (updates == 5) {
					/** Start background loader */
					new AsyncTaskLoader().execute(PreloadScreen.this);
				}

				for (int i = 10; i > 5; i--) {
					final Sprite sprite = circles.getByIndex(i);

					if (sprite.getScaleX() < 1f) {
						sprite.setScale(sprite.getScaleX() + 0.16f);
					}
					else {
						sprite.setScale(1f);
					}

					if (sprite.getScaleX() < 1f) {
						break;
					}
				}
			} else {
				if (loaded) {
					switch (ScreenManager.mChangeAction) {
					case 0:
						Game.screens.set(Screen.LEVEL);
						break;
					case 1:
						Game.screens.set(Screen.MENU);
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

		this.lines = new EntityManager<Sprite>(2, new Sprite(Resources.mPreloadLineTextureRegion, this.mBackground));
		this.circles = new EntityManager<Sprite>(11, new Sprite(Resources.mPreloadCirclesTextureRegion, this.mBackground));

		this.mBackground.create().setBackgroundCenterPosition();

		mTextBar.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY + 50f);

		this.circles.create().setVisible(false);

		for (int i = 0; i < 5; i++) {
			this.circles.create().setCenterPosition(Options.cameraCenterX - (35f * (i - 2)), Options.cameraCenterY);
		}

		for (int i = 0; i < 5; i++) {
			final Sprite sprite = this.circles.create();
			sprite.setCenterPosition(Options.cameraCenterX - (35f * (i - 2)), Options.cameraCenterY);
			sprite.setCurrentTileIndex(1);
			sprite.setScale(0f);
		}

		Sprite line;

		line = this.lines.create();
		line.setPosition(0, 0);

		line = this.lines.create();
		line.setPosition(this.mBackground.getWidth() - line.getWidth(), 0);

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

		for (int i = 10; i > 5; i--) {
			final Sprite sprite = circles.getByIndex(i);

			sprite.setScale(0f);
		}

		this.registerUpdateHandler(mTimer);
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
