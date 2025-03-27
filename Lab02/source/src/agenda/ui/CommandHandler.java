package agenda.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import agenda.bll.Agenda;
import agenda.bll.Register;
import agenda.bll.user.Manager;
import agenda.bll.user.User;

/**
 * 命令处理类
 * <p>
 * 用于处理用户输入的命令，并返回相应的提示信息
 * 本类采用单例模式，确保只有一个实例来处理所有用户命令
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
public class CommandHandler {
	
    /** CommandHandler 的唯一实例 */
    private static final CommandHandler Instance = new CommandHandler();
    
    /** 私有构造方法，防止外部实例化 */
    private CommandHandler() {}

    /**
     * 获取 CommandHandler 单例实例
     * 
     * @return CommandHandler 实例
     */
    public static CommandHandler getInstance() {
        return Instance;
    }
	
    /**
     * 处理 help 命令，打印所有可用命令的帮助信息
     * 
     * @param params 命令参数（无参数）
     * @throws Exception 可能抛出的异常
     */
    public void handleHelp(String[] params) throws Exception {
        System.out.println();
        
        // 输出帮助信息，列出所有支持的命令
		System.out.println("Help - View all available commands");
		System.out.println("Usage - help");
		System.out.println("");
		
		System.out.println("Register - Register a new user");
		System.out.println("Usage - register [userName] [password]");
		System.out.println("");
		
		System.out.println("Cancel - Cancel an existing user");
		System.out.println("Usage - cancel [userName] [password]");
		System.out.println("");
		
		System.out.println("Rename - Rename the current user");
		System.out.println("Usage - rename [userName] [password] [newName]");
		System.out.println("");
		
		System.out.println("Recode - Reset the current user's password");
		System.out.println("Usage - recode [userName] [password] [newCode]");
		System.out.println("");
		
		System.out.println("AddAgenda - Add a agenda");
		System.out.println("Usage - addagenda [userName] [password] [attendee] [starttime] [endtime] [label]");
		System.out.println("");
		
		System.out.println("DeleteAgenda - Delete a specified agenda");
		System.out.println("Usage - deleteagenda [userName] [password] [agendaID]");
		System.out.println("");
		
		System.out.println("ClearAgenda - Clear all agendas hosted by the current user");
		System.out.println("Usage - clearagenda [userName] [password]");
		System.out.println("");
		
		System.out.println("QueryAgenda - Query a specified agenda");
		System.out.println("Usage - queryagenda [userName] [password] [starttime] [endtime]");
        System.out.println("        queryagenda [userName] [password] [agendaID]");
        System.out.println("        queryagenda [userName] [password]");
		System.out.println("");
		
		System.out.println("AddAttendee - Add a attendee to a specified agenda");
		System.out.println("Usage - addattendee [userName] [password] [agendaID] [attendee]");
		System.out.println("");
		
		System.out.println("DeleteAttendee - Remove a attendee from a specified agenda");
		System.out.println("Usage - deleteattendee [userName] [password] [agendaID] [attendee]");
		System.out.println("");
		
		System.out.println("Batch - Execute a batch of commands from a text file");
		System.out.println("Usage - batch [fileName]");
		System.out.println("");
		
		System.out.println("Developer - Check the developers of this system");
		System.out.println("Usage - developer");
		System.out.println();
		
		System.out.println("Quit - Exit the system");
		System.out.println("Usage - quit");
		System.out.println("");
	}

    /**
     * 处理 register 命令，注册新用户
     * 
     * @param params 命令参数：[userName] [password]
     * @throws Exception 可能抛出的异常
     */
    public void handleRegister(String[] params) throws Exception {
        if (params.length != 2) {
            System.out.println("Error: Usage - register [userName] [password]");
            return;
        }
        
        // 调用 Register 类进行用户注册
        int result = Register.AddUser(params[0], params[1]);
        switch (result) {
            case -1:
                System.out.println("Error: Invalid username format");
                break;
            case -2:
                System.out.println("Error: Username already exists");
                break;
            case 0:
                System.out.println("Success: User registered");
                break;
        }
    }
    
    /**
     * 处理 cancel 命令，注销用户
     * 
     * @param params 命令参数：[userName] [password]
     * @throws Exception 可能抛出的异常
     */
    public void handleCancel(String[] params) throws Exception {
        if (params.length != 2) {
            System.out.println("Error: Usage - cancel [userName] [password]");
            return;
        }
        
        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;

        // 调用 Register 类删除用户
        Register.DeleteUser(params[0]);
        System.out.println("Success: User canceled");
    }
    
    /**
     * 处理 rename 命令，修改用户名
     * 
     * @param params 命令参数：[userName] [password] [newName]
     * @throws Exception 可能抛出的异常
     */
    public void handleRename(String[] params) throws Exception {
        if (params.length != 3) {
            System.out.println("Error: Usage - rename [userName] [password] [newName]");
            return;
        }

        // 验证用户身份
        User user = checkLogin(params[0], params[1]);
        if (user == null) return;

        // 修改用户名
        Manager manager = (Manager) user;
        if (manager.ChangeName(params[2])) {
            System.out.println("Success: User Renamed");
        } else {
            System.out.println("Error: Username already exists");
        }
    }
    
    /**
     * 处理用户更改密码的请求。
     * 
     * @param params 命令参数：[userName] [password] [newCode]
     * @throws Exception 可能抛出的异常
     */
    public void handleRecode(String[] params) throws Exception {
        if (params.length != 3) {
            System.out.println("Error: Usage - recode [userName] [password] [newCode]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user; // 将用户转换为管理者角色
        manager.ChangeCode(params[2]); // 更改密码
        System.out.println("Success: User Recoded");
    }
    
    /**
     * 处理用户添加会议的请求。
     * 
     * @param params 命令参数：[userName] [password] [attendee] [starttime] [endtime] [label]
     * @throws Exception 可能抛出的异常
     */
    public void handleAdd(String[] params) throws Exception {
        if (params.length != 6) {
            System.out.println("Error: Usage - addagenda [userName] [password] [attendee] [starttime] [endtime] [label]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        boolean success = false;

        // 处理时间格式，将"_"替换为空格
        params[3] = params[3].replace('_', ' ');
        params[4] = params[4].replace('_', ' ');
        params[5] = params[5].replace('_', ' ');

        try {
            success = manager.CreateAgenda(params[2], params[3], params[4], params[5]);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Format of time - XXXX年XX月XX日_XX:XX:XX");
            return;
        }

        System.out.println(success ? "Success: Meeting created" : "Error: Time conflict");
    }

    /**
     * 处理用户删除会议的请求。
     * 
     * @param params 命令参数：[userName] [password] [agendaID]
     * @throws Exception 可能抛出的异常
     */
    public void handleDelete(String[] params) throws Exception {
        if (params.length != 3) {
            System.out.println("Error: Usage - deleteagenda [userName] [password] [agendaID]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        manager.DeleteAgenda(Long.parseLong(params[2])); // 删除会议
        System.out.println("Success: Agenda deleted");
    }

    /**
     * 处理用户清空会议的请求。
     * 
     * @param params 命令参数：[userName] [password]
     * @throws Exception 可能抛出的异常
     */
    public void handleClear(String[] params) throws Exception {
        if (params.length != 2) {
            System.out.println("Error: Usage - clearagenda [userName] [password]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        manager.ClearAgenda(); // 清空所有会议
        System.out.println("Success: Agendas cleared");
    }
    
    /**
     * 处理用户查询会议的请求。
     * 
     * @param params 命令参数：[userName] [password] [starttime] [endtime]
     *                        [userName] [password] [agendaID]
     *                        [userName] [password]
     * @throws Exception 可能抛出的异常
     */
    public void handleQuery(String[] params) throws Exception {
        // 参数数量检查
        if (params.length != 4 && params.length != 3 && params.length != 2) {
            System.out.println("Error: Usage - queryagenda [userName] [password] [starttime] [endtime]");
            System.out.println("               queryagenda [userName] [password] [agendaID]");
            System.out.println("               queryagenda [userName] [password]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        Set<Agenda> agendas = new TreeSet<>();

        if (params.length == 4) { // 查询某一时间段内的会议
            params[2] = params[2].replace('_', ' ');
            params[3] = params[3].replace('_', ' ');
            try {
                agendas = manager.SearchAgenda(params[2], params[3]);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Format of time - XXXX年XX月XX日_XX:XX:XX");
                return;
            }
        } else if (params.length == 3) { // 查询某个特定会议
            try {
                agendas = manager.SearchAgenda(Long.parseLong(params[2]));
            } catch (NumberFormatException e) {
                System.out.println("Error: Usage - queryagenda [userName] [password] [agendaID]");
            }
        } else { // 查询所有会议
            agendas = manager.SearchAgenda();
        }

        // 格式化输出会议信息
        StringBuffer string = new StringBuffer("");
        for (Agenda agenda : agendas) {
            string.append("=============================================\r\n");
            string.append("Agenda ID: " + agenda.GetId() + "\r\n");
            string.append("Organizer: " + agenda.GetOrganizer() + "\r\n");
            string.append("Attendees: " + agenda.GetAttendeesFormat() + "\r\n");
            string.append("Start Time: " + agenda.GetStartTimeFormat() + "\r\n");
            string.append("End Time: " + agenda.GetEndTimeFormat() + "\r\n");
            string.append("Label: " + agenda.GetLabel() + "\r\n\r\n");
        }
        System.out.println(string.toString());
    }

    
    /**
     * 处理用户向会议添加参与者的请求。
     * 
     * @param params 命令参数：[user] [code] [meetingId] [attendee]
     * @throws Exception 可能抛出的异常
     */
    public void handleAddAttendee(String[] params) throws Exception {
        if (params.length != 4) {
            System.out.println("Error: Usage - addattendee [user] [code] [meetingId] [attendee]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        int result = manager.AddAttendee(Long.parseLong(params[2]), params[3]);

        switch (result) {
            case 0:
                System.out.println("Success: Attendee added");
                break;
            case -1:
                System.out.println("Error: No permission");
                break;
            case -2:
                System.out.println("Error: Meeting not found");
                break;
        }
    }
    
    /**
     * 处理用户从会议中删除参与者的请求。
     * 
     * @param params 命令参数：[userName] [password] [agendaID] [attendee]
     * @throws Exception 可能抛出的异常
     */
    public void handleDeleteAttendee(String[] params) throws Exception {
        if (params.length != 4) {
            System.out.println("Error: Usage - deleteattendee [userName] [password] [agendaID] [attendee]");
            return;
        }
        
        User user = checkLogin(params[0], params[1]); // 验证用户身份
        if (user == null) return;
        
        Manager manager = (Manager) user;
        manager.DeleteAttendee(Long.parseLong(params[2]), params[3]); // 删除指定的参与者
        System.out.println("Success: Attendee deleted");
    }
    
    /**
     * 处理 developer 命令，显示开发者信息
     * 
     * @param params 命令参数（无参数）
     * @throws Exception 可能抛出的异常
     */
    public void handleDeveloper(String[] params) throws Exception {
        System.out.println();
        System.out.println("==============================");
        System.out.println();
        System.out.println("Developer List");
        System.out.println();
        System.out.println("Developer: Fu Zhijue");
        System.out.println("Supervisor: Professor Li Wenjun");
        System.out.println();
        System.out.println("==============================");
        System.out.println();
        System.out.println("Thank you to everyone who contributed to this project!");
        System.out.println();
    }

    /**
     * 处理 batch 命令，执行批量命令
     * 
     * @param params 命令参数：[fileName]
     * @throws Exception 可能抛出的异常
     */
    public void handleBatch(String[] params) throws Exception {
        if (params.length != 1) {
            System.out.println("Error: Usage - batch [fileName]");
            return;
        }

        // 读取文件并逐行执行命令
        try (BufferedReader br = new BufferedReader(new FileReader(params[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Executing: " + line);
                String[] cmd = line.split("\\s+");
                String[] cmdParams = Arrays.copyOfRange(cmd, 1, cmd.length);
                switch (cmd[0].toLowerCase()) {
                    case "help":
                        this.handleHelp(cmdParams);
                        break;
                    case "register":
                        this.handleRegister(cmdParams);
                        break;
                    case "cancel":
                        this.handleCancel(cmdParams);
                        break;
                    case "rename":
                        this.handleRename(cmdParams);
                        break;
                    case "quit":
                        System.out.println("System exit.");
                        System.exit(0);
                    default:
                        System.out.println("Error: Unknown command in batch file");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: File not found - " + params[0]);
        }
    }
    
    /**
     * 检查用户登录状态
     * 
     * @param name 用户名
     * @param code 密码
     * @return 登录成功返回 User 对象，否则返回 null
     */
    private User checkLogin(String name, String code) {
        User manager = new Manager(name, code);
        int status = Register.Login(name, code);
        switch (status) {
            case -1:
                System.out.println("Error: User not found");
                return null;
            case -2:
                System.out.println("Error: Incorrect password");
                return null;
            default:
                return manager;
        }
    }
    
}
