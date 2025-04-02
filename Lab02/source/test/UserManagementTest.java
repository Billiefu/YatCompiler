package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import agenda.bll.user.User;
import agenda.dml.UserManagement;

/**
 * 用户管理（UserManagement）测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link UserManagement} 的基本功能是否能够
 * 正常、健壮地运行。<br>
 * 具体测试内容包括：<br>
 * 1. 用户的创建、添加。<br>
 * 2. 用户的查询功能。<br>
 * 3. 用户的删除功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月2日
 * @lastUpdated 2025年4月2日
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserManagementTest {
	
	/** 用户管理单例对象 */
    private UserManagement usermanagement = UserManagement.getInstance();
    
    /** 用于测试的用户对象 */
    private User user1;
    
    /** 用于测试的用户对象 */
    private User user2;
    
    /**
     * 测试初始化
     * 
     * <p>
     * 初始化两个测试用户对象：<br>
     * 1. {@code user1} - 用户名 "Jerry"，密码 "000000"。<br>
     * 2. {@code user2} - 用户名 "Jerry"，密码 "999999"（后续测试中会修改用户名）。
     * </p>
     */
    public UserManagementTest() {
        user1 = new User("Jerry", "000000");
        user2 = new User("Jerry", "999999");
    }
    
    /**
     * 测试用户的创建与添加
     * 
     * <p>
     * 1. 尝试添加 {@code user1}，预期返回 0（成功）。<br>
     * 2. 尝试添加 {@code user2}，由于用户名 "Jerry" 已存在，预期返回 -2（用户名重复）。<br>
     * 3. 修改 {@code user2} 的用户名为 "Knight"，再次尝试添加，预期返回 0（成功）。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(1)
    @Test
    void createTest() throws IOException {
        assertEquals(usermanagement.addUser(user1), 0); // 添加第一个用户，成功返回 0
        assertEquals(usermanagement.addUser(user2), -2); // 添加第二个用户，因用户名重复返回 -2
        
        user2.setName("Knight"); // 修改用户名为 "Knight"
        assertEquals(usermanagement.addUser(user2), 0); // 再次添加，成功返回 0
    }
    
    /**
     * 测试用户的查询功能
     * 
     * <p>
     * 1. 查找用户名为 "Jerry" 的用户，并验证其名称和密码是否匹配 {@code user1}。<br>
     * 2. 查找不存在的用户名 "Luv"，预期返回 null。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(2)
    @Test
    void searchTest() throws IOException {
        // 查找 "Jerry" 用户，预期找到 user1
        User user = usermanagement.searchUser("Jerry");
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getCode(), user1.getCode());

        // 查找 "Luv" 用户，预期返回 null（用户不存在）
        user = usermanagement.searchUser("Luv");
        assertNull(user);
    }
    
    /**
     * 测试用户的删除功能
     * 
     * <p>
     * 1. 删除用户名为 "Jerry" 和 "Knight" 的用户。<br>
     * 2. 重新查找 "Jerry" 和 "Knight" 用户，预期返回 null（表示已成功删除）。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(3)
    @Test
    void deleteTest() throws IOException {
        usermanagement.deleteUser("Jerry"); // 删除 "Jerry"
        usermanagement.deleteUser("Knight"); // 删除 "Knight"

        // 再次查找 "Jerry" 用户，预期返回 null
        User user = usermanagement.searchUser("Jerry");
        assertNull(user);

        // 再次查找 "Knight" 用户，预期返回 null
        user = usermanagement.searchUser("Knight");
        assertNull(user);
    }
    
}
