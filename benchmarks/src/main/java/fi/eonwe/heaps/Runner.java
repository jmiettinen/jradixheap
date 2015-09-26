package fi.eonwe.heaps;

import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 */
public class Runner {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(".*Benchmark.*")
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(10)
                .build();
        new org.openjdk.jmh.runner.Runner(options).run();
    }

}
