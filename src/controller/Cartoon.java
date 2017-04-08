package controller;
import javax.swing.*;
import java.awt.*;

public class Cartoon
{
	private static final int cellSize = 3;
	private static int i = 25;                             // Default ocean width
	private static int j = 30;                            // Default ocean height
	private static int starveTime = 3;
	private static void drawOcean(Graphics graphics, Sea sea)
	{
		    if (sea != null)
		    {
		      int width = sea.width();
		      int height = sea.height();

		      for (int y = 0; y < height; y++) 
		      {
		        for (int x = 0; x < width; x++)
		        {
		          int contents = sea.cellContents(x, y);
		          if (contents == Sea.SHARK) 
		          {
		            graphics.setColor(Color.red);                   // Draw a red shark
		            graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
		          } 
		          else if (contents == Sea.FISH) 
		          {
		            graphics.setColor(Color.green);                // Draw a green fish
		            graphics.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
		          } 
		          else 
		          {
		            graphics.clearRect(x * cellSize, y * cellSize, cellSize, cellSize);
		          }
		        }
		      }
		    }
	}
	public static void main(String[] argv)
	{
		JFrame frame = new JFrame("Sharks and Fish");
	    frame.setSize(i*cellSize+10,j*cellSize+10);
	    frame.setLocation(100,100);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);
        canvas.setSize(i * cellSize, j * cellSize);
        frame.add(canvas);
        Graphics graphics = canvas.getGraphics();
    }
}
	
	   
	/*   public void paint(Graphics g){
		   g.setColor(Color.black);
		   //g.drawRect(0, 0, 100, 100);
		   g.fillRect(0, 0, 100, 100);
		   
		   
	   }
	   private static void drawsea(Graphics g, sea object){
		  g.setColor(Color.red);
		 // g.drawRect(0, 0, 100, 100);
		  g.fillRect(0, 0, 100, 100);
			
		}*/
	 


