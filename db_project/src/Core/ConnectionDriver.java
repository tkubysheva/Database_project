package Core;

import Lib.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Locale;

public class ConnectionDriver {
    Connection connection;
    Statement statement;

    public ConnectionDriver() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }

    public boolean connect(String ip, String login, String password) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        String url = "jdbc:oracle:thin:@"+ip+":1521:XE";
        connection = DriverManager.getConnection(url, login, password);
        return connection != null;
    }

    public void commitSpectacle(int artDirId, int conDirId, int stDirId, int authorId, String name, int genreId, int categId) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO SPECTACLE (\"ART_DIRECTOR_ID\",\"CONDUCTOR_DIRECTOR_ID\",\"STAGE_DIRECTOR_ID\",\"AUTHOR_ID\",\"NAME\",\"GENRE_ID\",\"AGE_CATEGORY_ID\") VALUES " +
                "( " + artDirId + ", " + conDirId + ", " + stDirId +
                ", " + authorId + ", '" + name + "', " + genreId + ", " + categId +")";
        System.out.println(sql);
        statement.execute(sql);
    }

    public void commitTheaterWorker(String firstName, String lastName, int salary, String birthday, String gender, int childrens) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO THEATER_WORKER(\"FIRST_NAME\",\"LAST_NAME\",\"EMPLOYMENT_DAY\",\"GENDER\",\"BIRTHDAY\",\"SALARY\",\"CHILDRENS\") VALUES ( '" + firstName + "', '" + lastName + "', SYSDATE, '" + gender +
                "', to_date('" + birthday + "', 'dd/mm/yyyy'), " + salary + ", " + childrens + ")";
        System.out.println(sql);
        statement.execute(sql);
    }
    public void updateTheaterWorker(String firstName, String lastName, int salary, String birthday, String gender, int childrens) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO THEATER_WORKER(\"FIRST_NAME\",\"LAST_NAME\",\"EMPLOYMENT_DAY\",\"GENDER\",\"BIRTHDAY\",\"SALARY\",\"CHILDRENS\") VALUES ( '" + firstName + "', '" + lastName + "', SYSDATE, '" + gender +
                "', to_date('" + birthday + "', 'dd/mm/yyyy'), " + salary + ", " + childrens + ")";
        System.out.println(sql);
        statement.execute(sql);
    }
    public void deleteTheaterWorker(int id) throws SQLException {
        statement = connection.createStatement();
        String sql;
        sql = "DELETE FROM THEATER_WORKER WHERE ID = " + id;
        statement.executeQuery(sql);
    }

    public ResultSet getCategory()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT ID, Name FROM age_category""");
    }
    public ResultSet getAuthor()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT ID, Name FROM author""");
    }

    public ResultSet getGenre()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT ID, Name FROM genre""");
    }
    public ResultSet getArtDirector()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT d.ID, t.last_name, t.first_name FROM ART_DIRECTOR d join Theater_worker t on t.id=d.id""");
    }
    public ResultSet getConductorDirector()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT d.ID, t.last_name, t.first_name FROM CONDUCTOR_DIRECTOR d join Theater_worker t on t.id=d.id""");
    }
    public ResultSet getStageDirector()throws SQLException{
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT d.ID, t.last_name, t.first_name FROM STAGE_DIRECTOR d join Theater_worker t on t.id=d.id""");
    }

    public int getTicketCount(String spectacle, String from, String to ) throws SQLException {
        statement = connection.createStatement();
        String sql = "SELECT COUNT(*) FROM TICKETS";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }

    public ResultSet getRepertoire() throws SQLException {
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT S.NAME name, R.DATE_TIME, TO_CHAR (R.PREMIERE_DATE, 'DD.MM.YYYY') FROM REPERTOIRE R JOIN SPECTACLE S ON R.SPECTACLE_ID = S.ID
                ORDER BY name""");
    }

    public ResultSet getTheaterWorker() throws SQLException {
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT * FROM THEATER_WORKER""");
    }

    public ResultSet getSpectacle() throws SQLException {
        statement = connection.createStatement();
        return statement.executeQuery("""
                SELECT NAME, GENRE_ID, AGE_CATEGORY_ID, AUTHOR_ID, ART_DIRECTOR_ID, STAGE_DIRECTOR_ID, CONDUCTOR_DIRECTOR_ID FROM SPECTACLE
                ORDER BY name""");
    }
    public void createTriggers() throws SQLException{
        statement = connection.createStatement();
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_author before INSERT ON author FOR each row
                            BEGIN
                            SELECT sq_author.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""                    
                            CREATE OR REPLACE TRIGGER tr_A_PRODUCER after INSERT\s
                            ON ART_DIRECTOR
                            for each row
                            BEGIN
                            INSERT INTO PRODUCER(id) VALUES (:NEW.id);
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_S_PRODUCER after INSERT\s
                            ON STAGE_DIRECTOR
                            for each row
                            BEGIN
                            INSERT INTO PRODUCER(id) VALUES (:NEW.id);
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_C_PRODUCER after INSERT\s
                            ON CONDUCTOR_DIRECTOR
                            for each row
                            BEGIN
                            INSERT INTO PRODUCER(id) VALUES (:NEW.id);
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_genre before INSERT ON genre FOR each row
                            BEGIN
                            SELECT sq_genre.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_TICKET before INSERT ON TICKET FOR each row
                            BEGIN
                            SELECT sq_TICKET.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_AGE_CATEGORY before INSERT ON AGE_CATEGORY FOR each row
                            BEGIN
                            SELECT sq_AGE_CATEGORY.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_REPERTOIRE before INSERT ON REPERTOIRE FOR each row
                            BEGIN
                            SELECT sq_REPERTOIRE.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_SPECTACLE before INSERT ON SPECTACLE FOR each row
                            BEGIN
                            SELECT sq_SPECTACLE.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_THEATER_WORKER before INSERT ON THEATER_WORKER FOR each row
                            BEGIN
                            SELECT sq_THEATER_WORKER.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_id_a_SUBSCRIPTION before INSERT ON author_SUBSCRIPTION FOR each row
                            BEGIN
                            SELECT sq_a_SUBSCRIPTION.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_s_a_SUBSCRIPTION after INSERT
                            ON author_SUBSCRIPTION
                            for each row
                            BEGIN
                            INSERT INTO SUBSCRIPTION(id) VALUES (:NEW.id);
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_id_g_SUBSCRIPTION before INSERT ON GENRE_SUBSCRIPTION FOR each row
                            BEGIN
                            SELECT sq_g_SUBSCRIPTION.NEXTVAL
                            INTO :new.id
                            FROM dual;
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_s_g_SUBSCRIPTION after INSERT
                            ON GENRE_SUBSCRIPTION
                            for each row
                            BEGIN
                            INSERT INTO SUBSCRIPTION(id) VALUES (:NEW.id);
                            END;""");
        statement.executeQuery("""
                            CREATE OR REPLACE TRIGGER tr_CASH before INSERT ON CASH_REGISTER FOR each row
                            BEGIN
                            SELECT sq_CASH.NEXTVAL
                            INTO :new.PURCHASE_id
                            FROM dual;
                            END;"""
        );
    }

    public void createTables() throws IOException, SQLException {
        ScriptRunner runner = new ScriptRunner(connection, true, true);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Create_tables"));
        try {
            runner.runScript(reader);
            createTriggers();
            System.out.println("Tables created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, true);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Drop_tables"));
        try {
            runner.runScript(reader);
            System.out.println("Tables dropped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTables() throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(connection, true, true);
        InputStreamReader reader = new InputStreamReader(new FileInputStream("src/Scripts/Fill_tables"));
        try {
            runner.runScript(reader);
            System.out.println("Tables filled");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
