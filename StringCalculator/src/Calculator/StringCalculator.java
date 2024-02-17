package Calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",|\n";
        String numbersWithoutDelimiter = numbers;

        // Check for custom delimiter
        Matcher matcher = Pattern.compile("//(\\[.*?\\])\n(.*)").matcher(numbers);
        if (matcher.matches()) {
            delimiter = delimiter + "|" + Pattern.quote(matcher.group(1));
            numbersWithoutDelimiter = matcher.group(2);
        }

        List<Integer> numbersList = Arrays.stream(numbersWithoutDelimiter.split(delimiter))
                .map(Integer::parseInt)
                .filter(n -> n <= 1000)
                .collect(Collectors.toList());

        // Check for negative numbers
        List<Integer> negatives = numbersList.stream().filter(n -> n < 0).collect(Collectors.toList());
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }

        return numbersList.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        // Example usage:
        System.out.println(add(""));      // Output: 0
        System.out.println(add("1"));     // Output: 1
        System.out.println(add("1,5"));   // Output: 6
    }
}
