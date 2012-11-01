package com.tooflya.bubblefun.modifiers;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;

public class ScaleModifier extends org.anddev.andengine.entity.modifier.ScaleModifier {

	public ScaleModifier(float pDuration, float pFromScale, float pToScale) {
		super(pDuration, pFromScale, pToScale);

		this.addModifierListener(new IEntityModifierListener() {

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				onFinished();
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				onStarted();
			}

		});

		this.setRemoveWhenFinished(false);

		this.mFinished = true;
	}

	public void onStarted() {

	}

	public void onFinished() {

	}
}
