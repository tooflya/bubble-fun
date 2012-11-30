package com.tooflya.bubblefun.screens;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;

import com.tooflya.bubblefun.Options;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class Screen extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================

	public static final int SCREENS_COUNT = 11;

	public static final int NO_SCREEN = -1;

	public static final int MENU = 0;
	public static final int CHOISE = 1;
	public static final int LEVEL = 2;
	public static final int LEVELEND = 3;
	public static final int EXIT = 4;
	public static final int PAUSE = 5;
	public static final int BOX = 7;
	public static final int MORE = 8;
	public static final int CREDITS = 9;
	public static final int PRELOAD = 10;

	// ===========================================================
	// Fields
	// ===========================================================

	public static int screen = -1;

	private float mDeltaTiming = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

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

	/** Must be overrided. */
	public void onPostAttached() {
		throw new IllegalStateException("Sorry, but this method must be overrided by child Class.");
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

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#attachChild(org.anddev.andengine.entity.IEntity)
	 */
	@Override
	public void attachChild(final IEntity pEntity) {
		super.attachChild(pEntity);

		/** This section is scale object to the real size for adapt size of entity to the screen resolution. */
		pEntity.setScaleCenter(0, 0);
		pEntity.setScale(Options.cameraRatioFactor);

		/** After scale action we need to find center of entity position. */
		if (((Shape) pEntity).getWidthScaled() > Options.cameraWidth || ((Shape) pEntity).getHeightScaled() > Options.cameraHeight) {
			pEntity.setPosition((Options.screenWidth - ((Shape) pEntity).getWidthScaled()) / 2, (Options.screenHeight - ((Shape) pEntity).getHeightScaled()) / 2);
		}
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.scene.Scene#onManagedUpdate(float)
	 */
	protected void onManagedUpdate(final float pSecondsElapsed) {
		this.mDeltaTiming += pSecondsElapsed;
		if (this.mDeltaTiming < Options.framesPerSeconds) {
			return;
		} else {
			super.onManagedUpdate(Options.framesPerSeconds);
			this.mDeltaTiming -= Options.framesPerSeconds;
			while (this.mDeltaTiming >= Options.framesPerSeconds) {
				super.onManagedUpdate(Options.framesPerSeconds);
				this.mDeltaTiming -= Options.framesPerSeconds;
			}
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setScene(final Engine pEngine) {
		pEngine.setScene(this);
	}

	public void onBackPressed() {
	}
}
