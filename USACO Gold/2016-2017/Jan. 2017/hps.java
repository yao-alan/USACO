import java.io.*;
import java.util.*;

public class hps
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("hps.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] moves = new int[N + 1];
        /** since subsequent plays rely on what Bessie plays first, and Bessie has 3 choices of
         *  what to play first (H, P, S), dp is a 3D array with 3 other states */
        int[][][] dp = new int[N + 1][K + 1][3];
        for (int i = 1; i <= N; ++i) {
            st = new StringTokenizer(br.readLine());
            char c = st.nextToken().charAt(0);
            if (c == 'H')
                moves[i] = 1;
            else if (c == 'P')
                moves[i] = 2;
            else if (c == 'S')
                moves[i] = 0;
        }

        /** bessie's current gesture if she started with H (0), P (1), or S (2) */
        for (int i = 1; i <= N; ++i) {
            for (int j = K; j >= 0; --j) {
                for (int k = 0; k < 3; ++k) { /** 0 = H, 1 = P, 2 = S */
                    if (j == K) { /** haven't switched gestures */
                        if (k == moves[i]) /** bessie beats john */
                            dp[i][j][k] = dp[i - 1][j][k] + 1;
                        else /** bessie loses to john */
                            dp[i][j][k] = dp[i - 1][j][k];
                    } else {
                        if (k == moves[i]) { /** current gesture beats john */
                            int x = Math.max(dp[i - 1][j][k] + 1, dp[i - 1][j + 1][(k + 1) % 3] + 1);
                            x = Math.max(x, dp[i - 1][j + 1][(k + 2) % 3] + 1);
                            dp[i][j][k] = x;
                        } else { /** current gesture loses to john */
                            int x = Math.max(dp[i - 1][j][k], dp[i - 1][j + 1][(k + 1) % 3]);
                            x = Math.max(x, dp[i - 1][j + 1][(k + 2) % 3]);
                            dp[i][j][k] = x;
                        }
                    }
                }
            }
        }

        int max = 0;
        for (int i = 0; i < K + 1; ++i) {
            if (dp[N][i][0] > max)
                max = dp[N][i][0];
            if (dp[N][i][1] > max)
                max = dp[N][i][1];
            if (dp[N][i][2] > max)
                max = dp[N][i][2];
        }
        pw.println(max);

        br.close();
        pw.close();
    }
}