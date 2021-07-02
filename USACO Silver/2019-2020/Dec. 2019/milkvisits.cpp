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
    cin.tie(0);
    ios_base::sync_with_stdio(false);
    freopen("milkvisits.in", "r", stdin);
    freopen("milkvisits.out", "w", stdout);
    
    int N, M; cin >> N >> M;
    string s; cin >> s;
    V<int> g[N];
    FOR(i, 0, N-1) {
        int a, b; cin >> a >> b;
        g[a-1].pb(b-1);
        g[b-1].pb(a-1);
    }

    int component[N]; component[0] = 0;
    bool visited[N]; for (int i = 0; i < N; ++i) visited[i] = false;
    stack<int> stk;
    stk.push(0);
    int parent[N]; parent[0] = -1;
    int mxComponent = 0;
    while (!stk.empty()) {
        int curr = stk.top(); stk.pop();
        if (visited[curr]) continue;
        visited[curr] = true;
        if (parent[curr] != -1) {
            if (s[curr] == s[parent[curr]]) component[curr] = component[parent[curr]];
            else component[curr] = ++mxComponent;
        }
        for (auto &x : g[curr]) {
            stk.push(x);
            if (!visited[x]) parent[x] = curr;
        }
    }

    string ans = "";
    FOR(i, 0, M) {
        int a, b; cin >> a >> b;
        char c; cin >> c;
        if (component[a-1] != component[b-1]) ans += "1";
        else if (s[a-1] == c) ans += "1";
        else ans += "0";
    }

    cout << ans << endl;
}
