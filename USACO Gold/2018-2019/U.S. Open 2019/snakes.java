/** always switch to a group's size; on each update, check dp[index - 1] across all N variations
 *  and use the min wasted space; do this for all K variations on switches. Repeat this update process 
 *  across the N groups for a total of O(N^2 * K) runtime, which is on the order of 10^7
 */

import java.io.*;
import java.util.*;

public class snakes
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("snakes.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] groups = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i)
            groups[i] = Integer.parseInt(st.nextToken());
        
        /** N groups, K switches, N variations on current size of net */
        long[][][] dp = new long[N + 1][K + 2][N];
        for (int i = 1; i < N + 1; ++i)
            for (int k = 0; k < N; ++k)
                dp[i][0][k] = 400000000;

        for (int i = 1; i < N + 1; ++i) {
            for (int j = 1; j < K + 2; ++j) {
                long min = Integer.MAX_VALUE;
                for (int k = 0; k < N; ++k)
                    min = Math.min(min, dp[i - 1][j - 1][k]);
                for (int k = 0; k < N; ++k) {
                    if (groups[k] - groups[i - 1] < 0)
                        dp[i][j][k] = Integer.MAX_VALUE;
                    else
                        dp[i][j][k] = Math.min(min, dp[i - 1][j][k]) + groups[k] - groups[i - 1];
                }
            }
        }

        long minWaste = Integer.MAX_VALUE;
        for (int j = 1; j < K + 2; ++j) {
            for (int k = 0; k < N; ++k) {
                minWaste = Math.min(minWaste, dp[N][j][k]);
            }
        }
        pw.print(minWaste);

        br.close();
        pw.close();
    }
}