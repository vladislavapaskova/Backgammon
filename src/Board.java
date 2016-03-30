import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

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
	
	//create a variable for the dice roll
	int diceRoll; 
	
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
	public void movePiece(int numPlayer){
		
		int finalSix;
		int winPiece; 
		ArrayList<Integer> availablePos; 
		int playingPos;
		int outPieces; 
		int diceRoll; 
		
		if(numPlayer==1)
		{
			winPiece= winPlayer1; 
			finalSix= finalPlayer1;
			outPieces= outPlayer1; 
			
		}
		else 
		{
			winPiece= winPlayer2; 
			finalSix=finalPlayer2;
			outPieces= outPlayer1;
		}
		
		
		//set the dice roll
		diceRoll=dieRoll(); 
		if(outPieces==0)
		{
			//check all of the pieces that the player can use
			availablePos=checkMovingPositions(numPlayer);
			/*
			 * pick a random piece
			 * needs to change one we use minimax/reinforcement learning
			 */
			playingPos= availablePos.get(0 + (int)(Math.random() * availablePos.size()));
			System.out.println("Random position picked:"+playingPos);
			
			//we are not at the final stretch yet
			if((finalSix+winPiece)!=15)
			{
				if(numPlayer==1)
				{
					while(!checkIfPieceCanMove(numPlayer, (playingPos+diceRoll))){
						playingPos= availablePos.get(0 + (int)(Math.random() * availablePos.size()));
						System.out.println("Random position changed to:"+playingPos);
					}
					//take the piece from where it was
					boardA[playingPos].pop(); 
					//check if the piece is on top of the other player's piece
					if(boardA[playingPos+diceRoll].peek()==2)
					{
						boardA[playingPos+diceRoll].pop();
						outPlayer2++; 
					}
					boardA[playingPos+diceRoll].push(1);
					//check if it is final six
					if(playingPos<18&&(playingPos+diceRoll)>17)
					{
						finalPlayer1++; 
					}
				}
				else if(numPlayer==2)
				{
					while(!checkIfPieceCanMove(numPlayer, (playingPos-diceRoll))){
						playingPos= availablePos.get(0 + (int)(Math.random() * availablePos.size()));
						System.out.println("Random position changed to:"+playingPos);
					}
					boardA[playingPos].pop(); 
					if(numPlayer==2&&boardA[playingPos-diceRoll].peek()==1)
					{
						boardA[playingPos-diceRoll].pop();
						outPlayer1++; 
					}
					boardA[playingPos-diceRoll].push(2);
					if(playingPos>5&&(playingPos+diceRoll)<6)
					{
						finalPlayer2++; 
					}
				}
				
				
			}
			else
			{
				//we need to check if there is piece from the other player
				//add an extra rule that the piece can go beyond the boundary of the board
				if(numPlayer==1)
				{
					while(!checkIfPieceCanMove(numPlayer, (playingPos+diceRoll))&&playingPos+diceRoll<23){
						playingPos= availablePos.get(0 + (int)(Math.random() * availablePos.size()));
					}
					boardA[playingPos].pop(); 
					//check if the piece is on top of the other player's piece
					if(boardA[playingPos+diceRoll].peek()==2)
					{
						boardA[playingPos+diceRoll].pop();
						outPlayer2++; 
					}
					
					if(playingPos+diceRoll>23)
					{
						winPlayer1++;
						finalPlayer1--; 
					}
					else{
						boardA[playingPos+diceRoll].push(1);
					}
					
				}
				if(numPlayer==2)
				{
					while(!checkIfPieceCanMove(numPlayer, (playingPos-diceRoll))&&playingPos-diceRoll>=0){
						playingPos= availablePos.get(0 + (int)(Math.random() * availablePos.size()));
					}
					boardA[playingPos].pop(); 
					//check if the piece is on top of the other player's piece
					if(boardA[playingPos-diceRoll].peek()==1)
					{
						boardA[playingPos-diceRoll].pop();
						outPlayer1++; 
					}
					
					if(playingPos-diceRoll<0)
					{
						winPlayer2++;
						finalPlayer2--; 
					}
					else{
						boardA[playingPos-diceRoll].push(2);
					}
					
				}
			}
		}
		//we have pieces out of the board
		else
		{
			//if player 1 check if we can put pieces from 1-5
			if(numPlayer==1)
			{
				if(checkIfPieceCanMove(1,diceRoll-1))
				{
					if(boardA[diceRoll-1].peek()==2)
					{
						boardA[diceRoll-1].pop();
						outPlayer2++;
						finalPlayer2--; 
					}
					boardA[diceRoll-1].push(1);
				}
			}
			//if player 2 check if we can put pieces from 18-23
			if(numPlayer==2)
			{
				if(checkIfPieceCanMove(1,24-diceRoll))
				{
					if(boardA[24-diceRoll].peek()==1)
					{
						boardA[24-diceRoll].pop();
						outPlayer1++; 
						finalPlayer1--; 
					}
					boardA[24-diceRoll].push(2);
				}
			}
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
			System.out.println(i);
			if(boardA[i].peek()==numPlayer&&boardA[i].peek()!=null)
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
		//check if it goes outside of the board
		if(col<boardA.length||col>=0){
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
		else
			return false; 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b = new Board(); 
		b.movePiece(1); 
		b.printBoard(); 
	}

}
