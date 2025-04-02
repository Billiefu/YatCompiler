package agenda.bll.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.Agenda;
import agenda.dml.AgendaManagement;
import agenda.dml.UserManagement;

/**
 * 会议管理员类
 * 
 * <p>
 * 继承自 User 类，用于管理该用户的会议信息。<br>
 * 该类提供管理会议的功能，包括创建、删除、修改会议等操作。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Manager extends User {
    
    /** 该用户的会议集合 */
    private Set<Agenda> agendas;

    /**
     * 构造方法
     * 
     * <p>
     * 基于用户名和密码创建 Manager 对象，并初始化会议集合
     * </p>
     *
     * @param name 用户名
     * @param code 用户密码
     * 
     */
    public Manager(String name, String code) {
        super(name, code);
        this.agendas = new TreeSet<Agenda>();
    }

    /**
     * 构造方法
     * 
     * <p>
     * 通过用户名查找已有用户，并初始化该用户的会议集合
     * </p>
     *
     * @param name 用户名
     * 
     */
    public Manager(String name) {
        super(UserManagement.getInstance().searchUser(name));
        AgendaManagement agendamanagement = AgendaManagement.getInstance();
        this.agendas = agendamanagement.searchAgenda(getName());
    }

    /**
     * 修改用户名
     *
     * @param name 新用户名
     * @return 是否修改成功
     * @throws IOException 可能抛出的异常
     */
    public boolean changeName(String name) throws IOException {
        return UserManagement.getInstance().changeName(this.getName(), name);
    }

    /**
     * 修改用户密码
     *
     * @param code 新密码
     * @throws IOException 可能抛出的异常
     */
    public void changeCode(String code) throws IOException {
        UserManagement.getInstance().changeCode(this.getName(), code);
    }

    /**
     * 获取该用户的所有会议
     *
     * @return 会议集合
     */
    public Set<Agenda> getAgenda() {
        return this.agendas;
    }

    /**
     * 获取格式化后的会议信息
     *
     * @return 会议信息集合，每个元素包含会议的详细信息
     */
    public Set<String> getAgendaFormat() {
        Set<String> agendas = new TreeSet<String>();
        for (Agenda agenda : this.agendas) {
            // 会议信息按格式存储
            agendas.add(agenda.getId() + "\t" 
                      + agenda.getOrganizer() + "\t"
                      + agenda.getStartTime() + "\t" 
                      + agenda.getEndTime() + "\t" 
                      + agenda.getLabel());
        }
        return agendas;
    }

    /**
     * 通过会议 ID 查找特定会议
     *
     * @param id 会议 ID
     * @return 找到的会议对象，如果不存在则返回 null
     */
    public Agenda getAgenda(long id) {
        for (Agenda agenda : this.agendas) {
            if (agenda.getId() == id) {
                return agenda;
            }
        }
        return null;
    }

    /**
     * 创建新的会议
     *
     * @param attendee 参与者用户名
     * @param start_time 开始时间（字符串格式）
     * @param end_time 结束时间（字符串格式）
     * @param label 会议标签
     * @return 是否创建成功
     * @throws IOException 可能抛出的异常
     */
    public boolean createAgenda(String attendee, String start_time, String end_time, String label) throws IOException {
        // 创建参与者集合
        Set<User> attendees = new TreeSet<User>();
        attendees.add(UserManagement.getInstance().searchUser(attendee));

        // 创建新的会议对象
        Agenda agenda = new Agenda(new User(this.getName(), this.getCode()), attendees, start_time, end_time, label);
        
        // 设置会议 ID
        agenda.setId();

        // 获取会议管理实例，并添加会议
        AgendaManagement agendamanagement = AgendaManagement.getInstance();
        if (!agendamanagement.addAgenda(agenda)) {
            return false;
        }

        // 将新会议添加到本地管理的会议集合
        this.agendas.add(agenda);
        return true;
    }

    /**
     * 删除指定 ID 的会议
     *
     * @param id 会议 ID
     * @throws IOException 可能抛出的异常
     */
    public void deleteAgenda(long id) throws IOException {
        AgendaManagement.getInstance().deleteAgenda(this.getName(), id);
    }

    /**
     * 清空该用户的所有会议
     *
     * @throws IOException 可能抛出的异常
     */
    public void clearAgenda() throws IOException {
        AgendaManagement agendamanagement = AgendaManagement.getInstance();
        agendamanagement.clearAgenda(this.getName());
        this.agendas = new HashSet<Agenda>(); // 重新初始化会议集合
    }

    /**
     * 按时间范围搜索会议
     *
     * @param start_time_format 开始时间（字符串格式）
     * @param end_time_format 结束时间（字符串格式）
     * @return 符合时间范围的会议集合
     */
    public Set<Agenda> searchAgenda(String start_time_format, String end_time_format) {
        // 定义时间格式解析器
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        
        // 解析起始时间和结束时间为毫秒数
        long start_time = LocalDateTime.parse(start_time_format, formatter)
                .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        long end_time = LocalDateTime.parse(end_time_format, formatter)
                .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 搜索符合时间范围的会议
        return AgendaManagement.getInstance().searchAgenda(this.getName(), start_time, end_time);
    }

    /**
     * 按会议 ID 搜索会议
     *
     * @param id 会议 ID
     * @return 符合 ID 的会议集合
     */
    public Set<Agenda> searchAgenda(long id) {
        return AgendaManagement.getInstance().searchAgenda(this.getName(), id);
    }

    /**
     * 获取该用户的所有会议
     *
     * @return 会议集合
     */
    public Set<Agenda> searchAgenda() {
        return AgendaManagement.getInstance().searchAgenda(this.getName());
    }

    /**
     * 向指定 ID 的会议添加参与者
     *
     * @param id 会议 ID
     * @param name 参与者用户名
     * @return 操作结果状态
     * @throws IOException 可能抛出的异常
     */
    public int addAttendee(long id, String name) throws IOException {
    	User attendee = UserManagement.getInstance().searchUser(name);
    	if(attendee == null) {
    		return -3;
    	}
        return AgendaManagement.getInstance().addAttendee(this.getName(), id, attendee);
    }

    /**
     * 从指定 ID 的会议中移除参与者
     *
     * @param id 会议 ID
     * @param name 参与者用户名
     * @throws IOException 可能抛出的异常
     * @return 同 AgendaManagement 类的 DeleteAttendee 函数
     */
    public boolean deleteAttendee(long id, String name) throws IOException {
        return AgendaManagement.getInstance().deleteAttendee(this.getName(), id, name);
    }
    
    /**
     * 更换指定 ID 的会议的组织者
     *
     * @param id 会议 ID
     * @param name 新组织者用户名
     * @throws IOException 可能抛出的异常
     * @return 同 AgendaManagement 类的 ChangeOrganizer 函数
     */
    public int changeOrganizer(long id, String name) throws IOException {
    	User attendee = UserManagement.getInstance().searchUser(name);
    	if(attendee == null) {
    		return -2;
    	}
    	return AgendaManagement.getInstance().changeOrganizer(this.getName(), id, name);
    }
    
}
