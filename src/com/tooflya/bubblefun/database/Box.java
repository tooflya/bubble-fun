package com.tooflya.bubblefun.database;

/**
 * @author Tooflya.com
 * @since
 */
public class Box {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mId;
	private boolean mOpen;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Box(final int pId, final boolean pOpen) {
		this.mId = pId;
		this.mOpen = pOpen;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean isOpen() {
		return this.mOpen;
	}
}
