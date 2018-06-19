package com.rovi.roomdesigner;

import com.badlogic.gdx.graphics.Color;

public class RoomTag {

	private String tag;
	private Color color;
	
	public static RoomTag defaultTag = new RoomTag("Default", Color.LIGHT_GRAY);
	
	public RoomTag(String tag, Color color) {
		this.tag = tag;
		this.color = color;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getTag() {
		return tag;
	}
	
	public Color getColor() {
		return color;
	}
	
}
