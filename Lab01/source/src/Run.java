import java.io.IOException;

import windows.Home;

/**
 * 应用程序主入口类
 * 
 * <p>
 * 负责初始化并启动图形用户界面，作为整个税务计算软件程序的启动入口。<br>
 * 遵循Java应用程序标准入口规范，不包含业务逻辑实现。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Run {
    
    /**
     * 应用程序主入口方法
     * 
     * <p>
     * 创建并显示主界面窗口，启动Java Swing事件调度线程。<br>
     * 遵循Java应用程序标准入口方法签名规范。
     * </p>
     *
     * @param args 命令行参数（本系统未使用）
     * @throws IOException 当界面初始化失败时抛出
     */
    public static void main(String args[]) throws IOException {
        // 初始化主界面窗口
        Home frame = new Home();
        // 显示窗口并启动消息循环
        frame.setVisible(true);
    }
    
}
