import java.util.Scanner;

/**
 * Summary
 *
 * @author Ferat Capar -
 * @version Creation Date : 18.02.2018 11:09
 */
public class DecibinaryDP {

    private static final int MAXIMUM_VALUE = 285115;
    private static int MAX_LENGTH = 20;//(int) Math.ceil(Math.log(MAXIMUM_VALUE) / Math.log(2)) + 1;

    public static void main(String args[]) {
        long[][][] countPerDigitLengthValue = createDPArray();
        long[][] countPerLengthValue = new long[MAX_LENGTH + 1][MAXIMUM_VALUE + 1];
        long[] countPerValue = new long[MAXIMUM_VALUE + 1];
        long[] countPerMaxValue = new long[MAXIMUM_VALUE + 1];

        fillCounts(countPerDigitLengthValue, countPerLengthValue, countPerValue, countPerMaxValue);

        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) {
            long x = in.nextLong();
            System.out.println(nthNumber(x - 1, countPerMaxValue, countPerDigitLengthValue));
        }
        in.close();

    }

    private static void fillCounts(long[][][] countPerDigitLengthValue, long[][] countPerLengthValue, long[] countPerValue, long[] countPerMaxValue) {
        countPerValue[0] = 1;
        countPerLengthValue[0][0] = 1;
        countPerDigitLengthValue[0][0][0] = 1;

        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int d = 0; d < 10; d++) {
                for (int x = 0; x < countPerDigitLengthValue[i][d].length; x++) {
                    if (x - d * (1 << i) >= 0) {
                        countPerDigitLengthValue[i + 1][d][x] = countPerLengthValue[i][x - d * (1 << i)];
                        countPerLengthValue[i + 1][x] += countPerDigitLengthValue[i + 1][d][x];
                    }
                }
            }
        }

        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int x = 0; x <= MAXIMUM_VALUE; x++) {
                countPerValue[x] += countPerLengthValue[i][x] - (x < countPerDigitLengthValue[i][0].length ? countPerDigitLengthValue[i][0][x] : 0);
            }
        }

        for (int x = 0; x < MAXIMUM_VALUE; x++) {
            countPerMaxValue[x + 1] = countPerMaxValue[x] + countPerValue[x];
        }
    }

    private static long[][][] createDPArray() {
        long[][][] dp = new long[MAX_LENGTH + 1][10][];
        for (int i = 0; i <= MAX_LENGTH; i++) {
            for (int d = 0; d < 10; d++) {
                dp[i][d] = new long[Math.min(MAXIMUM_VALUE, 9 * (1 << (i + 1)))];
            }
        }
        return dp;
    }

    private static int decimalValue(long[] totalsPerMaxValue, long number) {
        int size = totalsPerMaxValue.length;
        int low = 0;
        int high = size - 1;
        int mid = (low + high) / 2;
        while (true) {
            if (totalsPerMaxValue[mid] <= number) {
                low = mid + 1;
                if (high < low) {
                    return mid < size - 1 ? mid : -1;
                }
            } else {
                high = mid - 1;
                if (high < low) {
                    return mid - 1;
                }
            }
            mid = (low + high) / 2;
        }
    }

    private static String nthNumber(long number, long[] totalDP, long[][][] dp) {
        int decimal = decimalValue(totalDP, number);
        long diff = number - totalDP[decimal];
        StringBuilder nthNumber = new StringBuilder();
        for (int i = MAX_LENGTH - 1; i >= 0; i--) {
            int digit = 0;
            long count = decimal < dp[i + 1][digit].length ? dp[i + 1][digit][decimal] : 0;
            while (count <= diff) {
                diff -= count;
                digit++;
                count = decimal < dp[i + 1][digit].length ? dp[i + 1][digit][decimal] : 0;
            }
            decimal -= digit * (1 << i);
            if ((nthNumber.length() > 0) || digit != 0) nthNumber.append(String.valueOf(digit));
        }
        if (nthNumber.length() == 0) nthNumber.append("0");
        return nthNumber.toString();
    }


}



