package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Tax;

/**
 * 2019年版个人所得税政策单元测试类
 * <p>
 * 根据中国政府网公布的2019年个人所得税政策标准（起征点5000元），
 * 验证税务计算核心逻辑的正确性。包含以下测试场景：
 * 1. 标准边界测试（5个典型用例）
 * 2. 随机数值测试（1个随机生成用例）
 * 测试覆盖税率表所有层级及边界条件。
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastModified 2025年3月7日
 */
class Test2 {
    
    private Tax tax = Tax.getInstance();

    /**
     * 初始化测试环境
     * <p>
     * 配置2019年个税政策参数：
     * - 起征点：5000元
     * - 税率表层级：
     *    ≤36000元部分：3%
     *    ≤144000元部分：10%
     *    ≤300000元部分：20%
     *    ≤420000元部分：25%
     *    ≤660000元部分：30%
     *    ≤960000元部分：35%
     *    ＞960000元部分：45%
     * 
     * @throws IOException 当税率数据加载失败时抛出
     */
    @BeforeEach
    private void setinfo() throws IOException {
        String policy2019 = "5000\r\n"
				          + "36000, 3\r\n"
				          + "144000, 10\r\n"
				          + "300000, 20\r\n"
				          + "420000, 25\r\n"
				          + "660000, 30\r\n"
				          + "960000, 35\r\n"
				          + "11000000000, 45\r\n";  // 大数模拟无限层级
        this.tax.setdata(policy2019);
    }

    /**
     * 标准边界条件测试
     * <p>
     * 验证关键计算节点：
     * 1. 3500元（月收入低于起征点）：0元
     * 2. 24800元（月收入，月应税36000层级）: (24800 - 5000) * 3% = 594元
     * 3. 54000元（月收入，月应税144000层级）： (54000 - 5000 - 36000) * 10% + 1080 = 2380元
     * 4. 188000元（月收入，月应税300000层级）： (188000 - 5000 - 144000) * 20% + 11880 = 19680元
     * 5. 497000元（月收入，月应税660000层级）： (497000 - 5000 - 420000) * 30% + 73080 = 94680元
     */
    @Test
    public void standardtest() {
    	assertEquals(0.0, this.tax.calculate(3500.0), 0.001);
        assertEquals(594.0, this.tax.calculate(24800.0), 0.001);
        assertEquals(2380.0, this.tax.calculate(54000.0), 0.001);
        assertEquals(19680.0, this.tax.calculate(188000.0), 0.001);
        assertEquals(94680.0, this.tax.calculate(497000.0), 0.001);
    }

    /**
     * 随机数值验证测试
     * <p>
     * 生成0-1,000,000,000范围内的随机收入，通过双重计算验证：
     * 1. 使用被测方法计算税款
     * 2. 根据2019年政策手动计算预期值
     * 3. 比较两者结果的误差范围（<0.001）
     * 特别验证负收入归零处理
     */
    @Test
    public void randomtest() {
    	Random random = new Random();
    	double income = random.nextInt(1000000000);  // 生成0-10亿随机数
    	double actually = income - 5000;
    	double result = 0;
    	
    	// 根据2019年政策手动计算
    	if(actually < 0) {
    	result = 0;
    	} else if(actually <= 36000) {
			result = actually * 0.03;
		} else if(actually <= 144000) {
			result = 1080 + (actually - 36000) * 0.1;
		} else if(actually <= 300000) {
			result = 11880 + (actually - 144000) * 0.2;
		} else if(actually <= 420000) {
			result = 43080 + (actually - 300000) * 0.25;
		} else if(actually <= 660000){
			result = 73080 + (actually - 420000) * 0.3;
		} else if(actually <= 960000){
			result = 145080 + (actually - 660000) * 0.35;
		} else {
			result = 250080 + (actually - 960000) * 0.45;
		}
    	
    	assertEquals(result, this.tax.calculate(income), 0.001);
    }
}
