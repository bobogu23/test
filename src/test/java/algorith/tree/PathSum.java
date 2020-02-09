package algorith.tree;

import java.util.Stack;

/**
 *
 *   5
   /   \
  4     8
  /     /  \
 11   13    4
 / |         \
 7  2         1
 * @author:ben.gu
 * @Date:2020/2/8 10:49 PM
 */
public class PathSum {

    public static void main(String args[]) {
        TreeNode root = new TreeNode(1);

        TreeNode r1 = new TreeNode(2);
        root.right = r1;

        TreeNode r2 = new TreeNode(3);
        r1.right = r2;

        TreeNode r3 = new TreeNode(4);
        r2.right = r3;

        TreeNode r4 = new TreeNode(5);
        r3.right = r4;

        PathSum p = new PathSum();
        boolean b = p.hasPathSum(root, 3);
        System.err.println("result:" + b);
    }

    /**
     * [1,null,2,null,3,null,4,null,5]
       15
      * 深度优先遍历,递归。叶节点的特点是 左右节点为null.判断根节点到达左右节点的叶节点之和是否等于给定值
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {

        return dfs(root, sum, 0);
    }

    private boolean dfs(TreeNode root, int sum, int value) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return sum == value + root.val;
        }
        return dfs(root.left, sum, value + root.val) || dfs(root.right, sum, value + root.val);
    }

    public static class TreeNode {
        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
