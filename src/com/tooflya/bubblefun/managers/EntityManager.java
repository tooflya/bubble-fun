package com.tooflya.bubblefun.managers;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.entities.Entity;

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

	protected int mLastIndex;
	protected Entity[] mElements; // TODO: (R) Can we create and use something like "T[] mElements"?

	// ===========================================================
	// Constructors
	// ===========================================================

	public EntityManager(final int pCapacity, final Entity pElement) {
		this.mLastIndex = -1;
		this.mElements = new Entity[pCapacity];
		this.mElements[0] = pElement;
		this.mElements[0].setManager(this);
		this.mElements[0].setID(0);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public int getCount() {
		return this.mLastIndex + 1;
	}

	public int getCapacity() {
		return this.mElements.length;
	}

	public int getLastIndex() {
		return this.mLastIndex;
	}

	@SuppressWarnings("unchecked")
	public T getByIndex(final int pIndex) {
		if (pIndex > this.mLastIndex) {
			return null;
		}
		return (T) this.mElements[pIndex];
	}

	@SuppressWarnings("unchecked")
	public T createElement() {
		if (this.mLastIndex + 1 >= this.mElements.length) {
			return null;
		}

		this.mLastIndex++;
		if (this.mElements[mLastIndex] == null) {
			this.mElements[this.mLastIndex] = this.mElements[0].deepCopy();
			this.mElements[this.mLastIndex].setManager(this);
			this.mElements[this.mLastIndex].setID(this.mLastIndex);
		}
		this.mElements[mLastIndex].create();
		return (T) this.mElements[mLastIndex];
	}

	public void destroyElement(final int pIndex) {
		if (pIndex <= this.mLastIndex) {
			if (pIndex < this.mLastIndex) {
				Entity temp_element = this.mElements[pIndex];
				this.mElements[pIndex] = this.mElements[this.mLastIndex];
				this.mElements[this.mLastIndex] = temp_element;

				this.mElements[pIndex].setID(pIndex);
				this.mElements[this.mLastIndex].setID(this.mLastIndex);
			}
			this.mLastIndex--;
		}
	}

	public void clear() {
		for (int i = this.mLastIndex; i >= 0; --i) {
			this.mElements[i].destroy();
		}
	}

	public void changeTextureRegion(final TiledTextureRegion pTiledTextureRegion) {
		final int len = this.mElements.length;
		for (int i = 0; i < len && this.mElements[i] != null; --i) {
			this.mElements[i].changeTextureRegion(pTiledTextureRegion);
		}
	}
}
