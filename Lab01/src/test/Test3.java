package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Tax;

/**
 * 2011年版个人所得税政策单元测试类
 * <p>
 * 根据中国政府网公布的2011年个人所得税政策标准（起征点1600元），
 * 验证税务计算核心逻辑的正确性。包含以下测试场景：
 * 1. 标准边界测试（5个典型用例）
 * 2. 随机数值测试（1个随机生成用例）
 * 测试覆盖税率表所有层级及边界条件。
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastModified 2025年3月7日
 */
class Test3 {
    
    private Tax tax = Tax.getInstance();

    /**
     * 初始化测试环境
     * <p>
     * 配置2011年个税政策参数：
     * - 起征点：1600元
     * - 税率表层级：
     *    ≤1500元部分：3%
     *    ≤4500元部分：10%
     *    ≤9000元部分：20%
     *    ≤35000元部分：25%
     *    ≤55000元部分：30%
     *    ≤80000元部分：35%
     *    ＞960000元部分：45%
     * 
     * @throws IOException 当税率数据加载失败时抛出
     */
    @BeforeEach
    private void setinfo() throws IOException {
        String policy2011 = "3500\r\n"
			              + "1500, 3\r\n"
			              + "4500, 10\r\n"
		                  + "9000, 20\r\n"
		                  + "35000, 25\r\n"
		                  + "55000, 30\r\n"
		                  + "80000, 35\r\n"
		                  + "11000000000, 45\r\n";  // 大数模拟无限层级
        this.tax.setdata(policy2011);
    }

    /**
     * 标准边界条件测试
     * <p>
     * 验证关键计算节点：
     * 1. 1500元（月收入低于起征点）：0元
     * 2. 4300元（月收入，月应税1500层级）: (4300 - 3500) * 3% = 24元
     * 3. 5600元（月收入，月应税4500层级）： (5600 - 3500 - 1500) * 10% + 45 = 105元
     * 4. 9600元（月收入，月应税9000层级）： (9600 - 3500 - 4500) * 20% + 345 = 664元
     * 5. 16800元（月收入，月应税35000层级）： (16800 - 3500 - 9000) * 25% + 1245 = 2320元
     */
    @Test
    public void standardtest() {
        assertEquals(0.0, this.tax.calculate(1500.0), 0.001);
        assertEquals(24.0, this.tax.calculate(4300.0), 0.001);
        assertEquals(105.0, this.tax.calculate(5600.0), 0.001);
        assertEquals(665.0, this.tax.calculate(9600.0), 0.001);
        assertEquals(2320.0, this.tax.calculate(16800.0), 0.001);
    }

    /**
     * 随机数值验证测试
     * <p>
     * 生成0-1,000,000,000范围内的随机收入，通过双重计算验证：
     * 1. 使用被测方法计算税款
     * 2. 根据2011年政策手动计算预期值
     * 3. 比较两者结果的误差范围（<0.001）
     * 特别验证负收入归零处理
     */
    @Test
    public void randomtest() {
    	Random random = new Random();
    	double income = random.nextInt(1000000000);  // 生成0-10亿随机数
    	double actually = income - 3500;
    	double result = 0;
    	
    	// 根据2011年政策手动计算
    	if(actually < 0) {
			result = 0;
		} else if(actually <= 1500) {
			result = actually * 0.03;
		} else if(actually <= 4500) {
			result = 45 + (actually - 1500) * 0.1;
		} else if(actually <= 9000) {
			result = 345 + (actually - 4500) * 0.2;
		} else if(actually <= 35000) {
			result = 1245 + (actually - 9000) * 0.25;
		} else if(actually <= 55000){
			result = 7745 + (actually - 35000) * 0.3;
		} else if(actually <= 80000){
			result = 13745 + (actually - 55000) * 0.35;
		} else {
			result = 22495+ (actually - 80000) * 0.45;
		}
    	
    	assertEquals(result, this.tax.calculate(income), 0.001);
    }
}
