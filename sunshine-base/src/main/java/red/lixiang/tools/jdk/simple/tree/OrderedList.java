package red.lixiang.tools.jdk.simple.tree;

public class OrderedList<T extends Comparable<T>> {
    private class Node {
        T value;
        Node next;
        Node(T v, Node n) {
            value = v;
            next = n;
        }
    }

    // A dummy head node -- having it simplifies a lot of code
    private Node head = new Node(null, null);

    /*
     * Inserts v into the list, maintaining elements in increasing order
     */
    public void insert(T v) {
        // TODO: Implement


        Node newNode = new Node(v,null);
        Node p = this.head.next;

        if(p==null){
            this.head.next =newNode;
            newNode.next=null;
            return;
        }
        Node p2=p.next;
        Node pre = p;
        if(p2==null){
            p.next = newNode;
            return;
        }
        while (p2!=null){
            if(p2.value.compareTo(v)<0){
                pre = p2;
                p2=p2.next;
                if(p2==null){
                    pre.next = newNode;
                }
                continue;
            }
            newNode.next = p2.next;
            pre.next = newNode;
            break;
        }

    }

    /*
     * Removes v; if there is more than one instance of v, removes only one.
     * If v was found, returns true. Else, does nothing and returns false.
     */
    public boolean delete(T v) {
        Node prev = head, cur = head.next;
        while (cur != null) {
            int compResult = cur.value.compareTo(v);
            if (compResult == 0) { // we found the value to delete
                prev.next = cur.next;
                return true;
            }
            if (compResult > 0) { // we went past the value to delete and it's not in the list
                return false;
            }
            prev = cur;
            cur = cur.next;
        }
        return false; // the value to delete is greater than everything in the list
    }

    /*
     * Returns true if the list contains v
     */

    public boolean contains(T v) {
        // TODO: Implement
        Node previous=head;
        Node current=head.next;
        if(previous==null){
            return false;
        }
        while(current!=null){
            if(previous.value.compareTo(v)==0){
                return true;
            }
            previous=previous.next;
            current=current.next;
        }
        return false;
    }//check pls ???

    /*
     * Returns the result of merging this and that; runs in time O(|this|+|that|).
     * Does not modify this or that.
     */
    OrderedList<T> merge(OrderedList<T> that) {
        // TODO: Implement
        //merge this and merge that, then add to new list
        //time complexity is n
        OrderedList<T> ret = new OrderedList<>(); // new list to contain this and that
        //compare this and that,
        //if empty this,
        doMerge(ret, this.head.next, that.head.next);
        //temp is the smallest value
        //add the smallest value to ret
        //recursion for n
        return ret; // Just a placeholder--remove
    }

    public void doMerge(OrderedList<T> ret, Node thisNode, Node thatNode) {
        if (thisNode == null && thatNode == null) {
            return;
        }
        if (thisNode == null) {
            while (thatNode != null) {
                ret.insert(thatNode.value);
                thatNode = thatNode.next;
            }
            return;
        }
        if (thatNode == null) {
            while (thisNode != null) {
                ret.insert(thisNode.value);
                thisNode = thisNode.next;
            }
            return;
        }

        if (thisNode.value.compareTo(thatNode.value) > 0) {
            ret.insert(thatNode.value);
            doMerge(ret, thisNode, thatNode.next);
        } else {
            ret.insert(thisNode.value);
            doMerge(ret, thisNode.next, thatNode);
        }


    }


    /* Same output format as java.util.Arrays.toString
     */
    @Override
    public String toString() {
        if (head.next == null) return "[]";
        String s = "[";
        Node p;
        for (p = head.next; p.next != null; p = p.next) {
            s += p.value + ", ";
        }
        s += p.value + "]"; // last one is special, because it has no comma after
        return s;
    }

    public static void main(String[] args) {
        OrderedList<Integer> thisList  = new OrderedList<>();
        thisList.insert(100);
        thisList.insert(100);
        thisList.insert(22);
        System.out.println(thisList.toString());
        OrderedList<Integer> thatList  = new OrderedList<>();
        thatList.insert(2);
        thatList.insert(4);
        thatList.insert(6);
        OrderedList<Integer> merge = thisList.merge(thatList);
        System.out.println(merge);

    }


}