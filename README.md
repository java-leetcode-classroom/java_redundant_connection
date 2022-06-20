# java_redundant_connection

In this problem, a tree is an **undirected graph** that is connected and has no cycles.

You are given a graph that started as a tree with `n` nodes labeled from `1` to `n`, with one additional edge added. The added edge has two **different** vertices chosen from `1` to `n`, and was not an edge that already existed. The graph is represented as an array `edges` of length `n` where `edges[i] = [ai, bi]` indicates that there is an edge between nodes `ai` and `bi` in the graph.

Return *an edge that can be removed so that the resulting graph is a tree of* `n` *nodes*. If there are multiple answers, return the answer that occurs last in the input.

## Examples

**Example 1:**

![https://assets.leetcode.com/uploads/2021/05/02/reduntant1-1-graph.jpg](https://assets.leetcode.com/uploads/2021/05/02/reduntant1-1-graph.jpg)

```
Input: edges = [[1,2],[1,3],[2,3]]
Output: [2,3]

```

**Example 2:**

![https://assets.leetcode.com/uploads/2021/05/02/reduntant1-2-graph.jpg](https://assets.leetcode.com/uploads/2021/05/02/reduntant1-2-graph.jpg)

```
Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]
Output: [1,4]

```

**Constraints:**

- `n == edges.length`
- `3 <= n <= 1000`
- `edges[i].length == 2`
- `1 <= ai < bi <= edges.length`
- `ai != bi`
- There are no repeated edges.
- The given graph is connected.

## 解析

做法一 ：

透過 edges 資訊做出 adjacency list

並且透過 hashset 紀錄已經走訪過的 vertex

然後透過 dfs 走訪 adjacency list

當發現 目前走訪的vertex 已經走過了 則回傳當下遇到的 edge

每次 loop 需要走訪 n 個 vertex

而 loop edge 有 n 個

所以時間複雜度是 $O(n^2$)

作法二：

因為 n 個 vertex 給 n 個 edge

一定會形成一個 cycle

透過 [Union-Find](https://zh.wikipedia.org/zh-tw/%E5%B9%B6%E6%9F%A5%E9%9B%86) 演算法

逐步形成 cycle 的 edge 找出來

一開使 先初始化 每個 vertex 的 parent 是 vertex 本身

還有 初始化每個 vertex 的 rank 是 1

定義 find(node int): int 用來找尋該 node的最上層的 parent

定義 union(node1, node2 int): bool 用來合成 node1, node2 在一個 connected tree

![](https://i.imgur.com/jMDMp8k.png)

## 程式碼
```java
import java.util.HashMap;

public class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> rank = new HashMap<>();
        int nLen = edges.length;
        for (int node = 1; node <= nLen; node++) {
            parent.put(node, node);
            rank.put(node, 1);
        }
        for (int[] edge: edges) {
            if (!union(edge[0], edge[1], parent, rank)) {
                return edge;
            }
        }
        return new int[]{};
    }
    public int find(int node, HashMap<Integer, Integer> parent) {
        int p = parent.get(node);
        while (p != parent.get(p)) {
            parent.put(parent.get(p), parent.get(parent.get(p)));
            p = parent.get(p);
        }
        return p;
    }
    public boolean union(int node1, int node2, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> rank) {
        int p1 = find(node1, parent);
        int p2 = find(node2, parent);
        if (p1 == p2) {
            return false;
        }
        if (rank.get(p1) > rank.get(p2)) {
            parent.put(p2, p1);
            rank.put(p1, rank.get(p1)+rank.get(p2));
        } else {
            parent.put(p1, p2);
            rank.put(p2, rank.get(p1)+rank.get(p2));
        }
        return true;
    }
}
```
## 困難點

1. 需要透過 DFS 找出 vertex adjacency list 中重複的 vertex 並且知道需要移除哪一個 edge
2. 透過 Union-Find 演算法可以優化時間複雜度

## Solve Point

- [x]  透過 edges 資訊找出 vertex adjacency list
- [x]  透過 hashset 來紀錄拜訪過的 vertex 並且檢查是否重複拜訪
- [x]  透過 DFS 找出 adjacency list 中重複拜訪的 vertex, 並且找出edge