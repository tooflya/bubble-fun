package com.planetbattle.modifiers;

import com.tooflya.bubblefun.entity.Entity;

public class ScaleModifier extends BaseModifier<Entity> {

	public ScaleModifier(float pAnimationTime, float pAnimationFromState, float pAnimationToState) {
		super(pAnimationTime, pAnimationFromState, pAnimationToState);
	}

	@Override
	public void reset() {
		super.reset();

		this.getEntity().setScale(this.mAnimationFromState);
	}
}
