package fi.eonwe.heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import com.carrotsearch.hppcrt.heaps.IntHeapPriorityQueue;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class HeapBenchmarks {

    private static final Random rng = new Random(0xcafebabe);
    private IntHeapPriorityQueue hppcrt;
    private RadixHeap radixHeap;
    private FibonacciHeap<Integer> fiboHeap;
    private PriorityQueue<Integer> jdkHeap;

    @Param({"64", "4096", "262144"})
    private int repeats;

    private int[] iterationOrder;

    @Setup(Level.Iteration)
    public void setup() {
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < repeats; i++) {
            tmp.add(i);
        }
        Collections.shuffle(tmp, rng);
        iterationOrder = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            iterationOrder[i] = tmp.get(i);
        }
        hppcrt = new IntHeapPriorityQueue();
        radixHeap = new RadixHeap();
        fiboHeap = new FibonacciHeap<>();
        jdkHeap = new PriorityQueue<>();
    }

    @Benchmark
    public int insertHPPCRT() {
        for (int i = 0; i < repeats; i++) {
            hppcrt.add(iterationOrder[i]);
        }
        return hppcrt.size();
    }

    @Benchmark
    public int insertRadix() {
        for (int i = 0; i < repeats; i++) {
            int val = iterationOrder[i];
            radixHeap.insert(val, val);
        }
        return radixHeap.size();
    }

    @Benchmark
    public int insertFibo() {
        for (int i = 0; i < repeats; i++) {
            int val = iterationOrder[i];
            fiboHeap.insert(new FibonacciHeapNode<>(val), val);
        }
        return fiboHeap.size();
    }

    @Benchmark
    public int insertJDK() {
        for (int i = 0; i < repeats; i++) {
            jdkHeap.add(iterationOrder[i]);
        }
        return jdkHeap.size();
    }

}
