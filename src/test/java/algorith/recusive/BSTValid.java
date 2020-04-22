package algorith.recusive;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证二叉搜索树
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 * @author:ben.gu
 * @Date:2020/4/11 1:39 下午
 */
public class BSTValid {
    public static class TreeNode {
        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    TreeNode pre;

    //根节点大于左子树的所有节点,小于右子树的所有节点
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!isValidBST(root.left)) {
            return false;
        }
        if (pre != null && pre.val > root.val) {
            return false;
        }
        pre = root;
        return isValidBST(root.right);

    }

    //解法2，中序遍历二叉搜索树,将节点放入list中，如果有序说明是有效的二叉搜索树
    List<TreeNode> nodeList = new ArrayList<>();

    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        addNode(root);
        TreeNode pre = null;
        for (int i = 0; i < nodeList.size(); i++) {
            if (pre == null || nodeList.get(i).val > pre.val) {
                pre = nodeList.get(i);
            }else  if (nodeList.get(i).val <= pre.val) {
                return false;
            }
        }
        return true;
    }

    public void addNode(TreeNode root) {
        if (root == null) {
            return;
        }
        addNode(root.left);
        nodeList.add(root);
        addNode(root.right);
    }

    /**
     * [10,5,15,null,null,6,20]
     * @param args
     */
    public static void main(String[] args) {
        BSTValid bst = new BSTValid();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        TreeNode treeNode = new TreeNode(3);
//        treeNode.left = new TreeNode(6);
//        treeNode.right = new TreeNode(20);
        root.right = treeNode;

        boolean result = bst.isValidBST(root);
        System.err.println("result:" + result);
        System.err.println("result2:" + bst.isValidBST2(root));
    }
}
