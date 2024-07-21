package com.t04.entities.projectiles;
import com.t04.main.Main;
public abstract class Projectile {
	private int state;
	private double x;
	private double y;
	private double vx;
	private double vy;

	Projectile(double x, double y, double vx, double vy){
		onProjectile();
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	
	public int getState() {
		return state;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean needToRemove(double delta) {
		if(getState() == Main.ACTIVE) {
			return exitScreen();
		}
		return true;
	}
	
	public void updateXY(double delta) {
		x = getX() + (vx * delta);
		y = getY() + (vy * delta);
	}
	
	public void offProjectile() {
		state = Main.INACTIVE;
	}
	
	public void onProjectile() {
		state = Main.ACTIVE;
	}
	
	public abstract boolean exitScreen();
	
	public abstract int getMax();
	
	public abstract void draw();
}
