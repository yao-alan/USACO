#include <iostream>
#include <algorithm>
#include <cstring>
#include <vector>
#include <tuple>
#include <utility>
#include <queue>

using namespace std;

typedef tuple<int, int, int> Coord;

class Graph
{
    public:
        Graph(int n);
        void addEdge(int v1, int v2);
        int bfs(int root);
    private:
        vector<vector<int>> adj;
};

Graph::Graph(int n) {
   adj.resize(n);
}

void Graph::addEdge(int v1, int v2) {
    adj[v1].push_back(v2);
}

int Graph::bfs(int root) {
    int transitive = 0;
    queue<int> q;
    q.push(root);
    while (q.size() != 0) {
        int curr = q.front(); q.pop();
        for (int e : adj[curr]) {
            q.push(e);
            ++transitive;
        }
    }
    return transitive;
}

bool cmpX(const Coord &c1, const Coord &c2) {
    return get<0>(c1) < get<0>(c2);
}

bool cmpY(const Coord &c1, const Coord &c2) {
    return get<1>(c1) < get<1>(c2);
}

int main()
{
    int N; cin >> N;
    vector<Coord> eastCows, northCows;
    for (int i = 0; i < N; ++i) {
        char dir; cin >> dir;
        int x, y; cin >> x >> y;
        if (dir == 'E') {
            eastCows.push_back(Coord(x, y, i));
        } else {
            northCows.push_back(Coord(x, y, i));
        }
    }
    sort(northCows.begin(), northCows.end(), cmpX);
    sort(eastCows.begin(), eastCows.end(), cmpY);
    
    Graph g(N);
    int *northBlockedBy = new int[northCows.size()]; // which east cows blocked north cows
    memset(northBlockedBy, -1, sizeof(int) * northCows.size());
    int *eastBlockedBy = new int[eastCows.size()]; // which north cows blocked east cows
    memset(eastBlockedBy, -1, sizeof(int) * eastCows.size());
    for (int i = 0; i < eastCows.size(); ++i) {
        Coord currEast = eastCows[i];
        for (int j = 0; j < northCows.size(); ++j) {
            // north hasn't already been blocked and cow moving north spawns beneath and in front of cow moving east
            if (northBlockedBy[j] == -1 && get<0>(northCows[j]) >= get<0>(currEast) && get<1>(northCows[j]) <= get<1>(currEast)) {
                // north blocks east
                if (get<0>(northCows[j]) + get<1>(northCows[j]) > get<0>(currEast) + get<1>(currEast)) {
                    eastBlockedBy[i] = get<2>(northCows[j]);
                    g.addEdge(get<2>(northCows[j]), get<2>(currEast));
                    break;
                }
                // east blocks north
                else if (get<0>(northCows[j]) + get<1>(northCows[j]) < get<0>(currEast) + get<1>(currEast)) {
                    northBlockedBy[j] = get<2>(currEast);
                    g.addEdge(get<2>(currEast), get<2>(northCows[j]));
                }
            }
        }
    }
    for (int i = 0; i < N; ++i) {
        cout << g.bfs(i) << endl;
    }
}