import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleList.java
 * A program which mimics a linked list data structure.
 * Allows for the addition, removal, existence, and retrieval of values.
 * This class uses a dummy head node at the beginning of every linked list.
 *
 * @author Ali Malik
 * Professor Zhong CS 310-001
 * @since 03-18-2018
 */

class SimpleList<T> implements Iterable<T> {

    /**
     * A simple node class with values "content" which contains the content of the node
     * And "next" which points to the next node
     */
    private static class Node<T> {
        Node<T> next;
        T content;

        /**
         * The default constructor creates an empty (null) node
         */
        public Node() {
            this.content = null;
            this.next = null;
        }

        public Node(T data) {
            this.content = data;
            this.next = null;
        }
    }

    // a linked list class
    // you decide the internal attributes and node structure
    // but they should all be private
    private int size;
    private Node<T> current; //the current node, most recently added node.
    private Node<T> head; //dummy head node, always null

    /**
     * A constructor to initialize all the attributes of the class
     * Create an empty node and assign it to both current and head.
     */
    @SuppressWarnings("unchecked")
    public SimpleList() {
        //constructor
        this.size = 0; //the head is a null node, doesn't count as a node
        this.current = new Node();
        this.head = this.current;
    }

    /**
     * Create a temporary node (toAdd) with the contents being value.
     * If the size is 0, allow head to point to toAdd, and current to equal toAdd
     * Otherwise, allow current to point to toAdd and then change current to toAdd
     * @param value
     * @return nothing
     */
    @SuppressWarnings("unchecked")
    public void add(T value) {
        // add a new node to the end of the linked list to hold value
        // O(1)
        Node<T> toAdd = new Node(value); //create a node for the value to be added
        if (size == 0) { //if there is no node, point head to toAdd (current)
            this.current = toAdd;
            this.head.next = this.current;
        }
        else { //otherwise point current.next to toAdd and set current to toAdd
            this.current.next = toAdd;
            this.current = toAdd;
        }
        this.size++;
    }

    /**
     * Remove a node by starting at the head.
     * The variable prev represents the previous node and temp represents the next node
     * Go through the linked list and find the first occurrence and remove it by pointing
     * @param value value we are looking for
     * @return boolean stating whether it was found or not. (true = found) (false = not found)
     */
    public boolean remove(T value) {
        // given a value, remove the first occurrence of that value
        // return true if value removed
        // return false if value not present
        // O(N) where N is the number of nodes returned by size()
        boolean loop = true, removed = false; //conditions for the loop and boolean to make sure node was removed
        Node<T> temp = this.head.next; //since head is always null, temp == this.head.next
        Node<T> prev = this.head; //prev will no longer point to prev.next but rather temp.next

        while (loop) { //while loop is true keep doing this
            if (prev.next == null) { //if the temp (prev.next) is null stop everything
                loop = false;
                removed = false;
                break;
            }
            if (temp.next == null) { //if temp.next is null, stop the loop
                loop = false;
            }
            if (temp.content.equals(value)) { //if the nodes content matches value, point prev.next to temp.next
                prev.next = temp.next; //skipping temp
                this.size--; //reduce the size
                removed = true; //node was successfully removed
                loop = false; //no longer need the loop
            } else {
                prev = temp; //increment the node
                temp = temp.next;
            }
        }
        return removed;
    }

    /**
     * Finds the index of node containing the value. Start at head.next, since head is always null.
     * Index is initially set to -1 because head doesnt count as a node.
     * Loop through all of the nodes until a node with contents equal to value is found.
     * @param value
     * @return int index at which the node was found
     */
    public int indexOf(T value) {
        // return index of a value (0 to size-1)
        // if value not present, return -1
        // O(N)
        Node<T> temp = this.head.next; //begin at an actual node
        int index = -1; //begin at -1, the first node is at index 0, we have a dummy head node
        boolean found = false; //boolean for finding it
        boolean loop = true; //boolean for the loop condition
        while (loop) {
            if (temp == null || temp.next == null) { //if the current node is null or the next one is null no longer loop
                loop = false;
            }
            index++; //increment the index
            if (temp.content.equals(value)) { //if the node contents equals the value
                found = true; //found = true we found it
                break; //break out of the loop
            } else { //otherwise increment the node
                temp = temp.next;
            }
        }
        if (found != true) { // if found is still false return -1
            return -1;
        } else {
            return index;
        }
    }

    /**
     * Checks whether any node in the linked list contains the value. If there are no nodes in the linked list
     * simply return false.
     * Start the incrementation of the nodes at head.next (the first node inserted), compare the contents with value.
     * @param value
     * @return boolean, true if any node contains the value, false otherwise
     */
    public boolean contains(T value) {
        // return true if value is present
        // false otherwise
        // O(N) where N is the number of nodes returned by size()
        if (this.head.next == null) { //head points to null there are no other nodes, so return false
            return false;
        }
        Node<T> temp = this.head.next; //start at head.next since head is a dummy node
        boolean found = false;
        boolean loop = true;
        while (loop) {
            if (temp.next == null) { //if temp points to null that means there are no more nodes to increment
                loop = false; //set loop to false
            }
            if (temp == null) { //if temp is null break
                break;
            }
            if (temp.content.equals(value)) { //if the nodes content is equal to the value
                found = true; //node is found
                loop = false; // no need for more looping
                break; // break out of loop
            } else {
                temp = temp.next; //otherwise increment the nodes
            }
        }
        return found;
    }

    /**
     * Returns the content of the node that contains the value. They should be the same.
     * Start at head.next since head is a dummy node. Increment through the nodes until contents of the node match the
     * value,  change the boolean found to true. Then if found is true return temp.contents
     * @param value
     * @return
     */
    public T get(T value) {
        // search for the node with the specified value:
        // if not found, return null;
        // if found, RETURN VALUE STORED from linked list, NOT the incoming value
        // Note: two values might be considered "equal" but not identical
        //       example: Pair <k,v1> and <k,v2> "equal" for different v1 and v2
        // O(N) where N is the number of nodes returned by size()
        Node<T> temp = this.head.next;
        boolean loop = true;
        boolean found = false;
        while (loop) {
            if (temp.next == null) {
                loop = false;
            }
            if (temp.content.equals(value)) {
                found = true;
                loop = false;
                break;
            } else {
                temp = temp.next;
            }
        }
        if (found) {
            return temp.content;
        } else {
            return null;
        }
    }

    /**
     * Return the number of nodes excluding the dummy node
     * @param nothing
     * @return integer this.size
     */
    public int size() {
        //return how many nodes are there
        //O(1)
        return this.size;
    }

    public Iterator<T> iterator () {
        // return a basic iterator
        // .hasNext() and .next() required
        // both should be of O
        return new Iterator<T>() {
            private Node<T> curNode = SimpleList.this.head;

            /**
             * Checks if the current node points to a node. If it points to anything other than null return true.
             * Otherwise return false.
             * @return boolean, whether there is a next node or not
             */
            public boolean hasNext() {
                if (curNode.next != null) {
                    return true;
                } else {
                    return false;
                }
            }

            /**
             * Returns the contents of the next node. Only return the contents if the next node is not null and the
             * current node is not null.
             * @return
             */
            public T next() {
                if (curNode.next != null && curNode != null) {
                    curNode = curNode.next;
                    return (T) curNode.content;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
    //----------------------------------------------------
    // example testing code... make sure you pass all ...
    // and edit this as much as you want!
    // also, consider add a toString() for your own debugging

    public static void main(String[] args) {
        SimpleList<Integer> ilist = new SimpleList<>();
        ilist.add(new Integer(11));
        ilist.add(new Integer(20));
        ilist.add(new Integer(5));

        if (ilist.size() == 3 && ilist.contains(new Integer(5)) &&
                !ilist.contains(new Integer(2)) && ilist.indexOf(new Integer(20)) == 1) {
            System.out.println("Yay 1");
        }

        if (!ilist.remove(new Integer(16)) && ilist.remove(new Integer(11)) &&
                !ilist.contains(new Integer(11)) && ilist.get(new Integer(20)).equals(new Integer(20))) {
            System.out.println("Yay 2");
        }

        Iterator iter = ilist.iterator();
        if (iter.hasNext() && iter.next().equals(new Integer(20)) && iter.hasNext() &&
                iter.next().equals(new Integer(5)) && !iter.hasNext()) {
            System.out.println("Yay 3");
        }

        ilist.add(new Integer(100));
        ilist.add(new Integer(99));
        ilist.add(new Integer(95));
        if (ilist.remove(new Integer(100)) && ilist.contains(new Integer(99))) {
            System.out.println("Yay 4");
        }
    }
}

