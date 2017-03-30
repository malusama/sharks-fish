package controller;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) throws IOException{

		windows();
		
	}

	public static void windows(){

		JFrame f=new JFrame("第一个Swing窗口");//实例化窗体对象

		f.setSize(500,500);//设置窗体大小
		
		f.setBackground(Color.WHITE);//设置窗体的背景颜色

		f.setLocation(50,50);//设置窗体的显示位置

		f.setVisible(true);//让组建显示

	}
}