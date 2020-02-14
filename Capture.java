import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;

public class Capture
{
  static File[] images;
  static File currentImage;
  static boolean bool = true;
  static Vector<Integer> time = new Vector<Integer>();
  static int x = 0;
  static long startTime;
  static long endTime;
  
  public static void record(Rectangle rect)
  {
    try
    {
      Robot robot = new Robot();
      String savePath;
      BufferedImage bufferedImage;
      
      while(bool)
      {
        startTime = System.nanoTime();
        savePath = "saveFolder\\pic" + x + ".png";
        
        bufferedImage = robot.createScreenCapture(rect);
        ImageIO.write(bufferedImage, "png", currentImage = new File(savePath));
        x++;
        endTime = System.nanoTime();
        time.add((int)(endTime - startTime)/1000000);
      }
      
      images = new File[x];
      
      for(int i = 0; i < x; i++)
      {
        images[i] = new File("saveFolder\\pic" + i + ".png");
      }
    }
    catch(AWTException | IOException ex)
    {
      ex.printStackTrace();
    }
  }
}