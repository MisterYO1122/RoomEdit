package com.rovi.roomdesigner;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Room {

	public int x, y, width, height;
	public RoomTag tag = RoomTag.defaultTag;
	
	public Room(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Room(int x, int y, int width, int height, RoomTag tag) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tag = tag;
	}
	
	public boolean isOverlappingRoom(Room other) {
		return !(x + width <= other.x || x >= other.x + other.width || y >= other.y + other.height || y + height <= other.y);
	}
	
	public boolean isOverlappingPoint(Vector2 point, int gridSize) {
		return !(point.x <= x * gridSize || point.x >= x * gridSize + width * gridSize || point.y <= y * gridSize || point.y >= y * gridSize + height * gridSize);
	}
	
}
