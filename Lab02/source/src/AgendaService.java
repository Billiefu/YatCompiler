import agenda.ui.CommandHandler;

/**
 * 会议管理服务类
 * 
 * <p>
 * 负责初始化并启动命令行用户界面，作为整个会议管理系统程序的启动入口。<br>
 * 遵循Java应用程序标准入口规范，不包含业务逻辑实现。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月6日
 * @lastUpdated 2025年3月28日
 * 
 */
public class AgendaService {
    
    /**
     * 程序的主入口方法。
     * 
     * @param args 命令行参数（未使用）
     * @throws Exception 可能抛出的异常
     */
    public static void main(String args[]) throws Exception {
    	
    	// 输出系统启动信息
        System.out.println("Agenda Management System Started. Enter 'quit' to exit.");
        
        // 无限循环，持续监听用户输入
        while (true) {
        	System.out.print("$ ");		// 显示命令行提示符
        	CommandHandler.process();
        }
        
    }
    
}
