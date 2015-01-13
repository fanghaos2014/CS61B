package list;

public class LockDList extends DList {

	public LockDList() {
	}

	public void lockNode(DListNode node) {
		((LockDListNode) node).lock=true;
	}

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, (LockDListNode) prev, (LockDListNode) next);
	}

	public void remove(DListNode node) {

		if (node!=null && ((LockDListNode) node).lock==false) {
			super.remove(node);
		}
	}

	public static void main(String[] args) {
		LockDList abc=new LockDList();
		abc.insertFront(1);
		System.out.println("Insert front 1        "+abc.toString());

		abc.insertFront(10);
		System.out.println("Insert front 10        "+abc.toString());

		abc.insertFront(100);
		System.out.println("Insert front 100        "+abc.toString());

		abc.insertBack(5);
		System.out.println("Insert back 5        "+abc.toString());

		abc.insertBack(50);
		System.out.println("Insert back 50        "+abc.toString());

		System.out.println("Return front        "+(abc.front()).item.toString());

		System.out.println("Return back        "+(abc.back()).item.toString());
		DListNode a=abc.head.next.next;
		System.out.println("Return next        "+abc.next(a).item.toString());

		System.out.println("Return prev        "+abc.prev(a).item.toString());

		abc.insertAfter(7,a);
		System.out.println("InsertAfter        "+abc.toString());
		abc.insertBefore(17,a);
		System.out.println("InsertBefore        "+abc.toString());
		abc.lockNode(a);
		abc.remove(a);
		System.out.println("Remove        "+abc.toString());

	}
}