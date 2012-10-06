package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Airgum;
import com.tooflya.airbubblegum.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private Airgum lastAirgum = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
		this.setOnSceneTouchListener(this);
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

		this.registerUpdateHandler(Game.GameTimer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		this.unregisterUpdateHandler(Game.GameTimer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.close();

		return true;
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (this.lastAirgum == null)
			{
				this.lastAirgum = (Airgum) Game.world.airgums.create();
				this.lastAirgum.setCenterPosition(pTouchEvent.getX(), pTouchEvent.getY());
			}
			this.lastAirgum.setIsScale(true);
			break;
		case TouchEvent.ACTION_UP:
			this.lastAirgum.setIsScale(false);
			this.lastAirgum = null;
			break;
		}

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}