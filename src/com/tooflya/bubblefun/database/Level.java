package com.tooflya.bubblefun.database;

public class Level {

	private int mId;
	private boolean mOpen;
	private int mStarsCount;

	public Level(final int pId, final boolean pOpen, final int pStars) {
		this.mId = pId;
		this.mStarsCount = pStars;
		this.mOpen = pOpen;
	}

	public Level(final boolean pOpen, final int pStars) {
		this.mStarsCount = pStars;
		this.mOpen = pOpen;
	}

	public Level(final int pId, final boolean pOpen) {
		this.mId = pId;

		this.mOpen = pOpen;
	}

	public Level(final boolean pOpen) {
		this.mOpen = pOpen;
	}

	public int getID() {
		return this.mId;
	}

	public boolean isOpen() {
		return this.mOpen;
	}

	public int getStarsCount() {
		return this.mStarsCount;
	}
}
