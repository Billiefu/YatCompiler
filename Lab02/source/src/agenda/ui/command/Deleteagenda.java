package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * deleteagenda类
 * 
 * <p>
 * 该类用于删除指定的会议。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Deleteagenda implements Loger {

    /**
     * 执行删除会议的命令
     * 
     * @param params 命令参数，包含用户名、密码和会议ID
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数数量是否正确
        if (params.length != 3) {
            System.out.println("Error: Usage - deleteagenda [userName] [password] [agendaID]");
            return;
        }
        
        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;
        
        // 将用户对象转换为Manager类型，并执行删除会议操作
        Manager manager = (Manager) user;
        manager.deleteAgenda(Long.parseLong(params[2]));
        
        System.out.println("Success: Agenda deleted");
    }

}
