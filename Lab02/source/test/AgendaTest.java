package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import agenda.bll.Agenda;
import agenda.bll.user.User;
import agenda.dal.AgendaIdGenerator;

/**
 * 会议（Agenda）测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link Agenda} 类的基本功能是否能够
 * 正常、健壮地运行。<br>
 * 具体测试内容包括：<br>
 * 1. 会议的创建及初始化信息验证。<br>
 * 2. 参会人员的添加与删除。<br>
 * 3. 会议组织者及标签的修改。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月30日
 * @lastUpdated 2025年3月30日
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendaTest {
	
	/** 用于测试的会议对象 */
	private Agenda agenda;
	
	/** 用于测试的会议对象 */
	private Agenda agenda2;
	
	/**
	 * 测试初始化
	 * 
	 * <p>
	 * 在构造函数中创建测试用户 "Jerry"（组织者）和 "Knight"（参会者），
	 * 并初始化两场会议数据。<br>
	 * agenda1 未设置 ID，默认为 -1。<br>
	 * agenda2 直接从 ID 生成器获取唯一 ID。
	 * </p>
	 */
	public AgendaTest() {
		User organizer = new User("Jerry", "000000"); // 创建会议组织者
		User user = new User("Knight", "000000"); // 创建参会者
		Set<User> attendee = new TreeSet<User>(); // 参会者集合
		attendee.add(user);
		
		// 初始化会议对象（未设置 ID）
		this.agenda = new Agenda(organizer, attendee, "2025年3月13日 16:30:00", "2025年3月13日 18:10:00", "编译器构造实验");
		
		// 初始化会议对象（直接从 ID 生成器获取 ID）
		this.agenda2 = new Agenda(AgendaIdGenerator.getInstance().getId(), organizer, attendee, "2025年3月13日 16:30:00", "2025年3月13日 18:10:00", "编译器构造实验");
	}

	/**
	 * 测试会议的创建
	 * 
	 * <p>
	 * 1. 确保 agenda 对象不为空。<br>
	 * 2. 验证 agenda 的 ID 是否为默认值 -1。<br>
	 * 3. 验证 agenda2 是否成功从 ID 生成器获取 ID。<br>
	 * 4. 验证组织者是否正确。<br>
	 * 5. 验证参会人员是否正确。<br>
	 * 6. 验证会议时间是否正确。<br>
	 * 7. 验证会议主题是否正确。
	 * </p>
	 */
	@Order(1)
	@Test
	void createtest() {
		// 确保 agenda 不为空
		assertNotNull(this.agenda);
		
		// agenda1 ID 应为默认值 -1
		assertEquals(-1, agenda.getId());
		
		// agenda2 ID 应与 ID 生成器的 ID 匹配
		assertEquals(AgendaIdGenerator.getInstance().getId(), agenda2.getId());
		
		// 组织者应为 "Jerry"
		assertEquals("Jerry", agenda.getOrganizer());
		
		// 确保参会者姓名为 "Knight"
		for (User attendee : agenda.getAttendees()) {
			assertEquals("Knight", attendee.getName());
		}
		
		// 验证会议开始时间
		assertEquals("2025年3月13日 16:30:00", agenda.getStartTimeFormat());
		
		// 验证会议结束时间
		assertEquals("2025年3月13日 18:10:00", agenda.getEndTimeFormat());
		
		// 验证会议主题
		assertEquals("编译器构造实验", agenda.getLabel());
	}
	
	/**
	 * 测试会议的参会者管理
	 * 
	 * <p>
	 * 1. 添加新的参会者 "Luv"。<br>
	 * 2. 验证参会者列表是否包含 "Knight" 和 "Luv"。<br>
	 * 3. 删除 "Luv" 参会者。<br>
	 * 4. 确保参会者列表只剩 "Knight"。
	 * </p>
	 */
	@Order(2)
	@Test
	void attendeetest() {
		// 预期的参会者名单
		String[] names = { "Knight", "Luv" };
		User user = new User("Luv", "000000"); // 创建新参会者 "Luv"
		
		// 添加新参会者 "Luv"
		agenda.addAttendee(user);
		int i = 0;
		
		// 验证参会者列表是否包含 "Knight" 和 "Luv"
		for (User attendee : this.agenda.getAttendees()) {
			assertEquals(names[i], attendee.getName());
			i++;
		}
		
		// 删除 "Luv"
		agenda.deleteAttendee(user);
		
		// 确保参会者列表只剩 "Knight"
		for (User attendee : this.agenda.getAttendees()) {
			assertEquals("Knight", attendee.getName());
		}
	}
	
	/**
	 * 测试会议的修改功能
	 * 
	 * <p>
	 * 1. 生成新的会议 ID。<br>
	 * 2. 修改会议组织者为 "Knight"。<br>
	 * 3. 修改会议标签为 "编译器构造实验课程"。<br>
	 * 4. 验证 ID 是否正确。<br>
	 * 5. 验证组织者是否修改成功。<br>
	 * 6. 验证参会者列表格式是否正确。<br>
	 * 7. 验证会议标签是否正确。
	 * </p>
	 */
	@Order(3)
	@Test
	void changetest() {
		// 生成新的 ID
		agenda.setId();
		
		// 修改会议组织者为 "Knight"
		agenda.changeOrganizer(new User("Knight", "000000"));
		
		// 修改会议标签
		agenda.changeLabel("编译器构造实验课程");
		
		// 验证 ID 是否正确
		assertEquals(AgendaIdGenerator.getInstance().getId(), agenda.getId());
		
		// 验证组织者是否修改成功
		assertEquals("Knight", agenda.getOrganizer());
		
		// 验证参会者格式是否正确
		for (String attendee : this.agenda.getAttendeesFormat()) {
			assertEquals("Jerry", attendee);
		}
		
		// 验证会议标签是否正确
		assertEquals("编译器构造实验课程", agenda.getLabel());
	}
	
}
