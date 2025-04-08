package agenda.ui.command;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * addagenda类
 * 
 * <p>
 * 用于处理创建会议的命令。<br>
 * 该类实现了Logger接口，负责解析用户输入的参数并调用Manager类的创建会议方法，处理会议的添加功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年03月27日
 * @lastUpdated 2025年03月28日
 * 
 */
public class Addagenda implements Loger {

    /**
     * 执行创建会议的命令
     * 
     * <p>
     * 该方法接收命令行参数，验证用户身份，并尝试根据提供的参数创建会议。<br>
     * 如果时间格式不正确或有冲突，会输出相应的错误信息。
     * </p>
     * 
     * @param params 命令行传入的参数
     * @throws IOException 如果出现I/O异常
     * 
     */
    @Override
    public void exec(String[] params) throws IOException {
        
        // 检查参数的数量是否为6，若不为6则输出错误提示
        if (params.length != 6) {
            System.out.println("Error: Usage - addagenda [userName] [password] [attendee] [starttime] [endtime] [label]");
            return; // 参数不正确，终止执行
        }

        // 验证用户身份，若验证失败则返回
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;

        // 将用户对象转换为Manager类型，以调用创建会议的方法
        Manager manager = (Manager) user;
        boolean success = false;

        // 处理时间格式，将“_”替换为空格，以符合时间格式要求
        params[3] = params[3].replace('_', ' ');
        params[4] = params[4].replace('_', ' ');
        params[5] = params[5].replace('_', ' ');

        // 尝试创建会议，若时间格式不正确会捕获DateTimeParseException异常并输出提示
        try {
            success = manager.createAgenda(params[2], params[3], params[4], params[5]);
        } catch (DateTimeParseException e) {
            // 输出时间格式错误提示
            System.out.println("Error: Format of time - XXXX-XX-XX_XX:XX:XX");
            return; // 如果时间格式不正确，终止执行
        } catch (NullPointerException e) {
        	System.out.println("Error: Attendee not found");
        	return;
        }

        // 根据创建会议的结果输出相应的提示信息
        System.out.println(success ? "Success: Meeting created" : "Error: Time conflict");
    }

}
