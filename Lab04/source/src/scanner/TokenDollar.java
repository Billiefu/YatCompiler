package scanner;

/**
 * TokenDollar 类
 * 
 * <p>
 * 表示语法分析中的结束符 Token（如“$”），继承自 Token 类。<br>
 * 一般用于表示表达式输入的结束标记，是词法分析中不可或缺的特殊符号之一。
 * </p>
 *
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class TokenDollar extends Token {

    /**
     * 构造函数
     * 
     * <p>
     * 初始化结束符类型的 Token，并设置其 type 字段为 "TokenDollar"，用于词法识别与调试分类。
     * </p>
     */
    public TokenDollar() {
        this.type = "TokenDollar";
    }

    /**
     * 获取该 Token 的名称表示。
     * 
     * @return 字符串形式的 "$"，作为 Token 名称。
     */
    public String getName() {
        return "$";
    }

    /**
     * 获取该 Token 的标签表示，常用于打印或语义分析输出。
     * 
     * @return 字符串形式的 "$"，作为 Token 标签。
     */
    public String getLabel() {
        return "$";
    }
    
}
