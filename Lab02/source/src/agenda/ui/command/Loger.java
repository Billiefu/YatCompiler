package agenda.ui.command;

import agenda.bll.UserRegister;
import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * Loger接口
 * <p>
 * 该接口继承自Command接口，作为需要进行登录验证的命令接口。
 * 实现该接口的类在执行命令之前，必须验证用户的身份。
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月28日
 */
public interface Loger extends Command {
    
    /**
     * 执行命令的方法
     * <p>
     * 由于该方法为接口的方法，所以具体实现会由继承该接口的类来提供。
     * 
     * @param params 命令参数
     * @throws Exception 可能抛出的异常
     */
    @Override
    public abstract void exec(String[] params) throws Exception;

    /**
     * 验证用户登录身份
     * <p>
     * 该方法通过用户名和密码验证用户的身份。
     * 如果验证成功，返回对应的User对象；
     * 如果验证失败，返回null。
     * 
     * @param name 用户名
     * @param code 密码
     * @return 返回User对象（成功）或null（失败）
     */
    default User checkLogin(String name, String code) {
        // 创建一个新的Manager对象（该对象用于进行用户身份验证）
        User manager = new Manager(name, code);
        
        // 调用UserRegister的Login方法进行用户登录验证，获取返回状态
        int status = UserRegister.login(name, code);
        
        // 根据返回状态判断登录结果
        switch (status) {
            case -1:
                // 用户不存在，输出错误信息并返回null
                System.out.println("Error: User not found");
                return null;
            case -2:
                // 密码错误，输出错误信息并返回null
                System.out.println("Error: Incorrect password");
                return null;
            default:
                // 登录成功，返回管理者对象
                return manager;
        }
    }
    
}
