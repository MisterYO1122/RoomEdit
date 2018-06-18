package com.rovi.roomdesigner;

import java.util.ArrayList;
import java.util.List;

public class StateManager {

	public List<State> states;
	
	public StateManager() {
		states = new ArrayList<>();
	}
	
	/**
	 * Sets the current State.
	 * @param state - the State you want to use.
	 */
	public void setState(State state) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).dispose();
		}
		states.clear();
		states.add(state);
	}
	
	/**
	 * Calls both the update and the draw functions of the top-level state. Update is called first.
	 */
	public void updateAndDraw() {
		states.get(states.size() - 1).update();
		states.get(states.size() - 1).draw();
	}
	
	/**
	 * Calls the dispose method of all the states in the States list.
	 */
	public void dispose() {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).dispose();
		}
	}
	
	public void resize(int width, int height) {
		states.get(states.size() - 1).resize(width, height);
	}
}
