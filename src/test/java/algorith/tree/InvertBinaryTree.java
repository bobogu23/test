package algorith.tree;

import java.util.*;

/**
 * input:

    4
   /   \
 2     7
 / \   / \
 1   3 6   9

 output:
    4
  /   \
  7     2
 / \   / \
 9  6  3   1
 * @author:ben.gu
 * @Date:2020/2/8 11:46 AM
 */
public class InvertBinaryTree {
    public static void main(String args[]) {
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

        InvertBinaryTree tree = new InvertBinaryTree();
        TreeNode node = tree.invertTree(root);
        node.print();

        tree.invertTree2(node).print();

    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode top = queue.poll();
            //交换left，right
            TreeNode tmp = top.left;
            top.left = top.right;
            top.right = tmp;

            if (top.left != null) {
                queue.add(top.left);
            }
            if (top.right != null) {
                queue.add(top.right);
            }
        }
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = invertTree2(root.left);
        TreeNode right = invertTree2(root.right);

        root.left =right;
        root.right =left;

        return root;
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
