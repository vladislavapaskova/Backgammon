import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
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
	Stack[] boardA;
	
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
	
	
	public Board(){
		boardSize=24;
		initializeGame(); 
	}
	
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
	private Stack putInitPieces(Stack s, int numPieces, int numPlayer)
	{
		for(int i=0; i<numPieces; i++)
		{
			s.push(numPlayer);
		}
		return s; 
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
