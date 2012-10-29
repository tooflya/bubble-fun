package com.tooflya.bubblefun.modifiers;

public class AlphaModifier extends org.anddev.andengine.entity.modifier.AlphaModifier {

	public AlphaModifier(float pDuration, float pFromAlpha, float pToAlpha, IEntityModifierListener pEntityModifierListener) {
		super(pDuration, pFromAlpha, pToAlpha, pEntityModifierListener);

		this.setRemoveWhenFinished(false);

		this.mFinished = true;
	}

}
