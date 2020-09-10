/** sliding window + priority queue for N log N */

import java.io.*;
import java.util.*;

public class hayfeast
{
    public static class Bale implements Comparable<Bale>
    {
        int id;
        int flavor;
        int spiciness;
        public Bale(int id, int flavor, int spiciness) {
            this.id = id;
            this.flavor = flavor;
            this.spiciness = spiciness;
        }
        @Override
        public int compareTo(Bale b) {
            return b.spiciness - this.spiciness;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("hayfeast.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hayfeast.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        long M = Long.parseLong(st.nextToken()); /** min flavor needed */
        Bale[] bales = new Bale[N];
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int F = Integer.parseInt(st.nextToken());
            int S = Integer.parseInt(st.nextToken());
            bales[i] = new Bale(i, F, S);
        }

        int left = 0;
        int right = 1; /** last element at index right - 1 */
        long flavor = bales[0].flavor;
        int minSpice = Integer.MAX_VALUE;
        PriorityQueue<Bale> pq = new PriorityQueue<>();
        pq.add(bales[0]);
        while (right != N || (left != N - 1 && right == N && flavor >= M)) {
            if (flavor >= M) {
                if (minSpice > pq.peek().spiciness)
                    minSpice = pq.peek().spiciness;
            }
            if (flavor < M) {
                ++right;
                pq.add(bales[right - 1]);
                flavor += bales[right - 1].flavor;
            } else {
                flavor -= bales[left].flavor;
                ++left;
                while (pq.peek().id < left)
                    pq.poll();
            }
        }

        pw.println(minSpice);
        br.close();
        pw.close();
    }
}