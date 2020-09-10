import java.io.*;
import java.util.*;

public class reststops
{
    private static class Stop implements Comparable<Stop>
    {
        long position;
        long tastiness;
        private Stop(long position, long tastiness) {
            this.position = position;
            this.tastiness = tastiness;
        }
        @Override
        public int compareTo(Stop s) {
            if (this.tastiness < s.tastiness)
                return -1;
            else if (this.tastiness == s.tastiness)
                return 0;
            else
                return 1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("reststops.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("reststops.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int L = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int R_F = Integer.parseInt(st.nextToken());
        int R_B = Integer.parseInt(st.nextToken());
        Stop[] stops = new Stop[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            int position = Integer.parseInt(st.nextToken());
            int tastiness = Integer.parseInt(st.nextToken());
            stops[i] = new Stop(position, tastiness);
        }
        Arrays.sort(stops, Collections.reverseOrder());

        long pos = 0;
        int p = 0;
        long maxTastiness = 0;
        while (p < N && pos < L) {
            if (stops[p].position > pos) {
                maxTastiness += stops[p].tastiness * (stops[p].position - pos) * (R_F - R_B);
                pos = stops[p].position;
            }
            p++;
        }

        out.print(maxTastiness);

        in.close();
        out.close();
    }
}