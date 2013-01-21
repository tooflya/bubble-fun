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

	private boolean mOpen;

	private int mId;
	private int mStars;
	private int mScore;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pId
	 * @param pOpen
	 * @param pStars
	 */
	public Level(final int pId, final boolean pOpen, final int pStars, final int pScore) {
		this.mId = pId;
		this.mOpen = pOpen;
		this.mStars = pStars;
		this.mScore = pScore;
	}

	/**
	 * @param pOpen
	 * @param pStars
	 */
	public Level(final boolean pOpen, final int pStars, final int pScore) {
		this.mOpen = pOpen;
		this.mStars = pStars;
		this.mScore = pScore;
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
		return this.mStars;
	}

	/**
	 * @return
	 */
	public int getScoreCount() {
		return this.mScore;
	}
}
