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
