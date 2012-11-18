package com.tooflya.bubblefun.entities;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Sprike extends Entity {

	private boolean mReverse;

	public float toX;
	private float baseX;

	private int size;

	public Sprike(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);

		this.baseX = this.mX;
	}

	public void init(final float pToX, final int pSize) {
		this.baseX = this.mX;
		this.toX = pToX;
		this.size = pSize;
	}

	public Entity create() {
		this.mReverse = false;

		return super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.mReverse) {
			this.mX -= this.getSpeedX();
			if (this.mX <= this.baseX) {
				this.mReverse = false;
			}
		} else {
			this.mX += this.getSpeedX();

			if (this.mX >= this.toX) {
				this.mReverse = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.entities.Entity#onManagedDraw(javax.microedition.khronos.opengles.GL10, org.anddev.andengine.engine.camera.Camera)
	 */
	@Override
	public void onManagedDraw(final GL10 GL, final Camera pCamera) {
		super.onManagedDraw(GL, pCamera);

		GL.glPushMatrix();

		GL.glTranslatef(this.getX(), this.getY(), 0);

		for (int i = 1; i < this.size; i++) {
			GL.glTranslatef(this.getBaseWidth(), 0, 0);
			this.doDraw(GL, pCamera);
		}

		GL.glPopMatrix();
	}

	@Override
	public float getWidth() {
		return super.getWidth() * this.size;
	}

}
