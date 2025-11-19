import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final String playerName;
    private final String difficulty;
    private int health;
    private final int maxHealth;
    private final int attack;
    private int day;
    private final ArrayList<String> inventory;
    private final Scanner scanner;

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
        inventory = new ArrayList<>();
    }

    public void start() {
        System.out.println("Welcome " + playerName + "! You have chosen " + difficulty + " mode. Best of luck and be not afraid.");
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("stuff");
        inventory.add("other stuff");
        inventory.add("other stuff");
        inventory.add("other stuff");
        inventory.add("other stuf");
        gameLoop();
    }

    private void gameLoop() {
        //a while loop that runs for 20 days or whenever the player dies
        //Shows the day and player info
        //Option to explore or rest
        while (day <= 20 && health != 0) {
            System.out.println("Day: " + day + " Player info: Health " + health + "/" + maxHealth + " Attack damage " + attack);
            showInventory();
            System.out.print("Do you want to explore today? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                explore();
            }
            day++;
        }

        if (health != 0) {
            bossBattle();
        }

        gameOverStats();
    }

    private void showInventory() {
        int n = inventory.toArray().length;
        String[] items = new String[n];
        System.out.println("Inventory: ");
        for (int i = 0; i < inventory.toArray().length; i++) {
            String item = inventory.get(i);
            if (!Arrays.asList(items).contains(item)) {

                int count = 0;

                for (int j = 0; j < inventory.toArray().length; j++) {
                    String currentItem = inventory.get(j);
                    if (currentItem.equals(item)) {
                        count++;
                    }
                }

                System.out.println(item + " (" + count + ")");
                items[i] = item;

            }
        }
    }

    private void explore() {
        int random = (int) (Math.random() * 100);
        if (difficulty.equals("hard")) {
            if (random < 50) {
                enemyFight();
            } else if (random <= 55) {
                int randomHeal = (int) (Math.random() * maxHealth);
                heal(randomHeal);
            } else if (random <= 85) {
                poiEncounter();
            }
        }
    }

    private void poiEncounter() {
    }

    private void heal(int randomHeal) {
        health += randomHeal;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    private void enemyFight() {
    }

    private void bossBattle() {}

    private void gameOverStats() {}

}
