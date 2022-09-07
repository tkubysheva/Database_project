package View;

import Core.ConnectionDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class SpectacleMenu {
    ConnectionDriver conDriver;

    private HashMap<String, Integer> resultSetToMap(ResultSet rs, boolean three) throws SQLException {
        HashMap<String, Integer> res = new HashMap();
        if(three){
            while (rs.next()) {
                res.put(rs.getString(2)+" "+rs.getString(3), rs.getInt(1));
            }
        }
        else{
            while (rs.next()) {
                res.put(rs.getString(2), rs.getInt(1));
            }
        }
        return res;

    }

    public SpectacleMenu(ConnectionDriver conDriver){
        this.conDriver = conDriver;
        try {
            JFrame passengersMenu = new JFrame("Новый спектакль");
            passengersMenu.setSize(300, 370);
            passengersMenu.setLocationRelativeTo(null);
            passengersMenu.setFocusable(true);
            passengersMenu.setLayout(null);

            JLabel nameLabel = new JLabel("Название");
            nameLabel.setBounds(30, 10, 100, 30);

            JTextField nameField = new JTextField(20);
            nameField.setBounds(110, 10, 160, 30);

            JLabel authorLabel = new JLabel("Автор");
            authorLabel.setBounds(30, 50, 100, 30);
            ResultSet authorSet = conDriver.getAuthor();
            HashMap<String, Integer> authorsMap = resultSetToMap(authorSet, false);
            JComboBox authorsBox = new JComboBox(new Vector<>(authorsMap.keySet()));
            authorsBox.setBounds(110, 50, 160, 30);

            JLabel genreLabel = new JLabel("Жанр");
            genreLabel.setBounds(30, 90, 100, 30);

            ResultSet genreSet = conDriver.getGenre();
            HashMap<String, Integer> genresMap = resultSetToMap(genreSet, false);
            JComboBox genresBox = new JComboBox(new Vector<>(genresMap.keySet()));
            genresBox.setBounds(110, 90, 160, 30);

            JLabel categoryLabel = new JLabel("Категория");
            categoryLabel.setBounds(30, 130, 100, 30);

            ResultSet categorySet = conDriver.getCategory();
            HashMap<String, Integer> categorysMap = resultSetToMap(categorySet, false);
            JComboBox categorysBox = new JComboBox(new Vector<>(categorysMap.keySet()));
            categorysBox.setBounds(130, 130, 140, 30);

            JLabel aDirectorLabel = new JLabel("Арт-директор");
            aDirectorLabel.setBounds(30, 170, 100, 30);

            ResultSet aDirectorSet = conDriver.getArtDirector();
            HashMap<String, Integer> aDirectorsMap = resultSetToMap(aDirectorSet, true);
            JComboBox aDirectorsBox = new JComboBox(new Vector<>(aDirectorsMap.keySet()));
            aDirectorsBox.setBounds(130, 170, 140, 30);

            JLabel sDirectorLabel = new JLabel("Режисер");
            sDirectorLabel.setBounds(30, 210, 100, 30);

            ResultSet sDirectorSet = conDriver.getStageDirector();
            HashMap<String, Integer> sDirectorsMap = resultSetToMap(sDirectorSet, true);
            JComboBox sDirectorsBox = new JComboBox(new Vector<>(sDirectorsMap.keySet()));
            sDirectorsBox.setBounds(110, 210, 160, 30);

            JLabel cDirectorLabel = new JLabel("Постановщик");
            cDirectorLabel.setBounds(30, 250, 100, 30);

            ResultSet cDirectorSet = conDriver.getConductorDirector();
            HashMap<String, Integer> cDirectorsMap = resultSetToMap(cDirectorSet, true);
            JComboBox cDirectorsBox = new JComboBox(new Vector<>(cDirectorsMap.keySet()));
            cDirectorsBox.setBounds(110, 250, 160, 30);

            JButton commit = new JButton("Save");
            commit.setBounds(110, 290, 80, 30);
            commit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conDriver.commitSpectacle(aDirectorsMap.get(aDirectorsBox.getName()),cDirectorsMap.get(cDirectorsBox.getName()),sDirectorsMap.get(sDirectorsBox.getName()),authorsMap.get(authorsBox.getName()),nameField.getText(),
                                genresMap.get(genresBox.getName()),categorysMap.get(categorysBox.getName()));
                        JOptionPane.showMessageDialog(passengersMenu, "Done!");
                        passengersMenu.setVisible(false);
                    } catch (SQLException ee) {
                        JOptionPane.showMessageDialog(passengersMenu, "SQLException [error -1]: " + ee);
                    }
                }
            });

            passengersMenu.add(nameLabel);
            passengersMenu.add(nameField);
            passengersMenu.add(authorLabel);
            passengersMenu.add(authorsBox);
            passengersMenu.add(aDirectorLabel);
            passengersMenu.add(aDirectorsBox);
            passengersMenu.add(cDirectorLabel);
            passengersMenu.add(cDirectorsBox);
            passengersMenu.add(sDirectorLabel);
            passengersMenu.add(sDirectorsBox);
            passengersMenu.add(genreLabel);
            passengersMenu.add(genresBox);
            passengersMenu.add(categoryLabel);
            passengersMenu.add(categorysBox);
            passengersMenu.add(commit);
            passengersMenu.setVisible(true);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
