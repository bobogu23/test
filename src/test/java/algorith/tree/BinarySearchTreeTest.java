package algorith.tree;

/**
 * 二叉查找树
 * 任意节点的值大于左边所有节点的值，任意节点的值小于右边所有节点的值
 *
 * 删除操作：
 * 1.如果删除节点没有子节点直接删除
 * 2.如果删除节点只有一个子节点，找到删除节点的父节点，指向删除节点的子节点
 * 3.如果删除节点有2个子节点,找到右子树中的最小节点n，拿最小节点n的值替换删除节点的值，然后删除最小节点n
 *
 *
 * 插入操作：
 * 1.从根节点开始 如果插入节点的值比当前节点值大,并且当前节点的右子节点为空,将待插入节点做为右子节点。如果当前节点的右子节点不为空
 * 遍历右子树 直到找到合适的位置。
 * 2.从根节点开始 如果插入节点的值比当前节点值小,并且当前节点的左子节点为空,将待插入节点做为左子节点。如果当前节点的左子节点不为空
 * 遍历左子树 直到找到合适的位置。
 *
 * @author:ben.gu
 * @Date:2019/7/4 11:43 PM
 */
public class BinarySearchTreeTest {
    public static void main(String args[]) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(100);
        tree.insert(200);
        tree.insert(50);
        tree.insert(40);
        tree.insert(101);
        tree.insert(202);
        tree.insert(201);

        tree.print(tree.tree);
        //        System.err.println("delete 105");
        //        tree.delete(105);
        //        tree.print(tree.tree);

        //        System.err.println("delete 101");
        //        tree.delete(101);
        //        tree.print(tree.tree);

        System.err.println("delete 100");
        tree.delete(100);
        tree.print(tree.tree);

        Node node = tree.find(101);
        System.err.println("node:" + (node == null ? "" : node.toString()));

        node = tree.findPrevious(202);
        System.err.println("node:" + (node == null ? "" : node.toString()));

        node = tree.findPrevious(40);
        System.err.println("node:" + (node == null ? "" : node.toString()));

    }

    static class BinarySearchTree {
        private Node tree;

        public Node find(int data) {
            Node p = tree;
            while (p != null) {
                if (p.data == data) {
                    return p;
                }
                if (p.data > data) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }
            return null;
        }

        /**
         * 中序遍历。
         * 查找某个节点的前驱节点。
         * 前驱节点的值小于 该节点的值,并且是该节点左子树中的最大节点。
         * 查找前驱节点有以下情况。
         * 2.如果该节点有左子树,该节点的前驱节点是左子树中的最大节点。
         * 1.如果该节点没有左子树,该节点是父节点的右孩子,则前驱节点是父节点。
         * 3.如果该节点没有左子树,该节点不是父节点的右孩子,则查找父节点的父节点,判断父节点是否是他的父节点的右孩子,如果是则
         *  当该节点的前驱节点是父节点的父节点。否则继续往父节点查找。
        
         *
         * @param data
         * @return
         */
        public Node findPrevious(int data) {
            Node p = tree;
            while (p != null) {
                if (p.data == data) {
                    break;
                } else if (p.data > data) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }

            if (p == null) {
                return null;
            }
            if (p.left != null) {
                Node leftRoot = p.left;
                while (leftRoot.right != null) {
                    leftRoot = leftRoot.right;
                }
                return leftRoot;
            }
            //tree
            Node parent = p.parent;
            while (parent != null && parent.right != p) {
                p = parent;
                parent = parent.parent;

            }
            return parent;
        }

        public Node findSucceed(int data) {
            Node p = tree;
            while (p != null) {
                if (p.data == data) {
                    //                    return pPre == p ? null : pPre;
                }

            }
            return null;
        }

        public void insert(int data) {
            if (tree == null) {
                tree = new Node(data, null, null, null);
                return;
            }
            Node p = tree;
            while (p != null) {
                if (data > p.data) {
                    if (p.right == null) {
                        p.right = new Node(data, null, null, p);
                        return;
                    } else {
                        p = p.right;
                    }
                } else {
                    if (p.left == null) {
                        p.left = new Node(data, null, null, p);
                        return;
                    } else {
                        p = p.left;
                    }
                }
            }
        }

        public void delete(int data) {
            if (tree == null) {
                return;
            }
            Node p = tree;
            //待删除节点的父节点
            Node pParent = null;
            while (p != null && p.data != data) {
                pParent = p;
                if (p.data > data) {
                    p = p.left;
                } else {
                    p = p.right;
                }
            }
            //没找到
            if (p == null) {
                return;
            }

            //要删除的节点B 有2个子节点，为了维持二叉查找树,左子树中的节点值都小于该节点,右子树中的节点值都大于该节点的特性.可以取左子树
            //中的最右边的节点C，或者取右节点中的最左边的节点D。将该节点的值赋值到待删除节点B上。然后删除节点C或者节点D。

            if (p.left != null && p.right != null) {
                Node rightMinParent = p;
                Node rightMin = p.right;
                while (rightMin.left != null) {
                    rightMinParent = rightMin;
                    rightMin = rightMin.left;
                }
                p.data = rightMin.data;
                p = rightMin;
                pParent = rightMinParent;
            }
            //要删除的节点没有子节点,直接删除

            //要删除的节点A只有一个子节点,将待删除的节点A的父节点指向A 的子节点
            Node child = p.left != null ? p.left : p.right;

            //删除根节点
            if (pParent == null) {
                tree = child;
            } else if (pParent.left == p) {
                pParent.left = child;
            } else {
                pParent.right = child;
            }
        }

        public void print(Node root) {
            Node p = root;
            if (p != null) {
                System.err.println(" " + p.data);
                print(p.left);
                print(p.right);
            }
        }
    }

    static class Node {
        private int data;

        private Node left;

        private Node right;

        private Node parent;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node(int data, Node left, Node right, Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node{" + "data=" + data + ", left=" + left + ", right=" + right + '}';
        }
    }
}
