package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import exceptions.DividedByZeroException;
import exceptions.ExpressionException;
import exceptions.FunctionCallException;
import exceptions.IllegalIdentifierException;
import exceptions.MissingLeftParenthesisException;
import exceptions.MissingOperandException;
import exceptions.MissingOperatorException;
import exceptions.MissingRightParenthesisException;
import exceptions.SyntacticException;
import exceptions.TrinaryOperationException;
import exceptions.TypeMismatchedException;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenBoolean;
import scanner.TokenDecimal;
import scanner.TokenDollar;
import scanner.TokenFunction;
import scanner.TokenOperator;

/**
 * 语法分析器类
 * 
 * <p>
 * 该类根据输入的tokens流，依照预定义的语法规则进行分析，并生成语法树。<br>
 * 它实现了表达式的分析和计算，包括算术、逻辑运算、比较操作、三元运算等，并处理各种语法错误。
 * </p>
 *
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class Parser {
    
	/** 存储每种符号的索引映射 */
    private static final Map<String, Integer> labelIndexMap = new HashMap<>();
    
    static {
        // 初始化符号索引映射
        String[] labels = {"(", ")", "func", "-", "^", "multiply_divid", "plus_minus", "cmp", "!", "&", "|", "?", ":", ",", "$"};
        for (int i = 0; i < labels.length; i++) {
            labelIndexMap.put(labels[i], i);
        }
    }

    /** 操作符栈 */
    private final Stack<Token> operators = new Stack<>();
    
    /** 操作数栈 */
    private final Stack<Token> operands = new Stack<>();
    
    /**
     * 构造函数，初始化语法分析器。
     */
    public Parser() {
        // 清空栈并初始化
        operators.clear();
        operands.clear();
        operators.push(new TokenDollar());  // 初始符号为"$"
    }

    /**
     * 解析输入的表达式并返回结果
     * 
     * <p>
     * 该方法将输入表达式转换为一系列tokens，并根据语法规则逐步进行分析。根据操作符优先级，操作数和操作符会被压入栈中，最终生成语法树并返回结果。
     * </p>
     *
     * @param input 要解析的表达式字符串
     * @return 表达式的计算结果
     * @throws ExpressionException 如果表达式格式错误或计算中发生错误
     */
    public Double parsing(String input) throws ExpressionException {
        input = input.toLowerCase().replace(" ", "");  // 转换为小写并去除空格

        Scanner scanner = new Scanner(input);  // 扫描器初始化
        Token curToken = scanner.getNextToken();  // 获取当前token

        while (true) {
            Token topToken = operators.peek();  // 查看操作符栈顶元素

            // 处理数字或布尔常量
            if (curToken.getType().equals("Decimal") || curToken.getType().equals("Boolean")) {
                operands.push(curToken);  // 将操作数压入操作数栈
                curToken = scanner.getNextToken();  // 获取下一个token
                continue;
            }

            // 根据当前token和栈顶token的类型，查找对应的操作
            int action = OPP.TABLE[getIndex(getLabel(topToken))][getIndex(getLabel(curToken))];

            switch (action) {
                case OPP.ACCEPT:
                    return extractFinalResult();  // 接受并返回最终结果

                case OPP.SHIFT:
                    shift(curToken);  // 移位
                    curToken = scanner.getNextToken();
                    break;

                case OPP.BRACETREDUCE:
                    reduce0();  // 括号归约
                    curToken = scanner.getNextToken();
                    break;

                case OPP.REDUCE1:
                    reduce1(); break;

                case OPP.REDUCE2:
                    reduce2(); break;

                case OPP.REDUCE3:
                    reduce3(); break;

                case OPP.ERRORLEFT:
                    throw new MissingLeftParenthesisException();  // 左括号错误

                case OPP.ERRORGRAM:
                    throw new SyntacticException();  // 语法错误

                case OPP.ERROROP:
                    throw new MissingOperandException();  // 缺少操作数

                case OPP.ERRORTYPE:
                    throw new TypeMismatchedException();  // 类型不匹配错误

                case OPP.ERRORFUNC:
                    throw new FunctionCallException();  // 函数调用错误

                case OPP.ERRORRIGHT:
                    throw new MissingRightParenthesisException();  // 右括号错误

                case OPP.ERRORTHREE:
                    throw new TrinaryOperationException();  // 三元操作错误

                default:
                    break;  // 默认语法错误
            }
        }
    }

    /**
     * 提取并返回最终的计算结果
     * 
     * <p>
     * 该方法会从操作数栈中提取结果，并进行类型检查，确保结果正确。
     * </p>
     *
     * @return 表达式的最终计算结果
     * @throws ExpressionException 如果表达式类型不匹配或缺少操作符
     */
    private Double extractFinalResult() throws ExpressionException {
        if (operands.size() == 1 && operands.peek().getType().equals("Decimal")) {
            return ((TokenDecimal) operands.peek()).getValue();  // 返回数值
        }
        if (operands.size() == 1) {
            throw new TypeMismatchedException();  // 类型不匹配
        }
        throw new MissingOperatorException();  // 缺少操作符
    }

    /**
     * 移位操作：将当前token压入操作符栈
     * 
     * @param token 当前操作符token
     */
    private void shift(Token token) {
        operators.push(token);  // 将操作符压入栈中
    }

    /**
     * 进行一元运算的归约操作
     * 
     * @throws MissingOperandException 如果缺少操作数
     */
    private void reduce1() throws MissingOperandException {
        if (operands.isEmpty()) throw new MissingOperandException();  // 如果操作数栈为空，抛出异常

        Token operand = operands.pop();  // 弹出操作数
        TokenOperator op = (TokenOperator) operators.pop();  // 弹出操作符

        switch (op.getLabel()) {
            case "-":
                ((TokenDecimal) operand).setValue(-((TokenDecimal) operand).getValue());  // 取反
                break;
            case "!":
                ((TokenBoolean) operand).setValue(!((TokenBoolean) operand).getValue());  // 逻辑非
                break;
        }
        operands.push(operand);  // 将结果压入操作数栈
    }

    /**
     * 进行二元运算的归约操作
     * 
     * @throws ExpressionException 如果表达式错误
     */
    private void reduce2() throws ExpressionException {
        if (operands.size() < 2) throw new MissingOperandException();  // 缺少操作数

        Token operand2 = operands.pop();  // 弹出第二个操作数
        Token operand1 = operands.pop();  // 弹出第一个操作数
        TokenOperator operator = (TokenOperator) operators.pop();  // 弹出操作符

        String label = operator.getLabel();  // 获取操作符标签
        String opName = operator.getName();  // 获取操作符名称

        // 执行不同类型的运算
        if (label.equals("plus_minus") || label.equals("multiply_divid") || label.equals("^")) {
            executeArithmetic(opName.charAt(0), operand1, operand2);  // 算术运算
        } else if (label.equals("&") || label.equals("|")) {
            executeLogical(opName.charAt(0), operand1, operand2);  // 逻辑运算
        } else if (label.equals("cmp")) {
            executeComparison(opName, operand1, operand2);  // 比较运算
        } else {
            throw new TypeMismatchedException();  // 语法错误
        }
    }

    /**
     * 执行算术运算
     * 
     * @param op 运算符
     * @param t1 第一个操作数
     * @param t2 第二个操作数
     * @throws ExpressionException 如果类型不匹配或除数为零
     */
    private void executeArithmetic(char op, Token t1, Token t2) throws ExpressionException {
        if (!(t1.getType().equals("Decimal")) || !(t2.getType().equals("Decimal"))) throw new TypeMismatchedException();  // 类型不匹配

        double v1 = ((TokenDecimal) t1).getValue();
        double v2 = ((TokenDecimal) t2).getValue();
        double result = 0.0;

        switch (op) {
            case '+': result = v1 + v2; break;
            case '-': result = v1 - v2; break;
            case '*': result = v1 * v2; break;
            case '/':
                if (Math.abs(v2) < 1e-9) throw new DividedByZeroException();  // 除零错误
                result = v1 / v2;
                break;
            case '^': result = Math.pow(v1, v2); break;
            default: break;  // 语法错误
        }
        operands.push(new TokenDecimal(Double.toString(result)));  // 将结果压入操作数栈
    }

    /**
     * 执行逻辑运算
     * 
     * @param op 运算符
     * @param t1 第一个操作数
     * @param t2 第二个操作数
     * @throws TypeMismatchedException 如果类型不匹配
     */
    private void executeLogical(char op, Token t1, Token t2) throws TypeMismatchedException {
        if (!(t1.getType().equals("Boolean")) || !(t2.getType().equals("Boolean"))) throw new TypeMismatchedException();  // 类型不匹配

        boolean b1 = ((TokenBoolean) t1).getValue();
        boolean b2 = ((TokenBoolean) t2).getValue();
        boolean result = (op == '&') ? b1 & b2 : b1 | b2;  // 执行逻辑与或操作

        operands.push(new TokenBoolean(Boolean.toString(result)));  // 将结果压入操作数栈
    }

    /**
     * 执行比较运算
     * 
     * @param op 运算符
     * @param t1 第一个操作数
     * @param t2 第二个操作数
     * @throws TypeMismatchedException 如果类型不匹配
     * @throws IllegalIdentifierException 如果运算符不匹配
     */
    private void executeComparison(String op, Token t1, Token t2) throws TypeMismatchedException, IllegalIdentifierException {
        if (!(t1.getType().equals("Decimal")) || !(t2.getType().equals("Decimal"))) throw new TypeMismatchedException();  // 类型不匹配

        double v1 = ((TokenDecimal) t1).getValue();
        double v2 = ((TokenDecimal) t2).getValue();
        boolean result = switch (op) {
            case ">" -> v1 > v2;
            case "<" -> v1 < v2;
            case "=" -> Math.abs(v1 - v2) < 1e-3;
            case ">=" -> v1 > v2 || Math.abs(v1 - v2) < 1e-9;
            case "<=" -> v1 < v2 || Math.abs(v1 - v2) < 1e-9;
            case "<>" -> Math.abs(v1 - v2) >= 1e-3;
            default -> throw new IllegalIdentifierException();  // 语法错误
        };
        operands.push(new TokenBoolean(Boolean.toString(result)));  // 将结果压入操作数栈
    }
    
    /**
     * 处理三元条件运算符（`?:`）的计算。
     * 
     * <p>
     * 该方法从栈中弹出三个操作数和两个操作符，根据条件运算符的语法判断并返回 `trueValue` 或 `falseValue`。
     * </p>
     *
     * @throws ExpressionException 如果操作数或操作符不足，抛出异常。
     * @throws TrinaryOperationException 如果操作符不是三元条件运算符，抛出异常。
     */
    private void reduce3() throws ExpressionException {
        if (operands.size() < 3 || operators.size() < 2) throw new MissingOperandException();

        TokenOperator colon = (TokenOperator) operators.pop();  // 弹出 ':' 操作符
        TokenOperator question = (TokenOperator) operators.pop();  // 弹出 '?' 操作符
        Token falseVal = operands.pop();  // 弹出 false 分支值
        Token trueVal = operands.pop();  // 弹出 true 分支值
        Token cond = operands.pop();  // 弹出条件操作数

        // 判断操作符和操作数是否符合三元运算符的条件
        if (question.getName().equals("?") && colon.getName().equals(":")) {
            if (cond.getType().equals("Boolean") && trueVal.getType().equals("Decimal") && falseVal.getType().equals("Decimal")) {
	            // 计算条件运算符结果
	            double result = ((TokenBoolean) cond).getValue()
	                    ? ((TokenDecimal) trueVal).getValue()  // 如果条件为真，返回 trueValue
	                    : ((TokenDecimal) falseVal).getValue();  // 否则返回 falseValue
	            operands.push(new TokenDecimal(Double.toString(result)));  // 将结果推入操作数栈
	        } else {
	            throw new TypeMismatchedException();  // 如果不符合三元运算符的运算格式，抛出异常
	        }
        } else {
        	throw new TrinaryOperationException();  // 如果不符合三元运算符格式，抛出异常
        }
    }

    /**
     * 处理带括号或逗号的表达式，解析函数调用的参数。
     * 
     * <p>
     * 该方法根据操作符栈顶的元素（如 `(`, `,` 或其他运算符）决定解析操作。
     * 遇到左括号 `(` 处理函数调用，遇到逗号 `,` 增加参数计数，其他情况调用简化方法。
     * </p>
     *
     * @throws ExpressionException 如果缺少左括号或遇到语法错误，抛出异常。
     */
    private void reduce0() throws ExpressionException {
        int paramCount = 0;  // 初始化参数计数器
        while (true) {
            Token operator = operators.peek();  // 查看操作符栈顶的元素

            if (operator.getType().equals("TokenDollar")) {
                throw new MissingLeftParenthesisException();  // 如果遇到 `$`，则说明缺少左括号
            }

            TokenOperator top = (TokenOperator) operator;
            switch (top.getName()) {
                case "(":  // 如果是左括号，开始处理函数调用
                    operators.pop();  // 弹出左括号
                    if (operators.peek() instanceof TokenFunction) {
                        calFunction(paramCount + 1, ((TokenFunction) operators.pop()).getName());  // 处理函数并计算参数
                    }
                    return;  // 处理完毕后返回

                case ",":  // 如果是逗号，表示参数分隔
                    operators.pop();  // 弹出逗号
                    paramCount++;  // 增加参数计数
                    break;

                default:
                    // 根据操作符的数字类型调用相应的简化方法
                    switch (top.getNumber()) {
                        case 1 -> reduce1();  // 处理一元运算符
                        case 2 -> reduce2();  // 处理二元运算符
                        case 3 -> reduce3();  // 处理三元运算符
                        default -> throw new SyntacticException();  // 如果操作符类型不合法，抛出语法异常
                    }
            }
        }
    }

    /**
     * 获取操作符或操作数的索引
     * 
     * @param label 需要获取索引的标签
     * @return 操作符或操作数的索引
     */
    private int getIndex(String label) {
        return labelIndexMap.getOrDefault(label, -1);  // 默认返回-1，表示不合法
    }

    // 
    /**
     * 获取token的标签
     * 
     * @param token 需要获取标签的最小语法单元
     * @return 该token的标签
     */
    private String getLabel(Token token) {
        return switch (token.getType()) {
            case "Function" -> ((TokenFunction) token).getLabel();
            case "Operator" -> ((TokenOperator) token).getLabel();
            case "TokenDollar" -> ((TokenDollar) token).getLabel();
            default -> "";
        };
    }
    
    /**
     * 计算一个函数的结果，支持不同参数个数的函数调用。
     * 
     * <p>
     * 该方法根据传入的参数数量和函数名，从操作数栈中弹出相应的操作数，计算并返回结果。
     * 支持单参数函数（如 `sin`、`cos`）和多参数函数（如 `max`、`min`）。
     * </p>
     *
     * @param arg 函数的参数个数。
     * @param function 函数的名称，如 `max`、`min`、`sin` 或 `cos`。
     * @throws SyntacticException 如果操作数栈中的操作数不足，抛出 `MissingOperandException` 异常。
     * @throws FunctionCallException 如果函数名称无效或参数数量不符，抛出此异常。
     */
    private void calFunction(int arg, String function) throws SyntacticException {
        if (operands.size() < arg) {
            throw new MissingOperandException(); // 不足的操作数
        }

        List<Double> args = new ArrayList<>();
        for (int i = 0; i < arg; i++) {
            args.add(getDecimalValue(this.operands.pop()));
        }
        // 参数是从右往左弹出，要反转顺序
        Collections.reverse(args);

        double result;

        switch (function) {
            case "sin":
            case "cos":
                if (arg != 1) throw new FunctionCallException();  // 单参函数必须只有一个参数
                result = function.equals("sin") ? Math.sin(args.get(0)) : Math.cos(args.get(0));
                break;
            case "min":
            case "max":
                if (arg < 2) throw new MissingOperandException(); // 多参函数必须至少两个参数
                result = args.get(0);
                for (int i = 1; i < args.size(); i++) {
                    result = function.equals("max") ? Math.max(result, args.get(i)) : Math.min(result, args.get(i));
                }
                break;
            default:
                throw new FunctionCallException();  // 不支持的函数
        }

        this.operands.push(new TokenDecimal(Double.toString(result)));
    }

    /**
     * 获取操作数的十进制值。
     * 
     * <p>
     * 该方法用于从栈中弹出的操作数中提取其十进制值。只支持 `TokenDecimal` 类型的操作数。
     * </p>
     * 
     * @param token 操作数，必须是 `TokenDecimal` 类型。
     * @return 操作数的十进制值。
     * @throws FunctionCallException 如果操作数不是 `TokenDecimal` 类型，抛出此异常。
     */
    private double getDecimalValue(Token token) throws FunctionCallException {
        // 如果操作数不是十进制数，则抛出异常
        if (!(token.getType().equals("Decimal"))) {
            throw new FunctionCallException();  // 抛出异常表示不支持的类型
        }
        return ((TokenDecimal) token).getValue();  // 返回十进制数值
    }
    
}
