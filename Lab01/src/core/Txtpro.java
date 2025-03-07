package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * 用于处理文本文件(.txt)数据存储的工具类
 * <p>
 * 提供文本文件的写入和读取能力，支持自动创建目录结构，
 * 当读取不存在的文件时会自动初始化默认数据文件
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastModified 2025年3月7日
 */
public class Txtpro {
    
    /**
     * 将数据写入文本文件
     * <p>
     * 该方法将阈值参数和文本内容按顺序写入目标文件，若文件路径不存在会自动创建父目录。
     * 文件存储路径默认设置为C盘Java目录，开发环境可切换为src目录（需注释当前路径配置）
     *
     * @param threshold 个人所得税起征点，会写入文件的第一行
     * @param textable  税率表，会写入文件的后续行
     * @throws IOException 当文件写入失败或路径创建失败时抛出
     */
    public static void Output(String threshold, String textable) throws IOException {
        // 文件路径配置（生产环境）
        String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
        // String filename = "src" + File.separator + "data.txt";
        
        File file = new File(filename);
        // 自动创建缺失的父目录
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        // 使用字符流写入文件内容
        try (Writer output = new FileWriter(file)) {
            output.write(threshold + "\r\n");
            output.write(textable);
        }
    }
    
    /**
     * 从文本文件读取数据
     * <p>
     * 该方法会读取指定路径的文本文件内容。当文件不存在时，会自动初始化包含默认值的文件，
     * 并返回"1000\r\n11000000000, 0\r\n"格式的默认数据。
     *
     * @return 文件内容字符串，包含个人所得税起征点及税率表
     * @throws IOException 当文件读取失败时抛出
     */
    public static String Input() throws IOException {
        // 文件路径配置需与写入方法保持一致
        String filename = "c:" + File.separator + "Java" + File.separator + "data.txt";
        // String filename = "src" + File.separator + "data.txt";
        
        File file = new File(filename);
        // 文件不存在时初始化默认数据
        if(!file.getParentFile().exists()) {
            Output("1000", "11000000000, 0\r\n");
            return "1000\r\n11000000000, 0\r\n";
        }
        
        // 使用字符流读取文件内容
        try (Reader input = new FileReader(file)) {
            char data[] = new char[10000000];
            int len = input.read(data);
            return new String(data, 0, len);
        }
    }
}
