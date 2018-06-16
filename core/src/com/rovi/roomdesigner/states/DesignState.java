package com.rovi.roomdesigner.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.rovi.roomdesigner.State;
import com.rovi.roomdesigner.StateManager;

public class DesignState extends State{

	Texture tex;
	Vector2 camMovement;
	
	private float cameraSpeed = 30f;
	
	public DesignState(StateManager stateManager) {
		super(stateManager);
		tex = new Texture("square.png");
		
		camMovement = new Vector2();
	}
	
	@Override
	public void update() {
		
		if(Gdx.input.isKeyPressed(Keys.A))
			camMovement.x = -1;
		else if(Gdx.input.isKeyPressed(Keys.D))
			camMovement.x = 1;
		else
			camMovement.x = 0;
		
		if(Gdx.input.isKeyPressed(Keys.W))
			camMovement.y = 1;
		else if(Gdx.input.isKeyPressed(Keys.S))
			camMovement.y = -1;
		else
			camMovement.y = 0;
		
		
		
		camera.update();
	}
	
	@Override
	public void draw() {
		super.draw();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(tex, 0, 0);
		batch.end();
	}
	
}
