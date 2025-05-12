package scanner;

/**
 * TokenBoolean 类
 * 
 * <p>
 * 表示布尔类型的词法单元，是 Token 类的子类。<br>
 * 用于在词法分析阶段识别和处理布尔常量（true / false），
 * 并将其封装为具有特定类型和值的 Token 对象。
 * </p>
 * 
 * @author 傅祉珏  
 * @created 2025年4月24日  
 * @lastUpdated 2025年5月12日
 */
public class TokenBoolean extends Token {

    /** 布尔值 true 或 false */
    private Boolean value;

    /**
     * 构造函数
     * 
     * <p>
     * 根据输入字符串设置 Token 类型为 "Boolean"，并解析布尔值。
     * </p>
     * 
     * @param value 输入的布尔字符串，应为 "true" 或 "false"（大小写不敏感）
     */
    public TokenBoolean(String value) {
        this.type = "Boolean";  // 设置类型字段
        // 将输入字符串转换为布尔值，忽略大小写
        this.value = value.toLowerCase().equals("true");
    }

    /**
     * 获取该布尔 Token 的值。
     *
     * @return 布尔值 true 或 false
     */
    public Boolean getValue() {
        return this.value;
    }

    /**
     * 设置布尔 Token 的值。
     *
     * @param value 布尔值 true 或 false
     */
    public void setValue(Boolean value) {
        this.value = value;
    }
    
}
