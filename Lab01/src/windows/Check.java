package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 保存成功提示窗口
 * <p>
 * 当用户在参数编辑界面完成合法数据保存时，显示操作成功的确认界面。
 * 采用模态窗口设计，提供简洁的成功反馈和返回操作。
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastModified 2025年3月7日
 */
public class Check extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * 成功提示窗口构造器
     * <p>
     * 初始化包含以下元素的界面：
     * - 保存成功提示标题
     * - 返回功能按钮
     * 窗口设置为固定尺寸且不可调整大小
     */
    public Check() {
        // 窗口基础设置
        setResizable(false);  // 禁止调整窗口尺寸
        setTitle("保存成功");  // 窗口标题栏显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 关闭时退出程序
        setBounds(100, 100, 259, 146);  // 初始位置和尺寸

        // 主内容面板配置
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 内边距设置
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局

        // 成功提示标题
        JLabel title = new JLabel("保存成功");
        title.setBounds(83, 20, 80, 24);  // 定位坐标和尺寸
        title.setFont(new Font("宋体", Font.PLAIN, 20));  // 设置中文字体
        contentPane.add(title);

        // 返回功能按钮
        JButton returnButton = new JButton("返回");
        returnButton.setBounds(77, 70, 93, 23);  // 按钮定位
        returnButton.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * <p>
             * 关闭当前提示窗口，释放窗口资源
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // 销毁当前窗口实例
            }
        });
        contentPane.add(returnButton);
    }
}
