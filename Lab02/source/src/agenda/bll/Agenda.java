package agenda.bll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import agenda.bll.user.User;
import agenda.dml.AgendaManagement;

/**
 * 会议日程类
 * <p>
 * 该类用于抽象化表示会议日程，并实现 Comparable 接口进行比较
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
public class Agenda implements Comparable<Agenda> {
    
    private long id; // 会议ID
    private User organizer; // 组织者
    private Set<User> attendees; // 参会者集合
    private LocalDateTime start_time; // 会议开始时间
    private LocalDateTime end_time; // 会议结束时间
    private String label; // 会议标签

    /**
     * 会议构造方法（无 ID 版本，ID 需后续赋值）
     * 
     * @param organizer 会议组织者
     * @param attendees 参会人员集合
     * @param start_time 会议开始时间（字符串格式）
     * @param end_time 会议结束时间（字符串格式）
     * @param label 会议标签
     */
    public Agenda(User organizer, Set<User> attendees, String start_time, String end_time, String label) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        this.id = -1; // 会议 ID 设为默认值 -1
        this.organizer = organizer;
        this.attendees = attendees;
        this.start_time = LocalDateTime.parse(start_time, formatter); // 解析时间字符串
        this.end_time = LocalDateTime.parse(end_time, formatter);
        this.label = label;
    }

    /**
     * 会议构造方法（带 ID 版本）
     * 
     * @param id 会议ID
     * @param organizer 会议组织者
     * @param attendees 参会人员集合
     * @param start_time 会议开始时间（字符串格式）
     * @param end_time 会议结束时间（字符串格式）
     * @param label 会议标签
     */
    public Agenda(long id, User organizer, Set<User> attendees, String start_time, String end_time, String label) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        this.id = id;
        this.organizer = organizer;
        this.attendees = attendees;
        this.start_time = LocalDateTime.parse(start_time, formatter); // 解析时间字符串
        this.end_time = LocalDateTime.parse(end_time, formatter);
        this.label = label;
    }

    /**
     * 设置会议 ID（从 AgendaManagement 获取新的 ID）
     */
    public void SetId() {
        this.id = AgendaManagement.getInstance().SetAgendaID();
    }

    /**
     * 获取会议 ID
     * 
     * @return 会议 ID
     */
    public long GetId() {
        return this.id;
    }

    /**
     * 获取会议组织者姓名
     * 
     * @return 组织者姓名
     */
    public String GetOrganizer() {
        return this.organizer.GetName();
    }
    
    /**
     * 获取所有参会者的集合
     * 
     * @return 参会者集合
     */
    public Set<User> GetAttendees() {
        return this.attendees;
    }

    /**
     * 获取所有参会者的姓名集合
     * 
     * @return 参会者姓名集合
     */
    public Set<String> GetAttendeesFormat() {
        Set<String> attendees = new HashSet<String>();
        for (User user : this.attendees) {
            attendees.add(user.GetName());
        }
        return attendees;
    }

    /**
     * 获取会议开始时间（时间戳格式）
     * 
     * @return 会议开始时间的时间戳
     */
    public long GetStartTime() {
        return this.start_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取格式化后的会议开始时间
     * 
     * @return 会议开始时间（格式化字符串）
     */
    public String GetStartTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        return formatter.format(this.start_time);
    }

    /**
     * 获取会议结束时间（时间戳格式）
     * 
     * @return 会议结束时间的时间戳
     */
    public long GetEndTime() {
        return this.end_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取格式化后的会议结束时间
     * 
     * @return 会议结束时间（格式化字符串）
     */
    public String GetEndTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        return formatter.format(this.end_time);
    }

    /**
     * 获取会议标签
     * 
     * @return 会议标签
     */
    public String GetLabel() {
        return this.label;
    }

    /**
     * 更改会议组织者
     * 
     * @param organizer 新组织者
     */
    public void ChangeOrganizer(User organizer) {
        User FormerOrganizer = this.organizer;
        // 在参会者列表中查找新组织者
        for (User user : this.attendees) {
            if (user.GetName().equals(organizer.GetName())) {
                this.organizer = user;
                break;
            }
        }
        // 原组织者加入参会者列表
        this.attendees.add(FormerOrganizer);
    }

    /**
     * 添加参会者
     * 
     * @param user 要添加的参会者
     */
    public void AddAttendee(User user) {
        this.attendees.add(user);
    }

    /**
     * 删除参会者
     * 
     * @param target 要删除的参会者
     */
    public void DeleteAttendee(User target) {
        for (User user : this.attendees) {
            if (user.GetName().equals(target.GetName())) {
                this.attendees.remove(target);
            }
        }
    }

    /**
     * 更改会议时间
     * 
     * @param start_time 新的会议开始时间
     * @param end_time 新的会议结束时间
     */
    public void ChangeTime(LocalDateTime start_time, LocalDateTime end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    /**
     * 更改会议标签
     * 
     * @param label 新的会议标签
     */
    public void ChangeLabel(String label) {
        this.label = label;
    }

    /**
     * 比较两个会议对象的时间先后顺序
     * 如果时间相同，则比较会议 ID
     * 
     * @param agenda 另一个会议对象
     * @return 比较结果（负值表示当前会议时间更早，0 表示相同，正值表示当前会议时间更晚）
     */
    @Override
    public int compareTo(Agenda agenda) {
        long thisTime = this.start_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        long otherTime = agenda.start_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        return thisTime == otherTime ? Long.compare(this.id, agenda.id) : Long.compare(thisTime, otherTime);
    }
}
