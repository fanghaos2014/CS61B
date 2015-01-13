/* DList1.java */

/**
 *  A DList1 is a mutable doubly-linked list.  (No sentinel, not
 *  circularly linked.)
 */

public class DList1 {

  /**
   *  head references the first node.
   *  tail references the last node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode1 head;
  protected DListNode1 tail;
  protected long size;
  protected int[] item;

  /* DList1 invariants:
   *  1)  head.prev == null.
   *  2)  tail.next == null.
   *  3)  For any DListNode1 x in a DList, if x.next == y and x.next != null,
   *      then y.prev == x.
   *  4)  For any DListNode1 x in a DList, if x.prev == y and x.prev != null,
   *      then y.next == x.
   *  5)  The tail can be accessed from the head by a sequence of "next"
   *      references.
   *  6)  size is the number of DListNode1s that can be accessed from the
   *      head by a sequence of "next" references.
   */

  /**
   *  DList1() constructor for an empty DList1.
   */
  public DList1() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   *  DList1() constructor for a one-node DList1.
   */
  public DList1(int[] a) {
    head = new DListNode1();
    tail = head;
    head.item = a;
    size = 1;
  }

  /**
   *  DList1() constructor for a two-node DList1.
   */
  public DList1(int a[], int b[]) {
    head = new DListNode1();
    head.item = a;
    tail = new DListNode1();
    tail.item = b;
    head.next = tail;
    tail.prev = head;
    size = 2;
  }

  /**
   *  insertFront() inserts an item at the front of a DList1.
   */
  public void insertFront(int i[]) {
      DListNode1 dummy=new DListNode1(i);


      if (head==null) {
          head=dummy;
          tail=head;
      
      } else {
          head.prev=dummy;
          dummy.next=head;          
          head=dummy;
         
      }
      size++;

  }

  /**
   *  removeFront() removes the first item (and node) from a DList1.  If the
   *  list is empty, do nothing.
   */
  public void removeFront() {

      if (head==null) {
          return;
      } else if(head.next==null) {
          head=null;
          tail=head;
      }else {
          DListNode1 dummy=head.next;
              dummy.prev=null;
              head = dummy;
              tail=head.next;
      }
      size--;
      
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode1 current = head;
    while (current != null) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

  public static void main(String[] args) {
    }
}
