package player;
import list.*;



public class GameTree{

	protected final static boolean COMPUTER=true;
	protected final static boolean PLAYER=false;
	protected int score;
	protected Move move;
	protected int chip=0;

	public GameTree() {
		move=new Move();
		score=0;
	}


	/*uses the board and the working moves to choose the best possible move*/
	public GameTree minmaxTree(GameBoard board, DList list_of_moves, int color, boolean turn, int alpha, int beta, int searchDepth) {
		GameTree bestComputer=this;
		GameTree myBest=new GameTree();
		DList working=board.workingMoves(color);
	
		int initialColor=color;
		if (board.network(color)) { /*figure out the color*/
//			System.out.println("yo");
			return myBest;
		}

		if (turn==COMPUTER) {
//			System.out.println("What's up");
			myBest.score=alpha;
		} else {
//			System.out.println("beta");
			myBest.score=beta;
		}
		try {
			if(working.front().item() != null) {
				ListNode node=working.front();
				myBest.move=new Move(1,1);
				for (int i=0; i<working.length(); i++) {
					Move m1=(Move) node.item();
					board.insert(color,m1);

					int c=0;
					if (color==0) {
						c=1;
					} else if (color==1) {
						c=0;
					}
					int j=searchDepth;
					if (j>0) {
						bestComputer=this.minmaxTree(board,working,c,!turn,alpha,beta,j-1);
					}

					// System.out.println("all" + board.chips);
				if (m1.moveKind == 1) {
					bestComputer.score=board.evaluate(c, m1);
					board.remove(m1.x1,m1.y1);
				} else if (m1.moveKind == 2) {
					Move m2=new Move(m1.x2,m1.y2,m1.x1,m1.y1);
					board.insert(color,m2);
					
					// board.stringRep();
				}
					if (turn==COMPUTER && bestComputer.score>myBest.score) {
						myBest.move=m1;
						myBest.score=bestComputer.score;
						alpha=bestComputer.score;
					} else if (turn==PLAYER && bestComputer.score<myBest.score) {
						myBest.move=m1;
						myBest.score=bestComputer.score;
						beta=bestComputer.score;
					}
					node=node.next();
					if (alpha>=beta) {
						return myBest;
					}
				} 
			}	
		} catch (InvalidNodeException e) {
			System.out.println(e);
		}
		
		return myBest;

	}




	public static void main(String args[]) {
		GameBoard gb=new GameBoard();
		GameTree gameT=new GameTree();
		int color=0;
		DList list_of_moves=gb.workingMoves(color);
		System.out.println("This is the working list length: "+list_of_moves.length());
		
		Move m1=new Move(1,3);
		gb.insert(1,m1);

		boolean turn=true;
		int alpha=Integer.MIN_VALUE;
		int beta=Integer.MAX_VALUE;
		int searchDepth=1;
		GameTree gt=gameT.minmaxTree(gb, list_of_moves, color, turn, alpha, beta, searchDepth);
		int x=gt.move.x1;
		int y=gt.move.y1;
		gb.insert(color,gt.move);
		System.out.println("MinmaxTree1: ["+x+", "+y+"]");

		GameTree gt1=gameT.minmaxTree(gb, list_of_moves, color, turn, alpha, beta, searchDepth);
		int x1=gt1.move.x1;
		int y1=gt1.move.y1;
		gb.insert(color,gt1.move);
		System.out.println("["+x1+", "+y1+"]");



		gb.stringRep();

		Move m11=new Move(0,7);
		if (gb.isValidMove(m11,color)) {
			System.out.println("true");
		} else {
			System.out.println("false");
			}
		
	}
	

}