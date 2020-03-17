package ru.job4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.config.Config;
import ru.job4j.config.SimpleLoaderConfig;
import ru.job4j.db.DbConnectionFactory;
import ru.job4j.db.DbService;
import ru.job4j.db.SimpleDbConnectionFactory;
import ru.job4j.db.SimpleDbService;
import ru.job4j.db.dao.VacancyDao;
import ru.job4j.sheduler.ParserTask;
import ru.job4j.sheduler.QuartzScheduler;
import ru.job4j.sheduler.Scheduler;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) {
        new App().run(args);
    }

    public void run(String[] args) {
        try {
            if (args.length == 0) {
                throw new RuntimeException("Необходимо указать путь к файлу с настройками");
            }
            DbConnectionFactory<Config> connectionFactory = new SimpleDbConnectionFactory();
            Config config = new SimpleLoaderConfig().apply(args[0]);
            Connection connection = connectionFactory.getPostgryConnection(config);
            DbService dbService = new SimpleDbService(new VacancyDao(connection));
            Map<String, Object> services = new HashMap<>();
            Map<JobDetail, Set<Trigger>> tasks = new HashMap<>();
            services.put("db", dbService);
            JobDetail job = JobBuilder.newJob(ParserTask.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData(new JobDataMap(services))
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(cronSchedule(config.getTime()))
                    .build();
            tasks.put(job, new HashSet<Trigger>() { { add(trigger); } });
            Scheduler scheduler = new QuartzScheduler(tasks, new StdSchedulerFactory().getScheduler());
            scheduler.run();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
