package com.tooflya.bubblefun.managers;

import java.util.ArrayList;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class ListEntityManager<T> extends EntityManager<T> {

	// ===========================================================
	// Constants
	// ===========================================================

	protected final ArrayList<Entity> mElements = new ArrayList<Entity>();

	// ===========================================================
	// Fields
	// ===========================================================

	protected int mMaxElemetCount;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pCreateBeforeCount
	 * @param pMaxElementCount
	 * @param pElement
	 */
	public ListEntityManager(final int pCreateBeforeCount, final int pMaxElementCount, final Entity pElement) {
		this.mMaxElemetCount = pMaxElementCount;
		this.mCurrentElemetCount = 0;

		this.mElements.add(pElement);
		this.mElements.get(this.mElements.size() - 1).setManager(this);

		for (int i = 0; i < pCreateBeforeCount - 1; i++) {
			this.mElements.add(pElement.deepCopy());
			this.mElements.get(this.mElements.size() - 1).setManager(this);
		}
	}

	/**
	 * @param pCreateBeforeCount
	 * @param pElement
	 */
	public ListEntityManager(final int pCreateBeforeCount, final Entity pElement) {
		this(pCreateBeforeCount, -1, pElement);
	}

	/**
	 * @param pElement
	 * @param pMaxElementCount
	 */
	public ListEntityManager(final Entity pElement, final int pMaxElementCount) {
		this(-1, pMaxElementCount, pElement);
	}

	/**
	 * @param pElement
	 */
	public ListEntityManager(final Entity pElement) {
		this(-1, -1, pElement);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<T> getElements() {
		return (ArrayList<T>) this.mElements;
	}

	/**
	 * Method which create an element and return it.
	 * 
	 * @return (T) element.
	 */
	@SuppressWarnings("unchecked")
	public final T create() {
		for (final Entity entity : this.mElements) {
			if (!entity.isAvailable()) {

				super.create();

				return (T) entity.create();
			}
		}

		if (this.mElements.size() > this.mMaxElemetCount) {
			this.mElements.add(this.mElements.get(0).deepCopy());
			this.mElements.get(this.mElements.size() - 1).setManager(this);

			super.create();

			return (T) this.mElements.get(this.mElements.size() - 1).create();
		}

		throw new NullPointerException(this.getClass().getName() + " can't create no more elements: (" + this.mElements.get(0).getClass().getName() + ").");
	}

	/**
	 * Method which myst destroy all elements in list.
	 */
	public void clear() {
		for (final Entity entity : this.mElements) {
			if (entity.isAvailable()) {
				entity.destroy();
			}
		}

		this.mCurrentElemetCount = 0;
	}

	/**
	 * Method which changing graphics texture in all elements of list.
	 * 
	 * @param pTiledTextureRegion
	 *            new TiledTextureRegion.
	 */
	public final void changeTextureRegion(final TiledTextureRegion pTiledTextureRegion) {
		for (final Entity entity : this.mElements) {
			entity.changeTextureRegion(pTiledTextureRegion);
		}
	}
}