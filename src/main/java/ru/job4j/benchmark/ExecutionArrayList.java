package ru.job4j.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;

@State(Scope.Benchmark)
public class ExecutionArrayList {

    @Param({ "10000000" })
    public int iterations;

    public List<Integer> list;

    @Setup(Level.Invocation)
    public void setUp() {
        list = new ArrayList<>();
    }
}
