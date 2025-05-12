package scanner;

import exceptions.EmptyExpressionException;
import exceptions.ExpressionException;
import exceptions.IllegalDecimalException;
import exceptions.IllegalIdentifierException;
import exceptions.IllegalSymbolException;
import exceptions.LexicalException;
import exceptions.MissingOperandException;

/**
 * 扫描器类
 * 
 * <p>
 * 该类用于分析输入的字符串，将其分解为不同的词法单元（如数字、布尔值、操作符、函数等），以便进一步的语法分析。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class Scanner {
	
	/** 当前扫描的位置 */
    private int pos;
    
    /** 输入的长度 */
    private final int len;
    
    /** 输入的字符流 */
    private final String input;

    /**
     * 构造函数，初始化扫描器。
     * 
     * @param input 输入的字符流
     */
    public Scanner(String input) {
        this.pos = 0;
        this.input = input;
        this.len = input.length();
    }

    /**
     * 获取下一个词法单元（token）。
     * 
     * <p>
     * 该方法从输入字符流中扫描并返回下一个词法单元，处理数字、布尔值、运算符、函数等。
     * </p>
     *
     * @return 下一个词法单元（token）
     * @throws LexicalException 如果扫描过程中发生词法错误，抛出此异常
     * @throws ExpressionException 如果表达式格式不合法，抛出此异常
     */
    public Token getNextToken() throws LexicalException, ExpressionException {
        if (len == 0) throw new EmptyExpressionException();
        if (pos >= len) return new TokenDollar();

        char current = input.charAt(pos);

        if (Character.isDigit(current)) {
            return processDigit();
        } else if (current == 't' || current == 'f') {
            return processBool();
        } else if ("+-*/^?:><=&|!(),".indexOf(current) != -1) {
            return processOperator(current);
        } else if ("scm".indexOf(current) != -1) {
            return processFunction();
        } else {
            processError(current);
            return new Token(); // Unreachable, added for syntax completeness
        }
    }

    /**
     * 处理数字（整数或浮点数）的词法单元。
     * 
     * <p>
     * 该方法解析数字，并处理浮点数和科学记数法的情况。
     * </p>
     *
     * @return 解析得到的数字词法单元
     * @throws IllegalDecimalException 如果数字格式不合法，抛出此异常
     */
    private Token processDigit() throws IllegalDecimalException {
        boolean dotFlag = false, eFlag = false;
        int start = pos;

        while (pos + 1 < len) {
            char ch = input.charAt(pos + 1);

            if (Character.isDigit(ch)) {
                pos++;
            } else if (ch == '.') {
                if (dotFlag || eFlag || pos + 2 >= len || !Character.isDigit(input.charAt(pos + 2))) {
                    throw new IllegalDecimalException();
                }
                dotFlag = true;
                pos++;
            } else if (ch == 'e' || ch == 'E') {
                if (eFlag || pos + 2 >= len) throw new IllegalDecimalException();
                char signOrDigit = input.charAt(pos + 2);
                if (signOrDigit == '+' || signOrDigit == '-') {
                    if (pos + 3 >= len || !Character.isDigit(input.charAt(pos + 3))) {
                        throw new IllegalDecimalException();
                    }
                    pos += 2; // 跳过 e 和符号
                } else if (Character.isDigit(signOrDigit)) {
                    pos += 1; // 跳过 e
                } else {
                    throw new IllegalDecimalException();
                }
                eFlag = true;
            } else if ((ch == '+' || ch == '-') && input.charAt(pos) == 'e') {
                if (pos + 2 >= len) throw new IllegalDecimalException();
                pos++;
            } else {
                break;
            }
        }

        pos++;
        return new TokenDecimal(input.substring(start, pos));
    }

    /**
     * 处理布尔值（`true` 或 `false`）的词法单元。
     * 
     * <p>
     * 该方法检查输入是否为有效的布尔值，并返回相应的词法单元。
     * </p>
     *
     * @return 解析得到的布尔值词法单元
     * @throws IllegalIdentifierException 如果布尔值格式不合法，抛出此异常
     */
    private Token processBool() throws IllegalIdentifierException {
        if (input.startsWith("true", pos)) {
            pos += 4;
            return new TokenBoolean("true");
        } else if (input.startsWith("false", pos)) {
            pos += 5;
            return new TokenBoolean("false");
        } else {
            throw new IllegalIdentifierException();
        }
    }

    /**
     * 处理运算符（如 `+`、`-`、`*`、`>` 等）的词法单元。
     * 
     * <p>
     * 该方法根据当前字符判断并处理运算符，支持单字符和双字符的运算符。
     * </p>
     *
     * @param current 当前字符
     * @return 解析得到的运算符词法单元
     * @throws MissingOperandException 如果操作符左右缺少操作数，抛出此异常
     */
    private Token processOperator(char current) throws MissingOperandException {
        String op = null;

        switch (current) {
            case '>': op = matchDoubleChar(">=", ">"); break;
            case '<':
                if (pos + 1 < len) {
                    if (input.startsWith("<=", pos)) op = "<=";
                    else if (input.startsWith("<>", pos)) op = "<>";
                    else op = "<";
                } else op = "<";
                break;
            case '=': op = matchDoubleChar("==", "="); break;
            case '!': op = matchDoubleChar("!=", "!"); break;
            case '&': op = matchDoubleChar("&&", "&"); break;
            case '|': op = matchDoubleChar("||", "|"); break;
            case '-':
                op = (pos > 0 && (input.charAt(pos - 1) == ')' || Character.isDigit(input.charAt(pos - 1))))
                    ? "-" : "minus";
                break;
            case ')':
                if (pos > 0 && input.charAt(pos - 1) == '(') throw new MissingOperandException();
                break;
            case ':':
                if (pos > 0 && input.charAt(pos - 1) == '?') throw new MissingOperandException();
                break;
        }

        if (op == null) {
            op = Character.toString(current);
        }

        pos += op.length();
        return new TokenOperator(op);
    }

    /**
     * 匹配双字符的运算符。
     * 
     * <p>
     * 该方法检查输入是否与预期的双字符运算符匹配，并返回匹配的运算符。
     * </p>
     *
     * @param expected 预期的双字符运算符
     * @param fallback 如果没有匹配的双字符运算符，返回的备选运算符
     * @return 匹配的运算符
     */
    private String matchDoubleChar(String expected, String fallback) {
        if (pos + 1 < len && input.startsWith(expected, pos)) {
            return expected;
        }
        return fallback;
    }

    /**
     * 处理函数名（如 `max`、`min`、`sin`、`cos`）的词法单元。
     * 
     * <p>
     * 该方法检查输入是否为有效的函数名，并返回相应的函数词法单元。
     * </p>
     *
     * @return 解析得到的函数词法单元
     * @throws IllegalIdentifierException 如果函数名不合法，抛出此异常
     */
    private Token processFunction() throws IllegalIdentifierException {
        if (pos + 3 <= len) {
            String func = input.substring(pos, pos + 3);
            if ("maxminsin".contains(func) || func.equals("cos")) {
                pos += 3;
                return new TokenFunction(func);
            }
        }
        throw new IllegalIdentifierException();
    }

    /**
     * 处理词法错误。
     * 
     * <p>
     * 该方法根据当前字符判断并抛出相应的词法错误异常。
     * </p>
     *
     * @param ch 当前字符
     * @throws LexicalException 如果发生词法错误，抛出此异常
     */
    private void processError(char ch) throws LexicalException {
        if (ch == '.' && pos + 1 < len && Character.isDigit(input.charAt(pos + 1))) {
            throw new IllegalDecimalException();
        } else if (!Character.isAlphabetic(ch)) {
            throw new IllegalSymbolException();
        } else {
            throw new IllegalIdentifierException();
        }
    }
    
}
