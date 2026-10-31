
using System;

public class Solution
{
    private record Component(int MaxValue, int OriginalValue) { }

    private static readonly int TOTAL_DIGITS = 10;

    public long MaximizeSum(int[] input, int maxTotalOperations)
    {
        Component[] components = CreateComponentsSortedByDifference(input);
        return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations);
    }

    private long calculateMaxSumAfterMaxTotalOperations(Component[] components, int maxTotalOperations)
    {
        long maxSum = 0;
        for (int i = 0; i < maxTotalOperations; ++i)
        {
            maxSum += components[i].MaxValue;
        }
        for (int i = maxTotalOperations; i < components.Length; ++i)
        {
            maxSum += components[i].OriginalValue;
        }
        return maxSum;
    }

    private static Component[] CreateComponentsSortedByDifference(int[] input)
    {
        Component[] components = new Component[input.Length];
        for (int i = 0; i < input.Length; ++i)
        {
            int maxValue = RearrangeDigitsForMaxValue(input[i]);
            components[i] = new Component(maxValue, input[i]);
        }
        Array.Sort(components, (x, y) => (y.MaxValue - y.OriginalValue) - (x.MaxValue - x.OriginalValue));
        return components;
    }

    private static int RearrangeDigitsForMaxValue(int value)
    {
        int[] frequencyDigits = new int[TOTAL_DIGITS];
        while (value > 0)
        {
            int digit = value % 10;
            ++frequencyDigits[digit];
            value /= 10;
        }

        int maxValue = 0;
        for (int digit = frequencyDigits.Length - 1; digit >= 0; --digit)
        {
            while (frequencyDigits[digit] > 0)
            {
                maxValue = 10 * maxValue + digit;
                --frequencyDigits[digit];
            }
        }

        return maxValue;
    }
}
