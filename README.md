# jradixheap
Radix heap of java ints with int keys.

There's clearly still some room for performance improvements:

    # Run complete. Total time: 00:28:55
    
    Benchmark                                             (removeMinFrequency)  (repeats)  (size)   Mode  Cnt       Score       Error  Units
    HeapBenchmarks.insertFibo                                              N/A         64     N/A  thrpt   10    8584.991 ±   127.601  ops/s
    HeapBenchmarks.insertFibo                                              N/A       4096     N/A  thrpt   10     138.538 ±     2.203  ops/s
    HeapBenchmarks.insertFibo                                              N/A     262144     N/A  thrpt   10       2.299 ±     0.142  ops/s
    HeapBenchmarks.insertHPPCRT                                            N/A         64     N/A  thrpt   10  294096.898 ± 15605.273  ops/s
    HeapBenchmarks.insertHPPCRT                                            N/A       4096     N/A  thrpt   10    4548.633 ±   319.460  ops/s
    HeapBenchmarks.insertHPPCRT                                            N/A     262144     N/A  thrpt   10      69.166 ±     5.296  ops/s
    HeapBenchmarks.insertJDK                                               N/A         64     N/A  thrpt   10  192829.568 ±  4578.554  ops/s
    HeapBenchmarks.insertJDK                                               N/A       4096     N/A  thrpt   10    1592.574 ±   223.231  ops/s
    HeapBenchmarks.insertJDK                                               N/A     262144     N/A  thrpt   10      22.902 ±    12.831  ops/s
    HeapBenchmarks.insertRadix                                             N/A         64     N/A  thrpt   10  283137.066 ± 43695.144  ops/s
    HeapBenchmarks.insertRadix                                             N/A       4096     N/A  thrpt   10    3985.895 ±   904.795  ops/s
    HeapBenchmarks.insertRadix                                             N/A     262144     N/A  thrpt   10      73.532 ±    13.379  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     2        N/A      64  thrpt   10   43919.484 ±   244.288  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     2        N/A    4096  thrpt   10     464.978 ±    26.232  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     2        N/A  262144  thrpt   10       5.091 ±     0.820  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     8        N/A      64  thrpt   10  103010.124 ±   801.784  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     8        N/A    4096  thrpt   10    1208.884 ±     3.361  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                     8        N/A  262144  thrpt   10      12.473 ±     2.357  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    16        N/A      64  thrpt   10  136535.863 ±   317.009  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    16        N/A    4096  thrpt   10    1680.846 ±    14.751  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    16        N/A  262144  thrpt   10      18.747 ±     2.482  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    32        N/A      64  thrpt   10  158176.523 ±   400.903  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    32        N/A    4096  thrpt   10    2149.723 ±    43.255  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                    32        N/A  262144  thrpt   10      23.203 ±     1.905  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   128        N/A      64  thrpt   10  172592.801 ±  4019.495  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   128        N/A    4096  thrpt   10    2656.646 ±   112.696  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   128        N/A  262144  thrpt   10      29.726 ±     1.894  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   512        N/A      64  thrpt   10  172022.064 ±  1258.020  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   512        N/A    4096  thrpt   10    2776.941 ±     4.151  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinFibonacci                   512        N/A  262144  thrpt   10      31.221 ±     3.012  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         2        N/A      64  thrpt   10   75838.389 ±  1988.986  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         2        N/A    4096  thrpt   10     524.226 ±     3.921  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         2        N/A  262144  thrpt   10       0.211 ±     0.003  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         8        N/A      64  thrpt   10  100577.224 ±  3421.735  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         8        N/A    4096  thrpt   10     335.878 ±     1.889  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                         8        N/A  262144  thrpt   10       0.110 ±     0.001  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        16        N/A      64  thrpt   10   88931.138 ±  2453.503  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        16        N/A    4096  thrpt   10     337.666 ±     1.076  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        16        N/A  262144  thrpt   10       0.103 ±     0.001  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        32        N/A      64  thrpt   10   91146.984 ±  3604.468  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        32        N/A    4096  thrpt   10     333.060 ±     0.637  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                        32        N/A  262144  thrpt   10       0.093 ±     0.001  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       128        N/A      64  thrpt   10   89314.199 ±  2635.581  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       128        N/A    4096  thrpt   10     324.090 ±     0.545  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       128        N/A  262144  thrpt   10       0.091 ±     0.001  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       512        N/A      64  thrpt   10   87624.925 ±  4065.383  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       512        N/A    4096  thrpt   10     325.215 ±     0.814  ops/s
    PriorityHeapBenchmarks.decreaseAndRemoveMinRadix                       512        N/A  262144  thrpt   10       0.097 ±     0.001  ops/s

