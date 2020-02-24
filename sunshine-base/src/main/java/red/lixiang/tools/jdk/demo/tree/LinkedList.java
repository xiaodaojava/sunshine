package red.lixiang.tools.jdk.demo.tree;
//practice exam 2 Q1

public class LinkedList <E extends Comparable<E>>{
    private static class Node<E>{
        private E data;
        private Node<E> next;
        public Node(E d){
            data=d;
            next=null;
        }
    }
    private Node<E> head = new Node<>(null);
    public void add(E e){
        Node<E> n  = new Node<>(e);
        if(head.next == null){
            head.next= n;
            return;
        }
        Node<E> l  = head.next;
        while (l.next!=null){
            l=l.next;
        }
        l.next = n;
    }
   public Node m(){
        return _m(head);
   }
   public Node _m(Node n){
        if(n==null){
            return null;
        }
        if(n.next ==null){
            return n;
        }
        Node next = n.next;
        n.next = null;
        Node newHead  = _m(next);
        Node current = newHead;
        Node previous  = null;
        while (current!=null){
            previous = current;
            current = current.next;
        }
        if(previous !=null){
            previous.next = n;
        }
        return newHead;
   }
    public static void main(String[] args){
       LinkedList<Integer> list = new LinkedList<>();
       list.add(1);
       list.add(2);
       list.add(3);
        Node m = list.m();
        System.out.println(m);


    }
}
