package com.t04.entities.players;
import java.awt.Color;

import com.t04.main.*;
import java.util.List;
import com.t04.entities.projectiles.*;
import com.t04.entities.enemies.Enemy;
import com.t04.items.ScoreDisplay;
import com.t04.items.PlayerLifeDisplay;
public class Player implements IPlayer, Explosive{
	private int state;
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double radius;
	private double explosion_start;
	private double explosion_end;
	private double damage_start;
	private double damage_end;
	private long nextShoot;
	private int score;
	private int life;
	private PlayerLifeDisplay displayLife;
	private ScoreDisplay displayScore;
	
	private static int lifeMax = 100;
	
	public void update(long currentTime) {
		if(getState() == Main.EXPLODING) {
			if(currentTime > getExplosionEnd()) {
				onPlayer();
				life = getLifeMAX();
			}
		}
		if(getState() == Main.DAMAGE) {
			if(currentTime > getDamageEnd()) {
				onPlayer();
			}
		}
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
	public double getVX() {
		return vx;
	}
	public double getVY() {
		return vy;
	}
	public double getRadius() {
		return radius;
	}
	
	public int getWidth() {
		return displayLife.getWidth();
	}
	
	public double getFinalX() {
		return displayLife.getFinalX();
	}
	
	public double getExplosionStart() {
		return explosion_start;
	}
	public double getExplosionEnd() {
		return explosion_end;
	}
	public long getNextShoot() {
		return nextShoot;
	}
	
	public void drawExplosion(long currentTime) {
		double alpha = (currentTime - explosion_start) / (explosion_end - explosion_start);
		GameLib.drawExplosion(getX(), getY(), alpha);
	}
	
	public void drawLife() {
		displayLife.draw(getLife());
	}
	
	public void drawScore() {
		displayScore.draw(getScore());
	}
	
	public void draw(long currentTime) {
		if(getState() == Main.DAMAGE) {
			GameLib.setColor(Color.WHITE);
		}
		else {
			GameLib.setColor(Color.BLUE);
		}
		GameLib.drawPlayer(getX(), getY(), getRadius());
		drawLife();
		drawScore();
	}
	
	public void up(long delta) {
		y -= delta * getVY();
	}
	
	public void down(long delta) {
		y += delta * getVY();
	}
	
	public void left(long delta) {
		x -= delta * getVX();
	}
	
	public void right(long delta) {
		x += delta * getVY();
	}
	
	public boolean hasNextShoot(long currentTime) {
		return currentTime > getNextShoot();
	}
	
	public void shoot(List<Projectile> projectiles, long currentTime) {
		Projectile pjE = new ProjPlayer(getX(), getY() - (2 * radius));
		if(hasNextShoot(currentTime) && (projectiles.size() < pjE.getMax())) {
			projectiles.add(pjE);
			nextShoot = currentTime + 100;
		}
	}
	
	public boolean finishedGame() {
		return false;
	}
	
	public boolean needToRevert(long currentTime) {
		return false;
	}
	
	public IPlayer revert(long currentTime) {
		return this;
	}
	
	public void verifyInGame() {
		if(x < 0.0) x = 0.0;
		if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
		if(y < 25.0) y = 25.0;
		if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;
	}
	
	public double distProjectile(ProjEnemy projectile) {
		double dx = projectile.getX() - getX();
		double dy = projectile.getY() - getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double distEnemy(Enemy enemy) {
		double dx = enemy.getX() - getX();
		double dy = enemy.getY() - getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public void isExploding() {
		state = Main.EXPLODING;
	}
	
	public void isDamage() {
		state = Main.DAMAGE;
	}
	
	public void onPlayer() {
		state = Main.ACTIVE;
	}
	
	public void offPlayer() {
		state = Main.INACTIVE;
	}
	
	public boolean colidedWithEnemy(Enemy enemy) {
		return distEnemy(enemy) < ((getRadius() + enemy.getRadius()) * 0.8);
	}
	
	public boolean colidedWithProjectile(ProjEnemy projectile) {
		return distProjectile(projectile) < ((getRadius() + projectile.getRadius()) * 0.8);
	}
	
	public void exploded(long currentTime) {
		 isExploding();
		 explosion_start = currentTime;
		 explosion_end = currentTime + 2000;
	}
	
	public void damageded(long currentTime) {
		isDamage();
		damage_start = currentTime;
		damage_end = currentTime + 50;
	}
	
	public double getDamageStart() {
		return damage_start;
	}
	
	public double getDamageEnd() {
		return damage_end;
	}
	
	public void setDamageStart(double damage_start) {
		this.damage_start = damage_start;
	}
	
	public void setDamageEnd(double damage_end) {
		this.damage_end = damage_end;
	}
	
	public void colisionWithEnemy(long currentTime, Enemy enemy) {
		if(colidedWithEnemy(enemy)) {
			life = getLife() - enemy.getDamage();
			life = Math.max(0, Math.min(lifeMax, getLife()));
			if(getLife() <= 0) {
				exploded(currentTime);
			}
			else {
				damageded(currentTime);
			}
		}
	}
	
	public void colisionWithProjectile(long currentTime, ProjEnemy proj) {
		if(colidedWithProjectile(proj)) {
			life = getLife() - proj.getDamage();
			life = Math.max(0, Math.min(lifeMax, getLife()));
			if(getLife() <= 0) {
				exploded(currentTime);
			}
			else {
				damageded(currentTime);
			}
		}
	}
	
	
	public void increaseLife(int life) {
		this.life += life;
		this.life = Math.max(0, Math.min(lifeMax, getLife()));
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLife() {
		return life;
	}
	
	public int getLifeMAX() { //Only for Player
		return lifeMax;
	}
		
	public Player(long current_time){
		nextShoot = current_time;
		vx = 0.25;
		vy = 0.25;
		radius = 12.0;
		explosion_start = 0;
		explosion_end = 0;
		damage_start = 0;
		damage_end = 0;
		state = Main.ACTIVE;
		x = GameLib.WIDTH/2.0;
		y = GameLib.HEIGHT * 0.90;
		life = 100;
		score = 0;
		displayLife = new PlayerLifeDisplay(getLifeMAX());
		displayScore = new ScoreDisplay(score);
		
	}
	
}
