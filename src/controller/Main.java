package controller;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



class sea{
	int width;
	int height;
    int starveTime;
	int ocean[][][];
	sea(int width, int height,int starveTime){
		this.width = width;
		this.height = height;
		this.starveTime = starveTime;
		ocean = new int[width][height][2];
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height; j++){
				int d = (int)(Math.random() * 2 + 0.5);
				//System.out.println(d); 
				ocean[i][j][0] =  d;
				
			}

		}
	}
	
}
public class Main extends JFrame {
	  public final static int EMPTY = 0;
	  public final static int SHARK = 1;
	  public final static int FISH = 2;   
	  public final static int StarveTime = 4;
	  public final static int OceanWhdth = 25;
	  public final static int OceanHeight = 30;
	  
	
	
	   public static void main(String[] a) {
		   
		   JFrame frame = new JFrame("Sharks and Fish");
		   frame.setLocation(10, 10);
		   frame.setSize(500, 500);
		   frame.setLayout(null);
		   sea DarkCentury = new sea(OceanWhdth, OceanHeight, StarveTime);
		   JPanel panel = new JPanel();
		   frame.add(panel);
		   panel.setBounds(10, 10, 465, 440);
		   //panel.setBackground(Color.WHITE);
		   frame.setVisible(true);
		   
		   Graphics graphics = panel.getGraphics();
		   
		   if(graphics == null){
			   
			   System.out.println("null"); 
			   
		   }
		   
		   
		   
		   while(true){
			   DrawSea(graphics, DarkCentury);
		   }
		   


	   }
	   
	   
	   private static void DrawSea(Graphics g, sea object){
		  //g.setColor(Color.red);
		  for(int i = 0; i < object.width; i++){
			  for(int j = 0; j < object.height; j++){
				  if(object.ocean[i][j][0] == EMPTY){
					  g.setColor(Color.WHITE);
					  g.fillRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[i][j][0] == SHARK){
					  g.setColor(Color.RED);
					  g.fillRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[i][j][0] == FISH){
					  g.setColor(Color.GREEN);
					  g.fillRect(i * 10, j * 10, 10, 10);
				  }
				  
			  }
			  
			  
		  }
			
		}
	  
}

