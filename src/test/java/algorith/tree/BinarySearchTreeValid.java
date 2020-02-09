package algorith.tree;

import org.apache.commons.lang3.math.NumberUtils;
import sun.jvm.hotspot.jdi.IntegerTypeImpl;

import java.util.*;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).

 Assume a BST is defined as follows:

 The left subtree of a node contains only nodes with keys less than the node's key.
 The right subtree of a node contains only nodes with keys greater than the node's key.
 Both the left and right subtrees must also be binary search trees.

 * @author:ben.gu
 * @Date:2020/2/8 6:10 PM
 */
public class BinarySearchTreeValid {

    //  //[10,5,15,null,null,6,20]
    public static void main(String args[]) {
        BinarySearchTreeValid tree = new BinarySearchTreeValid();

        TreeNode root = new TreeNode(10);

        TreeNode left1 = new TreeNode(5);
        TreeNode right1 = new TreeNode(15);
        root.left = left1;
        root.right = right1;

        TreeNode right21 = new TreeNode(6);
        TreeNode right22 = new TreeNode(20);

        right1.left = right21;
        right1.right = right22;

        boolean valid = tree.isValidBST(root);
        System.err.println("valid:" + valid);
        System.err.println(tree.isValidBST2(root));;

    }

    //中序遍历二叉搜索树,如果是有效的二叉搜索树,遍历出后的节点该是从小到大排列
    //从第一个节点开始往后比较,如果出现 前一个节点大于等于后一个节点则不是二叉搜索树
    public boolean isValidBST2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        Integer inorder = Integer.MIN_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode left = stack.pop();
            if (left.val < inorder) {
                return false;
            }
            inorder = left.val;
            root = left.right;
        }
        return true;
    }

    public boolean isValidBST(TreeNode root) {
        return isValid(root, null, null);
    }

    public boolean isValid(TreeNode root, Integer lower, Integer upper) {
        if (root == null) {
            return true;
        }
        //要保证节点的左右子树所有节点都小于或者大于当前节点的值,需要将当前节点的值作为上限或者下限 跟子树中的节点比较
        int val = root.val;

        if (lower != null && lower >= val) {
            return false;
        }
        if (upper != null && upper <= val) {
            return false;
        }

        if (!isValid(root.left, lower, val)) {
            return false;
        }

        if (!isValid(root.right, val, upper)) {
            return false;
        }
        return true;

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
