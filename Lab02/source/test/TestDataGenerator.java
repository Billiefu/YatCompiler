package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.Agenda;
import agenda.bll.user.User;

/**
 * 测试数据生成器
 * 
 * <p>
 * 该类用于生成测试数据实例，提供批量创建用户对象和会议对象的方法。<br>
 * 主要功能包括：<br>
 * 1. 生成指定数量的用户数据。<br>
 * 2. 生成指定数量的会议数据。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月2日
 * @lastUpdated 2025年4月2日
 * 
 */
public class TestDataGenerator {
    
    /**
     * 生成指定数量的用户集合
     * 
     * <p>
     * 每个用户的名称格式为 "User_X"，密码格式为 "Code_X"（X 为索引值）。<br>
     * 生成的用户集合采用 {@code TreeSet} 存储，保证用户按名称排序。
     * </p>
     * 
     * @param count 要生成的用户数量
     * @return 包含指定数量用户的 {@code Set<User>} 集合
     */
    public static Set<User> generateUsers(int count) {
        Set<User> users = new TreeSet<User>(); // 创建存储用户的 TreeSet
        for (int i = 0; i < count; i++) {
            users.add(new User("User_" + i, "Code_" + i)); // 生成用户并添加到集合
        }
        return users; // 返回用户集合
    }

    /**
     * 生成指定数量的会议对象集合
     * 
     * <p>
     * 每个会议包含以下信息：<br>
     * - 组织者：名称格式为 "Organizer_X"，密码格式为 "Code_X"。<br>
     * - 参与者：名称格式为 "Attendee_X"，密码格式为 "Code_X"。<br>
     * - 开始时间：当前时间加上 X 小时，格式化为中国本地时间格式。<br>
     * - 结束时间：开始时间加 1 小时并减去 1 秒，保证时间间隔为 1 小时。<br>
     * - 会议标签：格式为 "Label_X"。<br>
     * - 会议 ID：设置为 X（索引值）。<br>
     * 生成的会议集合采用 {@code TreeSet} 存储，以便按默认排序规则排序。
     * </p>
     * 
     * @param count 要生成的会议数量
     * @return 包含指定数量会议的 {@code Set<Agenda>} 集合
     */
    public static Set<Agenda> generateAgendas(int count) {
        Set<Agenda> agendas = new TreeSet<Agenda>(); // 创建存储会议的 TreeSet
        for (int i = 0; i < count; i++) {
            // 创建会议组织者
            User organizer = new User("Organizer_" + i, "Code_" + i);
            // 创建会议参与者
            User attendee = new User("Attendee_" + i, "Code_" + i);
            Set<User> attendees = new TreeSet<User>();
            attendees.add(attendee); // 将参与者添加到集合
            
            // 创建时间格式化工具，使用中国本地格式
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                                           .withLocale(Locale.CHINA);
            // 计算会议开始时间：当前时间 + i 小时
            String start_time = formatter.format(LocalDateTime.now().plusHours(i));
            // 计算会议结束时间：开始时间 + 1 小时 - 1 秒
            String end_time = formatter.format(LocalDateTime.now().plusHours(i + 1).minusSeconds(1));

            // 创建会议对象
            Agenda agenda = new Agenda(organizer, attendees, start_time, end_time, "Label_" + i);
            agenda.setId((long) i); // 设置会议 ID 为当前索引
            
            agendas.add(agenda); // 将会议对象添加到集合
        }
        return agendas; // 返回会议集合
    }
    
}
