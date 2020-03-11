package ru.job4j.db;

import java.sql.Connection;

public interface DbConnectionFactory<T> {
    Connection getPostgryConnection(T config);
}
