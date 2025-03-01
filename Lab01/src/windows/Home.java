/*
 * 类名：Home
 * 
 * 作用：生成计算器主界面
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Home() {
		// 生成 Home 界面的整体界面框架
		setResizable(false);
		setTitle("个人所得税计算器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 生成 Home 界面的界面标题
		JLabel title = new JLabel("个人所得税计算器");
		title.setFont(new Font("宋体", Font.PLAIN, 20));
		title.setBounds(70, 20, 175, 30);
		contentPane.add(title);
		
		// 生成 Home 界面的各种按钮以跳转至其它界面
		
		JButton jButton1 = new JButton("查看数据");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new CurrentData();
				frame.setVisible(true);
				dispose();
			}
		});
		jButton1.setBounds(107, 78, 93, 23);
		contentPane.add(jButton1);
		
		JButton jButton2 = new JButton("计算面板");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new Calculate();
				frame.setVisible(true);
				dispose();
			}
		});
		jButton2.setBounds(107, 123, 93, 23);
		contentPane.add(jButton2);
		
		JButton jButton3 = new JButton("开发者");
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame frame = new Developer();
				frame.setVisible(true);
				dispose();
			}
		});
		jButton3.setBounds(215, 182, 93, 23);
		contentPane.add(jButton3);
		
		JButton jButton4 = new JButton("退出");
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jButton4.setBounds(0, 182, 93, 23);
		contentPane.add(jButton4);
	}
	
}
