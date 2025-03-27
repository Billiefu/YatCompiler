package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * Recode类
 * <p>
 * 该类用于修改用户密码。用户提供用户名、密码和新密码，通过验证用户身份后，进行密码修改。
 * </p>
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月27日
 */
public class Recode implements Logger {

    /**
     * 执行修改用户密码的命令
     * 
     * @param params 命令参数，包括用户名、旧密码和新密码
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 参数数量检查，确保传入正确的参数
        if (params.length != 3) {
            // 输出错误信息并返回
            System.out.println("Error: Usage - recode [userName] [password] [newCode]");
            return;
        }
        
        // 验证用户身份
        User user = checkLogin(params[0], params[1]); 
        if (user == null) return; // 如果用户身份验证失败，则退出

        // 将用户转换为管理者角色，执行修改密码的操作
        Manager manager = (Manager) user;
        manager.ChangeCode(params[2]); // 更改密码

        // 输出成功信息
        System.out.println("Success: User Recoded");
    }

}

