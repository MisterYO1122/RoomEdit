package com.rovi.uigdx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class UIElement {

	public Vector2 position;
	private Vector2 realPosition;
	public Vector2 size = new Vector2(5, 2);
	public Color color = Color.WHITE;
	private Texture texture;
	private Vector2 boundsUpperLeft, boundsLowerRight;
	float timer;
	
	public UIElement(Texture texture, Vector2 position, Vector2 size, Camera camera) {
		this.texture = texture;
		this.position = position;
		this.size = size;
		SetBoundsUpperLeft(new Vector2(2, 2));
		SetBoundsLowerRight(new Vector2(13, 13));
	}
	
	public UIElement(Texture texture, Vector2 position, Vector2 size, Vector2 boundsUpperLeft, Vector2 boundsLowerRight, Camera camera) {
		this.texture = texture;
		this.position = position;
		this.size = size;
		SetBoundsUpperLeft(boundsUpperLeft);
		SetBoundsLowerRight(boundsLowerRight);
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch) {
		
		if(size.x < boundsUpperLeft.x + (texture.getWidth() - boundsLowerRight.x) + 1)
			size.x = boundsUpperLeft.x + (texture.getWidth() - boundsLowerRight.x) + 1;
		if(size.y < boundsUpperLeft.y + (texture.getWidth() - boundsLowerRight.y))
			size.y = boundsUpperLeft.y + (texture.getWidth() - boundsLowerRight.y);

		batch.draw(texture, position.x, position.y + size.y - boundsUpperLeft.y, size.x / 2, size.y / 2, boundsUpperLeft.x + 1, boundsUpperLeft.y + 1, 1, 1, 0, 0, 0, (int)boundsUpperLeft.x + 1, (int)boundsUpperLeft.y + 1, false, false); //Draw top left part
		batch.draw(texture, position.x - 1 + size.x - (texture.getWidth() - boundsLowerRight.x - 1), position.y + size.y - boundsUpperLeft.y, size.x / 2, size.y / 2, texture.getWidth() - boundsLowerRight.x, boundsUpperLeft.y + 1, 1, 1, 0, (int)boundsLowerRight.x, 0, (int)(texture.getWidth() - boundsLowerRight.x), (int)boundsUpperLeft.y + 1, false, false); //Draw top right part
		batch.draw(texture, position.x, position.y, size.x / 2, size.y / 2, boundsUpperLeft.x + 1, boundsUpperLeft.y + 1, 1, 1, 0, 0, (int)boundsLowerRight.y, (int)boundsUpperLeft.x + 1, (int)(texture.getHeight() - boundsLowerRight.y), false, false); //Draw bottom left part
		batch.draw(texture, position.x - 1 + size.x - (texture.getWidth() - boundsLowerRight.x - 1), position.y, size.x / 2, size.y / 2, texture.getWidth() - boundsLowerRight.x, boundsUpperLeft.y + 1, 1, 1, 0, (int)boundsLowerRight.x, (int)boundsLowerRight.y, (int)(texture.getWidth() - boundsLowerRight.x), (int)(texture.getHeight() - boundsLowerRight.y), false, false); //Draw bottom right part
		
		batch.draw(texture, position.x + boundsUpperLeft.x + 1, position.y + size.y - boundsUpperLeft.y, size.x / 2, size.y / 2, size.x - (texture.getWidth() - boundsLowerRight.x) - boundsUpperLeft.x - 1, boundsUpperLeft.y + 1, 1, 1, 0, (int)boundsUpperLeft.x + 1, 0, (int)(boundsLowerRight.x - boundsUpperLeft.x - 1), (int)boundsUpperLeft.y + 1, false, false); //Draw top part
		batch.draw(texture, position.x + boundsUpperLeft.x + 1, position.y, size.x / 2, size.y / 2, size.x - boundsUpperLeft.x - 1 - (texture.getWidth() - boundsLowerRight.x), texture.getHeight() - boundsLowerRight.y, 1, 1, 0, (int)boundsUpperLeft.x + 1, (int)boundsLowerRight.y, (int)(boundsLowerRight.x - boundsUpperLeft.x - 1), (int)boundsUpperLeft.y + 1, false, false); //Draw bottom part
		batch.draw(texture, position.x, position.y + texture.getHeight() - boundsLowerRight.y, size.x / 2, size.y / 2, boundsUpperLeft.x + 1, size.y - (texture.getHeight() - boundsLowerRight.y) - boundsUpperLeft.y, 1, 1, 0, 0, (int)boundsUpperLeft.y + 1, (int)boundsUpperLeft.x+1, (int)(boundsLowerRight.y - boundsUpperLeft.y - 1), false, false); //Draw left part
		batch.draw(texture, position.x - 1 + size.x - (texture.getWidth() - boundsLowerRight.x - 1), position.y + texture.getHeight() - boundsLowerRight.y, size.x / 2, size.y / 2, (texture.getWidth() - boundsLowerRight.x), size.y - (texture.getHeight() - boundsLowerRight.y) - boundsUpperLeft.y, 1, 1, 0, (int)boundsLowerRight.x, (int)boundsUpperLeft.y + 1, (int)boundsUpperLeft.x+1, (int)(boundsLowerRight.y - boundsUpperLeft.y - 1), false, false); //Draw right part
		
		batch.draw(texture, position.x + boundsUpperLeft.x + 1, position.y + boundsUpperLeft.y + 1, size.x / 2, size.y / 2, size.x - (texture.getWidth() - boundsLowerRight.x) - boundsUpperLeft.x - 1, size.y - (texture.getHeight() - boundsLowerRight.y) - boundsUpperLeft.y, 1, 1, 0, (int)boundsUpperLeft.x + 1, (int)boundsUpperLeft.y + 1, (int)(boundsLowerRight.x - boundsUpperLeft.x - 1), (int)(boundsLowerRight.y - boundsUpperLeft.y-1), false, false); //Draw center part
		
	}
	
	public void SetBoundsUpperLeft(Vector2 bounds) {
		boundsUpperLeft = bounds;
	}
	
	public void SetBoundsLowerRight(Vector2 bounds) {
		boundsLowerRight = bounds;
	}
}
