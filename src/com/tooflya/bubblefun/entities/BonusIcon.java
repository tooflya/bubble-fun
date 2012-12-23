package com.tooflya.bubblefun.entities;

import com.tooflya.bubblefun.factories.BubbleFactory;

public class BonusIcon {

	private enum States {
		Empty, Enable, InPreparing, Busy, Disable
	}

	private int mType = 0;

	private int mCount = 0;

	private States mState = States.Empty;

	private float mTime = 0;
	private float mMaxBusyTime = 0;
	private float mMaxDisableTime = 0;

	public BonusIcon() {
	}

	public void onCreate() {
		this.mState = States.Empty;

		this.mCount = 0;

		this.mTime = 0;
		this.mMaxBusyTime = 0;
		this.mMaxDisableTime = 0;
	}

	public void init(final int pType) {
		this.mState = States.Enable;

		this.mType = pType;
		// TODO: (R) Start factory of bonuses. Get count, maxBusyTime, max DisableTime.

		// TODO: (R) Test code.
		this.mCount = 1;
		this.mMaxBusyTime = 0;
		this.mMaxDisableTime = 0;
	}

	public void onClick() {
		switch (this.mState) {
		case Empty:
			// Don't do something.
			break;
		case Enable:
			this.prepare();
			break;
		case InPreparing:
			this.prepareBreak();
			break;
		case Busy:
			// Don't do something.
			break;
		case Disable:
			// Don't do something.
			break;
		}
	}

	protected void prepare() {
		this.mState = States.InPreparing;
		// TODO: (R) Add other code to preparing bonus (change image).
	}

	protected void prepareBreak() {
		this.mState = States.Enable;
		// TODO: (R) Add other code to break preparing bonus (change image).
	}

	public void start(final float pX, final float pY) {
		this.mState = States.Busy;
		this.mTime = 0;
		BubbleFactory.BubbleBonus(pX, pY, mType);
		// TODO: (R) Add other code to start bonus (change image).
	}

	protected void onManagedUpdate(final float pSecondsElapsed) {
		// TODO: (R) Add this code after adding supper class. super.onManagedUpdate(pSecondsElapsed);
		this.mTime += pSecondsElapsed;
		switch (this.mState) {
		case Empty:
			// Don't do something.
			break;
		case Enable:
			// Don't do something.
			break;
		case InPreparing:
			// Don't do something.
			break;
		case Busy:
			this.onManagedUpdateBusy();
			break;
		case Disable:
			this.onManagedUpdateDisable();
			break;
		}
	}

	protected void onManagedUpdateBusy() {
		if (this.mTime > this.mMaxBusyTime) {
			this.mCount--;
			if (this.mCount > 0) {
				this.mState = States.Empty;
				// TODO: (R) Add other code before start empty state (change image).
			}
			else {
				this.mState = States.Disable;
				this.mTime = 0;
				// TODO: (R) Add other code before start disable state (change image).
			}
		}
	}

	protected void onManagedUpdateDisable() {
		if (this.mTime > this.mMaxDisableTime) {
			this.mState = States.Enable;
			// TODO: (R) Add other code before start enable state.
		}
	}
}
