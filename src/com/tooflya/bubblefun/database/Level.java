package com.tooflya.bubblefun.database;

/**
 * @author Tooflya.com
 * @since
 */
public class Level {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mId;
	private boolean mOpen;
	private int mStarsCount;
	private int mScoreCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pId
	 * @param pOpen
	 * @param pStars
	 */
	public Level(final int pId, final boolean pOpen, final int pStars) {
		this.mId = pId;
		this.mStarsCount = pStars;
		this.mOpen = pOpen;
	}

	/**
	 * @param pOpen
	 * @param pStars
	 */
	public Level(final boolean pOpen, final int pStars) {
		this.mStarsCount = pStars;
		this.mOpen = pOpen;
	}

	/**
	 * @param pId
	 * @param pOpen
	 */
	public Level(final int pId, final boolean pOpen) {
		this.mId = pId;

		this.mOpen = pOpen;
	}

	/**
	 * @param pOpen
	 */
	public Level(final boolean pOpen) {
		this.mOpen = pOpen;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	public int getID() {
		return this.mId;
	}

	/**
	 * @return
	 */
	public boolean isOpen() {
		return this.mOpen;
	}

	/**
	 * @return
	 */
	public int getStarsCount() {
		return this.mStarsCount;
	}

	/**
	 * @return
	 */
	public int getScoreCount() {
		return this.mScoreCount;
	}
}
