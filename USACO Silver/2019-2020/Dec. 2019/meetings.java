/** essentially equivalent to cows phasing through each other; collisions don't really matter (in
 *  terms of timing)
 *  if there are k cows moving right and N - k moving left, the time it takes for the k moving right
 *  is simply summing their distances from the right barn; same for the N - k moving left
 *  the N - k on the left are the ones that finally move left
 *  collisions essentially occur whenever two cows phase through each other
 *  1. Find how long it takes for the two barns to contain half the total weights (time t)
 *  2. Run through the array of length N; for every cow in the array, see how many cows moving in the
 *     opposite direction can be reached in time t; divide answer by 2 due to double-counting
*/

import java.io.*;
import java.util.*;

public class meetings
{
    private static int minTime;
    private static class Cow implements Comparable<Cow>
    {
        int weight;
        int pos;
        int velocity;
        private Cow(int weight, int pos, int velocity) {
            this.weight = weight;
            this.pos = pos;
            this.velocity = velocity;
        }
        @Override
        public int compareTo(Cow c) {
            if (this.pos < c.pos)
                return -1;
            else if (this.pos == c.pos)
                return 0;
            else
                return 1;
        }
    }
    private static void findTime(int L, int totalWeight, Cow[] cows, int min, int max) {
        if (min > max)
            return;
        int mid = (min + max) / 2;
        double barnWeight = 0;
        int p1 = 0;
        int p2 = cows.length - 1;
        for (int i = 0; i < cows.length && barnWeight < totalWeight / 2.0; i++) {
            if (cows[i].velocity > 0) { /** moving right */
                if (L - cows[i].pos <= mid) {
                    barnWeight += cows[p2].weight;
                    p2--;
                }
            }
            else if (cows[i].velocity < 0) { /** moving left */
                if (cows[i].pos <= mid) {
                    barnWeight += cows[p1].weight;
                    p1++;
                }
            }
        }
        if (barnWeight >= totalWeight / 2.0) {
            if (mid < minTime)
                minTime = mid;
            findTime(L, totalWeight, cows, min, mid - 1);
        }
        else
            findTime(L, totalWeight, cows, mid + 1, max);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("meetings.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("meetings.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int totalWeight = 0;
        Cow[] cows = new Cow[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int pos = Integer.parseInt(st.nextToken());
            int velocity = Integer.parseInt(st.nextToken());
            cows[i] = new Cow(weight, pos, velocity);
            totalWeight += weight;
        }
        Arrays.sort(cows);

        /** Use binary search to find the min time needed to get 1/2 the weight */
        minTime = L;
        findTime(L, totalWeight, cows, 0, L);
        
        int meetings = 0;
        for (int i = 0; i < cows.length; i++) {
            if (cows[i].velocity > 0) {
                int p = i + 1;
                while (p < cows.length && (cows[p].pos - cows[i].pos) <= minTime * 2) {
                    if (cows[p].velocity < 0) {
                        meetings++;
                    }
                    p++;
                }
            }
        }

        out.print(meetings);

        in.close();
        out.close();
    }
}