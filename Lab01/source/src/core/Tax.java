package core;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 个人所得税计算核心类
 * 
 * <p>
 * 实现个人所得税起征点及税率表的存储管理，提供个税计算功能。<br>
 * 采用单例模式确保数据一致性，初始化时自动从持久化存储加载数据。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Tax {
    
    /** 单例实例（饿汉式） */
    private static final Tax Instance = new Tax();
    
    /** 个税起征点 */
    private double threshold;
    
    /** 税率表 */
    private double[][] taxtable;

    /**
     * 私有构造方法（单例模式）
     * 
     * <p>
     * 初始化时从持久化存储加载数据，若加载失败则使用默认配置
     * </p>
     * 
     */
    private Tax() {
        try {
            String info = Txtpro.Input();
            this.setdata(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单例实例
     * 
     * @return Tax类唯一实例
     */
    public static Tax getInstance() {
        return Instance;
    }

    /**
     * 设置税务数据（字符串格式）
     * 
     * <p>
     * 数据格式要求：<br>
     * 第一行为起征点数值，<br>
     * 后续每行格式为"级数上限, 税率百分比"
     * </p>
     * 
     * @param info 完整税务数据字符串
     * @throws IOException 当数据格式异常时抛出
     */
    public void setdata(String info) throws IOException {
        // 定义数据分隔符
        final String lineSeparator = "\r\n";
        final String dataSeparator = ", ";

        // 分割数据行
        String[] lines = info.split(lineSeparator);
        String[][] dataMatrix = new String[lines.length][];
        
        // 解析每行数据
        for(int i = 0; i < lines.length; i++) {
            dataMatrix[i] = lines[i].split(dataSeparator);
        }
        
        // 设置起征点
        this.threshold = Double.parseDouble(dataMatrix[0][0]);
        
        // 初始化税率表二维数组
        this.taxtable = new double[dataMatrix.length-1][2];
        
        // 转换税率数据（百分比转小数）
        for(int i = 1; i < dataMatrix.length; i++) {
            this.taxtable[i-1][0] = Double.parseDouble(dataMatrix[i][0]);  // 级数上限
            this.taxtable[i-1][1] = Double.parseDouble(dataMatrix[i][1]) / 100;  // 税率
        }
        
        // 持久化存储更新后的数据
        Txtpro.Output(this.getthreshold(), this.gettaxtable());
    }

    /**
     * 设置税务数据（参数格式）
     * 
     * @param threshold 起征点字符串
     * @param taxtable 税率表字符串（多行格式）
     * @throws IOException 当数据格式异常时抛出
     */
    public void setdata(String threshold, String taxtable) throws IOException {
        this.setdata(threshold + "\r\n" + taxtable);
        Txtpro.Output(threshold, taxtable);
    }

    /**
     * 获取当前起征点
     * 
     * @return 格式化后的起征点字符串
     */
    public String getthreshold() {
        return String.valueOf(this.threshold);
    }

    /**
     * 获取格式化税率表
     * 
     * <p>
     * 输出格式为多行字符串，每行"级数上限, 税率百分比"<br>
     * 使用DecimalFormat确保数值格式正确
     * </p>
     * 
     * @return 税率表字符串
     */
    public String gettaxtable() {
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(0);
        df.setGroupingUsed(false);  // 禁用科学计数法
        
        StringBuffer taxtable = new StringBuffer("");
        for(int i = 0; i < this.taxtable.length; i++) {
            taxtable.append(df.format(this.taxtable[i][0]));  // 级数上限
            taxtable.append(", ");
            taxtable.append(df.format(this.taxtable[i][1] * 100));  // 转百分比
            taxtable.append("\r\n");
        }
        return taxtable.toString();
    }

    /**
     * 计算应纳税额
     * 
     * <p>
     * 采用超额累进税率计算法，公式：<br>
     * 应纳税额 = 应税所得 × 税率 - 速算扣除数<br>
     * 本实现通过逐级计算实现相同效果
     * </p>
     * 
     * @param income 税前收入
     * @return 计算结果（保留两位小数）
     */
    public double calculate(double income) {
        // 扣除起征点
        if (income < threshold) {
            return 0;
        }
        double taxableIncome = income - threshold;
        
        double remainingIncome = taxableIncome;
        double taxAmount = 0;
        
        // 逐级计算税款
        for(int level = 0; level < taxtable.length; level ++) {
            double lowerBound = (level == 0) ? 0 : taxtable[level-1][0];
            
            if(taxableIncome <= taxtable[level][0]) {
                taxAmount += remainingIncome * taxtable[level][1];
                break;
            } else {
                taxAmount += (taxtable[level][0] - lowerBound) * taxtable[level][1];
                remainingIncome = taxableIncome - taxtable[level][0];
            }
        }
        return taxAmount;
    }
    
}
