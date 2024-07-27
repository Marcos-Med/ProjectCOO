package com.t04.entities.enemies;

import java.util.Random;

import com.t04.upgrades.*;
import com.t04.main.*;
import java.awt.Color;

import com.t04.entities.players.IPlayer;
import com.t04.entities.projectiles.ProjEnemy;
import com.t04.entities.projectiles.Projectile;
import java.util.List;

public class EnemyThree extends Enemy {
	private int type;
	private double radius;
	private static long nextEnemy;
	private static int currentQuantity;
	private boolean damageable;
	private static final int max = 3;
	private long nextShoot;
	
	private Upgrade item;
	public EnemyThree(long currentTime) {
		super(Math.random() * (GameLib.WIDTH - 20.0) + 25, -10.0, 0.3, Math.PI * (3 / 2.0), 0.0, 15, 12);
		Random number = new Random();
		type = number.nextInt(3);
		damageable = false;
		nextShoot = currentTime + 500;
		nextEnemy = currentTime + 3000;
		currentQuantity++;
		switch(type) {
			case 0:
				item = new Heal(getX(), getY());
				radius = 36;
				break;
			case 1:
				item = new Shield(getX(), getY());
				radius = 36 * (2.0 / 3.0);
				break;
			case 2:
				item = new Star(getX(), getY());
				radius = 36 / 3.0;
				break;
			
		}
	}
	
	public boolean exitScreen() {
		return false;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public int getMax() {
		return max;
	}
	
	public Upgrade returnUpgrade() {
		return item;
	}
	
	public void draw() {
		switch(type) {
			case 0:
				GameLib.setColor(Color.YELLOW);
				GameLib.drawHexagon(getX(), getY(), 36);
				GameLib.setColor(new Color(236, 118, 0));
				GameLib.drawHexagon(getX(), getY(), 36 * (2.0 / 3.0));
				GameLib.setColor(Color.RED);
				GameLib.drawHexagon(getX(), getY(), 36 / 3.0);
				break;
			case 1:
				GameLib.setColor(new Color(236, 118, 0));
				GameLib.drawHexagon(getX(), getY(), 36 * (2.0 / 3.0));
				GameLib.setColor(Color.RED);
				GameLib.drawHexagon(getX(), getY(), 36 / 3.0);
				break;
			case 2:
				GameLib.setColor(Color.RED);
				GameLib.drawHexagon(getX(), getY(), 36 / 3.0);
				break;
		}
	}
	
	public int getQuantity() {
		return currentQuantity;
	}
	
	public void removeOneUnit() {
		currentQuantity--;
	} 
	
	public long getNextShoot() {
		return nextShoot;
	}
	
	private boolean hasNextShoot(long currentTime, IPlayer p) {
		return (currentTime > getNextShoot()) && (getY() < p.getY());
	}
	
	public static boolean hasNextEnemy(long currentTime) {
		return (currentTime > nextEnemy) && (currentQuantity < max);
	}
	
	public void update(long currentTime, long delta, List<Projectile> projectiles, IPlayer p) {
		if(getState() == Main.EXPLODING) {
			if(currentTime > getExplosionEnd()) {
				offState();
				p.setScore(p.getScore() + getScore());
			}
		}
		if(getState() == Main.ACTIVE) {
			if(getY() > radius) {
				damageable = true;
			}
			if(damageable) {
				if (getX() <= radius) {
					if (getAngle() == Math.PI * 3.0 / 4.0) {
						System.out.println("Yes");
						setAngle(Math.PI / 4.0);
					} else if (getAngle() == Math.PI * 5.0 / 4.0) {
						setAngle(Math.PI * 7.0 / 4.0);
						System.out.println("Yes");
					}
				} else if (getX() >= GameLib.WIDTH - radius) {
					if (getAngle() == Math.PI / 4.0) {
						setAngle(Math.PI * 3.0 / 4.0);
					} else if (getAngle() == Math.PI * 7.0 / 4.0) {
						setAngle(Math.PI * 5.0 / 4.0);
					}
				} else if (getY() <= radius) {
					if (getAngle() == Math.PI / 4.0) {
						setAngle(Math.PI * 7.0 / 4.0);
					} else if (getAngle() == Math.PI * 3.0 / 4.0) {
						setAngle(Math.PI * 5.0 / 4.0);
					} else if (getAngle() == Math.PI / 2.0) {
						setAngle(Math.PI * 3.0 / 2.0);
					}
				} else if (getY() >= GameLib.HEIGHT - radius) {
					if (getAngle() == Math.PI * 7.0 / 4.0) {
						setAngle(Math.PI / 4.0);
					} else if (getAngle() == Math.PI * 5.0 / 4.0) {
						setAngle(Math.PI * 3.0 / 4.0);
					} else if (getAngle() == Math.PI * 3.0 / 2.0) {
						setAngle(Math.PI / 2.0);
					}
				}
			}
			
		}
		setX(getX() + (getV() * Math.cos(getAngle()) * delta));
		setY(getY() + (getV() * Math.sin(getAngle()) * delta * (-1.0)));
		item.updateForEnemy(getX(), getY());
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
