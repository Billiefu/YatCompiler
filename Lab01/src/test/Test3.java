/*
 * 类名：Test3
 * 
 * 作用：使用2011税率标准对程序核心功能进行测试
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

class Test3 {
	
	private String info;
	private double income;
	private double result;
	
	private void setinfo() {
		this.info = "3500\r\n"
				  + "1500, 3\r\n"
				  + "4500, 10\r\n"
				  + "9000, 20\r\n"
				  + "35000, 25\r\n"
				  + "55000, 30\r\n"
				  + "80000, 35\r\n"
				  + "11000000000, 45\r\n";
	}
	
	@Test
	public void standardtest() {
		/* 进行标准测试 */
		Tax tax = Tax.getInstance();
		this.setinfo();
		tax.setdata(this.info);
		
		this.income = (double)12500;
		this.result = (double)1245;
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
		double actually = income - 3500;
		if(actually <= 1500) {
			this.result = actually * 0.03;
		} else if(actually <= 4500) {
			this.result = 45 + (actually - 1500) * 0.1;
		} else if(actually <= 9000) {
			this.result = 345 + (actually - 4500) * 0.2;
		} else if(actually <= 35000) {
			this.result = 1245 + (actually - 9000) * 0.25;
		} else if(actually <= 55000){
			this.result = 7745 + (actually - 35000) * 0.3;
		} else if(actually <= 80000){
			this.result = 13745 + (actually - 55000) * 0.35;
		} else {
			this.result = 22495+ (actually - 80000) * 0.45;
		}
		
		assertEquals(this.result, tax.calculate(this.income));
	}
	
}
