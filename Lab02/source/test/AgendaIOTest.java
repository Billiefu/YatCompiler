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

import agenda.bll.Agenda;
import agenda.dml.AgendaManagement;

/**
 * 会议日程（Agenda）IO 测试类
 * 
 * <p>
 * 该类为 JUnit 测试用例，主要用于测试 {@link Agenda} 对单个或多个 {@link Agenda} 
 * 进行存储（写入）和删除（读取）操作的性能。<br>
 * 具体测试内容包括：<br>
 * 1. 大规模会议数据的添加性能测试。<br>
 * 2. 大规模会议数据的删除性能测试。<br>
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
class AgendaIOTest {
	
	/** 会议管理单例对象 */
	private AgendaManagement agendamanagement = AgendaManagement.getInstance();
	
	/** 测试数据大小 */
	private static final int datasize = 100;
	
	/** 存储测试用的会议数据集 */
	private Set<Agenda> agendas;
    
    /**
     * 测试初始化
     * 
     * <p>
     * 在所有测试方法执行前，生成 {@code datasize} 条测试会议数据。<br>
     * 并存储到 {@code agendas} 集合中，以便后续测试使用。
     * </p>
     */
    @BeforeAll
    void setup() {
    	this.agendas = TestDataGenerator.generateAgendas(datasize); // 生成指定数量的测试数据
    }
    
    /**
     * 测试会议数据的添加性能
     * 
     * <p>
     * 该测试方法验证 {@link AgendaManagement#addAgenda(Agenda)} 方法的性能。<br>
     * 主要测试以下内容：<br>
     * 1. 计算添加 {@code datasize} 条会议数据的时间。<br>
     * 2. 判断添加时间是否在 2500ms 内，若超时则测试失败。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(1)
    @Test
    void AddTest() throws IOException {
        long startTime = System.currentTimeMillis(); // 记录起始时间
        
        // 依次添加所有会议数据
        for (Agenda agenda : agendas) {
        	agendamanagement.addAgenda(agenda);
        }
        
        long duration = System.currentTimeMillis() - startTime; // 计算总耗时
        
        // 输出添加操作的耗时
        System.out.println("[AgendaIO - 添加性能] 添加 " + datasize + " 条会议数据耗时：" + duration + "ms");
        
        // 确保添加操作在 2500ms 内完成
        assertTrue(duration < 2500, "会议数据读写时间超出预期");
    }
    
    /**
     * 测试会议数据的删除性能
     * 
     * <p>
     * 该测试方法验证 {@link AgendaManagement#deleteAgenda(String, long)} 方法的性能。<br>
     * 主要测试以下内容：<br>
     * 1. 计算删除 {@code datasize} 条会议数据的时间。<br>
     * 2. 判断删除时间是否在 2500ms 内，若超时则测试失败。
     * </p>
     * 
     * @throws IOException 可能因文件存储失败抛出异常
     */
    @Order(2)
    @Test
    void DeleteTest() throws IOException {
        long startTime = System.currentTimeMillis(); // 记录起始时间
        
        // 依次删除所有会议数据
        for (Agenda agenda : agendas) {
        	agendamanagement.deleteAgenda(agenda.getOrganizer(), agenda.getId());
        }
        
        long duration = System.currentTimeMillis() - startTime; // 计算总耗时
        
        // 输出删除操作的耗时
        System.out.println("[AgendaIO - 删除性能] 删除 " + datasize + " 条会议数据耗时：" + duration + "ms");
        
        // 确保删除操作在 2500ms 内完成
        assertTrue(duration < 2500, "会议数据读写时间超出预期");
    }
    
}