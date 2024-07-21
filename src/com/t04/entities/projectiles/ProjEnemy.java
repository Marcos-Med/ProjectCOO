package com.t04.entities.projectiles;
import java.awt.Color;

import com.t04.main.*;
public class ProjEnemy extends Projectile {
	private static final int max = 200;
	private static final double radius = 2.0;
	private int damage;
	public ProjEnemy(double x, double y, double vx, double vy){
		super(x, y, vx, vy);
		this.damage = 1;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean exitScreen() {
		return getY() > GameLib.HEIGHT;
	}
	
	public int getMax() {
		return max;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void draw() {
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(getX(), getY(), radius);
	}
	
}
