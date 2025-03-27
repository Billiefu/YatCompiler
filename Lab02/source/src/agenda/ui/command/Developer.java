package agenda.ui.command;

/**
 * Developer类
 * <p>
 * 该类用于显示开发者信息。
 * </p>
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月27日
 */
public class Developer implements Other {

    /**
     * 执行显示开发者信息的命令
     * 
     * @param params 命令参数（本方法不需要参数）
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 打印开发者信息
        System.out.println();
        System.out.println("==============================");
        System.out.println();
        System.out.println("Developer List");
        System.out.println();
        System.out.println("Developer: Fu Tszkok");
        System.out.println("Supervisor: Professor Li Wenjun");
        System.out.println();
        System.out.println("==============================");
        System.out.println();
        System.out.println("Thank you to everyone who contributed to this project!");
        System.out.println("Want to know more details? Search my github!");
        System.out.println("https://github.com/Billiefu/YatCompiler");
        System.out.println();
    }

}

