/** run dijkstra's M times, each time limiting the max pipe flow to >= some M_i (min to max);
 *  sorting the pipe flows from min to max will take M log M time, and running dijkstra's M times
 *  should take M^2 log M time, which is under 10^9
 */

import java.io.*;
import java.util.*;

public class pump
{
    static class Graph
    {
        ArrayList<Edge>[] adj;
        public Graph(int N) {
            adj = new ArrayList[N];
            for (int i = 0; i < N; ++i)
                adj[i] = new ArrayList<>();
        }
        public void addEdge(int v, Edge e) {
            adj[v].add(e);
        }
    }
    static class Edge implements Comparable<Edge>
    {
        int dest; int cost; int flow;
        public Edge(int d, int c, int f) {
            dest = d; cost = c; flow = f;
        }
        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }
    }
    static double dijkstra(Graph g, int minFlow) {
        int N = g.adj.length;
        boolean[] visited = new boolean[N];
        Integer[] parent = new Integer[N];
        int[] costTo = new int[N];
        costTo[0] = 0;
        for (int i = 1; i < N; ++i)
            costTo[i] = 10000001;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, 0, 0));
        for (int i = 1; i < N; ++i)
            pq.add(new Edge(i, 10000001, -1));

        while (pq.peek().flow != -1) { /** queue has viable pipes */
            Edge curr = pq.poll();
            visited[curr.dest] = true;
            for (Edge n : g.adj[curr.dest]) {
                if (!visited[n.dest] && n.flow >= minFlow) {
                    if (n.cost + costTo[curr.dest] < costTo[n.dest]) {
                        parent[n.dest] = curr.dest;
                        costTo[n.dest] = n.cost + costTo[curr.dest];
                        pq.add(new Edge(n.dest, costTo[n.dest], n.flow));
                    }
                }
            }
        }
        
        return (double)minFlow / costTo[N - 1];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("pump.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pump.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] flows = new int[M];
        Graph g = new Graph(N);
        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            g.addEdge(a, new Edge(b, c, f));
            g.addEdge(b, new Edge(a, c, f));
            flows[i] = f;
        }
        Arrays.sort(flows);

        double maxRatio = 0;
        for (int i = 0; i < M; ++i) {
            double x;
            if ((x = dijkstra(g, flows[i])) > maxRatio)
                maxRatio = x;
        }

        long ans = (long)(maxRatio * Math.pow(10, 6));
        pw.print(ans);
        br.close();
        pw.close();
    }
}