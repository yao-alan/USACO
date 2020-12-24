#include <iostream>
#include <algorithm>
#include <utility>
#include <vector>
#include <queue>

using namespace std;

class Graph
{
    public:
        Graph(int n);
        void addEdge(int v1, int v2);
        vector<vector<int>> nodes;
};

Graph::Graph(int n) {
    nodes.resize(n);
}

void Graph::addEdge(int v1, int v2) {
    nodes[v1].push_back(v2);
    nodes[v2].push_back(v1);
}

int main()
{
    int N; cin >> N;
    Graph g(N);
    for (int i = 0; i < N-1; ++i) {
        int a, b; cin >> a >> b;
        g.addEdge(a-1, b-1);
    }
    bool *visited = new bool[N];
    int nDays = 0;
    queue<int> q;
    q.push(0);
    while (q.size() != 0) {
        int curr = q.front(); q.pop();
        visited[curr] = true;
        int nBranches = 0;
        for (int d : g.nodes[curr]) {
            if (!visited[d]) {
                ++nBranches;
                ++nDays;
                q.push(d);
            }
        }
        for (int i = 1; i < nBranches+1; i *= 2)
            ++nDays;
    }
    delete[] visited;
    cout << nDays << endl;
}
