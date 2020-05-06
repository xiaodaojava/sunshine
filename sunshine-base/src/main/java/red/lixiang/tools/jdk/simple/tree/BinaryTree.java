package red.lixiang.tools.jdk.simple.tree;

/**
 * @author lixiang
 * @date 2019/12/15
 **/
public class BinaryTree<T extends Comparable<T>> {
    private Node root;

    class Node {
        T data;
        Node left;
        Node right;

        public Node(T d) {
            data = d;
        }
    }

    private T max(T value1, T value2) {
        if (value1 == null && value2 == null) {
            return null;
        }
        if (value1 == null) {
            return value2;
        }
        if (value2 == null) {
            return value1;
        } else {
            return value1.compareTo(value2) > 0 ? value1 : value2;
        }
    }

    public T findMaximum() {
        if (root == null) {
            return null;
        }
        return _findMaximum(root);
    }

    public T _findMaximum(Node r) {
        //如果两孩子为空,则传回自己
        if (r.left == null && r.right == null) {
            return r.data;
        }


        T maxLeft = null; T maxRight = null;
        if(r.left!=null){
             maxLeft = _findMaximum(r.left);

        }
        if(r.right !=null){
             maxRight = _findMaximum(r.right);
        }
        return max(maxLeft, maxRight);

    }
}
