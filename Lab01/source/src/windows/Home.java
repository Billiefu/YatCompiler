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
 * 应用程序主界面
 * 
 * <p>
 * 作为程序核心导航界面，提供功能模块跳转入口：<br>
 * - 参数查询/编辑界面<br>
 * - 个税计算界面<br>
 * - 开发者信息界面<br>
 * - 程序退出功能<br>
 * 采用中心化布局保持界面整洁。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Home extends JFrame {

	/** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    
    /** GUI容器组件 */
    private JPanel contentPane;

    /**
     * 主界面构造器
     * 
     * <p>
     * 初始化包含以下界面元素：<br>
     * - 应用程序标题<br>
     * - 四个功能导航按钮<br>
     * 窗口尺寸固定为320x240像素
     * </p>
     */
    public Home() {
        // 窗口基础设置
        setResizable(false);  // 禁用窗口缩放
        setTitle("个人所得税计算器");  // 窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 关闭时退出程序
        setBounds(100, 100, 320, 240);  // 初始位置和尺寸

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 5像素内边距
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局

        /* 界面标题 */
        JLabel title = new JLabel("个人所得税计算器");
        title.setFont(new Font("宋体", Font.PLAIN, 20));  // 中文字体设置
        title.setBounds(70, 20, 175, 30);  // 定位坐标和尺寸
        contentPane.add(title);

        /* 功能导航按钮组 */
        createDataViewButton();    // 数据查看按钮
        createCalculatorButton();  // 计算面板按钮
        createDeveloperButton();   // 开发者信息按钮
        createExitButton();         // 程序退出按钮
    }

    /**
     * 创建数据查看按钮
     */
    private void createDataViewButton() {
        JButton dataBtn = new JButton("查看数据");
        dataBtn.setBounds(107, 78, 93, 23);  // 居中定位
        dataBtn.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 打开参数管理界面并关闭当前窗口，
             * 实现界面切换的无缝衔接
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new CurrentData().setVisible(true);  // 显示参数界面
                dispose();  // 关闭主界面
            }
        });
        contentPane.add(dataBtn);
    }

    /**
     * 创建计算面板按钮
     */
    private void createCalculatorButton() {
        JButton calcBtn = new JButton("计算面板");
        calcBtn.setBounds(107, 123, 93, 23);  // 垂直居中
        calcBtn.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 启动计算器界面并释放主界面资源，
             * 优化内存使用效率
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new Calculate().setVisible(true);  // 打开计算界面
                dispose();  // 释放当前窗口
            }
        });
        contentPane.add(calcBtn);
    }

    /**
     * 创建开发者信息按钮
     */
    private void createDeveloperButton() {
        JButton devBtn = new JButton("开发者");
        devBtn.setBounds(215, 182, 93, 23);  // 右下定位
        devBtn.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 显示开发者信息窗口，
             * 保持主窗口的独立性
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new Developer().setVisible(true);  // 展示开发者信息
                dispose();  // 关闭主界面
            }
        });
        contentPane.add(devBtn);
    }

    /**
     * 创建程序退出按钮
     */
    private void createExitButton() {
        JButton exitBtn = new JButton("退出");
        exitBtn.setBounds(0, 182, 93, 23);  // 左下定位
        exitBtn.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * 
             * <p>
             * 直接终止应用程序运行，
             * 适用于快速退出场景
             * </p>
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // 关闭当前窗口
            }
        });
        contentPane.add(exitBtn);
    }
}
