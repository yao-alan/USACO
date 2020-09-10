/** O(NM)? Memoize combinations of subsets */

import java.io.*;
import java.util.*;

public class teamwork
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("teamwork.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] skill = new int[N];
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            skill[i] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][K];
        int[] largest = new int[N];
        for (int i = 0; i < N; ++i) {
            int max = skill[i];
            for (int j = 0; j < K; ++j) {
                try {
                    if (max < skill[i - j])
                        max = skill[i - j];
                    dp[i][j] = (j + 1) * max + largest[i - j - 1];
                    if (dp[i][j] > largest[i])
                        largest[i] = dp[i][j];
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (j <= i)
                        largest[i] = dp[i][j] = (j + 1) * max;
                    else
                        dp[i][j] = 0;
                }
            }
        }

        pw.print(largest[N - 1]);
        br.close();
        pw.close();
    }
}