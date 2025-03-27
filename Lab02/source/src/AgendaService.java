import agenda.ui.IOProcess;

/**
 * 会议管理服务类，作为程序的入口。
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
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
        	System.out.print("$ "); // 显示命令行提示符
        	IOProcess.Process();
        }
    }
}

