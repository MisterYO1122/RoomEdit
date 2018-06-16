package com.rovi.roomdesigner;

import com.badlogic.gdx.ApplicationAdapter;
import com.rovi.roomdesigner.states.*;

public class GdxMain extends ApplicationAdapter {
	
	StateManager stateManager;
	
	@Override
	public void create () {
		stateManager = new StateManager();
		stateManager.setState(new DesignState(stateManager));
	}

	@Override
	public void render () {
		stateManager.updateAndDraw();
	}
	
	@Override
	public void dispose () {
		stateManager.dispose();
	}
}
