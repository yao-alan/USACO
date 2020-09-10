/** create graph of all the sessions and find a topological ordering for them
 *  create an indegree array; traverse the graph according to the topological order; 
 *  nodes with indegree 0 start at their earliest possible times, while subsequent
 *  nodes are updated as necessary; if a node has indegree 2+, pick the latest time
 */

import java.io.*;
import java.util.*;

public class timeline
{
    static class Graph
    {
        ArrayList<Edge>[] adj;
        HashMap<Integer, Integer>[] edgeMap;
        int[] indegree;
        public Graph(int N) {
            adj = new ArrayList[N];
            for (int i = 0; i < N; ++i)
                adj[i] = new ArrayList<>();
            edgeMap = new HashMap[N];
            for (int i = 0; i < N; ++i)
                edgeMap[i] = new HashMap<>();
            indegree = new int[N];
        }
        public void addEdge(int v, Edge e) {
            adj[v].add(e);
            Integer x = edgeMap[v].get(e.to);
            if (x == null || x < e.dist)
                edgeMap[v].put(e.to, e.dist);
            ++indegree[e.to];
        }
    }
    static class Edge
    {
        int to;
        int dist;
        public Edge(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }
    }
    static LinkedList<Integer> topoSort(Graph g) {
        boolean[] visited = new boolean[g.adj.length];
        LinkedList<Integer> order = new LinkedList<>();
        Stack<Integer> s = new Stack<>();
        int p = 0;
        while (p < visited.length) {
            if (!visited[p]) {
                s.push(p);
                dfs(g, s, visited, order);
            }
            ++p;
        }
        return order;
    }
    private static void dfs(Graph g, Stack<Integer> s, boolean[] visited, LinkedList<Integer> order) {
        int curr = s.peek();
        if (visited[curr])
            return;
        visited[curr] = true;
        for (Edge e : g.adj[curr]) {
            if (!visited[e.to]) {
                s.push(e.to);
                dfs(g, s, visited, order);
            }
        }
        order.addFirst(s.pop());
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("timeline.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("timeline.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int[] S = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i)
            S[i] = Integer.parseInt(st.nextToken());
        Graph g = new Graph(N);
        ArrayList<Integer>[] parents = new ArrayList[N];
        for (int i = 0; i < N; ++i)
            parents[i] = new ArrayList<>();
        for (int i = 0; i < C; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken());
            g.addEdge(a, new Edge(b, x));
            parents[b].add(a);
        }

        LinkedList<Integer> order = topoSort(g);
        int[] ans = new int[N];
        for (int i : order) {
            if (g.indegree[i] == 0)
                ans[i] = S[i];
            else {
                int max = S[i];
                for (int p : parents[i]) {
                    if (ans[p] + g.edgeMap[p].get(i) > max)
                        max = ans[p] + g.edgeMap[p].get(i);
                }
                ans[i] = max;
            }
        }

        for (int n : ans)
            pw.println(n);

        br.close();
        pw.close();
    }
}