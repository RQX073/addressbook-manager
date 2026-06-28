package addressbook;

import javax.swing.*;
import java.awt.*;

/**
 * 添加 / 修改联系人的对话框
 */
public class PersonDialog extends JDialog {

    private JTextField catField, nameField, phoneField, emailField;
    private boolean ok = false;
    private Person person;

    public PersonDialog(Frame owner, String title, Person existing) {
        super(owner, title, true);

        // 主面板
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 类别
        panel.add(new JLabel("类别："));
        catField = new JTextField(15);
        panel.add(catField);

        // 姓名
        panel.add(new JLabel("姓名："));
        nameField = new JTextField(15);
        panel.add(nameField);

        // 电话
        panel.add(new JLabel("电话："));
        phoneField = new JTextField(15);
        panel.add(phoneField);

        // 邮件
        panel.add(new JLabel("邮件："));
        emailField = new JTextField(15);
        panel.add(emailField);

        // 按钮
        panel.add(new JLabel()); // 占位
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        panel.add(btnPanel);

        // 按钮事件
        okBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(this, "姓名不能为空！");
                return;
            }
            person = new Person(
                    catField.getText().trim().equals("") ? "未分类" : catField.getText().trim(),
                    name,
                    phoneField.getText().trim(),
                    emailField.getText().trim()
            );
            ok = true;
            dispose();
        });
        cancelBtn.addActionListener(e -> dispose());

        // 如果是编辑模式，预填数据
        if (existing != null) {
            catField.setText(existing.getCategory());
            nameField.setText(existing.getName());
            phoneField.setText(existing.getPhone());
            emailField.setText(existing.getEmail());
        } else {
            catField.setText("朋友");
        }

        add(panel);
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public boolean isOk() {
        return ok;
    }

    public Person getPerson() {
        return person;
    }
}
