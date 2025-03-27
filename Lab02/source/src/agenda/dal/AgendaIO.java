package agenda.dal;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import agenda.bll.Agenda;

/**
 * AgendaIO 类
 * 该类负责会议的持久化存储和读取操作
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
public class AgendaIO {

    /**
     * 删除指定目录及其中所有文件
     *
     * @param dir 要删除的目录
     * @return 删除是否成功
     */
    public static boolean deleteDirectory(File dir) {
        // 如果是目录，则递归删除目录中的所有文件
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                // 遍历删除所有子文件或子目录
                for (File child : children) {
                    boolean success = deleteDirectory(child);
                    if (!success) {
                        return false; // 如果删除失败，则返回 false
                    }
                }
            }
        }
        // 删除空目录
        return dir.delete();
    }

    /**
     * 将会议对象的数据输出到文件
     *
     * @param agenda 要保存的会议对象
     * @throws IOException 发生文件读写错误时抛出异常
     */
    public static void Output(Agenda agenda) throws IOException {
        // 定义会议数据存储的文件路径
        String filename = "c:" + File.separator + "Java" + File.separator + "agenda" + File.separator;

        // 使用会议ID来创建文件名
        filename += (agenda.GetId()) + ".txt";

        // 创建文件对象
        File file = new File(filename);

        // 如果文件所在目录不存在，则创建目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 使用 try-with-resources 语法，自动关闭 FileWriter
        try (Writer output = new FileWriter(file)) {
            // 写入会议ID
            output.write(agenda.GetId() + "\r\n");

            // 写入会议主持人
            output.write(agenda.GetOrganizer() + "\r\n");

            // 写入所有与会者的格式化信息（使用逗号分隔）
            for (String attendee : agenda.GetAttendeesFormat()) {
                output.write(attendee + ",");
            }

            // 写入会议开始时间
            output.write("\r\n" + agenda.GetStartTimeFormat() + "\r\n");

            // 写入会议结束时间
            output.write(agenda.GetEndTimeFormat() + "\r\n");

            // 写入会议标签
            output.write(agenda.GetLabel() + "\r\n");

            // 关闭输出流
            output.close();
        }
    }

    /**
     * 从文件中读取所有的会议数据
     *
     * @return 读取到的会议数据集合
     * @throws IOException 发生文件读写错误时抛出异常
     */
    public static Set<String> Input() throws IOException {
        // 定义会议数据存储的目录路径
        String filename = "c:" + File.separator + "Java" + File.separator + "agenda" + File.separator;

        // 用于存储所有会议数据的集合
        Set<String> agendas = new HashSet<String>();

        // 遍历所有会议ID，从1开始读取每个会议数据
        for (int i = 1; i <= AgendaIdGenerator.getInstance().GetId(); i++) {
            // 创建文件对象，文件名为会议ID对应的文件
            File file = new File(filename + i + ".txt");

            // 如果文件所在目录不存在，则创建目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 如果文件不存在，则跳过
            if (!file.exists()) {
                continue;
            }

            // 用于存储读取的文件内容
            String str = new String("");

            // 使用 try-with-resources 语法，自动关闭 FileReader
            try (Reader input = new FileReader(file)) {
                char data[] = new char[10000000]; // 预分配一个大数组存储读取的数据
                int len = input.read(data); // 读取数据长度
                str = new String(data, 0, len); // 转换为字符串
                input.close(); // 关闭流（可省略，因为 try-with-resources 会自动关闭）
            } catch (Exception e) {
                // 捕获异常后，继续处理下一个文件
            }

            // 将读取到的数据添加到集合中
            agendas.add(str);
        }

        // 返回所有会议的数据集合
        return agendas;
    }

}

