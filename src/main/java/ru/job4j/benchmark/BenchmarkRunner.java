package ru.job4j.benchmark;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void loopArrayList(ExecutionArrayList plan) {
        for (int i = 0; i < plan.iterations; i++) {
            plan.list.add(i);
        }
    }


    @Benchmark
    public void loopLinckedList(ExecutionLinckedList plan) {
        for (int i = 0; i < plan.iterations; i++) {
            plan.list.add(i);
        }
    }
}
