/*
 * 类名：Fail1
 * 
 * 作用：生成个人所得税起征点异常的保存失败界面
 * 
 * Author：Fu Tszkok
 * 
 * Date：2025.2.27
 */

package windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Fail1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Fail1() {
		// 生成 Fail2 界面的整体界面框架
		setResizable(false);
		setTitle("保存失败");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 259, 146);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 生成 Fail1 界面的界面标题
		JLabel title = new JLabel("保存失败");
		title.setBounds(83, 20, 80, 24);
		title.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(title);
		
		// 生成 Fail2 界面的异常错误提示
		JLabel hint = new JLabel("个人所得税起征点出现错误！");
		hint.setBounds(40, 53, 166, 15);
		contentPane.add(hint);
		
		// 生成返回按钮
		JButton jButton = new JButton("返回");
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jButton.setBounds(77, 78, 93, 23);
		contentPane.add(jButton);
	}
	
}
