package com.t04.entities.players;

import java.awt.Color;
import java.util.List;

import com.t04.entities.enemies.Enemy;
import com.t04.entities.projectiles.ProjEnemy;
import com.t04.entities.projectiles.Projectile;
import com.t04.main.GameLib;
import com.t04.main.Main;

public class StarPlayer implements IPlayer, Decorador, Explosive {
	private IPlayer player;
	private long lastUpdateTime;
	
	public StarPlayer(IPlayer player) {
		this.player = player;
		lastUpdateTime = 0;
	}
	
	public boolean needToRevert(long currentTime) {
		return currentTime - lastUpdateTime > 20.000;
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
	
	public IPlayer getDecorado() {
		return player;
	}
	
	public int getState() {
		return player.getState();
	}
	
	public double getExplosionStart() {
		return player.getExplosionStart();
	}
	
	public double getExplosionEnd() {
		return player.getExplosionEnd();
	}
	
	public void drawExplosion(long currentTime) {
		double alpha = (currentTime - getExplosionStart()) / (getExplosionEnd() - getExplosionStart());
		GameLib.drawExplosion(getX(), getY(), alpha);
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
		return player.getRadius();
	}
	
	public long getNextShoot() {
		return player.getNextShoot();
	}
	
	public void draw(long currentTime) {
		if(getState() == Main.DAMAGE) {
			GameLib.setColor(Color.WHITE);
		}
		else {
			GameLib.setColor(Color.GREEN);
		}
		drawScore();
		drawLife();
		GameLib.drawStarPlayer(getX(), getY(), getRadius());
	}
	
	public void drawLife() {
		player.drawLife();
	}
	
	public void increaseLife(int life) {
		player.increaseLife(life);
	}
	
	public void drawScore() {
		player.drawScore();
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
		player.shoot(projectiles, currentTime - 40);
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
		player.update(currentTime);
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
		player.colisionWithEnemy(currentTime, enemy);
	}
	
	public void colisionWithProjectile(long currentTime, ProjEnemy projectile) {
		player.colisionWithProjectile(currentTime, projectile);
	}
	
	public int getWidth() {
		return player.getWidth();
	}
	
	public double getFinalX() {
		return player.getFinalX();
	}
}


