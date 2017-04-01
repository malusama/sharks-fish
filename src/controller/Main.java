package controller;
import javax.swing.*;
import java.awt.*;


class sea{
	int width;
	int height;
	sea(int width, int height){
		this.width = width;
		this.height = height;
	}
	
}
public class Main extends JFrame {
	
	   public static void main(String[] a) {
		   
		   JFrame frame = new JFrame("Sharks and Fish");
		   frame.setSize(500, 500);
		   sea Aa = new sea(100, 100);
		   //JPanel panel = new JPanel();
		  
		   //frame.add(panel);
		   //panel.setBackground(Color.WHITE);
		   
		   frame.setVisible(true);
		   
		   //Graphics graphics = panel.getGraphics();
		   
		  
		   //drawsea(graphics,Aa);


	   }
	   
	   public void paint(Graphics g){
		   g.setColor(Color.black);
		   //g.drawRect(0, 0, 100, 100);
		   g.fillRect(0, 0, 100, 100);
		   
		   
	   }
	   private static void drawsea(Graphics g, sea object){
		  g.setColor(Color.red);
		 // g.drawRect(0, 0, 100, 100);
		  g.fillRect(0, 0, 100, 100);
			
		}
	 
}

