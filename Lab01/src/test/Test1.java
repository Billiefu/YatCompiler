/*
 * 类名：Test1
 * 
 * 作用：使用2006税率标准对程序核心功能进行测试
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.28
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import core.Tax;

class Test1 {
	
	private String info;
	private double income;
	private double result;
	
	private void setinfo() {
		this.info = "1600\r\n"
				  + "500, 5\r\n"
				  + "2000, 10\r\n"
				  + "5000, 15\r\n"
				  + "20000, 20\r\n"
				  + "11000000000, 25\r\n";
	}
	
	@Test
	public void standardtest() {
		/* 进行标准测试 */
		Tax tax = Tax.getInstance();
		this.setinfo();
		tax.setdata(this.info);
		
		this.income = (double)4300;
		this.result = (double)280;
		assertEquals(this.result, tax.calculate(this.income));
	}
	
	@Test
	public void randomtest() {
		/* 进行随机测试 */
		Tax tax = Tax.getInstance();
		this.setinfo();
		tax.setdata(this.info);
		
		Random random = new Random();
		this.income = random.nextInt(1000000000);
		double actually = income - 1600;
		if(actually <= 500) {
			this.result = actually * 0.05;
		} else if(actually <= 2000) {
			this.result = 25 + (actually - 500) * 0.1;
		} else if(actually <= 5000) {
			this.result = 175 + (actually - 2000) * 0.15;
		} else if(actually <= 20000) {
			this.result = 625 + (actually - 5000) * 0.2;
		} else {
			this.result = 3625 + (actually - 20000) * 0.25;
		}
		
		assertEquals(this.result, tax.calculate(this.income));
	}
	
}
