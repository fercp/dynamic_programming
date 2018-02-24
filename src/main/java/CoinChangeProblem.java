import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Coin Change Problem DP Solution
 *
 * @author Ferat Capar
 * @version Creation Date : 24.02.2018 13:20
 */
public class CoinChangeProblem {


    private static final Map<String, Long> prevResults = new HashMap<>();


    private static long getWays(int index, long n, long[] c) {
        long count = 0L;

        for (int i = index; i < c.length; i++) {
            if (c[i] < n) {
                long sumI;
                if (prevResults.containsKey((n - c[i]) + "-" + i)) {
                    sumI = prevResults.get((n - c[i]) + "-" + i);
                } else {
                    sumI = getWays(i, n - c[i], c);
                    prevResults.put((n - c[i]) + "-" + i, sumI);
                }
                if (sumI > 0) {
                    count += sumI;
                }
            }

            if (c[i] == n) {
                count++;
            }
        }

        return count;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        long[] c = new long[m];
        for (int c_i = 0; c_i < m; c_i++) {
            c[c_i] = in.nextLong();
        }
        // Print the number of ways of making change for 'n' units using coins having the values given by 'c'
        long ways = getWays(0, n, c);
        System.out.println(ways);
    }
}


