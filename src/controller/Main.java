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
	private Node head;	 //	ֻ��¼��ǰ�е�ͷ�ڵ�
	private Node tail;	 //	ֻ��¼��ǰ�е�β�ڵ�
	int type = -1; 		 //	��¼��ǰ�������������
	Node yy[];     		 // ��̬���飬����ÿһ�������ͷ�ڵ�
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
				if(ocean.ocean[i][j][0] == type){					//�����ͬ���͵�
					if(ocean.ocean[i][j][0] == SHARK){				//������������Ӽ���ֵ
						if(ocean.ocean[i][j][2] == tail.StarveTime){	//�������ֵ��ͬ �����ӽڵ������
							setNodeNum(tail);							//���ӽڵ������
						}else{											//�����ͬ
							addNode(ocean.ocean[i][j][0], 1, ocean.ocean[i][j][2]); //����һ���ڵ�
						}
							//
						//setNodeStarveTime(tail,ocean.ocean[i][j][2]);// ���ü���ֵ 
					}else{
						setNodeNum(tail);
					}							
				}else{														//�����ͬ���;ʹ���һ���µĽڵ��¼
					addNode(ocean.ocean[i][j][0], 1, ocean.ocean[i][j][2]);  //��������ֱ������ͣ�����������ֵ
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
			j = 0;	// J Ϊ��¼��ǰһ���Ѿ�����ĸ����� 
			while(Node != null){
				for(int k = 0;k < Node.num; k++){		//��һ�еĽڵ㸳ֵ������
					decode[i][j + k][0] = Node.type;
					//decode[i][j + k][2] = Node.StarveTime;
					
				}
				j += Node.num;
				Node = Node.next;
			}
		}
		return decode;
		
	}
	void setNodeStarveTime(Node Node, int StarveTime){		//���ýڵ�ļ���ֵ
		Node.StarveTime = StarveTime;
	}
	void setNodeNum(Node Node){								//���ýڵ��¼������
		Node.num++;
	}
	void addNode(int  type, int num, int StarveTime){		//��ӽڵ㣬ά��βָ��
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
		ocean = new int[height][width][3]; // ���һ���� 0 �ǵ�ǰ״̬ 1����һ��״̬ 2���漢����
		for(int i = 0;i < height; i++){
			for(int j = 0; j < width; j++){
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
						
						if(ocean[i][j][2] != 1){ 	//��������Ȳ�Ϊ0
							if(sharkcount == EMPTY){	//�������������㶼Ϊ�� ���ƶ�
								int random = (int) (Math.random() * 8);
								int x = 0,y = 0;
								switch(random){
								case 0:
									x=(i-1+OceanHeight)%OceanHeight;  //�����ƶ�
									y=(j-1+OceanWhdth)%OceanWhdth;
									ocean[x][y][1]=ocean[i][j][0];
			                    	ocean[x][y][2]=--ocean[i][j][2];
			                    	break;
								case 1:
				                    	x=i;
				                    	y=(j-1+OceanWhdth)%OceanWhdth;   //�����ƶ�
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                case 2:
				                    	x=(i+1+OceanHeight)%OceanHeight;   //�����ƶ�
				                    	y=(j-1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 3:
				                    	x=(i-1+OceanHeight)%OceanHeight;    //�����ƶ�
				                    	y=j;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 4:
				                    	x=(i+1+OceanHeight)%OceanHeight;    //�����ƶ�
				                    	y=j;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 5:
				                    	x=(i-1+OceanHeight)%OceanHeight;   //�����ƶ�
				                    	y=(j+1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 6:
				                    	x=i;
				                    	y=(j+1+OceanWhdth)%OceanWhdth;      //�����ƶ�
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
				                    case 7:
				                    	x=(i+1+OceanHeight)%OceanHeight;     //�����ƶ�
				                    	y=(j+1+OceanWhdth)%OceanWhdth;
				                    	ocean[x][y][1]=ocean[i][j][0];
				                    	ocean[x][y][2]=--ocean[i][j][2];
				                    	break;
								}
								//System.out.println("old x and y is: " + i + "  " + j);
								//System.out.println("new x and y is: " + x + "  " + y);
							}else{
								ocean[i][j][2]--;		//�����ȼ�1
								ocean[i][j][1] = SHARK;
							}
							
							
							
							
							//ocean[i][j][2]--;		//�����ȼ�1
							//ocean[i][j][1] = SHARK;
						}else{					
							ocean[i][j][1] = EMPTY;	//����Ԫ��Ϊ0������������
						}
					}
				}
				if(ocean[i][j][0] == FISH){							//�������ִ�����²���
					if( sharkcount == EMPTY){	//���ھ�Ϊ��ʱ
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
				if(ocean[i][j][0] == EMPTY && ocean[i][j][1] == EMPTY){					//����Ԫ��Ϊ��ʱ
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
	 void copytoarr1(int x, int y, int newx, int newy, int starvetime){
		 ocean[newx][newy][1] = ocean[x][y][0];
		 ocean[newx][newy][2] = starvetime;
	 }
	 int aroundinfo(int x, int y, int option){ 
		// option 1 ����������������������� 0�����������
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
			return -1;  //����
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
	   static void printarr(int arr[][][]){
		   for(int i = 0; i < OceanWhdth; i++){
			   for(int j = 0;j < OceanHeight;j++){
				   System.out.print(arr[i][j][0] + "  ");    //�������
			   }
			   System.out.println("\n");
		   }
		   System.out.println("������������������������������������������������");
		   
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
					  g.drawString(String.valueOf(object.ocean[j][i][2]), ((i * cell)), j * cell + cell); //������ļ����Ȼ��ڸ�����
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

