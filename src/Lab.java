import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lab {

    public static final int b = 100;
    public static int comparisonCount = 0;

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(48, 30, 19, 36, 36, 27, 42, 42, 36, 24, 30);

        long time1 = System.currentTimeMillis();
        List<Bin> bins = calc(numbers, b);
        for (Bin bin : bins){
            System.out.println(bin);
        }
        long time2 = System.currentTimeMillis();

        System.out.println("Made " + comparisonCount + " comparisons.");
        System.out.println("Finished in " + (time2 - time1) + " ms.");
    }
    
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
