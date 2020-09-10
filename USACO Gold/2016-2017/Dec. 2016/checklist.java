import java.io.*;
import java.util.*;

public class checklist
{
    static class Pair
    {
        int x, y;
        public Pair() { ; }
        public Pair(int x, int y) {
            this.x = x; this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("checklist.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());
        /** holsteins begin at 0, guernseys at 1001 */
        Pair[] cows = new Pair[2002];
        for (int i = 0; i < H; ++i) {
            st = new StringTokenizer(br.readLine());
            cows[i] = new Pair();
            cows[i].x = Integer.parseInt(st.nextToken());
            cows[i].y = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < G; ++i) {
            st = new StringTokenizer(br.readLine());
            cows[1001 + i] = new Pair();
            cows[1001 + i].x = Integer.parseInt(st.nextToken());
            cows[1001 + i].y = Integer.parseInt(st.nextToken());
        }
        Integer[][] dp = new Integer[H + G][2002];
        Pair[][] next = new Pair[H + G][2002];
        dp[0][0] = 0;
        next[0][0] = new Pair(1, 1001);
        for (int i = 1; i < dp.length; ++i) {
            /** id of current cow */
            for (int j = 0; j < next[0].length; ++j) {
                if (next[i - 1][j] != null) {
                    /** id of the next holstein */
                    int nextHol = next[i - 1][j].x;
                    /** id of the next guernsey */
                    int nextGur = next[i - 1][j].y;
                    /** distance btwn current cow and next holstein */
                    int deltaH = (int)(Math.pow(cows[nextHol].x - cows[j].x, 2) + Math.pow(cows[nextHol].y - cows[j].y, 2));
                    if (dp[i][nextHol] == null || dp[i - 1][j] + deltaH < dp[i][nextHol]) {
                        dp[i][nextHol] = dp[i - 1][j] + deltaH;
                        next[i][nextHol] = new Pair((next[i - 1][j].x + 1) % H, next[i - 1][j].y);
                    }
                    /** distance btwn current cow and next guernsey */
                    int deltaG = (int)(Math.pow(cows[nextGur].x - cows[j].x, 2) + Math.pow(cows[nextGur].y - cows[j].y, 2));
                    if (dp[i][nextGur] == null || dp[i - 1][j] + deltaG < dp[i][nextGur]) {
                        dp[i][nextGur] = dp[i - 1][j] + deltaG;
                        next[i][nextGur] = new Pair(next[i - 1][j].x, (next[i - 1][j].y + 1) % (1001 + G));
                    }
                }
            }
        }
        pw.println(dp[H + G - 1][H - 1]);

        br.close();
        pw.close();
    }
}