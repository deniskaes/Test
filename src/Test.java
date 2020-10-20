import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Test {

    public static String generateNum(List<Integer> numbers) {
        final AtomicInteger first;
        if (numbers.isEmpty()) {
            return String.format("%03d", 1);
        } else {
            List<Integer> collInUniqueSorted = numbers.stream()
                    .distinct()
                    .sorted(Comparator.comparingInt(Integer::intValue))
                    .collect(Collectors.toList());

            first = collInUniqueSorted.isEmpty() ? new AtomicInteger(1) : new AtomicInteger(collInUniqueSorted.get(0));

            List<Integer> collectionWithGaps = collInUniqueSorted.stream()
                    .skip(1)
                    .map(it -> {
                        if (first.intValue() + 1 < it) {
                            int buf = first.intValue();
                            first.set(it);
                            return buf + 1;
                        } else {
                            first.set(it);
                            return -1;
                        }
                    }).collect(Collectors.toList());

            return String.format("%03d", collectionWithGaps.stream()
                    .filter(it -> it > 0)
                    .findFirst()
                    .orElse(collInUniqueSorted.get(collInUniqueSorted.size() - 1) + 1));
        }
    }

    public static void main(String[] args) {
        System.out.println(Test.generateNum(Arrays.asList(4, 8, 1, 4, 2, 9)));
    }
}
