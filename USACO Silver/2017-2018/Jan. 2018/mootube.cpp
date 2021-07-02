#include <iostream>
#include <iomanip>
#include <cmath>
#include <cctype>
#include <cstdlib>
#include <vector>
#include <set>
#include <map>
#include <unordered_set>
#include <unordered_map>
#include <utility>
#include <cstring>
#include <string>
#include <stack>
#include <queue>
#include <algorithm>

#define V vector
#define pb push_back
#define mp make_pair
#define sz size
#define rsz resize
typedef long long ll;
#define FOR(i, a, b) for(int i = (a); i < (b); ++i)

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    freopen("mootube.in", "r", stdin);
    freopen("mootube.out", "w", stdout);

    int N, Q; cin >> N >> Q;
    vector<pair<int, int>> g[N];
    FOR(i, 0, N-1) {
        int p, q, r; cin >> p >> q >> r;
        g[p-1].pb(mp(q-1, r));
        g[q-1].pb(mp(p-1, r));
    }

    FOR(i, 0, Q) {
        int k, v; cin >> k >> v;
        stack<int> stk; stk.push(v-1);
        bool visited[N]; FOR(i, 0, N) visited[i] = false;
        int nVisited = -1;
        while (!stk.empty()) {
            int curr = stk.top(); stk.pop();
            if (visited[curr]) continue;
            visited[curr] = true;
            nVisited++;
            for (auto &x : g[curr]) {
                if (x.second >= k) stk.push(x.first);
            }
        }
        cout << nVisited << endl;
    }
}
