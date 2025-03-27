package agenda.ui.command;

import agenda.bll.UserRegister;

/**
 * Register类
 * <p>
 * 该类用于注册新用户。用户提供用户名和密码，调用UserRegister类的AddUser方法进行用户注册。
 * 根据注册结果，输出不同的提示信息。
 * </p>
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月27日
 */
public class Register implements Other {

    /**
     * 执行用户注册的命令
     * 
     * @param params 命令参数，包括用户名和密码
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 参数数量检查，确保传入正确的用户名和密码
        if (params.length != 2) {
            // 输出错误信息并返回
            System.out.println("Error: Usage - register [userName] [password]");
            return;
        }
        
        // 调用 UserRegister 类进行用户注册
        int result = UserRegister.AddUser(params[0], params[1]);
        
        // 根据注册结果进行处理
        switch (result) {
            case -1:
                // 输出用户名格式无效的错误信息
                System.out.println("Error: Invalid username format");
                break;
            case -2:
                // 输出用户名已存在的错误信息
                System.out.println("Error: Username already exists");
                break;
            case 0:
                // 输出注册成功的信息
                System.out.println("Success: User registered");
                break;
        }
    }

}
