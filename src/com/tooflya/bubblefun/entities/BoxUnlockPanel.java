package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.primitive.Rectangle;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Resources;
import com.tooflya.bubblefun.screens.Screen;

public class BoxUnlockPanel extends Rectangle {

	private MoveModifier modifier1;
	private MoveModifier modifier2;

	private final Entity mPanel;

	private final ButtonScaleable mButton;

	private float mBasicY;

	public BoxUnlockPanel(final float pX, final float pY, final org.anddev.andengine.entity.Entity parent) {
		super(pX, pY, 190f, 80f);

		parent.attachChild(this);

		this.mY -= (Options.cameraHeight * Options.cameraRatioFactor - Options.screenHeight) / 2;

		this.mX = Math.round(this.mX);
		this.mY = Math.round(this.mY);

		this.mBasicY = this.mY;

		this.mPanel = new Entity(Resources.mBoxUnlockPanelTextureRegion, this);
		this.mPanel.setPosition(0, 10, true);
		this.mPanel.enableFullBlendFunction();
		this.mPanel.create();

		this.mButton = new ButtonScaleable(Resources.mBoxUnlockButtonTextureRegion, this) {
			@Override
			public void onClick() {

				Game.screens.get(Screen.BOX).setChildScene(Game.screens.get(Screen.BOXESUNLOCK), false, false, true);
				Game.screens.get(Screen.BOXESUNLOCK).onAttached();
			}
		};
		this.mButton.setPosition(this.mPanel.getX() + this.mPanel.getWidth() - 30f, 5f, true);
		this.mButton.create();

		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.setAlpha(0f);

		this.modifier1 = new MoveModifier(0.2f, this.mX, this.mX, this.mY, this.mY + 200f);
		this.modifier2 = new MoveModifier(0.4f, this.mX, this.mX, this.mY + 200f, this.mY);

		this.registerEntityModifier(modifier1);
		this.registerEntityModifier(modifier2);
	}

	public void up() {
		if (this.mBasicY != this.mY) {
			modifier1.stop();

			modifier2.reset();
		}
	}

	public void down() {
		if (this.mBasicY == Math.round(this.mY)) {
			modifier2.stop();

			modifier1.reset();
		}
	}
}
