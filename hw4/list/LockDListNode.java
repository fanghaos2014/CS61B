package list;

public class LockDListNode extends DListNode{

  protected boolean lock;

  LockDListNode(Object i, LockDListNode p, LockDListNode n) {
	    super(i,p,n);
	    lock=false;
  }

}