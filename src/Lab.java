import java.util.*;
import java.util.stream.Collectors;

public class Lab {

    public static int b = 100;
    public static List<Integer> numbers = new ArrayList<>();
    public static int comparisonCount = 0;

    public static void main(String[] args) {

        // Prompt the user for necessary input
        promptUser();

        // Time the operation
        long time1 = System.currentTimeMillis();
        for (Bin bin : calc(numbers, b)){
            System.out.println(bin);
        }
        long time2 = System.currentTimeMillis();

        System.out.println("Made " + comparisonCount + " comparisons.");
        System.out.println("Finished in " + (time2 - time1) + " ms.");

    }

    /**
     * promptUser: Requests the user for the input to be worked on and sets the value
     * of the global variables to the appropriate values.
     */
    private static void promptUser(){
        System.out.println("Bin size for first number\nNumber of entries for second number\nEntries to be packed for all following numbers\nUse spaces as delimiters between nums\nPlease enter your input:\n");
        String input = new Scanner(System.in).nextLine();
        String[] split = input.trim().split(" ");
        if (split.length < 3){
            System.out.println("Not enough input entered.");
            promptUser();
        }

        b = Integer.parseInt(split[0]);

        int number = Integer.parseInt(split[1]);

        // If the user entered too few inputs, then prompt the user again
        if (split.length - 2 != number){
            System.out.println("Incorrect number of entries entered.");
            promptUser();
        }

        // Convert the array split into a list, map each value from a string to an integer, then
        // get a subList of the numbers starting from index 2 upto the end of the array.
        numbers = Arrays.stream(split).map(Integer::parseInt)
                .collect(Collectors.toList())
                .subList(2, split.length);
    }

    /**
     * This function takes an input of items and a bin size, then puts the items in three bins of size b.
     * Efficiency is θ(n log n)
     * @param items: the items to be put in bins
     * @param b: the bin size
     * @return the three bins
     */
    private static List<Bin> calc(List<Integer> items, int b){
        // Sort the items in descending order
        List<Integer> sorted = items.stream()
                .sorted((o1, o2) -> {
                    comparisonCount++; // Keep track of each comparison
                    return o1.compareTo(o2) * -1;
                })
                .collect(Collectors.toList());

        List<Bin> bins = Arrays.asList(
                new Bin(b), new Bin(b), new Bin(b)
        );

        int currentBinIndex = 0;

        while(!sorted.isEmpty() && currentBinIndex < bins.size()){
            Bin currentBin = bins.get(currentBinIndex);

            // Find the first largest number that can be added in the current bin
            Integer first = sorted.stream()
                    .filter(integer -> {
                        comparisonCount++;
                        return integer <= currentBin.getRemainingCapacity();
                    })
                    .findFirst().orElse(null);

            // If nothing can be added, then move on to the next bin
            if (first == null) currentBinIndex++;
            else{
                currentBin.items.add(first);
                sorted.remove(first);
            }
        }

        return bins;
    }

}
