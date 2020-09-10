import java.io.*;
import java.util.*;

public class citystate
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("citystate.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("citystate.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int numSpecial = 0;
        /** String is cityState, Integer is number of times a cityState appears */
        HashMap<String, Integer> locations = new HashMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            String s = st.nextToken().substring(0, 2) + st.nextToken();
            /** don't add; only items that could be a special pair with this would come from same state */
            if (s.substring(0, 2).equals(s.substring(2, 4)))
                continue;
            if (!locations.containsKey(s))
                locations.put(s, 0);
            locations.replace(s, locations.get(s) + 1);
            String sReverse = s.substring(2, 4) + s.substring(0, 2);
            if (!locations.containsKey(sReverse))
                locations.put(sReverse, 0);
            numSpecial += locations.get(sReverse.substring(0, 4));
        }

        out.println(numSpecial);

        in.close();
        out.close();
    }
}