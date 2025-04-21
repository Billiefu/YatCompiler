package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import parser.Parser;

/**
 * 并发测试单元类（ConcurrencyTest）
 * 
 * <p>
 * 本类用于测试语法分析器（Parser）在多线程并发场景下的行为。<br>
 * 重点对比 `lookahead` 成员在设置为 static 与非 static 时是否存在线程间串扰问题。<br>
 * 若设置为 static，多个线程共享状态可能引发错误。
 * </p>
 * 
 * <p>
 * 本测试通过 ExecutorService 启动多个线程，解析不同表达式。<br>
 * 若解析失败或输出错误，即认为并发设计存在问题。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月21日
 * @lastUpdated 2025年4月21日
 */
class ConcurrencyTest {

    /**
     * 解析任务类（ParseTask）
     *
     * <p>
     * 实现 Callable 接口，用于并发执行表达式解析任务。<br>
     * 每个任务独立创建一个 Parser 实例并解析指定表达式。
     * </p>
     */
    static class ParseTask implements Callable<Boolean> {
    	/** 当前线程需要解析的表达式 */
        private final String expression;

        /**
         * 构造函数，传入待解析的表达式
         * 
         * @param expression 表达式字符串
         */
        public ParseTask(String expression) {
            this.expression = expression;
        }

        /**
         * 任务执行方法，执行语法分析并返回是否成功
         * 
         * @return true 表示无错误，false 表示解析出错
         */
        @Override
        public Boolean call() {
            try {
                // 将表达式转换为输入流
                InputStream input = new ByteArrayInputStream(expression.getBytes());

                // 创建新的 Parser 实例并执行解析
                Parser parser = new Parser(input);
                parser.expr();

                // 返回是否未发生错误
                return !parser.hadError();
            } catch (Exception e) {
                // 捕获异常并视为解析失败
                return false;
            }
        }
    }

    /**
     * 生成指定位数的数学表达式
     * 
     * <p>
     * 表达式格式为：0+1-2+3-4...，运算符交替出现
     * </p>
     * 
     * @param length 表达式中包含的数字个数
     * @return 生成的表达式字符串
     */
    private static String generateExpression(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i % 10); // 取个位数字作为数字部分
            if (i != length - 1) {
                // 交替添加运算符：偶数加号，奇数减号
                sb.append(i % 2 == 0 ? '+' : '-');
            }
        }
        return sb.toString();
    }

    /**
     * 并发语法分析测试
     * 
     * <p>
     * 创建固定数量的线程，每个线程解析一个不同长度的表达式，
     * 检查是否存在串扰现象。
     * </p>
     * 
     * @throws InterruptedException 线程等待中断异常
     */
    @Test
    void test() throws InterruptedException {
        int threadCount = 10; // 设置线程数量
        ExecutorService executor = Executors.newFixedThreadPool(threadCount); // 创建线程池
        List<Future<Boolean>> results = new ArrayList<>(); // 存储解析结果

        // 为每个线程生成不同的表达式任务并提交执行
        for (int i = 0; i < threadCount; i++) {
            String expr = generateExpression(10 + i); // 每个表达式长度不同
            results.add(executor.submit(new ParseTask(expr))); // 提交任务并收集 Future
        }

        executor.shutdown(); // 关闭线程池（不再接受新任务）

        // 等待所有线程完成执行，最长等待10秒
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
        assertTrue(finished, "线程可能陷入死锁"); // 若未完成说明可能存在死锁问题

        // 遍历每个线程结果并验证是否成功
        for (Future<Boolean> future : results) {
            try {
                // 如果某线程结果为 false，则说明可能存在 lookahead 共享问题
                assertTrue(future.get(), "某线程中的解析发生错误，可能由于 lookahead static 导致串扰");
            } catch (ExecutionException e) {
                // 捕获线程内部异常并报告失败
                fail("解析线程抛出异常：" + e.getCause().getMessage());
            }
        }
    }

}
