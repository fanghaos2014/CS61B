/* MachinePlayer.java */
package player;
import list.*;


/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  protected int searchDepth;
  public int color; // make this proteced  later!!
  public GameBoard gameBoard;  //make this protected later!!!
  protected GameTree gameTree = new GameTree();

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color=color;
    searchDepth=1;
    gameBoard=new GameBoard();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this.color=color;
    this.searchDepth=searchDepth;
    gameBoard=new GameBoard();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    GameTree tree= gameTree.minmaxTree(gameBoard,gameBoard.workingMoves(color),color,true,Integer.MIN_VALUE,Integer.MAX_VALUE,searchDepth);
    gameBoard.insert(color,tree.move);
    return tree.move;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if(gameBoard.isValidMove(m, otherColor(color))) {
      gameBoard.insert(otherColor(color), m);
      return true;
    }
    return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    if(gameBoard.isValidMove(m,color)) {
      gameBoard.insert(color,m);
      return true;
    }
    System.out.println("bad move");
    return false;
  }

  private int otherColor(int color) {
    if(color == 1) {
      return 0;
    } else if (color == 0) {
      return 1;
    } else {
      return 123;
    }
  }

}
