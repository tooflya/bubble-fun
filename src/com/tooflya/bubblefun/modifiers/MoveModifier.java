package com.tooflya.bubblefun.modifiers;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.util.modifier.IModifier;


/**
 * @author Tooflya.com
 * @since
 */
public class MoveModifier extends org.anddev.andengine.entity.modifier.MoveModifier {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public MoveModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY) {
		super(pDuration, pFromX, pToX, pFromY, pToY);

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
