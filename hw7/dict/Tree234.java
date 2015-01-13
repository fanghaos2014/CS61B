/* Tree234.java */

package dict;

/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   *
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   **/
  Tree234Node root;

  /**
   *  Tree234() constructs an empty 2-3-4 tree.
   *
   *  You may change this constructor, but you may not change the fact that
   *  an empty Tree234 contains no nodes.
   */
  public Tree234() {
    root = null;
    size = 0;
  }

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.  The test code depends on it.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      /* Most of the work is done by Tree234Node.toString(). */
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      /* Most of the work is done by Tree234Node.printSubtree(). */
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *  inserts into the lowest level
   *  @param key is the key sought.
   **/
  public void insert(int key) {
    if (size==0) {
      Tree234Node n=new Tree234Node(null, key);
      size++;
      root=n;
    } else {
      Tree234Node n=root;
      while (n!=null) {
        if (n.keys==3) {
         if (n.key1==key || n.key2==key || n.key3==key) {
          return;
        } else if (n.parent==null) {
          Tree234Node middleKey=new Tree234Node(null, n.key2);
          Tree234Node firstKey=new Tree234Node(middleKey, n.key1);
          Tree234Node secondKey=new Tree234Node(middleKey, n.key3);

          root=middleKey;

          middleKey.child1=firstKey;
          middleKey.child2=secondKey;
          firstKey.child1=n.child1;
          firstKey.child2=n.child2;
          secondKey.child1=n.child3;
          secondKey.child2=n.child4;

          if (n.child1!=null) {
            firstKey.child1.parent=firstKey;
            firstKey.child2.parent=firstKey;
          }
          if (n.child2!=null) {
            secondKey.child1.parent=secondKey;
            secondKey.child2.parent=secondKey;
          }

          middleKey.child2.parent=middleKey;
          n=middleKey;

        } else if (n.parent.keys==1) {
          n.parent.keys++;
          Tree234Node firstKey=new Tree234Node(n.parent, n.key1);
          Tree234Node secondKey=new Tree234Node(n.parent, n.key3);

          firstKey.child1=n.child1;
          firstKey.child2=n.child2;
          secondKey.child1=n.child3;
          secondKey.child2=n.child4;

          if (n.child1!=null) {
            firstKey.child1.parent=firstKey;
            firstKey.child2.parent=firstKey;
          }
          if (n.child2!=null) {
            secondKey.child1.parent=secondKey;
            secondKey.child2.parent=secondKey;
          }

          if (n.parent.key1<n.key2) {
            n.parent.key2=n.key2;
            n.parent.child2=firstKey;
            n.parent.child3=secondKey;
          } else if (n.parent.key1>n.key2) {
            n.parent.key2=n.parent.key1;
            n.parent.key1=n.key2;
            n.parent.child3=n.parent.child2;
            n.parent.child1=firstKey;
            n.parent.child2=secondKey;
            n.key2=n.key3;
            n.keys++;
          }

          n=n.parent;

        } else if (n.parent.keys==2) {
          n.parent.keys++;
          Tree234Node firstKey=new Tree234Node(n.parent, n.key1);
          Tree234Node secondKey=new Tree234Node(n.parent, n.key3);
          firstKey.child1=n.child1;
          firstKey.child2=n.child2;
          secondKey.child1=n.child3;
          secondKey.child2=n.child4;
          if (n.child1!=null) {
            firstKey.child1.parent=firstKey;
            firstKey.child2.parent=firstKey;
          }
          if (n.child2!=null) {
            secondKey.child1.parent=secondKey;
            secondKey.child2.parent=secondKey;
          }
          if (n.parent.key1>n.key2) {
            n.parent.key3=n.parent.key2;
            n.parent.key2=n.parent.key1;
            n.parent.key1=n.key2;
            n.parent.child4=n.parent.child3;
            n.parent.child3=n.parent.child2;
            n.parent.child2=secondKey;
            n.parent.child1=firstKey;
          } else if (n.parent.key1<n.key2 && n.parent.key2>n.key2) {
            n.parent.key3=n.parent.key2;
            n.parent.key2=n.key2;
            n.parent.child4=n.parent.child3;
            n.parent.child3=secondKey;
            n.parent.child2=firstKey;
          } else if (n.parent.key2<n.key2) {
            n.parent.key3=n.key2;
            n.parent.child3=firstKey;
            n.parent.child4=secondKey;
          }

          n=n.parent;

        }
      }

      if (n.child1==null && n.key1!=key && n.key2!=key && n.keys<3) {
        if (n.keys==1) {
          n.keys++;
          if (key<n.key1) {
            n.key2=n.key1;
            n.key1=key;
          } else {
            n.key2=key;
          }
        } else if (n.keys==2) {
          n.keys++;
          if (key<n.key1) {
            n.key3=n.key2;
            n.key2=n.key1;
            n.key1=key;
          }
          else if (key>n.key1 && key<n.key2) {
            n.key3=n.key2;
            n.key2=key;
          } else {
            n.key3=key;
          }
        }
        size++;
        return;
      }
      if (key<n.key1) {
        n=n.child1;
      } else if (key==n.key1) {
        break;
      } else if ((n.keys==1) || (key<n.key2)) {
        n=n.child2;
      } else if (key==n.key2) {
        break;
      } else if ((n.keys==2) || (key<n.key3)) {
        n=n.child3;
      } else if (key==n.key3) {
        break;
      } else {
        n=n.child4;
      }
    }
  }
}

  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
     "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
     "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
     "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
     "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}
