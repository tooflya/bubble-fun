package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.Screen;
import com.tooflya.bubblefun.screens.StoreScreen;

public class LevelIcon extends ButtonScaleable {

	public int id;

	public boolean blocked = false;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		this.id = 0;
		this.blocked = true;
	}

	@Override
	public void onClick() {
		if (!this.blocked) {
			Options.levelNumber = this.id;
			StoreScreen.ATTACH_TYPE = 1;
			Game.screens.set(Screen.STORE);
		}
	}
}
