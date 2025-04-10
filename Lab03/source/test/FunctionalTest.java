package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import parser.Parser;

/**
 * 功能测试单元类（FunctionalTest）
 * 
 * <p>
 * 本类用于对语法分析器（Parser）进行功能性测试，验证是否能够正确地将中缀表达式转换为后缀表达式。<br>
 * 每个测试用例均输入合法表达式，并断言输出的后缀表达式是否与预期一致。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月10日
 * @lastUpdated 2025年4月10日
 * 
 */
class FunctionalTest {

    /**
     * 测试1
     * 
     * <p>
     * 验证简单加法表达式 "1+1" 能否正确转换为后缀表达式。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test1() throws IOException {
        String line = "1+1";  // 测试输入：简单加法

        // 创建字节输出流用于捕获标准输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;  // 保存标准输出
        System.setOut(new PrintStream(buffer));  // 重定向输出至缓冲区

        // 构建输入流并传递给 Parser 分析器
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr();  // 执行表达式分析并输出后缀表达式

        System.setOut(originalOut);  // 恢复标准输出

        String result = buffer.toString().trim();  // 获取并去除输出中的空白字符

        // 验证结果是否为预期后缀表达式
        assertEquals("1 1 +", result);
    }

    /**
     * 测试2
     * 
     * <p>
     * 验证表达式 "9-5+2" 能否正确转换为后缀表达式。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test2() throws IOException {
        String line = "9-5+2";  // 测试输入：包含减法与加法的混合表达式

        // 创建字节输出流用于捕获标准输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;  // 保存标准输出
        System.setOut(new PrintStream(buffer));  // 重定向输出至缓冲区

        // 构建输入流并传递给 Parser 分析器
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr();  // 执行表达式分析并输出后缀表达式

        System.setOut(originalOut);  // 恢复标准输出

        String result = buffer.toString().trim();  // 获取并去除输出中的空白字符

        // 验证后缀表达式结果是否正确
        assertEquals("9 5 - 2 +", result);
    }

    /**
     * 测试3
     * 
     * <p>
     * 验证长表达式 "1+2+3+4+5" 是否能正确输出对应的后缀表达式。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test3() throws IOException {
        String line = "1+2+3+4+5";  // 测试输入：连续加法表达式

        // 创建字节输出流用于捕获标准输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;  // 保存标准输出
        System.setOut(new PrintStream(buffer));  // 重定向输出至缓冲区

        // 构建输入流并传递给 Parser 分析器
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr();  // 执行表达式分析并输出后缀表达式

        System.setOut(originalOut);  // 恢复标准输出

        String result = buffer.toString().trim();  // 获取并去除输出中的空白字符

        // 验证结果是否为正确的后缀表达式
        assertEquals("1 2 + 3 + 4 + 5 +", result);
    }

    /**
     * 测试4
     * 
     * <p>
     * 验证复杂表达式 "1-2+3-4+5-6+7-8+9-0" 是否能正确转换为后缀表达式。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    @Test
    void test4() throws IOException {
        String line = "1-2+3-4+5-6+7-8+9-0";  // 测试输入：交替加减法表达式

        // 创建字节输出流用于捕获标准输出内容
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;  // 保存标准输出
        System.setOut(new PrintStream(buffer));  // 重定向输出至缓冲区

        // 构建输入流并传递给 Parser 分析器
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        Parser parser = new Parser(input); // 创建语法分析器实例
        // RecursionParser parser = new RecursionParser(input); // 若使用递归版本
        parser.expr();  // 执行表达式分析并输出后缀表达式

        System.setOut(originalOut);  // 恢复标准输出

        String result = buffer.toString().trim();  // 获取并去除输出中的空白字符

        // 验证是否为预期的后缀表达式输出
        assertEquals("1 2 - 3 + 4 - 5 + 6 - 7 + 8 - 9 + 0 -", result);
    }
    
}
