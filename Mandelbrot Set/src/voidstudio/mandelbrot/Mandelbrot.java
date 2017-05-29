package voidstudio.mandelbrot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import voidstudio.mandelbrot.Game.STATE;

public class Mandelbrot {

	private double factor,c_re_shift,c_im_shift;
	private int iteration;
	private int max;
	private int[] colors;
	private BufferedImage image;
	private Game game;
	private ArrayList<ArrayList<double[]>> xyArray=new ArrayList<ArrayList<double[]>>();
	
	public Mandelbrot(Game game) {
		setMax(625);
		factor=4;
		iteration=0;
		c_re_shift=0;
		c_im_shift=0;
		this.game=game;
		iteration=0;
		
		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();
		
		for(int row=0;row<height;row++){
			xyArray.add(new ArrayList<double[]>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new double[3]);
			}
		}
		c_re_shift=0; c_im_shift=0;
	}

	public void tick(){
		if(iteration>=max){
			game.gameState=STATE.Select;
			return;
		}

		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();

		image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if(row>=xyArray.size()){
					xyArray.add(new ArrayList<double[]>());
				}
				if(col>=xyArray.get(row).size()){
					xyArray.get(row).add(new double[3]);
				}

				double x=xyArray.get(row).get(col)[0];
				double y=xyArray.get(row).get(col)[1];

				if((int)xyArray.get(row).get(col)[2]==0){
					double c_re = (col - width/2)*factor/width+c_re_shift;
					double c_im = (row - height/2)*factor/width-c_im_shift;
					if(x*x+y*y < 4) {
						double x_new = x*x-y*y+c_re;
						xyArray.get(row).get(col)[1] = 2*x*y+c_im;
						xyArray.get(row).get(col)[0] = x_new;
						image.setRGB(col, row, 0);
					}
					else{
						image.setRGB(col, row, colors[iteration]);
						xyArray.get(row).get(col)[2]=colors[iteration];
					}
				}
				else{

					image.setRGB(col, row, (int)xyArray.get(row).get(col)[2]);
				}
				
				//Color pallete testing
				

//				if(col>iteration-2 && col<iteration+2){
//					image.setRGB(col, row,colors[iteration]);
//					xyArray.get(row).get(col)[2]=colors[iteration];
//				}
//				else{
//
//					image.setRGB(col, row, (int)xyArray.get(row).get(col)[2]);
//				}
				
				//Pow mod testing
				
//				if(row>=xyArray.size()){
//					xyArray.add(new ArrayList<double[]>());
//				}
//				if(col>=xyArray.get(row).size()){
//					xyArray.get(row).add(new double[3]);
//				}
//
//				double x=xyArray.get(row).get(col)[0];
//				double y=xyArray.get(row).get(col)[1];
//
//				if((int)xyArray.get(row).get(col)[2]==0){
//					double c_re = (col - width/2)*factor/width+c_re_shift;
//					double c_im = (row - height/2)*factor/width-c_im_shift;
//					if(x*x+y*y < 4) {
//						double x_new = x*x*x-3*x*y*y+c_re;
//						xyArray.get(row).get(col)[1] = 3*x*x*y-y*y*y+c_im;
//						xyArray.get(row).get(col)[0] = x_new;
//						image.setRGB(col, row, 0);
//					}
//					else{
//						image.setRGB(col, row, colors[iteration]);
//						xyArray.get(row).get(col)[2]=colors[iteration];
//					}
//				}
//				else{
//
//					image.setRGB(col, row, (int)xyArray.get(row).get(col)[2]);
//				}
			}
		}
		iteration++;
	}

	public void render(Graphics g){
		g.drawImage(image, 0, 0, null);
		
		String fac="Zoom Factor: "+factor;
		String re="R[c]: "+c_re_shift;
		String im="I[c]: "+c_im_shift;
		
		if(fac.length()>re.length() && fac.length()>im.length())
			renderBackgroundBox(g, fac, 15, 15,82);
		else if(re.length()>fac.length() && re.length()>im.length())
			renderBackgroundBox(g, re, 15, 15,82);
		else
			renderBackgroundBox(g, im, 15, 15,82);
		
		g.setColor(Color.white);

		g.drawString("Iteration: "+iteration, 20, 30);
		g.drawString("Zoom Factor: "+factor, 20, 50);
		g.drawString("R[c]: "+c_re_shift, 20, 70);
		g.drawString("I[c]: "+c_im_shift, 20, 90);
	}
	
	public void renderPrev(Graphics g){
		render(g);
	}
	
	public static void renderBackgroundBox(Graphics g, String s, int x, int y, int rHeight){
		Font font=g.getFont();
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
		
	    g.setColor(new Color(0,0,0,255/2));
		g.fillRect(x, y, rWidth+15, rHeight);
	}
	
	public void reset(double factor, double c_re_shift, double c_im_shift){
//		System.out.println(factor);
		this.factor*=factor;
		this.c_re_shift+=c_re_shift;
		this.c_im_shift-=c_im_shift;
		iteration=0;
		
		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();
		
		xyArray=new ArrayList<ArrayList<double[]>>();
		for(int row=0;row<height;row++){
			xyArray.add(new ArrayList<double[]>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new double[3]);
			}
		}
	}
	public void reset(){
		iteration=0;
		
		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();
		
		xyArray=new ArrayList<ArrayList<double[]>>();
		for(int row=0;row<height;row++){
			xyArray.add(new ArrayList<double[]>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new double[3]);
			}
		}
	}
	public void resetFull(){
		factor=4.0;
		c_im_shift=0;
		c_re_shift=0;
		reset();
	}
	
	//Get and Set
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		
		colors=new int[max];
		for (int i = 0; i<max; i++) {
//			colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
			float[] tmp=Color.RGBtoHSB(i%256, (i*3)%256, (i*7+39)%256, null);
			colors[i]=Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]);
		}
	}

	public double getFactor() {
		return factor;
	}

	public double getC_re_shift() {
		return c_re_shift;
	}

	public double getC_im_shift() {
		return c_im_shift;
	}


}
