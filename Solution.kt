
class Solution {

    private data class Component(var maxValue: Int, var originalValue: Int)

    private companion object {
        const val TOTAL_DIGITS = 10
    }

    fun maximizeSum(input: IntArray, maxTotalOperations: Int): Long {
        val components = createComponentsSortedByDifference(input)
        return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations)
    }

    private fun calculateMaxSumAfterMaxTotalOperations(components: Array<Component>, maxTotalOperations: Int): Long {
        var maxSum: Long = 0L
        for (i in 0..<maxTotalOperations) {
            maxSum += components[i].maxValue
        }
        for (i in maxTotalOperations..<components.size) {
            maxSum += components[i].originalValue
        }
        return maxSum
    }

    private fun createComponentsSortedByDifference(input: IntArray): Array<Component> {
        val components = Array<Component>(input.size) { Component(0, 0) }
        for (i in input.indices) {
            val maxValue = rearrangeDigitsForMaxValue(input[i]);
            components[i].maxValue = maxValue
            components[i].originalValue = input[i]
        }
        components.sortWith() { x, y -> (y.maxValue - y.originalValue) - (x.maxValue - x.originalValue) };
        return components;
    }

    private fun rearrangeDigitsForMaxValue(value: Int): Int {
        var value = value
        val frequencyDigits = IntArray(TOTAL_DIGITS)
        while (value > 0) {
            val digit = value % 10
            ++frequencyDigits[digit]
            value /= 10
        }

        var maxValue = 0
        for (digit in frequencyDigits.size - 1 downTo 0) {
            while (frequencyDigits[digit] > 0) {
                maxValue = 10 * maxValue + digit
                --frequencyDigits[digit]
            }
        }

        return maxValue
    }
}
