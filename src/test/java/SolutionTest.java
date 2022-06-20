import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    final  private Solution sol = new Solution();
    @Test
    void findRedundantConnectionExample1() {
        assertArrayEquals(new int[]{2,3}, sol.findRedundantConnection(new int[][]{
                {1,2},
                {1,3},
                {2,3}
        }));
    }
    @Test
    void findRedundantConnectionExample2() {
        assertArrayEquals(new int[]{1,4}, sol.findRedundantConnection(new int[][]{
                {1,2},
                {2,3},
                {3,4},
                {1,4},
                {1,5}
        }));
    }
}