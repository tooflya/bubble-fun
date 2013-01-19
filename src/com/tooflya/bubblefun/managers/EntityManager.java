package com.tooflya.bubblefun.managers;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

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

	protected Entity[] mElements;
	protected int mLastElementNumber;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pCapacity
	 * @param pElement
	 */
	public EntityManager(final int pCapacity, final Entity pElement) {
		this.mLastElementNumber = -1;
		this.mElements = new Entity[pCapacity];
		this.mElements[0] = pElement;
		this.mElements[0].setManager(this);
		this.mElements[0].setID(0);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	public int getCount() {
		return this.mLastElementNumber + 1;
	}

	/**
	 * @return
	 */
	public int getCapacity() {
		return this.mElements.length;
	}

	/**
	 * @param pIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getByIndex(final int pIndex) {
		if (pIndex > this.mLastElementNumber) {
			return null;
		}
		return (T) this.mElements[pIndex];
	}

	@SuppressWarnings("unchecked")
	public T create() {
		if (this.mLastElementNumber + 1 >= this.mElements.length) {
			return null;
		}
		
		this.mLastElementNumber++;
		if(this.mElements[mLastElementNumber] == null) {
			this.mElements[this.mLastElementNumber] = this.mElements[0].deepCopy();
			this.mElements[this.mLastElementNumber].setManager(this);
			this.mElements[this.mLastElementNumber].setID(this.mLastElementNumber);
		}
		this.mElements[mLastElementNumber].create();
		return (T) this.mElements[mLastElementNumber];
	}

	/**
	 * @param pIndex
	 */
	public void destroy(final int pIndex) {
		if(pIndex <= this.mLastElementNumber) {
			Entity temp_element = this.mElements[pIndex];
			this.mElements[pIndex] = this.mElements[this.mLastElementNumber];
			this.mElements[this.mLastElementNumber] = temp_element;

			this.mElements[pIndex].setID(pIndex);
			this.mElements[this.mLastElementNumber].setID(this.mLastElementNumber);

			this.mLastElementNumber--;
		}
	}

	/**
	 * 
	 */
	public void clear() {
		for (int i = this.mLastElementNumber; i >= 0; --i) {
			this.mElements[i].destroy(); // TODO: (R) Create method that destroy entity without start destroy of manager. 
		}
	}

	/*
	 * 
	 */
	public void changeTextureRegion(final TiledTextureRegion pTiledTextureRegion) {
		for (int i = this.mElements.length - 1; i >= 0; --i) {
			this.mElements[i].changeTextureRegion(pTiledTextureRegion);
		}
	}
}
