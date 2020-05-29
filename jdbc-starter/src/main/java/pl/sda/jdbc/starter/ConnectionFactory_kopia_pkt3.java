package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory_kopia_pkt3 {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory_kopia_pkt3.class);

    private static final String DB_SERVER_NAME = "127.0.0.1";
    private static final String DB_NAME = "classicmodels";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Setsuna25Meiou";
    private static final int DB_PORT = 3306;

    private final MysqlDataSource dataSource;

    public ConnectionFactory_kopia_pkt3() throws SQLException {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(DB_SERVER_NAME);
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setPort(DB_PORT);
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF-8");
        logger.info("Connection factory created successfully.");
    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory_kopia_pkt3.class.getResourceAsStream(filename);
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) {
        try {
            ConnectionFactory_kopia_pkt3 connectionFactory = new ConnectionFactory_kopia_pkt3();
            Connection connection1 = connectionFactory.getConnection();
            Connection connection2 = connectionFactory.getConnection();
            logger.info("connection: {}", connection1);
            logger.info("connection: {}", connection2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}