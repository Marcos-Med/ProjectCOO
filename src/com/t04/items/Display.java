package com.t04.items;

public abstract class Display {
	private int width ;
	Display(int width){
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public abstract void draw(int life);
}
