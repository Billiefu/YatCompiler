package agenda.ui.command;

/**
 * Other接口
 * 
 * <p>
 * 该接口继承自Command接口，作为不需要进行登录验证的命令接口。<br>
 * 实现该接口的类可以直接执行命令，无需进行用户身份验证。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public interface Other extends Command {

    /**
     * 执行命令的方法
     * 
     * <p>
     * 由于该方法为接口的方法，具体实现会由继承该接口的类来提供。<br>
     * 在执行命令时，不需要进行用户身份验证。
     * </p>
     * 
     * @param params 命令参数
     * @throws Exception 可能抛出的异常
     * 
     */
    @Override
    public abstract void exec(String[] params) throws Exception;
    
}
