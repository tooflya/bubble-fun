package com.planetbattle.managers;

import com.tooflya.bubblefun.entity.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class EntityManager {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public int scenarioCount;

	private int count;
	private int capacity;

	public Entity[] elements;

	// ===========================================================
	// Constructors
	// ===========================================================

	public EntityManager(final int capacity, Entity element) {
		this.scenarioCount = 0;
		this.count = -1;

		this.capacity = capacity;

		elements = new Entity[capacity];

		for (int i = elements.length - 1; i > 0; --i) {
			elements[i] = element.deepCopy();
			//elements[i].setID(i);

			//Game.screens.get(Screen.LEVEL).attachChild(elements[i]);
		}

		elements[0] = element;
		//elements[0].setID(0);

		//Game.screens.get(Screen.LEVEL).attachChild(elements[0]);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public int getCount() {
		return this.count + 1;
	}

	public int getCapacity() {
		return capacity;
	}

	public Entity getByIndex(final int index) {
		return elements[index];
	}

	public Entity create(final boolean scenario) throws Exception {
		if (count + 1 < capacity) {
			count++;
			scenarioCount++;

			// ((Enemy) elements[count].create()).byScenario = true;

			return elements[count];
		}

		return null;
	}

	public Entity create() throws Exception {
		if (count + 1 < capacity) {
			count++;

			elements[count].create();

			return elements[count];
		}

		return null;
	}

	public void delete(final int i) {
		Entity temp_element = elements[i];
		elements[i] = elements[count];
		elements[count] = temp_element;

		//elements[i].setID(i);
		//elements[count].setID(count);

		count--;
	}

	public void clear() {
		this.count = -1;
		this.scenarioCount = 0;

		for (int i = 0; i < elements.length; ++i) {
			elements[i].reset();
			elements[i].setVisible(false);
		}
	}

	// ===========================================================
	// Classes
	// ===========================================================

}
