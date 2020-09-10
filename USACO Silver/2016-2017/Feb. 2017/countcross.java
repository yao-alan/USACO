/** build the N x N graph and then remove R edges; run a bfs/dfs from every cow to see how many cows
 *  a given cow can reach
 */

import java.io.*;
import java.util.*;

public class countcross
{
    private static int numPairs;
    private static boolean[] visited;
    static class Graph
    {
        ArrayList<Integer>[] adj;
        boolean[] cows;

        public Graph(int N, int K) {
            adj = new ArrayList[N * N];
            for (int i = 0; i < N * N; i++)
                adj[i] = new ArrayList<>();
            createDefault(N);
            cows = new boolean[N * N];
        }
        private void createDefault(int N) {
            /** top edge */
            for (int i = 1; i < N - 1; i++) {
                adj[i].add(i - 1);
                adj[i].add(i + 1);
                adj[i].add(i + N);
            }
            /** bottom edge */
            for (int i = (N - 1) * N + 1; i < N * N - 1; i++) {
                adj[i].add(i - 1);
                adj[i].add(i + 1);
                adj[i].add(i - N);
            }
            /** left edge */
            for (int i = N; i <= (N - 2) * N; i += N) {
                adj[i].add(i - N);
                adj[i].add(i + 1);
                adj[i].add(i + N);
            }
            /** right edge */
            for (int i = 2 * N - 1; i <= (N - 1) * N - 1; i += N) {
                adj[i].add(i - N);
                adj[i].add(i - 1);
                adj[i].add(i + N);
            }
            /** top-left corner */
            adj[0].add(1);
            adj[0].add(N);
            /** top-right corner */
            adj[N - 1].add(N - 2);
            adj[N - 1].add(2 * N - 1);
            /** bottom-left corner */
            adj[(N - 1) * N].add((N - 2) * N);
            adj[(N - 1) * N].add((N - 1) * N + 1);
            /** bottom-right corner */
            adj[N * N - 1].add((N - 1) * N - 1);
            adj[N * N - 1].add(N * N - 2);
            /** everything else */
            for (int i = 1; i < N - 1; i++) {
                for (int j = 1; j < N - 1; j++) {
                    adj[N * i + j].add((i - 1) * N + j);
                    adj[N * i + j].add((i + 1) * N + j);
                    adj[N * i + j].add(N * i + j - 1);
                    adj[N * i + j].add(N * i + j + 1);
                }
            }
        }
    }
    private static void dfs(Graph g, int N, int coord) {
        if (visited[coord])
            return;
        else {
            visited[coord] = true;
            if (g.cows[coord] == true)
                numPairs++;
            for (int i = 0; i < g.adj[coord].size(); i++) {
                dfs(g, N, g.adj[coord].get(i));
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("countcross.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("countcross.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        Graph g = new Graph(N, K);
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;
            for (int j = 0; j < g.adj[N * a + b].size(); j++) {
                if (g.adj[N * a + b].get(j) == N * c + d) {
                    g.adj[N * a + b].remove(j);
                    break;
                }
            }
            for (int j = 0; j < g.adj[N * c + d].size(); j++) {
                if (g.adj[N * c + d].get(j) == N * a + b) {
                    g.adj[N * c + d].remove(j);
                    break;
                }
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            g.cows[N * a + b] = true;
        }
        /** run from every cow */
        numPairs = 0;
        visited = new boolean[N * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (g.cows[N * i + j] == true) {
                    dfs(g, N, N * i + j);
                    numPairs--;
                }
                /** overcounts since a cow will pair itself with itself */
                visited = new boolean[N * N];
            }
        }
        
        out.println((K * (K - 1) - numPairs) / 2);

        in.close();
        out.close();
    }
}