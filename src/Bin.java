import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Bin {

    public static void main(String[] args) {



    }

    public static ArrayList<Integer>[] solveBin(int[] i, int b){
        ArrayList<Integer> items = new ArrayList(Arrays.asList(i));
        Collections.sort(items);

        ArrayList<Integer>[] bins = new ArrayList[3];
        int binCounter = 0;

        ArrayList<Integer> result = new ArrayList();

        for (int j = 0; j < 3; j++) {
            int currentHighest = items.get(items.size() - 1);


            int counter = 0;
            while (currentHighest + items.get(counter) < b){
                counter++;
            }
            currentHighest += items.get(counter);
            int counter2 = 0;
            while (currentHighest + items.get(counter2) < b){
                counter++;
            }

        }

        while(items.size() > 0 && itemsCanBeAdded(bins, items.get(0), b)){
            int greatestNumber = items.get(items.size() - 1);
            int current = items.get(0);

            forloop: for (int item: items){
                if (item + greatestNumber < b){
                    current  = item;
                } else if(item + greatestNumber == b){
                    bins[binCounter].add(greatestNumber);
                    bins[binCounter].add(item);
                    bins[binCounter].remove(greatestNumber);
                    bins[binCounter].remove(item);
                    binCounter += 1;
                    break forloop;
                }
                else {
                    bins[binCounter].add(greatestNumber);
                    bins[binCounter].add(current);
                    bins[binCounter].remove(greatestNumber);
                    bins[binCounter].remove(current);
                    break forloop;
                }
            }
        }

        return bins;
    }

    private static boolean itemsCanBeAdded(ArrayList<Integer>[] bins, int smallNumber, int b){
        for (ArrayList<Integer> bin: bins){
            int total = 0;
            for(int current: bin){
                total += current;
            }
            total += smallNumber;
            if (total > b) return false;
        }
        return true;
    }

}
