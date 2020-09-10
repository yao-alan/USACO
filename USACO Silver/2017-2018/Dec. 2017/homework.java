import java.io.*;
import java.util.*;

public class homework
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("homework.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("homework.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        double[] hw = new double[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            hw[i] = Double.parseDouble(st.nextToken());
        }

        double maxScore = 0;
        LinkedList<Integer> maxEaten = new LinkedList<>();
        double prefix = hw[N - 1];
        double smallest = hw[N - 1];
        for (int i = N - 2, j = N - 2; i >= 1; i--, j--) {
            if (smallest > hw[i])
                smallest = hw[i];
            prefix += hw[i];
            if ((prefix - smallest) / (N - j - 1) > maxScore) {
                maxScore = (prefix - smallest) / (N - j - 1);
            }
        }
        prefix = hw[N - 1];
        smallest = hw[N - 1];
        for (int i = N - 2, j = N - 2; i >= 1; i--, j--) {
            if (smallest > hw[i])
                smallest = hw[i];
            prefix += hw[i];
            if ((prefix - smallest) / (N - j - 1) == maxScore) {
                maxScore = (prefix - smallest) / (N - j - 1);
                maxEaten.add(j);
            }
        }

        Collections.reverse(maxEaten);
        for (int x : maxEaten)
            out.println(x);

        in.close();
        out.close();
    }
}