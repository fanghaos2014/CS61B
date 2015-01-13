package player;
import list.*;


public class GameBoard{

	protected int[][] board;
	protected int dimension=8;
	protected int blackChips=0;
	protected int whiteChips=0;
	protected int chips=0;

	public GameBoard() {
		board=new int[dimension][dimension];
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				board[i][j]=2;
			}
		}
	}

	public int color(int x, int y) {
		return board[x][y];
	}

	public void insert(int color, int x, int y) {
		board[x][y]=color;
		if (color==0) {
			blackChips++;
		} else if (color==1) {
			whiteChips++;
		}
		chips++;
	}

	public void insert(int color, Move move) {
		board[move.x1][move.y1]=color;
		if(move.moveKind == 2) {
			// System.out.println("insert movekind 2");
			board[move.x2][move.y2] = 2;
			if (color==0) {
				blackChips--;
			} else if (color==1) {
			whiteChips--;
			}
			chips--;
		}
		if (color==0) {
			blackChips++;
		} else if (color==1) {
			whiteChips++;
		}
		chips++;
	}

	public void remove(int x, int y) {
		int color=board[x][y];
		if (color==0) {
			blackChips--;
		} else if (color==1) {
			whiteChips--;
		}
		chips--;
		board[x][y] = 2;
	}

	//finds connections for any given piece on the board for a certain color. 
	private int[][] connections(int x, int y, int color) {
		int[][] connectionlist = new int[8][2];
		int xcounter = 0;
		int ycounter = 0;
		for(int i = 0; i < 8; i++) {
			switch(i) {
				case 0:
					xcounter = x-1;              //each case for each direction. case 0 is leftup, case 1 is up, case 2 is rightup.. and so on in clockwise direction
					ycounter = y-1;
					break;
					case 1:
					xcounter = x;
					ycounter = y-1;
					break;
					case 2:
					xcounter = x+1;
					ycounter = y-1;
					break;
					case 3:
					xcounter = x+1;
					ycounter = y;
					break;
					case 4:
					xcounter = x+1;
					ycounter = y+1;
					break;
					case 5:
					xcounter = x;
					ycounter = y+1;
					break;
					case 6:
					xcounter = x-1;
					ycounter = y+1;
					break;
					case 7:
					xcounter = x-1;
					ycounter = y;
					break;
				}			
				int counter = i;
				
				while(validCell(xcounter, ycounter, color)) {
					if(board[xcounter][ycounter] == oppositeColor(color)) {
						break;
					} else if(board[xcounter][ycounter] == color) {
						connectionlist[counter][0] = xcounter;
						connectionlist[counter][1] = ycounter;
						break;
						
					} else if(board[xcounter][ycounter] == 2) {
						switch(i) {
							case 0:
							xcounter -= 1;              //each case for each direction. case 0 is leftup, case 1 is up, case 2 is rightup.. and so on in clockwise direction
							ycounter -= 1;
							break;
							case 1:
							ycounter -= 1;
							break;
							case 2:
							xcounter += 1;
							ycounter -= 1;
							break;
							case 3:
							xcounter += 1;
							break;
							case 4:
							xcounter += 1;
							ycounter += 1;
							break;
							case 5:
							ycounter += 1;
							break;
							case 6:
							xcounter -= 1;
							ycounter += 1;
							break;
							case 7:
							xcounter -= 1;
							break;
						}
					}

				}
			
			}
			return connectionlist;
		}


	// helper function to change color to opposite color

	private int oppositeColor(int color) {
		if(color == 1) {
			return 0;
		} else if (color == 0) {
			return 1;
		} else {
			return 123;
		}
	}

	// helper function for connections; tests to see if a certain box exists 

	private boolean validCell(int x, int y, int color) {
		if(color == 0) {
			if(x <= 0 || x >= 7 || y < 0 || y > 7) {
				return false;
			} else {
				return true;
			}
		} else if(color == 1) {
			if(y <= 0 || y >= 7 || x < 0 || x > 7) {
				return false;
			} else { 
				return true;
			}
		} else {
			return false;
		}
	}




	// given a gameboard, find out if a network exists. sets up the DList and other information 
	// necessary to run the check

	public boolean network(int color) {
		DList networklist = new DList();
		DList startnodes = new DList();
		try {
			if(color == 0) {
				for(int i = 1; i < 7; i++) {
					if(board[i][0] == 0) {
						int[] x = new int[3];
						x[0] = i;
						x[1] = 0;
						startnodes.insertBack(x);
					}
				}
			} else if(color == 1) {
				for(int i = 1; i < 7; i++) {
					if(board[0][i] == 1) {
						int[] x = new int[3];
						x[0] = 0;
						x[1] = i;
						startnodes.insertBack(x);
					}
				}
			} else {
				System.out.println("not a valid color");
				return false;
			}
			if(startnodes.length() == 0) {
				// System.out.println("length = 0");
				return false;
			}
			DListNode checknode = (DListNode) startnodes.front();
			boolean net = false;
			for(int i = 0; i < startnodes.length(); i++) {
				networklist.insertFront(checknode.item());
				net = networkHelper(networklist, color);
				if(net == true) {
					return net;
				} else {
					networklist = new DList();
					if(checknode.next().isValidNode()) {
						checknode=(DListNode) checknode.next();
					} else {
						continue;
					}
				}
			}
			return false;
		} catch (InvalidNodeException e) {
			System.out.println("error in network");
			System.out.println(e);
			return false;
		}
	}

	// helper function for network. does the majority of the recursive work. 
	// only network should ever be using this method

	private boolean networkHelper(DList networklist, int color) {
		/*System.out.println(networklist.length());*/
		boolean state = false;
		DListNode holder = (DListNode) networklist.back();
		try{
			int[] arrayHolder=(int[]) holder.item();
			/*System.out.println("Hi");*/
			int[][] connects = connections(arrayHolder[0], arrayHolder[1], color);
			if(networklist.length() >= 6 && inGoal(arrayHolder[0], arrayHolder[1])) {
				/*System.out.println("Should return true");*/
				return true;		
			} else if(networklist.length() <= 6 && inGoal(arrayHolder[0], arrayHolder[1])) {
				return false;
			} else {
				/*System.out.println("Sup");*/
				//arrayToString(connects);
				for(int i = 0; i < 8; i++) {
					/*System.out.println("Yo");*/
					// System.out.println(connects[i][0] + " " + connects[i][1]);
					// System.out.println(connectionValid(networklist, i, connects[i][0], connects[i][1], color));
					if(connectionValid(networklist, i, connects[i][0], connects[i][1], color)) {
						/*System.out.println("Hola");*/
						int[] node = new int[3];
						node[0] = connects[i][0];
						node[1] = connects[i][1];
						node[2] = i;
						networklist.insertBack(node);
						//arrayToString(networkToArray(networklist));
						state = networkHelper(networklist, color);
						if(state == true) {
							/*System.out.println("state is true");*/
							return state;
						} else {
							/*System.out.println("removed");*/
							networklist.back().remove();
							
						}
					}
				}
				return false;
			}
		} catch (InvalidNodeException e) {
			System.out.println("sup");
			System.out.println(e);
		}
		return false;
	}

	// checks to see if node is in end goal

	private boolean inGoal(int x, int y) {
		if(x == 7 || y == 7) {
			return true;
		} else {
			return false;
		}
	}


	// checks to see if the connection goes back into the start goal
	// or if the connection does not change direcion.
	// in addition, since the check in only done when length of network is less than 6,
	// a connection will not be valid if it goes into the end goal because
	// that would kill the network. 

	private boolean connectionValid(DList previous, int position, int x, int y, int color) {
		try{	
			if(position == ((int[]) previous.back().item())[2]) {
				//System.out.println("same position");
				return false;
			} else if(color == 0 && y == 0) {
				//System.out.println("in goal");
				return false;
			} else if(color == 1 && x == 0) {
				return false;
			// } else if(color == 0 && y == 7) {
			// 	//System.out.println("in other");
			} else if(color == 1 && x == 7) {
				return false;
			} else if(previous.length() > 1) {
				DListNode checknode = (DListNode) previous.front();
				//System.out.println(x + " " + y);
				for(int i = 0; i < previous.length(); i++) {
					if(((int[]) checknode.item())[0] == x && ((int[]) checknode.item())[1] == y) {
						return false;
					} else {
						if(checknode.next().isValidNode()) {
							checknode = (DListNode) checknode.next();
						} 
					}
				}
				return true;
			} else {
				return true;
			}
		} catch (InvalidNodeException e) {
			System.out.println("error");
			System.out.println(e);
		}
		//System.out.println("reached end");
		return true;
	}


	public int evaluate(int color,Move move) {
		int opponentColor=oppositeColor(color);
		if (this.network(opponentColor)) {
			return (Integer.MAX_VALUE-1);
		}

		if (this.network(color)) {
			return Integer.MAX_VALUE;
		}

		int score=0;
		score=numConnections(color, move)+connectionLength(color);

		if (whiteChips==0 && (move.x1==4 && move.y1==4)) {
			score+=6;
		}
		if (blackChips==0 && (move.x1==4 && move.y1==3)) {
			score+=6;
		}

		if (blackChips==5 && (move.y1==0 || move.y1==7)) {
			score+=3;
		}

		if (whiteChips==5 && (move.x1==0 || move.x1==7)) {
			score+=3;
		}

		int[][] coord=coordinates(color);
		for (int i=0; i<coord.length; i++) {
			if (color==0 && (coord[i][1]==0 || coord[i][1]==7)) {
				score-=2;
			}
			if (color==1 && (coord[i][0]==0 || coord[i][0]==7)) {
				score-=2;
			}
		}
		return score;
       }

	private int numConnections(int color, Move move) {
		/*int[][] coord=coordinates(color);*/
		int x=move.x1;
		int y=move.y1;
		int[][] conCoord=connections(x,y,color);
		int number=0;
		for (int i=0; i<conCoord.length; i++) {
			if (conCoord[i][0]!=0 && conCoord[i][1]!=0) {
				number++;
			}
		}
		return number;
	}

	private int connectionLength(int color) {
		DList networklist = new DList();
		DList startnodes = new DList();
		try {
			if(color == 0) {
				for(int i = 1; i < 7; i++) {
					if(board[i][0] == 0) {
						int[] x = new int[3];
						x[0] = i;
						x[1] = 0;
						startnodes.insertBack(x);
					}
				}
			} else if(color == 1) {
				for(int i = 1; i < 7; i++) {
					if(board[0][i] == 1) {
						int[] x = new int[3];
						x[0] = 0;
						x[1] = i;
						startnodes.insertBack(x);
					}
				}
			} else {
				System.out.println("not a valid color");
				return 0;
			}
			if(startnodes.length() == 0) {
				return 0;
			}
			DListNode checknode = (DListNode) startnodes.front();
			boolean net = false;
			for(int i = 0; i < startnodes.length(); i++) {
				networklist.insertFront(checknode.item());
				net = networkHelper(networklist, color);
				if(net == true) {
					return networklist.length();
				} else {
					networklist = new DList();
					if(checknode.next().isValidNode()) {
						checknode=(DListNode) checknode.next();
					} else {
						continue;
					}
				}
			}
			return networklist.length();
		} catch (InvalidNodeException e) {
			System.out.println("error in network");
			System.out.println(e);
			return networklist.length();
		}
	}

	//gets the coordinates of every chip for the color
	public int[][] coordinates(int color) {
		int coord[][]=new int[10][2];
		int countx = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (board[i][j] == color) {
					// System.out.println("color" + color);
					// System.out.println(countx);
					coord[countx][0] = i;
					coord[countx][1] = j;
					countx++;
				}
			}
		}
		return coord;
	}

	//intakes the coordinates of one chip; we should be able to get the coordinates of each chip
	// so this way we can just plug in the coordinates and check
	private boolean box(int x, int y) {
		int boxcolor=board[x][y];
		if (board[x+2][y+2]==boxcolor && board[x+2][y]==boxcolor && board[x][y+2]==boxcolor) {
			return false;
		}
		return true;
	}

	private void firstMove(int color) {
		if (board[4][5]==2) {
			insert(color, 4,5);
		} else {
			insert (color,5,4);
		}
	}


	

	/*move list for adding*/
	public DList moveList() {
		DList moveList=new DList();
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Move move = new Move(i,j);
				moveList.insertFront(move);
			}
		}
		return moveList;
	}
	/*move list for stepping*/
	// need to check for edge case where won't be cluster if you move it so you can cluster it after. 
	public DList moveListStep(int color) {
		int count=0;
		int[][] chipLocations=new int[10][2];
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if (board[i][j] == color) {
					chipLocations[count][0]=i;
					chipLocations[count][1]=j;
					count++;
				}
			}
		}
		int x;
		int y;
		DList moveList = new DList();
		for (int a=0; a<10; a++) {
			for (int i=0; i<8; i++) {
				for (int j=0; j<8; j++) {
					x=chipLocations[a][0];
					y=chipLocations[a][1];
					Move move = new Move(i,j,x,y);
					moveList.insertFront(move);
				}
			}
		}
			return moveList;
	}

	/*checks for a valid move
	0=black
	1=white
	2=neutral	
	added movekind to check for new edge case */
	public boolean isValidMove(Move move, int color) {
		int moveKind = move.moveKind;
		int x=move.x1;
		int y=move.y1;


		if ((x==0 && y==0) || (x==7 && y==7) || (x==0 && y==7) || (x==7 && y==0)) {
			return false;
		}
		if (color == 0 && (x==0 || x==7)) {
			return false;
		}
		if (color == 1 && (y==0 || y==7)) {
			return false;
		}
		if (board[x][y]!=2) {
			return false;
		}
		if(moveKind == 2) {
			int xprior = move.x2;
			int yprior = move.y2;
			board[xprior][yprior] = 2;
			if(xprior == x && yprior == y) {
				return false;
			}
			if(checkAround(x, y, color) == false) {
				board[xprior][yprior] = color;
				return false;
			}
		}
		if(moveKind == 1) {
			if(checkAround(x, y, color) == false) {
				return false;
			}
		}
		return true;
	}

	/*checks around a point twice to see if there's a cluster*/
	private boolean checkAround(int x, int y, int color) {

		int count=0;
		int xcounter=x;
		int ycounter=y;
		for(int i = 0; i < 8; i++) {
			switch(i) {
				case 0:
				xcounter = x-1;              //each case for each direction. case 0 is leftup, case 1 is up, case 2 is rightup.. and so on in clockwise direction
				ycounter = y-1;
				break;
				case 1:
				xcounter = x;
				ycounter = y-1;
				break;
				case 2:
				xcounter = x+1;
				ycounter = y-1;
				break;
				case 3:
				xcounter = x+1;
				ycounter = y;
				break;
				case 4:
				xcounter = x+1;
				ycounter = y+1;
				break;
				case 5:
				xcounter = x;
				ycounter = y+1;
				break;
				case 6:
				xcounter = x-1;
				ycounter = y+1;
				break;
				case 7:
				xcounter = x-1;
				ycounter = y;
				break;
			}
			if(validCell(xcounter, ycounter, color) && board[xcounter][ycounter] == color) {
				count++;
				int xcounter1 = 0;
				int ycounter1 = 0;
				for(int j = 0; j < 8; j++) {
					switch(j) {
						case 0:
						xcounter1 = xcounter-1;              //each case for each direction. case 0 is leftup, case 1 is up, case 2 is rightup.. and so on in clockwise direction
						ycounter1 = ycounter-1;
						break;
						case 1:
						xcounter1 = xcounter;
						ycounter1 = ycounter-1;
						break;
						case 2:
						xcounter1 = xcounter+1;
						ycounter1 = ycounter-1;
						break;
						case 3:
						xcounter1 = xcounter+1;
						ycounter1 = ycounter;
						break;
						case 4:
						xcounter1 = xcounter+1;
						ycounter1 = ycounter+1;
						break;
						case 5:
						xcounter1 = xcounter;
						ycounter1 = ycounter+1;
						break;
						case 6:
						xcounter1 = xcounter-1;
						ycounter1 = ycounter+1;
						break;
						case 7:
						xcounter1 = xcounter-1;
						ycounter1 = ycounter;
						break;
					}
					if(validCell(xcounter1, ycounter1, color) && board[xcounter1][ycounter1] == color) {
						return false;
					} else {
						continue;
					}
				}	

			} else {
				continue;
		 	}

		// 	if (xcounter>=0 && xcounter<8 && ycounter>=0 && ycounter<8) {
		// 		if (board[xcounter][ycounter]==color) {
		// 			if(checkAround(xcounter,ycounter,color)){
		// 				return false;
		// 			} else {
		// 				count++;
		// 			}
		// 			/*return true;*/
		// 		}
		// 	}
		// }
		// if (count==1 || count==0) {
		// 	return true;
		// } else {
		// 	return false;
		// }
		}
		if(count > 1) {
			return false;
		} else {
			return true;
		}
	}

	/*returns a list of valid moves*/
	//need to consider edge case of if you move a chick and it breaks a cluster, you can cluster there again. 
	public DList workingMoves(int color) {
		DList list_of_moves=new DList();
		if (chips<20) {
			list_of_moves=moveList();
		} else {
			list_of_moves=moveListStep(color);
		} 
		DListNode node = (DListNode) list_of_moves.front();
		int len = list_of_moves.length();
/*		ListNode removedNode=list_of_moves.front();*/
		try{
			for (int i=0; i<len; i++) {
				Move move = (Move) node.item();
				if (isValidMove(move,color)) {
					if(node.next().isValidNode()) {
					node = (DListNode) node.next();
/*						removedNode.remove();		// System.out.println("This is the list length: "+l.length());
					removedNode = node;*/
					}
				} else {
					if(node.next().isValidNode()) {
					node = (DListNode) node.next();
					node.prev().remove();
					/*removedNode = node;*/
					} else {
						node.remove();
					}
				}
			}
			return list_of_moves;
		} catch (InvalidNodeException e) {
			System.out.println(e);
		}
		return list_of_moves;
	}



















//all under this is for testing purposes.



public int[][] networkToArray(DList list) {
		int[][] print = new int[10][2];
		try {
		DListNode curr = (DListNode) list.front();
			for(int i = 0; i < list.length(); i++) {
				int[] item = (int[]) curr.item();
				print[i][0] = item[0];
				print[i][1] = item[1];
				curr = (DListNode) curr.next();  
			}
		return print;
		}
		catch(InvalidNodeException e) {
			System.out.println(e);
		}
		return print;
	}

	public void stringRep(){
		String result = "[ ";
		System.out.println("     C0    C1    C2    C3    C4    C5    C6    C7");
		for(int j=0; j<8; j++){
			for(int i=0; i<8; i++){
				/* System.out.println(i + " " + j);*/
				if(i == 7){
					result = result + board[i][j] + " ]";
				}
				else{
					result = result /*+"(" + i + ", " + j + "): " */+ board[i][j] + " ] [ ";
				}
			}
			System.out.println("R" + j + ": " + result);
			result = "[ ";
		}
	}


	public static void arrayToString(int[][] array) {
		String string=new String();
		for (int i=0; i<array.length; i++) {
			string+="[ ";
			for (int j=0; j<2; j++) {
				string+=array[i][j]+" ";
			} 
			string+="] ";
		}
			System.out.println(string);
	}







	public static void main(String[] args) {
		GameBoard board=new GameBoard();
		DList workinglist = board.workingMoves(0);


	// 	Move m1=new Move(6,0);
	// 	Move m2=new Move(6,5);
	// 	Move m3=new Move(5,5);
	// 	Move m4=new Move(3,3);
	// 	Move m5=new Move(3,5);
	// 	Move m6=new Move(5,7);
	// 	Move m7=new Move(2,0);
	// 	Move m8=new Move(2,5);
	// 	Move m9=new Move(3,5);
	// 	Move m10=new Move(1,3);
	// 	Move m11=new Move(5,0);

	// 	board.insert(0,m1);
	// 	board.insert(0,m2);
	// 	board.insert(0,m3);
	// 	board.insert(0,m4);
	// 	board.insert(0,m5);
	// 	board.insert(0,m6);
	// 	board.insert(0,m7);
	// 	board.insert(0,m8);
	// 	board.insert(0,m9);
	// 	board.insert(0,m10);
	// 	board.insert(0,m11);
	// 	int[][] connections=board.connections(2,5,0);



		

	// 	board.stringRep();
	// 	System.out.println(board.network(0));
	// 	board.remove(2,0);
	// 	board.stringRep();
	}

	}