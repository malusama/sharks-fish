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
	public final static int OceanWhdth = 10;
	public final static int OceanHeight = 10;
	public final static double SharkAndFishRatio = 0.25;
}
class node{
	int  type;
	int num;
	int StarveTime;
	node next;
	node previous;
	node(){
		
	}
	node(int  type, int num, int StarveTime){
		this.type = type;
		this.num = num;
		this.StarveTime = StarveTime;
	}
	
}
class seacode extends node implements Constants{
	private node head;	 //	ֻ��¼��ǰ�е�ͷ�ڵ�
	private node tail;	 //	ֻ��¼��ǰ�е�β�ڵ�
	int type = -1; 		 //	��¼��ǰ�������������
	node yy[];     		 // ��̬���飬����ÿһ�������ͷ�ڵ�
	seacode(){
		head = tail = null;
	}
	seacode(sea ocean, int x, int y){
		yy = new node[x];
		for(int i = 0; i < x; i++ ){
			head = tail = new node(ocean.ocean[i][0][0], 1, ocean.ocean[i][0][2]);
			yy[i] = head;	
			type = ocean.ocean[i][0][0];
			for(int j = 1; j < y; j++){
				if(ocean.ocean[i][j][0] == type){					//�����ͬ���͵�
					if(ocean.ocean[i][j][0] == SHARK){				//������������Ӽ���ֵ
						setNodeNum(tail);
						setNodeStarveTime(tail,ocean.ocean[i][j][2]);
					}
					setNodeNum(tail);								
				}else{												//�����ͬ���;ʹ���һ���µĽڵ��¼
					addnode(ocean.ocean[i][j][0], 1, 0); 
					type = ocean.ocean[i][j][0];
				}
				
			}
		}
	}
	void setNodeStarveTime(node node, int StarveTime){
		node.StarveTime = StarveTime;
	}
	void setNodeNum(node node){
		node.num++;
	}
	void addnode(int  type, int num, int StarveTime){
		tail.next = new node(type,num,StarveTime);
		tail = tail.next;
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
		ocean = new int[width][height][3]; // ���һ���� 0 �ǵ�ǰ״̬ 1����һ��״̬ 2���漢����
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height; j++){
				double d = (Math.random() * 4);				//����0~4����������
				//System.out.println(d); 
				if(d > 1){									//���������� ��ֻռһ����λ
					ocean[i][j][0] = EMPTY;
				}
				if(d < SharkAndFishRatio){					//���dС�� SharkAndFishRatio ����Ԫ��Ϊ����
					ocean[i][j][0] = SHARK;
					ocean[i][j][2] = StarveTime;
				}
				if(d > SharkAndFishRatio && d <= 1){		//d��  SharkAndFishRatio ~ 1������Ϊ�� 
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
				if(ocean[i][j][0] == SHARK){		//���������ִ�����²���
					if(fishcount > 0){				//ͬʱ���ھ����㣬��ô���������һ��ʱ�䲽���ڳԵ����ǣ�
						ocean[i][j][1] = SHARK; 	//��ʱ�䲽������ʱ����Ȼ���ڵ�ǰ������С�
					}else{							//�ھ�û����ʱִ�����²���
						if(ocean[i][j][2] != 0){ 	//��������Ȳ�Ϊ0
							ocean[i][j][2]--;		//�����ȼ�1
							ocean[i][j][1] = SHARK;
						}else{					
							ocean[i][j][1] = EMPTY;	//����Ԫ��Ϊ0������������
						}
					}
				}
				if(ocean[i][j][0] == FISH){							//�������ִ�����²���
					if(fishcount == EMPTY && sharkcount == EMPTY){	//���ھ�Ϊ��ʱ
						ocean[i][j][1] = FISH;
					}
					if(sharkcount == 1){							//���ھ�ֻ��һ������ʱ
						ocean[i][j][1] = EMPTY;						//�㱻�Ե�����Ԫ��Ϊ��
					}
					if(sharkcount > 1){
						ocean[i][j][1] = SHARK;						//����Ԫ����Χ��������������ʱ��Ԫ�����һ����������
						ocean[i][j][2] = StarveTime;				//�������㼢����Ϊ����4
					}
				}
				if(ocean[i][j][0] == EMPTY){					//����Ԫ��Ϊ��ʱ
					if(fishcount < 2){							//����Ԫ����Χ����������ʱ
						ocean[i][j][1] = EMPTY;					//��Ԫ�񱣳ֲ���
					}
					if(fishcount >= 2 && sharkcount <= 1){		//����Ԫ����Χ�����2������Ϊ1ʱ
						ocean[i][j][1] = FISH;					//��Ԫ�����һ��������
					}
					if(fishcount >=2 && sharkcount >= 2){		//����Ԫ����Χ����2����2������ʱ
						ocean[i][j][1] = SHARK;					//�����µ�����
						ocean[i][j][2] = StarveTime;			//�����㼢��Ϊ4����ʹû�Զ���
					}
				}
				
				
			}
		}
		for(int i = 0; i < OceanHeight; i++){					//���������鿽������ʾ������
			for(int j = 0; j < OceanWhdth; j++){
				ocean[i][j][0] = ocean[i][j][1];
			}
		}
		
	}
	 int aroundinfo(int x, int y, int option){ 
		// option 1 ����������������������� 0�����������
		int sharkcount = 0;
		int fishcount = 0;
		
		
		int up_x = (x + OceanHeight + 1) %  OceanHeight;
		int down_x = (x + OceanHeight - 1) % OceanHeight;
		int up_y = (y + OceanWhdth + 1) % OceanWhdth;
		int down_y = (y + OceanWhdth - 1) % OceanWhdth;
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
			return -1;  //����
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
		   JPanel panel = new JPanel();
		   frame.add(panel);
		   panel.setBounds(0, 0, 500, 500);
		   //panel.setBackground(Color.WHITE);
		   frame.setVisible(true);
		   Graphics graphics = panel.getGraphics();   
		   sea DarkCentury = new sea(OceanWhdth, OceanHeight, StarveTime);
		   
		   while(true){
			   seacode code = new seacode(DarkCentury, OceanWhdth, OceanHeight);
			   Thread.sleep(1000);  
			   DrawSea(graphics, DarkCentury);
			   
			   printseacode(code);
			   DarkCentury.timeStep();
		   }
		   


	   }
	   static void windows(){
		   
	   }
	   
	   static void printseacode(seacode code){
		   node tem;
		   for(int i = 0; i < OceanHeight; i++){
			   tem = code.yy[i];
			   while(tem != null){
				   System.out.print(decode(tem.type, tem.type, tem.StarveTime) + tem.num); 
				   System.out.print("   ");    //�������
				   tem = tem.next;
			   }
			   System.out.println("\n");
		   }
	   }
	   static String decode(int type, int x, int StarveTime){	//tpye�����ͣ� x��ת����ֵ�� StarveTime�Ǽ����ȣ� �������������ʱ��Ŵ�ӡ����
		  if(type == SHARK){
			  if(x == 0){
				   return ".";
			   }
			   if(x == 1){
				   return "S" + StarveTime + "��";
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
	   private static void DrawSea(Graphics g, sea object){
		  //g.setColor(Color.red);
		  for(int i = 0; i < object.height; i++){
			  for(int j = 0; j < object.width; j++){
				  if(object.ocean[j][i][0] == EMPTY){
					  g.setColor(Color.WHITE);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[j][i][0] == SHARK){
					  g.setColor(Color.RED);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  if(object.ocean[j][i][0] == FISH){
					  g.setColor(Color.GREEN);
					  g.fillRect(i * 10, j * 10, 10, 10);
					  g.setColor(Color.BLACK);
					  g.drawRect(i * 10, j * 10, 10, 10);
				  }
				  
			  }
			  
			  
		  }
			
		}
	  
}

