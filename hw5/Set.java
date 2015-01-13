/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
  protected List setlist;

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    setlist=new DList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    return setlist.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
/*    if (c==null){
      return;
    } 
    if (setlist.isEmpty()){
      setlist.insertFront(c);
    } else {
      ListNode holder=setlist.front();
      try {
        int a=0;
        while ((a==c.compareTo(holder.item())==true)) {
          if (a<0) {
            holder.insertBefore(c);
            return;
          }
          holder=holder.next();
          if (!holder.isValidNode()) {
            setlist.insertBack(c);
            return;
          }
        }
      } catch (InvalidNodeException exception) {
      }
    }
  }*/
  if(setlist.isEmpty()) {  
    setlist.insertFront(c);
  } else {
    tobegin:
    try{
      ListNode holder = setlist.front();
      while(holder.isValidNode()) {
        int comparison = ((Comparable) holder.item()).compareTo(c);
        if(comparison==0) {
          break tobegin;
        } else if(comparison < 0) {
          holder = holder.next();
        } else {
          holder.insertBefore(c);
          break tobegin;
        }
      }
      setlist.insertBack(c);
    } catch(InvalidNodeException e) {
      System.out.println(e);
    }
  }
}
  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) {
    /*if (s.cardinality() == 0 || s==null) {
      return;
    }
    ListNode h1=setlist.front();
    ListNode h2=s.setlist.front();
    Comparable c;
    Comparable d;
    int a;
    while (h2.isValidNode()) {
      c=null;
      d=null;
      a=0;
    }
    try {
      d=(Comparable) h2.item();
      c=(Comparable) h1.item();
    } catch (InvalidNodeException e1) {
      return;
}
try {
  do {
    this.setlist.insertBack(h2.item());
    h2=h2.next();
  }
  while(h2.isValidNode());
} catch (InvalidNodeException e2) {
  
}
return;
}

a=d.compareTo(c);
try {
  if (a>0) {
    h1=h1.next();
  } else if (a<0) {
    h1.insertBefore(d);
    h2=h2.next();
  } else {
    h1=h1.next();
    h2=h2.next();
  }
} catch (InvalidNodeException e3) {

}*/

ListNode n1 = s.setlist.front();
ListNode n2 = setlist.front();
try {
  for(int i=0; i<s.cardinality(); i++) {
    loop: {        
      while(n2.isValidNode()) {
        int comparison = ((Comparable) n1.item()).compareTo(n2.item());
        if(comparison==0) {
          n1 = n1.next();
          break loop;
        } else if(comparison > 0) {
          n2 = n2.next();
        } else {
          n2.insertBefore(n1.item());
          n1 = n1.next();
          break loop;
        }
      }
      setlist.insertBack(n1.item());
    }
  }
} catch (InvalidNodeException e) {
  System.out.println(e);
}
}

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
    ListNode n1 = s.setlist.front();
    ListNode n2 = setlist.front();
    try {
      for(int i=0; i<this.cardinality(); i++) {
        loop: {        
          while(n2.isValidNode()) {
            int comparison = ((Comparable) n2.item()).compareTo(n1.item());
            if(comparison==0) {
              n2 = n2.next();
              break loop;
            } else if(comparison > 0) {
              n1 = n1.next();
            } else {
              ListNode holder=n2;
              n2 = n2.next();
              holder.remove();
              break loop;
            }
          }
          
        }
      }
    } catch (InvalidNodeException e) {
      System.out.println(e);
    }
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {

    ListNode h1=setlist.front();
    String string="{  ";
    while (h1.isValidNode()) {
      try {
        /*      String string=string+h1.item()+"  ";*/
        string=string+h1.item()+"  ";
        h1=h1.next();
      } catch(InvalidNodeException e1) {

      }
    }
    string=string+"}";
    return string;
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
