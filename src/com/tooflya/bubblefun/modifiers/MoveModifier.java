package com.tooflya.bubblefun.modifiers;

public class MoveModifier extends org.anddev.andengine.entity.modifier.MoveModifier {

	public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, IEntityModifierListener pEntityModifierListener) {
		super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener);

		this.setRemoveWhenFinished(false);

		this.mFinished = true;
	}

}
