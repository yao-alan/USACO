/** every 15 numbers, only 8 show up */

import java.io.*;
import java.util.*;

public class moobuzz
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("moobuzz.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moobuzz.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int m = 15 * (N / 8);
        int x = N % 8;

        int r;
        for (r = 1; x > 0; r++) {
            if (r % 3 != 0 && r % 5 != 0)
                x--;
        }
        r--;

        /** divided into 15 perfectly */
        if ((m + r) % 15 == 0)
            m--;
        out.println(m + r);

        in.close();
        out.close();
    }
}