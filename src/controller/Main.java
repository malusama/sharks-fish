package controller;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) throws IOException{

		test1();
		
	}



//����һ���µĴ���

public static void test1(){

	JFrame f=new JFrame("��һ��Swing����");//ʵ�����������

	f.setSize(230,80);//���ô����С

	f.setBackground(Color.WHITE);//���ô���ı�����ɫ

	f.setLocation(1000,600);//���ô������ʾλ��

	f.setVisible(true);//���齨��ʾ

}
}