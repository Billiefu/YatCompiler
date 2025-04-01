package agenda.ui.command;

/**
 * help类
 * 
 * <p>
 * 该类用于显示所有可用命令的帮助信息。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Help implements Other {

    /**
     * 执行显示帮助信息的命令
     * 
     * @param params 命令参数（本方法不需要参数）
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        System.out.println();

        // 输出帮助信息，列出所有支持的命令
        System.out.println("Help - View all available commands");
        System.out.println("Usage - help");
        System.out.println("");

        // 注册新用户
        System.out.println("Register - Register a new user");
        System.out.println("Usage - register [userName] [password]");
        System.out.println("");

        // 取消用户账户
        System.out.println("Cancel - Cancel an existing user");
        System.out.println("Usage - cancel [userName] [password]");
        System.out.println("");

        // 修改用户名
        System.out.println("Rename - Rename the current user");
        System.out.println("Usage - rename [userName] [password] [newName]");
        System.out.println("");

        // 重置密码
        System.out.println("Recode - Reset the current user's password");
        System.out.println("Usage - recode [userName] [password] [newCode]");
        System.out.println("");

        // 添加会议
        System.out.println("AddAgenda - Add a agenda");
        System.out.println("Usage - addagenda [userName] [password] [attendee] [starttime] [endtime] [label]");
        System.out.println("");

        // 删除指定会议
        System.out.println("DeleteAgenda - Delete a specified agenda");
        System.out.println("Usage - deleteagenda [userName] [password] [agendaID]");
        System.out.println("");

        // 清空当前用户主持的所有会议
        System.out.println("ClearAgenda - Clear all agendas hosted by the current user");
        System.out.println("Usage - clearagenda [userName] [password]");
        System.out.println("");

        // 查询会议
        System.out.println("QueryAgenda - Query a specified agenda");
        System.out.println("Usage - queryagenda [userName] [password] [starttime] [endtime]");
        System.out.println("        queryagenda [userName] [password] [agendaID]");
        System.out.println("        queryagenda [userName] [password]");
        System.out.println("");

        // 添加与会者到指定会议
        System.out.println("AddAttendee - Add a attendee to a specified agenda");
        System.out.println("Usage - addattendee [userName] [password] [agendaID] [attendee]");
        System.out.println("");

        // 从指定会议中移除与会者
        System.out.println("DeleteAttendee - Remove a attendee from a specified agenda");
        System.out.println("Usage - deleteattendee [userName] [password] [agendaID] [attendee]");
        System.out.println("");
        
        // 从指定会议中转移组织者
        System.out.println("ChangeOrganizer - Change an organizer from a specified agenda");
        System.out.println("Usage - changeorganizer [userName] [password] [agendaID] [organizer]");
        System.out.println("");

        // 批量执行命令
        System.out.println("Batch - Execute a batch of commands from a text file");
        System.out.println("Usage - batch [fileName]");
        System.out.println("");

        // 查看开发者信息
        System.out.println("Developer - Check the developers of this system");
        System.out.println("Usage - developer");
        System.out.println();

        // 退出系统
        System.out.println("Quit - Exit the system");
        System.out.println("Usage - quit");
        System.out.println("");
    }

}
