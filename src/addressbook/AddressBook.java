package addressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址簿管理类
 * 负责联系人的增删改查，以及用对象流读写文件
 */
public class AddressBook {

    // 用 ArrayList 存储所有联系人
    private List<Person> list;

    // 当前打开的文件路径
    private String filePath;

    public AddressBook() {
        list = new ArrayList<>();
        filePath = null;
    }

    // ============ 基本操作 ============

    /** 添加联系人 */
    public void add(Person p) {
        list.add(p);
    }

    /** 删除第 index 个联系人 */
    public void delete(int index) {
        list.remove(index);
    }

    /** 修改第 index 个联系人 */
    public void update(int index, Person p) {
        list.set(index, p);
    }

    /** 移动联系人位置（上移或下移） */
    public void move(int from, int to) {
        Person p = list.remove(from);
        list.add(to, p);
    }

    /** 获取第 index 个联系人 */
    public Person get(int index) {
        return list.get(index);
    }

    /** 获取联系人总数 */
    public int size() {
        return list.size();
    }

    /** 获取全部联系人 */
    public List<Person> getAll() {
        return list;
    }

    /** 清空 */
    public void clear() {
        list.clear();
    }

    // ============ 查找 ============

    /** 按姓名查找（模糊匹配） */
    public List<Person> searchByName(String keyword) {
        List<Person> result = new ArrayList<>();
        for (Person p : list) {
            if (p.getName().contains(keyword)) {
                result.add(p);
            }
        }
        return result;
    }

    /** 全局查找，在所有字段中匹配 */
    public List<Person> search(String keyword) {
        List<Person> result = new ArrayList<>();
        for (Person p : list) {
            if (p.getName().contains(keyword)
                    || p.getPhone().contains(keyword)
                    || p.getEmail().contains(keyword)
                    || p.getCategory().equals(keyword)) {
                result.add(p);
            }
        }
        return result;
    }

    // ============ 文件读写（对象流）============

    /** 保存到文件 */
    public void saveToFile(String path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(list);
        out.close();
        filePath = path;
    }

    /** 从文件读取 */
    @SuppressWarnings("unchecked")
    public void loadFromFile(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        list = (List<Person>) in.readObject();
        in.close();
        filePath = path;
    }

    /** 保存到当前文件 */
    public void save() throws IOException {
        if (filePath != null) {
            saveToFile(filePath);
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
