package com.t04.items;
import com.t04.main.*;
import java.awt.Color;
public class ScoreDisplay extends Display {
	public ScoreDisplay(int width) {
		super(width);
	}
	
	public void draw(int score) {
		GameLib.setColor(Color.WHITE);
		GameLib.drawString(new String("SCORE"), GameLib.WIDTH * 0.85, GameLib.HEIGHT * 0.93 - 5);

		String scoreString = Integer.toString(score);
		GameLib.drawString(new String(scoreString), GameLib.WIDTH * 0.85, GameLib.HEIGHT * 0.95);
	}
}
