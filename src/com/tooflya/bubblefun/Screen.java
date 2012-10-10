package com.tooflya.bubblefun;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Screen extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int SCREENS_COUNT = 5;

	public static final int MENU = 0;
	public static final int CHOISE = 1;
	public static final int LOAD = 2;
	public static final int LEVEL = 3;

	// ===========================================================
	// Fields
	// ===========================================================

	public static int screen = -1;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Screen() {
		this.loadResources();
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity#onAttached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		this.setIgnoreUpdate(false);
		this.setChildrenIgnoreUpdate(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.Entity#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		this.setIgnoreUpdate(true);
		this.setChildrenIgnoreUpdate(true);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setScene(final Engine pEngine) {
		pEngine.setScene(this);
	}

	// ===========================================================
	// Abstract methods
	// ===========================================================

	public abstract void init();

	public abstract void loadResources();

	public abstract void unloadResources();

	public abstract boolean onBackPressed();
}