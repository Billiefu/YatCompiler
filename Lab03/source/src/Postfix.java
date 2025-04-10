import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import parser.Parser;

/**
 * 后缀表达式转换程序入口类（Postfix）
 * 
 * <p>
 * 该类作为程序的入口，负责接收用户输入的中缀表达式并将其转换为后缀表达式（逆波兰表达式）。<br>
 * 用户可以输入多次中缀表达式，直到输入“exit”命令退出程序。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月10日
 * @lastUpdated 2025年4月10日
 * 
 */
public class Postfix {

    /**
     * 程序入口，负责接收输入并调用解析器进行转换
     * 
     * <p>
     * 该方法通过标准输入获取用户的中缀表达式，并使用Parser类转换为后缀表达式。<br>
     * 当用户输入“exit”时，程序结束。
     * </p>
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
    	
        System.out.println("Input an infix expression and output its postfix notation:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // 创建BufferedReader读取输入

        // 程序主循环，持续接收输入
        while (true) {
            try {
                System.out.print("> "); // 提示用户输入
                String line = reader.readLine(); // 读取一行输入

                // 如果输入为空或为“exit”，则退出程序
                if (line.length() == 0 || line.equalsIgnoreCase("exit")) break;

                // 缓冲输出：临时重定向System.out
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(); // 创建缓冲输出流
                PrintStream originalOut = System.out; // 保存原始System.out（标准输出）
                System.setOut(new PrintStream(buffer)); // 重定向System.out到缓冲输出流

                // 使用输入的中缀表达式创建Parser实例并调用expr方法进行转换
                ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes()); // 创建ByteArrayInputStream
                Parser parser = new Parser(input); // 创建Parser实例
                // RecursionParser parser = new RecursionParser(input); // 创建RecursionParser实例
                parser.expr(); // 执行表达式转换

                System.setOut(originalOut); // 恢复标准输出到原始流

                // 等待50毫秒，确保错误输出完成
                try {
                    Thread.sleep(50); // 等待一段时间
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                }

                // 输出转换后的后缀表达式
                System.out.println(buffer.toString().trim()); // 输出结果并去除多余的空格
                System.out.println(); // 输出空行，分隔不同的输出

            } catch (IOException e) {
                e.printStackTrace(); // 如果发生IO异常，输出堆栈信息
            }
        }

        // 程序结束后输出
        System.out.println("End of program.");
    }
    
}
