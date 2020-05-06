package red.lixiang.tools.jdk.simple.tree;
import java.util.LinkedList;

public class Tree<E>{
   private Node<E> root;
    private static class Node<E>{ //static class

        E data;
        LinkedList<Node<E>> child;
        public Node(E d){
            data=d;
            child=new LinkedList<Node<E>>();
        }
        public void addChild(Node<E> temp){

            this.child.add(temp);
        }
        public String toString(){
            return data.toString()+" has children"+child.toString();
        }
    }

    public static void main(String[] args){
        Tree<Integer> t= new Tree<Integer>(); //created an empty tree at this point
        t.root=new Node<Integer>(1);// the root Node t contains value 1
        Node<Integer> teamp1  = new Node<>(2);
        t.root.addChild(teamp1);
        Node<Integer> teamp2  = new Node<>(4);

        t.root.addChild(teamp2);
        Node<Integer> teamp3  = new Node<>(5);
        t.root.child.get(1).addChild(teamp3);



    }



}
