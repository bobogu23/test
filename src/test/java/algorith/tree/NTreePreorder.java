package algorith.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/n-ary-tree-preorder-traversal/
 * 
 * Given an n-ary tree, return the preorder traversal of its nodes' values.
 *
 * Nary-Tree input serialization is represented in their level order traversal,
 * each group of children is separated by the null value (See examples).
 * @author:ben.gu
 * @Date:2020/4/9 11:26 下午
 */
public class NTreePreorder {

    // Definition for a Node.
    class Node {
        public int val;

        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    //前序遍历，先取父节点
    public List<Integer> preorder(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        list.add(root.val);
        List<Node> children = root.children;
        for(Node n : children){
            list.addAll(preorder(n));
        }
        return list;
    }
}
