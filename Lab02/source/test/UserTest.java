package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import agenda.bll.user.User;

/**
 * 用户（User）测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link User} 类的基本功能是否能够
 * 正常、健壮地运行。<br>
 * 具体测试内容包括：<br>
 * 1. 用户的创建测试。<br>
 * 2. 用户的属性修改测试。<br>
 * 3. 用户的拷贝测试。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月30日
 * @lastUpdated 2025年3月30日
 * 
 */
@TestInstance(Lifecycle.PER_CLASS) // 使测试实例在整个测试类生命周期内共享
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 按 @Order 指定的顺序执行测试
class UserTest {
    
	/** 用于测试的用户对象 */
    private User user = new User("Jerry", "000000");

    /**
     * 测试用户的创建
     * 
     * <p>
     * 1. 验证 {@code user} 对象是否成功创建。<br>
     * 2. 验证 {@code user} 的名称和密码是否正确。<br>
     * 3. 使用拷贝构造方法创建一个 {@code copy}，并验证其数据是否与原对象一致。
     * </p>
     */
    @Order(1)
    @Test
    void createTest() {
        assertNotNull(this.user); // 验证 user 对象是否成功创建
        assertEquals("Jerry", this.user.getName()); // 验证用户名是否正确
        assertEquals("000000", this.user.getCode()); // 验证密码是否正确

        // 使用拷贝构造方法创建一个副本
        User copy = new User(this.user);
        assertNotNull(copy); // 验证副本对象是否成功创建
        assertEquals("Jerry", copy.getName()); // 验证副本的用户名是否与原对象一致
        assertEquals("000000", copy.getCode()); // 验证副本的密码是否与原对象一致
    }

    /**
     * 测试用户的属性修改
     * 
     * <p>
     * 1. 修改 {@code user} 的用户名为 "Knight"。<br>
     * 2. 修改 {@code user} 的密码为 "999999"。<br>
     * 3. 验证修改后的数据是否符合预期。
     * </p>
     */
    @Order(2)
    @Test
    void changeTest() {
        this.user.setName("Knight"); // 修改用户名
        this.user.setCode("999999"); // 修改密码

        assertEquals("Knight", user.getName()); // 验证修改后的用户名
        assertEquals("999999", user.getCode()); // 验证修改后的密码
    }

    /**
     * 测试用户对象的拷贝
     * 
     * <p>
     * 1. 通过拷贝构造方法创建 {@code copy}。<br>
     * 2. 验证拷贝对象的名称与原对象一致。<br>
     * 3. 验证拷贝对象的密码与原对象一致。
     * </p>
     */
    @Order(3)
    @Test
    void sameTest() {
        User copy = new User(this.user); // 通过拷贝构造方法创建对象
        assertEquals(this.user.getName(), copy.getName()); // 验证用户名一致
        assertEquals(this.user.getCode(), copy.getCode()); // 验证密码一致
    }
    
}
