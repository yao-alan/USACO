/** run through all N^2 pairs of cows to find the distances between each of them */

import java.io.*;
import java.util.*;

public class moocast
{
    static class Query implements Comparable<Query>
    {
        int x1; int y1; int id1;
        int x2; int y2; int id2;
        int sqDist;
        public Query(int x1, int y1, int id1, int x2, int y2, int id2) {
            this.x1 = x1; this.y1 = y1; this.id1 = id1;
            this.x2 = x2; this.y2 = y2; this.id2 = id2;
            sqDist = (int)(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        }
        @Override
        public int compareTo(Query q) {
            return this.sqDist - q.sqDist;
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
            if (nodes[v] < 0)
                return v;
            return nodes[v] = find(nodes[v]);
        }
        public void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 == root2)
                return;
            if (-1 * root1 > -1 * root2) {
                nodes[root1] += nodes[root2];
                nodes[root2] = root1;
            } else {
                nodes[root2] += nodes[root1];
                nodes[root1] = root2;
            }
        }
        public boolean allConnected() {
            return -1 * nodes[find(0)] == nodes.length;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[][] coords = new int[N][2];
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            coords[i][0] = x; coords[i][1] = y;
        }

        Query[] queries = new Query[N * (N - 1) / 2];
        int count = 0;
        for (int i = 0; i < N - 1; ++i) {
            for (int j = i + 1; j < N; ++j) {
                queries[count] = new Query(coords[i][0], coords[i][1], i, coords[j][0], coords[j][1], j);
                ++count;
            }
        }
        Arrays.sort(queries);

        DisjointSet dsu = new DisjointSet(N);
        int minCost = 0;
        for (int i = 0; i < queries.length; ++i) {
            if (dsu.allConnected())
                break;
            dsu.union(queries[i].id1, queries[i].id2);
            minCost = queries[i].sqDist;
        }

        pw.print(minCost);
        br.close();
        pw.close();
    }
}