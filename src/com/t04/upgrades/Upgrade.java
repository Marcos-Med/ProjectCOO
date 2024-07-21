package com.t04.upgrades;

import com.t04.entities.players.IPlayer;

public interface Upgrade {
	public int getState();
	public void draw(long currentTime);
	public void update();
	public double getX();
	public double getY();
	public double distPlayer(IPlayer player);
	public boolean colidedWithPlayer(IPlayer player);
	public IPlayer colisionWithPlayer(IPlayer player);
	public void updateForEnemy(double x, double y);
}
