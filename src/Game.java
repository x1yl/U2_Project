import java.util.ArrayList;
import java.util.Arrays;
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
        while (day <= 20 && health != 0) {
            System.out.println("Day: " + day + " Player info: Health " + health + "/" + maxHealth + " Attack damage " + attack);
            showInventory();
            day++;
        }

        // only if the player is alive after the 20th day spawn a boss battle

        //Show player their stats after the game ends
    }

    private void showInventory() {
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("other stuff");
        inventory.add("other stuff");
        inventory.add("other stuff");
        inventory.add("other stuf");
        int n = inventory.toArray().length;
        String[] items = new String[n];
        System.out.println("Inventory: ");
        for (int i = 0; i < inventory.toArray().length; i++) {
            String item = inventory.get(i);
            if (!Arrays.asList(items).contains(item)) {

                int count = 0;

                for (int j = 0; j < inventory.toArray().length; j++) {
                    if (item.equals(inventory.get(i))) {
                        count++;
                    }
                }

                System.out.println(item + "(" + count + ")");
                items[i] = item;

            }
        }
    }

}
