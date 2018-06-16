package com.rovi.roomdesigner;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera{
	
	private float rotation;
	
	public Camera(int viewportWidth, int viewportHeight) {
		super(viewportWidth, viewportHeight);
		setToOrtho(false);
	}
	
	/**
	 * Sets the rotation of the camera in degrees.
	 * @param angle - the rotation angle you want.
	 */
	public void setRotation(float angle) {
		this.rotate(this.rotation - angle);
		rotation -= angle;
	}
	/**
	 * Returns the camera rotation in degrees.
	 * @return the current angle of the camera, in degrees.
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Sets the X and Y position of the camera.
	 * @param x - the X position you want the camera to move to.
	 * @param y - the Y position you want the camera to move to.
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y, 0);
	}
	
	/**
	 * Sets only the X position of the camera.
	 * @param x - the X position you want the camera to move to.
	 */
	public void setPositionX(float x) {
		this.position.set(x, this.position.y, 0);
	}
	
	/**
	 * Sets only the Y position of the camera.
	 * @param y - the Y position you want the camera to move to.
	 */
	public void setPositionY(float y) {
		this.position.set(this.position.x, y, 0);
	}
	
	/**
	 * Moves the camera, relative to it's current position.
	 * @param x - how much to move on the X coordinate.
	 * @param y - how much to move on the Y coordinate.
	 */
	public void move(float x, float y) {
		this.position.set(this.position.x + x, this.position.y + y, 0);
	}
}
