package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class EntityManager<T> extends org.anddev.andengine.entity.Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected int lastElementNumber;
	protected int capacity;

	protected Entity[] elements;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param capacity
	 * @param element
	 */
	public EntityManager(final int capacity, final Entity element) {
		this.lastElementNumber = -1;

		this.capacity = capacity;

		elements = new Entity[capacity];

		for (int i = elements.length - 1; i > 0; --i) {
			elements[i] =element.deepCopy();
			elements[i].setManager(this);
			elements[i].setID(i);
		}

		elements[0] = element;
		elements[0].setManager(this);
		elements[0].setID(0);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	public int getCount() {
		return this.lastElementNumber + 1;
	}

	/**
	 * @return
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getByIndex(final int index) {
		return (T) elements[index];
	}

	@SuppressWarnings("unchecked")
	public T create() {
		if (lastElementNumber + 1 < capacity) {
			lastElementNumber++;

			elements[lastElementNumber].create();

			return (T) elements[lastElementNumber];
		}

		return null;
	}

	/**
	 * @param i
	 */
	public void destroy(final int i) {
		try {
			Entity temp_element = elements[i];
			elements[i] = elements[lastElementNumber];
			elements[lastElementNumber] = temp_element;

			elements[i].setID(i);
			elements[lastElementNumber].setID(lastElementNumber);

			lastElementNumber--;
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	/**
	 * 
	 */
	public void clear() {
		for (int i = lastElementNumber; i >= 0; --i) {
			elements[i].destroy();
		}
	}
}
