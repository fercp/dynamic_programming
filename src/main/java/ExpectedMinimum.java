/**
 *
 * @author Ferat Capar
 */
public class ExpectedMinimum {

    public double findExp(int n, int x) {
        long[][] dp = new long[n + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i][1] = 1;
            dp[i][0] = 0;
        }

        for (int j = 2; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                for (int k = i + 1; k <= n; k++) {
                    dp[i][j] += dp[k][j - 1];
                }
            }
        }
        double sum = 0;
        long count = 0;
        for (int i = 1; i <= n; i++) {
            if (dp[i][x] != 0) {
                sum += Math.pow(2, i) * dp[i][x];
                count += dp[i][x];
            }
        }
        return sum / count;
    }
}

