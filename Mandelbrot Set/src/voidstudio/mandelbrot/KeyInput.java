package voidstudio.mandelbrot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import voidstudio.mandelbrot.Game.STATE;

public class KeyInput extends KeyAdapter{

	private Game game;

	public KeyInput(Game game){
		this.game=game;
	}

	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();

		if(game.gameState==STATE.Menu){
			if(key==KeyEvent.VK_UP){
				int max=game.getMandelbrot().getMax();
				game.getMandelbrot().setMax(game.getMandelbrot().getMax()*5);
				max=Game.clamp(max*5, 5, 1000000000);
				game.getMandelbrot().setMax(max);
			}
			else if(key==KeyEvent.VK_DOWN){
				int max=game.getMandelbrot().getMax();
				game.getMandelbrot().setMax(game.getMandelbrot().getMax()/5);
				max=Game.clamp(max/5, 5, Integer.MAX_VALUE);
				game.getMandelbrot().setMax(max);
			}
		}
		else if(game.gameState==STATE.Select){
			if(game.getMenu().getX()!=0&&game.getMenu().getY()!=0){
				if(key==KeyEvent.VK_UP){
					int width=game.getMenu().getWidth();
					game.getMenu().setWidth(width+10);
				}
				else if(key==KeyEvent.VK_DOWN){
					int width=game.getMenu().getWidth();
					game.getMenu().setWidth(width-10);
				}
				else if(key==KeyEvent.VK_ENTER){
					double factor=(double)game.getMenu().getWidth()/game.getWindow().getFrame().getWidth();
					double c_re=(game.getMenu().getX()-game.getWindow().getFrame().getWidth()/2.0)/(game.getWindow().getFrame().getWidth()/game.getMandelbrot().getFactor());
					double c_im=(game.getMenu().getY()-game.getWindow().getFrame().getHeight()/2.0)/(game.getWindow().getFrame().getHeight()/(game.getMandelbrot().getFactor()/1.35));
					game.getMandelbrot().reset(factor,c_re,c_im);
					game.gameState=STATE.Render;
					game.getMenu().reset();
				}
			}
			else{
				if(key==KeyEvent.VK_UP){
					game.getMenu().setMaxAdjust(50);
				}
				else if(key==KeyEvent.VK_DOWN){
					game.getMenu().setMaxAdjust(-50);
				}
				else if(key==KeyEvent.VK_ENTER){
					game.getMandelbrot().reset();
					game.getMandelbrot().setMax(game.getMenu().getMaxAdjust()+game.getMandelbrot().getMax());
					game.gameState=STATE.Render;
					game.getMenu().reset();
				}
			}
			if(key==KeyEvent.VK_R){
				Mandelbrot mandelbrot=game.getMandelbrot();
				mandelbrot.reset();
				game.gameState=STATE.Render;
				game.getMenu().reset();
			}
			else if(key==KeyEvent.VK_BACK_SPACE){
				game.getMenu().reset();
				game.getMandelbrot().reset(2,0,0);
				game.gameState=STATE.Render;
			}
			else if(key==KeyEvent.VK_HOME){
				game.reset();
			}
		}
		else if(game.gameState==STATE.Render){
			if(key==KeyEvent.VK_S){
				game.gameState=STATE.Select;
			}
		}
	}

	public void keyReleased(KeyEvent e){
		
	}
}
