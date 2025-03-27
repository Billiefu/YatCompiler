/**
 * 用户交互类
 * <p>
 * 该类用于读取命令行上输入的指令，并分发给 CommandHandler 类进行处理
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
package agenda.ui;

import java.util.Arrays;
import java.util.Scanner;

public class IOProcess {
    
    /** 全局 Scanner 实例，用于读取用户输入 */
    private static final Scanner scanner = new Scanner(System.in);
    
    /** CommandHandler 实例，用于处理用户输入的命令 */
    private static final CommandHandler commandHandler = CommandHandler.getInstance();
    
    /**
     * Home 方法
     * 该方法用于启动命令行界面，读取用户输入，并分发给相应的命令处理方法
     *
     * @throws Exception 可能抛出的异常
     */
    public static void Home() throws Exception {
        // 输出系统启动信息
        System.out.println("Agenda Management System Started. Enter 'quit' to exit.");
        
        // 无限循环，持续监听用户输入
        while (true) {
            System.out.print("$ "); // 显示命令行提示符
            String input = scanner.nextLine().trim(); // 读取并去除前后空格
            
            // 如果输入为空，则跳过当前循环
            if (input.isEmpty()) continue;
            
            // 解析用户输入，将其拆分为命令和参数
            String[] tokens = input.split("\\s+"); // 按空格拆分输入
            String command = tokens[0].toLowerCase(); // 获取命令并转换为小写
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length); // 获取命令参数
            
            // 根据不同的命令，调用相应的方法进行处理
            switch (command) {
                case "help":
                    commandHandler.handleHelp(params);
                    break;
                case "register":
                    commandHandler.handleRegister(params);
                    break;
                case "cancel":
                    commandHandler.handleCancel(params);
                    break;
                case "rename":
                    commandHandler.handleRename(params);
                    break;
                case "recode":
                    commandHandler.handleRecode(params);
                    break;
                case "addagenda":
                    commandHandler.handleAdd(params);
                    break;
                case "deleteagenda":
                    commandHandler.handleDelete(params);
                    break;
                case "clearagenda":
                    commandHandler.handleClear(params);
                    break;
                case "queryagenda":
                    commandHandler.handleQuery(params);
                    break;
                case "addattendee":
                    commandHandler.handleAddAttendee(params);
                    break;
                case "deleteattendee":
                    commandHandler.handleDeleteAttendee(params);
                    break;
                case "batch":
                    commandHandler.handleBatch(params);
                    break;
                case "developer":
                    commandHandler.handleDeveloper(params);
                    break;
                case "quit":
                    // 退出系统
                    System.out.println("System exit.");
                    scanner.close(); // 关闭 Scanner
                    return; // 终止循环
                default:
                    // 处理未知命令
                    System.out.println("Error: Unknown command");
            }
        }
    }
}

