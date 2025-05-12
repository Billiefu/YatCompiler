package scanner;

/**
 * Token类
 * 
 * <p>
 * 该类用于词法分析阶段，帮助将输入字符流转换为词法单元，以供后续的语法分析和语义分析。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class Token {

    /** Token的类型（例如，数字、运算符、布尔值等） */
    protected String type;

    /**
     * Token的默认构造函数。
     * 
     * <p>
     * 该构造函数通常用于创建通用的Token对象，子类可以根据需要设置具体类型。
     * </p>
     */
    public Token() {
        this.type = "";  // 默认类型为空
    }

    /**
     * 获取Token的类型。
     * 
     * @return 返回Token的类型。
     */
    public String getType() {
        return this.type;
    }
    
}
