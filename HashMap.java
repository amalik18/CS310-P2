// This class has been implemented for you!
// Do not change it!

import java.util.*;

class HashMap<K,V> {
    // This class defines a dictionary that maps key->value.
    // A hash table is used that decides the entry based on key
    // and stores <key, value> pair in the table entry.

    // keys in a dictionary must be unique.

    private HashTable<Pair<K,V>> hashTable = new HashTable<>();


    // The class representing <key,value> pair
    private static class Pair<K,V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            // return true if two pairs have matching keys
            // i.e. <"Alice", 1> is considered as equal to <"Alice", 2>

            if(o instanceof Pair) {
                Pair<K,V> pair = (Pair<K,V>)o;
                return pair.key.equals(key);
            }
            return false;
        }

        public int hashCode() {
            // In order to make sure that keys in a dictionary must be unique,
            // hash code is only determined by key.hashCode().

            return key.hashCode();
        }

        public String toString() {
            return "<" + key + "," + value + ">";
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }


    public boolean add(K key, V value) {
        // add a new <key, value> pair into the dictionary
        // return false if cannot be added (i.e. key already there)
        Pair<K,V> pair = new Pair<>(key, value);
        return hashTable.add(pair);
    }

    public boolean update(K key, V value) {
        // update the mapping of key in the dictionary to be a new value
        // if key not present in dictionary, return false
        // if fail to update, return false
        Pair<K,V> pair = new Pair<>(key, value);
        if(!remove(key)) {
            return false;
        }
        return hashTable.add(pair);
    }

    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        // remove <key, value> pair from dictionary and return true
        // if key not present, return false and no change to dictionary
        Pair<K,V> pair = new Pair<>(key, null);
        return hashTable.remove(pair);
    }


    public int size() {
        // return how many <key, value> pairs are there
        return hashTable.size();
    }

    public boolean contains(K key){
        // return true if key is present
        // return false otherwise
        return hashTable.contains(new Pair<>(key,null));
    }

    public boolean has(K key, V value){
        // return true if <key, value> pair is present in dictionary
        // return false otherwise
        Pair<K, V> pair = new Pair<>(key, null);
        return this.contains(key) && (hashTable.get(pair)).getValue().equals(value);

    }

    public V getValue(K key){
        // return the value this key mapped to from the dictionary
        // if key not present, return null
        if (this.contains(key)){
            Pair<K, V> pair = new Pair<>(key, null);
            return hashTable.get(pair).getValue();
        }
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public Object[] pairsToArray() {
        // return an object array as the contents of the dictionary
        // use the valueToArray() of hash table
        // every item in the array should be a <key,value> pair

        Object[] hashTableValues = hashTable.valuesToArray();
        Object[] arr = new Object[hashTableValues.length];


        for(int i = 0; i < arr.length; i++) {
            arr[i] = ((Pair<K,V>)hashTableValues[i]);
        }

        return arr;
    }

    //-----------------------------------------------
    // example testing code... edit this as much as you want!
    // This is actually more testing of HashTable
    public static void main(String[] args) {

        HashMap<String,Integer> nameDict = new HashMap<>();
        if ( nameDict.add("Alice",1) && nameDict.contains("Alice")
                && !nameDict.contains("Bob") && nameDict.size()==1){
            System.out.println("Yay 1!");
        }

        if ( nameDict.has("Alice", 1) && !nameDict.has("Alice", 2) &&
                nameDict.update("Alice", 10) && !nameDict.update("Bob",2)){
            System.out.println("Yay 2!");
        }

        if ( nameDict.getValue("Alice")==10 && nameDict.add("Chris", 20) &&
                nameDict.remove("Chris") && !nameDict.has("Chris",20)){
            System.out.println("Yay 3!");
        }

        nameDict.add("David",32);
        nameDict.add("Eddie",1);
        nameDict.add("Frank",2);
        if (nameDict.contains("David") && nameDict.contains("Eddie") && nameDict.contains("Frank")) {
            System.out.println("Yay 4");
        }
        System.out.println(Arrays.toString(nameDict.pairsToArray()));


    }

}