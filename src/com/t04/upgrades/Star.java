package com.t04.upgrades;

import java.awt.Color;

import com.t04.entities.players.*;
import com.t04.main.GameLib;
import com.t04.main.Main;

public class Star implements Upgrade {
	private double x;
	private double y;
	private double v;
	private int state;
	private long lastUpdateTime = 0;
	private int colorState = 0;
	
	public Star(double x, double y) {
		this.x = x;
		this.y = y;
		v = 0.5;
		state = Main.ACTIVE;
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
	
	public void draw(long currentTime) {
		if(getState() == Main.ACTIVE) {
			if(currentTime - lastUpdateTime >= 200) {
				lastUpdateTime = currentTime;
				colorState = (colorState + 1) % 4;
			}
			switch(colorState) {
				case 0:
					GameLib.setColor(Color.RED);
					break;
				case 1:
					GameLib.setColor(Color.GREEN);
					break;
				case 2:
					GameLib.setColor(Color.BLUE);
					break;
				case 3:
					GameLib.setColor(Color.YELLOW);
					break;
			}
			
			GameLib.drawStar(x, y, 10, 4);
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
			if(player instanceof ShieldPlayer) {
				IPlayer p = ((ShieldPlayer) player).getDecorado();
				int lifeShield = ((ShieldPlayer) player).getShieldLife();
				IPlayer q;
				if(!(p instanceof StarPlayer)) {
					q = new StarPlayer(p);
					player = new ShieldPlayer(q);
				}
				else {
					q = ((StarPlayer) p).getDecorado();
					IPlayer star = new StarPlayer(q);
					player = new ShieldPlayer(star);
				}
				
				((ShieldPlayer) player).restoreShieldLife(lifeShield);
				return player;
			}
			else if(!(player instanceof StarPlayer)) {
				return new StarPlayer(player);
			}
			IPlayer p = ((StarPlayer) player).getDecorado();
			return new StarPlayer(p);
		}
		return player;
	}
}
