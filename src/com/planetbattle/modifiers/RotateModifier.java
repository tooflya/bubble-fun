package com.planetbattle.modifiers;

/**
 * @author Tooflya.com
 * @since
 */
public class RotateModifier extends BaseModifier {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new rotate modifier.
	 * 
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 */
	public RotateModifier(float animationFromState, float animationToState) {
		super(animationFromState, animationToState);
	}

	/**
	 * Instantiates a new rotate modifier.
	 * 
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 * @param detachAfterFinish
	 *            the detach after finish
	 */
	public RotateModifier(float animationFromState, float animationToState, boolean detachAfterFinish) {
		super(animationFromState, animationToState, detachAfterFinish);
	}

	/**
	 * Instantiates a new rotate modifier.
	 * 
	 * @param animationSleep
	 *            the animation sleep
	 * @param animationFromState
	 *            the animation from state
	 * @param animationToState
	 *            the animation to state
	 * @param detachAfterFinish
	 *            the detach after finish
	 */
	public RotateModifier(int animationSleep, float animationFromState, float animationToState, boolean detachAfterFinish) {
		super(animationSleep, animationFromState, animationToState, detachAfterFinish);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	protected boolean runAnimation() {
		if (!super.runAnimation()) {
			return false;
		}

		if (this.animationSleepForward == 0) {
			if (this.parent.getRotation() < this.animationToState) {
				this.parent.setRotation(this.parent.getRotation() + 20 / (this.animationSleep + 1));
			} else {
				this.stopAnimation();
			}
		} else {
			this.animationSleepForward--;
		}

		return true;
	}

	@Override
	public void reset() {
		super.reset();

		this.parent.setRotation(animationFromState);
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
		super.onAnimationStarted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.planetbattle.interfaces.IAnimationListener#onAnimationFinished()
	 */
	@Override
	public void onAnimationFinished() {
		super.onAnimationFinished();
	}

	// ===========================================================
	// Classes
	// ===========================================================

}
