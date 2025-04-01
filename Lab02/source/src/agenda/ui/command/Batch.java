package agenda.ui.command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import agenda.ui.Factory;

/**
 * batch类
 * 
 * <p>
 * 该类用于执行批处理任务，从文件中读取命令并依次执行。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Batch implements Other {

    /**
     * 执行批处理任务
     * 
     * <p>
     * 该方法从指定文件中读取命令，并逐行执行。
     * </p>
     * 
     * @param params 命令参数，包含批处理文件名
     * @throws Exception 可能抛出的异常
     * 
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数个数是否正确
        if (params.length != 1) {
            System.out.println("Error: Usage - batch [fileName]");
            return;
        }

        // 读取文件并逐行执行命令
        try (BufferedReader br = new BufferedReader(new FileReader(params[0]))) {
            String line;

            // 按行读取文件内容
            while ((line = br.readLine()) != null) {
                System.out.println("Executing: " + line);

                // 解析命令
                String[] tokens = line.split("\\s+");
                String command = tokens[0].toLowerCase();
                command = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
                String[] cmdParams = Arrays.copyOfRange(tokens, 1, tokens.length);

                try {
                	// 通过工厂模式创建命令实例并执行
                    Command cmd = Factory.getInstance("agenda.ui.command." + command, Command.class);
                    cmd.exec(cmdParams);
                } catch(Exception e) {
                    // 捕获未知命令异常并输出错误信息
                    System.out.println("Error: Unknown command");
                }
            }
        } catch (IOException e) {
            // 文件未找到或读取错误
            System.out.println("Error: File not found - " + params[0]);
        }
    }

}
