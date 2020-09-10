import java.io.*;
import java.util.*;

public class closing
{
    static class DisjointSet
    {
        int[] elements;
        public DisjointSet(int N) {
            elements = new int[N];
            Arrays.fill(elements, -1);
        }
        public int find(int v) {
            while (elements[v] >= 0)
                v = elements[v];
            return v;
        }
        public void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (-1 * elements[root1] > -1 * elements[root2]) {
                elements[root1] += elements[root2];
                elements[root2] = root1;
            } else {
                elements[root2] += elements[root1];
                elements[root1] = root2;
            }
        }
        public boolean isConnected(int v1, int v2) {
            if (find(v1) == find(v2))
                return true;
            return false;
        }
        public boolean isFullyConnected(int v, int open) {
            if (-1 * elements[find(v)] == open)
                return true;
            return false;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("closing.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        LinkedList<Integer>[] paths = new LinkedList[N];
        for (int i = 0; i < N; ++i)
            paths[i] = new LinkedList<>();
        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            paths[a].add(b);
            paths[b].add(a);
        }
        int[] close = new int[N];
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            close[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        String[] connected = new String[N];
        connected[N - 1] = "YES";
        boolean[] active = new boolean[N];
        active[close[N - 1]] = true;
        DisjointSet dsu = new DisjointSet(N);
        for (int i = N - 2; i >= 0; --i) {
            int curr = close[i];
            active[curr] = true;
            int j = 0;
            while (j < paths[curr].size()) {
                if (dsu.isConnected(curr, paths[curr].get(j))) {
                    paths[curr].remove(j);
                    continue;
                } else if (active[paths[curr].get(j)]) {
                    dsu.union(curr, paths[curr].get(j));
                    paths[curr].remove(j);
                    continue;
                } else {
                    ++j;
                }
            }
            if (dsu.isFullyConnected(curr, N - i))
                connected[i] = "YES";
            else
                connected[i] = "NO";
        }

        for (int i = 0; i < N; ++i)
            pw.println(connected[i]);
        br.close();
        pw.close();
    }
}