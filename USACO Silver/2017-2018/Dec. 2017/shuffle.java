/** simulate cows moving between positions; store their states in a HashSet; once a state is reached
 *  twice, check to see how many open positions there are 
 */

import java.io.*;
import java.util.*;

public class shuffle
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("shuffle.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] moves = new int[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            moves[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        char[] state = new char[N];
        for (int i = 0; i < N; i++) {
            state[i] = 'X';
        }
        HashSet<String> usedStates = new HashSet<>();
        String str = String.valueOf(state);
        while (!usedStates.contains(str)) {
            usedStates.add(str);
            char[] tmp = new char[N];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = '0';
            }
            for (int i = 0; i < moves.length; i++) {
                if (state[i] == 'X') {
                    tmp[moves[i]] = 'X';
                }
            }
            state = tmp;
            str = String.valueOf(state);
        }

        boolean[] notInvariants = new boolean[N];
        for (String s : usedStates) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0' && notInvariants[i] == false)
                    notInvariants[i] = true;
            }
        }
        int invariantPos = 0;
        for (int i = 0; i < notInvariants.length; i++) {
            if (notInvariants[i] == false)
                invariantPos++;
        }

        out.println(invariantPos);

        in.close();
        out.close();
    }
}
