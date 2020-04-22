package algorith.tree;

import java.util.Stack;

/**
 * 满二叉树:
 * 1.每个节点有2个子节点
 * 2.叶子节点都在最底层
 *
 * 完全二叉树:
 * 1.每个节点至多2个子节点
 * 2.叶子节点在最后两层,最后一层叶子节点靠左排列.并且除了最后一层，其他层的叶子节点数达到最大
 * 完全二叉树在数组中排列空间使用率最高
 * @author:ben.gu
 * @Date:2019/7/4 9:59 PM
 */
public class BinaryTreeTest {

    public static void main(String args[]) {
        //前序遍历：先遍历父节点，然后遍历左子节点，再遍历右子节点
        //中序遍历：先遍历左子节点，然后遍历父节点，再遍历右子节点
        //后序遍历：先遍历左子节点，然后遍历右子节点，再遍历父节点
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n3.left = n1;
        n3.right = n2;
        n1.left=n4;

        preOrder(n3);
        System.err.println("===========");
        preOrderUseStack(n3);

    }

    /**
     * 使用栈不用递归，实现前序遍历
     * @param node
     */
    private static void preOrderUseStack(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        //
        Node root = node;
        while (root != null || !stack.isEmpty()){

            //模拟递归先打印父节点数据,然后遍历左子树,遍历左子树的过程中,先打印父节点的数据。
            //左子树遍历完之后，取出最后一个左子树的右节点,继续做类似的操作。如果右节点为null,再取出最后第二个左子树节点
            while (root != null){
                //打印节点信息,放入左节点
                stack.push(root);
                System.err.println(root.data);
                root = root.left;
            }
            //左节点放完了,拿出来一个左节点，遍历右节点
            Node rootNode = stack.pop();
            root = rootNode.right;
        }

    }


    private static void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.err.println(node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    static class Node {
        private int data;

        private Node left;

        private Node right;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
