package ru.job4j.db;

import ru.job4j.db.model.Vacancy;

import java.util.List;

public interface DbService {
    void insertNewVacancys(List<Vacancy> vacancys);
}
