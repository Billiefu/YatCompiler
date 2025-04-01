package agenda.bll;

import java.io.IOException;

import agenda.bll.user.User;
import agenda.dml.AgendaManagement;
import agenda.dml.UserManagement;

/**
 * 用户注册类
 * 
 * <p>
 * 用于完成用户的注册、注销和登录功能。
 * 该类提供用户的添加、删除及登录验证的方法。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月28日
 * 
 */
public class UserRegister {
	
    /** 用户管理实例 */
    private static UserManagement usermanagement = UserManagement.getInstance();

    /**
     * 添加用户
     *
     * @param name 用户名
     * @param code 用户密码
     * @return 添加用户的结果状态码（由 UserManagement 决定）
     * @throws IOException 可能抛出的 IO 异常
     */
    public static int addUser(String name, String code) throws IOException {
        // 调用 UserManagement 进行用户注册
        return usermanagement.AddUser(new User(name, code));
    }

    /**
     * 删除用户
     *
     * @param name 要删除的用户名
     * @throws IOException 可能抛出的 IO 异常
     */
    public static void deleteUser(String name) throws IOException {
        // 清空该用户的所有会议记录
        AgendaManagement.getInstance().ClearAgenda(name);
        // 从用户管理系统中删除用户
        usermanagement.DeleteUser(name);
    }

    /**
     * 用户登录验证
     *
     * @param name 用户名
     * @param code 用户输入的密码
     * @return 返回登录状态：
     *         -1：用户不存在；
     *         -2：密码错误；
     *          0：登录成功
     */
    public static int login(String name, String code) {
        // 查询用户是否存在
        User user = usermanagement.SearchUser(name);
        if (user == null) {
            return -1; // 用户不存在
        }
        // 检查密码是否匹配
        if (!user.sameCode(code)) {
            return -2; // 密码错误
        }
        return 0; // 登录成功
    }
    
}
