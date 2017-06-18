package tett;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


interface Constants {
    //private Constants() {}
    public final static int EMPTY = 0;
	public final static int SHARK = 1;
	public final static int FISH = 2;  
	public final static int StarveTime = 3;
	public final static int OceanWhdth = 30;	// Y ,J
	public final static int OceanHeight = 30;	// X, I
	public final static double SharkAndFishRatio = 0.25;
}
class Node{
	int  type;
	int num;
	int StarveTime;
	Node next;
	Node previous;
	Node(){
		
	}
	Node(int  type, int num, int StarveTime){
		this.type = type;
		this.num = num;
		this.StarveTime = StarveTime;
		this.next = null;
	}
	
}
class seacode extends Node implements Constants{
	private Node head;	 //	只记录当前行的头节点
	private Node tail;	 //	只记录当前行的尾节点
	int type = -1; 		 //	记录当前遍历生物的类型
	Node yy[];     		 // 动态数组，储存每一行链表的头节点
	seacode(){
		head = tail = null;
	}
	seacode(sea ocean){
		yy = new Node[OceanHeight];
		for(int i = 0; i < OceanHeight; i++ ){
			head = tail = new Node(ocean.ocean[i][0][0], 1, ocean.ocean[i][0][2]);
			yy[i] = head;	
			type = ocean.ocean[i][0][0];
			for(int j = 1; j < OceanWhdth; j++){
				if(ocean.ocean[i][j][0] == type){					//如果相同类型的
					if(ocean.ocean[i][j][0] == SHARK){				//如果是鲨鱼就添加饥饿值
						if(ocean.ocean[i][j][2] == tail.StarveTime){	//如果饥饿值相同 就增加节点的数量
							setNodeNum(tail);							//增加节点的数量
						}else{											//如果不同
							addNode(ocean.ocean[i][j][0], 1, ocean.ocean[i][j][2]); //增加一个节点
						}
							//
						//setNodeStarveTime(tail,ocean.ocean[i][j][2]);// 设置饥饿值 
					}else{
						setNodeNum(tail);
					}							
				}else{														//如果不同类型就创建一个新的节点记录
					addNode(ocean.ocean[i][j][0], 1, ocean.ocean[i][j][2]);  //参数含义分别是类型，数量，饥饿值
					type = ocean.ocean[i][j][0];
					
				}
				
			}
		}
	}
	public int[][][] decode(seacode code){
		
		int decode[][][] = new int[OceanWhdth][OceanHeight][3];
		Node Node;
		int j;
		for(int i = 0; i < OceanWhdth; i++){
			Node = code.yy[i];
			j = 0;	// J 为记录当前一行已经解码的个数， 
			while(Node != null){
				for(int k = 0;k < Node.num; k++){		//这一行的节点赋值给数组
					decode[i][j + k][0] = Node.type;
					//decode[i][j + k][2] = Node.StarveTime;
					
				}
				j += Node.num;
				Node = Node.next;
			}
		}
		return decode;
		
	}
	void setNodeStarveTime(Node Node, int StarveTime){		//设置节点的饥饿值
		Node.StarveTime = StarveTime;
	}
	void setNodeNum(Node Node){								//设置节点记录的数量
		Node.num++;
	}
	void addNode(int  type, int num, int StarveTime){		//添加节点，维护尾指针
		tail.next = new Node(type,num,StarveTime);
		tail = tail.next;
		tail.next = null;
	}
	
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
		ocean = new int[height][width][3]; // 最后一个数 0 是当前状态 1是下一个状态 2储存饥饿数
		for(int i = 0;i < height; i++){
			for(int j = 0; j < width; j++){
				double d = (Math.random() * 4);				//产生0~4区间的随机数
				//System.out.println(d); 
				if(d > 1){									//这里鲨鱼与 鱼只占一个单位
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
						
						if(ocean[i][j][2] != 1){ 	//如果饥饿度不为0
							if(sharkcount == EMPTY){	//如果四周鱼和鲨鱼都为空 则移动
								int random = (int) (Math.random() * 8);
								int x = 0,y = 0;
								switch(random){
								case 0:
									x=(i-1+OceanHeight)%OceanHeight;  //左上移动
									y=(j-1+OceanWhdth)%OceanWhdth;
									ocean[x][y][1]=ocean[i][j][0];
			                    	ocean[x][y][2]=--ocean[i][j][2];
			                    	break;
								case 1:
				                    	x=i;
				                    	y=(j-1+OceanWhdth)%OceanWhdth;   //向上移动
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                case 2:
				                    	x=(i+1+OceanHeight)%OceanHeight;   //右上移动
				                    	y=(j-1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 3:
				                    	x=(i-1+OceanHeight)%OceanHeight;    //向左移动
				                    	y=j;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 4:
				                    	x=(i+1+OceanHeight)%OceanHeight;    //向右移动
				                    	y=j;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 5:
				                    	x=(i-1+OceanHeight)%OceanHeight;   //左下移动
				                    	y=(j+1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 6:
				                    	x=i;
				                    	y=(j+1+OceanWhdth)%OceanWhdth;      //向下移动
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 7:
				                    	x=(i+1+OceanHeight)%OceanHeight;     //右下移动
				                    	y=(j+1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
								}
								//System.out.println("old x and y is: " + i + "  " + j);
								//System.out.println("new x and y is: " + x + "  " + y);
							}else{
								ocean[i][j][2]--;		//饥饿度减1
								ocean[i][j][1] = SHARK;
							}
							
							
							
							
							//ocean[i][j][2]--;		//饥饿度减1
							//ocean[i][j][1] = SHARK;
						}else{					
							ocean[i][j][1] = EMPTY;	//否则单元格为0，鲨鱼死掉了
						}
					}
				}
				if(ocean[i][j][0] == FISH){							//如果是鱼执行以下操作
					if( sharkcount == EMPTY){	//当邻居为空时
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
				if(ocean[i][j][0] == EMPTY && ocean[i][j][1] == EMPTY){					//当单元格为空时
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
	 void copytoarr1(int x, int y, int newx, int newy, int starvetime){
		 ocean[newx][newy][1] = ocean[x][y][0];
		 ocean[newx][newy][2] = starvetime;
	 }
	 int aroundinfo(int x, int y, int option){ 
		// option 1 返回坐标四周鲨鱼的数量， 0返回鱼的数量
		int sharkcount = 0;
		int fishcount = 0;
		
		
		int down_x = (x + OceanHeight + 1) %  OceanHeight;
		int up_x = (x + OceanHeight - 1) % OceanHeight;
		
		int down_y = (y + OceanWhdth + 1) % OceanWhdth;
		int up_y = (y + OceanWhdth - 1) % OceanWhdth;
		
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
				if(xx[i] == x && yy[j] == y){
					continue;
				}
				if(ocean[ xx[i] ][ yy[j] ][0] == SHARK){
					sharkcount++;
				}
				if(ocean[ xx[i] ][ yy[j] ][0] == FISH){
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
	 public void addfish(){
		 int height;
		   int whdth;
		   while(true){
			   //double r=Math.random()*1000;
			   height=(int) (Math.random() * OceanHeight);
			   whdth= (int)( Math.random() * OceanWhdth);
			   if(ocean[height][whdth][0]==EMPTY)
				   break;	   
		   }
		   ocean[height][whdth][0]=FISH;
		   System.out.print(" ");
	 }
	 public void addshark(){
		 int height;
		   int whdth;
		   while(true){
			   //double r=Math.random()*1000;
			   height=(int)( Math.random() * OceanHeight);
			   whdth= (int) (Math.random() * OceanWhdth);
			   if(ocean[height][whdth][0]==EMPTY)
				   break;	   
		   }
		   ocean[height][whdth][0]=SHARK;
		   ocean[height][whdth][2]=StarveTime;
		   System.out.print(" ");
	 }
	 
}
@SuppressWarnings("serial")
public class Main extends JFrame implements Constants, ActionListener  {
	   public Graphics graphics = null;
	   JButton jbNextTime = new JButton("NextTime"); 
	   JButton jbaddFish=new JButton("addFish"); 
	   JButton jbAddShark=new JButton("AddShark"); 
	   JButton jbMaxSpeed=new JButton("MaxSpeed"); 
	   boolean flag = false;
	   boolean con = false;
	   sea DarkCentury = new sea(OceanWhdth, OceanHeight, StarveTime);
	   seacode code = new seacode(DarkCentury);
	   public static void main(String[] a) throws InterruptedException {
		   
		   
		   /*
		   JFrame frame = new JFrame("Sharks and Fish");
		   frame.setLocation(10, 10);
		   frame.setSize(OceanWhdth * 10 + 100, OceanHeight * 10 + 40);
		   //frame.setLayout(new GridLayout(1,2));
		   
		   
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		   JPanel panel = new JPanel();
		   frame.add(panel);
		   
		   JPanel button = new JPanel();
		   button.setSize(100,40);
		   frame.add(button);
		   
		   panel.setBounds(0, 0, OceanWhdth * 10, OceanHeight * 10);
		   //panel.setBackground(Color.WHITE);
		   
		   
		   
		   
		   frame.setVisible(true);
		   Graphics graphics = panel.getGraphics();   
		   
		   */
		    
		   Main W = new Main();
		   //Graphics graphics =w.panel.getGraphics();  
		   
		   Thread.sleep(500);
		   DrawSea(W.graphics, W.DarkCentury);
		   //Thread.sleep(2000);
		   
		   while(true){
			   System.out.print("");	
			   while(W.flag){
				   Thread.sleep(1000);  
				   W.DarkCentury.timeStep();
				   DrawSea(W.graphics, W.DarkCentury);
				   
				   printseacode(W.code);
				   printarr(W.code.decode(W.code));
				   
				   
				   W.code = new seacode(W.DarkCentury);
				   
				   if(W.con){
					   
					   W.flag = false;
					   W.con = false;

				   }
				   //W.flag = false;
				   //System.out.print(" second while");
			   }
			   
			   //System.out.print(" first while");	   
		   }
	   	}

	   Main(){
		   JFrame frame = new JFrame("Sharks and Fish");
		   frame.setLocation(10, 10);
		   frame.setSize(OceanWhdth * 10 + 120, OceanHeight * 10 + 40);
		   frame.setLayout(null);
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   JPanel panel = new JPanel();
		   frame.add(panel);
		   JPanel button = new JPanel();
		   button.setSize(120,40);
		   button.setLayout(new GridLayout(4, 1, 10, 5));
		   frame.add(button);
		   jbNextTime.addActionListener(this);
		   jbaddFish.addActionListener(this);
		   jbAddShark.addActionListener(this);
		   jbMaxSpeed.addActionListener(this);
		   button.add(jbNextTime);
		   button.add(jbaddFish);
		   button.add(jbAddShark);
		   button.add(jbMaxSpeed);
		   button.setBounds(OceanWhdth * 10, 0, 100, 140);
		   panel.setBounds(0, 0, OceanWhdth * 10, OceanHeight * 10);
		   frame.setVisible(true);
		   graphics = panel.getGraphics(); 
	   }

	   public void actionPerformed(ActionEvent e) { 
		   if (e.getSource() == jbNextTime) {
			   
	          this.flag = true;
	          this.con = true;
	          //this.DarkCentury.timeStep();
			  //DrawSea(graphics, this.DarkCentury);
			  //printseacode(code);
			  //printarr(code.decode(code));
			  //System.out.print("test");
	         }
		   if (e.getSource() == jbaddFish) {
	            this.DarkCentury.addfish();
	            DrawSea(graphics, this.DarkCentury);
			   //System.out.print("test");
	         }
		   if (e.getSource() == jbAddShark) {
			   this.DarkCentury.addshark();
			   DrawSea(graphics, this.DarkCentury);
			   //System.out.print("test");
	         }
		   if (e.getSource() == jbMaxSpeed) {
	           this.flag = !this.flag;
			   //System.out.print( this.flag);
	         }
		   
	   	} 
	   static void printseacode(seacode code){
		   Node tem;
		   for(int i = 0; i < OceanHeight; i++){
			   tem = code.yy[i];
			   while(tem != null){
				   System.out.print(decode(tem.type, tem.type, tem.StarveTime) + tem.num); 
				   System.out.print("   ");    //给个间隔
				   tem = tem.next;
			   }
			   System.out.println("\n");
		   }
	   }
	   static String decode(int type, int x, int StarveTime){	//tpye是类型， x是转换的值， StarveTime是饥饿度， 当类型是鲨鱼的时候才打印出来
		  if(type == SHARK){
			  if(x == 0){
				   return ".";
			   }
			   if(x == 1){
				   return "S" + StarveTime + "，";
			   }
			   if(x == 2){
				   return "F";
			   }
		  }else{
			  if(x == 0){
				   return ".";
			   }
			   if(x == 1){
				   return "S";
			   }
			   if(x == 2){
				   return "F";
			   }
		  }
		   
		return null;
	   }
	   static void printarr(int arr[][][]){
		   for(int i = 0; i < OceanWhdth; i++){
			   for(int j = 0;j < OceanHeight;j++){
				   System.out.print(arr[i][j][0] + "  ");    //给个间隔
			   }
			   System.out.println("\n");
		   }
		   System.out.println("————————————————————————");
		   
	   }
	   private static void DrawSea(Graphics g, sea object){
		  //g.setColor(Color.red);
		  int cell = 10;
		  for(int i = 0; i < object.width; i++){
			  for(int j = 0; j < object.height; j++){
				  if(object.ocean[j][i][0] == EMPTY){
					  g.setColor(Color.WHITE);
					  g.fillRect(i * cell, j * cell, cell, cell);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * cell, j * cell, cell, cell);
					 
				  }
				  if(object.ocean[j][i][0] == SHARK){
					  g.setColor(Color.RED);
					  g.fillRect(i * cell, j * cell, cell, cell);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * cell, j * cell, cell, cell);
					  g.drawString(String.valueOf(object.ocean[j][i][2]), ((i * cell)), j * cell + cell); //把鲨鱼的饥饿度画在格子里
				  }
				  if(object.ocean[j][i][0] == FISH){
					  g.setColor(Color.GREEN);
					  g.fillRect(i * cell, j * cell, cell, cell);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * cell, j * cell, cell, cell);
				  }
				  
			  }
			  
			  
		  }
			
		}
	  
}

