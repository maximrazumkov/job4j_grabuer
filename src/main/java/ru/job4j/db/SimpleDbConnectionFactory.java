package ru.job4j.db;

import ru.job4j.config.Config;

import java.sql.*;

public class SimpleDbConnectionFactory implements DbConnectionFactory<Config> {
    @Override
    public Connection getPostgryConnection(Config config) {
        Connection connection = null;
        try {
            createBD(config);
            Class.forName(config.getDriver());
            connection = DriverManager.getConnection(
                    config.getUrlDb(),
                    config.getUrlLogin(),
                    config.getUrlPassword()
            );
        } catch (Exception e) {

        }
        return connection;
    }

    private void createBD(Config config) throws Exception {
        String url = config.getUrlDb();
        String nameDb = url.substring(url.lastIndexOf("/") + 1);
        String urlWithoutBd = url.substring(0, url.lastIndexOf("/") + 1);
        try (
                Connection connection = DriverManager.getConnection(
                        urlWithoutBd,
                        config.getUrlLogin(),
                        config.getUrlPassword()
                )
        ) {
            PreparedStatement preStatement = connection.prepareStatement("select 1 from pg_database where datname = ?");
            preStatement.setString(1, nameDb);
            ResultSet rs = preStatement.executeQuery();
            if (!rs.next()) {
                String query = String.format("create database %s with owner = postgres encoding = 'UTF8' TABLESPACE = pg_default", nameDb);
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        } catch (Exception e) {

        }
    }
}
