package agenda.ui.command;

/**
 * Quit类
 * <p>
 * 该类用于退出会议系统，执行退出操作时，打印退出信息并终止系统的运行。
 * </p>
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月27日
 */
public class Quit implements Other {

    /**
     * 执行退出系统的命令
     * 
     * @param params 命令参数，退出命令不需要任何参数
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 打印退出信息
        System.out.println("System exit");
        
        // 调用System.exit(0)方法退出系统
        System.exit(0);
    }
    
}
