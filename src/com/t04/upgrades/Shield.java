package com.t04.upgrades;

import com.t04.main.GameLib;
import com.t04.main.Main;
import java.awt.Color;
import com.t04.entities.players.*;

public class Shield implements Upgrade {
	private double x;
	private double y;
	private double v;
	private int state;
	private int shieldLife;
	private long lastUpdateTime = 0;
	private int colorState = 0;
		
	public Shield(double x, double y){
		this.x = x;
		this.y = y;
		v = 0.5;
		state = Main.ACTIVE;
		shieldLife = 10;
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
	
	public int getShieldLife() {
		return shieldLife;
	}
	
	public void draw(long currentTime) {
		if(getState() == Main.ACTIVE) {
			if(currentTime - lastUpdateTime >= 200) {
				lastUpdateTime = currentTime;
				colorState = (colorState + 1) % 4;
			}
			
			switch(colorState) {
				case 0:
					GameLib.setColor(new Color(0, 255, 255));
					break;
				case 1:
					GameLib.setColor(new Color(50, 255, 255));
					break;
				case 2:
					GameLib.setColor(new Color(100, 255, 255));
					break;
				case 3:
					GameLib.setColor(new Color(150, 255, 255));
					break;
			}
			
			GameLib.drawLine(x - 6, y + 3, x - 6, y - 8);
			GameLib.drawLine(x + 5, y + 3, x + 5, y - 8);
			GameLib.drawLine(x - 6, y - 8, x + 5, y - 8);
			GameLib.drawArc(x - 6, y, 11, 7, 180, 180);
			GameLib.drawString(new String("S"), x - 4, y + 4);
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
		if(this.y > GameLib.HEIGHT) {
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
			if(player instanceof ShieldPlayer) {
				((ShieldPlayer)player).setShieldLife(shieldLife);
				return player;
			}
			else {
				IPlayer shield = new ShieldPlayer(player);
				return shield;
			}
		}
		else {
			return player;
		}
	}
}
