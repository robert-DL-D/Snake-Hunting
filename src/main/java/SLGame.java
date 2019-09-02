import com.snakehunter.Dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SLGame {

    // These arrays and variables are required only for part II
    Snake snakes[] = new Snake[5]; // array can store up to 10 Snake objects
    Ladder ladders[] = new Ladder[5]; // array can store up to 10 Ladder objects
    Trap traps[] = new Trap[10]; // array can store up to 10 Trap objects
    String name[] = new String[2]; // array for storing the names
    int snakesCount = 0;
    int laddersCount = 0;
    int trapsCount = 0;

    // Creating a Board, dice and a Scanner objects
    Board bd;
    Dice dice;

    Scanner scan = new Scanner(System.in);

    private List<Integer> slList = new ArrayList<Integer>();

    private Boolean valid = false;

    private StringBuilder sB = new StringBuilder();

    // The very first method to be called
    // This method constructs a SLGame object and calls its control method
    public static void main(String args[]) {

        SLGame slg = new SLGame();

        slg.setUpWindow(slg);

    }

    public void setUpWindow(SLGame slg) {

        Setup setup = new Setup(slg);

        this.control();
    }

    // The main method implementing the game logic for Part A
    // For Part A you may use the hard coded values in the setup() method
    // You will need to change the code for Part B
    // For user interaction use the methods getString, getInt and plainMessage
    // For display/erase on the board use bd.addMessage(), bd.clearMessages()

    public void control() {

        // setup.setVisible(false);

        int numPlayers = 2;

        bd = new Board();

        dice = bd.getDice();

        // setup = new Setup(bd);

        // setUpValidation();

        setup(bd); // setup method currently hard-codes the values

        bd.clearMessages(); // clears the display board

        // String name1 = getString("Player 1 name : ");
        // String name2 = getString("Player 2 name : ");
        bd.clearMessages();
        // bd.addMessage("Current Players are");
        // bd.addMessage("Player 1 : " + name1);
        // bd.addMessage("Player 2 : " + name2);
        bd.setLocations();
		/*int p1Location = 1;
		int p2Location = 1;
		bd.setPiece(1, p1Location);
		bd.setPiece(2, p2Location);*/

		/*int val = getInt(
				//name1 + 
				": Enter 0 to throw dice. Enter 1 - 6 for Testing.", 0, 6);
		if (val == 0)
			val = dice.roll();
		else
			dice.set(val);*/

		/*p1Location += val;
		plainMessage(name1 + ": moving to " + p1Location);
		bd.setPiece(1, p1Location);
		if (p1Location == 7) {
			p1Location = 49; // going up the first ladder
			plainMessage(name1 + ": goin up a ladder to " + p1Location);
			bd.setPiece(1, p1Location);
		}*/

		/*
		// here we are allowing use to set the dice value for testing purposes
		val = getInt(name2 + ": Enter 0 to throw dice. Enter 1 - 6 for Testing.", 0, 6);
		if (val == 0)
			val = dice.roll();
		else
			dice.set(val);
		p2Location += val;
		plainMessage(name2 + ": moving to " + p2Location);
		bd.setPiece(2, p2Location);
		if (p2Location == 7) {
			p2Location = 49; // going up the first ladder
			plainMessage(name2 + ": going up a ladder to " + p2Location);
			bd.setPiece(2, p2Location);
		}*/

        // plainMessage("The rest is up to you. You may have to introduce additional
        // variables.");

        // Complete the game logic
        // throwing or setting the dice
        // moving by dice value and showing the pieces at new position
        // moving up the ladder, going down the snake or being trapped (lose 3 moves)

        bd.addMessage("Continue until");
        bd.addMessage("a player gets to 100");
        bd.addMessage("Remember to have fun!");
        bd.addMessage("Danger: Traps,Snakes");
        bd.addMessage("Trap: lose 3 moves");

        // Move piece by input
        Scanner sc = new Scanner(System.in);

        while (true) {

            String s = sc.nextLine().toLowerCase();

            if (s.equals("roll")) {
                dice.roll();
            } else if (checkInput(s)) {
//                dice.set(Integer.parseInt(s));
                bd.setPiece(1, bd.getP1Location() + Integer.parseInt(s));
            } else {
                bd.moveSnake(s);
            }

        }

    }

    public boolean checkInput(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public boolean setUpValidation(Setup setup) {

        // As each field is added to the list, they are also being checked for blank
        slList.add((blankCheck(setup.getS1HeadTxtFld().getText(), 0)));
        slList.add((blankCheck(setup.getS2HeadTxtFld().getText(), 1)));
        slList.add((blankCheck(setup.getS3HeadTxtFld().getText(), 2)));
        slList.add((blankCheck(setup.getS4HeadTxtFld().getText(), 3)));
        slList.add((blankCheck(setup.getS5HeadTxtFld().getText(), 4)));

        slList.add((blankCheck(setup.getS1TailTxtFld().getText(), 5)));
        slList.add((blankCheck(setup.getS2TailTxtFld().getText(), 6)));
        slList.add((blankCheck(setup.getS3TailTxtFld().getText(), 7)));
        slList.add((blankCheck(setup.getS4TailTxtFld().getText(), 8)));
        slList.add((blankCheck(setup.getS5TailTxtFld().getText(), 9)));

        slList.add((blankCheck(setup.getL1TopTxtFld().getText(), 10)));
        slList.add((blankCheck(setup.getL2TopTxtFld().getText(), 11)));
        slList.add((blankCheck(setup.getL3TopTxtFld().getText(), 12)));
        slList.add((blankCheck(setup.getL4TopTxtFld().getText(), 13)));
        slList.add((blankCheck(setup.getL5TopTxtFld().getText(), 14)));

        slList.add((blankCheck(setup.getL1BotTxtFld().getText(), 15)));
        slList.add((blankCheck(setup.getL2BotTxtFld().getText(), 16)));
        slList.add((blankCheck(setup.getL3BotTxtFld().getText(), 17)));
        slList.add((blankCheck(setup.getL4BotTxtFld().getText(), 18)));
        slList.add((blankCheck(setup.getL5BotTxtFld().getText(), 19)));

        // Checks ladder and snake's values are in correct order
        for (int i = 0; i <= 4; i++) {
            if (slList.get(i) <= slList.get(i + 5)) {
                sB.append(getType(i) + " " + getNum(i) + "'s values are not correct \n");
            }

        }

        for (int i = 10; i <= 14; i++) {
            if (slList.get(i) <= slList.get(i + 5)) {
                sB.append(getType(i) + " " + getNum(i) + "'s values are not correct \n");
            }

        }

        // Checks snake head and tail and ladder top and bottom difference is not more
        // than 30
        int sHeadCount = 0;

        for (int i = 0; i <= 4; i++) {

            // Only non blank is checked
            if (!(slList.get(i) == null) && !(slList.get(i + 5) == null) && slList.get(i) - slList.get(i + 5) > 30) {
                sB.append(getType(i) + " " + getNum(i) + "'s difference is greater than 30\n");
            }

            // Only one snake (head) can be in locations 81 to 100 at any one time.
            if (slList.get(i) >= 81 && slList.get(i) <= 100) {
                sHeadCount++;

                if (sHeadCount > 1) {
                    sB.append("There are more than 1 snake head in grid 81 to 100\n");
                }

            }

        }

        // Ensures a ladder base cannot be placed on top of another ladder’s head
        for (int i = 15; i <= 19; i++) {
            for (int j = 10; j <= 14; j++) {
                if (slList.get(i) == slList.get(j)) {
                    sB.append(getType(i) + " " + getNum(i) + " " + getPos(i) + " is the same as " + getType(j) + " "
                                      + getNum(j) + " " + getPos(j) + "\n");
                }
            }

        }

        // Ensures a ladder top or base cannot be placed on a snake’s head
        // Ensures a snake’s head cannot be placed on top of an existing ladder head,
        // ladder base or next to another snake’s head
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 19; j++) {

                if ((i >= 5 && i <= 9) || i == j) {
                    break;
                }

                if (slList.get(i) == slList.get(j)) {
                    sB.append(getType(i) + " " + getNum(i) + " " + getPos(i) + " is the same as " + getType(j) + " "
                                      + getNum(j) + " " + getPos(j) + "\n");
                }

                if (slList.get(i) == slList.get(j) + 1 || slList.get(i) == slList.get(j) - 1) {
                    sB.append(getType(i) + " " + getNum(i) + " " + getPos(i) + " is next to " + getType(j) + " "
                                      + getNum(j) + " " + getPos(j) + "\n");
                }
            }

        }

        // There should be no ladder base at location 1.
        for (int i = 14; i <= 19; i++) {
            if (slList.get(i) == 1) {
                sB.append(getType(i) + " " + getNum(i) + " " + getPos(i) + " cannot be in grid 1\n");
            }
        }

        // There should be no ladder top at location 100.
        for (int i = 9; i <= 14; i++) {
            if (slList.get(i) == 100) {
                sB.append(getType(i) + " " + getNum(i) + " " + getPos(i) + " cannot be in grid 100\n");
            }
        }

        setup.setErrorText("Error:\n" + sB.toString());

        return valid;
    }

    public String getType(int i) {
        String s;

        if (i <= 9) {
            s = "Snake";
        } else {
            s = "Ladder";
        }

        return s;
    }

    public int getNum(int i) {
        if (i <= 4) {
            i = i + 1;
        } else if (i >= 5 && i <= 9) {
            i = i + 1 - 5;
        } else if (i >= 10 && i <= 14) {
            i = i + 1 - 10;
        } else {
            i = i + 1 - 15;
        }

        return i;
    }

    public String getPos(int i) {
        String s;

        if (i <= 4) {
            s = "Head";
        } else if (i >= 5 && i <= 9) {
            s = "Tail";
        } else if (i >= 10 && i <= 14) {
            s = "Top";
        } else {
            s = "Bot";
        }

        return s;
    }

    public int blankCheck(String text, int i) {
        int j = 0;

        try {
            j = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            sB.append(getType(i) + " " + getNum(i) + "'s " + getPos(i) + " is blank\n");
        }

        return j;
    }

    // This method creates Snakes and Ladders at fixed positions for Part A
    // Start-up code is given just to get you started
    // For Part B, modify this code to allow users to specify snake, ladder and trap
    // positions
    // You must enforce all design rules when placing snakes, ladders and traps
    // If the design rules are not violated users must re-enter values
    public void setup(Board bd) {
        // int choice = 0;

        bd.add(new Trap(25, 3));
        bd.add(new Trap(95, 3));
        // trapsCount = 2;

        for (int i = 0; i < 5; i++) {

            bd.add(new Snake(slList.get(i), slList.get(i + 5)));
        }

        for (int i = 10; i < 15; i++) {

            bd.add(new Ladder(1, slList.get(i), slList.get(i + 5)));
        }

    }

    // A method to print a message and to read an int value in the range specified
    int getInt(String message, int from, int to) {
        String s;
        int n = 0;
        boolean invalid;

        do {
            invalid = false;
            s = (String) JOptionPane.showInputDialog(bd, message, "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
            try {
                n = Integer.parseInt(s);
                if (n < from || n > to) {
                    plainMessage("Re-enter: Input not in range " + from + " to " + to);
                }
            } catch (NumberFormatException nfe) {
                plainMessage("Re-enter: Invalid number");
                invalid = true;
            }
        } while (invalid || n < from || n > to);

        return n;
    }

    // A method to print a message and to read a String
    String getString(String message) {
        String s = (String) JOptionPane.showInputDialog(bd, message, "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
        return s;
    }

    // A method to print a message
    void plainMessage(String message) {
        JOptionPane.showMessageDialog(bd, message, "A prompt message", JOptionPane.PLAIN_MESSAGE);
    }

}