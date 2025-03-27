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
        // 调用 IOProcess 类的 Home 方法，启动程序
        IOProcess.Home();
    }
}

