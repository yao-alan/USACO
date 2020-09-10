/** 1. Take O(NM) time to get the configuration at the end of the M-step process. (NM = 10^7)
 *  2. Trace one element through until it comes back to its original position, storing positions it 
 *     visits in a hashset while keeping track of the exact path with an arraylist.
 *  3. Take mod(path_length) for every other element that exists in hashset; then find its final position
 *     in the arraylist.
 *  4. Repeat 2 and 3 until every element has been visited. O(N) time.
 *  5. Final runtime is basically O(NM)? This would run in time
 */

import java.io.*;
import java.util.*;

public class swap
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("swap.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("swap.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] moves = new int[M][2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            moves[i][0] = Integer.parseInt(st.nextToken()) - 1;
            moves[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }
        ArrayList<Integer> cows = new ArrayList<>();
        for (int i = 0; i < N; i++)
            cows.add(i + 1);
        /** Step 1 */
        for (int i = 0; i < M; i++)
            Collections.reverse(cows.subList(moves[i][0], moves[i][1] + 1));
        
        /** Step 2 */
        HashMap<Integer, ArrayList<Integer>> pathMap = new HashMap<>();
        int[] indices = new int[cows.size()];
        for (int i = 0; i < cows.size(); i++) {
            ArrayList<Integer> path = new ArrayList<>();
            int j = i;
            int count = 0;
            while (!pathMap.containsKey(j) && cows.get(j) != i) {
                path.add(j);
                pathMap.put(j, path);
                indices[j] = count;
                j = cows.get(j) - 1;
                count++;
            }
        }

        /** Step 3 */
        for (int i = 0; i < cows.size(); i++) {
            int remain = K % pathMap.get(i).size();
            int num = pathMap.get(i).get((remain + indices[i]) % pathMap.get(i).size());
            out.println(num + 1);
        }

        in.close();
        out.close();
    }
}