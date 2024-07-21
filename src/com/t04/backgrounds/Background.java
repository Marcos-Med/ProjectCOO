package com.t04.backgrounds;
import com.t04.main.*;
import java.util.List;
import java.util.ArrayList;
public abstract class Background {
	private double speed;
	private double count;
	private List<Double> x;
	private List<Double> y;
	
	Background(double speed){
		this.speed = speed;
		this.count = 0.0;
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
	}
	
	public int size() {
		return x.size();
	}
	
	protected void generationScene(int size) {
		for(int i = 0; i < size; i++) {
			x.add(Math.random() * GameLib.WIDTH);
			y.add(Math.random() * GameLib.HEIGHT);
		}
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getCount() {
		return count;
	}
	
	public double getX(int i) {
		return x.get(i);
	}
	
	public double getY(int i) {
		return y.get(i);
	}
	
	protected void upCount(long delta) {
		count += speed * delta;
	}
	
	protected void fill(int value) {
		for(int i = 0; i < size(); i++) {
			GameLib.fillRect(getX(i), (getY(i) + getCount()) % GameLib.HEIGHT, value, value);
		}
	}
	
	public abstract void draw(long delta);
}
