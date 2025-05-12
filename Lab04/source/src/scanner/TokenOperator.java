package scanner;

import java.util.Set;

/**
 * TokenOperator 类
 * 
 * <p>
 * 表示运算符类型的词法单元（Token），继承自基类 Token。<br>
 * 可用于表示加减乘除、比较运算等操作符。
 * </p>
 *
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class TokenOperator extends Token {

    /** 运算符的标签（用于分类运算符） */
    protected String label;

    /** 运算符的原始名称（如 "+"、"-"、"*"、"/" 等） */
    protected String name;

    /** 加法与减法操作符集合 */
    private static final Set<String> PLUS_MINUS = Set.of("+", "-");

    /** 乘法与除法操作符集合 */
    private static final Set<String> MULTIPLY_DIVIDE = Set.of("*", "/");

    /** 定义比较操作符集合（支持等于、不等于、大于、小于等） */
    private static final Set<String> COMPARISON = Set.of("=", "<", ">", "<=", ">=", "<>");

    /**
     * 构造函数，用于根据输入的操作符初始化运算符类型的 Token。
     *
     * @param operator 输入的操作符字符串
     */
    public TokenOperator(String operator) {
        this.type = "Operator";      // 设置类型为“运算符”
        this.name = operator;        // 保存原始操作符字符串

        // 判断操作符类型并设置标签（label）
        if (PLUS_MINUS.contains(operator)) {
            this.label = "plus_minus";           // 加减
        } else if (MULTIPLY_DIVIDE.contains(operator)) {
            this.label = "multiply_divid";       // 乘除
        } else if (COMPARISON.contains(operator)) {
            this.label = "cmp";                  // 比较运算
        } else if (operator.equals("minus")) {
            this.label = "-";                    // 特殊表示一元负号
        } else {
            this.label = operator;               // 其他情况直接使用操作符名作为标签
        }
    }

    /**
     * 获取运算符的名称（原始字符串）。
     *
     * @return 运算符字符串
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取运算符的标签，用于对操作符分类识别。
     *
     * @return 运算符标签
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 获取当前运算符的操作数数量。
     * 
     * <p>
     * 常用于语法分析阶段判断运算符的元数（arity）。
     * </p>
     *
     * @return 操作数数量（1表示一元运算符，2表示二元，3表示三元）
     */
    public int getNumber() {
        return switch (this.label) {
            case "-", "!" -> 1;     // 一元运算符
            case "&", "|", "cmp", "plus_minus", "multiply_divid", "^" -> 2; // 二元运算符
            case "?", ":" -> 3;     // 三元运算符（用于条件表达式）
            default -> 0;           // 未识别的标签，返回0
        };
    }
    
}
