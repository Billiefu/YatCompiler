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
 * 起征点格式错误提示窗口
 * 
 * <p>
 * 当用户在参数编辑界面输入无效的个税起征点数据时，
 * 显示错误提示信息并提供返回操作。<br>
 * 本窗口采用模态对话框设计，强制用户处理输入错误。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Fail1 extends JFrame {
	
	/** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    
    /** GUI容器组件 */
    private JPanel contentPane;

    /**
     * 错误提示窗口构造器
     * 
     * <p>
     * 初始化包含以下元素的界面：<br>
     * - 错误提示标题<br>
     * - 具体错误描述<br>
     * - 返回操作按钮<br>
     * 窗口尺寸固定为259x146像素
     * </p>
     */
    public Fail1() {
        // 窗口基础设置
        setResizable(false);  // 禁止窗口缩放
        setTitle("保存失败");  // 窗口标题栏文字
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 关闭时终止程序
        setBounds(100, 100, 259, 146);  // 初始位置和尺寸

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 5像素内边距
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局定位

        /* 错误提示标题 */
        JLabel title = new JLabel("保存失败");
        title.setBounds(83, 20, 80, 24);  // X,Y,W,H
        title.setFont(new Font("宋体", Font.PLAIN, 20));  // 中文字体设置
        contentPane.add(title);

        /* 具体错误描述 */
        JLabel errorDetail = new JLabel("个人所得税起征点出现错误！");
        errorDetail.setBounds(40, 53, 166, 15);  // 精确像素定位
        contentPane.add(errorDetail);

        /* 返回操作按钮 */
        JButton returnButton = new JButton("返回");
        returnButton.setBounds(77, 78, 93, 23);  // 按钮尺寸位置
        returnButton.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 关闭当前错误提示窗口，释放系统资源，
             * 允许用户返回编辑界面修正输入
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // 销毁窗口实例
            }
        });
        contentPane.add(returnButton);
    }
}
