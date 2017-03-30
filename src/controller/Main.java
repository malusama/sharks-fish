package controller;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) throws IOException{

		test1();
		
	}



//创建一个新的窗体

public static void test1(){

	JFrame f=new JFrame("第一个Swing窗口");//实例化窗体对象

	f.setSize(230,80);//设置窗体大小

	f.setBackground(Color.WHITE);//设置窗体的背景颜色

	f.setLocation(1000,600);//设置窗体的显示位置

	f.setVisible(true);//让组建显示

}
}