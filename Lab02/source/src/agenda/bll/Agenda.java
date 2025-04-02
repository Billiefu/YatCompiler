package agenda.bll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.user.User;
import agenda.dml.AgendaManagement;

/**
 * 会议日程类
 * 
 * <p>
 * 该类用于抽象化表示会议日程，并实现 Comparable 接口进行比较
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月6日
 * @lastUpdated 2025年3月28日
 * 
 */
public class Agenda implements Comparable<Agenda> {
    
	/** 会议ID */
    private long id;
    
    /** 组织者 */
    private User organizer;
    
    /** 参会者集合 */
    private Set<User> attendees;
    
    /** 会议开始时间 */
    private LocalDateTime start_time;
    
    /** 会议结束时间 */
    private LocalDateTime end_time;
    
    /** 会议标签 */
    private String label;

    /**
     * 会议构造方法
     * 
     * <p>
     * 该方法用于构造一个全新的会议<br>
     * 会议ID会在成功添加进系统后再赋值
     * </p>
     * 
     * @param organizer 会议组织者
     * @param attendees 参会人员集合
     * @param start_time 会议开始时间（字符串格式）
     * @param end_time 会议结束时间（字符串格式）
     * @param label 会议标签
     * 
     */
    public Agenda(User organizer, Set<User> attendees, String start_time, String end_time, String label) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        this.id = -1;	// 会议 ID 设为默认值 -1
        this.organizer = organizer;
        this.attendees = attendees;
        this.start_time = LocalDateTime.parse(start_time, formatter);	// 解析时间字符串
        this.end_time = LocalDateTime.parse(end_time, formatter);
        this.label = label;
    }

    /**
     * 会议构造方法
     * 
     * <p>
     * 该方法用于构造一个已存在的会议<br>
     * 已存在的会议已经赋予了会议ID，需一同传入至该会议当中
     * </p>
     * 
     * @param id 会议ID
     * @param organizer 会议组织者
     * @param attendees 参会人员集合
     * @param start_time 会议开始时间（字符串格式）
     * @param end_time 会议结束时间（字符串格式）
     * @param label 会议标签
     * 
     */
    public Agenda(long id, User organizer, Set<User> attendees, String start_time, String end_time, String label) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        this.id = id;
        this.organizer = organizer;
        this.attendees = attendees;
        this.start_time = LocalDateTime.parse(start_time, formatter);	// 解析时间字符串
        this.end_time = LocalDateTime.parse(end_time, formatter);
        this.label = label;
    }

    /**
     * 设置会议 ID
     * 
     * 从会议管理类中获取新的 ID
     */
    public void setId() {
        this.id = AgendaManagement.getInstance().setAgendaID();
    }
    
    /**
     * 设置会议 ID
     * 
     * 自定义设置会议 ID（仅在测试时调用）
     * 
     * @param id 自定义会议ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取会议 ID
     * 
     * @return 会议 ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * 获取会议组织者姓名
     * 
     * @return 组织者姓名
     */
    public String getOrganizer() {
        return this.organizer.getName();
    }
    
    /**
     * 获取所有参会者的集合
     * 
     * @return 参会者集合
     */
    public Set<User> getAttendees() {
        return this.attendees;
    }

    /**
     * 获取所有参会者的姓名集合
     * 
     * @return 参会者姓名集合
     */
    public Set<String> getAttendeesFormat() {
        Set<String> attendees = new TreeSet<String>();
        for (User user : this.attendees) {
            attendees.add(user.getName());
        }
        return attendees;
    }

    /**
     * 获取会议开始时间（时间戳格式）
     * 
     * @return 会议开始时间的时间戳
     */
    public long getStartTime() {
        return this.start_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取格式化后的会议开始时间
     * 
     * @return 会议开始时间（格式化字符串）
     */
    public String getStartTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        return formatter.format(this.start_time);
    }

    /**
     * 获取会议结束时间（时间戳格式）
     * 
     * @return 会议结束时间的时间戳
     */
    public long getEndTime() {
        return this.end_time.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取格式化后的会议结束时间
     * 
     * @return 会议结束时间（格式化字符串）
     */
    public String getEndTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        return formatter.format(this.end_time);
    }

    /**
     * 获取会议标签
     * 
     * @return 会议标签
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 更改会议组织者
     * 
     * @param organizer 新组织者
     */
    public void changeOrganizer(User organizer) {
        User FormerOrganizer = this.organizer;
        // 在参会者列表中查找新组织者
        for (User user : this.attendees) {
            if (user.getName().equals(organizer.getName())) {
                this.organizer = user;
                break;
            }
        }
        
        // 原组织者加入参会者列表
        this.attendees.add(FormerOrganizer);
        
        // 新组织者离开参会者列表
        this.attendees.remove(organizer);
    }

    /**
     * 添加参会者
     * 
     * @param user 要添加的参会者
     */
    public void addAttendee(User user) {
        this.attendees.add(user);
    }

    /**
     * 删除参会者
     * 
     * @param target 要删除的参会者
     */
    public void deleteAttendee(User target) {
        for (User user : this.attendees) {
            if (user.getName().equals(target.getName())) {
                this.attendees.remove(target);
            }
        }
    }

    /**
     * 更改会议标签
     * 
     * @param label 新的会议标签
     */
    public void changeLabel(String label) {
        this.label = label;
    }

    /**
     * 比较会议顺序
     * 
     * <p>
     * 比较两个会议对象的时间先后顺序<br>
     * 如果时间相同，则比较会议 ID
     * </p>
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
