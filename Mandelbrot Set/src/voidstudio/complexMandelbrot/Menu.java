package voidstudio.complexMandelbrot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import voidstudio.complexMandelbrot.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Mandelbrot mandelbrot;
	private int x,y;
	private int width,height;
	private int maxAdjust;
	
	public Menu(Game game,Mandelbrot mandelbrot){
		this.game=game;
		this.mandelbrot=mandelbrot;
		x=0; y=0;
		width=100; height=width/12*9;
		maxAdjust=0;
	}
	
	public void mousePressed(MouseEvent e){
		if(e.getButton()!=MouseEvent.BUTTON1)
			return;
		
		int mx=e.getX();
		int my=e.getY();
		
		
		if(game.gameState==STATE.Menu){
			if(mouseOver(mx, my, game.getWindow().getFrame().getWidth()/2-50-205, 300, 210, 64)){
				game.gameState=STATE.Render;
				game.getMandelbrot().setMode("Standard");
			}
			else if(mouseOver(mx, my, game.getWindow().getFrame().getWidth()/2+50, 300, 210, 64)){
				game.gameState=STATE.Render;
				game.getMandelbrot().setMode("exp");
			}
		}
		else if(game.gameState==STATE.Select){
			x=mx;y=my;
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean mouseOver(int mx, int my,int x,int y,int width, int height){
		if(mx>x && mx<x+width){
			if(my>y && my<y+height){
				return true;
			}
		}
		return false;	
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		DecimalFormat df=new DecimalFormat("#");

//		Font fnt=new Font("arial",1,50);
		Font fnt2=new Font("arial",1,30);
//		Font fnt3=new Font("arial",1,15);

		if(game.gameState==STATE.Menu){
			g.setColor(Color.white);
			Game.drawCenteredString(g, "Mandelbrot Set", new Rectangle(game.getWindow().getFrame().getWidth()/2-50, 10, 100, 64), new Font("arial",1,60));

			Game.drawCenteredString(g, "Start z=z^n+c", new Rectangle(game.getWindow().getFrame().getWidth()/2-200, 300,100,64), fnt2);

			Game.drawCenteredString(g, "Maximum iterations", new Rectangle(game.getWindow().getFrame().getWidth()/2-50, 170,100,64), fnt2);
			Game.drawCenteredString(g, ""+mandelbrot.getMax()+":Power of "+df.format(Math.log(mandelbrot.getMax())/Math.log(5)), new Rectangle(game.getWindow().getFrame().getWidth()/2-50, 205,100,64), fnt2);
			g.drawRect(game.getWindow().getFrame().getWidth()/2-50-205, 300, 210, 64);
			
			g.drawRect(game.getWindow().getFrame().getWidth()/2+50, 300, 210, 64);
			Game.drawCenteredString(g, "Start z=e^z+c", new Rectangle(game.getWindow().getFrame().getWidth()/2+105,300,100,64), fnt2);
		}
		else if(game.gameState==STATE.Select){
			mandelbrot.renderPrev(g);
			int gWidth=game.getWindow().getFrame().getWidth();
			int gHeight=game.getWindow().getFrame().getHeight();
			if(x!=0&&y!=0){
				
				String fac="Zoom Factor: "+(double)width/gWidth;
				String re="R[c]: "+(mandelbrot.getC_re_shift()+(x-gWidth/2.0)/(gWidth/(mandelbrot.getFactor())));
				String im="I[c]: "+(mandelbrot.getC_im_shift()-(y-gHeight/2.0)/(gHeight/(mandelbrot.getFactor()/2)));
				
				if(fac.length()>re.length() && fac.length()>im.length())
					Mandelbrot.renderBackgroundBox(g, fac, gWidth-300-5, 15,62);
				else if(re.length()>fac.length() && re.length()>im.length())
					Mandelbrot.renderBackgroundBox(g, re, gWidth-300-5, 15,62);
				else
					Mandelbrot.renderBackgroundBox(g, im, gWidth-300-5, 15,62);

				g.setColor(Color.white);
				
				g.drawLine(x-3, y, x+3, y);
				g.drawLine(x, y-3, x, y+3);
				g.drawRect(x-width/2, y-height/2, width, height);
				g.drawString("Zoom Factor: "+(double)width/gWidth, gWidth-300, 30);
				g.drawString("R[c]: "+(mandelbrot.getC_re_shift()+(x-gWidth/2.0)/(gWidth/(mandelbrot.getFactor()))), gWidth-300, 50);
				g.drawString("I[c]: "+(mandelbrot.getC_im_shift()-(y-gHeight/2.0)/(gHeight/(mandelbrot.getFactor()/2))), gWidth-300, 70);
			}
			else if(maxAdjust!=0){
				Mandelbrot.renderBackgroundBox(g, "Maximum iterations: "+(maxAdjust+mandelbrot.getMax()), gWidth-300-5, 15,22);
				g.setColor(Color.white);
				g.drawString("Maximum iterations: "+(maxAdjust+mandelbrot.getMax()),gWidth-300, 30);
			}
		}
	}
	
	public void reset(){
		x=0;y=0;
		width=100; height=width/12*9;
		maxAdjust=0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.height=width/12*9;
	}

	public int getHeight() {
		return height;
	}

	public void setMaxAdjust(int maxAdjust) {
		this.maxAdjust += maxAdjust;
	}
	
	public int getMaxAdjust(){
		return maxAdjust;
	}

}
