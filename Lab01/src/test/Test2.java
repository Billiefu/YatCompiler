/*
 * 类名：Test2
 * 
 * 作用：使用2019税率标准对程序核心功能进行测试
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

class Test2 {
	
	private String info;
	private double income;
	private double result;
	
	private void setinfo() {
		this.info = "5000\r\n"
				  + "36000, 3\r\n"
				  + "144000, 10\r\n"
				  + "300000, 20\r\n"
				  + "420000, 25\r\n"
				  + "660000, 30\r\n"
				  + "960000, 35\r\n"
				  + "11000000000, 45\r\n";
	}
	
	@Test
	public void standardtest() {
		/* 进行标准测试 */
		Tax tax = Tax.getInstance();
		this.setinfo();
		tax.setdata(this.info);
		
		this.income = (double)540000;
		this.result = (double)107580;
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
		double actually = income - 5000;
		if(actually <= 36000) {
			this.result = actually * 0.03;
		} else if(actually <= 144000) {
			this.result = 1080 + (actually - 36000) * 0.1;
		} else if(actually <= 300000) {
			this.result = 11880 + (actually - 144000) * 0.2;
		} else if(actually <= 420000) {
			this.result = 43080 + (actually - 300000) * 0.25;
		} else if(actually <= 660000){
			this.result = 73080 + (actually - 420000) * 0.3;
		} else if(actually <= 960000){
			this.result = 145080 + (actually - 660000) * 0.35;
		} else {
			this.result = 250080 + (actually - 960000) * 0.45;
		}
		
		assertEquals(this.result, tax.calculate(this.income));
	}

}
