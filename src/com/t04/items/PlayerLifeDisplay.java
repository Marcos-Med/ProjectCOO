package com.t04.items;
import com.t04.main.*;
import java.awt.Color;
public class PlayerLifeDisplay extends Display {
	public PlayerLifeDisplay(int width) {
		super(width);
	}
	
	public void draw(int life) {
		life = Math.max(0, Math.min(getWidth(), life));
		GameLib.drawString(new String("LIFE"), GameLib.WIDTH * 0.05, GameLib.HEIGHT * 0.93 - 5);
		GameLib.drawRectangle(GameLib.WIDTH * 0.05, GameLib.HEIGHT * 0.93, getWidth() + 9, 25);
		int r = Math.max(0, Math.min(255, (int) (255 * (1.0 - life / 100.0))));
		int g = Math.max(0, Math.min(255, (int) (255 * (life / 100.0))));
		GameLib.setColor(new Color(r, g, 0));

		double currentBarWidth = 100 * (life / 100.0);

		double currentX = (GameLib.WIDTH * 0.05 + 5 + getWidth() / 2) - (getWidth() - currentBarWidth) / 2;

		GameLib.fillRect(currentX, 683, currentBarWidth, 18);
		GameLib.drawNumber(life, 60, GameLib.HEIGHT * 0.93 - 5);
	}
	
	public double getFinalX() {
		return GameLib.WIDTH * 0.05 + getWidth() + 9;
	}
	
}
