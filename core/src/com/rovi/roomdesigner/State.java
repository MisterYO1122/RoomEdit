package com.rovi.roomdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {

	private StateManager stateManager;
	protected SpriteBatch batch;
	private Color backgroundColor;
	protected Camera camera;
	
	public State(StateManager stateManager){
		this.stateManager = stateManager;
		
		batch = new SpriteBatch();
		camera = new Camera(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		backgroundColor = Color.BLACK;
	}
	
	/**
	 * Returns the connected StateManager. 
	 * @return Returns the StateManager if it's not null. Otherwise, it returns null.
	 */
	public StateManager getStateManager() {
		return this.stateManager;
	}
	
	/**
	 * Sets the StateManager.
	 * @param stateManager - the StateManager this State is connected to.
	 */
	public void setStateManager(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	/** Sets the color of the background.
	 * 
	 * @param color is the color you want the background to be.
	 */
	protected void setBackgroundColor(Color color) {
		backgroundColor = color;
	}
	
	/**
	 * Updates all the elements of the State (for example, the camera, the entities, etc)
	 */
	public void update() {}
	
	/**
	 * Clears the screen and sets it's color. Also used to draw the sprites.
	 */
	public void draw() {
		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void dispose() {
		batch.dispose();
	}
	
}
