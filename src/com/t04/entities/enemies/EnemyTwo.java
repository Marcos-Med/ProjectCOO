package com.t04.entities.enemies;

import java.awt.Color;
import java.util.List;

import com.t04.entities.players.IPlayer;
import com.t04.entities.projectiles.Projectile;
import com.t04.entities.projectiles.ProjEnemy;
import com.t04.main.GameLib;

public class EnemyTwo extends Enemy{

	private static double spawnX = GameLib.WIDTH * 0.20;
	private static final double radius = 12.0;
	private static int count;
	private static long nextEnemy;
	private static final int max = 10;
	private static int currentQuantity;
	
	public EnemyTwo(long currentTime){
		super(spawnX, -10.0, 0.42, (3 * Math.PI) / 2, 0.0, 10, 8);
		count++;
		currentQuantity++;
		if(count < 10) {
			nextEnemy = currentTime + 120;
		}
		else {
			count = 0;
			spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
			nextEnemy = (long) (currentTime + 3000 + Math.random() * 3000);
		}
	}
	
	public boolean exitScreen() {
		return (getX() < -10) || (getX() > GameLib.WIDTH + 10);
	}
	
	public void removeOneUnit() {
		currentQuantity--;
	}
	
	public int getQuantity() {
		return currentQuantity;
	}
	
	public int getMax() {
		return max;
	}
	
	public double getSpawnX() {
		return spawnX;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public boolean nextEnemy() {
		return count < 10;
	}
	
	public static boolean hasNextEnemy(long currentTime) {
		return (currentTime > nextEnemy) && (currentQuantity < max);
	}
	
	public void draw() {
		GameLib.setColor(Color.MAGENTA);
		GameLib.drawDiamond(getX(), getY(), radius);
	}
	
	protected void Shoot(List<Projectile> projectiles, long currentTime, IPlayer p) {
		double threshold = GameLib.HEIGHT * 0.30;
		boolean shootNow = false;
		
		if(getPreviousY() < threshold && getY() >= threshold) {
			
			if(getX() < GameLib.WIDTH / 2) setRV(0.003);
			else setRV(-0.003);
		}
		
		if(getRV() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05){
			
			setRV(0.0);
			setAngle(3 * Math.PI);
			shootNow = true;
		}
		
		if(getRV() < 0 && Math.abs(getAngle()) < 0.05){
			
			setRV(0.0);
			setAngle(0.0);
			shootNow = true;
		}
		
		if(shootNow) {
			double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
			for(int k = 0; k < angles.length; k++) {
				double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
				double vx = Math.cos(a);
				double vy = Math.sin(a);
				Projectile pjE = new ProjEnemy(getX(), getY(), vx*0.30, vy*0.30);
				if(projectiles.size() < pjE.getMax()) {
					projectiles.add(pjE);
				}
			}
		}
		
	}
	
}
