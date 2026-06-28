package addressbook;

import java.io.Serializable;

/**
 * 人员类
 * 存储联系人的基本信息
 * 实现 Serializable 接口，可以用对象流读写
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String category;  // 类别，比如"朋友""家人""同学"
    private String name;      // 姓名
    private String phone;     // 电话号码
    private String email;     // 邮件地址

    // 构造方法
    public Person(String category, String name, String phone, String email) {
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // ===== getter 和 setter =====

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 更改类别
     */
    public void changeCategory(String newCategory) {
        this.category = newCategory;
    }
}
