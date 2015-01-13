/* HashTableChained.java */
package dict;

import list.*;



/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  public DList[] hashtable;
  public int size = 0;
  public int collisions = 0;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public static boolean isPrime(int n) {
      int divisor = 2;
      while (divisor < n) {       
        if (n % divisor == 0) {     
          return false;           
        }                          
        divisor++;                  
      }
      return true;
    }

  public HashTableChained(int sizeEstimate) {
    int x = sizeEstimate;
    if(isPrime(sizeEstimate)) {
      this.hashtable = new DList[sizeEstimate];
    } else {
      while(isPrime(x) == false) {
        x++;
      }
      this.hashtable = new DList[x];
      this.size = x;
    }
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    this.hashtable = new DList[101];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  protected int compFunction(int code) {
    if(code < 0){
      code = code * -1;
    }
    return code % hashtable.length;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return this.size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if(size == 0) {
        return true;
      } else {
      return false;
    }
    } 

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    int code = key.hashCode();
    int index = compFunction(code);
    Entry entry = new Entry();
    entry.key = key;
    entry.value = value;
    if(hashtable[index] == null) {
      hashtable[index] = new DList();
      hashtable[index].insertFront(entry);
    } else {
      hashtable[index].insertBack(entry);
      collisions++;
    }
    size++;
    return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    try{
    int code = key.hashCode();
    int index = compFunction(code);
    DList item = hashtable[index];
      if(item != null) {
        DListNode node = (DListNode) item.front();
        while(node.isValidNode()) {
          if(key.equals(((Entry) node.item()).key())) {
            return ((Entry) node.item());
          } else if(node.next() != null) {
            node = (DListNode) node.next();
          }
        }
        return null;
      } else {
        return null;
      }

    }
    catch (InvalidNodeException e1) {
      return null;
    }
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    
    int code = key.hashCode();
    int index = compFunction(code);
    DList item = hashtable[index];
    try{
    if(item != null) {
        DListNode node = (DListNode) item.front();
        while(node.isValidNode()) {
          if(key.equals(((Entry) node.item()).key())) {
            Entry remove = (Entry) node.item();
            node.remove();
            size--;
            return ((Entry) remove);
          } else if(node.next() != null) {
            node = (DListNode) node.next();
          }
        }
        return null;
      } else {
        return null;
      }

    }
    catch (InvalidNodeException e1) {
      return null;
    }
  }


  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    DList[] newtable = new DList[hashtable.length];
    hashtable = newtable;
    size = 0; 
    collisions = 0;
  }



public String toString(){
   // String result = "[ ";
   for(int i=0; i < (hashtable.length); i++){
      if(hashtable[i] == null) {
        // result += "0" + " --> ";
      } else {
        System.out.println(hashtable[i]);
       //result += hashtable[i].length() + " --> ";
      }
   }
   //return result + " ]" + " & collisions: " + collisions;
   return "collisions: " + collisions;
 }

} 
