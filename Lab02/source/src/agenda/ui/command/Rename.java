package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * rename类
 * 
 * <p>
 * 该类用于修改用户的用户名。
 * 用户需要提供当前用户名和密码以及新的用户名。
 * 如果验证通过并且新的用户名没有被占用，则修改用户名。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Rename implements Loger {

    /**
     * 执行用户重命名的命令
     * 
     * @param params 命令参数，包括用户名、密码和新的用户名
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 参数数量检查，确保传入正确的用户名、密码和新的用户名
        if (params.length != 3) {
            // 输出错误信息并返回
            System.out.println("Error: Usage - rename [userName] [password] [newName]");
            return;
        }

        // 验证用户身份，检查用户名和密码是否正确
        User user = checkLogin(params[0], params[1]);
        if (user == null) return; // 如果验证失败，返回

        // 将用户转换为管理者角色
        Manager manager = (Manager) user;
        
        // 修改用户名，如果修改成功，输出成功信息；否则输出错误信息
        if (manager.changeName(params[2])) {
            System.out.println("Success: User Renamed");
        } else {
            System.out.println("Error: Username already exists");
        }
    }

}
