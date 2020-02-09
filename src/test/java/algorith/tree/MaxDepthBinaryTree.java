package algorith.tree;

import java.util.*;

/**
 * Given a binary tree, find its maximum depth.


 * @author:ben.gu
 * @Date:2020/2/8 2:32 PM
 */
public class MaxDepthBinaryTree {

    public static void main(String args[]) {
        MaxDepthBinaryTree tree = new MaxDepthBinaryTree();

        TreeNode root = new TreeNode(4);

        TreeNode level1Left = new TreeNode(2);
        TreeNode level1Right = new TreeNode(7);
        root.left = level1Left;
        root.right = level1Right;

        TreeNode level2Left = new TreeNode(1);
        TreeNode level2Right = new TreeNode(3);
        level1Left.left = level2Left;
        level1Left.right = level2Right;

        TreeNode level2Left2 = new TreeNode(6);
        TreeNode level2Right2 = new TreeNode(9);

        level1Right.left = level2Left2;
        level1Right.right = level2Right2;

        root.print();

        System.err.println("depth:" + tree.maxDepth(root));

    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode top = queue.poll();
                if (top.left != null) {
                    queue.add(top.left);
                }
                if (top.right != null) {
                    queue.add(top.right);
                }
                size--;
            }
            depth++;
        }

        return depth;
    }

    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMax = root.left == null ? 0 : maxDepth2(root.left);
        int rightMax = root.right == null ? 0 : maxDepth2(root.right);

        return 1 + Math.max(leftMax, rightMax);
    }

    public static class TreeNode {
        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        public void print() {
            List<Integer> list = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(this);
            while (!queue.isEmpty()) {
                int size = queue.size();
                while (size > 0) {
                    TreeNode top = queue.poll();
                    list.add(top.val);
                    if (top.left != null) {
                        queue.add(top.left);
                    }
                    if (top.right != null) {
                        queue.add(top.right);
                    }
                    size--;
                }
            }
            System.err.println("tree:" + Arrays.toString(list.toArray()));
        }
    }

}
