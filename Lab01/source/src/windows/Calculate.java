package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.Tax;

/**
 * 个人所得税计算界面实现类
 * 
 * <p>
 * 提供可视化计算界面，集成收入输入、税款计算、结果展示功能，
 * 实现与税务计算核心模块的交互操作。
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class Calculate extends JFrame {
	
	/** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    
    /** GUI容器组件 */
    private JPanel contentPane;
    
    /** 获取税率计算器 */
    private Tax tax = Tax.getInstance();

    /**
     * 计算界面构造器
     * 
     * <p>
     * 初始化窗口属性，创建界面元素，包含：<br>
     * - 收入输入框；<br>
     * - 税款展示区域；<br>
     * - 计算/返回功能按钮；<br>
     * 实现界面元素的事件绑定
     * </p>
     */
    public Calculate() {
        // 窗口基础设置
        setResizable(false);  // 禁止调整窗口大小
        setTitle("个人所得税计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置关闭行为
        setBounds(100, 100, 320, 240);  // 窗口位置和尺寸

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 设置边距
        setContentPane(contentPane);
        contentPane.setLayout(null);  // 使用绝对布局

        // 界面标题组件
        JLabel title = new JLabel("个人所得税计算器");
        title.setFont(new Font("宋体", Font.PLAIN, 20));  // 设置标题字体
        title.setBounds(70, 20, 175, 30);
        contentPane.add(title);

        /* 收入输入区域 */
        JLabel incomeLabel = new JLabel("工资薪金总收入");
        incomeLabel.setBounds(20, 61, 111, 15);
        contentPane.add(incomeLabel);
        
        JTextField incomeField = new JTextField();
        incomeField.setBounds(20, 86, 264, 21);
        incomeField.setColumns(10);  // 设置推荐列数
        contentPane.add(incomeField);

        /* 税款展示区域 */
        JLabel resultLabel = new JLabel("个人所得税");
        resultLabel.setBounds(20, 117, 111, 15);
        contentPane.add(resultLabel);
        
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);  // 设置为只读
        resultArea.setBounds(20, 142, 264, 22);
        contentPane.add(resultArea);

        // 计算功能按钮
        JButton calcButton = createCalcButton(incomeField, resultArea);
        contentPane.add(calcButton);

        // 返回功能按钮
        JButton returnButton = createReturnButton();
        contentPane.add(returnButton);
    }

    /**
     * 创建计算功能按钮
     * 
     * @param inputField 收入输入框组件
     * @param outputArea 结果展示区域
     * @return 配置完成的计算按钮
     */
    private JButton createCalcButton(JTextField inputField, JTextArea outputArea) {
        JButton button = new JButton("计算");
        button.setBounds(215, 182, 93, 23);
        
        button.addActionListener(new ActionListener() {
            /**
             * 按钮点击事件处理
             * <p>
             * 执行以下操作：<br>
             * 1. 验证输入格式有效性；<br>
             * 2. 执行税款计算；<br>
             * 3. 展示计算结果或错误信息
             * </p>
             */
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                
                // 输入格式验证
                if(!input.matches("-?\\d+(\\.\\d+)?")) {
                    outputArea.setText("请输入有效数字！");
                    return;
                }
                
                double income = Double.parseDouble(input);
                if(income < 0) {
                    outputArea.setText("收入不可为负值！");
                    return;
                }
                
                // 执行税款计算并格式化结果
                double taxAmount = tax.calculate(income);
                outputArea.setText(String.format("%.2f 元", taxAmount));
            }
        });
        
        return button;
    }

    /**
     * 创建返回功能按钮
     * 
     * @return 配置完成的返回按钮
     */
    private JButton createReturnButton() {
        JButton button = new JButton("返回");
        button.setBounds(0, 182, 93, 23);
        
        button.addActionListener(new ActionListener() {
            /**
             * 返回主界面操作
             * 
             * 关闭当前窗口并重新显示主界面
             */
            public void actionPerformed(ActionEvent e) {
                new Home().setVisible(true);  // 显示主界面
                dispose();  // 关闭当前窗口
            }
        });
        
        return button;
    }
    
}
