package com.t04.entities.players;
import java.awt.Color;

import com.t04.items.ShieldDisplay;
import com.t04.main.GameLib;
import com.t04.main.Main;
import com.t04.entities.projectiles.Projectile;
import com.t04.entities.projectiles.ProjEnemy;
import com.t04.entities.enemies.Enemy;
import java.util.List;

public class ShieldPlayer implements IPlayer, Decorador{
	private IPlayer player;
	private ShieldDisplay displayShield;
	private int lifeShield;
	private int shieldState;
	private long lastUpdateTime;
	private int colorState;
	private double shieldRadius;
	
	private static final int lifeShieldMax = 50;
	
	public ShieldPlayer(IPlayer player, int shieldLife) {
		this.player = player;
		displayShield = new ShieldDisplay(player.getWidth(), player.getFinalX());
		lifeShield = shieldLife;
		shieldState = Main.ACTIVE;
		lastUpdateTime = 0;
		shieldRadius = 20;
		colorState = 0;
	}
	
	public IPlayer getDecorado() {
		return player;
	}
	
	public void increaseLife(int life) {
		player.increaseLife(life);
	}
	
	private int getShieldMax() {
		return lifeShieldMax;
	}
	
	public int getState() {
		return shieldState;
	}
	
	public double getExplosionStart() {
		return player.getExplosionStart();
	}
	
	public double getExplosionEnd() {
		return player.getExplosionEnd();
	}
	
	public double getX() {
		return player.getX();
	}
	
	public double getY() {
		return player.getY();
	}
	
	public double getVX() {
		return player.getVX();
	}
	
	public double getVY() {
		return player.getVY();
	}
	
	public double getRadius() {
		return shieldRadius;
	}
	
	public long getNextShoot() {
		return player.getNextShoot();
	}
	
	public void drawLife() {
		player.drawLife();
	}
	
	public void drawScore() {
		player.drawScore();
	}
	
	public void draw(long currentTime) {
		player.draw(currentTime);
		GameLib.setColor(Color.DARK_GRAY);
		displayShield.draw(lifeShield);
		if (getState() == Main.SHIELD_DAMAGE) {
			GameLib.setColor(Color.WHITE);
			displayShield.draw(lifeShield);
			GameLib.setColor(Color.WHITE);
			GameLib.drawCircle(getX(), getY(), 18);
			GameLib.drawCircle(getX(), getY(), 19);
			GameLib.drawCircle(getX(), getY(), 20);
		} else if (getState() == Main.ACTIVE) {
			if (currentTime - lastUpdateTime >= 200) {
				this.lastUpdateTime = currentTime;
				this.colorState = (colorState + 1) % 3;
			}
			switch (colorState) {
				case 0:
					GameLib.setColor(new Color(0, 255, 255));
					break;
				case 1:
					GameLib.setColor(new Color(50, 255, 255));
					break;
				case 2:
					GameLib.setColor(new Color(100, 255, 255));
					break;
			}
			GameLib.drawCircle(getX(), getY(), 18);
			GameLib.drawCircle(getX(), getY(), 19);
			GameLib.drawCircle(getX(), getY(), 20);
		}
	}
		
	public void up(long delta) {
		player.up(delta);
	}
	
	public void down(long delta) {
		player.down(delta);
	}
	
	public void left(long delta) {
		player.left(delta);
	}
	
	public void right(long delta) {
		player.right(delta);
	}
	
	public void shoot(List<Projectile> projectiles, long currentTime) {
		player.shoot(projectiles, currentTime);
	}
	
	public boolean hasNextShoot(long currentTime) {
		return player.hasNextShoot(currentTime);
	}
	
	public boolean finishedGame() {
		return player.finishedGame();
	}
	
	public void verifyInGame() {
		player.verifyInGame();
	}
	
	public double distProjectile(ProjEnemy projectile) {
		return player.distProjectile(projectile);
	}
	
	public double distEnemy(Enemy enemy) {
		return player.distEnemy(enemy);
	}
	
	public boolean colidedWithEnemy(Enemy enemy) {
		return player.colidedWithEnemy(enemy);
	}
	
	public boolean colidedWithProjectile(ProjEnemy projectile) {
		return player.colidedWithProjectile(projectile);
	}
	
	public void onPlayer() {
		player.onPlayer();
	}
	
	public void offPlayer() {
		player.offPlayer();
	}
	
	public int getScore() {
		return player.getScore();
	}
	
	public int getLife() {
		return player.getLife();
	}
	
	public void setScore(int score) {
		player.setScore(score);
	}
	
	public void update(long currentTime) {
		if(getState() == Main.SHIELD_DAMAGE) {
			if(currentTime > getDamageEnd()) {
				shieldState = Main.ACTIVE;
			}
		}
		player.update(currentTime);
	}
	
	public boolean needToRevert(long currentTime) {
		return getState() == Main.INACTIVE;
	}
	
	public IPlayer revert(long currentTime) {
		if(needToRevert(currentTime)) {
			return player.revert(currentTime);
		}
		else {
			player = player.revert(currentTime);
			return this;
		}
	}
	
	public void isDamage() {
		player.isDamage();
	}
	
	public double getDamageStart() {
		return player.getDamageStart();
	}
	
	public double getDamageEnd() {
		return player.getDamageEnd();
	}
	
	public void setDamageStart(double damage_start) {
		player.setDamageStart(damage_start);
	}
	
	public void setDamageEnd(double damage_end) {
		player.setDamageEnd(damage_end);
	}
	
	public void colisionWithEnemy(long currentTime, Enemy enemy) {
		if(colidedWithEnemy(enemy)) {
			lifeShield -= enemy.getDamage();
			lifeShield = Math.max(0, Math.min(getShieldMax(), lifeShield));
			enemy.setState(Main.EXPLODING);
			enemy.setExplosionStart(currentTime);
			enemy.setExplosionEnd(currentTime + 500);
			if(lifeShield > 0) {
				shieldState = Main.SHIELD_DAMAGE;
				setDamageStart(currentTime);
				setDamageEnd(currentTime + 50);
			}
			else {
				shieldState = Main.INACTIVE;
			}
		}
	}
	
	public void colisionWithProjectile(long currentTime, ProjEnemy projectile) {
		if(colidedWithProjectile(projectile)) {
			lifeShield -= projectile.getDamage();
			lifeShield = Math.max(0, Math.min(getShieldMax(), lifeShield));
			if(lifeShield > 0) {
				shieldState = Main.SHIELD_DAMAGE;
				setDamageStart(currentTime);
				setDamageEnd(currentTime + 50);
			}
			else {
				shieldState = Main.INACTIVE;
			}
		}
	}
	
	public int getWidth() {
		return player.getWidth();
	}
	
	public double getFinalX() {
		return player.getFinalX();
	}
	
	public void setShieldLife(int shieldLife) {
		lifeShield += shieldLife;
		lifeShield = Math.max(0, Math.min(getShieldMax(), lifeShield));
	}
	
	public int getShieldLife() {
		return lifeShield;
	}
}
