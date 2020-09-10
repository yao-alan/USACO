/** dijkstra's from the barn will get shortest paths to every node in E log V time, which is on 
 *  the order of 10^6; to check how much time is saved, run through the full path from all N fields; 
 *  max of N^2 time, which is on the order of 10^8
*/

import java.io.*;
import java.util.*;

public class shortcut
{
    static int[] parentTo;
    static long[] timeTo;
    static class Edge implements Comparable<Edge>
    {
        int to;
        long time;
        public Edge(int to, long time) {
            this.to = to; this.time = time;
        }
        @Override
        public int compareTo(Edge e) {
            if (this.time < e.time)
                return -1;
            else if (this.time == e.time)
                return 0;
            else
                return 1;
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
        public void addEdge(int a, int b, int t) {
            adj[a].add(new Edge(b, t));
            adj[b].add(new Edge(a, t));
        }
    }
    static void dijkstra(Graph g) {
        /** start at the barn */
        int N = g.adj.length;
        parentTo = new int[N]; parentTo[0] = -1;
        timeTo = new long[N]; timeTo[0] = 0;
        for (int i = 1; i < N; ++i)
            timeTo[i] = Integer.MAX_VALUE;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0));
        for (int i = 1; i < N; ++i)
            pq.add(new Edge(i, Integer.MAX_VALUE));

        while (pq.peek().time != Integer.MAX_VALUE) {
            Edge curr = pq.poll();
            for (Edge n : g.adj[curr.to]) {
                if (timeTo[curr.to] + n.time <= timeTo[n.to]) {
                    /** same time, larger lexicographically */
                    if (curr.to >= parentTo[n.to] && timeTo[curr.to] + n.time == timeTo[n.to])
                        continue;
                    timeTo[n.to] = timeTo[curr.to] + n.time;
                    parentTo[n.to] = curr.to;
                    pq.add(new Edge(n.to, timeTo[n.to]));
                }
            }
        }
    }
    static long maxReduction(Graph g, int[] cows, int T) {
        int N = g.adj.length;
        long[] savedTime = new long[N];
        /** N - 1 different starting points */
        for (int i = 1; i < N; ++i) {
            int p = i;
            while (p != 0) {
                if (timeTo[p] > T)
                    savedTime[p] += cows[i] * (timeTo[p] - T);
                p = parentTo[p];
            }
        }
        long max = 0;
        for (int i = 1; i < N; ++i) {
            if (savedTime[i] > max)
                max = savedTime[i];
        }
        return max;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("shortcut.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        int[] cows = new int[N]; /** cows[i] = num cows on ith field */
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i)
            cows[i] = Integer.parseInt(st.nextToken());
        Graph g = new Graph(N);
        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());
            g.addEdge(a, b, t);
        }
        
        dijkstra(g);
        pw.print(maxReduction(g, cows, T));

        br.close();
        pw.close();
    }
}