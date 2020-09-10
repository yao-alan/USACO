/** iterate through dp a maximum of max_m * T / min_c = T^2 times, or T = 1000; maintain and update up to 
 *  N = 1000 cities for each T, which takes O(1) time for each city; total runtime = O(NT), which is on the order
 *  of 10^6
 */

import java.io.*;
import java.util.*;

public class time
{
    static class Edge
    {
        int from;
        int to;
        int money;
        public Edge(int from, int to, int money) {
            this.from = from; this.to = to; this.money = money;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Edge) {
                Edge e = (Edge)obj;
                return this.from == e.from && this.to == e.to && this.money == e.money;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return from * 1000 + to;
        }
    }
    static class Graph
    {
        LinkedList<Edge>[] adj;
        public Graph(int N) {
            adj = new LinkedList[N];
            for (int i = 0; i < N; ++i)
                adj[i] = new LinkedList<>();
        }
        public void addEdge(int v, Edge e) {
            adj[v].add(e);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("time.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("time.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] moonies = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i) {
            moonies[i] = Integer.parseInt(st.nextToken());
        }
        Graph g = new Graph(N);
        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            g.addEdge(a, new Edge(a, b, moonies[b]));
        }

        /** run a bfs through the graph, iterating up to 1000 times */
        int[][] dp = new int[1001][N];
        LinkedList<Edge> q = new LinkedList<>();
        q.add(new Edge(-1, 0, 0));
        int qSize = 1;
        for (int i = 1; i < 1001; ++i) {
            HashSet<Edge> set = new HashSet<>();
            for (int j = 0; j < qSize; ++j) {
                int curr = q.poll().to;
                for (Edge n : g.adj[curr]) {
                    int x;
                    if (dp[i][n.to] < (x = dp[i - 1][n.from] + n.money - C * (int)(Math.pow(i, 2) - Math.pow(i - 1, 2))))
                        dp[i][n.to] = x;
                    if (!set.contains(n)) {
                        set.add(n);
                        q.add(n);
                    }
                }
            }
            qSize = q.size();
        }

        int maxProfit = 0;
        for (int i = 1; i < 1001; ++i)
            maxProfit = Math.max(dp[i][0], maxProfit);
        
        pw.print(maxProfit);
        br.close();
        pw.close();
    }
}