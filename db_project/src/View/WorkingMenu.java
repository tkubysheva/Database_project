package View;

import Core.ConnectionDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkingMenu {
    private ConnectionDriver conDriver;
    JFrame frame;
    JFrame directorMenu;
    JPanel panel;

    public WorkingMenu(ConnectionDriver conDriver, String username, int role) throws SQLException {
        this.conDriver = conDriver;
        frame = new JFrame("Меню");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel field = new JLabel("User: " + username);
        //field.setFont(Font.getFont(Font.SANS_SERIF));
        field.setBounds(140, 10, 150, 10);
        panel.add(field);

        JButton repertoire = new JButton("Репертуар");
        repertoire.setBounds(68, 30, 160, 35);
        panel.add(repertoire);
        repertoire.addActionListener(this::getRepertoire);

        if(role == 0){//admin
            JButton adminMenu = new JButton("Меню Администратора");
            adminMenu.setBounds(68, 75, 160, 35);
            panel.add(adminMenu);;
            frame.setSize(300, 170);
        }
        else if(role == 1){//director
            JButton directorMenu = new JButton("Меню Директора");
            directorMenu.setBounds(68, 75, 160, 35);
            panel.add(directorMenu);
            directorMenu.addActionListener(this::openDirectorsMenu);
            frame.setSize(300, 170);
        }
        else if(role == 2){//visitor
            JButton menu = new JButton("Меню Посетителя");
            menu.setBounds(68, 75, 160, 35);
            panel.add(menu);
            frame.setSize(300, 170);
        }
        else if(role == 3){//adminDB
            JButton developerMenu = new JButton("Developer Menu");
            developerMenu.addActionListener(this::openDeveloperMenu);
            developerMenu.setBounds(68, 75, 160, 35);
            panel.add(developerMenu);
            frame.setSize(300, 150);
        }
        frame.getContentPane().add(panel);
    }

    public void getRepertoire (ActionEvent event) {
        frame = new JFrame("Репертуар");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        try {
            ResultSet set = conDriver.getRepertoire();

            JTable table = new JTable();
            Object[] columnNames = {"Название спектакля", "Дата и время", "Премьера"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columnNames);
            table.setModel(model);
            table.setEnabled(false);
            table.setBackground(Color.gray);
            table.setForeground(Color.white);
            table.setRowHeight(20);
            JScrollPane pane = new JScrollPane(table);

            while (set.next()) {
                Object[] row = new Object[3];
                for (int j = 0; j < 3; ++j){
                    row[j] = set.getString(j+1);
                }
                model.addRow(row);
            }
            set.close();
            frame.add(pane);
            frame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateSpectacle(DefaultTableModel model){

        try{
            ResultSet set = conDriver.getSpectacle();
            while (set.next()) {
                Object[] row = new Object[7];
                for (int j = 0; j < 7; ++j){
                    row[j] = set.getString(j+1);
                }
                model.addRow(row);
            }
            set.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTheaterWorker(DefaultTableModel model){

        try{
            ResultSet set = conDriver.getTheaterWorker();
            while (set.next()) {
                Object[] row = new Object[7];
                for (int j = 0; j < 7; ++j){
                    row[j] = set.getString(j+2);
                }
                model.addRow(row);
            }
            set.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getSpectacle (ActionEvent event) {
        frame = new JFrame("Спектакли");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);


        JTable table = new JTable();
        Object[] columnNames = {"Название спектакля", "Жанр", "Возрастная категория", "Автор", "Художник-постановщик", "Режисер-постановщик", "Дирижер-постановщик"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        table.setEnabled(false);
        table.setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setRowHeight(20);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0,0,800,300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updateSpectacle(model);

        frame.add(pane);
        frame.setVisible(true);

    }
    public void getTheaterWorker (ActionEvent event) {
        frame = new JFrame("Работники");
        frame.setSize(800, 420);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setLayout(null);


        JTable table = new JTable();
        Object[] columnNames = {"Фамилия", "Имя", "Дата трудоустройства", "Пол", "Дата рождения", "Зарплата", "Дети"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        table.setEnabled(false);
        table.setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setRowHeight(20);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0,0,800,300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        updateTheaterWorker(model);


        JButton edit = new JButton("Правка");
        edit.setBounds(650, 320, 100, 35);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int current = table.getSelectedRow();
                TheaterWorkerMenu menu = new TheaterWorkerMenu(
                        Integer.parseInt((String) model.getValueAt(current, 0)),
                        (String) model.getValueAt(current, 1),
                        (String) model.getValueAt(current, 2),
                        (String) model.getValueAt(current, 3),
                        (String) model.getValueAt(current, 4),
                        (String) model.getValueAt(current, 5),
                        (String) model.getValueAt(current, 6),
                        conDriver);
                //System.out.println(Integer.parseInt((String) model.getValueAt(current, 5)));
            }
        });

        JButton update = new JButton("Обновить");
        update.setBounds(500, 320, 100, 35);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTheaterWorker(model);
            }
        });

        JButton delete = new JButton("Удалить");
        delete.setBounds(350, 320, 100, 35);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conDriver.deleteTheaterWorker(Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 1)));
                    JOptionPane.showMessageDialog(directorMenu, "После изменения данных в таблице просьба нажать кнопку \" Обновить\"");
                    JOptionPane.showMessageDialog(directorMenu, "Готово!");
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(frame, "Ошибка! " + throwables);
                }
            }
        });

        frame.add(delete);
        frame.add(update);
        frame.add(edit);

        frame.add(pane);
        frame.setVisible(true);

    }

    private void addTheaterWorker(ActionEvent e) {
        TheaterWorkerMenu menu = new TheaterWorkerMenu(conDriver);
    }
    private void addSpectacle(ActionEvent e) {
        SpectacleMenu menu = new SpectacleMenu(conDriver);
    }

    public void numBoughtTickets(ActionEvent e){
        frame = new JFrame("Билеты");
        frame.setSize(610, 420);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setLayout(null);

        JLabel ticketsCountTextLabel = new JLabel("Количество проданных билетов: ");
        JLabel ticketsCountLabel = new JLabel("16");
        ticketsCountTextLabel.setBounds(180, 100, 205, 35);
        ticketsCountLabel.setBounds(385, 100, 50, 35);

        JLabel nameLabel = new JLabel("Название спектакля");
        nameLabel.setBounds(15, 200, 140, 30);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(120+40, 200, 100, 30);

        JLabel dateFromLabel = new JLabel("Дата от");
        dateFromLabel.setBounds(225+40, 200, 50, 30);
        JTextField dateFromField = new JTextField(20);
        dateFromField.setBounds(280+40, 200, 100, 30);

        JLabel dateToLabel = new JLabel("До");
        dateToLabel.setBounds(385+40, 200, 30, 30);
        JTextField dateToField = new JTextField(20);
        dateToField.setBounds(420+40, 200, 100, 30);

        JButton params = new JButton("Применить");
        params.setBounds(400, 320, 150, 35);


        frame.setLayout(null);
        frame.add(ticketsCountLabel);
        frame.add(ticketsCountTextLabel);
        frame.add(dateFromField);
        frame.add(dateToField);
        frame.add(dateFromLabel);
        frame.add(dateToLabel);
        frame.add(nameField);
        frame.add(nameLabel);

        frame.add(params);
        frame.setVisible(true);

    }

    private void openAdminReportMenu(ActionEvent e) {
        directorMenu = new JFrame("Отчет администратора");
        directorMenu.setSize(300, 220);
        directorMenu.setLocationRelativeTo(null);
        directorMenu.setFocusable(true);
        directorMenu.setLayout(null);

        JButton TheaterWorker = new JButton("Выручка с билетов");
        TheaterWorker.setBounds(15, 15, 255, 35);
        directorMenu.add(TheaterWorker);
        TheaterWorker.addActionListener(this::getTheaterWorker);

        JButton addTheaterWorker = new JButton("Количество проданных билетов");
        addTheaterWorker.setBounds(15, 55, 255, 35);
        directorMenu.add(addTheaterWorker);
        addTheaterWorker.addActionListener(this::numBoughtTickets);

        JButton spectaclesList = new JButton("Перечень свободных мест");
        spectaclesList.setBounds(15, 95, 255, 35);
        directorMenu.add(spectaclesList);
        spectaclesList.addActionListener(this::getSpectacle);

        directorMenu.setVisible(true);
    }

    private void openDirectorsMenu(ActionEvent e) {
        directorMenu = new JFrame("Меню директора");
        directorMenu.setSize(300, 260);
        directorMenu.setLocationRelativeTo(null);
        directorMenu.setFocusable(true);
        directorMenu.setLayout(null);

        JButton TheaterWorker = new JButton("Работники");
        TheaterWorker.setBounds(50, 15, 200, 35);
        directorMenu.add(TheaterWorker);
        TheaterWorker.addActionListener(this::getTheaterWorker);

        JButton addTheaterWorker = new JButton("Добавить работника");
        addTheaterWorker.setBounds(50, 55, 200, 35);
        directorMenu.add(addTheaterWorker);
        addTheaterWorker.addActionListener(this::addTheaterWorker);

        JButton spectaclesList = new JButton("Спектакли");
        spectaclesList.setBounds(50, 95, 200, 35);
        directorMenu.add(spectaclesList);
        spectaclesList.addActionListener(this::getSpectacle);

        JButton addSpectacle = new JButton("Добавить новый спектакль");
        addSpectacle.setBounds(50, 135, 200, 35);
        directorMenu.add(addSpectacle);
        addSpectacle.addActionListener(this::addSpectacle);

        JButton report = new JButton("Отчет");
        report.setBounds(50, 175, 200, 35);
        directorMenu.add(report);
        report.addActionListener(this::openAdminReportMenu);

        directorMenu.setVisible(true);
    }

    private void openDeveloperMenu(ActionEvent e) {
        JFrame developerMenu = new JFrame("Developer menu");
        developerMenu.setSize(300, 175);
        developerMenu.setLocationRelativeTo(null);
        developerMenu.setFocusable(true);
        developerMenu.setLayout(null);

        JButton dropTables = new JButton("Drop tables");
        dropTables.setBounds(68, 15, 160, 35);
        developerMenu.add(dropTables);
        dropTables.addActionListener(this::dropTables);

        JButton createTables = new JButton("Create tables");
        createTables.setBounds(68, 55, 160, 35);
        developerMenu.add(createTables);
        createTables.addActionListener(this::createTables);

        JButton fillTables = new JButton("Fill tables");
        fillTables.setBounds(68, 95, 160, 35);
        developerMenu.add(fillTables);
        fillTables.addActionListener(this::fillTables);

        developerMenu.setVisible(true);
    }

    private void createTables(ActionEvent e) {
        try {
            conDriver.createTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(frame, "SQLException [error -1]");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "IOException [error -2]");
        }
    }

    private void dropTables(ActionEvent e) {
        try {
            conDriver.dropTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        }
    }

    private void fillTables(ActionEvent e) {
        try {
            conDriver.fillTables();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, "Something wrong with test script!");
        }
    }
}
