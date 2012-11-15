package com.planetbattle.modifiers;

import com.planetbattle.interfaces.IAnimationListener;
import com.tooflya.bubblefun.entity.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public abstract class BaseModifier implements IAnimationListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean isAnimationRunnig = false;
	private boolean detachAfterFinish;

	protected int animationSleep;
	protected int animationSleepForward;
	protected float animationFromState;
	protected float animationToState;

	private int resetCount = 1;

	protected Entity parent;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Instantiates a new base modifier.
	 * 
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 */
	public BaseModifier(final float animationFromState, final float animationToState) {
		this(0, animationFromState, animationToState, false);
	}

	/**
	 * Instantiates a new base modifier.
	 * 
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 * @param detachAfterFinish
	 *            the detach after finish
	 */
	public BaseModifier(final float animationFromState, final float animationToState, final boolean detachAfterFinish) {
		this(0, animationFromState, animationToState, detachAfterFinish);
	}

	/**
	 * Instantiates a new base modifier.
	 * 
	 * @param animationSleep
	 *            the animation sleep
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 */
	public BaseModifier(final int animationSleep, final float animationFromState, final float animationToState, final boolean detachAfterFinish) {
		this.isAnimationRunnig = true;
		this.detachAfterFinish = detachAfterFinish;
		this.animationFromState = animationFromState;
		this.animationToState = animationToState;
		this.animationSleep = animationSleep;
		this.animationSleepForward = animationSleep;
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Update.
	 */
	public void update() {
		this.runAnimation();
	}

	/**
	 * Sets the parent.
	 * 
	 * @param parent
	 *            the new parent
	 */
	public void setParent(final Entity parent) {
		this.parent = parent;
	}

	/**
	 * Sets the detach after finish.
	 * 
	 * @param detachAfterFinish
	 *            the new detach after finish
	 */
	public void setDetachAfterFinish(final boolean detachAfterFinish) {
		this.detachAfterFinish = detachAfterFinish;
	}

	/**
	 * Checks if is detach after finish.
	 * 
	 * @return true, if is detach after finish
	 */
	public boolean isDetachAfterFinish() {
		return this.detachAfterFinish;
	}

	/**
	 * Reset animation.
	 */
	public void reset() {
		this.resetCount--;
		if (this.detachAfterFinish) {
			return;
		}

		this.isAnimationRunnig = true;
		this.onAnimationStarted();
	}

	/**
	 * Run animation.
	 * 
	 * @return true, if successful
	 */
	protected boolean runAnimation() {
		if (!this.isAnimationRunnig || this.resetCount > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Stop animation.
	 */
	protected void stopAnimation() {
		this.isAnimationRunnig = false;
		this.onAnimationFinished();
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
	public void onAnimationStarted() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationFinished()
	 */
	@Override
	public void onAnimationFinished() {
		if (this.detachAfterFinish) {
			// TODO: Remove from list
		}
	}

	// ===========================================================
	// Classes
	// ===========================================================

}
