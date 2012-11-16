package com.tooflya.bubblefun.handlers;

public class BaseHadler<T> {

	private T mEntity;

	protected float mSecondsElapsed;
	protected float mSeconds;

	public BaseHadler(final float pSeconds) {
		this.mSeconds = pSeconds;
	}

	public void setEntity(final T pEntity) {
		this.mEntity = pEntity;
	}

	public T getEntity() {
		return this.mEntity;
	}

	public void onManagedUpdate(final float pSecondsElapsed) {
		this.mSecondsElapsed += pSecondsElapsed;
	}

	protected void onUpdate(final T pEntit) {

	}
}
