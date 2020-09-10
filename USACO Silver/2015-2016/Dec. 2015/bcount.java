/** prefix sums */

import java.io.*;
import java.util.*;

public class bcount 
{
    private static class Count
    {
        int h;
        int g;
        int j;
        Count() {
            h = 0;
            g = 0;
            j = 0;
        }
        Count (int h, int g, int j) {
            this.h = h;
            this.g = g;
            this.j = j;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("bcount.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bcount.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        
        Count[] cows = new Count[N + 1];
        for (int i = 0; i < N + 1; i++) {
            cows[i] = new Count();
        }
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(in.readLine());
            int type = Integer.parseInt(st.nextToken());
            switch (type) {
                case 1:
                    cows[i].h = cows[i - 1].h + 1;
                    cows[i].g = cows[i - 1].g;
                    cows[i].j = cows[i - 1].j;
                    break;
                case 2:
                    cows[i].h = cows[i - 1].h;
                    cows[i].g = cows[i - 1].g + 1;
                    cows[i].j = cows[i - 1].j;
                    break;
                case 3:
                    cows[i].h = cows[i - 1].h;
                    cows[i].g = cows[i - 1].g;
                    cows[i].j = cows[i - 1].j + 1;
                    break;
            }
        }
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(in.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int holsteins = cows[end].h - cows[start - 1].h;
            int guernseys = cows[end].g - cows[start - 1].g;
            int jerseys = cows[end].j - cows[start - 1].j;
            out.println(holsteins + " " + guernseys + " " + jerseys);
        }

        in.close();
        out.close();
    }
}