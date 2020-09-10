/** use binary search to find the smallest simultaneous number of cows
 *  put the cows into a priority queue; pop the cow with the smallest amount of time and increment
 *  the total amount of time
 *  when adding in a new cow, its value should be time_elapsed + own time
 */

import java.io.*;
import java.util.*;

public class cowdance
{   
    private static int K;
    /** smallest simultaneous number of cows */ 
    private static void findSmallestK(int[] cows, int T_max, int min, int max) {
        if (min > max)
            return;
        PriorityQueue<Integer> stage = new PriorityQueue<>();
        int mid = (min + max) / 2;
        int time = 0;
        int i;
        for (i = 0; i < mid; i++) {
            stage.add(cows[i]);
        }
        while (stage.size() != 0) {
            time = stage.poll();
            if (i < cows.length) {
                stage.add(cows[i] + time);
                i++;
            }
        }
        if (time <= T_max) {
            K = mid;
            findSmallestK(cows, T_max, min, mid - 1);
        }
        else
            findSmallestK(cows, T_max, mid + 1, max);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("cowdance.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowdance.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int T_max = Integer.parseInt(st.nextToken());
        int[] cows = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            cows[i] = Integer.parseInt(st.nextToken());
        }

        K = N;
        findSmallestK(cows, T_max, 0, N);
        out.println(K);

        in.close();
        out.close();
    }
}