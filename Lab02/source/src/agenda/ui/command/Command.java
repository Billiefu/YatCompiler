package agenda.ui.command;

/**
 * Command接口
 * <p>
 * 该接口定义了所有命令类必须实现的方法。
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月28日
 */
public interface Command {

    /**
     * 执行具体命令
     * <p>
     * 所有实现该接口的类都必须提供该方法，以处理具体的命令执行逻辑。
     * 
     * @param params 命令参数
     * @throws Exception 可能抛出的异常
     */
    public abstract void exec(String[] params) throws Exception;

}
