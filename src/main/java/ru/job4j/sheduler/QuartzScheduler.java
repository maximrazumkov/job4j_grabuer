package ru.job4j.sheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QuartzScheduler extends BaseScheduler<JobDetail, Set<Trigger>> {

    private final org.quartz.Scheduler scheduler;

    public QuartzScheduler(Map<JobDetail, Set<Trigger>> tasks, Scheduler scheduler) {
        super(tasks);
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            if (scheduler.isStarted()) {
                stop();
            }
            scheduler.start();
            Map<JobDetail, Set<? extends Trigger>> tasks = new HashMap<>();
            for (Map.Entry<JobDetail, Set<Trigger>> taskEntry : getTasks().entrySet()) {
                tasks.put(taskEntry.getKey(), taskEntry.getValue());
            }
            scheduler.scheduleJobs(tasks, true);
        } catch (Exception e) {

        }
    }

    @Override
    public void stop() {
        try {
            scheduler.shutdown();
        } catch (Exception e) {

        }
    }

}
