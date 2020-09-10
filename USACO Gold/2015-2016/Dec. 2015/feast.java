/** dynamic programming with tabulation? */

import java.io.*;
import java.util.*;

public class feast
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("feast.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int T = Integer.parseInt(st.nextToken());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        boolean[] dp = new boolean[T + 1];
        boolean[] halved = new boolean[T + 1];
        PriorityQueue<Integer> active = new PriorityQueue<>();
        dp[0] = true;
        active.add(0);
        int largest = 0;
        while (active.size() != 0) {
            int x = active.poll();
            if (x > largest)
                largest = x;
            if ((x + A) <= T && !dp[x + A]) {
                if (halved[x])
                    halved[x + A] = true;
                dp[x + A] = true;
                active.add(x + A);
            }
            if ((x + B) <= T && !dp[x + B]) {
                if (halved[x])
                    halved[x + B] = true;
                dp[x + B] = true;
                active.add(x + B);
            }
            int y;
            if ((x + A) <= T && (y = (int)((x + A) / 2)) <= T && !halved[x] && !dp[y]) {
                dp[y] = true;
                halved[y] = true;
                active.add(y);
            }
            if ((x + B) <= T && (y = (int)((x + B) / 2)) <= T && !halved[x] && !dp[y]) {
                dp[y] = true;
                halved[y] = true;
                active.add(y);
            }
        }

        out.print(largest);

        in.close();
        out.close();
    }
}