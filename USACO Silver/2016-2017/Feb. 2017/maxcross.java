/** use one pass to fill in consecutive empty spaces; start by filling in all empty spots (N - K)
 *  each pass takes O(N) time; use binary search to narrow down the number of consecutive spaces needed
 *      minimum number found using binary search will be the returned value
 *      binary search narrows down in O(log N), giving a total runtime of O(N log N), which should run in 
 *      a few seconds given the spec of N < 100,000
 */

import java.io.*;
import java.util.*;

public class maxcross
{
    private static int minNeeded;
    private static void findMin(boolean[] crosswalks, int K, int min, int max) {
        if (min > max)
            return;
        boolean[] tmp = new boolean[crosswalks.length];
        System.arraycopy(crosswalks, 0, tmp, 0, crosswalks.length);
        boolean possible = false;
        int mid = (min + max) / 2;
        int p1 = 0, p2 = 0;
        int count = 0;
        /** setting up beginning configuration */
        while (count < mid && p2 < tmp.length - 1) {
            p2++;
            if (crosswalks[p2] == false)
                count++;
        }
        int passed = 0;
        while (!possible && p2 < tmp.length) {
            if (p2 - p1 + passed >= K) {
                possible = true;
                continue;
            }
            p2++;
            while (p2 < tmp.length && crosswalks[p2] == true)
                p2++;
            passed = 0;
            p1++;
            while (p1 < tmp.length && crosswalks[p1] == true) {
                passed++;
                p1++;
            }
        }
        if (possible) {
            if (mid < minNeeded)
                minNeeded = mid;
            findMin(crosswalks, K, min, mid - 1);
        }
        else
            findMin(crosswalks, K, mid + 1, max);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("maxcross.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maxcross.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        boolean[] crosswalks = new boolean[N + 1];
        Arrays.fill(crosswalks, true);
        for (int i = 0; i < B; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            crosswalks[x - 1] = false;
        }
        crosswalks[N] = false;

        minNeeded = N - K;
        findMin(crosswalks, K, 0, N - K);

        out.println(minNeeded);

        in.close();
        out.close();
    }
}