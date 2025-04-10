package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import parser.Parser;
import parser.RecursionParser;

/**
 * 性能比较测试类（RecursionTest）
 * 
 * <p>
 * 本类为 JUnit 单元测试类，用于对比两种不同的中缀转后缀表达式语法分析器：<br>
 * {@code RecursionParser}（递归版本）与 {@code Parser}（非递归版本）的运行性能。<br>
 * 比较指标为：处理不同长度表达式所需时间（纳秒），每组表达式测试执行多次求平均值。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月10日
 * @lastUpdated 2025年4月10日
 * 
 */
class RecursionTest {

    /**
     * 动态生成指定长度的表达式字符串。
     * 表达式格式为交替加减的数字序列，例如 "0+1-2+3-4..."
     *
     * @param length 表达式的长度（数字个数）
     * @return 构造好的中缀表达式字符串
     */
    private static String generateExpression(int length) {
        StringBuilder sb = new StringBuilder();  // 使用 StringBuilder 构造表达式
        for (int i = 0; i < length; i++) {
            sb.append(i % 10);  // 数字为 0~9 的循环
            if (i != length - 1) {
                // 在数字之间交替添加 '+' 和 '-' 符号
                sb.append(i % 2 == 0 ? '+' : '-');
            }
        }
        return sb.toString();  // 返回构造好的表达式
    }

    /**
     * 测量解析器处理表达式所需的运行时间（纳秒）。
     *
     * @param inputStr      输入的中缀表达式字符串
     * @param useRecursion  是否使用递归解析器（true 为递归版本，false 为非递归版本）
     * @return 执行解析所需的纳秒时间，若出现异常则返回 -1
     * @throws IOException 输入输出异常
     */
    private static long measureParserTime(String inputStr, boolean useRecursion) throws IOException {
        // 将字符串转换为输入流形式
        ByteArrayInputStream input = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();  // 缓冲输出
        PrintStream originalOut = System.out;  // 保存原始输出流

        System.setOut(new PrintStream(buffer));  // 重定向 System.out 到缓冲区

        long start = System.nanoTime();  // 记录开始时间（纳秒）
        try {
            if (useRecursion) {
                // 使用递归版本的解析器
                RecursionParser parser = new RecursionParser(input);
                parser.expr();  // 执行表达式解析
            } else {
                // 使用非递归版本的解析器
                Parser parser = new Parser(input);
                parser.expr();  // 执行表达式解析
            }
        } catch (Exception e) {
            // 捕获解析过程中可能发生的异常
            System.setOut(originalOut);  // 恢复标准输出
            System.err.println("Exception occurred in " + (useRecursion ? "RecursionParser" : "Parser") + ": " + e.getMessage());
            e.printStackTrace();
            return -1;  // 返回错误标志
        }

        long end = System.nanoTime();  // 记录结束时间
        System.setOut(originalOut);  // 恢复标准输出
        return (end - start);  // 返回总耗时
    }

    /**
     * 主测试方法：比较递归与非递归解析器在不同表达式长度下的性能表现。
     * 
     * <p>
     * 表达式长度从 1 到 10001，每隔 100 递增。每个表达式重复运行 10 次求平均值。<br>
     * 若总运行时间超过 3 秒，则测试失败。
     * </p>
     *
     * @throws IOException 输入输出异常
     */
    @Test
    void test() throws IOException {
        System.out.println("Length\tRecursion(ns)\tLoop(ns)");  // 打印标题

        long startTime = System.currentTimeMillis();  // 记录总开始时间（毫秒）

        // 遍历表达式长度：1 到 10001，每隔 100 测试一次
        for (int length = 1; length <= 10001; length += 100) {
            String expr = generateExpression(length);  // 生成表达式
            long totalRec = 0, totalLoop = 0;  // 初始化累计时间
            int repeat = 10;  // 每组表达式重复测试次数

            for (int i = 0; i < repeat; i++) {
                totalRec += measureParserTime(expr, true);   // 递归解析器总时间
                totalLoop += measureParserTime(expr, false); // 非递归解析器总时间
            }

            long avgRec = totalRec / repeat;   // 递归平均耗时
            long avgLoop = totalLoop / repeat; // 非递归平均耗时

            // 输出当前长度的平均性能对比
            System.out.println(length + "\t" + avgRec + "\t\t" + avgLoop);
        }

        long duration = System.currentTimeMillis() - startTime;  // 计算总耗时（毫秒）

        // 验证总耗时不超过 3 秒，否则测试失败
        assertTrue(duration < 3000, "表达式处理时间超出预期！");
    }
    
}
