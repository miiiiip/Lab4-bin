import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lab {

    public static int b = 100;
    public static List<Integer> numbers = new ArrayList<>();
    public static int comparisonCount = 0;

    public static void main(String[] args) {

        promptUser();

        long time1 = System.currentTimeMillis();
        for (Bin bin : calc(numbers, b)){
            System.out.println(bin);
        }
        long time2 = System.currentTimeMillis();

        System.out.println("Made " + comparisonCount + " comparisons.");
        System.out.println("Finished in " + (time2 - time1) + " ms.");

    }

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

        if (split.length - 2 != number){
            System.out.println("Incorrect number of entries entered.");
            promptUser();
        }

        numbers = Arrays.stream(split).map(Integer::parseInt)
                .collect(Collectors.toList())
                .subList(2, split.length);
    }

    //Algorithm runs here
    //Uses an array of arrays
    private static List<Bin> calc(List<Integer> items, int b){
        List<Integer> sorted = items.stream()
                .sorted((o1, o2) -> {
                    comparisonCount++;
                    return o1.compareTo(o2) * -1;
                })
                .collect(Collectors.toList());

        List<Bin> bins = Arrays.asList(
                new Bin(b), new Bin(b), new Bin(b)
        );

        int currentBinIndex = 0;

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

        return bins;
    }

}
