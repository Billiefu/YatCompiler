package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

/**
 * 开发者信息展示界面
 * <p>
 * 本界面显示项目开发者信息及致谢内容，
 * 提供返回主界面的操作入口。
 * 采用带滚动条的文本面板确保信息完整展示。
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastModified 2025年3月7日
 */
public class Developer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * 开发者界面构造器
     * <p>
     * 初始化包含以下元素的界面：
     * - 带滚动条的开发者信息展示区
     * - 返回主界面功能按钮
     * 窗口尺寸固定为320x240像素
     */
    public Developer() {
        // 窗口基础设置
        setResizable(false);  // 禁止窗口缩放
        setTitle("个人所得税计算器");  // 窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 关闭时退出程序
        setBounds(100, 100, 320, 240);  // 初始位置和尺寸

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 5像素内边距
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局

        /* 开发者信息展示区域 */
        JTextPane infoPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(infoPane);  // 添加滚动支持
        scrollPane.setBounds(10, 10, 288, 155);  // 滚动面板定位
        contentPane.add(scrollPane);

        // 设置格式化开发者信息
        infoPane.setText(buildDeveloperInfo());

        /* 返回功能按钮 */
        JButton returnButton = new JButton("返回");
        returnButton.setBounds(0, 182, 93, 23);  // 按钮定位
        returnButton.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * <p>
             * 创建并显示主界面窗口，
             * 销毁当前开发者信息窗口
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home().setVisible(true);  // 显示主界面
                dispose();  // 关闭当前窗口
            }
        });
        contentPane.add(returnButton);
    }

    /**
     * 构建开发者信息文本
     * 
     * @return 格式化后的开发者信息字符串
     */
    private String buildDeveloperInfo() {
        return "==============================\r\n" 
             + "                       开发者名单\r\n" 
             + "\r\n"
             + "           开发者：傅祉珏\r\n"
             + "           指导老师：李文军 教授\r\n"
             + "==============================\r\n"
             + "\r\n"
             + "感谢所有为这个项目做出贡献的人！\r\n"
             + "傅祉珏 留";
    }
}
