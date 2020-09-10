/** N <= 1000 --> suggests an N^2 approach, since this will take approx. 1 second 
 *  1. make every fence length 2 rather than 1; a fence will be the border of a unit square if the point
 *     in the middle of the square is bordered on all sides
 *      a. Thus, points with even (x,y) will be fences and points with odd (x, y) will be empty space
 *  2. run a floodfill on all even points to find the total number of distinct regions
*/

import java.io.*;
import java.util.*;

public class Gates 
{
    private static int maxRegions;
    private static boolean newRegion;
    private static void floodfill(int x, int y, boolean[][] farm, boolean[][] visited) {
        if (visited[x][y])
            return;
        else {
            newRegion = true;
            visited[x][y] = true;
            if (x >= 2 && farm[x - 1][y] == false)
                floodfill(x - 2, y, farm, visited);
            if (x <= farm.length - 2 && farm[x + 1][y] == false)
                floodfill(x + 2, y, farm, visited);
            if (y >= 2 && farm[x][y - 1] == false)
                floodfill(x, y - 2, farm, visited);
            if (y <= farm[0].length - 2 && farm[x][y + 1] == false)
                floodfill(x, y + 2, farm, visited);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("gates.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gates.out")));

        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        boolean[][] farm = new boolean[4 * N][4 * N];
        boolean[][] visited = new boolean[4 * N][4 * N];
        int x = 2 * N - 2, y = 2 * N - 2; /** start in the middle of the farm */
        farm[x][y] = true;
        String str = new StringTokenizer(in.readLine()).nextToken();
        for (int i = 0; i < N; i++) {
            char c = str.charAt(i);
            if (c == 'N') {
                y += 2;
                farm[x][y - 1] = true;
                farm[x][y] = true;
            }
            else if (c == 'S') {
                y -= 2;
                farm[x][y + 1] = true;
                farm[x][y] = true;
            }
            else if (c == 'E') {
                x += 2;
                farm[x - 1][y] = true;
                farm[x][y] = true;
            }
            else if (c == 'W') {
                x -= 2;
                farm[x + 1][y] = true;
                farm[x][y] = true;
            }
        }

        maxRegions = 0;
        newRegion = true;
        for (int i = 1; i < 4 * N; i += 2) {
            for (int j = 1; j < 4 * N; j += 2) {
                Gates.floodfill(i, j, farm, visited);
                if (newRegion)
                    maxRegions++;
                newRegion = false;
            }
        }
        out.println(maxRegions - 1);

        in.close();
        out.close();
    }
}