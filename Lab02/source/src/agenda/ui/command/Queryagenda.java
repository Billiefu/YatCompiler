package agenda.ui.command;

import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.Agenda;
import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * queryagenda类
 * 
 * <p>
 * 该类用于查询和显示指定的会议信息，包括查询所有会议、查询特定时间段内的会议以及查询特定会议。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月27日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Queryagenda implements Loger {

    /**
     * 执行查询会议的命令
     * 
     * @param params 命令参数：<br>
     *               queryagenda [userName] [password] [starttime] [endtime] —— 查询某一时间段内的会议；<br>
     *               queryagenda [userName] [password] [agendaID] —— 查询某个特定会议；<br>
     *               queryagenda [userName] [password] —— 查询所有会议
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void exec(String[] params) throws Exception {
        // 参数数量检查
        if (params.length != 4 && params.length != 3 && params.length != 2) {
            System.out.println("Error: Usage - queryagenda [userName] [password] [starttime] [endtime]");
            System.out.println("               queryagenda [userName] [password] [agendaID]");
            System.out.println("               queryagenda [userName] [password]");
            return;
        }

        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;

        // 获取用户管理权限
        Manager manager = (Manager) user;

        // 创建一个存储会议的集合
        Set<Agenda> agendas = new TreeSet<>();

        // 查询某一时间段内的会议
        if (params.length == 4) {
            params[2] = params[2].replace('_', ' '); // 替换时间格式中的下划线为空格
            params[3] = params[3].replace('_', ' ');
            try {
                agendas = manager.searchAgenda(params[2], params[3]);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Format of time - XXXX年XX月XX日_XX:XX:XX");
                return;
            }
        }
        // 查询某个特定会议
        else if (params.length == 3) {
            try {
                agendas = manager.searchAgenda(Long.parseLong(params[2]));
            } catch (NumberFormatException e) {
                System.out.println("Error: Usage - queryagenda [userName] [password] [agendaID]");
                return;
            }
        }
        // 查询所有会议
        else {
            agendas = manager.searchAgenda();
        }

        // 格式化输出会议信息
        StringBuffer string = new StringBuffer("\r\n");
        for (Agenda agenda : agendas) {
            string.append("=============================================\r\n");
            string.append("Agenda ID: " + agenda.getId() + "\r\n");
            string.append("Organizer: " + agenda.getOrganizer() + "\r\n");
            string.append("Attendees: " + agenda.getAttendeesFormat() + "\r\n");
            string.append("Start Time: " + agenda.getStartTimeFormat() + "\r\n");
            string.append("End Time: " + agenda.getEndTimeFormat() + "\r\n");
            string.append("Label: " + agenda.getLabel() + "\r\n\r\n");
        }

        // 输出查询到的会议信息
        System.out.print(string.toString());
    }

}
