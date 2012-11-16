package com.tooflya.bubblefun.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.planetbattle.managers.ScreensManager;
import com.tooflya.bubblefun.Camera;
import com.tooflya.bubblefun.entity.Entity;
import com.tooflya.bubblefun.interfaces.IEntityEvents;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Screen extends Entity implements IEntityEvents {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int SCREENS_COUNT = 5;

	public static final int SPLASH = 0;
	public static final int LOADING = 1;
	public static final int MENU = 2;
	public static final int BOX = 3;
	public static final int EXIT = 4;

	public final static SpriteBatch mSpriteBatch = new SpriteBatch();

	// ===========================================================
	// Fields
	// ===========================================================

	protected ScreensManager mScreensManager;

	private Screen mChildScreen;

	private boolean mIsChildScreenModalUpdate;
	private boolean mIsChildScreenModalDraw;
	private boolean mIsChildScreenModalTouch;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Screen(final ScreensManager pScreensManager, final Camera pCamera) {
		this.mSpriteBatch.setProjectionMatrix(pCamera.combined);

		this.mScreensManager = pScreensManager;

		this.mIsChildScreenModalUpdate = false;
		this.mIsChildScreenModalDraw = false;
		this.mIsChildScreenModalTouch = false;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	@Override
	public void onManagedDraw(final float pSecondsElapsed) {
		super.onManagedDraw(pSecondsElapsed);

		if (this.hasChildScreen()) {
			this.mChildScreen.onManagedDraw(pSecondsElapsed);
		}
	}

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.hasChildScreen()) {
			this.mChildScreen.onManagedUpdate(pSecondsElapsed);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setChildScreen(final Screen pScreen, final boolean pIsChildScreenModalUpdate, final boolean pIsChildScreenModalDraw, final boolean pIsChildScreenModalTouch) {
		this.mChildScreen = pScreen;

		this.mIsChildScreenModalUpdate = pIsChildScreenModalUpdate;
		this.mIsChildScreenModalDraw = pIsChildScreenModalDraw;
		this.mIsChildScreenModalTouch = pIsChildScreenModalTouch;

		pScreen.onAttached();
	}

	public void clearChildScreen() {
		this.mChildScreen = null;

		this.mIsChildScreenModalUpdate = false;
		this.mIsChildScreenModalDraw = false;
		this.mIsChildScreenModalTouch = false;
	}

	public boolean hasChildScreen() {
		return this.mChildScreen != null;
	}

	// ===========================================================
	// Abstract methods
	// ===========================================================

	public abstract void onBackPressed();

	// ===========================================================
	// Classes
	// ===========================================================

}
