package com.t04.entities.projectiles;

import java.awt.Color;

import com.t04.main.GameLib;

public class ProjPlayer extends Projectile {

	private static final int max = 10;
	
	public ProjPlayer(double x, double y){
		super(x, y, 0.0, -1.0);
	}
	
	public boolean exitScreen() {
		return getY() < 0;
	}
	
	public int getMax() {
		return max;
	}
	
	public void draw() {
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
		GameLib.drawLine(getX() - 1, getY() - 3, getX() - 1, getY() + 3);
		GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
	}
}
