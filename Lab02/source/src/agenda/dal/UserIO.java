package agenda.dal;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Set;

import agenda.bll.user.User;

/**
 * UserIO 类
 * 该类负责用户的持久化存储和读取操作
 * 
 * @author 傅祉珏
 * @created 2025年3月13日
 * @lastUpdated 2025年3月27日
 */
public class UserIO {

    /**
     * 将用户信息写入文件
     *
     * @param users 用户集合
     * @throws IOException 发生文件读写错误时抛出异常
     */
    public static void Output(Set<User> users) throws IOException {
        // 定义用户信息存储的文件路径
        String filename = "c:" + File.separator + "Java" + File.separator + "user.txt";

        // 创建文件对象
        File file = new File(filename);

        // 如果文件所在的目录不存在，则创建目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 使用 try-with-resources 语法，自动关闭 FileWriter
        try (Writer output = new FileWriter(file)) {
            // 遍历用户集合，将每个用户的信息写入文件
            for (User user : users) {
                output.write(user.GetName() + ","); // 写入用户名
                output.write(user.GetCode() + "\r\n"); // 写入用户代码，并换行
            }
            output.close(); // 关闭流（可省略，因为 try-with-resources 会自动关闭）
        }
    }

    /**
     * 从文件中读取用户信息
     *
     * @return 文件内容的字符串表示
     * @throws IOException 发生文件读写错误时抛出异常
     */
    public static String Input() throws IOException {
        // 定义用户信息存储的文件路径
        String filename = "c:" + File.separator + "Java" + File.separator + "user.txt";

        // 创建文件对象
        File file = new File(filename);

        // 如果文件所在的目录不存在，则创建目录，并写入默认的空用户信息
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            UserIO.Output(null); // 初始化文件内容为空
        }

        // 用于存储读取的用户信息
        String result = "";

        // 使用 try-with-resources 语法，自动关闭 FileReader
        try (Reader input = new FileReader(file)) {
            char data[] = new char[10000000]; // 预分配一个大数组存储读取的数据
            int len = input.read(data); // 读取数据长度
            result = new String(data, 0, len); // 截取实际读取的部分，转换为字符串
            input.close(); // 关闭流（可省略，因为 try-with-resources 会自动关闭）
        }

        return result; // 返回读取的用户信息
    }

}
