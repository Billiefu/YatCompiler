package scanner;

/**
 * TokenFunction 类
 * 
 * <p>
 * 表示函数类型的词法单元（Token），继承自基类 Token。<br>
 * 在表达式语言中用于识别诸如 sin、cos、max 等预定义函数。
 * </p>
 *
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class TokenFunction extends Token {

    /** 函数标签，统一标识为 "func" */
    private String label;

    /** 函数名称，如 "sin", "max" 等 */
    private String name;

    /**
     * 构造函数，用于根据输入的函数名初始化函数类型的 Token。
     *
     * @param func 输入的函数名字符串（区分大小写）
     */
    public TokenFunction(String func) {
        this.type = "Function";             // 设置词法单元的类型
        this.name = func.toLowerCase();     // 将函数名统一转换为小写以便于识别
        this.label = "func";                // 函数的统一标签
    }

    /**
     * 获取函数名称（小写形式），用于语义处理与匹配。
     *
     * @return 函数名字符串
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取函数标签，用于打印或语义处理时区分函数。
     *
     * @return 标签字符串 "func"
     */
    public String getLabel() {
        return this.label;
    }
    
}
