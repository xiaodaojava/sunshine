package red.lixiang.tools.jdk.demo.tree;
import java.lang.IndexOutOfBoundsException;

public class RingLinkedList<T>
{
    public Node lastNode;            // Tail reference to last node of chain
    public int  numberOfEntries;

    /*
     * We have completed this method for you
     */
    public RingLinkedList()
    {
        initializeDataFields();
    } // end default constructor

    /*
     * We have completed this method for you
     */
    public void clear()
    {
        initializeDataFields();
    } // end clear

    /** TODO: Adds a new entry to the end of this list.
     Entries currently in the list are unaffected.
     The list's size is increased by 1.
     @param newEntry  The object to be added as a new entry. */

    public void add(T newEntry)
    {
        Node NewNode=new Node(newEntry);
        if(isEmpty())
        {
            NewNode.next=NewNode;
        }else
        {
            Node firstNode=lastNode.next;
            NewNode.next=firstNode;
            lastNode.next=NewNode;
        }
        lastNode=NewNode;
        numberOfEntries++;
    }

    /** TODO: Adds a new entry at a specified position within this list.
     Entries originally at and above the specified position
     are at the next higher position within the list.
     The list's size is increased by 1.
     @param newPosition  An integer that specifies the desired
     position of the new entry.
     @param newEntry     The object to be added as a new entry.
     @throws  IndexOutOfBoundsException if either
     newPosition < 1 or newPosition > getLength() + 1. */
    public void add(int newPosition, T newEntry)
    {
        /* In-class review , Time complexity : O(N)
        worse case still O(n), efficiency


        if(newPosition>=1&& newPosition<=numberOfEntries+1){
            Node newNode=new Node(newEntry);
            if(isEmpty()) {
                lastNode = newNode;
                newNode.setNextNode(lastNode);
            }else if(newPosition==1) {
                  Node firstNode=lastNode.getNextNode();
                  newNode.setNextNode(firstNode);
                  lastNode.setNextNode(newNode);

            }else if{
                  Node firstNode=lastNode.getNextNode();
                  newNode.setNextNode(firstNode);
                  lastNode.setNextNode(newNode);
                  lastNode=newNode;
            }else{
                  Node nodeBefore=getNodeAt(newPosition-1);
                  Node nodeAfter=nodeBefore.getNextNode();
                  newNode.setNextNode(nodeAfter);
                  nodeBefore.setNextNode(newNode);
            }

        }
        else{
            throw new IndexOutOfBoundsException();
        }

*/







        if(newPosition < 1||newPosition > getLength()+1)
        {
            throw new IndexOutOfBoundsException();
        }else
        {
            Node NewNode=new Node(newEntry);
            if(newPosition==1)
            {
                if(isEmpty())
                {
                    NewNode.next=NewNode;
                    lastNode=NewNode;
                }else
                {
                    Node firstNode=lastNode.next;
                    NewNode.next=firstNode;
                    lastNode.next=NewNode;
                }
            }else if(newPosition==getLength()+1)
            {
                Node firstNode=lastNode.next;
                NewNode.next=firstNode;
                lastNode.next=NewNode;
                lastNode=NewNode;
            }else
            {
                Node current=getNodeAt(newPosition-1);
                NewNode.next=current.next;
                current.next=NewNode;

            }
            numberOfEntries++;
        }
    }

    /** TODO: Removes the entry at a given position from this list.
     Entries originally at positions higher than the given
     position are at the next lower position within the list,
     and the list's size is decreased by 1.
     @param givenPosition  An integer that indicates the position of
     the entry to be removed.
     @return  A reference to the removed entry.
     @throws  IndexOutOfBoundsException if either
     givenPosition < 1 or givenPosition > getLength(). */

    public T remove(int givenPosition)
    {
        /*

      if(newPosition>=1&& newPosition<=numberOfEntries+1){
            if(givenPosition==1){
                Node firstNode=lastNode.getNextNode();
                T ret=firstNode.getData(); //same as .data;
                Node forLastNode=firstNode.getNextNode();
                firstNode.setNextNode(null); //???? null bc no more element, clear memory


            }else{
                Node nodeBefore=getNodeAt(givenPosition-1);
                Node nodeRemove=nodeBefore.getNextNode();
                Node nodeAfter=nodeRemove.getNextNode();
                // its a ring, so no null
                nodeBefore.setNextNode(nodeAfter);
                T ret=nodeRemove.getData();
               b return ret;

            }
      }





        * */







        if(givenPosition < 1||givenPosition > getLength())
        {
            throw new IndexOutOfBoundsException();
        }else
        {
            if(isEmpty())
            {
                return null;
            }
            T removedata=getEntry(givenPosition); //element needs to be deleted
            Node remove=getNodeAt(givenPosition); //
            if(givenPosition==1)
            {
                if(getLength()==1)
                {
                    lastNode.next=null;
                }else
                {
                    lastNode.next=remove.next;
                }
            }else if(givenPosition==getLength())
            {//delete the last position
                Node firstNode=lastNode.next;
                Node frontNode=getNodeAt(givenPosition-1);
                frontNode.next=firstNode;
                lastNode=frontNode;
            }else
            { // delete midd position not head or end
                Node frontNode=getNodeAt(givenPosition-1);
                frontNode.next=remove.next;
            }
            remove.next=null;
            numberOfEntries--;
            return removedata;
        }
    }

    /** TODO: Replaces the entry at a given position in this list.
     @param givenPosition  An integer that indicates the position of
     the entry to be replaced.
     @param newEntry  The object that will replace the entry at the
     position givenPosition.
     @return  The original entry that was replaced.
     @throws  IndexOutOfBoundsException if either
     givenPosition < 1 or givenPosition > getLength(). */

    public T replace(int givenPosition, T newEntry)
    {
        /*
       if(newPosition>=1&& newPosition<=numberOfEntries+1){
            Node desiredNode=getNodeAt(givenPosition);
            T ret=desiredNode.getData();
            desiredNode.setData(newEntry);
            return ret;
       }else{
             throw new IndexOutOfBoundException();


       }









        * */




        if(givenPosition < 1||givenPosition > getLength())
        {
            throw new IndexOutOfBoundsException();
        }else
        {
            if(isEmpty())
            {
                return null;
            }else {
                T olddata=this.getEntry(givenPosition);
                getNodeAt(givenPosition).setData(newEntry);
                return olddata;
            }
        }
    }


    /** TODO: Retrieves the entry at a given position in this list.
     @param givenPosition  An integer that indicates the position of
     the desired entry.
     @return  A reference to the indicated entry.
     @throws  IndexOutOfBoundsException if either
     givenPosition < 1 or givenPosition > getLength(). */
    public T getEntry(int givenPosition)
    {
        if(givenPosition < 1||givenPosition > getLength())
        {
            throw new IndexOutOfBoundsException();
        }else
        {
            if(isEmpty())
            {
                return null;
            }else
            {
                return getNodeAt(givenPosition).data;
            }
        }
    }

    /** TODO: Retrieves all entries that are in this list in the order in which
     they occur in the list.
     @return  A newly allocated array of all the entries in the list.
     If the list is empty, the returned array is empty. */
    public T[] toArray()
    {
        if(isEmpty())
            return null;
        else
        {
            T[] arr =(T[]) new Object[getLength()]; //generic type define
            Node current=lastNode.getNextNode(); //
            for(int count=0;count<getLength();count++)
            {
                arr[count]=current.data;
                current=current.getNextNode();
            }
            return (T[])arr;
        }
    }


    /** TODO: Sees whether this list contains a given entry.
     @param anEntry  The object that is the desired entry.
     @return  True if the list contains anEntry, or false if not. */
    public boolean contains(T anEntry)
    {
        for(int count=1;count<=getLength();count++)
        {
            if(getEntry(count)==anEntry)return true;
        }
        return false;
    }


    /** TODO: Gets the length of this list.
     @return  The integer number of entries currently in the list. */

    public int getLength()
    {
        return numberOfEntries;
    }

    /** TODO: Sees whether this list is empty.
     @return  True if the list is empty, or false if not. */
    public boolean isEmpty()
    {
        if(lastNode==null&&numberOfEntries==0)
            return true;
        else
            return false;
    }


    /*
     * We have completed this method for you
     */
    // Initializes the class's data fields to indicate an empty list.

    private void initializeDataFields()
    {
        lastNode = null;
        numberOfEntries = 0;
    } // end initializeDataFields

    // Returns a reference to the node at a given position.
    // Precondition: The chain is not empty;
    //               1 <= givenPosition <= numberOfEntries.
    private Node getNodeAt(int givenPosition)
    {
        // Assertion: The list is not empty and (1 <= givenPosition) and (givenPosition <= numberOfEntries)
        Node currentNode = lastNode.getNextNode();

        if (givenPosition == numberOfEntries)
            currentNode = lastNode;
        else if (givenPosition > 1)	// Traverse the chain to locate the desired node
        {
            for (int counter = 1; counter < givenPosition; counter++)
                currentNode = currentNode.getNextNode();
        } // end if

        // Assertion: currentNode != null
        return currentNode;
    } // end getNodeAt

    private class Node
    {
        private T    data; // Entry in list
        private Node next; // Link to next node

        private Node(T dataPortion)
        {
            data = dataPortion;
            next = null;
        } // end constructor

        private Node(T dataPortion, Node nextNode)
        {
            data = dataPortion;
            next = nextNode;
        } // end constructor

        private T getData()
        {
            return data;
        } // end getData

        private void setData(T newData)
        {
            data = newData;
        } // end setData

        private Node getNextNode()
        {
            return next;
        } // end getNextNode

        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        } // end setNextNode
    } // end Node
}