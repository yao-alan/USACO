/** 1. create an array [1...N] where every index i holds the sum from 1 to i mod 7 
 *  2. every list can be created by subtracting a smaller prefix from a larger prefix
 *  3. store the first occurrences of 1 to 6 in a different array
 *  4. (i - j) mod 7 = i mod 7 - j mod 7
*/

import java.io.*;
import java.util.*;

public class div7
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("div7.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("div7.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());

        long sum = 0;
        int[] prefixes = new int[N];
        int[] firstOccur = new int[7];
        Arrays.fill(firstOccur, -1);
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            sum += Integer.parseInt(st.nextToken()) % 7;
            prefixes[i] = (int)(sum % 7);
            if (firstOccur[prefixes[i] % 7] == -1)
                firstOccur[prefixes[i] % 7] = i;
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            /** subsequence already sums to 7 */
            if (prefixes[i] == 0) {
                if (i > max)
                    max = i;
            }
            else {
                int p = i - firstOccur[prefixes[i]];
                if (p > max)
                    max = p;
            }
        }

        out.println(max);

        in.close();
        out.close();
    }
}