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
    private final boolean isAlive;
    private final ArrayList<String> inventory;
    private final String[] items;
    private final String[] itemStats;
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
        isAlive = true;
        inventory = new ArrayList<>();
        items = new String[]{"stuff", "other stuff", "more stuff"};
        itemStats = new String[]{"1", "5", "6 10"};
    }

    public void start() {
        System.out.println("Welcome " + playerName + "! You have chosen " + difficulty + " mode. Best of luck and be not afraid.");
        gameLoop();
    }

    private void gameLoop() {
        //a while loop that runs for 20 days or whenever the player dies
        //Shows the day and player info
        //Option to explore or rest
        while (day <= 20 && isAlive) {
            System.out.println("Day: " + day + " Player info: Health " + health + "/" + maxHealth + " Attack damage " + attack);
            showInventory();
            System.out.print("Do you want to explore today? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("y")) {
                explore();
            } else {
                heal(10);
            }
            day++;
        }

        if (isAlive) {
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
            } else if (random <= 100) {
                randomItem();
            }
        }
    }

    private void randomItem() {
    int length = items.length;
    int num = (int) (Math.random() * length);
    String randomItem = items[num];
    inventory.add(randomItem);
    }

    private void poiEncounter() {
    }

    private void heal(int healAmount) {
        health += healAmount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    private void enemyFight() {
        int randNum;
        if (difficulty.equals("hard")) {
            randNum = (int) (Math.random() * 100);
        } else if (difficulty.equals("medium")) {
            randNum = (int) (Math.random() * 90);
        } else {
            randNum = (int) (Math.random() * 80);
        }

        if (randNum <= 50) {
            //hard boss
        } else if (randNum <= 75) {
            // medium
        } else {
            // easy
        }
    }

    private void bossBattle() {}

    private void gameOverStats() {
        if (isAlive) {
            System.out.println("Congrats on surviving 20 day! You're built like a tiger. How about a harder mode.");
        } else {
            System.out.println("Aww unfortunate that you died. Maybe you should've been more careful. Try again.");
        }
    }

}
