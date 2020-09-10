/** greedy approach; two pointers */

import java.io.*;
import java.util.*;

public class highcard
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("highcard.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("highcard.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int[] elsieCards = new int[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            elsieCards[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(elsieCards);
        int[] bessieCards = new int[N];
        int p = 0;
        int i, j;
        for (i = 1, j = 0; i <= 2 * N && p < N; i++) {
            if (i < elsieCards[p]) {
                bessieCards[j] = i;
                j++;
            }
            else {
                p++;
            }
        }
        if (j < N) {
            bessieCards[j] = elsieCards[p - 1] + 1;
            j++;
        }
        while (j < N) {
            bessieCards[j] = bessieCards[j - 1] + 1;
            j++;
        }
        int p1 = 0; /** pointer on Bessie's cards */
        int p2 = 0; /** pointer on Elsie's cards */
        int maxWins = 0;

        while (p2 < N) {
            while (p1 < N && bessieCards[p1] < elsieCards[p2]) {
                p1++;
            }
            if (p1 >= N)
                break;
            else if (bessieCards[p1] > elsieCards[p2])
                maxWins++;
            p1++;
            p2++;
        }

        out.println(maxWins);

        in.close();
        out.close();
    }
}