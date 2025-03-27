package agenda.ui.command;

import agenda.bll.UserRegister;
import agenda.bll.user.User;

/**
 * Cancel类
 * <p>
 * 该类用于执行用户注销操作，删除指定用户账户。
 * </p>
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月27日
 */
public class Cancel implements Logger {

    /**
     * 执行用户注销
     * <p>
     * 该方法验证用户身份，并删除指定用户名的账户。
     * </p>
     * 
     * @param params 命令参数，包括用户名和密码
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数个数是否正确
        if (params.length != 2) {
            System.out.println("Error: Usage - cancel [userName] [password]");
            return;
        }

        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;

        // 调用 UserRegister 删除用户
        UserRegister.DeleteUser(params[0]);

        // 输出成功信息
        System.out.println("Success: User canceled");
    }

}

