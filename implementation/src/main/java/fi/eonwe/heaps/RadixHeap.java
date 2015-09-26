package fi.eonwe.heaps;

import com.carrotsearch.hppcrt.cursors.LongCursor;
import com.carrotsearch.hppcrt.lists.LongArrayList;
import com.carrotsearch.hppcrt.procedures.LongProcedure;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class RadixHeap {

    public static final int UNAVAILABLE_KEY = Integer.MIN_VALUE;
    private static final long UNSET = createFrom(-1, UNAVAILABLE_KEY);
    private static final int MAX_BUCKET = 33;

    private LongArrayList[] buckets = new LongArrayList[MAX_BUCKET];
    private int lastDeleted = 0;
    private int size = 0;
    private int[] bucketSizes;

    public RadixHeap(int[] bucketSizes) {
        this.bucketSizes = bucketSizes;
        buckets[0] = new LongArrayList(bucketSizeFor(0));
    }

    RadixHeap(int bucketSize) {
        this(createArrayOf(MAX_BUCKET, bucketSize));
        if (bucketSize <= 0) throw new IllegalArgumentException("Bucket default size must be strictly positive (was " + bucketSize + ")");
    }

    RadixHeap() { this(128); }

    private int bucketSizeFor(int index) {
        if (index >= bucketSizes.length) {
            return bucketSizes[bucketSizes.length - 1];
        }
        return bucketSizes[index];
    }

    private static int[] createArrayOf(int size, int content) {
        int[] array = new int[size];
        Arrays.fill(array, content);
        return array;
    }

    private LongArrayList getBucket(int i) {
        LongArrayList list = buckets[i];
        if (list == null) {
            list = new LongArrayList(bucketSizeFor(i));
            buckets[i] = list;
        }
        return list;
    }

    private int getBucketIndex(int value) {
        int xorred = lastDeleted ^ value;
        if (xorred == 0) return 0;
        return getLargestBitSet(xorred) + 1;
    }

    /**
     *
     * @param key any value barring {@linkplain #UNAVAILABLE_KEY}
     * @param data data
     * @return a long comprising of both the data and key. Can be used by {@linkplain #decreaseKey(int, long)} to change the key.
     */
    public long insert(int key, int data) {
        if (key == UNAVAILABLE_KEY) {
            throw new IllegalArgumentException("Key must not be " + UNAVAILABLE_KEY);
        }
        int bucketIndex = getBucketIndex(key);
        long result = createFrom(data, key);
        getBucket(bucketIndex).add(result);
        size++;
        return result;
    }

    /**
     * Upda
     * @param newKey any value barring {@linkplain #UNAVAILABLE_KEY}
     * @param packedVal packed value consisting of both key and data
     * @return a long comprising of both the data and and new key.
     */
    public long decreaseKey(int newKey, long packedVal) {
        if (newKey == UNAVAILABLE_KEY) {
            throw new IllegalArgumentException("Key must not be " + UNAVAILABLE_KEY);
        }
        if (newKey >= lastDeleted) {
            final int oldKey = extractKey(packedVal);
            if (oldKey <= newKey) {
                if (oldKey == newKey) return packedVal;
                throw new IllegalArgumentException("New key must be <= old key ( " + newKey + " !<= " + oldKey + " )");
            }
            int bucketIndex = getBucketIndex(oldKey);
            LongArrayList array = getBucket(bucketIndex);
            int lastIndex = array.size() - 1;
            if (lastIndex > 0) {
                // Find the value we're removing from the array and copy the last value
                // in the array into its place. Update the size of the array.
                int indexOfRemoved = -1;
                for (int i = 0; i < array.size(); i++) {
                    long val = array.get(i);
                    if (val == packedVal) {
                        indexOfRemoved = i;
                        break;
                    }
                }
                array.set(indexOfRemoved, array.get(lastIndex));
            }
            array.remove(lastIndex);
            size--;
            return insert(newKey, extractData(packedVal));
        }
        throw new IllegalArgumentException("Cannot decrease the key under last deleted key (-> " + newKey + " when last deleted was " + lastDeleted + ")");
    }

    private void removeMin(final long packedVal, int indexInBucket) {
        final int deletedKey = extractKey(packedVal);
        int bucketIndex = getBucketIndex(deletedKey);
        lastDeleted = deletedKey;
        size--;
        if (bucketIndex == 0) {
            handleSmallestBucket(indexInBucket);
        } else {
            LongArrayList arrayList = getBucket(bucketIndex);
            arrayList.forEach((LongProcedure) new LongProcedure() {
                @Override
                public void apply(long value) {
                    if (value != packedVal) {
                        RadixHeap.this.insert(extractKey(value), extractData(value));
                        size--;
                    }
                }
            });
            arrayList.clear();
        }
    }

    private void handleSmallestBucket(int indexInBucket) {
        LongArrayList arrayList = getBucket(0);
        int lastIndex = arrayList.size() - 1;
        if (lastIndex > 0) {
            arrayList.set(indexInBucket, arrayList.get(lastIndex));
        }
        arrayList.remove(lastIndex);
    }

    private int extractSmallest() {
        LongArrayList smallestBucket = buckets[0];
        if (!smallestBucket.isEmpty()) {
            return 0;
        }
        return -1;
    }

    public long removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        int smallestKey = Integer.MAX_VALUE;
        long smallestPacked = UNSET;
        int smallestIndex = extractSmallest();
        if (smallestIndex >= 0) {
            smallestPacked = buckets[0].get(smallestIndex);
        } else {
            for (int i = 1; i < buckets.length; i++) {
                LongArrayList list = buckets[i];
                if (list != null && !list.isEmpty()) {
                    for (LongCursor c : list) {
                        int key = extractKey(c.value);
                        if (key < smallestKey) {
                            smallestKey = key;
                            smallestPacked = c.value;
                            smallestIndex = c.index;
                        }
                    }
                    break;
                }
            }
        }
        if (smallestPacked == UNSET) {
            throw new IllegalStateException("No minimum found");
        }
        removeMin(smallestPacked, smallestIndex);
        return smallestPacked;
    }

    public boolean isEmpty() { return size() == 0; }

    public static int extractData(long val) {
        return (int) val;
    }

    public static int extractKey(long val) {
        return (int) (val >>> 32);
    }

    public static long createFrom(int data, int key) {
        return data | ((long) key) << 32;
    }

    public int size() { return size; }

    private static int getLargestBitSet(int val) {
        if (val == 0) return -1;
        int leadingZeroes = Integer.numberOfLeadingZeros(val);
        return 31 - leadingZeroes;
    }

    public Map<Integer, String> getPrettyContent() {
        int i = 0;
        TreeMap<Integer, String> map = new TreeMap<>();
        for (LongArrayList l : buckets) {
            if (l != null) {
                String bucketAsString;
                if (l.size() > 10) {
                    bucketAsString = String.format("< %d values >", l.size());
                } else {
                    final StringBuilder sb = new StringBuilder("[ ");
                    l.forEach(new LongProcedure() {
                        boolean isFirst = true;
                        @Override
                        public void apply(long value) {
                            if (isFirst) isFirst = false;
                            else sb.append(", ");
                            sb.append(extractKey(value));
                        }
                    });
                    sb.append(" ]");
                    bucketAsString = sb.toString();
                }
                map.put(i, String.format("%d: %s", i, bucketAsString));
            }
            i++;
        }
        return map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : getPrettyContent().entrySet()) {
            sb.append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

}
