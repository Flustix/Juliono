package flustix.julino.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import flustix.julino.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    static HikariDataSource newConnection() {
        HikariConfig cfg = new HikariConfig();
        cfg.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        cfg.addDataSourceProperty("serverName", Main.config.get("dbHost").getAsString());
        cfg.addDataSourceProperty("databaseName", Main.config.get("dbName").getAsString());
        cfg.addDataSourceProperty("port", 3306);
        cfg.setUsername(Main.config.get("dbUser").getAsString());
        cfg.setPassword(Main.config.get("dbPass").getAsString());
        cfg.setIdleTimeout(0);
        return new HikariDataSource(cfg);
    }

    public static ResultSet execQuery(String query) {
        try {
            HikariDataSource connection = newConnection();
            PreparedStatement ps = connection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            connection.close();
            return rs;
        } catch (Exception e) {
            Main.logger.error("Error while executing query: " + query, e);
            e.printStackTrace();
            return null;
        }
    }
}
