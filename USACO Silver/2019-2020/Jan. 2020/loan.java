import java.io.*;
import java.util.*;

public class loan
{
    private static long X;
    private static void findX(long N, long K, long M, long min, long max) {
        if (min > max)
            return;
        long mid = (min + max) / 2;
        long G = 0;
        long i = K;
        while (i > 0) {
            long Y = (N - G) / mid;
            if (Y < M) {
                G += i * M;
                break;
            }
            else {
                long j = (N - (G + (mid - 1) * Y)) / Y;
                G += j * Y;
                i -= j;
            }
        }
        if (G >= N) {
            if (mid > X)
                X = mid;
            findX(N, K, M, mid + 1, max);
        }
        else
            findX(N, K, M, min, mid - 1);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("loan.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("loan.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        long N = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        long M = Long.parseLong(st.nextToken());

        X = 1;
        findX(N, K, M, 1, (long)Math.pow(10, 12));
        out.print(X);

        in.close();
        out.close();
    }
}