package ru.job4j.db;


import ru.job4j.db.dao.VacancyDao;
import ru.job4j.db.model.Vacancy;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SimpleDbService implements DbService {
    private final VacancyDao vacancyDao;

    public SimpleDbService(VacancyDao vacancyDao) {
        this.vacancyDao = vacancyDao;
    }

    @Override
    public void insertNewVacancys(List<Vacancy> vacancys) {
        List<Vacancy> newVacancy = new LinkedList<>();
        Set<Vacancy> vacancySet = new LinkedHashSet<>(vacancyDao.findAll());
        for (Vacancy vacancy : vacancys) {
            if (!vacancySet.contains(vacancy)) {
                newVacancy.add(vacancy);
            }
        }
        if (!newVacancy.isEmpty()) {
            vacancyDao.insertAll(newVacancy);
        }
    }
}
