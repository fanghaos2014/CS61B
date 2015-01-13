import player.*;
import list.*;


public class test{

	public static void main(String[] args) {
		MachinePlayer player = new MachinePlayer(0);
		Move m1=new Move(1,0);
		Move m2=new Move(0,1);
		Move m3=new Move(3,0);
		Move m4=new Move(2,1);
		Move m5=new Move(5,0);
		Move m6=new Move(4,1);
		Move m7=new Move(1,2);
		Move m8=new Move(0,3);
		Move m9=new Move(3,2);
		Move m10=new Move(2,3);
		Move m11=new Move(5,2);
		Move m12=new Move(4,3);
		Move m13=new Move(1,4);
		Move m14=new Move(0,5);
		Move m15=new Move(3,4);
		Move m16=new Move(2,5);
		Move m17=new Move(5,4);
		Move m18=new Move(4,5);
		Move m19=new Move(5,6);
		Move m20=new Move(6,5);

		// player.forceMove(m1);
		// player.opponentMove(m2);
		// player.forceMove(m3);
		// player.opponentMove(m4);
		// player.forceMove(m5);
		// player.opponentMove(m6);
		// player.forceMove(m7);
		// player.opponentMove(m8);
		// player.forceMove(m9);
		// player.opponentMove(m10);
		// player.forceMove(m11);
		// player.opponentMove(m12);
		// player.forceMove(m13);
		// player.opponentMove(m14);
		// player.forceMove(m15);
		// player.opponentMove(m16);
		// player.forceMove(m17);
		// player.opponentMove(m18);
		// player.forceMove(m19);
		// player.opponentMove(m20);

		// player.chooseMove();
		player.opponentMove(m2);
		// player.chooseMove();
		player.opponentMove(m4);
		// player.chooseMove();
		player.opponentMove(m6);
		// player.chooseMove();
		player.opponentMove(m8);
		// player.chooseMove();
		player.opponentMove(m10);
		// player.chooseMove();
		player.opponentMove(m12);
		// player.chooseMove();
		player.opponentMove(m14);
		// player.chooseMove();
		player.opponentMove(m16);
		// player.chooseMove();
		player.opponentMove(m18);
		// player.chooseMove();
		player.opponentMove(m20);


		// Move m1=new Move(6,0);
		// Move m2=new Move(6,4);
		// Move m3=new Move(4,4);
		// Move m4=new Move(6,6);
		// Move m5=new Move(6,7);
		// Move m6=new Move(4,6);

		// player.forceMove(m1);
		// player.forceMove(m2);
		// player.forceMove(m3);
		// player.forceMove(m4);
		// player.forceMove(m5);
		// player.forceMove(m6);


		player.gameBoard.stringRep();

		Move mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());
		mm1 = player.chooseMove();
		System.out.println(mm1.toString());

		
		// System.out.println(player.gameBoard.network(player.color));

/*		DList list = player.gameBoard.workingMoves(player.color);
		System.out.println(list);
*/
		
		// player.gameBoard.stringRep();

		// Move m21 = player.chooseMove();
		// System.out.println(m21.toString());
		//System.out.println(m21.x1 + "," + m21.y1 + "," + m21.x2 + "," + m21.y2);

		// int[][] array = player.gameBoard.coordinates(1);
		// player.gameBoard.arrayToString(array);
		player.gameBoard.stringRep();

		


		// GameTree testgametree = new GameTree();
		// testgametree.minmaxTree(board, board.workingMoves(0),0,false,Integer.MIN_VALUE,Integer.MAX_VALUE,4);
 		




		// System.out.println(someMove.x1 + " " + someMove.y1);
	}

}