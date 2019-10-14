package oose.dea.resources.dataresources;

import oose.dea.resources.exceptions.DatabaseConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private Properties properties;

    private static final String DB_CONNECTION_URL = "jdbc:sqlserver://localhost:1433";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_USER = "diego";
    private static final String DB_PASSWORD = "cuppie123";

    public DatabaseConnection(){
        properties = getProperties();
    }

    public Properties getProperties() {

        Properties properties = new Properties();
        String propertiesFetch = getClass().getClassLoader().getResource("database.properties").getPath();
//        String propertiesFetch = getClass().getClassLoader().getResource("").getPath() + "oose/dea/properties/database.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(propertiesFetch);
            properties.load(fileInputStream);
        } catch (IOException e) {
            properties.setProperty("db.url", DB_CONNECTION_URL);
            properties.setProperty("db.user", DB_USER);
            properties.setProperty("db.password", DB_PASSWORD);
            properties.setProperty("db,driver", DRIVER);

            e.printStackTrace();
        }
        return properties;
    }

    public java.sql.Connection getConnection(){
        loadDriver();
        try {
            return DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }

    private void loadDriver(){
        try {
//            Class.forName(DRIVER);
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//        java.sql.Connection cnEmps = null;
//        var driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        String connectionString = "jdbc:sqlserver://localhost:1433";
//        var userID = "diego";
//        var password = "cuppie123";
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try
//        {
//            cnEmps = DriverManager.getConnection(connectionString, userID, password);
//
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Error connecting to a database: " + e);
//        }
//        return cnEmps;
//    }
}
