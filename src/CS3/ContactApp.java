package CS3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ContactApp extends JFrame {
    private JTextField nameField, phoneField, addressField;
    private JList<Contact> contactList;
    private DefaultListModel<Contact> listModel;
    private JLabel statusLabel; // 用于显示操作状态的标签

    public ContactApp() {
        setTitle("个人通讯录");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        add(new JScrollPane(contactList));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // 创建标签和文本框的面板
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("姓名:"));
        namePanel.add(nameField = new JTextField(20)); // 设置文本框宽度

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.add(new JLabel("电话:"));
        phonePanel.add(phoneField = new JTextField(20)); // 设置文本框宽度

        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.add(new JLabel("地址:"));
        addressPanel.add(addressField = new JTextField(20)); // 设置文本框宽度

        inputPanel.add(namePanel);
        inputPanel.add(phonePanel);
        inputPanel.add(addressPanel);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER ,10, 5)); // 设置按钮之间的间距
        JButton addButton = new JButton("添加");
        JButton queryButton = new JButton("查询");
        JButton deleteButton = new JButton("删除");

        buttonPanel.add(addButton);
        buttonPanel.add(queryButton);
        buttonPanel.add(deleteButton);

        inputPanel.add(buttonPanel); // 按钮面板添加到输入面板

        // 创建状态标签
        statusLabel = new JLabel("",SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        add(inputPanel);

        addButton.addActionListener(e -> addContact());
        queryButton.addActionListener(e -> queryContact());
        deleteButton.addActionListener(e -> deleteContact());

        loadContacts();
    }

    private void addContact() {
        try {
            DatabaseManager.addContact(nameField.getText(), phoneField.getText(), addressField.getText());
            showStatus("联系人已添加");
            loadContacts();
        } catch (SQLException ex) {
            showStatus("添加联系人失败");
            ex.printStackTrace();
        }
    }

    private void queryContact() {
        try {
            DatabaseManager.queryContact(nameField.getText());
            showStatus("查询成功");
        } catch (SQLException ex) {
            showStatus("查询联系人失败");
            ex.printStackTrace();
        }
    }

    private void deleteContact() {
        try {
            DatabaseManager.deleteContact(nameField.getText());
            showStatus("联系人已删除");
            loadContacts();
        } catch (SQLException ex) {
            showStatus("删除联系人失败");
            ex.printStackTrace();
        }
    }

    private void loadContacts() {
        listModel.clear();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM User";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                listModel.addElement(new CS3.Contact(rs.getString("name"), rs.getString("phone"), rs.getString("address")));
            }
        } catch (SQLException ex) {
            showStatus("加载联系人失败");
            ex.printStackTrace();
        }
    }

    private void showStatus(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactApp app = new ContactApp();
            app.setVisible(true);
        });
    }
}