package agenda.ui.command;

import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * addattendee类
 * 
 * <p>
 * 该类用于处理添加与会人的命令。实现了Logger接口，执行添加与会人功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Addattendee implements Loger {

    /**
     * 执行添加与会人的命令
     * 
     * <p>
     * 根据传入的参数，验证用户身份，获取用户对象，然后通过用户对象调用添加与会人的方法。
     * </p>
     * 
     * @param params 命令参数，包含用户名、密码、会议ID和与会人
     * @throws Exception 可能抛出的异常
     * 
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 检查参数个数是否正确
        if (params.length != 4) {
            System.out.println("Error: Usage - addattendee [user] [code] [meetingId] [attendee]");
            return;
        }

        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return; // 用户验证失败，返回

        // 强制转换为管理员对象
        Manager manager = (Manager) user;

        // 调用添加与会人方法
        int result = manager.addAttendee(Long.parseLong(params[2]), params[3]);

        // 根据返回值打印不同的提示信息
        switch (result) {
            case 0:
                System.out.println("Success: Attendee added");
                break;
            case -1:
                System.out.println("Error: No permission");
                break;
            case -2:
                System.out.println("Error: Agenda not found");
                break;
            case -3:
            	System.out.println("Error: User not found");
        }
    }

}
