import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 *This Class is going to hold the backgammon board and the main logic behind moving the pieces 
 */

/**
 * @author vladislavapaskova
 *
 */
public class Board {
	/*
	 * Instance variables
	 */
	
	//game board
	Stack<Integer>[] boardA;
	
	//board size
	private static int boardSize;
	
	//pieces out of the board
	int outPlayer1; 
	int outPlayer2; 
	
	//pieces that the player has managed to get out;count towards winning
	int winPlayer1; 
	int winPlayer2; 
	
	//pieces in final six lanes; we count those to see if we can start taking pieces out towards winning
	int finalPlayer1; 
	int finalPlayer2; 
	
	/*
	 * board constructor
	 */
	public Board(){
		boardSize=24;
		initializeGame();
		printBoard(); 
	}
	
	/*
	 * initializes game
	 */
	private void initializeGame()
	{
		outPlayer1=0; 
		outPlayer2=0; 
		winPlayer1=0; 
		winPlayer2=0; 
		finalPlayer1=5;
		finalPlayer2=5;
		
		initializeBoardArray(); 
	}
	
	/*
	 * This method initializes the initial board
	 * i.e. puts all of the pieces in the correct locations
	 * initializes all of the stacks 
	 */
	private void initializeBoardArray()
	{
		boardA = new Stack[boardSize];
		
		for(int i=0; i<boardSize; i++)
		{
			//initialize an empty stack for now
			boardA[i] = new Stack<Integer>(); 

			/*
			 * arrange pieces 
			 */
			if(i==0)
			{
				putInitPieces(boardA[i], 2, 1);
			}
			if(i==5||i==12)
			{
				putInitPieces(boardA[i], 5, 2);
			}
			if(i==7)
			{
				putInitPieces(boardA[i], 3, 2);
			}
			if(i==11||i==18)
			{
				putInitPieces(boardA[i], 5, 1);
			}
			if(i==16)
			{
				putInitPieces(boardA[i], 3, 1);
			}
			if(i==23)
			{
				putInitPieces(boardA[i], 2, 2);
			}
			
		}

	}
	
	/*
	 * puts correct number of pieces in a given stack
	 */
	private Stack<Integer> putInitPieces(Stack<Integer> s, int numPieces, int numPlayer)
	{
		for(int i=0; i<numPieces; i++)
		{
			s.push(numPlayer);
		}
		
		return s; 
	}
	
	/*
	 * print board
	 */
	public void printBoard()
	{
		String print="";
		for(int i=0; i<boardSize; i++)
		{
			print += Arrays.toString(boardA[i].toArray());
		}
		System.out.println(print);
	}
	
	/*
	 * generates a random number from 1-6 for the die roll
	 */
	public int dieRoll()
	{
		return (1 + (int)(Math.random() * 6)); 
	}
	
	
	/*
	 * move
	 */
	public void movePiece(int numPlayer,int pieceCol, int diceRoll){
		
		int finalSix;
		int winPiece; 
		
		if(numPlayer==1)
		{
			winPiece= winPlayer1; 
			finalSix= finalPlayer1; 
		}
		else 
		{
			winPiece= winPlayer2; 
			finalSix=finalPlayer2; 
		}
		
		//we are not at the final stretch yet
		if((finalSix+winPiece)!=15)
		{
			
		}
		
		
	}
	
	
	/*
	 * checks who won the game
	 */
	public int gameWon()
	{
		if(winPlayer1==15)
			return 1; 
		if(winPlayer2==15)
			return 2; 
		else
			return 0; 
	}
	
	/*
	 * This method returns the positions at which the given player can move a piece FROM
	 */
	public ArrayList<Integer> checkMovingPositions(int numPlayer){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i=0; i<boardSize; i++)
		{
			if(boardA[i].peek()==numPlayer)
			{
				list.add(i);
			}
		}
		
		return list; 
		
	}
	
	/*
	 * checks if the piece can move in this column
	 */
	public boolean checkIfPieceCanMove(int numPlayer, int col){
		if(boardA[col].peek()==null||boardA[col].peek()==numPlayer)
			return true; 
		else 
		{
			int temp= boardA[col].pop();
			
			if(boardA[col].peek()==null)
			{
				boardA[col].push(temp);
				return true;
			}
			else
			{
				boardA[col].push(temp);
				return false; 
			}	
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b = new Board(); 
	}

}
