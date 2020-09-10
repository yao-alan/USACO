/** play the low card rounds first, as this will necessarily reserve the high cards for the high card rounds */

import java.io.*;
import java.util.*;

public class cardgame
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cardgame.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] elsie = new int[N];
        int[] firstHalf = new int[N / 2];
        int[] secondHalf = new int[N / 2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            elsie[i] = Integer.parseInt(st.nextToken());
            if (i < N / 2)
                firstHalf[i] = elsie[i];
            else 
                secondHalf[i - N / 2] = elsie[i];
        }
        Arrays.sort(elsie);
        Arrays.sort(firstHalf);
        Arrays.sort(secondHalf);
        LinkedList<Integer> bessie = new LinkedList<>();
        int p = 0;
        int i, j;
        for (i = 1, j = 0; i <= 2 * N && p < N; i++) {
            while (p < N && elsie[p] < i)
                p++;
            if (p < N && i != elsie[p]) {
                bessie.add(i);
            }
            if (p >= N)
                break;
        }
        while (i <= 2 * N) {
            bessie.add(i);
            i++;
        }
        Collections.sort(bessie);
        
        int wins = 0;
        for (i = 0, j = 0; i < secondHalf.length; i++) {
            if (j < bessie.size() && bessie.get(j) > secondHalf[i])
                continue;
            if (j != bessie.size() && bessie.get(j) < secondHalf[i]) {
                wins++;
                bessie.remove(j);
            }
        }
        for (i = 0, j = 0; i < firstHalf.length; i++) {
            while (j < bessie.size() && bessie.get(j) < firstHalf[i])
                j++;
            if (j != bessie.size() && bessie.get(j) > firstHalf[i]) {
                wins++;
                bessie.remove(j);
            }
        }

        pw.println(wins);

        br.close();
        pw.close();
    }
}