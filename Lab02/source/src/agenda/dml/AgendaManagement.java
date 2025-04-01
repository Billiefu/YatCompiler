package agenda.dml;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.Agenda;
import agenda.bll.user.User;
import agenda.dal.AgendaIO;
import agenda.dal.AgendaIdGenerator;

/**
 * 会议管理类
 * 
 * <p>
 * 用于管理所有已有的会议信息。
 * 该类采用单例模式，提供会议的添加、删除、修改和查询功能。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年3月15日
 * @lastUpdated 2025年3月28日
 * 
 */
public class AgendaManagement {

	/** 单例实例（饿汉式） */
    private static final AgendaManagement Instance = new AgendaManagement();

    /** 存储所有会议的集合 */
    private Set<Agenda> agendas = new TreeSet<Agenda>();

    /**
     * 私有构造方法（单例模式）
     * 
     * 初始化会议管理系统，从存储中读取会议数据
     */
    private AgendaManagement() {
        try {
            // 从存储中读取会议数据
            Set<String> agendas = AgendaIO.input();
            UserManagement usermanagement = UserManagement.getInstance();

            // 解析会议数据
            for (String result : agendas) {
                if (result.matches("")) {
                    break;
                }

                // 按行拆分会议数据
                String regexa = "\r\n";
                String regexb = ",";
                String resulta[] = result.split(regexa);
                String resultb[][] = new String[resulta.length][3];

                for (int i = 0; i < resulta.length; i++) {
                    resultb[i] = resulta[i].split(regexb);
                }

                // 获取会议 ID
                long id = Long.parseLong(resultb[0][0]);

                // 获取会议组织者
                User organizer = usermanagement.SearchUser(resultb[1][0]);
                if (organizer == null) {
                    continue;
                }

                // 获取会议参与者
                Set<User> attendees = new TreeSet<User>();
                for (int i = 0; i < resultb[2].length; i++) {
                    User attendee = usermanagement.SearchUser(resultb[2][i]);
                    if (attendee == null) {
                        continue;
                    }
                    attendees.add(attendee);
                }

                // 获取会议时间与标签
                String start_time = resultb[3][0];
                String end_time = resultb[4][0];
                String label = resultb[5][0];

                // 创建会议对象并添加到集合
                Agenda agenda = new Agenda(id, organizer, attendees, start_time, end_time, label);
                this.agendas.add(agenda);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 AgendaManagement 单例实例。
     *
     * @return AgendaManagement 实例
     */
    public static AgendaManagement getInstance() {
        return Instance;
    }

    /**
     * 保存当前会议信息到存储中。
     *
     * @throws IOException 可能抛出的异常
     */
    private void SaveAgenda() throws IOException {
        // 删除存储的会议文件夹
        AgendaIO.deleteDirectory(new File("c:" + File.separator + "Java" + File.separator + "agenda"));
        
        // 逐个写入会议数据
        for (Agenda agenda : this.agendas) {
            AgendaIO.output(agenda);
        }
    }

    /**
     * 添加会议
     *
     * @param agenda 需要添加的会议对象
     * @return 添加结果：
     *         true - 添加成功；
     *         false - 会议时间冲突
     * @throws IOException 可能抛出的异常
     */
    public boolean AddAgenda(Agenda agenda) throws IOException {
        for (Agenda other : this.agendas) {
            // 检查会议时间是否冲突
            if (other.getOrganizer().matches(agenda.getOrganizer()) &&
                ((agenda.getStartTime() >= agenda.getEndTime()) ||
                (agenda.getEndTime() >= other.getStartTime() && agenda.getEndTime() <= other.getEndTime()) || 
                (agenda.getStartTime() <= other.getEndTime() && agenda.getStartTime() >= other.getStartTime()))) {
            	AgendaIdGenerator.getInstance().resetId();
                return false;
            }
        }

        // 添加会议并保存
        this.agendas.add(agenda);
        this.SaveAgenda();
        return true;
    }

    /**
     * 清空指定用户的所有会议
     *
     * @param name 需要清空会议的用户名
     * @throws IOException 可能抛出的异常
     */
    public void ClearAgenda(String name) throws IOException {
        Set<Agenda> toDelete = new TreeSet<Agenda>();
        for (Agenda agenda : this.agendas) {
            if (agenda.getOrganizer().matches(name)) {
                toDelete.add(agenda);
            }
        }
        toDelete.forEach(this.agendas::remove);
        this.SaveAgenda();
    }

    /**
     * 删除指定用户的某个会议
     *
     * @param name 会议组织者的用户名
     * @param id 会议 ID
     * @throws IOException 可能抛出的异常
     */
    public void DeleteAgenda(String name, long id) throws IOException {
        Set<Agenda> toDelete = new TreeSet<Agenda>();
        for (Agenda agenda : this.agendas) {
            if (agenda.getOrganizer().matches(name) && agenda.getId() == id) {
                toDelete.add(agenda);
            }
        }
        toDelete.forEach(this.agendas::remove);
        this.SaveAgenda();
    }

    /**
     * 按用户名搜索会议
     *
     * @param name 会议组织者或参会者的用户名
     * @return 匹配的会议集合
     */
    public Set<Agenda> SearchAgenda(String name) {
    	// 存储匹配的会议
    	Set<Agenda> agendas = new TreeSet<Agenda>();
    	
    	// 遍历所有会议
		for(Agenda agenda : this.agendas) {
			// 如果会议组织者匹配，则添加到结果集合
			if(agenda.getOrganizer().matches(name)) {
				agendas.add(agenda);
			}
			
			// 遍历参会者列表，检查是否匹配
			Set<User> attendees = agenda.getAttendees();
			for(User attendee : attendees) {
				if(attendee.getName().matches(name)) {
					agendas.add(agenda);
				}
			}
		}
		return agendas;
    }

    /**
     * 按用户名和会议 ID 搜索会议
     *
     * @param name 会议组织者或参会者的用户名
     * @param id   会议 ID
     * @return 匹配的会议集合
     */
    public Set<Agenda> SearchAgenda(String name, long id) {
        // 存储匹配的会议
        Set<Agenda> agendas = new TreeSet<Agenda>();

        // 遍历所有会议
        for (Agenda agenda : this.agendas) {
            // 如果会议组织者匹配且 ID 相同，则添加到结果集合
            if (agenda.getOrganizer().matches(name) && agenda.getId() == id) {
                agendas.add(agenda);
            }

            // 遍历参会者列表，检查是否匹配
            Set<User> attendees = agenda.getAttendees();
            for (User attendee : attendees) {
                if (attendee.getName().matches(name) && agenda.getId() == id) {
                    agendas.add(agenda);
                    break; // 找到匹配的参会者后即可跳出循环
                }
            }
        }
        return agendas;
    }

    /**
     * 按用户名和时间范围搜索会议
     *
     * @param name       会议组织者或参会者的用户名
     * @param start_time 会议开始时间
     * @param end_time   会议结束时间
     * @return 匹配的会议集合
     */
    public Set<Agenda> SearchAgenda(String name, long start_time, long end_time) {
        // 存储匹配的会议
        Set<Agenda> agendas = new TreeSet<Agenda>();

        // 遍历所有会议
        for (Agenda agenda : this.agendas) {
            // 如果会议组织者匹配，且会议时间在范围内，则添加到结果集合
            if (agenda.getOrganizer().matches(name) &&
                agenda.getStartTime() >= start_time && agenda.getEndTime() <= end_time) {
                agendas.add(agenda);
            }

            // 遍历参会者列表，检查是否匹配
            Set<User> attendees = agenda.getAttendees();
            for (User attendee : attendees) {
                if (attendee.getName().matches(name) &&
                    agenda.getStartTime() >= start_time && agenda.getEndTime() <= end_time) {
                    agendas.add(agenda);
                    break; // 找到匹配的参会者后即可跳出循环
                }
            }
        }
        return agendas;
    }

    /**
     * 设置会议 ID
     *
     * @return 生成的会议 ID
     */
    public long SetAgendaID() {
        return AgendaIdGenerator.getInstance().nextId();
    }

    /**
     * 添加参会者
     *
     * @param name 会议组织者
     * @param id 会议 ID
     * @param attendee 新增的参会者
     * @return 添加结果：
     *         0 - 添加成功；
     *        -1 - 不是该会议组织者；
     *        -2 - 会议不存在；
     *        -3 - 用户不存在（该返回码在Manager中返回）
     * @throws IOException 可能抛出的异常
     */
    public int AddAttendee(String name, long id, User attendee) throws IOException {
        for (Agenda agenda : this.agendas) {
            if (agenda.getId() == id) {
                if (agenda.getOrganizer().matches(name)) {
                    agenda.addAttendee(attendee);
                    this.SaveAgenda();
                    return 0;
                }
                return -1;
            }
        }
        return -2;
    }

    /**
     * 删除参会者
     *
     * @param name 需要删除的参会者用户名
     * @throws IOException 可能抛出的异常
     */
    public void DeleteAttendee(String name) throws IOException {
        for (Agenda agenda : this.agendas) {
            agenda.deleteAttendee(UserManagement.getInstance().SearchUser(name));
        }
        this.SaveAgenda();
    }

    /**
     * 从指定会议中删除参会者
     *
     * @param name 会议组织者用户名
     * @param id 会议 ID
     * @param attendee 需要删除的参会者用户名
     * @return flag 是否删除参会者成功：
     *         true - 删除成功；
     *         false - 用户不存在
     * @throws IOException 可能抛出的异常
     */
    public boolean DeleteAttendee(String name, long id, String attendee) throws IOException {
        boolean flag = false;
    	for (Agenda agenda : this.agendas) {
            if (agenda.getId() == id && agenda.getOrganizer().matches(name)) {
                agenda.deleteAttendee(UserManagement.getInstance().SearchUser(attendee));
                flag = true;
            }
        }
        this.SaveAgenda();
        return flag;
    }
    
    /**
     * 更换指定会议的组织者
     *
     * @param name 会议组织者用户名
     * @param id 会议 ID
     * @param organizer 需要更换的组织者用户名
     * @return 更换结果：
     *         0 - 更换成功；
     *        -1 - 会议不存在；
     *        -2 - 用户不存在
     * @throws IOException 可能抛出的异常
     */
    public int ChangeOrganizer(String name, long id, String organizer) throws IOException {
    	for (Agenda agenda : this.agendas) {
    		if (agenda.getId() == id && agenda.getOrganizer().matches(name)) {
    			agenda.changeOrganizer(UserManagement.getInstance().SearchUser(organizer));
    			this.SaveAgenda();
    			return 0;
    		}
    	}
    	return -1;
    }
    
}
