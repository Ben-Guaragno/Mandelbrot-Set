import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Mandelbrot{
    public static void main(String[] args) throws Exception {
        int width = 1920*4, height = 1080*4, max = 2000;
        final double fact=.0000687456;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = (col - width/2)*fact/width+(-.732990109824);
                double c_im = (row - height/2)*fact/width-(.22123013832316923);
                double x = 0, y = 0;
                int iteration = 0;
                while (x*x+y*y < 4 && iteration < max) {
                    double x_new = x*x-y*y+c_re;
                    y = 2*x*y+c_im;
                    x = x_new;
                    iteration++;
                }
                if (iteration < max) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, black);
            }
        }

        ImageIO.write(image, "png", new File("mandelbrot.png"));
        System.out.println("DONE");
    }
}

