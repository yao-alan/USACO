/** Run a DFS/BFS from every cow; connect cows with weighted edges to represent distance 
 *  If an edge's weight is greater than the cow's power, prune that pathway
 *  Maintain a number to track the maximum number of cows a single broadcast can reach
*/

import java.io.*;
import java.util.*;

public class moocast 
{
    public static class Graph
    {
        ArrayList<Edge>[] adj;
        int[] power;
        int[][] coord;
        int numVertices;
        private class Edge 
        {
            int dst;
            double distance;
            private Edge(int dst, double distance) {
                this.dst = dst;
                this.distance = distance;
            }
        }
        public Graph(int N) {
            adj = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                adj[i] = new ArrayList<>();
            }
            power = new int[N];
            coord = new int[N][2]; /** [i][0] = x, [i][1] = y */
            numVertices = 0;
        }
        /** vertex, x-coord, y-coord, power */
        public void addVertex(int v, int x, int y, int pow) {
            coord[v][0] = x;
            coord[v][1] = y;
            for (int i = 0; i < numVertices; i++) {
                double dist = Math.sqrt(Math.pow(x - coord[i][0], 2) + Math.pow(y - coord[i][1], 2));
                /** bidirectional */
                adj[i].add(new Edge(v, dist));
                adj[v].add(new Edge(i, dist));
            }
            power[v] = pow;
            numVertices++;
        }
    }
    private static int bfs(Graph g, int v, int N, int maxReachable) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N];
        queue.add(v);
        while (queue.size() != 0) {
            int x = queue.pollFirst();
            if (!visited[x]) {
                visited[x] = true;
                maxReachable++;
                for (int i = 0; i < g.adj[x].size(); i++) {
                    if (g.adj[x].get(i).distance <= g.power[x])
                        queue.add(g.adj[x].get(i).dst);
                }
            }
        }
        return maxReachable;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("moocast.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        Graph g = new Graph(N);
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int pow = Integer.parseInt(st.nextToken());
            g.addVertex(i, x, y, pow);
        }

        int maxReachable = 0;
        for (int i = 0; i < N; i++) {
            int x = bfs(g, i, N, 0);
            if (x > maxReachable)
                maxReachable = x;
        }

        out.println(maxReachable);

        in.close();
        out.close();
    }
}