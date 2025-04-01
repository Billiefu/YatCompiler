package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * clearagenda类
 * 
 * <p>
 * 该类用于执行清空用户所有会议的操作。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Clearagenda implements Loger {

    /**
     * 执行清空会议操作
     * 
     * <p>
     * 该方法验证用户身份，并清空该用户的所有会议。
     * </p>
     * 
     * @param params 命令参数，包括用户名和密码
     * @throws Exception 可能抛出的异常
     * 
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数个数是否正确
        if (params.length != 2) {
            System.out.println("Error: Usage - clearagenda [userName] [password]");
            return;
        }

        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;

        // 将用户转换为Manager对象以执行清空会议操作
        Manager manager = (Manager) user;
        manager.clearAgenda(); // 清空所有会议

        // 输出成功信息
        System.out.println("Success: Agendas cleared");
    }

}
