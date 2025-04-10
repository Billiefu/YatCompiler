package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import parser.Parser;

/**
 * 异常测试单元类（ExceptionTest）
 *
 * <p>
 * 本类用于测试语法分析器（Parser）在处理非法或异常中缀表达式时是否能正确识别出错误，
 * 并输出预期的错误表达式格式。<br>
 * 每个测试方法都构造一个非法输入字符串，验证转换输出是否包含对应的“(error)”提示。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月10日
 * @lastUpdated 2025年4月10日
 * 
 */
class ExceptionTest {

    /**
     * 测试1
     * 
     * <p>
     * 输入多位数字（"95+2"）是否能检测到并报告为错误。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test1() throws IOException {
        String line = "95+2"; // 非法输入，"95"为多位数字

        // 捕获输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 保存原始输出流
        System.setOut(new PrintStream(buffer)); // 重定向标准输出至缓冲区

        // 将字符串输入转换为输入流
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr(); // 执行表达式分析

        System.setOut(originalOut); // 恢复标准输出

        String result = buffer.toString().trim(); // 获取输出内容并去除首尾空格

        // 断言输出结果是否与预期一致
        assertEquals("9 (error)", result); // 期望输出：只处理第一个数字后报错
    }

    /**
     * 测试2
     * 
     * <p>
     * 输入包含多个运算符和负数符号（"9-5+-2"），检测运算优先级与非法负数处理。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test2() throws IOException {
        String line = "9-5+-2"; // 输入含多个运算符，"+-"组合非法

        // 捕获输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 保存原始输出流
        System.setOut(new PrintStream(buffer)); // 重定向标准输出至缓冲区

        // 将字符串输入转换为输入流
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr(); // 执行表达式分析

        System.setOut(originalOut); // 恢复标准输出

        String result = buffer.toString().trim(); // 获取输出内容并去除首尾空格

        assertEquals("9 5 - (error)", result); // 应在尝试处理“-2”时报错
    }

    /**
     * 测试3
     * 
     * <p>
     * 输入包含非法乘法符号（"9-5*2+6"），检测不支持的运算符处理。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test3() throws IOException {
        String line = "9-5*2+6"; // "*" 不在支持范围内，应报告错误

        // 捕获输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 保存原始输出流
        System.setOut(new PrintStream(buffer)); // 重定向标准输出至缓冲区

        // 将字符串输入转换为输入流
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr(); // 执行表达式分析

        System.setOut(originalOut); // 恢复标准输出

        String result = buffer.toString().trim(); // 获取输出内容并去除首尾空格

        assertEquals("9 5 - (error)", result); // 处理到 "*" 时出错
    }

    /**
     * 测试4
     * 
     * <p>
     * 输入带空格的表达式（"9- 5+2"），测试是否能识别空格错误。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test4() throws IOException {
        String line = "9- 5+2"; // 在 "-" 与 "5" 之间有空格，应视为错误

        // 捕获输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // 保存原始输出流
        System.setOut(new PrintStream(buffer)); // 重定向标准输出至缓冲区

        // 将字符串输入转换为输入流
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr(); // 执行表达式分析

        System.setOut(originalOut); // 恢复标准输出

        String result = buffer.toString().trim(); // 获取输出内容并去除首尾空格

        assertEquals("9 (error)", result); // 应在处理空格时出错
    }

}
