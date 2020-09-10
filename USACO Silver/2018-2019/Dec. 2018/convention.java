import java.io.*;
import java.util.*;

public class convention
{
    private static int minTime;
    private static void findMinTime(int[] cows, int M, int C, int min, int max) {
        if (min > max)
            return;
        int p = 0;
        int mid = (min + max) / 2;
        /** outer loop controls getting a new bus */
        for (int i = 0; i < M; i++) {
            try {
                int cowsOnBus = 0;
                int startTime = cows[p];
                while (cowsOnBus < C && p < cows.length && cows[p] - startTime <= mid) {
                    cowsOnBus++;
                    p++;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        if (p == cows.length) {
            if (mid < minTime)
                minTime = mid;
            findMinTime(cows, M, C, min, mid - 1);
        }
        else if (p < cows.length)
            findMinTime(cows, M, C, mid + 1, max);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("convention.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("convention.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken()); /** number of cows */
        int M = Integer.parseInt(st.nextToken()); /** number of buses */
        int C = Integer.parseInt(st.nextToken()); /** number of cows/bus */
        int[] cows = new int[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            cows[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(cows);

        minTime = cows[N - 1] - cows[0];
        findMinTime(cows, M, C, 0, minTime);

        out.print(minTime);

        in.close();
        out.close();
    }
}