package agenda.dml;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.user.User;
import agenda.dal.UserIO;

/**
 * 用户管理类
 * 
 * <p>
 * 用于管理所有已有的用户信息。<br>
 * 该类采用单例模式，提供用户的添加、删除、修改和查询功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月15日
 * @lastUpdated 2025年3月28日
 * 
 */
public class UserManagement {
    
	/** 单例实例（饿汉式） */
    private static final UserManagement Instance = new UserManagement();
    
    /** 存储所有用户的集合 */
    private Set<User> users = new TreeSet<User>();

    /**
     * 私有构造方法（单例模式）
     * 
     * 初始化用户管理系统，从存储中读取用户数据。
     */
    private UserManagement() {
        try {
            // 从文件中读取用户数据
            String users = UserIO.input();

            // 按行拆分用户数据
            String regexa = "\r\n";
            String regexb = ","; // 逗号分隔用户信息
            String resulta[] = users.split(regexa);
            String resultb[][] = new String[resulta.length][3];

            // 解析用户信息
            for (int i = 0; i < resulta.length; i++) {
                resultb[i] = resulta[i].split(regexb);
            }

            // 创建用户对象并添加到用户集合
            for (int i = 0; i < resulta.length; i++) {
                User user = new User(resultb[i][0], resultb[i][1]);
                this.users.add(user);
            }
        } catch (IOException e) {
            // 读取数据时发生异常，保持用户集合为空
        }
    }

    /**
     * 获取 UserManagement 单例实例。
     *
     * @return UserManagement 实例
     */
    public static UserManagement getInstance() {
        return Instance;
    }

    /**
     * 将当前用户信息保存到存储中。
     *
     * @throws IOException 可能抛出的异常
     */
    private void saveUsers() throws IOException {
        UserIO.output(this.users);
    }

    /**
     * 添加新用户
     *
     * @param user 需要添加的用户对象
     * @return 添加结果：<br>
     *         0 - 添加成功；<br>
     *        -1 - 用户名为空；<br>
     *        -2 - 用户名已存在
     * @throws IOException 可能抛出的异常
     */
    public int addUser(User user) throws IOException {
        // 判断用户名是否为空
        if (user.getName() == null || user.getName().matches("")) {
            return -1;
        }
        
        // 检查用户名是否已存在
        for (User other : this.users) {
            if (other.sameName(user.getName())) {
                return -2;
            }
        }

        // 添加用户并保存
        this.users.add(user);
        this.saveUsers();
        return 0;
    }

    /**
     * 删除指定用户名的用户
     *
     * @param name 需要删除的用户名
     * @throws IOException 可能抛出的异常
     */
    public void deleteUser(String name) throws IOException {
        // 记录待删除用户
        Set<User> toDelete = new TreeSet<User>();
        for (User user : this.users) {
            if (user.getName().matches(name)) {
                toDelete.add(user);
            }
        }

        // 执行删除
        toDelete.forEach(this.users::remove);
        this.saveUsers();
    }
    
    /**
     * 查找指定用户名的用户
     *
     * @param name 需要查找的用户名
     * @return 找到的用户对象，如果不存在则返回 null
     */
    public User searchUser(String name) {
        for (User user : this.users) {
            if (user.getName().matches(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 修改用户名
     *
     * @param name 旧用户名
     * @param newname 新用户名
     * @return 是否修改成功：<br>
     *         true - 修改成功；<br>
     *         false - 新用户名已存在
     * @throws IOException 可能抛出的异常
     */
    public boolean changeName(String name, String newname) throws IOException {
        // 查找原用户
        User origin = this.searchUser(name);
        
        // 检查新用户名是否已存在
        for (User user : this.users) {
            if (user.getName().matches(newname)) {
                return false;
            }
        }

        // 更新用户名并保存
        origin.setName(newname);
        this.saveUsers();
        return true;
    }

    /**
     * 修改用户密码
     *
     * @param name 需要修改密码的用户名
     * @param code 新密码
     * @throws IOException 可能抛出的异常
     */
    public void changeCode(String name, String code) throws IOException {
        for (User user : this.users) {
            if (user.getName().matches(name)) {
                user.setCode(code);
            }
        }
        this.saveUsers();
    }
    
}
