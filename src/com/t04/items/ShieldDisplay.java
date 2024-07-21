package com.t04.items;
import com.t04.main.*;
import java.awt.Color;
public class ShieldDisplay extends Display {
	private double finalX;
	public ShieldDisplay(int width, double finalX) {
		super(width);
		this.finalX = finalX;
	}
	
	public void draw(int shieldLife) {
		shieldLife = Math.max(0, Math.min(getWidth(), shieldLife));
		GameLib.drawRectangle(finalX, GameLib.HEIGHT * 0.93, getWidth() + 9, 25);
		GameLib.setColor(Color.CYAN);

		double currentBarWidth = 100 * (shieldLife / 100.0);

		double currentX = (finalX + 5 + getWidth() / 2) - (50 - currentBarWidth) / 2;

		GameLib.fillRect(currentX, 683, currentBarWidth, 18);
	}
}
