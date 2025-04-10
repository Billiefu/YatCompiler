package parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * 含递归的语法解析器类（RecursionParser）
 * 
 * <p>
 * 该类用于处理输入的中缀表达式，并将其转换为后缀表达式（逆波兰表达式）。<br>
 * 同时支持对非法输入进行错误识别与报告，确保表达式格式正确。<br>
 * 与Parser类不同的是，该类在进行处理的时候使用了递归。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月10日
 * @lastUpdated 2025年4月10日
 * 
 */
public class RecursionParser {

    /** 当前读取的字符（lookahead） */
    private int lookahead;

    /** 当前读取字符的位置（从1开始） */
    private int pos = 0;

    /** 输入流（带回退功能） */
    private final PushbackInputStream in;

    /** 是否已经检测到错误 */
    private boolean hasError = false;

    /**
     * 构造函数
     * 
     * <p>
     * 初始化语法解析器，准备从输入流读取中缀表达式。
     * </p>
     * 
     * @param input 输入流
     * @throws IOException 输入流读取失败时抛出
     */
    public RecursionParser(InputStream input) throws IOException {
        this.in = new PushbackInputStream(new BufferedInputStream(input)); // 使用带缓冲与回退的输入流
        this.lookahead = nextToken(); // 预读取一个字符
    }

    /**
     * 表达式处理函数
     * 
     * <p>
     * 处理 expr → term rest 的文法规则<br>
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    public void expr() throws IOException {
        term(); // 首先处理一个 term
        rest(); // 处理 expr 后续部分
    }

    /**
     * 余项处理函数
     * 
     * <p>
     * 处理 rest → { ('+' | '-') term } 的文法规则<br>
     * 如果检测到错误，会调用错误处理函数并输出提示信息。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    private void rest() throws IOException {
        if (hasError) return;

        // 检查下一个符号是否是 '+' 或 '-'
        if (lookahead == '+' || lookahead == '-') {
            int op = lookahead; // 保存当前操作符
            match(op); // 尝试匹配该操作符
            if (hasError) return;

            term(); // 再处理一个 term
            if (hasError) return; // 出错了就立即返回，避免输出多余内容
            System.out.write(op); // 输出操作符（后缀表达式格式）
            System.out.write(' '); // 空格分隔

            rest(); // 递归处理后续的 expr 部分
        } else {
        	// 不对输入做任何处理
        }
        
        // 表达式末尾如果还有非法字符，则报错
        if (!hasError && lookahead != -1) {
            error("Unexpected character '" + (char) lookahead + "' at position " + pos);
            printErrorExpression("(error)");
        }
    }

    /**
     * 项处理函数
     * 
     * <p>
     * 处理 term → digit<br>
     * 只支持单个数字作为有效项。多位数字会被识别为错误。
     * </p>
     * 
     * @throws IOException 输入输出异常
     */
    private void term() throws IOException {
        if (Character.isDigit((char) lookahead)) {
            StringBuilder sb = new StringBuilder();
            int startPos = pos; // 记录开始位置
            while (Character.isDigit((char) lookahead)) {
                sb.append((char) lookahead);
                lookahead = nextToken(); // 读取下一个字符
            }
            if (sb.length() > 1) {
                // 多位数字不被允许，报告错误
                error("Multiple digits detected: " + sb + " at position " + startPos);
                printErrorExpression(sb.charAt(0) + " (error)");
                return;
            }
            // 输出数字（后缀表达式格式）
            System.out.write(sb.charAt(0));
            System.out.write(' ');
        } else if (lookahead == ' ') {
            // 空格作为项时非法
            error("Unexpected space at position " + pos);
            printErrorExpression("(error)");
        } else if (lookahead == '*' || lookahead == '/' || lookahead == '(' || lookahead == ')') {
            // 非法操作符
            error("Unexpected operator '" + (char) lookahead + "' at position " + pos);
            printErrorExpression("(error)");
        } else {
            // 不是数字的其它字符
            error("Expected a number but found '" + (char) lookahead + "' at position " + pos);
            printErrorExpression("(error)");
        }
    }

    /**
     * 匹配当前字符与期望字符
     * 
     * <p>
     * 如果匹配成功，则继续读取下一个字符；否则报告错误。
     * </p>
     * 
     * @param t 期望匹配的字符
     * @throws IOException 输入输出异常
     */
    private void match(int t) throws IOException {
        if (lookahead == t) {
            lookahead = nextToken(); // 匹配成功，读取下一个字符
        } else {
            // 匹配失败，输出错误提示
            error("Expected '" + (char) t + "' but found '" + (char) lookahead + "' at position " + pos);
            printErrorExpression("(error)");
        }
    }

    /**
     * 读取下一个字符（Token）
     * 
     * @return 读取到的字符（ASCII码）
     * @throws IOException 输入输出异常
     */
    private int nextToken() throws IOException {
        int ch = in.read(); // 从输入流读取一个字符
        pos++; // 字符位置递增
        return ch; // 不跳过空格，保留
    }

    /**
     * 错误处理函数
     * 
     * <p>
     * 设置错误标记，并输出错误信息。
     * </p>
     * 
     * @param message 错误提示信息
     */
    private void error(String message) {
        hasError = true;
        System.err.println("[Syntax Error] " + message); // 错误提示输出到标准错误
        System.err.println("[Info] Expression skipped due to error.");
    }

    /**
     * 输出错误表达式提示
     * 
     * @param errorExpr 错误信息或标识
     */
    private void printErrorExpression(String errorExpr) {
        System.out.println(errorExpr); // 输出至标准输出
    }

    /**
     * 获取是否发生错误
     * 
     * @return true 表示发生错误；false 表示无错误
     */
    public boolean hadError() {
        return hasError;
    }
    
}
