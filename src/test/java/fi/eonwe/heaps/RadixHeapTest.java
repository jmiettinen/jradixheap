package fi.eonwe.heaps;

import com.google.common.collect.Sets;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 */
public class RadixHeapTest {

    Random rng;

    @Before
    public void setup() {
        rng  = new Random(0xcafebabe);
    }

    @Test
    public void extractMinSimple() {
        RadixHeap heap = new RadixHeap();

        final int count = 100_000;
        int[] numbers = new int[count];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rng.nextInt(50_000);
            heap.insert(i, numbers[i]);
            assertEquals(i+1, heap.size());
        }

        Arrays.sort(numbers);

        for (int expected : numbers) {
            int extractedVal = RadixHeap.extractKey(heap.removeMin());
            assertEquals(expected, extractedVal);
        }

        assertTrue(heap.isEmpty());
    }

    @Test
    public void decreaseKey() {
        RadixHeap heap = new RadixHeap();

        final int count = 10_000;
        final int divisor = 10;
        assertTrue(count % divisor == 0);
        long[] numbers = new long[count];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = heap.insert(i, i);
        }
        long sum = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            long val = numbers[i];
            int newKey = RadixHeap.extractKey(val) % divisor;
            heap.decreaseKey(val, newKey);
            assertEquals(count, heap.size());
        }
        for (int i = 0; i < count; i++) {
            long packed = heap.removeMin();
            sum += RadixHeap.extractKey(packed);
            assertEquals(count - i - 1, heap.size());
        }
        int checkSum = 0;
        for (int i = 0; i < divisor; i++) {
            checkSum += (count / divisor) * i;
        }

        assertEquals(checkSum, sum);
        assertTrue(heap.isEmpty());
    }

    @Test
    @Ignore("unfinished")
    public void decreaseKeyAndExtract() {
        RadixHeap heap = new RadixHeap();

        final int count = 10_000;
        final int divisor = 10;
        assertTrue(count % divisor == 0);
        long[] numbers = new long[count];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = heap.insert(i, i);
        }

        assertEquals(count, heap.size());
    }

    @Test
    public void compareToFibonacciHeap() {
        RadixHeap rHeap = new RadixHeap();
        FibonacciHeap<Integer> fHeap = new FibonacciHeap<>();

        Set<FibonacciHeapNode<Integer>> nodes = Sets.newHashSet();
        final int count = 3_000;
        int insertCounter = count + 1;
        for (int i = 0; i < count; i++) {
            FibonacciHeapNode<Integer> node = new FibonacciHeapNode<>(i);
            nodes.add(node);
            int key = i + 1;
            rHeap.insert(node.getData(), key);
            fHeap.insert(node, key);
        }
        while (!fHeap.isEmpty()) {
            FibonacciHeapNode<Integer> extracted = extractAndCheck(fHeap, rHeap);
            nodes.remove(extracted);
            for (FibonacciHeapNode<Integer> node : nodes) {
                int oldKey = (int) node.getKey();
                int newKey = oldKey - 1;
                fHeap.decreaseKey(node, newKey);
                rHeap.decreaseKey(RadixHeap.createFrom(node.getData(), oldKey), newKey);
            }
            while (rng.nextBoolean()) {
                FibonacciHeapNode<Integer> newNode = new FibonacciHeapNode<>(insertCounter++);
                nodes.add(newNode);
                int key = insertCounter;
                rHeap.insert(newNode.getData(), key);
                fHeap.insert(newNode, key);
            }

            if (!fHeap.isEmpty()) {
                FibonacciHeapNode<Integer> anotherExtracted = extractAndCheck(fHeap, rHeap);
                nodes.remove(anotherExtracted);
            }

        }
        assertEquals(fHeap.isEmpty(), rHeap.isEmpty());
    }

    private static FibonacciHeapNode<Integer> extractAndCheck(FibonacciHeap<Integer> fHeap, RadixHeap rHeap) {
        FibonacciHeapNode<Integer> extracted = fHeap.removeMin();
        long extractedVal = rHeap.removeMin();
        assertEquals((int) extracted.getData(), RadixHeap.extractData(extractedVal));
        assertEquals((int) extracted.getKey(), RadixHeap.extractKey(extractedVal));
        assertEquals(fHeap.size(), rHeap.size());
        return extracted;

    }

    @Test
    public void itPacksCorrectly() {
        for (int data = 0; data < 100_000; data++) {
            int key = rng.nextInt(Integer.MAX_VALUE);
            long packed = RadixHeap.createFrom(data, key);
            assertEquals(RadixHeap.extractKey(packed), key);
            assertEquals(RadixHeap.extractData(packed), data);
        }
    }

}
