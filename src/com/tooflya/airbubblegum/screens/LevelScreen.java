package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Screen;

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
	public void onDetached() {
		super.onDetached();
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
		case TouchEvent.ACTION_UP:
			Game.world.airgums.create().setCenterPosition(pTouchEvent.getX(), pTouchEvent.getY());
			break;
		}

		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}