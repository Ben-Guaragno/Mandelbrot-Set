package voidstudio.cMandelbrotHisto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import voidstudio.cMandelbrotHisto.Game.STATE;

public class Mandelbrot {

	private double factor,c_re_shift,c_im_shift;
	private int iteration;
	private int max;
	private int[] colors;
	private BufferedImage image;
	private Game game;
	private ArrayList<ArrayList<Complex>> xyArray=new ArrayList<ArrayList<Complex>>();
	private ArrayList<ArrayList<Integer>> xyArrayColor=new ArrayList<ArrayList<Integer>>();
	private String mode;
	private int[] histo;
	
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
			xyArray.add(new ArrayList<Complex>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new Complex(0,0));
			}
		}
		setxyArrayColor(width, height);
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
					xyArray.add(new ArrayList<Complex>());
					xyArrayColor.add(new ArrayList<Integer>());
				}
				if(col>=xyArray.get(row).size()){
					xyArray.get(row).add(new Complex(0,0));
					xyArrayColor.get(row).add(new Integer(0));
				}

				Complex c=xyArray.get(row).get(col);

				if((int)xyArrayColor.get(row).get(col)==0){
					double c_re = (col - width/2)*factor/width+c_re_shift;
					double c_im = (row - height/2)*factor/width-c_im_shift;
					double x=c.re();
					double y=c.im();
					int temp;
					if(mode.equals("Standard")) temp=4;
					else temp=250;
					if(x*x+y*y < temp) {
						if(mode.equals("Standard")) c=c.power(2);
						if(mode.equals("exp")) c=c.exp();
						
						xyArray.get(row).set(col, new Complex(c.re()+c_re,c.im()+c_im));
						image.setRGB(col, row, 0);
					}
					else{
						histo[iteration]++;
						xyArrayColor.get(row).set(col,new Integer(iteration));
					}
				}
//				else{
//
//					image.setRGB(col, row, xyArrayColor.get(row).get(col));
//				}
				
				
				//Color pallete testing
				

//				if(col>iteration-2 && col<iteration+2){
//					image.setRGB(col, row,colors[iteration]);
//					xyArrayColor.get(row).set(col,new Integer(colors[iteration]));
//				}
//				else{
//
//					image.setRGB(col, row, (int)xyArrayColor.get(row).get(col));
//				}
				
			}
		}
		iteration++;
		
		int maxHistoVal=histo[0];
		
		for(int x:histo){
			if(maxHistoVal<x) maxHistoVal=x;
		}
		
		softSetColorMax(maxHistoVal);
//		System.out.println(maxHistoVal);
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if((int)xyArrayColor.get(row).get(col)!=0){
					int i=histo[(int)xyArrayColor.get(row).get(col)];
					image.setRGB(col, row, i);
				}
			}
		}
		
		
		
	}

	public void render(Graphics g){
		g.drawImage(image, 0, 0, null);
		g.setColor(Color.white);

		g.drawString("Iteration: "+iteration, 20, 30);
		g.drawString("Zoom Factor: "+factor, 20, 50);
		g.drawString("R[c]: "+c_re_shift, 20, 70);
		g.drawString("I[c]: "+c_im_shift, 20, 90);
	}
	
	public void renderPrev(Graphics g){
//		g.setFont(new Font("arial",1,15));
		g.drawImage(image, 0, 0, null);
		g.setColor(Color.white);
		g.drawString("Iteration: "+iteration, 20, 30);
		g.drawString("Zoom Factor: "+factor, 20, 50);
		g.drawString("R[c]: "+c_re_shift, 20, 70);
		g.drawString("I[c]: "+c_im_shift, 20, 90);
	}
	
	public void reset(double factor, double c_re_shift, double c_im_shift){
		this.factor*=factor;
		this.c_re_shift+=c_re_shift;
		this.c_im_shift-=c_im_shift;
		iteration=0;
		
		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();
		
		xyArray=new ArrayList<ArrayList<Complex>>();
		for(int row=0;row<height;row++){
			xyArray.add(new ArrayList<Complex>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new Complex(0,0));
			}
		}
		setxyArrayColor(width, height);
	}
	public void reset(){
		iteration=0;
		
		int height=game.getWindow().getFrame().getHeight();
		int width=game.getWindow().getFrame().getWidth();
		
		xyArray=new ArrayList<ArrayList<Complex>>();
		for(int row=0;row<height;row++){
			xyArray.add(new ArrayList<Complex>());
			for(int col=0;col<width;col++){
				xyArray.get(row).add(new Complex(0,0));
			}
		}
		setxyArrayColor(width, height);
	}
	public void resetFull(){
		factor=4.0;
		c_im_shift=0;
		c_re_shift=0;
		reset();
	}
	private void setxyArrayColor(int width, int height){
		xyArrayColor=new ArrayList<ArrayList<Integer>>();
		for(int row=0;row<height;row++){
			xyArrayColor.add(new ArrayList<Integer>());
			for(int col=0;col<width;col++){
				xyArrayColor.get(row).add(new Integer(0));
			}
		}
		histo=new int[max];
	}
	
	//Get and Set
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		
		histo=new int[max];
		
		colors=new int[max];
/*		for (int i = 0; i<max; i++) {
//			colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
//			float[] tmp=Color.RGBtoHSB(i%256, (i*3)%256, (i*7+39)%256, null);
			float[] tmp=Color.RGBtoHSB((i)%256, (i*2)%256, (i*2)%256, null);
			colors[i]=Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]);
		}	*/
		
		double[][] colorVals=generatePalletVals(max);
		for(int i=0;i<max;i++){
			float[] tmp=Color.RGBtoHSB((int)colorVals[i][0], (int)colorVals[i][1], (int)colorVals[i][2], null);
			colors[i]=Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]);
//			if(colors[i]==-65277) System.out.println(colorVals[i][0]+":"+colorVals[i][1]+":"+colorVals[i][2]);
		}
	}
	
	private double[][] generatePalletVals(int max){
		double[][] colorVal=new double[max][3];
		int val=4*max/9;
		for(int i=0;i<val;i++){
			colorVal[i][0]=0;
			colorVal[i][1]=45+137.0/(val)*i;
			colorVal[i][2]=45+210.0/(val)*i;
		}
		val=max/9+val;
		for(int i=4*max/9;i<val;i++){
			colorVal[i][0]=255.0/(max/9)*(i-4*max/9);
			colorVal[i][1]=182.0+(255.0-182.0)/(max/9)*(i-4*max/9);
			colorVal[i][2]=255;
			if(colorVal[i][1]>255) colorVal[i][1]=255;
			if(colorVal[i][2]>255) colorVal[i][2]=255;
		}
		for(int i=val;i<max;i++){
			colorVal[i][0]=255;
			colorVal[i][1]=255.0-81.0/(4*max/9)*(i-5*max/9);
			colorVal[i][2]=255.0-231.0/(4*max/9)*(i-5*max/9);
			if(colorVal[i][1]>255) colorVal[i][1]=255;
			if(colorVal[i][2]>255) colorVal[i][2]=255;
		}
		
		return colorVal;
	}
	
	private void softSetColorMax(int val){
		colors=new int[val];
	
		double[][] colorVals=generatePalletVals(val);
		for(int i=0;i<val;i++){
			float[] tmp=Color.RGBtoHSB((int)colorVals[i][0], (int)colorVals[i][1], (int)colorVals[i][2], null);
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

	public void setMode(String mode) {
		this.mode = mode;
	}


}
