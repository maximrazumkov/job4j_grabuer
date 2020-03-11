package ru.job4j.sheduler;

import java.util.Map;

public abstract class BaseScheduler<V, T> implements Scheduler {
    private final Map<V, T> tasks;

    public BaseScheduler(Map<V, T> tasks) {
        this.tasks = tasks;
    }

    public Map<V, T> getTasks() {
        return tasks;
    }
}
