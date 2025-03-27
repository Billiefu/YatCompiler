package agenda.bll.user;

/**
 * 用户类
 * <p>
 * 用于抽象化用户信息，实现用户的基本操作，如设置和获取用户名、密码等。
 * 该类实现 Comparable 接口，以支持用户对象的比较（按用户名排序）。
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
public class User implements Comparable<User> {
    
    // 用户名
    private String name;
    
    // 用户密码
    private String code;

    /**
     * 构造方法
     * <p>
     * 使用用户名和密码创建用户对象
     *
     * @param name 用户名
     * @param code 用户密码
     */
    public User(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * 拷贝构造方法
     * <p>
     * 基于现有用户对象创建新的用户对象
     *
     * @param user 要复制的用户对象
     */
    public User(User user) {
        this.name = user.GetName();
        this.code = user.GetCode(); // 修正错误：原代码错误地将 name 赋值两次
    }

    /**
     * 设置用户名
     *
     * @param name 新的用户名
     */
    public void SetName(String name) {
        this.name = name;
    }

    /**
     * 设置用户密码
     *
     * @param code 新的密码
     */
    public void SetCode(String code) {
        this.code = code;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String GetName() {
        return this.name;
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    public String GetCode() {
        return this.code;
    }

    /**
     * 判断用户名是否匹配
     *
     * @param name 要匹配的用户名
     * @return 如果匹配则返回 true，否则返回 false
     */
    public boolean SameName(String name) {
        return this.name.matches(name);
    }

    /**
     * 比较两个用户对象，按用户名的字典序进行排序
     *
     * @param user 要比较的用户对象
     * @return 比较结果（负值：当前用户小；零：相等；正值：当前用户大）
     */
    @Override
    public int compareTo(User user) {
        return this.name.compareTo(user.name);
    }
}
