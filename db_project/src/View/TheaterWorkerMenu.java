package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class TheaterWorkerMenu {
    ConnectionDriver conDriver;
    public TheaterWorkerMenu(ConnectionDriver conDriver) {
            this.conDriver = conDriver;
            JFrame tWorkerMenu = new JFrame("Новый работник");
            tWorkerMenu.setSize(300, 350);
            tWorkerMenu.setLocationRelativeTo(null);
            tWorkerMenu.setFocusable(true);
            tWorkerMenu.setLayout(null);

            JLabel firstNameLabel = new JLabel("Имя");
            firstNameLabel.setBounds(30, 10, 100, 30);

            JTextField firstNameField = new JTextField(20);
            firstNameField.setBounds(110, 10, 160, 30);

            JLabel lastNameLabel = new JLabel("Фамилия");
            lastNameLabel.setBounds(30, 50, 100, 30);

            JTextField lastNameField = new JTextField(20);
            lastNameField.setBounds(110, 50, 160, 30);

            JLabel birthdayLabel = new JLabel("Дата рождения");
            birthdayLabel.setBounds(30, 90, 100, 30);

            JTextField birthdayField = new JTextField(11);
            birthdayField.setBounds(130, 90, 140, 30);

            JLabel salaryLabel = new JLabel("Зарплата");
            salaryLabel.setBounds(30, 130, 100, 30);

            JTextField salaryField = new JTextField(7);
            salaryField.setBounds(130, 130, 140, 30);

            JLabel childrenLabel = new JLabel("Дети");
            childrenLabel.setBounds(30, 170, 100, 30);

            JTextField childrenField = new JTextField(2);
            childrenField.setBounds(130, 170, 140, 30);

        JLabel genderLabel = new JLabel("Пол");
            genderLabel.setBounds(30, 210, 100, 30);

            Vector<String> data = new Vector<>();
            data.add("Male");
            data.add("Female");

            JComboBox comboBox = new JComboBox(data);
            comboBox.setBounds(110, 210, 160, 30);

            JButton commit = new JButton("Сохранить");
            commit.setBounds(110, 250, 80, 30);
            commit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conDriver.commitTheaterWorker(firstNameField.getText(), lastNameField.getText(),
                            Integer.parseInt(salaryField.getText()), birthdayField.getText(), comboBox.getSelectedIndex() == 0?"Male":"Female", Integer.parseInt(childrenField.getText()));
                        JOptionPane.showMessageDialog(tWorkerMenu, "Done!");
                        tWorkerMenu.setVisible(false);
                    } catch (SQLException ee) {
                        JOptionPane.showMessageDialog(tWorkerMenu, "SQLException [error -1]: " + ee);
                    }
                }
            });

            tWorkerMenu.add(firstNameLabel);
            tWorkerMenu.add(firstNameField);
            tWorkerMenu.add(lastNameLabel);
            tWorkerMenu.add(lastNameField);
            tWorkerMenu.add(birthdayLabel);
            tWorkerMenu.add(birthdayField);
            tWorkerMenu.add(salaryField);
            tWorkerMenu.add(salaryLabel);
            tWorkerMenu.add(childrenField);
            tWorkerMenu.add(childrenLabel);
            tWorkerMenu.add(genderLabel);
            tWorkerMenu.add(comboBox);
            tWorkerMenu.add(commit);

            tWorkerMenu.setVisible(true);
    }
    public TheaterWorkerMenu(int id, String firstName, String lastName, String birthday, String gender, String salary,String  childrens,  ConnectionDriver conDriver) {
        this.conDriver = conDriver;
        JFrame tWorkerMenu = new JFrame("Правка информации");
        tWorkerMenu.setSize(300, 300);
        tWorkerMenu.setLocationRelativeTo(null);
        tWorkerMenu.setFocusable(true);
        tWorkerMenu.setLayout(null);

        JLabel firstNameLabel = new JLabel("Имя");
        firstNameLabel.setBounds(30, 10, 100, 30);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(110, 10, 160, 30);
        firstNameField.setText(firstName);

        JLabel lastNameLabel = new JLabel("Фамилия");
        lastNameLabel.setBounds(30, 50, 100, 30);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(110, 50, 160, 30);
        lastNameField.setText(lastName);

        JLabel birthdayLabel = new JLabel("Дата рождения");
        birthdayLabel.setBounds(30, 90, 100, 30);

        JTextField birthdayField = new JTextField(11);
        birthdayField.setBounds(135, 90, 160, 30);
        birthdayField.setText(birthday);

        JLabel salaryLabel = new JLabel("Зарплата");
        salaryLabel.setBounds(30, 130, 100, 30);

        JTextField salaryField = new JTextField(7);
        salaryField.setBounds(130, 130, 140, 30);
        salaryField.setText(salary);

        JLabel childrenLabel = new JLabel("Дети");
        childrenLabel.setBounds(30, 170, 100, 30);

        JTextField childrenField = new JTextField(2);
        childrenField.setBounds(130, 170, 140, 30);
        childrenField.setText(childrens);

        JLabel genderLabel = new JLabel("Пол");
        genderLabel.setBounds(30, 210, 100, 30);

        Vector<String> data = new Vector<>();
        data.add("Male");
        data.add("Female");

        JComboBox comboBox = new JComboBox(data);
        comboBox.setBounds(110, 210, 160, 30);
        comboBox.setSelectedIndex(gender.equals("Male")?0:1);

        JButton commit = new JButton("Сохранить");
        commit.setBounds(110, 210, 80, 30);
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(tWorkerMenu, "Пожалуйста, обновите таблицу после изменения информации");
                try {
                    conDriver.updateTheaterWorker(firstNameField.getText(), lastNameField.getText(),
                            Integer.parseInt(salaryField.getText()), birthdayField.getText(), comboBox.getSelectedIndex() == 0?"Male":"Female", Integer.parseInt(childrenField.getText()));
                    tWorkerMenu.setVisible(false);
                } catch (SQLException ee) {
                    JOptionPane.showMessageDialog(tWorkerMenu, "SQLException [error -1]: " + ee);
                }
            }
        });

        tWorkerMenu.add(firstNameLabel);
        tWorkerMenu.add(firstNameField);
        tWorkerMenu.add(lastNameLabel);
        tWorkerMenu.add(lastNameField);
        tWorkerMenu.add(birthdayLabel);
        tWorkerMenu.add(birthdayField);
        tWorkerMenu.add(salaryField);
        tWorkerMenu.add(salaryLabel);
        tWorkerMenu.add(childrenField);
        tWorkerMenu.add(childrenLabel);
        tWorkerMenu.add(genderLabel);
        tWorkerMenu.add(comboBox);
        tWorkerMenu.add(commit);

        tWorkerMenu.setVisible(true);
    }
}
