package controller;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

interface Constants {
    //private Constants() {}
    public final static int EMPTY = 0;
	public final static int SHARK = 1;
	public final static int FISH = 2;  
	public final static int StarveTime = 3;
	public final static int OceanWhdth = 25;
	public final static int OceanHeight = 25;
	public final static double SharkAndFishRatio = 0.25;
}

class sea implements Constants{
	  
	int width;
	int height;
    int starveTime;
    int ocean[][][];
	sea(int width, int height,int starveTime){
		this.width = width;
		this.height = height;
		this.starveTime = starveTime;
		ocean = new int[width][height][3]; // 最后一个数 0 是当前状态 1是下一个状态 2储存饥饿数
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height; j++){
				double d = (Math.random() * 4);				//产生0~2区间的随机数
				//System.out.println(d); 
				if(d > 1){									//这里海洋里空的单元格占一半
					ocean[i][j][0] = EMPTY;
				}
				if(d < SharkAndFishRatio){					//如果d小于 SharkAndFishRatio 比则单元格为鲨鱼
					ocean[i][j][0] = SHARK;
					ocean[i][j][2] = StarveTime;
				}
				if(d > SharkAndFishRatio && d <= 1){		//d在  SharkAndFishRatio ~ 1区间则为鱼 
					ocean[i][j][0] = FISH;
				}
			}
		}
	}
	 void timeStep(){
		int sharkcount = 0;
		int fishcount = 0;
		
		for(int i = 0; i < OceanHeight; i++){
			for(int j = 0; j < OceanWhdth; j++){
				sharkcount = aroundinfo(i, j, 1);
				fishcount = aroundinfo(i, j, 0);
				if(ocean[i][j][0] == SHARK){		//如果是鲨鱼执行以下操作
					if(fishcount > 0){				//同时其邻居有鱼，那么鲨鱼可以在一个时间步长内吃掉它们，
						ocean[i][j][1] = SHARK; 	//在时间步长结束时刻仍然留在当前海洋块中。
					}else{							//邻居没有鱼时执行以下操作
						if(ocean[i][j][2] != 0){ 	//如果饥饿度不为0
							ocean[i][j][2]--;		//饥饿度减1
							ocean[i][j][1] = SHARK;
						}else{					
							ocean[i][j][1] = EMPTY;	//否则单元格为0，鲨鱼死掉了
						}
					}
				}
				if(ocean[i][j][0] == FISH){							//如果是鱼执行以下操作
					if(fishcount == EMPTY && sharkcount == EMPTY){	//当邻居为空时
						ocean[i][j][1] = FISH;
					}
					if(sharkcount == 1){							//当邻居只有一条鲨鱼时
						ocean[i][j][1] = EMPTY;						//鱼被吃掉，单元格为空
					}
					if(sharkcount > 1){
						ocean[i][j][1] = SHARK;						//当单元格周围有两条以上鲨鱼时单元格出现一条新生鲨鱼
						ocean[i][j][2] = StarveTime;				//新生鲨鱼饥饿度为满，4
					}
				}
				if(ocean[i][j][0] == EMPTY){					//当单元格为空时
					if(fishcount < 2){							//当单元格周围少于两条鱼时
						ocean[i][j][1] = EMPTY;					//单元格保持不变
					}
					if(fishcount >= 2 && sharkcount <= 1){		//但单元格周围鱼大于2且鲨鱼为1时
						ocean[i][j][1] = FISH;					//单元格产生一条新生鱼
					}
					if(fishcount >=2 && sharkcount >= 2){		//当单元格周围至少2条鱼2条鲨鱼时
						ocean[i][j][1] = SHARK;					//产生新的鲨鱼
						ocean[i][j][2] = StarveTime;			//新鲨鱼饥饿为4，即使没吃东西
					}
				}
				
				
			}
		}
		for(int i = 0; i < OceanHeight; i++){					//将副本数组拷贝到显示数组里
			for(int j = 0; j < OceanWhdth; j++){
				ocean[i][j][0] = ocean[i][j][1];
			}
		}
		
	}
	 int aroundinfo(int x, int y, int option){ 
		// option 1 返回坐标四周鲨鱼的数量， 0返回鱼的数量
		int sharkcount = 0;
		int fishcount = 0;
		
		
		int up_x = x + OceanHeight + 1 %  OceanHeight;
		int down_x = x + OceanHeight - 1 % OceanHeight;
		int up_y = y + OceanWhdth + 1 % OceanWhdth;
		int down_y = y + OceanWhdth - 1 % OceanWhdth;
		int[] xx = new int[3];
		xx[0] = up_x;
		xx[1] = x;
		xx[2] = down_x;
		int[] yy = new int[3];
		yy[0] = up_y;
		yy[1] = y;
		yy[2] = down_y;
		for(int i = 0;i < 3; i++){
			for(int j = 0;j < 3; j++){
				if(i == x && j == y){
					continue;
				}
				if(ocean[i][j][0] == SHARK){
					sharkcount++;
				}
				if(ocean[i][j][0] == FISH){
					fishcount++;
				}
			}
		}
		
		if(option == 1){
			return sharkcount;
		}
		
		if(option == 0){
			return fishcount;
		}else{
			return -1;  //出错
		}
		
				
	}
}
public class Main extends JFrame implements Constants {
	   public static void main(String[] a) throws InterruptedException {
		   
		   JFrame frame = new JFrame("Sharks and Fish");
		   frame.setLocation(10, 10);
		   frame.setSize(500, 500);
		   frame.setLayout(null);
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		   sea DarkCentury = new sea(OceanWhdth, OceanHeight, StarveTime);
		   JPanel panel = new JPanel();
		   frame.add(panel);
		   panel.setBounds(0, 0, 500, 500);
		   //panel.setBackground(Color.WHITE);
		   frame.setVisible(true);
		   
		   Graphics graphics = panel.getGraphics();
		   
		   if(graphics == null){
			   
			   System.out.println("null"); 
			   
		   }
		   
		   
		   
		   while(true){
			   Thread.sleep(1000);  
			   DrawSea(graphics, DarkCentury);
			   DarkCentury.timeStep();
		   }
		   


	   }
	   
	   
	   private static void DrawSea(Graphics g, sea object){
		  //g.setColor(Color.red);
		  for(int i = 0; i < object.width; i++){
			  for(int j = 0; j < object.height; j++){
				  if(object.ocean[i][j][0] == EMPTY){
					  g.setColor(Color.WHITE);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[i][j][0] == SHARK){
					  g.setColor(Color.RED);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[i][j][0] == FISH){
					  g.setColor(Color.GREEN);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  
			  }
			  
			  
		  }
			
		}
	  
}

