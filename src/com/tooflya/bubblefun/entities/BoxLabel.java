package com.tooflya.bubblefun.entities;

import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class BoxLabel extends Entity {

	private MoveModifier modifier1;
	private MoveModifier modifier2;

	private ScaleModifier modifier3;
	private ScaleModifier modifier4;

	private ScaleModifier modifier5 = new ScaleModifier(0.2f, 1f, 0.9f, 1f, 1.1f) {
		@Override
		public void onFinished() {
			modifier6.reset();
		}
	};

	private ScaleModifier modifier6 = new ScaleModifier(0.2f, 0.9f, 1.1f, 1.1f, 0.9f) {
		@Override
		public void onFinished() {
			modifier7.reset();
		}
	};

	private ScaleModifier modifier7 = new ScaleModifier(0.2f, 1.1f, 1f, 0.9f, 1f);

	private float mBasicY;

	public BoxLabel(final float pX, final float pY, TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen, final boolean reverse) {
		super(pTiledTextureRegion, pParentScreen);

		this.setCenterPosition(pX, pY);

		this.mX = Math.round(this.mX);
		this.mY = Math.round(this.mY);

		this.mBasicY = this.mY;

		if(!reverse) {
			this.modifier1 = new MoveModifier(0.2f, this.mX, this.mX, this.mY, this.mY + 100f);
			this.modifier2 = new MoveModifier(0.2f, this.mX, this.mX, this.mY + 100f, this.mY);
		} else {
			this.modifier1 = new MoveModifier(0.2f, this.mX, this.mX, this.mY, this.mY - 100f);
			this.modifier2 = new MoveModifier(0.4f, this.mX, this.mX, this.mY - 100f, this.mY);
		}

		this.modifier3 = new ScaleModifier(0.2f, 1f, 0f);
		this.modifier4 = new ScaleModifier(0.2f, 0f, 1f) {
			@Override
			public void onFinished() {
				modifier5.reset();
			}
		};

		this.registerEntityModifier(modifier1);
		this.registerEntityModifier(modifier2);
		this.registerEntityModifier(modifier3);
		this.registerEntityModifier(modifier4);
		this.registerEntityModifier(modifier5);
		this.registerEntityModifier(modifier6);
		this.registerEntityModifier(modifier7);

		this.setScaleCenter(this.getWidth() / 2, this.getHeight() / 2);
	}


	public BoxLabel(final float pX, final float pY, TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		this(pX, pY, pTiledTextureRegion, pParentScreen, false);
	}
	
	public void up() {
		if (this.mBasicY != this.mY) {
			modifier1.stop();
			modifier3.stop();

			modifier2.reset();
			modifier4.reset();
		} else {
			modifier5.reset();
		}
	}

	public void down() {
		if (this.mBasicY == Math.round(this.mY)) {
			modifier2.stop();
			modifier4.stop();

			modifier1.reset();
			modifier3.reset();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);


	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.entity.Entity#setPosition(float, float)
	 */
	@Override
	public void setPosition(final float pX, final float pY) {
		this.mX = pX;
		this.mY = pY;
	}
}
