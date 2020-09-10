/** 1. cow power is maximized when the edge of the blast just touches the hay bale; greedy approach
 * where Bessie always targets the nearest hay bale with coordinate p; blast will extend to p + 2R
 *  2. use binary search to narrow down the options for power R
 */

import java.io.*;
import java.util.*;

 public class angry
 {
     private static int min;
     private static void testPower(int K, TreeSet<Integer> bales, int minPower, int maxPower) {
        if (minPower > maxPower)
            return;
        int midPower = (minPower + maxPower) / 2;
        int current = bales.first();
        /** poor coding practice to use try-catch here but should work */
        boolean enoughPower = false;
        for (int i = 0; i < K; i++) {
            try {
                current = bales.higher(current + 2 * midPower);
            }
            catch (Exception e) { /** catches nullpointerexcpetion when there is no higher element in bales */
                enoughPower = true;
                break;
            }
        }
        if (enoughPower) {
            min = midPower;
            testPower(K, bales, minPower, midPower - 1);
        }
        else
            testPower(K, bales, midPower + 1, maxPower);
     }
     public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("angry.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        TreeSet<Integer> bales = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            bales.add(Integer.parseInt(st.nextToken()));
        }

        min = 1000000000;
        angry.testPower(K, bales, 0, 2000000000);
        out.println(min);

        in.close();
        out.close();
     }
 }