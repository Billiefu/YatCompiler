/*
 * 类名：Tax
 * 
 * 作用：整个程序的核心
 *      用于计算个人所得税
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package core;

import java.text.DecimalFormat;

public class Tax {
	
	private static final Tax Instance = new Tax();
	private double threshold;			// 个人所得税起征点
	private double[][] taxtable;		// 税率表
	
	// 单例模式，防止出现多重税率政策的情况。
	private Tax() {
		this.threshold = 0;
		this.taxtable = null;
	}
	public static Tax getInstance() {
		return Instance;
	}
	
	public void setdata(String info) {
		/* 导入数据，仅使用读取数据的入，防止数据篡改 */
		String regexa = "\r\n";
		String regexb = ", ";
		
		String temp[] = info.split(regexa);
		String result[][] = new String[temp.length][10];
		for(int i = 0; i < temp.length; i ++) {
			result[i] = temp[i].split(regexb);
		}
		
		this.threshold = Double.valueOf(result[0][0]);
		this.taxtable = new double[result.length-1][2];
		
		for(int i = 1; i < result.length; i ++) {
			this.taxtable[i-1][0] = Double.valueOf(result[i][0]);
			this.taxtable[i-1][1] = Double.valueOf(result[i][1]) / 100;
		}
	}
	
	/* 输出类中的数据，以让用户知晓当前税率政策情况 */
	public String getthreshold() {
		return String.valueOf(this.threshold);
	}
	public String gettaxtable() {
		// 防止大数目出现 6E10 之类的情况，而是显示完整数目。
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(0);
        df.setGroupingUsed(false);
        
        // 数组组合成字符串进行输出
		StringBuffer taxtable = new StringBuffer("");
		for(int i = 0; i < this.taxtable.length; i ++) {
			taxtable.append(df.format(this.taxtable[i][0]));
			taxtable.append(", ");
			taxtable.append(df.format(this.taxtable[i][1] * 100));
			taxtable.append("\r\n");
		}
		return taxtable.toString();
	}
	
	public double calculate(double income) {
		/* 进行税率的计算 */
		// 未达到个人所得税起征点，无需缴税。
		if (income < threshold) {
			return 0;
		}
		income -= threshold;
		
		double temp = income;
		double tax = 0;
		
		for(int i = 0; i < taxtable.length; i ++) {
			// 得到该级别的下界（即上一级别的上界）
			double lowerbound = 0;
			if(i != 0) {
				lowerbound = taxtable[i-1][0];
			}
			
			// 计算该级别需缴纳的税款
			if(income <= taxtable[i][0]) {
				tax += temp * taxtable[i][1];
				break;
			} else {
				tax += (taxtable[i][0] - lowerbound) * taxtable[i][1];
				temp = income - taxtable[i][0];
			}
		}
		return tax;
	}
	
}
