package com.t04.main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/***********************************************************************/
/*                                                                     */
/* Classe com métodos úteis para implementação de um jogo. Inclui:     */
/*                                                                     */
/* - Método para iniciar modo gráfico.                                 */
/*                                                                     */
/* - Métodos para desenhos de formas geométricas.                      */
/*                                                                     */
/* - Método para atualizar o display.                                  */
/*                                                                     */
/* - Método para verificar o estado (pressionada ou não pressionada)   */
/*   das teclas usadas no jogo:                                        */
/*                                                                     */
/*   	- up, down, left, right: movimentação do player.               */
/*		- control: disparo de projéteis.                               */
/*		- ESC: para sair do jogo.                                      */
/*                                                                     */
/***********************************************************************/

public class GameLib {
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	
	public static final int KEY_UP = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_LEFT = 2;
	public static final int KEY_RIGHT = 3;
	public static final int KEY_CONTROL = 4;
	public static final int KEY_ESCAPE = 5;

	private static MyFrame frame = null;
	private static Graphics g = null;
	private static MyKeyAdapter keyboard = null;
	
	public static void initGraphics(){
		
		frame = new MyFrame("Projeto COO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		keyboard = new MyKeyAdapter();
		frame.addKeyListener(keyboard);
		frame.requestFocus();
		
		frame.createBufferStrategy(2);		
		g = frame.getBufferStrategy().getDrawGraphics();
	}
	
	public static void setColor(Color c){
		
		g.setColor(c);
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2){
		
		g.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	
	public static void drawCircle(double cx, double cy, double radius){
		
		int x = (int) Math.round(cx - radius);
		int y = (int) Math.round(cy - radius);
		int width = (int) Math.round(2 * radius);
		int height = (int) Math.round(2 * radius);
		
		g.drawOval(x, y, width, height);
	}
	
	public static void drawDiamond(double x, double y, double radius){
		
		int x1 = (int) Math.round(x);
		int y1 = (int) Math.round(y - radius);
		
		int x2 = (int) Math.round(x + radius);
		int y2 = (int) Math.round(y);
		
		int x3 = (int) Math.round(x);
		int y3 = (int) Math.round(y + radius);
		
		int x4 = (int) Math.round(x - radius);
		int y4 = (int) Math.round(y);
		
		drawLine(x1, y1, x2, y2);
		drawLine(x2, y2, x3, y3);
		drawLine(x3, y3, x4, y4);
		drawLine(x4, y4, x1, y1);
	}
	
	public static void drawRectangle(double cx, double cy, double width, double height) {
		int x = (int) Math.round(cx);
		int y = (int) Math.round(cy);

		g.drawRect(x, y, (int) width, (int) height);
	}
	
	public static void drawStarPlayer(double player_X, double player_Y, double player_size) {

		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X - player_size * 0.25,
				player_Y + player_size * 0.5);
		GameLib.drawLine(player_X, player_Y + player_size, player_X - player_size * 0.25, player_Y + player_size * 0.5);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X + player_size * 0.25,
				player_Y + player_size * 0.5);
		GameLib.drawLine(player_X, player_Y + player_size, player_X + player_size * 0.25, player_Y + player_size * 0.5);
	}
	
	public static void drawCross(double x, double y, double maiorDist, double menorDist) {
		// maiorDist seria a distância do centro (x,y) à qualquer das 4 arestas mais
		// distantes da cruz

		// menorDist seria a distância do centro (x,y) à qualquer das 4 arestas
		// imaginárias que ligam dois vértices adjacentes dos 4 mais próximos ao centro

		GameLib.drawLine(x + maiorDist, y - menorDist, x + maiorDist, y + menorDist);
		GameLib.drawLine(x + menorDist, y + menorDist, x + maiorDist, y + menorDist);
		GameLib.drawLine(x + menorDist, y + menorDist, x + menorDist, y + maiorDist);
		GameLib.drawLine(x - menorDist, y + maiorDist, x + menorDist, y + maiorDist);
		GameLib.drawLine(x - menorDist, y + maiorDist, x - menorDist, y + menorDist);
		GameLib.drawLine(x - maiorDist, y + menorDist, x - menorDist, y + menorDist);
		GameLib.drawLine(x - maiorDist, y + menorDist, x - maiorDist, y - menorDist);
		GameLib.drawLine(x - menorDist, y - menorDist, x - maiorDist, y - menorDist);
		GameLib.drawLine(x - menorDist, y - menorDist, x - menorDist, y - maiorDist);
		GameLib.drawLine(x + menorDist, y - maiorDist, x - menorDist, y - maiorDist);
		GameLib.drawLine(x + menorDist, y - maiorDist, x + menorDist, y - menorDist);
		GameLib.drawLine(x + maiorDist, y - menorDist, x + menorDist, y - menorDist);
	}
	
	public static void drawPlayer(double player_X, double player_Y, double player_size){
		
		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
	}
	
	public static void drawExplosion(double x, double y, double alpha){

		int p = 5;
		int r = (int) (255 - Math.pow(alpha, p) * 255);
		int g = (int) (128 - Math.pow(alpha, p) * 128);
		int b = 0;
		
		r = Math.max(0, r);
		g = Math.max(0, g);

		GameLib.setColor(new Color(r, g, b));
		GameLib.drawCircle(x, y, alpha * alpha * 40);
		GameLib.drawCircle(x, y, alpha * alpha * 40 + 1);
	}
	
	public static void drawArc(double cx, double cy, double width, double height, double startAngle, double arcAngle) {
		int x = (int) Math.round(cx);
		int y = (int) Math.round(cy);
		int w = (int) Math.round(width);
		int h = (int) Math.round(height);
		int sAngle = (int) Math.round(startAngle);
		int aAngle = (int) Math.round(arcAngle);

		g.drawArc(x, y, w, h, sAngle, aAngle);
	}
	
	public static void drawHexagon(double cx, double cy, double radius) {
		int x = (int) Math.round(cx);
		int y = (int) Math.round(cy);

		// raio da circunferência que o hexágono regular está inscrito
		int raio = (int) Math.round(radius);

		GameLib.drawLine(x + raio * Math.cos(Math.PI / 3), y + raio * Math.sin(Math.PI / 3), x + raio, y);
		GameLib.drawLine(x + raio * Math.cos(Math.PI * 5 / 3), y + raio * Math.sin(Math.PI * 5 / 3), x + raio, y);
		GameLib.drawLine(x + raio * Math.cos(Math.PI * 5 / 3), y + raio * Math.sin(Math.PI * 5 / 3),
				x + raio * Math.cos(Math.PI * 4 / 3), y + raio * Math.sin(Math.PI * 4 / 3));
		GameLib.drawLine(x - raio, y, x + raio * Math.cos(Math.PI * 4 / 3), y + raio * Math.sin(Math.PI * 4 / 3));
		GameLib.drawLine(x - raio, y, x + raio * Math.cos(Math.PI * 2 / 3), y + raio * Math.sin(Math.PI * 2 / 3));
		GameLib.drawLine(x + raio * Math.cos(Math.PI / 3), y + raio * Math.sin(Math.PI / 3),
				x + raio * Math.cos(Math.PI * 2 / 3), y + raio * Math.sin(Math.PI * 2 / 3));
	}
	
	public static void drawStar(double cx, double cy, double radius1, double radius2) {
		double[] outerX = new double[5];
		double[] outerY = new double[5];
		double[] innerX = new double[5];
		double[] innerY = new double[5];

		// Calculate outer points
		for (int k = 0; k < 5; k++) {
			outerX[k] = cx + radius1 * Math.cos((2 * Math.PI * k / 5) - (Math.PI / 2));
			outerY[k] = cy + radius1 * Math.sin((2 * Math.PI * k / 5) - (Math.PI / 2));
		}

		// Calculate inner points
		for (int k = 0; k < 5; k++) {
			innerX[k] = cx + radius2 * Math.cos((2 * Math.PI * k / 5) - (Math.PI / 2) + (2 * Math.PI / 10));
			innerY[k] = cy + radius2 * Math.sin((2 * Math.PI * k / 5) - (Math.PI / 2) + (2 * Math.PI / 10));
		}

		// Draw lines connecting outer and inner points
		for (int i = 0; i < 5; i++) {
			GameLib.drawLine(outerX[i], outerY[i], innerX[i], innerY[i]);
			if (i == 0) {
				GameLib.drawLine(outerX[i], outerY[i], innerX[4], innerY[4]);
			} else {
				GameLib.drawLine(outerX[i], outerY[i], innerX[i - 1], innerY[i - 1]);
			}
		}

	}
	
	public static void drawString(String string, double cx, double cy) {
		int x = (int) Math.round(cx);
		int y = (int) Math.round(cy);

		g.drawString(string, x, y);
	}

	public static void drawNumber(Number number, double cx, double cy) {
		String str = number.toString();
		int x = (int) Math.round(cx);
		int y = (int) Math.round(cy);

		g.drawString(str, x, y);
	}

	
	public static void fillRect(double cx, double cy, double width, double height){
		
		int x = (int) Math.round(cx - width/2);
		int y = (int) Math.round(cy - height/2);
		
		g.fillRect(x, y, (int) Math.round(width), (int) Math.round(height));
	}
	
	public static void display(){
									
		g.dispose();
		frame.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		g = frame.getBufferStrategy().getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth() - 1, frame.getHeight() - 1);
		g.setColor(Color.WHITE);
	}
	
	public static boolean iskeyPressed(int index){
		
		return keyboard.isKeyPressed(index);
	}
	
	public static void debugKeys(){
		
		keyboard.debug();
	}
}

@SuppressWarnings("serial")
class MyFrame extends JFrame {
	
	public MyFrame(String title){
		
		super(title);
	}

	public void paint(Graphics g){ }
	
	public void update(Graphics g){ }
	
	public void repaint(){ }
}

class MyKeyAdapter extends KeyAdapter{
	
	private int [] codes = {	KeyEvent.VK_UP,
								KeyEvent.VK_DOWN,
								KeyEvent.VK_LEFT,
								KeyEvent.VK_RIGHT, 
								KeyEvent.VK_CONTROL,
								KeyEvent.VK_ESCAPE
							};
	
	private boolean [] keyStates = null;
	private long [] releaseTimeStamps = null;
	
	public MyKeyAdapter(){
		
		keyStates = new boolean[codes.length];
		releaseTimeStamps = new long[codes.length];
	}
	
	public int getIndexFromKeyCode(int keyCode){
		
		for(int i = 0; i < codes.length; i++){
			
			if(codes[i] == keyCode) return i;
		}
		
		return -1;
	}
	
	public void keyPressed(KeyEvent e){
		
		//System.out.println("KeyPressed " + e.getWhen() + " " + System.currentTimeMillis());
		
		int index = getIndexFromKeyCode(e.getKeyCode());
		
		if(index >= 0){
			
			keyStates[index] = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
	
		//System.out.println("KeyReleased " + e.getWhen() + " " + System.currentTimeMillis());
		
		int index = getIndexFromKeyCode(e.getKeyCode());
		
		if(index >= 0){
			
			keyStates[index] = false;
			releaseTimeStamps[index] = System.currentTimeMillis();
		}
	}
	
	public boolean isKeyPressed(int index){
		
		boolean keyState = keyStates[index];
		long keyReleaseTime = releaseTimeStamps[index];
		
		if(keyState == false){

			if(System.currentTimeMillis() - keyReleaseTime > 5) return false;
		}
		
		return true;		
	}
	
	public void debug(){
		
		System.out.print("Key states = {");
		
		for(int i = 0; i < codes.length; i++){
			
			System.out.print(" " + keyStates[i] + (i < (codes.length - 1) ? "," : ""));
		}
		
		System.out.println(" }");
	}
}

