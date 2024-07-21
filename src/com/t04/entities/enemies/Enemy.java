package com.t04.entities.enemies;
import com.t04.entities.players.IPlayer;
import com.t04.entities.projectiles.ProjPlayer;
import com.t04.entities.projectiles.Projectile;
import java.util.List;
import com.t04.main.GameLib;
//import com.t04.entities.players.Player;
import com.t04.main.Main;

public abstract class Enemy {
	private int state;
	private double x;
	private double y;
	private double v;
	private double angle;
	private double RV;
	private double explosion_start;
	private double explosion_end;
	private double previous_y;
	private int damage;
	private int score;
	
	public abstract boolean exitScreen();
	
	public abstract double getRadius();
	
	public abstract void draw();
	
	public abstract int getMax();
	
	public abstract int getQuantity();
	
	public abstract void removeOneUnit();
	
	
	protected abstract void Shoot(List<Projectile> projectiles, long currentTime, IPlayer p);
	
	Enemy(double x, double y, double v, double angle, double RV, int score, int damage){
		onState();
		this.x = x;
		this.y = y;
		this.v = v;
		this.angle = angle;
		this.RV = RV;
		this.score = score;
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void drawExplosion(long currentTime) {
		double alpha = (currentTime - explosion_start) / (explosion_end - explosion_start);
		GameLib.drawExplosion(x, y, alpha);
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setExplosionStart(double explosion_start) {
		this.explosion_start = explosion_start;
	}
	
	public void setExplosionEnd(double explosion_end) {
		this.explosion_end = explosion_end;
	}
	
	private void exploded(long currentTime) {
		isExploding();
		explosion_start = currentTime;
		explosion_end = currentTime + 500;
	}
	
	public void colidedWithProjectile(ProjPlayer p, long currentTime) {
		double dist = distProjectile(p);
		if(dist < getRadius()) {
			exploded(currentTime);
		}
	}
	
	public int getState() {
		return state;
	}
	
	public int getScore() {
		return score;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getV() {
		return v;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getRV() {
		return RV;
	}
	
	public double getExplosionStart() {
		return explosion_start;
	}
	
	public double getExplosionEnd() {
		return explosion_end;
	}
	
	protected double getPreviousY() {
		return previous_y;
	}
	
	public void onState() {
		state = Main.ACTIVE;
	}
	
	public void offState() {
		state = Main.INACTIVE;
	}
	
	public void isExploding() {
		state = Main.EXPLODING;
	}
	
	public double distProjectile(ProjPlayer p) {
		double dx = getX() - p.getX();
		double dy = getY() - p.getY();
		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	protected void setRV(double RV) {
		this.RV = RV;
	}
	
	protected void setAngle(double angle) {
		this.angle = angle;
	}
	
	protected void setX(double x) {
		this.x = x;
	}
	
	protected void setY(double y) {
		this.y = y;
	}
	
	public void update(long currentTime, long delta, List<Projectile> projectiles, IPlayer p) {
		if(getState() == Main.EXPLODING) {
			if(currentTime > getExplosionEnd()) {
				offState();
			}
		}
		if(getState() == Main.ACTIVE) {
			if(exitScreen()) {
				offState();
			}
			else {
				previous_y = y;
				x += getV()*Math.cos(getAngle())*delta;
				y += getV()*Math.sin(getAngle())*delta*(-1.0);
				angle += getRV()*delta;
				Shoot(projectiles, currentTime, p);
			}
		}
	}
}