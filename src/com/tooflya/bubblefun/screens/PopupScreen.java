package com.tooflya.bubblefun.screens;

import com.planetbattle.managers.ScreensManager;
import com.planetbattle.modifiers.ScaleModifier;
import com.tooflya.bubblefun.Camera;

public class PopupScreen extends Screen {

	private ScaleModifier modifier1 = new ScaleModifier(0.3f, 0f, 1.1f);

	public PopupScreen(ScreensManager pScreensManager, Camera pCamera) {
		super(pScreensManager, pCamera);

		//this.registerEntityModifier(this.modifier1);
	}

	@Override
	public void onAttached() {
		//modifier1.reset();
	}

	@Override
	public void onDetached() {
	}

	@Override
	public void onBackPressed() {
	}

}
