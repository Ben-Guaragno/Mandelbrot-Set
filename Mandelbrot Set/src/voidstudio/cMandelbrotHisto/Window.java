package voidstudio.cMandelbrotHisto;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas{
	
	private JFrame frame;
	
	public Window(int width, int height, String title, Game game){
		frame=new JFrame(title);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setSize(new Dimension(width,height));
	//	frame.setMaximumSize(new Dimension (width,height));
	//	frame.setMinimumSize(new Dimension (width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(game);
	}

	public JFrame getFrame() {
		return frame;
	}
}
