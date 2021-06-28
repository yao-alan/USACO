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

/* 1. For a given power R, optimal to land in the middle of a gap as close to 
      2R in size; for each haybale at position x, look for haybale with 
      greatest position <= x+2R
   2. Once a position y has been found, simulate explosions by destroying 
      a lower bound >= y-R and upper bound <= y+R; continue until nothing more
      can be destroyed and see if this covers max(haybale) - min(haybale)
   3. Binary search on 2R
*/

int binsearch_helper(bool type, vector<int>& arr, double x) {
    // lower_bound = greatest number <= x
    // upper_bound = smallest number >= x
    int lo = 0, hi = arr.size()-1;
    int best = type ? lo : hi;
    while (lo <= hi) {
        int mid = lo + (hi-lo)/2;
        if (type) { // lower bound
            if (arr[mid] <= x) {
                best = max(best, mid);
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        } else { // upper bound
            if (arr[mid] >= x) {
                best = min(best, mid);
                hi = mid-1;
            } else {
                lo = mid+1;
            }
        }
    }
    return best;
}

int lower_bound(vector<int>& arr, double x) { return binsearch_helper(true, arr, x); }
int upper_bound(vector<int>& arr, double x) { return binsearch_helper(false, arr, x); }

int main() {
    freopen("angry.in", "r", stdin);
    freopen("angry.out", "w", stdout);

    // all values are doubled so that we can binary search on multiples of 0.5
    int N; cin >> N;
    vector<int> haybales;
    for (int i = 0; i < N; ++i) {
        int h; cin >> h;
        haybales.pb(2*h);
    }
    sort(haybales.begin(), haybales.end());

    int min2R = 2e9;
    int lo = 1, hi = 2*(haybales.back() - haybales.front());
    double center = haybales.front() + (haybales.back()-haybales.front())/2.0;
    while (lo <= hi) {
        int mid2R = lo + (hi-lo)/2;
        /* figure out where to drop cow
           1. if we can explode everything on right but not left, move left
           2. if we can explode everything on left but not right, move right
           3. else if we cannot explode everything, break
        */
        bool possible = false;
        int left = 0, right = haybales.back();
        while (left <= right) {
            int mid = left + (right-left)/2;
            // simulate exploding cows
            double power = mid2R/2.0;
            int lowestLeft = upper_bound(haybales, mid-power);
            while (power > 0 && lowestLeft != 0) {
                power -= 2.0;
                int lower = upper_bound(haybales, haybales[lowestLeft] - power);
                if (lower == lowestLeft) break;
                lowestLeft = lower;
            }
            power = mid2R/2.0;
            int highestRight = lower_bound(haybales, mid+power);
            while (power > 0 && highestRight != N-1) {
                power -= 2.0;
                int higher = lower_bound(haybales, haybales[highestRight] + power);
                if (higher == highestRight) break;
                highestRight = higher;
            }
            if (lowestLeft > 0 && highestRight < N-1) { // R unusable
                break;
            } else if (lowestLeft > 0) { // move left
                right = mid-1;
            } else if (highestRight < N-1) { // move right
                left = mid+1;
            } else if (lowestLeft == 0 && highestRight == N-1) {
                possible = true;
                break;
            }
        }
        if (possible) {
            min2R = min(min2R, mid2R);
            hi = mid2R-1;
        } else {
            lo = mid2R+1;
        }
    }

    printf("%1.1f", double(min2R)/4.0);
}
