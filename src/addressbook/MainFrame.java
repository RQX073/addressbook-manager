package addressbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 主窗口
 * 包含菜单栏、表格、右键菜单
 */
public class MainFrame extends JFrame implements ActionListener {

    private AddressBook book;           // 地址簿数据
    private DefaultTableModel model;    // 表格数据模型
    private JTable table;              // 表格

    // 菜单项（因为要在 actionPerformed 里判断是哪个）
    private JMenuItem newItem, openItem, saveItem, saveAsItem, exitItem;
    private JMenuItem addItem, editItem, deleteItem, upItem, downItem, changeCatItem;
    private JMenuItem findItem, showAllItem;

    // 列名
    private String[] cols = {"类别", "姓名", "电话", "邮件"};

    public MainFrame() {
        book = new AddressBook();

        // 设置窗口
        setTitle("地址簿管理器");
        setSize(700, 500);
        setLocationRelativeTo(null);  // 居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 菜单栏
        setJMenuBar(createMenus());

        // 表格
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能单选
        table.setRowHeight(24);

        // 右键菜单
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }
        });

        // 双击编辑
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editPerson();
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    // ==================== 菜单栏 ====================

    private JMenuBar createMenus() {
        JMenuBar bar = new JMenuBar();

        // ---- 文件菜单 ----
        JMenu fileMenu = new JMenu("文件");
        newItem = new JMenuItem("新建");
        openItem = new JMenuItem("打开...");
        saveItem = new JMenuItem("保存");
        saveAsItem = new JMenuItem("另存为...");
        exitItem = new JMenuItem("退出");

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        bar.add(fileMenu);

        // ---- 编辑菜单 ----
        JMenu editMenu = new JMenu("编辑");
        addItem = new JMenuItem("添加联系人");
        editItem = new JMenuItem("修改联系人");
        deleteItem = new JMenuItem("删除联系人");
        upItem = new JMenuItem("上移");
        downItem = new JMenuItem("下移");
        changeCatItem = new JMenuItem("更改类别");

        addItem.addActionListener(this);
        editItem.addActionListener(this);
        deleteItem.addActionListener(this);
        upItem.addActionListener(this);
        downItem.addActionListener(this);
        changeCatItem.addActionListener(this);

        editMenu.add(addItem);
        editMenu.add(editItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(upItem);
        editMenu.add(downItem);
        editMenu.addSeparator();
        editMenu.add(changeCatItem);
        bar.add(editMenu);

        // ---- 查找菜单 ----
        JMenu searchMenu = new JMenu("查找");
        findItem = new JMenuItem("查找联系人...");
        showAllItem = new JMenuItem("显示全部");

        findItem.addActionListener(this);
        showAllItem.addActionListener(this);

        searchMenu.add(findItem);
        searchMenu.add(showAllItem);
        bar.add(searchMenu);

        return bar;
    }

    // ==================== 右键菜单 ====================

    private void showPopup(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();

        int row = table.rowAtPoint(e.getPoint());

        // "添加"始终可用
        JMenuItem add = new JMenuItem("添加联系人");
        add.addActionListener(ev -> addPerson());
        popup.add(add);

        if (row >= 0) {
            // 选中了某一行才显示这些
            table.setRowSelectionInterval(row, row);

            popup.addSeparator();

            JMenuItem edit = new JMenuItem("修改联系人");
            edit.addActionListener(ev -> editPerson());
            popup.add(edit);

            JMenuItem del = new JMenuItem("删除联系人");
            del.addActionListener(ev -> deletePerson());
            popup.add(del);

            popup.addSeparator();

            JMenuItem up = new JMenuItem("上移");
            up.addActionListener(ev -> moveUp());
            popup.add(up);

            JMenuItem down = new JMenuItem("下移");
            down.addActionListener(ev -> moveDown());
            popup.add(down);

            popup.addSeparator();

            JMenuItem chCat = new JMenuItem("更改类别");
            chCat.addActionListener(ev -> changeCategory());
            popup.add(chCat);
        }

        popup.show(table, e.getX(), e.getY());
    }

    // ==================== 事件处理 ====================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == newItem) {
            newFile();
        } else if (src == openItem) {
            openFile();
        } else if (src == saveItem) {
            saveFile();
        } else if (src == saveAsItem) {
            saveAsFile();
        } else if (src == exitItem) {
            System.exit(0);
        } else if (src == addItem) {
            addPerson();
        } else if (src == editItem) {
            editPerson();
        } else if (src == deleteItem) {
            deletePerson();
        } else if (src == upItem) {
            moveUp();
        } else if (src == downItem) {
            moveDown();
        } else if (src == changeCatItem) {
            changeCategory();
        } else if (src == findItem) {
            searchPerson();
        } else if (src == showAllItem) {
            refreshTable();
        }
    }

    // ==================== 功能实现 ====================

    /** 新建 */
    private void newFile() {
        book.clear();
        setTitle("地址簿管理器");
        refreshTable();
    }

    /** 打开文件 */
    private void openFile() {
        JFileChooser chooser = new JFileChooser("data");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                book.loadFromFile(chooser.getSelectedFile().getPath());
                setTitle("地址簿管理器 - " + chooser.getSelectedFile().getName());
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "打开失败：" + ex.getMessage());
            }
        }
    }

    /** 保存 */
    private void saveFile() {
        if (book.getFilePath() == null) {
            saveAsFile();
        } else {
            try {
                book.save();
                JOptionPane.showMessageDialog(this, "保存成功！");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "保存失败：" + ex.getMessage());
            }
        }
    }

    /** 另存为 */
    private void saveAsFile() {
        JFileChooser chooser = new JFileChooser("data");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            if (!path.endsWith(".dat")) {
                path = path + ".dat";
            }
            try {
                book.saveToFile(path);
                setTitle("地址簿管理器 - " + new File(path).getName());
                JOptionPane.showMessageDialog(this, "保存成功！");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "保存失败：" + ex.getMessage());
            }
        }
    }

    /** 添加联系人 */
    private void addPerson() {
        PersonDialog dlg = new PersonDialog(this, "添加联系人", null);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            book.add(dlg.getPerson());
            refreshTable();
        }
    }

    /** 修改联系人 */
    private void editPerson() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要修改的联系人！");
            return;
        }
        Person old = book.get(row);
        PersonDialog dlg = new PersonDialog(this, "修改联系人", old);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            book.update(row, dlg.getPerson());
            refreshTable();
        }
    }

    /** 删除联系人 */
    private void deletePerson() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的联系人！");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
                "确定要删除 " + book.get(row).getName() + " 吗？",
                "确认删除", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            book.delete(row);
            refreshTable();
        }
    }

    /** 上移 */
    private void moveUp() {
        int row = table.getSelectedRow();
        if (row > 0) {
            book.move(row, row - 1);
            refreshTable();
            table.setRowSelectionInterval(row - 1, row - 1);
        }
    }

    /** 下移 */
    private void moveDown() {
        int row = table.getSelectedRow();
        if (row >= 0 && row < book.size() - 1) {
            book.move(row, row + 1);
            refreshTable();
            table.setRowSelectionInterval(row + 1, row + 1);
        }
    }

    /** 更改类别 */
    private void changeCategory() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择联系人！");
            return;
        }
        String newCat = JOptionPane.showInputDialog(this, "输入新类别：",
                book.get(row).getCategory());
        if (newCat != null && !newCat.trim().equals("")) {
            book.get(row).changeCategory(newCat.trim());
            refreshTable();
        }
    }

    /** 查找 */
    private void searchPerson() {
        new SearchDialog(this, book).setVisible(true);
    }

    /** 刷新表格 */
    void refreshTable() {
        model.setRowCount(0); // 清空
        for (Person p : book.getAll()) {
            model.addRow(new Object[]{
                    p.getCategory(), p.getName(), p.getPhone(), p.getEmail()
            });
        }
        setTitle("地址簿管理器 - 共" + book.size() + "条记录");
    }
}
