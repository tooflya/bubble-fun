package com.tooflya.bubblefun.managers;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class ArrayEntityManager<T> extends EntityManager<T> {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected Entity[] mElements;

	protected int mLastElementNumber;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Base constructor which create a few elements before it will be need.
	 * 
	 * @param pCapacity
	 *            capacity of manager, count of elements.
	 * @param pElement
	 *            element which myst be copyed a few times.
	 */
	public ArrayEntityManager(final int pCapacity, final Entity pElement) {
		this.mLastElementNumber = -1;

		this.mElements = new Entity[pCapacity];

		for (int i = this.mElements.length - 1; i > 0; --i) {
			this.mElements[i] = pElement.deepCopy();
			this.mElements[i].setManager(this);
			this.mElements[i].setID(i);
		}

		this.mElements[0] = pElement;
		this.mElements[0].setManager(this);
		this.mElements[0].setID(0);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return capacity of current manager.
	 */
	public final int getCapacity() {
		return this.mElements.length;
	}

	/**
	 * Method which return an element with need index.
	 * 
	 * @param pIndex
	 *            index of elements in list.
	 * @return (T) element.
	 */
	@SuppressWarnings("unchecked")
	public final T getByIndex(final int pIndex) {
		return (T) this.mElements[pIndex];
	}

	/**
	 * Method which create an element and return it.
	 * 
	 * @return (T) element.
	 */
	@SuppressWarnings("unchecked")
	public final T create() {
		if (this.mLastElementNumber + 1 < this.getCapacity()) {
			this.mLastElementNumber++;

			super.create();

			this.mElements[this.mLastElementNumber].create();

			return (T) this.mElements[this.mLastElementNumber];
		}

		throw new NullPointerException(this.getClass().getName() + " can't create no more elements: (" + this.mElements[0].getClass().getName() + ").");
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.managers.EntityManager#destroy(int)
	 */
	@Override
	public void destroy(final int pIndex) {
		super.destroy(pIndex);

		try {
			final Entity temp_element = this.mElements[pIndex];
			this.mElements[pIndex] = this.mElements[this.mLastElementNumber];
			this.mElements[this.mLastElementNumber] = temp_element;

			this.mElements[pIndex].setID(pIndex);
			this.mElements[this.mLastElementNumber].setID(this.mLastElementNumber);

			this.mLastElementNumber--;
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	/**
	 * Method which myst destroy all elements in list.
	 */
	public void clear() {
		for (int i = this.mLastElementNumber; i >= 0; --i) {
			this.mElements[i].destroy();
		}
	}

	/**
	 * Method which changing graphics texture in all elements of list.
	 * 
	 * @param pTiledTextureRegion
	 *            new TiledTextureRegion.
	 */
	public final void changeTextureRegion(final TiledTextureRegion pTiledTextureRegion) {
		for (int i = this.getCapacity() - 1; i >= 0; --i) {
			this.mElements[i].changeTextureRegion(pTiledTextureRegion);
		}
	}
}