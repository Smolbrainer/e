import hsa.Console;
import java.awt.*;

public class TicTacToe
{
  
    static int player = 1;
    static int square = 9;     //9 squares in a Tic Tac Toe grid.
    final static int computer = 4;   //computer has value of 4.
    
    static Console c = new Console (70, 125);

    //Declaration of Variables
    static String[] args1;     //used to reset game
    static char XorO;    //Symbol choice (X or O)
    static char playAgain;     //(Y or N)
    static int squareNumber = 0;    //Player choice (1-9)
    static boolean done = false;  //Completion of a game.
    static boolean chose;     //Did the player choose a valid square?
    static int promptStatement, choice, compChoice, compChosen, freeSquare;
    static int[] intSquare = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    static String[] strSquare = {"1", "2", "3", "4", "5", "6", "7", "8", "9"}; //Numbers entered into the empty squares
    static int x, horizontalTop, horizontalMid, horizontalBot, verticalLeft, verticalMid, verticalRight, diagonalLeft, diagonalRight, total;
    static int roundsWon, roundsLost, roundsPlayed, roundsTied, winner, counter; //These variables generate the game results.
    static int[] [] compAvail = new int [9] [9]; // two dimensional array


    public static void main (String[] args)
    {
     args1 = args;
     
     c.print("\n \n \n \n \t \t \t \t \t Please enter your choice to be either X or O: ");
     
  //While user does not type in a valid X or O.
  while (!((XorO == 'X') || (XorO == 'x') || (XorO == 'O') || (XorO == 'o')))
  {
      XorO = c.readChar();
      c.clear();
      c.print("\n \n \n \n \t \t \t \t \t Please enter your choice to be either X or O: ");
  }
  
  if ((XorO == 'X') || (XorO == 'x'))
  {
      while (!done) //Runs until game is done.
      {
       player("X"); //Human player chooses to be X.
       if (!done)
       {
        computer("O"); //Computer player will play as O.
       }
      }
  } //End If SymbolChoice = X statement.

  else if ((XorO == 'o') || (XorO == 'O'))
  {
      while (!done) //Runs until game is done.
      {
    computer("X"); //Computer player goes first, playing as X.
    if (!done)
    {
        player("O"); //Human player plays as O.
    }
      }
  } //End If symbol choice = o statement.

  if (winner == player)
  {
      c.print("\t \t \t \t \t \t Game over, congratulations you won!");
      roundsWon++; //Number of rounds won by human player is added
  }
  else if (winner == computer)
  {
      c.print("\t \t \t \t \t \t Game over, the computer won.");
      roundsLost++;
  }
  else
  {
      gameBoard(); //game board method.
      c.print("\t \t \t \t \t \t Game over, tie game.");
      roundsTied++;
  }
  roundsPlayed++;
  
  c.print("\n \t\t\t\t\t\t Would you like to play again? (Y/N)");
  //Invalid response to play again.
  while (!((playAgain == 'y') || (playAgain == 'Y') || (playAgain == 'n') || (playAgain == 'N')))
  {
      playAgain = c.readChar();
      c.clear();
      c.println("\t\t\t\t\t Sorry, your entry is invalid.");
      c.println("\n \t\t\t\t\t Would you like to play again? (Y/N)\n");
  }
 
  if ((playAgain == 'y') || (playAgain == 'Y')) //Player wants to play again
  {
      resetGame(); //Reset Game Method
  }
  else if ((playAgain == 'n') || (playAgain == 'N')) //Player wants to end game, results shown.
  {
      c.clear();
      c.println("\n \t \t \t \t \t \t You have won " + roundsWon + " game(s).");
      c.println("\t \t \t \t \t \t You have lost " + roundsLost + " game(s).");
      c.println("\t \t \t \t \t \t You have tied " + roundsTied + " game(s).");
      c.println("\t \t \t \t \t \t You have played " + roundsPlayed + " game(s).");
 
  }
 
    } //end Main Method


    public static void player (String symbol)
    {
  chose = false; //player did not choose a number yet.
  promptStatement = 0;

  while (!chose)
  {
      if (promptStatement == 0)
      {
    gameBoard();
    c.println("\t \t \t \t Enter the number that corresponds with an empty square of your choice");
      }
      else if (promptStatement == 1)
   //promptStatement == 1 when player enters number that is out of range or when play enters an occupied option
   {
       gameBoard();
       c.println("\t\t\t\t\t\t\t" + (choice + 1) + " is an invalid choice!");
       c.println("\t\t\t Please enter the number between 1 and 9 that corresponds with an empty square");
   }
 
      choice = (c.readInt () - 1);
      if ((choice <= 8) && (choice >= 0) && (intSquare [choice] == 0))
      {
    intSquare [choice] = player;
    strSquare [choice] = symbol;
    chose = true;
      } // If valid choice entered.
      else if ((choice < 0) || (choice > 8) || (intSquare [choice] != 0))
      {
       promptStatement = 1;
      } //choice out of range/already occupied
  }
 
  //If the player has occupied square 1,5, or 9, human player might have won.
  if ((intSquare [0] == player) || (intSquare [4] == player) || (intSquare [8] == player))
  {
      winCheck(); //Checks if player has won, since player must have either 1,5,9 to win.
  }

    } //End player method.

    
    public static void computer (String symbol)
    {
     if (intSquare [4] == 0) //Center square is not occupied
     {
      compChoice = 4; //Computer will choose the center square
  }
  else //center square is occupied:
  {
      sumofLines(); //Calculates sum of lines to begin the offensive moves method
      compOffence(); //Method
  }
  intSquare [compChoice] = computer;
  strSquare [compChoice] = symbol;
 
  //check if computer has won, only if they have occupied squares 1,5, or 9
  if ((intSquare [0] == computer) || (intSquare [4] == computer) || (intSquare [8] == computer))
  {
      winCheck(); //Method
  }
    } //End computer method.


    public static void sumofLines ()
    { //Sum of horizontal lines from top to bottom
  horizontalTop = intSquare [0] + intSquare [1] + intSquare [2];
  horizontalMid = intSquare [3] + intSquare [4] + intSquare [5];
  horizontalBot = intSquare [6] + intSquare [7] + intSquare [8];
  //Sum of vertical lines from left to right
  verticalLeft = intSquare [0] + intSquare [3] + intSquare [6];
  verticalMid = intSquare [1] + intSquare [4] + intSquare [7];
  verticalRight = intSquare [2] + intSquare [5] + intSquare [8];
  //Sum of diagonal lines, \ , /
  diagonalLeft = intSquare [0] + intSquare [4] + intSquare [8];
  diagonalRight = intSquare [2] + intSquare [4] + intSquare [6];
  //Sum of all lines 
  total = 0;
  for (x = 0 ; x < square ; x++)
  {
      total += intSquare [x];
  }
    } // End sumOfLines method.


    public static void winCheck ()  
    { 
     //For each line, the human player has a value of 1, the computer player has a value of 4
     //2 cases are used, case 3 and case 12. case 3 is valid when the player wins, case 12 is valid when the computer wins.
     //Calculate sum of lines
  sumofLines ();
 
  switch (horizontalTop)
  {
      case 3: //One occupied square for the player is declared to have a value of 1, a total of 3 means that the human player has won.
   crossLine (player, 1); //cross line method, player is winner.
   break;
      case 12: //Since the computer is declared to have a value of 4, if the horizontal top line adds to 12
   crossLine (computer, 1); //Computer is the winner and crossline method, line == 1 is printed out.
   break;
      default:
   break;
  } // switch horizontalTop
  switch (horizontalMid)
  {
      case 3:
   crossLine (player, 2); //HorizontalMid line sums up to 3, player has won since
   break; //Player value is 1, three of the symbols make a product of 3
      case 12:
   crossLine (computer, 2); //When sum is 12, computer has won because each computer square
   break; //has a value of 4, when it occupies three in the same line, it gives a product of 12
      default:
   break;
  } // switch horizontalMid
  switch (horizontalBot)
  {
      case 3:
   crossLine (player, 3);
   break;
      case 12:
   crossLine (computer, 3);
   break;
      default:
   break;
  } // switch horizontalBot
  switch (verticalLeft)
  {
      case 3:
   crossLine (player, 4);
   break;
      case 12:
   crossLine (computer, 4);
   break;
      default:
   break;
  } // switch verticalLeft
  switch (verticalMid)
  {
      case 3:
   crossLine (player, 5);
   break;
      case 12:
   crossLine (computer, 5);
   break;
      default:
   break;
  } // switch verticalMid
  switch (verticalRight)
  {
      case 3:
   crossLine (player, 6);
   break;
      case 12:
   crossLine (computer, 6);
   break;
      default:
   break;
  } // switch verticalRight
  switch (diagonalLeft)
  {
      case 3:
   crossLine (player, 7);
   break;
      case 12:
   crossLine (computer, 7);
   break;
      default:
   break;
  } // switch diagonalLeft
  switch (diagonalRight)
  {
      case 3:
   crossLine (player, 8);
   break;
      case 12:
   crossLine (computer, 8);
   break;
      default:
   break;
  } // switch diagonalRight

  if (winner == 0)
  {
      done = true; //Game is doned.
      for (x = 0 ; x < square ; x++)
      {
    if (intSquare [x] == 0)
    {
        done = false;
    }
      }
  }

    } //End win check method


    public static void crossLine (int won, int line)  //Creates a line that goes through three of the same symbols in a row.
    {
  winner = won;
  gameBoard(); // Print board and draw a line through the winning squares
  done = true;
 
  if (line == 1)
  {
      c.drawLine (800, 190, 240, 190); //horizontal line goes through 1,2,3
  } // if (line)
  else if (line == 2)
  {
      c.drawLine (800, 290, 240, 290); //horizontal line goes through 4,5,6
  } // else if
  else if (line == 3)
  {
      c.drawLine (800, 390, 240, 390); //horizontal line goes through 7,8,9
  } // else if
  else if (line == 4)
  {
      c.drawLine (260, 120, 260, 460); //vertical line goes through squares 1,4,7
  } // else if
  else if (line == 5)
  {
      c.drawLine (515, 120, 515, 460); //vertical line goes through squares 2,5,8
  } // else if
  else if (line == 6)
  {
      c.drawLine (710, 120, 710, 460); //vertical line goes through squares 3,6,9
  } // else if
  else if (line == 7)
  {
      c.drawLine (800, 430, 250, 180); //diagonal line \ squares 1,5,9
  } // else if
  else if (line == 8)
  {
      c.drawLine (800, 150, 250, 410); //diagonal line / squares 3,5,7
  } // else if
    } // end crossLine method


    public static void gameBoard ()
    {
  c.clear();
  c.print("\n\n\n\n\n\n\n\n\n");
  for (x = 0 ; x < square ; x++)
  {
      // Print square contents and add new line after every third square
      if (((x + 1) % 3) != 0)
      {
   c.print("\t\t\t\t" + (strSquare [x])); //Prints the numbers in each square.
      } 
      else
      {
   c.print("\t\t\t" + (strSquare [x] + "\n\n\n\n\n"));
      } // else
  } // for
 
  c.drawLine (800, 350, 240, 350); // Horizontal top
  c.drawLine (800, 240, 240, 240); // Horizontal bottom
  c.drawLine (430, 120, 430, 460); // Vertical left
  c.drawLine (620, 120, 620, 460); // Vertical right
    } //End game board method


    public static void compDefence ()
    {
  if (horizontalTop == 2) //Checks if the horizontal top already has 2 occupied squares by the human player.
  {
      for (x = 0 ; x < 3 ; x++)
      {
    if (intSquare [x] == 0) //Checks if arrays 0,1,2 are empty.
    {
        compChoice = x; //Computer choice will be the empty array.
    }
      }
  }
  else if (horizontalMid == 2) //Checks if the horizontal middle line already has 2 occupied squares by the human player.
  {
      for (x = 3 ; x < 6 ; x++)
      {
    if (intSquare [x] == 0) //Checks if arrays 3,4,5 are empty.
    {
        compChoice = x; //Computer choice will be the empty array.
    }
      }
  } // else if (horizontalMid)
  else if (horizontalBot == 2) //Checks if the horizontal bottom line already has 2 occupied squares.
  {
      for (x = 6 ; x < 9 ; x++)
      {
    if (intSquare [x] == 0) //Checks which of the 3 arrays are empty 6/7/8.
    {
        compChoice = x; //Computer choice will be the empty array.
    }
      }
  } //else if (horizontalBot)
  else if (verticalLeft == 2)
  {
      for (x = 0 ; x < 9 ; x = (x + 3)) //For a vertical line, the numbers increase by 3.
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    }
      }
  } // else if (verticalLeft)
  else if (verticalMid == 2)
  {
      for (x = 1 ; x < 9 ; x = (x + 3))
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    }
      }
  } // else if (verticalMid)
  else if (verticalRight == 2)
  {
      for (x = 2 ; x < 9 ; x = (x + 3))
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    }
      }
  } // else if (verticalRight)
  else if (diagonalLeft == 2)
  {
      for (x = 0 ; x < 9 ; x = (x + 4))
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    }
      }
  } // else if (diagonalLeft)
  else if (diagonalRight == 2)
  {
      for (x = 2 ; x < 7 ; x = (x + 2))
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    }
      }
  } // else if (diagonalRight)
  else
  {
      randomMove();
  } // else
    } // end computer defence method


    public static void compOffence ()
    {
  if (horizontalTop == 8) //Since value of each computer square is 4, program searches to see if it already has 2 occupied squares in a row
  {
      for (x = 0 ; x < 3 ; x++)
      {
    if (intSquare [x] == 0) //Finds the unoccupied square and chooses it.
    {
        compChoice = x;
    }
      }
  } //if horizontalTop
  else if (horizontalMid == 8) //Since value of computer is 4, 2 multiplied by 4 is 8, which means 2 squares are occupied by the computer
  {
      for (x = 3 ; x < 6 ; x++)
      {
    if (intSquare [x] == 0)
    {
        compChoice = x; //Finds the unoccupied square and occupies it.
    } // if
      } // for
  } // else if (horizontalMid)
  else if (horizontalBot == 8)
  {
      for (x = 6 ; x < 9 ; x++)
      {
    if (intSquare [x] == 0) //intSquare [x] == 0 means that square is unoccupied
    {
        compChoice = x; //Finds the unoccupied square and occupies it.
    }
      }
  } // else if (horizontalBot)
  else if (verticalLeft == 8)
  {
      for (x = 0 ; x < 9 ; x = (x + 3)) //For a vertical line, the numbers increase by 3.
      {
    if (intSquare [x] == 0) //Searchs squares 1,4,7
    {
        compChoice = x;
    }
      }
  } // else if (verticalLeft)
  else if (verticalMid == 8)
  {
      for (x = 1 ; x < 9 ; x = (x + 3))
      {
    if (intSquare [x] == 0) //Searches squares 2,5,8
    {
        compChoice = x;
    }
      }
  } // else if (verticalMid)
  else if (verticalRight == 8)
  {
      for (x = 2 ; x < 9 ; x = (x + 3))
      {
    if (intSquare [x] == 0) //Searches squares 3,6,9
    {
        compChoice = x;
    }
      }
  } // else if (verticalRight)
  else if (diagonalLeft == 8)
  {
      for (x = 0 ; x < 9 ; x = (x + 4)) //Searches squares 1,4,7
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    } // if
      } // for
  } // else if (diagonalLeft)
  else if (diagonalRight == 8)
  {
      for (x = 2 ; x < 7 ; x = (x + 2)) //Searches squares 3,5,7
      {
    if (intSquare [x] == 0)
    {
        compChoice = x;
    } // if
      } // for
  } // else if (diagonalRight)
  else
  {
      compDefence();
  } // else
    } // end computer offence method

    //Method used when compOffence and compDefence is not used.
    public static void randomMove ()  
    {
  //determine the free squares
  freeSquare = 0;
  for (x = 0 ; x < square ; x++)
  {
      if (intSquare [x] == 0) //Unocupied squares.
      {
   compAvail [counter] [freeSquare] = x;
   freeSquare++;
      }
  }
  //Now randomly selects a free square for the computer with the list of availible comp. choices
 
  compChosen = (int) Math.floor (Math.random () * (freeSquare));
  compChoice = compAvail [counter] [compChosen];
  counter++;

    } //end random move method


    public static void resetGame ()
    {
  XorO = ' '; //symbol choice is reprompted to user
  playAgain = ' ';
  for (x = 0 ; x < square ; x++)
  {
      intSquare [x] = 0; //All squares are now empty
      strSquare [x] = "" + (x + 1);
  }
  done = false; //all values are reset for a new game
  counter = 0;
  winner = 0;
 
  c.clear();
  main(args1);
    } //end reset game method
    
} // main class
