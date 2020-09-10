/** sort relevancies (K) from smallest to largest and then merge based on relevancies; to check
 *  how many videos are suggested from a given vertex, simply check how many nodes are connected to the
 *  parent of the given vertex */

import java.io.*;
import java.util.*;

public class mootube
{
    static class Edge implements Comparable<Edge>
    {
        int from;
        int to;
        int relevance;
        public Edge(int f, int t, int r) {
            from = f; to = t; relevance = r;
        }
        @Override
        public int compareTo(Edge e) {
            return e.relevance - this.relevance;
        }
    }
    static class DisjointSet
    {
        int[] nodes;
        public DisjointSet(int N) {
            nodes = new int[N];
            Arrays.fill(nodes, -1);
        }
        public int find(int v) {
            while (nodes[v] >= 0)
                v = nodes[v];
            return v;
        }
        public void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 == root2)
                return;
            if (-1 * nodes[root1] > -1 * nodes[root2]) {
                nodes[root1] += nodes[root2];
                nodes[root2] = root1;
            } else {
                nodes[root2] += nodes[root1];
                nodes[root1] = root2;
            }
        }
        public int numConnected(int v) {
            return -1 * nodes[find(v)];
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        Edge[] paths = new Edge[N - 1];
        for (int i = 0; i < N - 1; ++i) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int relevance = Integer.parseInt(st.nextToken());
            paths[i] = new Edge(from, to, relevance);
        }
        Arrays.sort(paths);
        Edge[] queries = new Edge[Q]; /** using Edge in place of triplet */
        for (int i = 0; i < Q; ++i) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken()) - 1;
            queries[i] = new Edge(i, v, K);
        }
        Arrays.sort(queries);

        DisjointSet dsu = new DisjointSet(N);
        int[] ans = new int[Q];
        for (int i = 0, j = 0; i < Q; ++i) {
            while (j < N - 1 && paths[j].relevance >= queries[i].relevance) {
                dsu.union(paths[j].from, paths[j].to);
                ++j;
            }
            /** from/to meaningless here; just used Edge in place of a triplet */
            ans[queries[i].from] = dsu.numConnected(queries[i].to) - 1;
        }

        for (int i = 0; i < Q; ++i)
            pw.println(ans[i]);
        br.close();
        pw.close();
    }
}