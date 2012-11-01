package com.tooflya.bubblefun.modifiers;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;

/**
 * @author Tooflya.com
 * @since
 */
public class RotationModifier extends org.anddev.andengine.entity.modifier.RotationModifier {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public RotationModifier(float pDuration, float pFromRotation, float pToRotation) {
		super(pDuration, pFromRotation, pToRotation);
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

	// ===========================================================
	// Virtual methods
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onStarted() {

	}

	public void onFinished() {

	}

}
