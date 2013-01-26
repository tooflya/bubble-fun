package com.tooflya.bubblefun.managers;

/**
 * @author Tooflya.com
 * @since
 */
public class EntityManager<T> {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected int mCurrentElemetCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	public T create() {
		this.mCurrentElemetCount++;

		return null;
	}

	/**
	 * Method which will destroy an element with pIndex index.
	 * 
	 * @param pIndex
	 *            index of element.
	 */
	public void destroy(final int pIndex) {
		this.mCurrentElemetCount--;
	}

	/**
	 * @return
	 */
	public int getCount() {
		return this.mCurrentElemetCount;
	}

}