/** Check whether N lands on the left or right half of a word; if it lands on the word length / 2 + 1,
 *  replace its position with word length / 2; take the position mod word length / 2 + 1
 */

import java.io.*;
import java.util.*;

public class cowcode
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("cowcode.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowcode.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        String code = " " + st.nextToken();
        long N = Long.parseLong(st.nextToken());

        long x = code.length() - 1;
        while (x < N)
            x *= 2;
        x /= 2;
        while (N > code.length()) {
            if (N != (x + 1))
                N %= (x + 1);
            else
                N = (N - 1) % (x + 1);
            x /= 2;
        }
        char nth = code.charAt((int)(N));
        
        out.print(nth);

        in.close();
        out.close();
    }
}