package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectMenu {
    private JFrame frame;
    private ConnectionDriver conDriver;

    private JTextField ipTextField;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;

    public ConnectMenu(ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        frame = new JFrame("Подключение");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(290, 335);

        ipTextField = new JTextField(15);
        ipTextField.setToolTipText("IP адрес");
        ipTextField.setBounds(125, 10, 150, 25);
        JLabel ipLabel = new JLabel("DB IP адрес");
        ipLabel.setBounds(10, 10, 150, 25);
        loginTextField = new JTextField(15);
        loginTextField.setBounds(125, 40, 150, 25);
        JLabel loginLabel = new JLabel("DB пользователь");
        loginLabel.setBounds(10, 40, 150, 25);
        passwordTextField = new JPasswordField(15);
        passwordTextField.setBounds(125, 70, 150, 25);
        JLabel passwordLabel = new JLabel("DB пароль");
        passwordLabel.setBounds(10, 70, 150, 25);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(ipLabel);
        panel.add(ipTextField);
        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton adminTheaterButton = new JRadioButton("Администратор театра");
        adminTheaterButton.setBounds(10, 135, 200, 20);
        buttonGroup.add(adminTheaterButton);
        panel.add(adminTheaterButton);
        adminTheaterButton.setSelected(true);

        JRadioButton DirectorButton = new JRadioButton("Директор");
        DirectorButton.setBounds(165, 105, 100, 20);
        buttonGroup.add(DirectorButton);
        panel.add(DirectorButton);

        JRadioButton visitorButton = new JRadioButton("Посетитель");
        visitorButton.setBounds(10, 105, 150, 20);
        buttonGroup.add(visitorButton);
        panel.add(visitorButton);

        JRadioButton adminDbButton = new JRadioButton("Администратор БД");
        adminDbButton.setBounds(10, 165, 190, 20);
        buttonGroup.add(adminDbButton);
        panel.add(adminDbButton);

        JButton connect = new JButton("Подключение");
        connect.setBounds(15, 195, 245, 35);
        panel.add(connect);

        JButton connectStandard = new JButton("Подключение через НГУ");
        connectStandard.setBounds(15, 240, 245, 35);
        panel.add(connectStandard);
        connectStandard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authorization(adminTheaterButton.isSelected(), DirectorButton.isSelected(), visitorButton.isSelected(), adminDbButton.isSelected());

            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect(adminTheaterButton.isSelected(), DirectorButton.isSelected(), visitorButton.isSelected(), adminDbButton.isSelected());

            }
        });
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    private void authorization(boolean admin, boolean director, boolean visitor, boolean adminDB){
        JFrame frameA = new JFrame("Вход в систему");
        frameA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameA.setSize(300, 160);
        frameA.setLocationRelativeTo(null);
        frameA.setFocusable(true);
        frameA.setVisible(true);

        JLabel logLabel = new JLabel("Логин");
        logLabel.setBounds(20, 20, 100, 30);

        JTextField logField = new JTextField(20);
        logField.setBounds(110, 20, 160, 30);


        JLabel pasLabel = new JLabel("Пароль");
        pasLabel.setBounds(20, 60, 100, 30);

        JTextField pasField = new JTextField(20);
        pasField.setBounds(110, 60, 160, 30);

        JButton login = new JButton("Вход");
        login.setSize(100, 35);
        login.setBounds(100, 100, 100, 35);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(logField.getText().equals("") && pasField.getText().equals("")){
                    connectStandart(admin, director, visitor, adminDB);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Incorrect!");
                }
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(login);
        panel.add(pasField);
        panel.add(pasLabel);
        panel.add(logField);
        panel.add(logLabel);
        frameA.getContentPane().add(panel);
        frameA.setVisible(true);
    }
    public void connect(boolean admin, boolean director, boolean visitor, boolean adminDB) {
        try {
            boolean status = conDriver.connect(ipTextField.getText(), loginTextField.getText(), passwordTextField.getText());
            if (status) {
                frame.setVisible(false);
                if (admin) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 0);
                } else if (director) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 1);
                } else if (visitor) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 2);
                } else if (adminDB) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, loginTextField.getText(), 3);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Bad connection");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Bad connection");
        }
    }

    public void connectStandart(boolean admin, boolean director, boolean visitor, boolean adminBD) {
        try {
            boolean status = conDriver.connect("84.237.50.81", "19212_Kubysheva", "***");
            if (status) {
                frame.setVisible(false);
                if (admin) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "19212_Kubysheva", 0);
                } else if (director) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "19212_Kubysheva", 1);
                } else if (visitor) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "19212_Kubysheva", 2);
                } else if (adminBD) {
                    WorkingMenu workingMenu = new WorkingMenu(conDriver, "19212_Kubysheva", 3);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Bad connection");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Bad connection");
        }
    }


}
