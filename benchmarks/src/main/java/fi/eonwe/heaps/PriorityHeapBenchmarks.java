package fi.eonwe.heaps;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class PriorityHeapBenchmarks {

    @Param({"64", "4096", "262144"})
    private int size;

    @Param({"2", "8", "16", "32", "128", "512"})
    private int removeMinFrequency;

    private RadixHeap radixHeap;
    private long[] radixValues;

    private FibonacciHeap<Integer> fiboHeap;
    private FibonacciHeapNode<Integer>[] fiboNodes;

    @SuppressWarnings("unchecked")
    @Setup(Level.Invocation)
    public void setup() {
        radixHeap = new RadixHeap();
        fiboHeap = new FibonacciHeap<>();
        fiboNodes = new FibonacciHeapNode[size];
        radixValues = new long[size];
        for (int i = 0; i < size; i++) {
            int key = (i + 1) * removeMinFrequency;
            FibonacciHeapNode<Integer> fiboNode = new FibonacciHeapNode<>(i);
            fiboNodes[i] = fiboNode;
            fiboHeap.insert(fiboNode, key);
            radixValues[i] = radixHeap.insert(key, i);
        }
    }

    @Benchmark
    public int decreaseAndRemoveMinRadix() {
        long last = 0;
        for (int i = 0; i < size; i++) {
            if (i % removeMinFrequency == 0) {
                last = radixHeap.removeMin();
            } else {
                long oldVal = radixValues[i];
                radixValues[i] = radixHeap.decreaseKey(RadixHeap.extractKey(oldVal) - 1, oldVal);
            }
        }
        return RadixHeap.extractData(last);
    }

    @Benchmark
    public FibonacciHeapNode<Integer> decreaseAndRemoveMinFibonacci() {
        FibonacciHeapNode<Integer> removed = null;
        for (int i = 0; i < size; i++) {
            if (i % removeMinFrequency == 0) {
                removed = fiboHeap.removeMin();
            } else {
                FibonacciHeapNode<Integer> oldVal = fiboNodes[i];
                fiboHeap.decreaseKey(oldVal, oldVal.getKey() - 1);
            }
        }
        return removed;
    }


}
