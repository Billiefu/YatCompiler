package agenda.ui;

import java.util.Arrays;
import java.util.Scanner;

import agenda.ui.command.Command;

/**
 * 命令处理类
 * 
 * <p>
 * 处理用户输入的指令并将其分发至各个不同的类中进行处理。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年03月26日
 * @lastUpdated 2025年03月28日
 * 
 */
public class CommandHandler {

    /** 创建一个 Scanner 对象用于读取用户输入 */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 处理用户输入的指令，并将指令传递给相应的处理类进行处理。
     * 
     * @throws Exception 如果在处理过程中发生错误
     */
    public static void process() throws Exception {
        // 读取用户输入并去除前后空白字符
        String input = scanner.nextLine().trim();
        
        // 如果用户输入为空，直接返回
        if (input.isEmpty()) return;
        
        // 将输入的指令按空格分割为多个部分
        String[] tokens = input.split("\\s+");
        
        // 获取第一个单词作为命令并将其首字母大写
        String command = tokens[0].toLowerCase();
        command = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
        
        // 将命令后面的部分作为参数
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        
        try {
            // 使用工厂模式根据命令创建相应的 Command 实例并执行
            Command cmd = Factory.getInstance("agenda.ui.command." + command, Command.class);
            cmd.exec(params);
        } catch(NumberFormatException e) {
            // 捕获数字格式异常并输出错误信息
            System.out.println("Error: Wrong number format");
        } catch(Exception e) {
        	// 捕获其它所有命令异常并输出错误信息
        	System.out.println("Error: Unknown command");
        }
    }
    
}
