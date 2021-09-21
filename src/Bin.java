import java.util.ArrayList;

public class Bin {
    int capacity;
    ArrayList<Integer> items;

    public Bin(int capacity, ArrayList<Integer> items) {
        this.capacity = capacity;
        this.items = items;
    }
    public Bin(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public int getRemainingCapacity() {
        int sum = 0;
        for (Integer i: items){
            sum += i;
        }
        return capacity - sum;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Bin[");
        for (int i = 0; i < items.size(); i++) {
            if (i == items.size() - 1){
                result.append(items.get(i));
            }
            else result.append(items.get(i)).append(",");
        }
        result.append("]");
        return result.toString();
    }
}
