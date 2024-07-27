package com.t04.upgrades;

import com.t04.main.Main;
import com.t04.entities.players.IPlayer;
import com.t04.main.GameLib;
import java.awt.Color;

public class Heal implements Upgrade {
	private double x;
	private double y;
	private double v;
	private int state;
	private int heal;
	private long lastUpdateTime = 0;
	private int colorState = 0;
	
	public Heal(double x, double y) {
		this.x = x;
		this.y = y;
		v = 0.5;
		state = Main.ACTIVE;
		heal = 10;
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
	
	public int getHeal() {
		return heal;
	}
	
	public void draw(long currentTime) {
		if (state == Main.ACTIVE) {
			if (currentTime - lastUpdateTime >= 200) {
				lastUpdateTime = currentTime;
				colorState = (colorState + 1) % 4;
			}
			switch (colorState) {
				case 0:
					GameLib.setColor(new Color(0, 255, 0));
					break;
				case 1:
					GameLib.setColor(new Color(50, 255, 50));
					break;
				case 2:
					GameLib.setColor(new Color(100, 255, 100));
					break;
				case 3:
					GameLib.setColor(new Color(150, 255, 150));
					break;
			}
			GameLib.drawCircle(x, y, 9);
			GameLib.drawCross(x, y, 6, 2);
		}
	}
	
	public void update() {
		y += v;
		if(y > GameLib.HEIGHT) {
			state = Main.INACTIVE;
		}
	}
	
	public void updateForEnemy(double x, double y) {
		this.x = x;
		this.y = y;
		if(y > GameLib.HEIGHT) {
			state = Main.INACTIVE;
		}
		else {
			state = Main.ACTIVE;
		}
	}
	
	public double distPlayer(IPlayer player) {
		double dx = player.getX() - getX();
		double dy = player.getY() - getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public boolean colidedWithPlayer(IPlayer player) {
		return distPlayer(player) < (player.getRadius() + 3);
	}
	
	public IPlayer colisionWithPlayer(IPlayer player) {
		if(colidedWithPlayer(player)) {
			this.state = Main.INACTIVE;
			player.increaseLife(heal);
		}
		return player;
	}
}
