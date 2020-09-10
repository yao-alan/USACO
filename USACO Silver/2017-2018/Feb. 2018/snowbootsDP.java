/** bottom-up tabulation? dp[x] = min steps from (dp[x - 1], dp[x - 2], dp[x - 3]...)*/

import java.io.*;
import java.util.*;

public class snowbootsDP
{
    private static void solveDP(int[] dp, int[] path, int[][] boots, int pos, int end, int B) {
        if (pos > end)
            return;
        int minUsed = B - 1;
        for (int i = 0; i < pos; i++) {
            int j = dp[i];
            while (j < minUsed) {
                if (path[i] <= boots[j][0] && path[pos] <= boots[j][0] && pos - i <= boots[j][1]) {
                    minUsed = j;
                    dp[pos] = minUsed;
                }
                else
                    j++;
            }
            if (j == minUsed)
                dp[pos] = minUsed;
        }
        solveDP(dp, path, boots, pos + 1, end, B);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("snowboots.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int[] path = new int[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            path[i] = Integer.parseInt(st.nextToken());
        }
        int[][] boots = new int[B][2];
        for (int i = 0; i < B; i++) {
            st = new StringTokenizer(in.readLine());
            /** depth of snow that this pair of boots can step in */
            boots[i][0] = Integer.parseInt(st.nextToken());
            /** max step size for this pair of boots */
            boots[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N];
        dp[0] = 0;
        solveDP(dp, path, boots, 0, N - 1, B);

        out.print(dp[N - 1]);

        in.close();
        out.close();
    }
}