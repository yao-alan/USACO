/** intuitively, we want to pair cows that produce little milk with renting and cows that produce
 *  lots of milk with milking
 *  sort cows by min amount of milk to max amount of milk; if we have cow A and B, where A produces less
 *  milk, there is no case in which we would sell cow B and milk cow A; thus we need to find only one 
 *  spot where we draw the line between milking and renting
 *  a prefix sum lets us get the max amount of money in O(N) time
 */

import java.io.*;
import java.util.*;

public class rental
{
    static class Milkers implements Comparable<Milkers>
    {
        int gallons;
        int remainingGal;
        int price;
        private Milkers(int gallons, int price) {
            this.gallons = gallons;
            remainingGal = gallons;
            this.price = price;
        }
        public int compareTo(Milkers r) {
            if (this.price < r.price)
                return -1;
            else if (this.price == r.price)
                return 0;
            else
                return 1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("rental.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rental.out")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        int[] cows = new int[N];
        Milkers[] milkers = new Milkers[M];
        Integer[] rentals = new Integer[R];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            cows[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int gallons = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            milkers[i] = new Milkers(gallons, price);
        }
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(in.readLine());
            int price = Integer.parseInt(st.nextToken());
            rentals[i] = price;
        }
        Arrays.sort(cows);
        Arrays.sort(milkers, Collections.reverseOrder());
        Arrays.sort(rentals, Collections.reverseOrder());

        long maxProfit = 0;
        /** Create default configuration */
        int i, j;
        int p = 0; /** position at which the milk/rent split occurs */
        for (i = 0, j = 0; i < N; i++) {
            if (i < R) {
                maxProfit += rentals[i];
                p = i;
            }
            else {
                int x = cows[i];
                while (x > 0 && j < M) {
                    /** cow can supply more milk than is needed by a shop */
                    if (x > milkers[j].remainingGal) {
                        maxProfit += milkers[j].remainingGal * milkers[j].price;
                        x -= milkers[j].remainingGal;
                        milkers[j].remainingGal = 0;
                        j++;
                    }
                    else {
                        maxProfit += x * milkers[j].price;
                        milkers[j].remainingGal -= x;
                        x = 0;
                    }
                }
            }
        }
        /** moving milk/rent split backwards */
        long currProfit = maxProfit;
        for (i = p; j < M && i >= 0; i--) {
            currProfit -= rentals[i];
            int x = cows[i];
                while (x != 0 && j < M) {
                    /** cow can supply more milk than is needed by a shop */
                    if (x > milkers[j].remainingGal) {
                        currProfit += milkers[j].remainingGal * milkers[j].price;
                        x -= milkers[j].remainingGal;
                        milkers[j].remainingGal = 0;
                        j++;
                    }
                    else {
                        currProfit += x * milkers[j].price;
                        milkers[j].remainingGal -= x;
                        x = 0;
                    }
                }
            if (currProfit > maxProfit)
                maxProfit = currProfit;
        }

        out.print(maxProfit);

        in.close();
        out.close();
    }
}