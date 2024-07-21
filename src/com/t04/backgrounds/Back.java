package com.t04.backgrounds;
import java.awt.Color;

import com.t04.main.*;
public class Back extends Background{
	public Back(){
		super(0.070);
		generationScene(20);
	}
	
	public void draw(long delta) {
		GameLib.setColor(Color.DARK_GRAY);
		upCount(delta);
		fill(2);
	}
}
