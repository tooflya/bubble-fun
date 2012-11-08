package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.screens.LevelScreen;

public class LevelIcon extends ButtonScaleable {

	public int id;

	public boolean blocked = false;

	public LevelIcon(TiledTextureRegion pTiledTextureRegion, final org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public Entity create() {
		this.id = 0;
		this.blocked = true;
		
		return super.create();
	}
	@Override
	public void onClick() {
		if (!this.blocked) {
			Options.levelNumber = this.id;
			((LevelScreen)Game.screens.get(Screen.LEVEL)).reInit();
			Game.screens.set(Screen.PRELOAD);
		}
	}
}
