# Documentation, explanation of the algorithm
Our algorithm takes an array of integers (entries to be packed)
and a maximum bin size, then
creates an array of arrays (the three bins).
We begin by sorting the array of integers in DECENDING order 
(comparisons counted, using mergesort so O(nlogn))
We then grab the first number down the list that will fit inside the bin
This will always be the largest number which can fit, as the array is sorted decending.
This is repeated looking at the sum of our current number with other numbers,
going down the list until we find a sum which fits into the bin.
This stops when either the sum is the same size as the bin,
or there are no new sums which would fit

This is repeated three times, once for each bin.

This produces a close enough solution because it's grabbing maximum sums at each step,
this means that it only produces a non-optimal solution when: 

sum + n[z] < sum + n[z-1] + n[z-2] <= bin
(z is the index where we sum non-optimally)


# Examples of optimal/non-optimal solutions, test data

NON-OPTIMAL CASE:
b = 100
Entries = 100, 100, 50, 30, 25, 25

OUR BINS 1[100], 2[100], 3[50, 30]
(This is suboptimal)

OPTIMAL BINS 1[100], 2[100], 3[50, 25, 25]

Our bins are suboptimal
Made 20 comparisons.
Finished in 2 ms.

--------------------------------------------------------------------------------------

OPTIMAL CASE 1:
b = 20
Entries = 12, 4, 8, 15, 9, 3, 1, 10

OUR BINS 1[15, 4, 1] 2[12, 8] 3[10, 9]

Made 42 comparisons.
Finished in 2 ms.

--------------------------------------------------------------------------------------

OPTIMAL CASE 2:
b = 100
Entries = 100, 100, 50, 25, 25, 10

OUR BINS 1[100], 2[100], 3[50, 25, 25]

Made 20 comparisons.
Finished in 4 ms.

# Efficiency analysis
__WORST CASE EFFICIENCY ANALYSIS:__

Our worst case is dependent entirely upon bin size

bin = b

Worst case:

An array with

n-((b-1)*3) number of entries that are equal to "(b/2)-1"

and (b-1)*3 number of entries that are equal to "1"

EXAMPLE OF WORST CASE ARRAY (SORTED):

b = 10

Array{6, 6, ..., 6, 1, 1, ..., 1}

END RESULT:

bin1[6, 1, 1, 1, 1] bin2[6, 1, 1, 1, 1] bin3[6, 1, 1, 1, 1]

however, it needs to search through n-((b-1)*3) number of entries "6"

Therefore if n=1,000,000, it essentially needs to look through 1,000,000 elements ((b/2-1)*3) number of times

which in this case would be 12*n number of times.

with a bin size of 100, it would be 147*n number of times

```java
while(!sorted.isEmpty() && currentBinIndex < bins.size()){
            Bin currentBin = bins.get(currentBinIndex);

            Integer first = sorted.stream()
                    .filter(integer -> {
                        comparisonCount++;
                        return integer <= currentBin.getRemainingCapacity();
                    })
                    .findFirst().orElse(null);

            if (first == null) currentBinIndex++;
            else{
                currentBin.items.add(first);
                sorted.remove(first);
            }
        }
```
_The while loop:_

runs (b/2-1) times for each bin, therefore running (b/2-1)*3 times total

```java
while(!sorted.isEmpty() && currentBinIndex < bins.size()){
```

_The filter:_

In the worst case runs n-(b/2-1) times for each iteration of the while loop

```java
Integer first = sorted.stream()
        .filter(integer -> {
        comparisonCount++;
        return integer <= currentBin.getRemainingCapacity();
        })
        .findFirst().orElse(null);
```

__Therefore the efficiency in the worst case for the while loop and it's filter iterations is:__

__((b/2-1)*3) * (n-(b/2-1))__

__or Essentially (b/2-1)*3) * n__

This is always going to be a constant multiplied by n, __so therefore the while loop E O(n).__

#__ALGORITHM EFFICIENCY:__

At the start of the algorithm we sort the entries using `Streams.sorted()` which uses mergesort under the hood, giving it an efficiency of `n log(n)`

Our while loop is linear, therefore __the efficiency of our algorithm, `calc` is in ??(n log(n)).__