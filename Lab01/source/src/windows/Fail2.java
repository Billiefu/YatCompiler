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
 * 税率表格式错误提示窗口
 * 
 * <p>
 * 当用户在参数编辑界面提交无效的税率表数据时，
 * 显示错误提示信息并要求重新输入。<br>
 * 本窗口采用模态对话框设计，确保错误必须被处理。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Fail2 extends JFrame {
	
	/** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    
    /** GUI容器组件 */
    private JPanel contentPane;

    /**
     * 错误提示窗口构造器
     * 
     * <p>
     * 初始化包含以下界面元素：<br>
     * - 错误提示标题<br>
     * - 具体税率表错误描述<br>
     * - 返回编辑界面的操作按钮<br>
     * 窗口尺寸固定为259x146像素
     * </p>
     */
    public Fail2() {
        // 窗口基础设置
        setResizable(false);  // 禁用窗口缩放
        setTitle("保存失败");  // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 关闭时终止应用
        setBounds(100, 100, 259, 146);  // 初始位置和尺寸

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 5像素内边距
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局定位

        /* 错误提示标题 */
        JLabel title = new JLabel("保存失败");
        title.setBounds(83, 20, 80, 24);  // X坐标,Y坐标,宽度,高度
        title.setFont(new Font("宋体", Font.PLAIN, 20));  // 设置中文字体
        contentPane.add(title);

        /* 具体错误描述 */
        JLabel errorMessage = new JLabel("税率表出现错误，请检查税率表！");
        errorMessage.setBounds(30, 53, 187, 15);  // 精确定位错误信息
        contentPane.add(errorMessage);

        /* 返回操作按钮 */
        JButton returnButton = new JButton("返回");
        returnButton.setBounds(77, 78, 93, 23);  // 按钮尺寸和位置
        returnButton.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 销毁当前窗口实例，允许用户返回编辑界面修正税率表<br>
             * 释放窗口占用的系统资源
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // 关闭并销毁窗口对象
            }
        });
        contentPane.add(returnButton);
    }
}
