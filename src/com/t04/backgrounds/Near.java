package com.t04.backgrounds;
import java.awt.Color;

import com.t04.main.*;
public class Near extends Background {
	public Near(){
		super(0.045);
		generationScene(50);
		
	}
	public void draw(long delta) {
		GameLib.setColor(Color.GRAY);
		upCount(delta);
		fill(3);
	}
}
