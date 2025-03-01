/*
 * 类名：Txtpro
 * 
 * 作用：进行数据的输入与输出
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Txtpro {
	
	/* 该函数用于将当前的个人所得税起征点及税率表存储进存储文件当中 */
	public static void Output(String threshold, String textable) throws IOException {
		// 加载数据存储文件
		// String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
		String filename = "src" + File.separator + "data.txt";
		File file = new File(filename);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		// 将数据写入存储文件当中
		Writer output = new FileWriter(file);
		output.write(threshold + "\r\n");
		output.write(textable);
		output.close();
		
		return ;
	}
	
	/* 该函数用于将当前的个人所得税起征点及税率表从存储文件读入到程序当中 */
	public static String Input() throws IOException {
		// 加载数据存储文件
		// String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
		String filename = "src" + File.separator + "data.txt";
		File file = new File(filename);
		if(!file.getParentFile().exists()) {
			Output("1000", "11000000000, 0\r\n");
			return "1000\r\n11000000000, 0\r\n";
		}
		
		// 将数据从存储文件当中读入程序当中
		Reader input = new FileReader(file);
		char data[] = new char[10000000];
		int len = input.read(data);
		String str = new String(data, 0, len);
		input.close();
		
		return str;
	}
	
}
