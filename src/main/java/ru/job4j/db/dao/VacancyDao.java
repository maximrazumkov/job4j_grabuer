package ru.job4j.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.db.model.Vacancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VacancyDao {
    private final Connection connection;

    private static final Logger LOGGER = LogManager.getLogger(VacancyDao.class.getName());

    public VacancyDao(Connection connection) {
        this.connection = connection;
    }

    public List<Vacancy> findAll() {
        String query = "select * from vacancy";
        List<Vacancy> vacancies = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
             ResultSet rs = statement.executeQuery(query);
             while (rs.next()) {
                 vacancies.add(new Vacancy(
                         rs.getInt("id"),
                         rs.getString("name"),
                         rs.getString("text"),
                         rs.getString("link")
                 ));
             }
        } catch (Exception e) {
            LOGGER.error("При попытке получить данные из БД произошла ошибка: {}", e.getMessage());
            throw new RuntimeException("Не удалось получить данные из БД", e);
        }
        return vacancies;
    }

    public void insertAll(List<Vacancy> vacancys) {
        String query = "insert into vacancy(name, text, link) values (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (Vacancy vacancy : vacancys) {
                ps.setString(1, vacancy.getName());
                ps.setString(2, vacancy.getText());
                ps.setString(3, vacancy.getLink());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            LOGGER.error("При попытке записать данные в БД произошла ошибка: {}", e.getMessage());
            throw new RuntimeException("Не удалось записать данные в БД", e);
        }
    }
}
