package controller;
import javax.swing.*;
import java.awt.*;

class DrawWindows extends JFrame{
	
	DrawWindows(){
		
		this.setTitle("Sharks And Fish"); //���ñ���
	    this.setSize(500, 500);  // ���ô��ڴ�С
	     //������ʲô�ģ�
	   // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //������ʲô�ģ�
	      //����Ϊ��ʾ
	    
	    //Graphics graphics = this.getGraphics();
	    
	    //paint(graphics);
	    
	    
	    this.setVisible(true);
	    
	    
	}
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 200, 200);
		
	}
	public void paint(Graphics g, SharksAndFish object){
		
		
		
	}
	 
}
class SharksAndFish{
	int width;
	int height;
	SharksAndFish(int width, int height){
		this.width = width;
		this.height = height;
	}
}
public class Main extends JPanel {
	   public static void main(String[] a) {
		   DrawWindows MainWindows = new DrawWindows();
		   //Frame frame = new Frame("Sharks and Fish");
		   //frame.setSize(500, 500);
		  // frame.setVisible(true);
	   }
	   public void paint(Graphics g,SharksAndFish sea) {
		   g.setColor(Color.white);
		   for(int i = 0;i < sea.height;i++)
		   {
			   for(int j = 0;j < sea.width;j++){
				   
				   g.fillRect (0, 0, 20, 20);
				   
			   }
			   
		   }
		   
		   
	   }
}

