package com.t04.entities.enemies;

import com.t04.entities.projectiles.Projectile;
import com.t04.entities.projectiles.ProjEnemy;

import com.t04.entities.players.IPlayer;

import java.util.List;

import java.awt.Color;

import com.t04.main.GameLib;

public class EnemyOne extends Enemy{
	
	private static final double radius = 9.0;
	private static long nextEnemy;
	private long nextShoot;
	private static final int max = 10;
	private static int currentQuantity;
	
	public EnemyOne(long currentTime){
		super(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.20 + Math.random() * 0.15, 3 * Math.PI / 2, 0.0, 20, 6);
		nextShoot = currentTime + 500;
		nextEnemy = currentTime + 500;
		currentQuantity++;
	}
	
	public boolean exitScreen() {
		return getY() > GameLib.WIDTH + 10;
	}
	
	public static boolean hasNextEnemy(long currentTime) {
		return (currentTime > nextEnemy) && (currentQuantity < max);
	}
	
	private boolean hasNextShoot(long currentTime, IPlayer p) {
		return (currentTime > getNextShoot()) && (getY() < p.getY());
	}
	
	public int getQuantity() {
		return currentQuantity;
	}
	
	public void removeOneUnit() {
		currentQuantity--;
	}
	
	public int getMax() {
		return max;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public long getNextShoot() {
		return nextShoot;
	}
	
	public void draw() {
		GameLib.setColor(Color.CYAN);
		GameLib.drawCircle(getX(), getY(), radius);
	}
	
	protected void Shoot(List<Projectile> projectiles, long currentTime, IPlayer p) {
		double vx = Math.cos(getAngle()) * 0.45;
		double vy = Math.sin(getAngle()) * 0.45 * (-1.0);
		Projectile pjE = new ProjEnemy(getX(), getY(), vx, vy);
		if(hasNextShoot(currentTime, p) && projectiles.size() < pjE.getMax()) {
			projectiles.add(pjE);
			long nextShoot = (long) (currentTime + 200 + Math.random() * 500);
			this.nextShoot = nextShoot;
		}
	}
}
