import java.io.*;
import java.util.*;

public class wormsort
{
    static class DisjointSet
    {
        int[] set;
        public DisjointSet(int N) {
            set = new int[N];
            Arrays.fill(set, -1);
        }
        public int find(int v) {
            while (set[v] >= 0)
                v = set[v];
            return v;
        }
        public void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 == root2)
                return;
            if (-1 * set[root1] > -1 * set[root2]) {
                set[root1] += set[root2];
                set[root2] = root1;
            } else {
                set[root2] += set[root1];
                set[root1] = root2;
            }
        }
        public boolean isConnected(int v1, int v2) {
            return find(v1) == find(v2);
        }
    }
    static class Wormhole implements Comparable<Wormhole>
    {
        int from;
        int to;
        int width;
        public Wormhole(int from, int to, int width) {
            this.from = from; this.to = to; this.width = width;
        }
        /** reverse order */
        @Override
        public int compareTo(Wormhole w) {
            return w.width - this.width;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("wormsort.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("wormsort.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] cows = new int[N];
        ArrayList<Integer> outOfOrder = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i) {
            cows[i] = Integer.parseInt(st.nextToken()) - 1;
            if (cows[i] != i)
                outOfOrder.add(cows[i]);
        }
        if (outOfOrder.size() == 0) {
            pw.print(-1);
            br.close();
            pw.close();
            return;
        }
        Wormhole[] holes = new Wormhole[M];
        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());
            holes[i] = new Wormhole(a, b, w);
        }
        Arrays.sort(holes);

        DisjointSet dsu = new DisjointSet(N);
        int minWidth = Integer.MAX_VALUE;
        int p = 0;
        for (int i = 1; i < outOfOrder.size(); ++i) {
            while (!dsu.isConnected(outOfOrder.get(0), outOfOrder.get(i))) {
                dsu.union(holes[p].from, holes[p].to);
                minWidth = holes[p].width;
                ++p;
            }
        }

        pw.print(minWidth);
        br.close();
        pw.close();
    }
}