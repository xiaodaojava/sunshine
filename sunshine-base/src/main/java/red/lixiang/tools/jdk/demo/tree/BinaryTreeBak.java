//package com.platform.tools.jdk.demo.tree;
//
//
//import java.util.*;
//
//
//public class BinaryTreeBak {
//
//    private Node root;
//
//    public BinaryTreeBak() {
//        root = null;
//    }
//
//    private class Node {
//        int data;
//        Node left;
//        Node right;
//
//        public Node(int d) {
//            data = d;
//            left = null;
//            right = null;
//        }
//
//        public String toString() {
//            return Integer.toString(data);
//        }
//
//
//        /**
//         * 向node里面添加数据
//         *
//         * @param n
//         */
//        private void addData(Node n) {
//            Queue<Node> q = new LinkedList<>();
//            //把当前节点添加到队列里面
//            q.add(this);
//            while (true) {
//                // 取出队列的第一个元素
//                Node candidate = q.poll();
//                if (candidate.left == null) {
//                    // 如果第一个元素的左子树为空,把n添加到左子树
//                    candidate.left = n;
//                    break;
//                } else if (candidate.right == null) {
//                    // 如果第一个元素的右子树为空,把n添加到右子树
//                    candidate.right = n;
//                    break;
//                } else {
//                    // 如果左右子树都不为空的话,则把左右子树都添加到队列中
//                    // 然后再到循环开始的地方,从队列的头取出一个元素
//                    q.add(candidate.left);
//                    q.add(candidate.right);
//                }
//            }
//        }
//
//    }
//
//
//    public int sum() throws EmptyBinaryTree {
//        if (root == null)
//            throw new EmptyBinaryTree();
//        else
//            return _sum(root);
//    }
//
//    private int _sum(Node node) {
//        if (node == null) {
//            return 0;
//        }
//        Queue<Node> q = new LinkedList<>();
//        //把当前节点添加到队列里面
//        q.add(node);
//        int totalCount = 0;
//        while (true) {
//            // 取出队列的第一个元素,这里要记数,
//            Node candidate = q.poll();
//            if (candidate != null) {
//                totalCount++;
//            } else {
//                break;
//            }
//            // 如果左右子树都不为空的话,则把左右子树都添加到队列中
//            // 然后再到循环开始的地方,从队列的头取出一个元素
//            if(candidate.left!=null){
//                q.add(candidate.left);
//            }
//            if(candidate.right !=null){
//                q.add(candidate.right);
//            }
//        }
//        return totalCount;
//        //这里要改循环的话, 参考添加节点那里
//       // return node.data + _sum(node.left) + _sum(node.right);
//    }
//
//    public int countNumberOfLeafNodes() throws EmptyBinaryTree {
//        if (root == null)
//            throw new EmptyBinaryTree();
//        else
//            return _countNumberOfLeafNodes(root);
//    }
//
//    private int _countNumberOfLeafNodes(Node node) {
//        if (node == null)
//            return 0;
//        else if (node.left == null && node.right == null)
//            return 1;
//        else
//            return _countNumberOfLeafNodes(node.left) + _countNumberOfLeafNodes(node.right);
//    }
//
//    public String getPreOrder() throws EmptyBinaryTree {
//        if (root == null)
//            throw new EmptyBinaryTree();
//        else
//            return _getPreOrder(root, 1);
//    }
//
//    /**
//     * 这个是中序遍历
//     * @param node
//     * @param depth
//     * @return
//     */
//    public String _getPreOrder(Node node, int depth) {
//        String ret = "";
//        if (node == null)
//            return ret;
//        int counter = 0;
//        while (counter < depth) {
//            ret = ret + " ";
//            counter++;
//        }
//
//        ret = ret + node.data + "\n";
//        ret = ret + _getPreOrder(node.left, depth + 1);
//        ret = ret + _getPreOrder(node.right, depth + 1);
//        return ret;
//    }
//    public String _getPreOrderByStack(){
//
//        Deque<Node> stack = new ArrayDeque<>();
//        stack.addLast(root);
//        while (stack.size()>0){
//            Node n = stack.removeLast();
//            System.out.println(n.data);
//            if(n.right!=null){
//                stack.addLast(n.right);
//            }
//            if(n.left!=null){
//                stack.addLast(n.left);
//            }
//        }
//        return null;
//    }
//
//    public void addDataInTree(int d) {
//        Node node = new Node(d);
//        if (root == null) {
//            root = node;
//            return;
//        }
//        root.addData(node);
//    }
//
//    public String toString() {
//        return _toString(root);
//    }
//
//    /**
//     * @param n root
//     * @return
//     */
//    private String _toString(Node n) {
//        if (n != null)
//            return n.data + _toString(n.left) + _toString(n.right);
//        else
//            return "";
//    }
//
//
//    public static void main(String[] args) {
//        BinaryTreeBak bTree = new BinaryTreeBak();
//        bTree.addDataInTree(1);
//        bTree.addDataInTree(2);
//        bTree.addDataInTree(3);
//        bTree.addDataInTree(4);
//        bTree.addDataInTree(5);
//        bTree.addDataInTree(6);
//        bTree.addDataInTree(7);
//        /*
//         *              1
//         *             / \
//         *      2               3
//         *     / \             / \
//         *  4       5       6       7
//         *
//         */
//        try {
//            System.out.println(bTree._getPreOrderByStack());
//            System.out.println();
//
//            System.out.println(bTree.countNumberOfLeafNodes());
//            System.out.println(bTree.sum());
//        } catch (EmptyBinaryTree e) {
//            e.printStackTrace();
//        }
//
//    }
//}