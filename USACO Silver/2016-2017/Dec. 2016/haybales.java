import java.io.*;
import java.util.*;

public class haybales 
{
    private static class Haybale implements Comparable<Haybale>
    {
        int id;
        int numBefore;

        private Haybale(int id) {
            this.id = id;
        }
        @Override
        public int compareTo(Haybale bale) {
            if (this.id < bale.id)
                return -1;
            else if (this.id == bale.id)
                return 0;
            else
                return 1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("haybales.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        Haybale[] bales = new Haybale[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            int baleID = Integer.parseInt(st.nextToken());
            bales[i] = new Haybale(baleID);
        }
        Arrays.sort(bales);
        TreeMap<Integer, Haybale> set = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            bales[i].numBefore = i;
            set.put(bales[i].id, bales[i]);
        }
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int numBeforeBaleOne = -1;
            int numBeforeBaleTwo = -1;
            if (set.containsKey(a))
                numBeforeBaleOne = set.get(a).numBefore;
            else {
                if (set.higherKey(a) == null)
                    ;
                else
                    numBeforeBaleOne = set.get(set.higherKey(a)).numBefore;
            }
            if (set.containsKey(b))
                numBeforeBaleTwo = set.get(b).numBefore;
            else {
                if (set.lowerKey(b) == null)
                    ;
                else
                    numBeforeBaleTwo = set.get(set.lowerKey(b)).numBefore;
            }
            if (numBeforeBaleOne == -1 || numBeforeBaleTwo == -1)
                out.println(0);
            else
                out.println(numBeforeBaleTwo - numBeforeBaleOne + 1);
        }

        in.close();
        out.close();
    }
}