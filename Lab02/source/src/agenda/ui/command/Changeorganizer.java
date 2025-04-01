package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * changeorganizer 类
 * 
 * <p>
 * 该类用于更改指定会议的组织者，需要进行用户身份验证后才能执行操作。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月28日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Changeorganizer implements Loger {

    /**
     * 执行更改会议组织者的命令
     * 
     * <p>
     * 该方法首先验证用户身份，然后尝试更改指定会议的组织者，并根据执行结果输出相应信息。
     * </p>
     * 
     * @param params 命令参数，格式为 [userName] [password] [agendaID] [organizer]
     * @throws Exception 可能抛出的异常
     * 
     */
    @Override
    public void exec(String[] params) throws Exception {

        // 检查参数的数量是否正确，必须为4个
        if (params.length != 4) {
            System.out.println("Error: Usage - changeorganizer [userName] [password] [agendaID] [organizer]");
            return; // 参数不正确，终止执行
        }

        // 验证用户身份，若验证失败则返回
        User user = checkLogin(params[0], params[1]); 
        if (user == null) return;

        // 将用户对象转换为Manager类型，以调用更改组织者的方法
        Manager manager = (Manager) user;
        
        // 尝试更改会议组织者
        int success = manager.changeOrganizer(Long.parseLong(params[2]), params[3]);

        // 根据返回值判断操作结果并输出相应信息
        switch (success) {
            case 0:
                System.out.println("Success: Organizer changed"); // 成功更改组织者
                break;
            case -1:
                System.out.println("Error: Agenda not found"); // 会议未找到
                break;
            case -2:
                System.out.println("Error: User not found"); // 新组织者用户未找到
                break;
        }
    }
    
}
