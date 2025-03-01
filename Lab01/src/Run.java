/*
 * 类名：Run
 * 
 * 作用：整个程序的入口
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

import java.io.IOException;

import core.Tax;
import core.Txtpro;
import windows.Home;

public class Run {
	
	public static void main(String args[]) throws IOException {
		// 从存储文件中读取信息入 Tax 类中
		String info = Txtpro.Input();
		Tax tax = Tax.getInstance();
		tax.setdata(info);
		
		// 运行 Home 界面
		Home frame = new Home();
		frame.setVisible(true);
		return ;
	}
	
}
