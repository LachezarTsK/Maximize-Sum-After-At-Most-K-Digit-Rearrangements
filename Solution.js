
/**
 * @param {number[]} input
 * @param {number} maxTotalOperations
 * @return {number}
 */
var maximizeSum = function (input, maxTotalOperations) {
    const components = createComponentsSortedByDifference(input);
    return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations);
};

/**
 * @param {Component[]} components
 * @param {number} maxTotalOperations
 * @return {number}
 */
function calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations) {
    let maxSum = 0;
    for (let i = 0; i < maxTotalOperations; ++i) {
        maxSum += components[i].maxValue;
    }
    for (let i = maxTotalOperations; i < components.length; ++i) {
        maxSum += components[i].originalValue;
    }
    return maxSum;
}

/**
 * @param {number[]} input
 * @return {Component[]}
 */
function   createComponentsSortedByDifference(input) {
    const components = new Array(input.length);
    for (let i = 0; i < input.length; ++i) {
        const maxValue = rearrangeDigitsForMaxValue(input[i]);
        components[i] = new Component(maxValue, input[i]);
    }
    components.sort((x, y) => (y.maxValue - y.originalValue) - (x.maxValue - x.originalValue));
    return components;
}

/**
 * @param {number} value
 * @return {number}
 */
function  rearrangeDigitsForMaxValue(value) {
    const frequencyDigits = new Array(Util.TOTAL_DIGITS).fill(0);
    while (value > 0) {
        const digit = value % 10;
        ++frequencyDigits[digit];
        value = Math.floor(value / 10);
    }

    let maxValue = 0;
    for (let digit = frequencyDigits.length - 1; digit >= 0; --digit) {
        while (frequencyDigits[digit] > 0) {
            maxValue = 10 * maxValue + digit;
            --frequencyDigits[digit];
        }
    }

    return maxValue;
}

class Util {
    static TOTAL_DIGITS = 10;
}

class  Component {

    /**
     * @param {number} maxValue
     * @param {number} originalValue
     */
    constructor(maxValue, originalValue) {
        this.maxValue = maxValue;
        this.originalValue = originalValue;
    }
}
