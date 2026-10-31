
import java.util.Arrays;

public class Solution {

    private record Component(int maxValue, int originalValue) {}

    private static final int TOTAL_DIGITS = 10;

    public long maximizeSum(int[] input, int maxTotalOperations) {
        Component[] components = createComponentsSortedByDifference(input);
        return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations);
    }

    private long calculateMaxSumAfterMaxTotalOperations(Component[] components, int maxTotalOperations) {
        long maxSum = 0;
        for (int i = 0; i < maxTotalOperations; ++i) {
            maxSum += components[i].maxValue;
        }
        for (int i = maxTotalOperations; i < components.length; ++i) {
            maxSum += components[i].originalValue;
        }
        return maxSum;
    }

    private static Component[] createComponentsSortedByDifference(int[] input) {
        Component[] components = new Component[input.length];
        for (int i = 0; i < input.length; ++i) {
            int maxValue = rearrangeDigitsForMaxValue(input[i]);
            components[i] = new Component(maxValue, input[i]);
        }
        Arrays.sort(components, (x, y) -> (y.maxValue - y.originalValue) - (x.maxValue - x.originalValue));
        return components;
    }

    private static int rearrangeDigitsForMaxValue(int value) {
        int[] frequencyDigits = new int[TOTAL_DIGITS];
        while (value > 0) {
            int digit = value % 10;
            ++frequencyDigits[digit];
            value /= 10;
        }

        int maxValue = 0;
        for (int digit = frequencyDigits.length - 1; digit >= 0; --digit) {
            while (frequencyDigits[digit] > 0) {
                maxValue = 10 * maxValue + digit;
                --frequencyDigits[digit];
            }
        }

        return maxValue;
    }
}
