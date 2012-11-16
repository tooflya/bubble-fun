package com.tooflya.bubblefun.handlers;

public class UpdateHandler<T> extends BaseHadler<T> {

	public UpdateHandler(final float pSeconds) {
		super(pSeconds);
	}

	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		while (this.mSecondsElapsed >= this.mSeconds) {
			this.onUpdate(this.getEntity());

			this.mSecondsElapsed -= this.mSeconds;
		}
	}
}
