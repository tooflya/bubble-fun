package com.planetbattle.modifiers;

import com.tooflya.bubblefun.interfaces.IAnimationListener;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class BaseModifier<T> implements IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private T mEntity;

	protected float mSecondsElapsed;

	protected float mAnimationFromState;
	protected float mAnimationToState;

	protected float mAnimationStep;

	private boolean finished = true;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BaseModifier(final float pAnimationTime, final float pAnimationFromState, final float pAnimationToState) {
		this.mAnimationFromState = pAnimationFromState;
		this.mAnimationToState = pAnimationToState;

		this.mAnimationStep = (this.mAnimationToState - this.mAnimationFromState) / pAnimationTime;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void reset() {
		this.finished = false;
	}

	public boolean onManagedUpdate(final float pSecondsElapsed) {
		if (this.finished) {
			return false;
		}

		return true;
	}

	public void setEntity(final T pEntity) {
		this.mEntity = pEntity;
	}

	public T getEntity() {
		return this.mEntity;
	}

	// ===========================================================
	// Events
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationStarted()
	 */
	@Override
	public void onStarted() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationFinished()
	 */
	@Override
	public void onFinished() {

	}

	// ===========================================================
	// Classes
	// ===========================================================

}
