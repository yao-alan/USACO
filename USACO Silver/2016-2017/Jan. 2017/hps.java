/** O(N) prefix sum to see how many wins Bessie can get with hoof/paper/scissors 
 *      (store in 2D array of [N][4], where [N][0] stores the gesture itself)
 *  run a pointer through the set to designate the point at which Bessie switches; on either side
 *  of the pointer Bessie uses a certain gesture (hoof/paper, hoof/scissors, scissors/paper, etc.)
 *  total num won = num_hoof up to the pointer + num_paper from pointer to end (for example); try 
 *  all 6 combinations at each pointer location
 *      pointer can run through in O(N); 6N should be fast enough for N < 100,000
 */

 import java.io.*;
 import java.util.*;

 public class hps
 {
     private static class Gesture
     {
         char move;
         int hoof;
         int paper;
         int scissors;
         private Gesture(char move) {
            this.move = move;
         }
     }
     public static void main(String[] args) throws IOException {
         BufferedReader in = new BufferedReader(new FileReader("hps.in"));
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
         StringTokenizer st = new StringTokenizer(in.readLine());

         int N = Integer.parseInt(st.nextToken());
         Gesture[] bessie = new Gesture[N + 1];
         bessie[0] = new Gesture('x');
         for (int i = 1; i <= N; i++) {
             st = new StringTokenizer(in.readLine());
             bessie[i] = new Gesture(st.nextToken().charAt(0));
             bessie[i].hoof = bessie[i - 1].hoof;
             bessie[i].paper = bessie[i - 1].paper;
             bessie[i].scissors = bessie[i - 1].scissors;
             if (bessie[i].move == 'H')
                bessie[i].paper++;
             else if (bessie[i].move == 'P')
                bessie[i].scissors++;
             else
                bessie[i].hoof++;
         }

         int maxWins = 0;
         for (int i = 1; i <= N; i++) { /** pointer location */
            /** hoof/paper */
            if (bessie[N].paper - bessie[i].paper + bessie[i].hoof > maxWins)
                maxWins = bessie[N].paper - bessie[i].paper + bessie[i].hoof;
            /** hoof/scissors */
            if (bessie[N].scissors - bessie[i].scissors + bessie[i].hoof > maxWins)
                maxWins = bessie[N].scissors - bessie[i].scissors + bessie[i].hoof;
            /** paper/hoof */
            if (bessie[N].hoof - bessie[i].hoof + bessie[i].paper > maxWins)
                maxWins = bessie[N].hoof - bessie[i].hoof + bessie[i].paper;
            /** paper/scissors */
            if (bessie[N].scissors - bessie[i].scissors + bessie[i].paper > maxWins)
                maxWins = bessie[N].scissors - bessie[i].scissors + bessie[i].paper;
            /** scissors/hoof */
            if (bessie[N].hoof - bessie[i].hoof + bessie[i].scissors > maxWins)
                maxWins = bessie[N].hoof - bessie[i].hoof + bessie[i].scissors;
            /** scissors/paper */
            if (bessie[N].paper - bessie[i].paper + bessie[i].scissors > maxWins)
                maxWins = bessie[N].paper - bessie[i].paper + bessie[i].scissors;
         }

         out.print(maxWins);

         in.close();
         out.close();
     }
 }