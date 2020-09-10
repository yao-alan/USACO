/** maintain a list of cows sorted by arrival time and seniority; since arrival time takes precedence 
 *  over seniority, let the comparator be a decimal of (arrival_time).(100 - seniority) (or something
 *  like that)
 */

import java.io.*;
import java.util.*;

public class convention2
{
    private static class Cow implements Comparable<Cow>
    {
        int seniority;
        int arrivalTime;
        int eatingTime;
        private Cow(int seniority, int arrivalTime, int eatingTime) {
            this.seniority = seniority;
            this.arrivalTime = arrivalTime;
            this.eatingTime = eatingTime;
        }
        @Override
        public int compareTo(Cow c) {
            if (this.seniority < c.seniority)
                return -1;
            else if (this.seniority == c.seniority)
                return 0;
            else
                return 1;
        }
    }
    public static class TimeComparator implements Comparator<Cow>
    {
        @Override
        public int compare(Cow one, Cow two) {
            return (one.arrivalTime - two.arrivalTime);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("convention2.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        Cow[] cows = new Cow[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            int arrivalTime = Integer.parseInt(st.nextToken());
            int eatingTime = Integer.parseInt(st.nextToken());
            cows[i] = new Cow(i, arrivalTime, eatingTime);
        }
        Arrays.sort(cows, new TimeComparator());

        PriorityQueue<Cow> inWait = new PriorityQueue<>();
        inWait.add(cows[0]);
        int maxWait = 0;
        int currTime = inWait.peek().arrivalTime;
        int p = 1;
        while (p < cows.length) {
            if (inWait.size() == 0) {
                inWait.add(cows[p]);
                currTime = inWait.peek().arrivalTime;
                p++;
            }
            int currWait = currTime - inWait.peek().arrivalTime;
            if (currWait > maxWait)
                maxWait = currWait;
            currTime += inWait.poll().eatingTime;
            while (p < cows.length && currTime > cows[p].arrivalTime) {
                inWait.add(cows[p]);
                p++;
            }
        }
        int currWait = currTime - inWait.peek().arrivalTime;
        if (currWait > maxWait)
                maxWait = currWait;

        out.print(maxWait);

        in.close();
        out.close();
    }
}