
package main
import "slices"

type Component struct {
    maxValue      int
    originalValue int
}

const TOTAL_DIGITS = 10

func maximizeSum(input []int, maxTotalOperations int) int64 {
    components := createComponentsSortedByDifference(input)
    return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations)
}

func calculateMaxSumAfterMaxTotalOperations(components []Component, maxTotalOperations int) int64 {
    maxSum := int64(0)
    for i := 0; i < maxTotalOperations; i++ {
        maxSum += int64(components[i].maxValue)
    }
    for i := maxTotalOperations; i < len(components); i++ {
        maxSum += int64(components[i].originalValue)
    }
    return maxSum
}

func createComponentsSortedByDifference(input []int) []Component {
    components := make([]Component, len(input))
    for i := range input {
        maxValue := rearrangeDigitsForMaxValue(input[i])
        components[i].maxValue = maxValue
        components[i].originalValue = input[i]
    }
    slices.SortFunc(components, func(x Component, y Component) int {
        return (y.maxValue - y.originalValue) - (x.maxValue - x.originalValue)
    })
    return components
}

func rearrangeDigitsForMaxValue(value int) int {
    frequencyDigits := make([]int, TOTAL_DIGITS)
    for value > 0 {
        digit := value % 10
        frequencyDigits[digit]++
        value /= 10
    }

    maxValue := 0
    for digit := len(frequencyDigits) - 1; digit >= 0; digit-- {
        for frequencyDigits[digit] > 0 {
            maxValue = 10 * maxValue + digit
            frequencyDigits[digit]--
        }
    }

    return maxValue
}
