package ru.job4j.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void loopArrayList(ExecutionArrayList plan, Blackhole blackhole) {
        for (int i = 0; i < plan.iterations; i++) {
            plan.list.add(i);
            blackhole.consume(i);
        }
    }

    @Benchmark
    public void loopLinckedList(ExecutionLinckedList plan, Blackhole blackhole) {
        for (int i = 0; i < plan.iterations; i++) {
            plan.list.add(i);
            blackhole.consume(i);
        }
    }
}
