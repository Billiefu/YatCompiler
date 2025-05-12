package parser;

/**
 * 运算符优先关系处理类（OPP）
 * 
 * <p>
 * 该类用于定义表达式中运算符的优先级和结合性，消除语法分析中的二义性问题。<br>
 * 类中包含操作码（如 SHIFT、REDUCE 等）、错误码（如 ERRORRIGHT、ERRORFUNC 等）及算符优先关系表（OPP 表），
 * 用于支持基于算符优先分析法的表达式解析与语法树构建。
 * </p>
 * 
 * @author 傅祉珏  
 * @created 2025年4月24日  
 * @lastUpdated 2025年5月12日
 */
public class OPP {

	
    // 操作码定义（用于语法分析器决策）

    /** 接受：分析成功 */
    public static final int ACCEPT        =  0;

    /** 移进：将下一个符号移入分析栈 */
    public static final int SHIFT         =  1;

    /** 归约：根据产生式1进行归约 */
    public static final int REDUCE1       =  2;

    /** 归约：根据产生式2进行归约 */
    public static final int REDUCE2       =  3;

    /** 归约：根据产生式3进行归约 */
    public static final int REDUCE3       =  4;

    /** 括号归约：用于括号包裹的子表达式归约 */
    public static final int BRACETREDUCE  =  5;


    // 错误码定义（用于语法分析器报错处理）

    /** 问号冒号不匹配错误（?: 结构问题） */
    public static final int ERRORTHREE    = -1;

    /** 缺少右括号 */
    public static final int ERRORRIGHT    = -2;

    /** 函数使用错误 */
    public static final int ERRORFUNC     = -3;

    /** 运算符类型不匹配 */
    public static final int ERRORTYPE     = -4;

    /** 运算符优先级冲突或未定义 */
    public static final int ERROROP       = -5;

    /** 文法错误 */
    public static final int ERRORGRAM     = -6;

    /** 缺少左括号 */
    public static final int ERRORLEFT     = -7;


    // 运算符优先关系表（OPP 表）

    /**
     * 算符优先关系表
     * 
     * <p>
     * 行与列的顺序依次对应以下符号：<br>
     * <code>( ) func - ^ md pm cmp ! & | ? : , $</code><br>
     * 表中元素的含义为：<br>
     * 正整数：表示进行某种归约操作（如 REDUCE1, REDUCE2, REDUCE3 等）；<br>
     * 0：ACCEPT；1：SHIFT；负值：表示特定类型的语法错误（见上方错误码）；<br>
     * </p>
     */
    public static final int[][] TABLE = {
        /*  (     )     func  -     ^     md    pm    cmp   !     &     |     ?     :     ,     $  */
        {  1,    5,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,   -1,    1,   -2 }, // (
        {  5,    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,    5 }, // )
        {  1,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3,   -3 }, // func
        {  1,    5,    1,    1,    2,    2,    2,    2,   -6,   -4,   -4,    2,    2,    2,    2 }, // -
        {  1,    5,    1,    1,    1,    3,    3,    3,   -6,   -4,   -4,    3,    3,    3,    3 }, // ^
        {  1,    5,    1,    1,    1,    3,    3,    3,   -6,   -4,   -4,    3,    3,    3,    3 }, // md
        {  1,    5,    1,    1,    1,    1,    3,    3,   -6,   -4,   -4,    3,    3,    3,    3 }, // pm
        {  1,    5,    1,    1,    1,    1,    1,   -4,   -6,    3,    3,    3,   -1,   -3,    3 }, // cmp
        {  1,    5,   -4,   -4,   -4,   -4,   -4,    1,    1,    2,    2,    2,   -1,   -3,    2 }, // !
        {  1,    5,   -4,   -4,   -4,   -4,   -4,    1,    1,    3,    3,    3,   -1,   -3,    3 }, // &
        {  1,    5,   -4,   -4,   -4,   -4,   -4,    1,    1,    1,    3,    3,   -1,   -3,    3 }, // |
        {  1,   -1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,   -1,   -1 }, // ?
        {  1,    5,    1,    1,    1,    1,    1,    1,   -1,   -1,   -1,    1,   -1,   -1,    4 }, // :
        {  1,    5,    1,    1,    1,    1,    1,   -3,   -3,   -3,   -3,    1,   -1,    1,   -3 }, // ,
        {  1,   -7,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,   -1,   -3,    0 }  // $
    };
    
}
