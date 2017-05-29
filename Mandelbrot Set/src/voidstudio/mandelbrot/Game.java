package voidstudio.mandelbrot;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	private Thread thread;
	private boolean running=false;
	private Window window;
	private Mandelbrot mandelbrot;
	private Menu menu;

	public enum STATE{
		Menu, Render, Select
	}
	public STATE gameState=STATE.Menu;
	
	public Game(){
		window=new Window(1000, 1000/12*9, "Mandelbrot", this);
		mandelbrot=new Mandelbrot(this);
		this.addKeyListener(new KeyInput(this));
		
		menu=new Menu(this,mandelbrot);
		this.addMouseListener(menu);
		start();
	}

	public synchronized void start(){
		thread=new Thread(this);
		thread.start();
		running=true;
	}
	public synchronized void stop(){
		try{
			thread.join();
			running=false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				delta--;
			}
//			if (running)
				tick();
				render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick(){												//Tick Method
		if(gameState==STATE.Render){
			mandelbrot.tick();
		}
	}
	private void render(){
		BufferStrategy bs=this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g=bs.getDrawGraphics();
		do {
			try{
				g=bs.getDrawGraphics();
				g.setColor(Color.black);
				g.fillRect(0, 0, window.getFrame().getWidth(), window.getFrame().getHeight());

				if(gameState==STATE.Menu || gameState==STATE.Select) menu.render(g);
				if(gameState==STATE.Render) mandelbrot.render(g);

			} finally {
				g.dispose();
			}
			bs.show();
		} while (bs.contentsLost());
		getToolkit().sync();
	}
	
	public void reset(){
		gameState=STATE.Menu;
		mandelbrot.resetFull();
	}

	public static int clamp(int var, int min, int max){
		if(var>=max)
			return max;
		else if(var<=min)
			return min;
		else
			return var;
	}

	public static void drawCenteredString(Graphics g, String s, Rectangle r, Font font) {
		//Outisde code for rendering a string centered in a rectangle r
		FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = (r.width / 2) - (rWidth / 2) - rX;
	    int b = (r.height / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, r.x + a, r.y + b);
	}

	public static void main(String[] args){
		new Game();
	}

	public Window getWindow() {
		return window;
	}

	public Mandelbrot getMandelbrot() {
		return mandelbrot;
	}

	public Menu getMenu() {
		return menu;
	}
}
