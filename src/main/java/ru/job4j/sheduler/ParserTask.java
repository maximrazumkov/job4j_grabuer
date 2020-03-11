package ru.job4j.sheduler;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.job4j.db.DbService;
import ru.job4j.db.model.Vacancy;
import ru.job4j.parser.ParserSqlSite;

import java.sql.Timestamp;
import java.util.List;

public class ParserTask implements Job {

    private Timestamp lastRun;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        DbService dbService = (DbService) dataMap.get("db");
        ParserSqlSite parserSqlSite = new ParserSqlSite(lastRun);
        List<Vacancy> vacancies = parserSqlSite.apply("https://www.sql.ru/forum/job-offers");
        dbService.insertNewVacancys(vacancies);
        lastRun = new Timestamp(System.currentTimeMillis());
    }
}
