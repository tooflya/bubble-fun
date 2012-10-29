package com.tooflya.bubblefun.modifiers;

public class ScaleModifier extends org.anddev.andengine.entity.modifier.ScaleModifier {

	public ScaleModifier(float pDuration, float pFromScale, float pToScale, IEntityModifierListener iEntityModifierListener) {
		super(pDuration, pFromScale, pToScale, iEntityModifierListener);

		this.setRemoveWhenFinished(false);

		this.mFinished = true;
	}

}
