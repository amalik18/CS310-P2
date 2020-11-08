import java.util.Iterator;

/**
 * HashTable.java
 * A hash table which uses separate chaining to overcome collisions. Uses the SimpleList file to work properly.
 * This class allows for the addition, removal, retrieval, rehashing, and existence of values using SimpleList.
 *
 * @author Ali Malik
 * Professor Zhong CS 310-001
 * @since 03-18-2018
 */

class HashTable<T> {
    // this is the class that you need to write to implement a simple hash table
    // with separate chaining

    // you decide which additional attributes to include in this class but they should all be private

    private int size; //number of elements
    private int tbSize = 11; //tbSize is the size of the table, initially 11
    private int numChain; //number of chains
    private double avgChain, load;
    @SuppressWarnings("unchecked")
    private SimpleList<T>[] table;

    /**
     * Initialize all of the internal attributes. Go through each element in table and initialize it to an empty linked
     * list.
     */
    public HashTable(){
        //constructor
        this.size = 0;
        this.avgChain = 0.0;
        this.numChain = 0;
        this.load = 0.0;
        this.table = new SimpleList[11];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new SimpleList<>();
        }
    }

    /**
     * Calculate the hash code for the incoming value and mod it against the current table size.
     * If the linked list at that hash value already contains that value, do not add.
     * If the linked list at that hash value is empty, add 1 to the numChain attribute.
     * Add the value to the linked list at the hash value using the add() method in SimpleList
     * Now check if the avgChainLength is greater than 1.2, if so rehash.
     * Find the next prime number greater than 2 * current table size.
     * Rehash to a table of the size returned by nextPrime().
     * @param value value that needs to be added.
     * @return boolean, true if successfully added, false otherwise.
     */
    public boolean add(T value) {
        // adds an item to the hash table
        // returns true if you successfully add value
        // returns false if the value can not be added
        // (i.e. the value already exists in the set)

        // note: if the average chain length is > 1.2
        // must rehash to the next prime number larger
        // than twice the size before returning

        // O(M) worst case, where M =  size returned by size()
        // O(1) or O(M/N) average case (where M/N is the load)
        // the average case can be amortized Big-O

        int hash = Math.abs(value.hashCode()) % this.tbSize; //positive number less than the table size.
        if (table[hash].contains(value)) {
            return false;
        }
        else {
            if (this.table[hash].size() == 0) { //if size at a hash value is 0, we are adding a new chain
                this.numChain++;
            }
            this.table[hash].add(value); //add value to the linked list at hash value
            this.size++; //add 1 to size
            if (getAvgChainLength() > 1.2) { //if avgChainLength is greater than 1.2 rehash
                boolean done = false;
                int tempSize = 2 * this.tbSize;
                int newSize = 0;
                while (!done) {
                    newSize = nextPrime(tempSize);
                    if((newSize  > (2*this.tbSize))){ //keep getting the next prime until the prime number is > 2*tbSize
                        done = rehash(newSize); //rehash once newSize is > 2*tbSize.
                    }
                    tempSize = newSize+1;
                }
                if (done) {
                    this.tbSize = newSize; //if successfully rehashed changed the table size
                }
            }

            return true;
        }
    }

    /**
     * Remove the value from the hash table.
     * Start by finding the hash value for the value.
     * Remove the node from the linked list located at hash value  using the remove() method from SimpleList
     * If successfully removed decrement the size (number of elements)
     * @param value value to be removed
     * @return boolean, true if successfully removed item, false otherwise.
     */
    public boolean remove(T value) {
        // removes a value from the hash table
        // returns true if you remove the item
        // returns false if the item could not be found

        // O(M) worst case, where M =  size returned by size()
        // O(1) or O(M/N) average case (where M/N is the load)
        int hash = Math.abs(value.hashCode()) % this.tbSize;
        if (this.table[hash].remove(value)) {
            this.size--;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns a boolean specifying whether the value is contained in the table
     * Calculate the positive hash code for the value
     * Check if the value is contained in the linked list at the hash value using the contains() method in SimpleList
     * If the value is contained in the linked list at the hash value return true, false otherwise.
     * @param value value that is being checked
     * @return boolean, true if the value is in the hash table, false otherwise
     */
    public boolean contains(T value) {
        // returns true if the item can be found in the table

        // O(M) worst case, where M = size returned by size()
        // O(1) or O(M/N) average case (where M/N is the load)
        int hash  = Math.abs(value.hashCode()) % this.tbSize;
        if (this.table[hash].contains(value)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Return the content of the node that has value.
     * Calculate the positive hash code.
     * Check if the linked list at the hash value contains that value and then return it using the get() method in
     * SimpleList
     * Return the contents of that node.
     * @param value value that is being checked
     * @return T, return the value
     */
    public T get(T value) {
        // return null if the item could not be found in hash table;
        // return the item FROM THE HASH TABLE if it was found.
        // NOTE: do NOT return the parameter value!!
        //       While "equal" they may not be the same.
        //       For example, When value is a PAIR<K,V>,
        //       its "equals" methods returns true if just the keys are equal.

        // O(M) worst case, where M = size returned by size()
        // O(1) or O(M/N) average case (where M/N is the load)
        int hash = Math.abs(value.hashCode()) % this.tbSize;
        T retValue = this.table[hash].get(value);
        return retValue;

    }

    /**
     * Rehash the current table to a table of a size 2 times greater
     * If the load with the new size is > 0.7 dont rehash
     * Otherwise, create a temporary array to hold current values, create a new array of size newCapacity
     * and rehash all of the nodes
     * @param newCapacity the size of the new table
     * @return boolean, true if successfully rehashed, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean rehash(int newCapacity) {
        // rehash to a larger table size (specified as the
        // parameter to this method)
        // O(M) where M = size returned by size()

        // - return true if table gets resized
        // - if the newCapacity will make the load to be more than 0.7, do not resize
        //   and return false
        int curSize = this.size; //temp variable
        int curChain = this.numChain; //temp variable
        this.size = 0; //change fields to 0
        this.numChain = 0;
        boolean ret = false; //status of rehashing
        if (((double)curSize / newCapacity) > 0.7) { //if the load of the newCapacity is > 0.7 dont rehash
            ret = false;
        }
        else {
            this.tbSize = newCapacity; //change tbSize to newCapacity
            SimpleList<T> [] temp = this.table; //temporary array to hold previous array
            this.table = new SimpleList[newCapacity]; //table is a new table with new capacity
            for (int i = 0; i < newCapacity; i++) { //initialize all of the indices of table
                this.table[i] = new SimpleList<>();
            }
            for (SimpleList<T> node : temp) { //for each node in temp
                Iterator i = node.iterator(); //iterator to make traversing simpler
                while (i.hasNext()) { //if there is a next node add
                    add((T)i.next());
                }
            }
            ret = true;
        }
        if (!ret) { //if false return all fields to original values
            this.size = curSize;
            this.numChain = curChain;
        }
        return ret;
    }

    /**
     * Return the number of enteries (size) in the hash table
     * @return this.size
     */
    public int size() {
        // return the number of items in the table
        // O(1)
        return this.size;
    }

    /**
     * Return the load of the hash table
     * Calculation: # of enteries (size) divided by the size of the hashtable (tbSize)
     * @return this.load
     */
    public double getLoad() {
        // return the load on the table
        // O(1)
        this.load = (double)this.size/this.tbSize;
        return this.load;
    }

    /**
     * Return the average chain length of the hash table
     * Calculation: # of elements (size) divided by # of chains (num chains)
     * @return this.avgChain;
     */
    public double getAvgChainLength(){
        // return the average length of non-empty chains in the hash table
        // O(1)
        this.avgChain = this.size/this.numChain;
        return this.avgChain;
    }

    /**
     * Convert the hashtable to a 1D array.
     * Go through all of the linked lists in each of the hashValues and add them to the array that will be returned.
     * The arrays size will be size (number of elements) to ensure no empty spaces.
     * @return the object array
     */
    public Object[] valuesToArray() {
        // take all the values in the table and put them
        // into an array (the array should be the same
        // size returned by the size() method -- no extra space!).
        // Note: it doesn't matter what order the items are
        // returned in, this is a set rather than a list.

        // O(M) where M = size returned by size()
        Object [] retValue = new Object [size()];
        int j = 0;
        for (int i = 0; i < this.table.length; i++) {
            Iterator it = this.table[i].iterator();
            while(it.hasNext()) {
                retValue[j] = it.next();
                j++;
            }
        }
        return retValue;
    }

    // inefficiently finds the next prime number >= x
    // this is written for you
    public int nextPrime(int x) {
        while(true) {
            boolean isPrime = true;
            for(int i = 2; i <= Math.sqrt(x); i++) {
                if(x % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if(isPrime) return x;
            x++;
        }
    }

    //------------------------------------
    // example test code... edit this as much as you want!
    public static void main(String[] args) {
        HashTable<String> names = new HashTable<>();

        if(names.add("Alice") && names.add("Bob") && !names.add("Alice")
                && names.size() == 2 && names.getAvgChainLength() == 1) 	{
            System.out.println("Yay 1");
        }

        if(names.remove("Bob") && names.contains("Alice") && !names.contains("Bob")
                && names.valuesToArray()[0].equals("Alice")) {
            System.out.println("Yay 2");
        }

        boolean loadOk = true;
        if(names.getLoad() != 1/11.0 || !names.rehash(10) || names.getLoad() != 1/10.0 || names.rehash(1)) {
            loadOk = false;
        }

        boolean avgOk = true;
        HashTable<Integer> nums = new HashTable<>();
        for(int i = 1; i <= 70 && avgOk; i++) {
            nums.add(i);
            double avg = nums.getAvgChainLength();
            if(avg> 1.2 || (i < 12 && avg != 1) || (i >= 14 && i <= 23 && avg != 1) ||
                    (i >= 28 && i <= 47 && avg != 1) || (i >= 57 && i <= 70 && avg!= 1)) {
                avgOk = false;
            }

        }
        if(loadOk && avgOk) {
            System.out.println("Yay 3");

        }

        names.add("James"); names.add("Lester");
        if (names.contains("James") && names.contains("Lester")) {
            System.out.println("Yay 4");
        }

    }
}