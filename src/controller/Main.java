package controller;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) throws IOException{

		windows();
		
	}

	public static void windows(){

		JFrame f=new JFrame("��һ��Swing����");//ʵ�����������

		f.setSize(500,500);//���ô����С
		
		f.setBackground(Color.WHITE);//���ô���ı�����ɫ

		f.setLocation(50,50);//���ô������ʾλ��

		f.setVisible(true);//���齨��ʾ

	}
}