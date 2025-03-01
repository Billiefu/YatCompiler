/*
 * 类名：Developer
 * 
 * 作用：生成开发者界面
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class Developer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Developer() {
		// 生成 Developer 界面的整体界面框架
		setResizable(false);
		setTitle("个人所得税计算器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 生成开发者信息显示栏
		JTextPane textPane = new JTextPane();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(textPane);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 10, 288, 155);
		contentPane.add(scrollPane);
		
		// 将需要显示的信息显示在显示栏内
		textPane.setText(
			"==============================\n" + 
			"                       开发者名单\n" + 
			"\n" +
			"           开发者：傅祉珏\n" +
			"           指导老师：李文军 教授\n" +
			"==============================\n" +
			"\n" +
			"感谢所有为这个项目做出贡献的人！\n" +
			"傅祉珏 留"
		);
		
		// 生成返回按钮
		JButton jButton = new JButton("返回");
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});
		jButton.setBounds(0, 182, 93, 23);
		contentPane.add(jButton);
	}
	
}
