import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.*;


public class zGif extends JFrame implements java.awt.event.MouseListener
{
  static int x, y, x1, y1, x2, y2, z, width, height;
  static zGif zgif;
  JButton startButton = new JButton("Start");
  JButton stopButton = new JButton("Stop");
  
  public zGif()
  {
    startButton.addActionListener(e -> 
    {
      z = 3;
      remove(startButton);
      add(stopButton);
      setVisible(false);
      setVisible(true);
    }
    );
    stopButton.addActionListener(e ->
    {
      Capture.bool = false;
      zgif.dispose();
    }
    );
    setAlwaysOnTop(true);
    addMouseListener(this);
    setUndecorated(true);
    pack();
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setLocationRelativeTo(null);
    AWTUtilities.setWindowOpacity(this, 0.50f);
    setVisible(true);
  }
  
  public void mousePressed(java.awt.event.MouseEvent ev)
  {
    if(ev.getButton() == 1 && z == 0)
    {
      x1 = ev.getX();
      y1 = ev.getY();
      z++;
    }
  }
  
  public void mouseReleased(java.awt.event.MouseEvent ev)
  {
    if(ev.getButton() == 1 && z == 1)
    {
      x2 = ev.getX();
      y2 = ev.getY();
      z++;
      if(x1 <= x2)
      {
        x = x1;
      }
      else
      {
        x = x2;
      }
      if(y1 <= y2)
      {
        y = y1;
      }
      else
      {
        y = y2;
      }
      width = Math.abs(x1-x2);
      height = Math.abs(y1-y2);
      
      zgif.setSize(70, 40);
      zgif.setLocation(0, 0);
      //AWTUtilities.setWindowOpacity(zgif, 1f);//Sets the opacity when startButton is added, size/location changes
      zgif.add(startButton);
    }
  }
  
  public void mouseClicked(java.awt.event.MouseEvent ev){}
  public void mouseEntered(java.awt.event.MouseEvent ev){}
  public void mouseExited(java.awt.event.MouseEvent ev){}
  
  public static void main(String args[])
  {
    System.out.println("Start");
    zgif = new zGif();
    
    while(true)
    {
      System.out.println(z);
      if(z == 3)
      {
        System.out.println(width + "," + height);
        if(args.length >= 1)
          GifSequenceWriter.saveName = args[0];
        if(args.length >= 2)
          GifSequenceWriter.bool = Boolean.parseBoolean(args[1]);
        //if(args.length >= 3)
        //  GifSequenceWriter.savePath = args[2];//Custom savePath
        if(width == 0 || height == 0)
          Capture.record(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        else
          Capture.record(new Rectangle(x,y,width,height));
        GifSequenceWriter.create(Capture.images);
        System.out.println("Finished");
        for(int i = 0; i < Capture.images.length; i++)
        {
          Capture.images[i].delete();
        }
        return;
      }
    }
  }
}