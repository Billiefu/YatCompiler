package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * deleteattendee类
 * <p>
 * 该类用于删除会议的指定与会人。
 * 
 * @author 傅祉珏
 * @create 2025年3月27日
 * @lastUpdated 2025年3月28日
 */
public class Deleteattendee implements Loger {

    /**
     * 执行删除与会人的命令
     * 
     * @param params 命令参数，包含用户名、密码、会议ID和与会人姓名
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数数量是否正确
        if (params.length != 4) {
            System.out.println("Error: Usage - deleteattendee [userName] [password] [agendaID] [attendee]");
            return;
        }
        
        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;
        
        // 将用户对象转换为Manager类型，并执行删除与会人操作
        Manager manager = (Manager) user;
        boolean success = manager.deleteAttendee(Long.parseLong(params[2]), params[3]);
        
        System.out.println(success ? "Success: Attendee deleted" : "Error: Attendee not found");
    }

}
