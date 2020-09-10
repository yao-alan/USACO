/** N < 1000 suggests N^2 algorithm 
 *  Every spot should only have one cow after N passes
*/

import java.io.*;
import java.util.*;

public class CBarn
{
    private static class Cow
    {
        int d;
        private Cow(int d) {
            this.d = d;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("cbarn.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        ArrayList<Cow>[] positions = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            positions[i] = new ArrayList<>();
            st = new StringTokenizer(in.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++) {
                positions[i].add(new Cow(0));
            }
        }

        /** This is O(N^2) even though at first glance it seems like O(N^3) */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (positions[j].size() <= 1)
                    ;
                else {
                    Cow smallest = positions[j].get(0);
                    for (int k = 1; k < positions[j].size(); k++) {
                        if (smallest.d > positions[j].get(k).d)
                            smallest = positions[j].get(k);
                    }
                    positions[j].remove(smallest);
                    smallest.d++;
                    positions[(j + 1) % N].add(smallest);
                }
            }
        }

        int minEnergy = 0;
        for (int i = 0; i < N; i++) {
            minEnergy += Math.pow(positions[i].get(0).d, 2);
        }
        out.println(minEnergy);

        in.close();
        out.close();
    }
}