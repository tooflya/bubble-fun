package com.tooflya.bubblefun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.planetbattle.managers.ScreensManager;

/**
 * @author Tooflya.com
 * @since
 */
public class Game implements ApplicationListener, InputProcessor {

	// ===========================================================
	// Constants
	// ===========================================================

	@SuppressWarnings("unused")
	private final float mFrameRate = 1.0f / 60.0f;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private ScreensManager mScreens;

	private float mSecondsElapsed = 0;

	public static float mMouseX, mMouseY;
	public static boolean mMouseDown;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);

		this.mCamera = new Camera();
		this.mScreens = new ScreensManager(this.mCamera);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int key) {
		switch (key) {
		case Keys.ESCAPE:
		case Keys.BACK:
			this.mScreens.getCurrent().onBackPressed();
			break;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char key) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor/25#keyUp(int)
	 */
	@Override
	public boolean keyUp(int key) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		mMouseDown = true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		this.touchMoved(x, y);

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchMoved(int, int)
	 */
	@Override
	public boolean touchMoved(int x, int y) {
		mMouseX = x;
		mMouseY = y;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		mMouseDown = false;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	@Override
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void pause() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		this.mSecondsElapsed = Gdx.graphics.getDeltaTime();

		this.mScreens.onManagedUpdate(this.mSecondsElapsed);
		this.mScreens.onManagedDraw(this.mSecondsElapsed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	@Override
	public void resize(final int pWidth, final int pHeight) {
		GL20 gl = Gdx.graphics.getGL20();

		gl.glViewport(pWidth / 2 - Options.cameraCenterX, pHeight / 2 - Options.cameraCenterY, Options.cameraWidth, Options.cameraHeight);
		this.mCamera.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	@Override
	public void resume() {
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Classes
	// ===========================================================

}
