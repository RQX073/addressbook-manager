package addressbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 查找对话框
 */
public class SearchDialog extends JDialog {

    private AddressBook book;
    private JComboBox<String> typeBox;
    private JTextField keywordField;
    private DefaultTableModel resultModel;
    private JTable resultTable;
    private List<Person> results;

    private String[] cols = {"类别", "姓名", "电话", "邮件"};

    public SearchDialog(Frame owner, AddressBook book) {
        super(owner, "查找联系人", false);
        this.book = book;

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 搜索条件区
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        typeBox = new JComboBox<>(new String[]{"全局搜索", "按姓名"});
        topPanel.add(new JLabel("方式："));
        topPanel.add(typeBox);

        keywordField = new JTextField(12);
        topPanel.add(new JLabel("关键字："));
        topPanel.add(keywordField);

        JButton searchBtn = new JButton("搜索");
        JButton clearBtn = new JButton("清除");
        topPanel.add(searchBtn);
        topPanel.add(clearBtn);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 结果表格
        resultModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        resultTable = new JTable(resultModel);
        resultTable.setRowHeight(22);
        mainPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        // 底部
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 事件
        searchBtn.addActionListener(e -> doSearch());
        clearBtn.addActionListener(e -> {
            keywordField.setText("");
            resultModel.setRowCount(0);
        });
        keywordField.addActionListener(e -> doSearch());

        add(mainPanel);
        setSize(500, 380);
        setLocationRelativeTo(owner);
    }

    private void doSearch() {
        String kw = keywordField.getText().trim();
        if (kw.equals("")) {
            JOptionPane.showMessageDialog(this, "请输入关键字！");
            return;
        }

        if (typeBox.getSelectedIndex() == 1) {
            results = book.searchByName(kw);   // 按姓名
        } else {
            results = book.search(kw);          // 全局
        }

        // 显示结果
        resultModel.setRowCount(0);
        for (Person p : results) {
            resultModel.addRow(new Object[]{
                    p.getCategory(), p.getName(), p.getPhone(), p.getEmail()
            });
        }
    }
}
