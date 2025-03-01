/*
 * 类名：Calculate
 * 
 * 作用：生成计算器界面
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.Tax;

import javax.swing.JTextArea;

public class Calculate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Tax tax = Tax.getInstance();

	public Calculate() {
		// 生成 Calculate 界面的整体界面框架
		setResizable(false);
		setTitle("个人所得税计算器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 生成 Calculate 界面的界面标题
		JLabel title = new JLabel("个人所得税计算器");
		title.setFont(new Font("宋体", Font.PLAIN, 20));
		title.setBounds(70, 20, 175, 30);
		contentPane.add(title);
		
		// 生成界面的主体内容
		
		JLabel jLabel1 = new JLabel("工资薪金总收入");
		jLabel1.setBounds(20, 61, 111, 15);
		contentPane.add(jLabel1);
		
		JTextField textField = new JTextField();
		textField.setBounds(20, 86, 264, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel jLabel2 = new JLabel("个人所得税");
		jLabel2.setBounds(20, 117, 111, 15);
		contentPane.add(jLabel2);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(20, 142, 264, 22);
		contentPane.add(textArea);
		
		// 生成计算按钮
		JButton jButton1 = new JButton("计算");
		jButton1.addActionListener(new ActionListener() {
			/* 实现计算功能，并完成异常行为的检测 */
			public void actionPerformed(ActionEvent e) {
				String income = textField.getText();
				if(!income.matches("-?\\d+(\\.\\d+)?")) {
					textArea.setText("请输入一个数字！");
				} else if(Double.valueOf(income) < 0) {
					textArea.setText("请输入一个正收入！");
				} else {
					textArea.setText(String.valueOf(tax.calculate(Double.valueOf(income))));
				}
			}
		});
		jButton1.setBounds(215, 182, 93, 23);
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
		jButton2.setBounds(0, 182, 93, 23);
		contentPane.add(jButton2);
	}
	
}
