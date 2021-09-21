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

//    public static ArrayList<Integer>[] solveBin(int[] i, int b){
//        ArrayList<Integer> items = new ArrayList(Arrays.asList(i));
//        Collections.sort(items);
//
//        ArrayList<Integer>[] bins = new ArrayList[3];
//        int binCounter = 0;
//
//        ArrayList<Integer> result = new ArrayList();
//
//        for (int j = 0; j < 3; j++) {
//            int currentHighest = items.get(items.size() - 1);
//
//
//            int counter = 0;
//            while (currentHighest + items.get(counter) < b){
//                counter++;
//            }
//            currentHighest += items.get(counter);
//            int counter2 = 0;
//            while (currentHighest + items.get(counter2) < b){
//                counter++;
//            }
//
//        }
//
//        while(items.size() > 0 && itemsCanBeAdded(bins, items.get(0), b)){
//            int greatestNumber = items.get(items.size() - 1);
//            int current = items.get(0);
//
//            forloop: for (int item: items){
//                if (item + greatestNumber < b){
//                    current  = item;
//                } else if(item + greatestNumber == b){
//                    bins[binCounter].add(greatestNumber);
//                    bins[binCounter].add(item);
//                    bins[binCounter].remove(greatestNumber);
//                    bins[binCounter].remove(item);
//                    binCounter += 1;
//                    break forloop;
//                }
//                else {
//                    bins[binCounter].add(greatestNumber);
//                    bins[binCounter].add(current);
//                    bins[binCounter].remove(greatestNumber);
//                    bins[binCounter].remove(current);
//                    break forloop;
//                }
//            }
//        }
//
//        return bins;
//    }
//
//    private static boolean itemsCanBeAdded(ArrayList<Integer>[] bins, int smallNumber, int b){
//        for (ArrayList<Integer> bin: bins){
//            int total = 0;
//            for(int current: bin){
//                total += current;
//            }
//            total += smallNumber;
//            if (total > b) return false;
//        }
//        return true;
//    }

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
