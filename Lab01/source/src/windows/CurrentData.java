package windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import core.Tax;

/**
 * 税务参数管理界面类
 * 
 * <p>
 * 实现个人所得税参数的可视化编辑功能，支持：<br>
 * - 实时展示当前起征点及税率表；<br>
 * - 参数格式有效性验证；<br>
 * - 参数动态更新与持久化存储
 * </p>
 * 
 * @author 傅祉珏
 * @created 2025年2月27日
 * @lastUpdated 2025年3月7日
 * 
 */
public class CurrentData extends JFrame {
    
	/** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    
    /** GUI容器组件 */
    private JPanel contentPane;
    
    /** 获取税率计算器 */
    private Tax tax = Tax.getInstance();

    /**
     * 参数管理界面构造器
     * 
     * <p>
     * 初始化包含以下组件的编辑界面：<br>
     * - 起征点输入框（带当前值展示）；<br>
     * - 税率表文本编辑区（带滚动条）；<br>
     * - 保存/返回功能按钮
     * </p>
     */
    public CurrentData() {
        // 窗口基础设置
        setResizable(false);
        setTitle("个人所得税计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 320, 363);

        // 主内容面板初始化
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 界面标题组件
        JLabel title = new JLabel("当前计算器设定数据");
        title.setFont(new Font("宋体", Font.PLAIN, 15));
        title.setBounds(89, 20, 145, 31);
        contentPane.add(title);

        /* 起征点编辑区域 */
        JLabel thresholdLabel = new JLabel("个人所得税起征点");
        thresholdLabel.setBounds(20, 61, 111, 15);
        contentPane.add(thresholdLabel);
        
        JTextField thresholdField = new JTextField();
        thresholdField.setBounds(20, 86, 264, 21);
        thresholdField.setColumns(10);
        thresholdField.setText(this.tax.getthreshold());  // 初始化当前值
        contentPane.add(thresholdField);

        /* 税率表编辑区域 */
        JLabel tableLabel = new JLabel("税率表");
        tableLabel.setBounds(20, 117, 111, 15);
        contentPane.add(tableLabel);
        
        JTextPane tableEditor = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(tableEditor);
        scrollPane.setBounds(20, 142, 264, 143);
        tableEditor.setText(this.tax.gettaxtable());  // 初始化当前值
        contentPane.add(scrollPane);

        // 功能按钮区域
        createSaveButton(thresholdField, tableEditor);
        createReturnButton();
    }

    /**
     * 创建保存功能按钮
     * 
     * @param thresholdField 起征点输入框组件
     * @param tableEditor 税率表编辑组件
     */
    private void createSaveButton(JTextField thresholdField, JTextPane tableEditor) {
        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(205, 295, 93, 23);
        
        saveBtn.addActionListener(new ActionListener() {
            /**
             * 保存操作事件处理
             * 
             * <p>
             * 执行参数验证流程：<br>
             * 1. 起征点有效性验证（正数校验）；<br>
             * 2. 税率表格式验证（数值校验、级数逻辑校验）；<br>
             * 3. 通过验证后更新核心数据并持久化
             * </p>
             */
            public void actionPerformed(ActionEvent e) {
                String threshold = thresholdField.getText();
                String taxtable = tableEditor.getText();
                
                // 起征点验证
                if(!isValidThreshold(threshold)) {
                    new Fail1().setVisible(true);
                    return;
                }
                
                // 税率表验证
                if(!checkformat(taxtable)) {
                    new Fail2().setVisible(true);
                    return;
                }
                
                // 数据更新
                try {
                    tax.setdata(threshold, taxtable);
                    new Check().setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(saveBtn);
    }

    /**
     * 创建返回功能按钮
     */
    private void createReturnButton() {
        JButton returnBtn = new JButton("返回");
        returnBtn.setBounds(10, 295, 93, 23);
        
        returnBtn.addActionListener(e -> {
            new Home().setVisible(true);
            dispose();
        });
        contentPane.add(returnBtn);
    }

    /**
     * 起征点有效性验证
     * 
     * @param input 用户输入的起征点字符串
     * @return 验证结果（true=有效）
     */
    private boolean isValidThreshold(String input) {
        return input.matches("\\d+(\\.\\d+)?") && Double.parseDouble(input) > 0;
    }

    /**
     * 税率表格式验证
     * 
     * <p>
     * 执行四级校验：<br>
     * 1. 行级分隔符有效性；<br>
     * 2. 数值格式有效性；<br>
     * 3. 首级金额有效性（>0）；<br>
     * 4. 级数连贯性校验（逐级递增）
     * </p>
     *
     * @param taxtable 税率表原始字符串
     * @return 验证结果（true=有效）
     */
    private boolean checkformat(String taxtable) {
        final String LINE_SEP = "\r\n";
        final String DATA_SEP = ", ";
        
        try {
            String[] lines = taxtable.split(LINE_SEP);
            double[][] taxData = new double[lines.length][2];
            
            for(int i = 0; i < lines.length; i++) {
                String[] values = lines[i].split(DATA_SEP);
                // 字段完整性校验
                if(values.length != 2) return false;
                
                // 数值格式校验
                if(!values[0].matches("\\d+(\\.\\d+)?") || 
                   !values[1].matches("\\d+(\\.\\d+)?")) {
                    return false;
                }
                
                taxData[i][0] = Double.parseDouble(values[0]);
                taxData[i][1] = Double.parseDouble(values[1]);
                
                // 首级校验
                if(i == 0 && taxData[i][0] <= 0) return false;
                
                // 级数递增校验
                if(i > 0 && taxData[i-1][0] >= taxData[i][0]) {
                    return false;
                }
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
