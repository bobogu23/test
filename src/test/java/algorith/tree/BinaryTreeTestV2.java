package algorith.tree;

/**
 * @author:ben.gu
 * @Date:2020/2/6 10:53 PM
 */
public class BinaryTreeTestV2 {

    static class BinaryTree {
        private Node tree;

        private void insert(int data) {
            if (tree == null) {
                tree = new Node(data, null, null);
                return;
            }
            Node p = tree;
            while (p != null) {
                if (tree.left == null) {
                    tree.left = new Node(data, null, null);
                } else if (tree.right == null) {
                    tree.right = new Node(data, null, null);
                } else {
                    p = p.left;
                }
            }
        }

    }

    static class Node {
        private int data;

        private Node left;

        private Node right;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
