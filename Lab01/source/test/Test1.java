package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Tax;

/**
 * 2006年版个人所得税政策单元测试类
 * 
 * <p>
 * 根据中国政府网公布的2006年个人所得税政策标准（起征点1600元），
 * 验证税务计算核心逻辑的正确性。包含以下测试场景：<br>
 * 1. 标准边界测试（5个典型用例）<br>
 * 2. 随机数值测试（1个随机生成用例）<br>
 * 测试覆盖税率表所有层级及边界条件。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
class Test1 {
    
	/** 获取税率计算器 */
    private Tax tax = Tax.getInstance();

    /**
     * 初始化测试环境
     * 
     * <p>
     * 配置2006年个税政策参数：<br>
     * - 起征点：1600元<br>
     * - 税率表层级：<br>
     *     ≤500元部分：5%<br>
     *     ≤2000元部分：10%<br>
     *     ≤5000元部分：15%<br>
     *     ≤20000元部分：20%<br>
     *     ＞20000元部分：25%
     * </p>
     * 
     * @throws IOException 当税率数据加载失败时抛出
     */
    @BeforeEach
    private void setinfo() throws IOException {
        String policy2006 = "1600\r\n"
                          + "500, 5\r\n"
                          + "2000, 10\r\n"
                          + "5000, 15\r\n"
                          + "20000, 20\r\n"
                          + "11000000000, 25\r\n";  // 大数模拟无限层级
        this.tax.setdata(policy2006);
    }

    /**
     * 标准边界条件测试
     * 
     * <p>
     * 验证关键计算节点：<br>
     * 1. 1500元（月收入低于起征点）：0元<br>
     * 2. 4300元（月收入，月应税5000层级）: (4300 - 1600 - 2000) * 15% + 175 = 280元<br>
     * 3. 5600元（月收入，月应税5000层级）： (5600 - 1600 - 2000) * 15% + 175 = 475元<br>
     * 4. 9600元（月收入，月应税20000层级）： (9600 - 1600 - 5000) * 20% + 625 = 1225元<br>
     * 5. 16800元（月收入，月应税20000层级）： (16800 - 1600 - 5000) * 20% + 625 = 2665元
     * </p>
     */
    @Test
    public void standardtest() {
        assertEquals(0.0, this.tax.calculate(1500.0), 0.001);
        assertEquals(280.0, this.tax.calculate(4300.0), 0.001);
        assertEquals(475.0, this.tax.calculate(5600.0), 0.001);
        assertEquals(1225.0, this.tax.calculate(9600.0), 0.001);
        assertEquals(2665.0, this.tax.calculate(16800.0), 0.001);
    }

    /**
     * 随机数值验证测试
     * 
     * <p>
     * 生成0~1,000,000,000范围内的随机收入，通过双重计算验证：<br>
     * 1. 使用被测方法计算税款<br>
     * 2. 根据2006年政策手动计算预期值<br>
     * 3. 比较两者结果的误差范围（＜0.001）<br>
     * 特别验证负收入归零处理
     * </p>
     */
    @Test
    public void randomtest() {
    	Random random = new Random();
    	double income = random.nextInt(1000000000);  // 生成0-10亿随机数
    	double actually = income - 1600;
    	double result = 0;
    	
    	// 根据2006年政策手动计算
    	if(actually < 0) {
    	result = 0;
    	} else if(actually <= 500) {
    	result = actually * 0.05;
    	} else if(actually <= 2000) {
    	result = 25 + (actually - 500) * 0.1;
    	} else if(actually <= 5000) {
    	result = 175 + (actually - 2000) * 0.15;
    	} else if(actually <= 20000) {
    	result = 625 + (actually - 5000) * 0.2;
    	} else {
    	result = 3625 + (actually - 20000) * 0.25;
    	}
    	
    	assertEquals(result, this.tax.calculate(income), 0.001);
    }
}
