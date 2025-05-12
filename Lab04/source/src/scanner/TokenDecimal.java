package scanner;

/**
 * TokenDecimal 类
 * 
 * <p>
 * 表示十进制（Decimal）类型的词法单元，是 Token 类的子类。<br>
 * 主要用于封装带小数点或科学记数法表示的数字，并将其解析为双精度浮点值。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年4月24日
 * @lastUpdated 2025年5月12日
 */
public class TokenDecimal extends Token {

    /** 存储十进制数字对应的双精度浮点值 */
    private Double value;

    /**
     * 构造函数
     * 
     * <p>
     * 接收表示十进制数字的字符串，支持科学记数法（如 "1.23e+2"）。
     * 解析该字符串并转换为对应的 double 值。
     * </p>
     *
     * @param number 输入的数字字符串，可以为普通十进制或科学记数法
     */
    public TokenDecimal(String number) {
        this.type = "Decimal";  // 设置词法单元类型

        number = number.toLowerCase();  // 统一转换为小写，处理 'E' -> 'e'
        int sciIndex = number.indexOf('e');  // 查找科学计数法标志位

        if (sciIndex != -1) {
            // 若包含 'e'，则为科学记数法
            double base = Double.parseDouble(number.substring(0, sciIndex));  // 提取底数
            String exponentStr = number.substring(sciIndex + 1);              // 提取指数部分
            double exponent;

            // 处理负指数情况（如 1e-2 -> 0.01）
            if (exponentStr.charAt(0) == '-') {
                exponent = Double.parseDouble(exponentStr.substring(1));
                this.value = base / Math.pow(10.0, exponent);
            } else {
                // 处理正指数情况（默认或带 '+'）
                if (exponentStr.charAt(0) == '+') {
                    exponentStr = exponentStr.substring(1);  // 去除正号
                }
                exponent = Double.parseDouble(exponentStr);
                this.value = base * Math.pow(10.0, exponent);
            }
        } else {
            // 普通十进制数字直接解析
            this.value = Double.parseDouble(number);
        }
    }

    /**
     * 获取当前 Decimal Token 的数值。
     *
     * @return 表示十进制数字的 double 值
     */
    public Double getValue() {
        return this.value;
    }

    /**
     * 设置当前 Decimal Token 的数值。
     *
     * @param value 要设置的 double 值
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
}
