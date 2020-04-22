package algorith.recusive;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * @author:ben.gu
 * @Date:2020/4/10 10:34 下午
 */
public class MaximumDepthOfBinaryTree {

    public static void main(String[] args) {
        MaximumDepthOfBinaryTree tree = new MaximumDepthOfBinaryTree();

        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left =left;
        root.right =right;

        left.left = new TreeNode(4);
        left.right = new TreeNode(5);

        System.err.println("depth:"+tree.maxDepth(root));

    }

    public static class TreeNode {
        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 根节点的最大深度为左子树或者右子树的最大深度+1
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        //终止条件
        if (root == null) {
            return 0;
        }
        //处理

        //归纳
        return Math.max(maxDepth(root.left),  maxDepth(root.right)) + 1;
    }



}
