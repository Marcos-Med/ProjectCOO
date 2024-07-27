package com.t04.main;
import com.t04.entities.players.*;
import com.t04.entities.projectiles.*;
import com.t04.entities.enemies.*;
import com.t04.backgrounds.*;
import com.t04.upgrades.*;
import java.util.List;
import java.util.ArrayList;

public class Main {
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	public static final int DAMAGE = 3;
	public static final int SHIELD_DAMAGE = 4;
	
	public static void busyWait(long time) { 
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	public static void main(String[] args) {
		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();
		
		IPlayer player = new Player(currentTime);
		List<Projectile> projectiles_player = new ArrayList<>();
		List<Projectile> projectiles_enemies = new ArrayList<>();
		List<Enemy> enemies  = new ArrayList<>();
		List<Upgrade> upgrades = new ArrayList<>();
		Background back = new Back();
		Background near = new Near();
		
		GameLib.initGraphics();
		
		while(running) {
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			if(player.getState() == ACTIVE) {
				for(int i = 0; i < projectiles_enemies.size(); i++) {
					Projectile p = projectiles_enemies.get(i);
					player.colisionWithProjectile(currentTime, (ProjEnemy) p);
				}
				for(int i = 0; i < enemies.size(); i++) {
					Enemy e = enemies.get(i);
					player.colisionWithEnemy(currentTime, e);
				}
				for(int i = 0; i < upgrades.size(); i++) {
					Upgrade u = upgrades.get(i);
					player = u.colisionWithPlayer(player);
				}
				
			}
			for(int i = 0; i < projectiles_player.size(); i++) {
				Projectile p = projectiles_player.get(i);
				for(int j = 0; j < enemies.size(); j++) {
					Enemy e = enemies.get(j);
					if(e.getState() == ACTIVE) {
						e.colidedWithProjectile((ProjPlayer) p, currentTime);
					}
				}
			}
			
			for(int i = 0; i < projectiles_player.size(); i++) {
				Projectile p = projectiles_player.get(i);
				if(p.needToRemove(delta)) {
					p.offProjectile();
					projectiles_player.remove(p);
				}
				else {
					p.updateXY(delta);
				}
			}
			for(int i = 0; i < projectiles_enemies.size(); i++) {
				Projectile p = projectiles_enemies.get(i);
				if(p.needToRemove(delta)) {
					p.offProjectile();
					projectiles_enemies.remove(p);
				}
				else {
					p.updateXY(delta);
				}
			}
			
			for(int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				e.update(currentTime, delta, projectiles_enemies, player);
				if(e.getState() == INACTIVE) {
					if(e instanceof EnemyThree) {
						upgrades.add(((EnemyThree) e).returnUpgrade());
					}
					enemies.remove(e);
					e.removeOneUnit();
				}
			}
			
			for(int i = 0; i < upgrades.size(); i++) {
				Upgrade u = upgrades.get(i);
				u.update();
				if(u.getState() == INACTIVE) {
					upgrades.remove(u);
				}
			}
		 	
			if(EnemyOne.hasNextEnemy(currentTime)) {
				enemies.add(new EnemyOne(currentTime));
			}
			
			if(EnemyTwo.hasNextEnemy(currentTime)) {
				enemies.add(new EnemyTwo(currentTime));
			}
			
			if(EnemyThree.hasNextEnemy(currentTime)) {
				enemies.add(new EnemyThree(currentTime));
			}
			
			if(player.getState() == EXPLODING) {
				if(currentTime >  player.getExplosionEnd()) {
					player.onPlayer();
				}
			}
			
			player.update(currentTime);
			player = player.revert();
			
			if(player.getState() == ACTIVE) {
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.up(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.down(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.left(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.right(delta);
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) player.shoot(projectiles_player, currentTime);
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = player.finishedGame();
			
			player.verifyInGame();
			
			back.draw(delta);
			near.draw(delta);
			if(!(player instanceof ShieldPlayer)) {
				
			}
			if((player.getState() == EXPLODING) && (player instanceof Explosive)) {
				((Explosive) player).drawExplosion(currentTime);
			}
			else {
				player.draw(currentTime);
			}
			
			for(Projectile p: projectiles_player) {
				p.draw();
			}
			
			for(Projectile p: projectiles_enemies) {
				p.draw();
			}
			
			for(Enemy e: enemies) {
				if(e.getState() == EXPLODING) {
					e.drawExplosion(currentTime);
				}
				else {
					e.draw();
				}
				
			}
			
			for(Upgrade up: upgrades) {
				up.draw(currentTime);
			}
			
			GameLib.display();
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
