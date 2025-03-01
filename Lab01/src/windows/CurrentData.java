/*
 * 类名：CurrentData
 * 
 * 作用：生成参数查询/编辑界面
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import core.Tax;
import core.Txtpro;

public class CurrentData extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Tax tax = Tax.getInstance();
	
	public CurrentData() {
		// 生成 CurrentData 界面的整体界面框架
		setResizable(false);
		setTitle("个人所得税计算器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 生成 CurrentData 界面的界面标题
		JLabel title = new JLabel("当前计算器设定数据");
		title.setFont(new Font("宋体", Font.PLAIN, 15));
		title.setBounds(89, 20, 145, 31);
		contentPane.add(title);
		
		// 生成界面的主体内容
		
		JLabel jLabel1 = new JLabel("个人所得税起征点");
		jLabel1.setBounds(20, 61, 111, 15);
		contentPane.add(jLabel1);
		
		JTextField textField = new JTextField();
		textField.setBounds(20, 86, 264, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(this.tax.getthreshold());
		
		JLabel jLabel2 = new JLabel("税率表");
		jLabel2.setBounds(20, 117, 111, 15);
		contentPane.add(jLabel2);
		
		JTextPane textPane = new JTextPane();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(textPane);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(20, 142, 264, 143);
		contentPane.add(scrollPane);
		textPane.setText(this.tax.gettaxtable());
		
		// 生成保存按钮
		JButton jButton1 = new JButton("保存");
		jButton1.addActionListener(new ActionListener() {
			/* 保存更改后的起征点及税率表，并完成异常行为的检测 */
			public void actionPerformed(ActionEvent e) {
				String threshold = textField.getText();
				String taxtable = textPane.getText();
				try {
					if(!threshold.matches("-?\\d+(\\.\\d+)?") || Double.valueOf(threshold) < 0) {
						// 对个人所得税起征点进行异常检测
						JFrame frame = new Fail1();
						frame.setVisible(true);
					} else if(!checkformat(taxtable)) {
						// 对税率表进行异常检测
						JFrame frame = new Fail2();
						frame.setVisible(true);
					} else {
						// 无异常则保存至存储文件，并同步更新 Tax 类
						Txtpro.Output(threshold, taxtable);
						tax.setdata(Txtpro.Input());
						JFrame frame = new Check();
						frame.setVisible(true);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		jButton1.setBounds(205, 295, 93, 23);
		contentPane.add(jButton1);
		
		// 生成返回按钮
		JButton jButton2 = new JButton("返回");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});
		jButton2.setBounds(10, 295, 93, 23);
		contentPane.add(jButton2);
	}
	
	// 由于税率表的异常检测逻辑较为复杂，因此另起一私有方法进行检测
	private boolean checkformat(String taxtable) {
		String regexa = "\r\n";
		String regexb = ", ";
		
		String tempa[] = taxtable.split(regexa);
		String tempb[][] = new String[tempa.length][2];
		for(int i = 0; i < tempa.length; i ++) {
			tempb[i] = tempa[i].split(regexb);
		}
		
		double[][] result = new double[tempb.length][2];
		
		for(int i = 0; i < tempb.length; i ++) {
			// 税率表中的字符串为非数字串为异常行为
			if(!tempb[i][0].matches("-?\\d+(\\.\\d+)?") || !tempb[i][1].matches("-?\\d+(\\.\\d+)?")) {
				return false;
			}
			result[i][0] = Double.valueOf(tempb[i][0]);
			result[i][1] = Double.valueOf(tempb[i][1]) / 100;
		}
		
		// 第一级别的起征点为负数为异常行为
		if(result[0][0] <= 0) {
			return false;
		}
		
		// 相邻级别之间不连贯为异常行为
		for(int i = 1; i < result.length; i ++) {
			if(result[i-1][0] >= result[i][0]) {
				return false;
			}
		}
		
		// 三层检测无异常则通过异常检测
		return true;
	}
	
}
