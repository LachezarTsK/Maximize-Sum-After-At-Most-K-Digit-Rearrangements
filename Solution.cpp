
#include <span>
#include <ranges>
#include <vector>
using namespace std;

class Solution {

    struct Component {
        int maxValue{};
        int originalValue{};
        Component(int maxValue, int originalValue): maxValue{ maxValue }, originalValue{ originalValue } {}
    };

    static const int TOTAL_DIGITS = 10;

public:
    long long maximizeSum(vector<int>& input, int maxTotalOperations) {
        vector<Component> components = createComponentsSortedByDifference(input);
        return calculateMaxSumAfterMaxTotalOperations(components, maxTotalOperations);
    }

private:
    long long calculateMaxSumAfterMaxTotalOperations(span<const Component> components, int maxTotalOperations) {
        long long maxSum = 0;
        for (int i = 0; i < maxTotalOperations; ++i) {
            maxSum += components[i].maxValue;
        }
        for (int i = maxTotalOperations; i < components.size(); ++i) {
            maxSum += components[i].originalValue;
        }
        return maxSum;
    }

    static vector<Component> createComponentsSortedByDifference(span<const int> input) {
        vector<Component> components;
        components.reserve(input.size());

        for (int i = 0; i < input.size(); ++i) {
            int maxValue = rearrangeDigitsForMaxValue(input[i]);
            components.emplace_back(maxValue, input[i]);
        }
        ranges::sort(components, [](const auto& x, const auto& y) {return (y.maxValue - y.originalValue) < (x.maxValue - x.originalValue); });
        return components;
    }

    static int rearrangeDigitsForMaxValue(int value) {
        vector<int> frequencyDigits(TOTAL_DIGITS);
        while (value > 0) {
            int digit = value % 10;
            ++frequencyDigits[digit];
            value /= 10;
        }

        int maxValue = 0;
        for (int digit = frequencyDigits.size() - 1; digit >= 0; --digit) {
            while (frequencyDigits[digit] > 0) {
                maxValue = 10 * maxValue + digit;
                --frequencyDigits[digit];
            }
        }

        return maxValue;
    }
};
