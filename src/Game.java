import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private String playerName, difficulty;
    private int health, maxHealth, attack, day;
    private ArrayList<String> inventory;
    private Scanner scanner;

    public Game(String name, String difficulty, Scanner scanner) {
        this.playerName = name;
        this.difficulty = difficulty;
        this.scanner = scanner;

        if (difficulty.equals("easy")) {
            health = 25;
            maxHealth = 25;
            attack = 6;
        } else if (difficulty.equals("medium")) {
            health = 20;
            maxHealth = 20;
            attack = 5;
        } else {
            health = 15;
            maxHealth = 15;
            attack = 4;
        }

        day = 1;
        inventory = new ArrayList<String>();
    }

    public void start() {
        System.out.println("Welcome " + playerName + "! You have chosen " + difficulty + " mode. Best of luck and be not afraid.");
        gameLoop();
    }

    private void gameLoop() {
        //a while loop that runs for 20 days or whenever the player dies
        //Shows the day and player info
        //Option to explore or rest

        // only if the player is alive after the 20th day spawn a boss battle

        //Show player their stats after the game ends
    }

}
