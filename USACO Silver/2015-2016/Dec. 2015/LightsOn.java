/** dfs floodfill
 * Naive floodfill construction:
 * 1. begin by creating a 2D array to represent the room; each room must store whether it is lit and 
 * the coordinates of the room its switch can light (assuming it has a switch)
 * 2. traverse the rooms with a dfs floodfill; if the room has a light switch, increase the number of 
 * lit rooms by 1.
 *      a. need worst-case NxN dfs traversals, as some pathways may not open up until a switch
 *         is turned on, but once that pathway is opened up, Bessie may not be able to go back
 *         and traverse that path (since in dfs, you cannot go across an already-traversed path)
 *              Happens to work with 2N traversals
 * 3. Key to the actual solution: If Bessie turns on a square that is adjacent to one that she has 
 * already visited, visit that square
 *      a. Eliminates the problem with needing NxN dfs traversals in worst case
 */

import java.io.*;
import java.util.*;

public class LightsOn
{
    private static int maxLit;
    public static class Room 
    {
        public boolean lit;
        public ArrayList<Coord> switches;
        public Room() {
            switches = new ArrayList<>();
        }
    }
    public static class Coord 
    {
        public int x;
        public int y;
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lightson.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lightson.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        /** rooms[i][j][0] = lit/not lit; rooms[i][j][1] = x-coord. of the room the switch lights up;
         * rooms[i][j][2] = y-coord. of the room the switch lights up; x & y = 0 if no switch exists
         */
        Room[][] rooms = new Room[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rooms[i][j] = new Room();
            }
        }
        rooms[0][0].lit = true; /** 1 = lit, 0 = unlit */
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            Coord c = new Coord(a, b);
            rooms[x][y].switches.add(c);
        }

        boolean[][] coreVisited = new boolean[N][N];
        boolean[][] tmpVisited = new boolean[N][N];
        maxLit = 1;
        for (int i = 0; i < 2 * N; i++) {
            dfs(rooms, 0, 0, coreVisited, tmpVisited);
            tmpVisited = new boolean[N][N];
        }
        pw.println(maxLit);

        br.close();
        pw.close();
    }
    /** 1 represents currently lit and unvisited, 0 represents unlit and unvisited, 
     * -1 represents previously lit and visited */
    private static void dfs(Room[][] rooms, int x, int y, boolean[][] coreVisited, boolean[][] tmpVisited) {
        if (!rooms[x][y].lit || tmpVisited[x][y])
            return;
        else {
            for (int i = 0; i < rooms[x][y].switches.size(); i++) {
                int a = rooms[x][y].switches.get(i).x;
                int b = rooms[x][y].switches.get(i).y;
                if (!rooms[a][b].lit) {
                    rooms[a][b].lit = true;
                    if (!coreVisited[a][b]) {
                        coreVisited[x][y] = true;
                        maxLit++;
                    }
                }
            }
            tmpVisited[x][y] = true;
            if (x < rooms.length - 1) 
                dfs(rooms, x + 1, y, coreVisited, tmpVisited);
            if (x > 0)
                dfs(rooms, x - 1, y, coreVisited, tmpVisited);
            if (y < rooms[0].length - 1)
                dfs(rooms, x, y + 1, coreVisited, tmpVisited);
            if (y > 0)
                dfs(rooms, x, y - 1, coreVisited, tmpVisited);
        }
    }
}