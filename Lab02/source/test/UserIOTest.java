package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import agenda.bll.user.User;
import agenda.dml.UserManagement;

/**
 * 用户（User）IO 测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link UserManagement} 对单个或多个 {@link User} 
 * 进行存储（写入）和删除（读取）操作的性能。<br>
 * 具体测试内容包括：<br>
 * 1. 大规模用户数据的添加性能测试。<br>
 * 2. 大规模用户数据的删除性能测试。<br>
 * 测试方法按照顺序执行，确保添加测试先于删除测试进行。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月2日
 * @lastUpdated 2025年4月2日
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIOTest {
	
    /** 用户管理单例对象 */
    private UserManagement usermanagement = UserManagement.getInstance();
    
    /** 测试数据大小 */
    private static final int datasize = 1000; 
    
    /** 存储测试用的用户数据集 */
    private Set<User> users;
    
    /**
     * 测试初始化
     * 
     * <p>
     * 在所有测试方法执行前，生成 {@code datasize} 条测试用户数据。<br>
     * 并存储到 {@code users} 集合中，以便后续测试使用。
     * </p>
     */
    @BeforeAll
    void setup() {
    	this.users = TestDataGenerator.generateUsers(datasize);
    }
    
    /**
     * 测试用户数据的添加性能
     * 
     * <p>
     * 该测试方法验证 {@link UserManagement#addUser(User)} 方法的性能。<br>
     * 主要测试以下内容：<br>
     * 1. 计算添加 {@code datasize} 条用户数据的时间。<br>
     * 2. 判断添加时间是否在 1000ms 内，若超时则测试失败。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(1)
    @Test
    void AddTest() throws IOException {
        long startTime = System.currentTimeMillis(); // 记录开始时间
        
        // 批量添加用户
        for(User user : users) {
        	usermanagement.addUser(user);
        }
        
        long duration = System.currentTimeMillis() - startTime; // 计算耗时
        
        // 打印性能日志
        System.out.println("[UserIO - 添加性能] 添加 " + datasize + " 条用户数据耗时：" + duration + "ms");
        
        // 断言：添加操作应在 1000ms 内完成
        assertTrue(duration < 1000, "用户数据读写时间超出预期");
    }
    
    /**
     * 测试用户数据的删除性能
     * 
     * <p>
     * 该测试方法验证 {@link UserManagement#deleteUser(String)} 方法的性能。<br>
     * 主要测试以下内容：<br>
     * 1. 计算删除 {@code datasize} 条用户数据的时间。<br>
     * 2. 判断删除时间是否在 1000ms 内，若超时则测试失败。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(2)
    @Test
    void DeleteTest() throws IOException {
        long startTime = System.currentTimeMillis(); // 记录开始时间
        
        // 批量删除用户
        for(User user : users) {
        	usermanagement.deleteUser(user.getName());
        }
        
        long duration = System.currentTimeMillis() - startTime; // 计算耗时
        
        // 打印性能日志
        System.out.println("[UserIO - 删除性能] 删除 " + datasize + " 条用户数据耗时：" + duration + "ms");
        
        // 断言：删除操作应在 1000ms 内完成
        assertTrue(duration < 1000, "用户数据读写时间超出预期");
    }
    
}
