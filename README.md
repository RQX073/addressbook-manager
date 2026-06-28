[Uploading README.md…]()
# 📇 地址簿管理器

基于 **Java Swing** 的图形化地址簿（通讯录）管理程序，支持联系人的增删改查、数据持久化存储等功能。

> 🎓 面向对象程序设计课程设计项目

## ✨ 功能特性

- **联系人管理** — 添加、修改、删除、移动联系人
- **分类管理** — 支持按类别（朋友/家人/同事等）管理联系人
- **搜索功能** — 支持按姓名模糊搜索和全局搜索
- **数据持久化** — 使用 Java 对象序列化流（Object Stream）保存/读取数据
- **图形界面** — 基于 Swing 的完整 GUI，含菜单栏、表格、对话框
- **右键菜单** — 表格右键快捷操作，根据选中状态自动变化
- **纯标准库** — 仅使用 Java 标准库，零第三方依赖

## 📁 项目结构

```
├── src/addressbook/
│   ├── App.java              # 程序入口
│   ├── Person.java           # 联系人类（实现 Serializable）
│   ├── AddressBook.java      # 数据管理类（增删改查 + 对象流读写）
│   ├── MainFrame.java        # 主窗口（菜单栏 + 表格 + 右键菜单）
│   ├── PersonDialog.java     # 添加/修改联系人对话框
│   └── SearchDialog.java     # 搜索对话框
├── data/                     # 数据文件目录（.dat）
├── 运行.bat                   # Windows 一键编译运行脚本
└── 课程设计文档.md             # 详细设计文档
```

## 🚀 快速开始

### 环境要求

- **JDK 8** 或更高版本
- 仅需 Java 标准库，无需 Maven/Gradle 或其他依赖

### 编译运行

**方式一：双击运行（Windows）**

直接双击 `运行.bat`，自动编译并启动程序。

**方式二：命令行**

```bash
# 编译
javac -d bin -encoding UTF-8 src/addressbook/*.java

# 运行
java -cp bin addressbook.App
```

## 📖 使用说明

| 操作 | 方法 |
|------|------|
| 添加联系人 | 菜单「编辑 → 添加联系人」，或右键表格空白处 |
| 修改联系人 | 选中后「编辑 → 修改联系人」，或双击该行 |
| 删除联系人 | 选中后「编辑 → 删除联系人」 |
| 调整顺序 | 选中后「编辑 → 上移/下移」 |
| 更改类别 | 选中后「编辑 → 更改类别」 |
| 查找联系人 | 菜单「查找 → 查找联系人」 |
| 保存/打开 | 菜单「文件 → 保存 / 另存为 / 打开」 |

## 🛠️ 技术要点

- **MVC 思想**：Person（模型）→ AddressBook（数据逻辑）→ Frame/Dialog（视图控制）
- **序列化存储**：Person 实现 `Serializable`，通过 `ObjectOutputStream` / `ObjectInputStream` 整存整取
- **事件处理**：统一使用 `ActionListener` 接口处理菜单和按钮事件
- **对话框复用**：新增和编辑共用 `PersonDialog`，通过构造参数区分模式

## 📄 开发环境

- JDK 8+
- IntelliJ IDEA
- Windows 11

## 📜 License

MIT License
