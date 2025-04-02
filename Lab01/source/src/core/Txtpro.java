package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * 文本文件处理器
 * 
 * <p>
 * 实现将结构化数据输出到.txt文件，并提供从.txt文件加载数据的功能。<br>
 * 自动处理文件路径创建和默认文件初始化操作。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Txtpro {
    
    /**
     * 将数据写入文本文件
     * 
     * <p>
     * 自动创建不存在的父级目录，使用系统默认换行符
     * </p>
     * 
     * @param threshold 阈值参数，通常作为文件首行存储
     * @param textable 结构化文本内容，支持多行数据
     * @throws IOException 当文件创建失败或写入错误时抛出
     */
    public static void Output(String threshold, String textable) throws IOException {
    	// 文件路径配置
    	// String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
        String filename = "src" + File.separator + "data.txt";
        
        File file = new File(filename);
        // 自动创建缺失的父级目录
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        // 使用try-with-resources自动管理资源
        try (Writer output = new FileWriter(file)) {
            output.write(threshold + "\r\n");  // 写入阈值参数
            output.write(textable);            // 写入主体内容
            output.close();
        }
    }
    
    /**
     * 从文本文件加载数据内容
     * 
     * <p>
     * 当目标文件不存在时，自动初始化默认数据文件：<br>
     * 阈值1000，预设电话号码11000000000与初始状态0
     * </p>
     * 
     * @return 文件内容的字符串形式，保留原始换行格式
     * @throws IOException 当文件读取失败时抛出
     */
    public static String Input() throws IOException {
    	// 文件路径配置
    	// String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
        String filename = "src" + File.separator + "data.txt";
        
        File file = new File(filename);
        // 文件不存在时执行初始化操作
        if(!file.getParentFile().exists()) {
            Output("1000", "11000000000, 0\r\n");
            return "1000\r\n11000000000, 0\r\n";
        }
        
        // 使用字符数组缓冲区读取文件内容
        try (Reader input = new FileReader(file)) {
            char data[] = new char[10_000_000];  // 10MB字符缓冲区
            int len = input.read(data);
            input.close();
            
            return new String(data, 0, len);  // 转换有效数据为字符串
        }
    }
    
}
