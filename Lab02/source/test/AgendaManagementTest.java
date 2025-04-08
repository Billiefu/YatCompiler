package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import agenda.bll.Agenda;
import agenda.bll.user.User;
import agenda.dal.AgendaIdGenerator;
import agenda.dml.AgendaManagement;
import agenda.dml.UserManagement;

/**
 * 会议管理（AgendaManagement）测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link AgendaManagement} 的基本功能是否能够
 * 正常、健壮地运行。<br>
 * 具体测试内容包括：<br>
 * 1. 会议的创建、添加。<br>
 * 2. 会议的查询功能。<br>
 * 3. 会议的删除功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月2日
 * @lastUpdated 2025年4月2日
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendaManagementTest {
	
	/** 会议管理单例对象 */
	private AgendaManagement agendamanagement = AgendaManagement.getInstance();
	
	/** 用户管理单例对象 */
	private UserManagement usermanagement = UserManagement.getInstance();
	
	/** 用于测试的会议对象 */
	private Agenda agenda1;
	
	/** 用于测试的会议对象 */
	private Agenda agenda2;
	
	/**
	 * 测试初始化
	 * 
	 * <p>
	 * 在构造函数中创建测试用户 "Jerry"（组织者）和 "Knight"（参会者），
	 * 并初始化两场会议数据。<br>
	 * 其中，agenda1 会议时间为 14:20 - 16:00，<br>
	 * agenda2 会议时间为 14:00 - 15:00（与 agenda1 冲突）。
	 * </p>
	 * 
	 * @throws IOException 如果初始化用户或会议数据时发生 IO 异常
	 */
	public AgendaManagementTest() throws IOException {
		User organizer = new User("Jerry", "000000"); // 创建会议组织者
		User attendee = new User("Knight", "000000"); // 创建参会者
		Set<User> attendees = new TreeSet<User>(); // 创建参会者集合
		attendees.add(attendee); 
		
		// 将用户添加到用户管理系统
		usermanagement.addUser(organizer);
		usermanagement.addUser(attendee);
		
		// 初始化会议对象
		this.agenda1 = new Agenda(organizer, attendees, "2025-03-13 14:20:00", "2025-03-13 16:00:00", "编译原理");
		this.agenda2 = new Agenda(organizer, attendees, "2025-03-13 14:00:00", "2025-03-13 15:00:00", "实验室组会");
	}
	
	/**
	 * 测试会议的创建与添加
	 * 
	 * <p>
	 * 1. 先为 agenda1 和 agenda2 设置 ID。<br>
	 * 2. 尝试添加 agenda1（应成功），再尝试添加 agenda2（因时间冲突，应失败）。<br>
	 * 3. 修改 agenda2 的时间，使其与 agenda1 不冲突，并再次添加（应成功）。
	 * </p>
	 * 
	 * @throws IOException 如果添加会议时发生 IO 异常
	 */
	@Order(1)
	@Test
	void createTest() throws IOException {
		agenda1.setId(); // 生成 agenda1 ID
		agenda2.setId(); // 生成 agenda2 ID
		
		// 添加 agenda1，应返回 true（成功）
		assertEquals(agendamanagement.addAgenda(agenda1), true);
		
		// 添加 agenda2，应返回 false（时间冲突，失败）
		assertEquals(agendamanagement.addAgenda(agenda2), false);
		
		// 重新调整 agenda2 的时间
		this.agenda2 = new Agenda(
			this.usermanagement.searchUser(this.agenda2.getOrganizer()), 
			this.agenda2.getAttendees(), 
			"2025-03-13 16:30:00", "2025-03-13 18:10:00", "编译器构造实验"
		);
		agenda2.setId(); // 生成新的 ID
		
		// 添加新的 agenda2，应返回 true（成功）
		assertEquals(agendamanagement.addAgenda(agenda2), true);
	}
	
	/**
	 * 测试会议的查询功能
	 * 
	 * <p>
	 * 1. 通过组织者 "Jerry" 查询 agenda1，确保返回的 ID 和标签正确。<br>
	 * 2. 通过参会者 "Knight" 按时间范围查询 agenda2，确保返回的 ID 和标签正确。
	 * </p>
	 * 
	 * @throws IOException 如果查询会议时发生 IO 异常
	 */
	@Order(2)
	@Test
	void searchTest() throws IOException {
		// 通过 "Jerry" 组织者 ID 查询 agenda1
		Set<Agenda> agendas1 = agendamanagement.searchAgenda("Jerry", agenda1.getId());
		for (Agenda agenda : agendas1) {
			assertEquals(agenda.getId(), agenda1.getId()); // ID 应匹配
			assertEquals(agenda.getLabel(), agenda1.getLabel()); // 标签应匹配
		}
		
		// 通过 "Knight" 参会者按时间范围查询 agenda2
		Set<Agenda> agendas2 = agendamanagement.searchAgenda("Knight", agenda2.getStartTime(), agenda2.getEndTime());
		for (Agenda agenda : agendas2) {
			assertEquals(agenda.getId(), agenda2.getId()); // ID 应匹配
			assertEquals(agenda.getLabel(), agenda2.getLabel()); // 标签应匹配
		}
	}
	
	/**
	 * 测试会议的删除功能
	 * 
	 * <p>
	 * 1. 清除 "Knight" 参会的所有会议，确保仍有 2 条记录（因 "Knight" 仅是参会者）。<br>
	 * 2. 清除 "Jerry" 组织的所有会议，确保所有会议都被删除（应返回 0）。
	 * </p>
	 * 
	 * @throws IOException 如果删除会议时发生 IO 异常
	 */
	@Order(3)
	@Test
	void deleteTest() throws IOException {
		// 删除 "Knight" 参会的所有会议
		agendamanagement.clearAgenda("Knight");
		Set<Agenda> agendas1 = agendamanagement.searchAgenda("Knight");
		assertEquals(agendas1.size(), 2); // 仍然有 2 条记录（因为 "Knight" 仅是参会者）

		// 删除 "Jerry" 组织的所有会议
		agendamanagement.clearAgenda("Jerry");
		Set<Agenda> agendas2 = agendamanagement.searchAgenda("Jerry");
		assertEquals(agendas2.size(), 0); // 所有会议都应被删除
	}
	
	/**
	 * 测试结束后重置系统状态
	 * 
	 * <p>
	 * 1. 重置会议 ID 生成器，以便后续测试不会受到影响。<br>
	 * 2. 删除测试过程中创建的用户 "Jerry" 和 "Knight"。
	 * </p>
	 * 
	 * @throws IOException 如果删除用户或重置 ID 发生 IO 异常
	 */
	@AfterAll
	void reset() throws IOException {
		// 重置 ID 生成器
		AgendaIdGenerator.getInstance().resetId();
		AgendaIdGenerator.getInstance().resetId();
		
		// 删除测试用户
		usermanagement.deleteUser("Jerry");
		usermanagement.deleteUser("Knight");
	}
	
}
