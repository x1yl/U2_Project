import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private final String playerName;
    private final String difficulty;
    private final int maxHealth;
    private final ArrayList<String> inventory;
    private final String[] items;
    private final int[] itemStats;
    private final Scanner scanner;
    private int health;
    private final int attack;
    private int day;
    private int tempAttackBoost;
    private String equippedSword;
    private int equippedSwordDamage;

    public Game(String name, String difficulty, Scanner scanner) {
        this.playerName = name;
        this.difficulty = difficulty;
        this.scanner = scanner;

        if (difficulty.equals("easy")) {
            maxHealth = 120;
            health = maxHealth;
            attack = 35;
        } else if (difficulty.equals("medium")) {
            maxHealth = 100;
            health = maxHealth;
            attack = 30;
        } else {
            maxHealth = 90;
            health = maxHealth;
            attack = 25;
        }

        day = 1;
        inventory = new ArrayList<>();
        items = new String[]{"Iron Sword", "Steel Sword", "Diamond Sword", "Health Potion", "Super Health Potion", "Damage Potion"};
        itemStats = new int[]{8, 12, 16, 20, 40, 10};
        tempAttackBoost = 0;
        equippedSword = "None";
        equippedSwordDamage = 0;
    }

    public void start() {
        System.out.println("Welcome " + playerName + "! You have chosen " + difficulty + " mode. Best of luck and be not afraid.");
        gameLoop();
    }

    private void gameLoop() {
        while (day <= 20 && health > 0) {
            System.out.println();
            System.out.println("Day: " + day + " Player info: Health " + health + "/" + maxHealth + " Attack damage " + attack);
            System.out.println("Current attack boost: +" + tempAttackBoost);
            System.out.println("Equipped: " + equippedSword + " (+" + equippedSwordDamage + " damage)");
            showInventory();
            System.out.print("Do you want to (1) explore, (2) rest, (3) use item, or (4) forge item? ");
            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case 1:
                    explore();
                    day++;
                    break;
                case 2:
                    heal(10);
                    System.out.println("You rest and heal 10 HP.");
                    day++;
                    break;
                case 3:
                    useItem();
                    break;
                case 4:
                    forgeItem();
                    break;
                default:
                    System.out.println("Invalid choice! The day passes...");
                    day++;
            }
        }

        if (health > 0) {
            bossBattle();
        }

        gameOverStats();
    }

    private void gameOverStats() {
        System.out.println();
        System.out.println("=== GAME OVER ===");
        if (health > 0) {
            System.out.println("Congrats on surviving 20 days! You're built like a tiger. How about a harder mode.");
        } else {
            System.out.println("Aww unfortunate that you died on day " + day + ". Maybe you should've been more careful. Try again.");
        }
    }

    private void showInventory() {
        int n = inventory.toArray().length;
        String[] items = new String[n];
        System.out.println("Inventory: ");
        for (int i = 0; i < inventory.toArray().length; i++) {
            String item = inventory.get(i);
            if (!Arrays.asList(items).contains(item)) {

                int count = countItem(item);

                System.out.println(item + " (" + count + ")");
                items[i] = item;

            }
        }
    }

    private void explore() {
        int random = (int) (Math.random() * 100);
        if (random <= 25) {
            enemyFight();
        } else if (random <= 65) {
            randomItem();
        } else if (random <= 75) {
            poiEncounter();
        } else {
            int randomHeal = (int) (Math.random() * 20);
            heal(randomHeal);
            System.out.println("You found healing herbs and healed " + randomHeal + " HP!");
        }
    }

    private void randomItem() {
        int length = items.length;
        int num = (int) (Math.random() * length);
        String randomItem = items[num];
        inventory.add(randomItem);
        System.out.println("You found: " + randomItem);
        autoEquipBestSword();
    }

    private void poiEncounter() {
        System.out.println("You discovered an interesting location but found nothing useful.");
    }

    private void enemyFight() {
        int randNum = (int) (Math.random() * 100);
        int enemyHealth;
        int enemyAttack;
        int enemyDamage;
        String enemyName;

        if (randNum <= 50) {
            enemyName = "Tough Enemy";
            enemyHealth = 60;
            enemyAttack = 20;
        } else if (randNum <= 75) {
            enemyName = "Regular Enemy";
            enemyHealth = 40;
            enemyAttack = 12;
        } else {
            enemyName = "Weak Enemy";
            enemyHealth = 20;
            enemyAttack = 8;
        }

        System.out.println();
        System.out.println("You encountered a " + enemyName + "!");

        while (enemyHealth > 0 && health > 0) {
            System.out.println();
            System.out.println("Enemy health: " + enemyHealth + " | Your health: " + health);
            System.out.print("Do you want to (1) attack, (2) use item, or (3) run? ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int totalAttack = attack + tempAttackBoost + equippedSwordDamage;
                    int damageDealt = calculateRandomDamage(totalAttack);
                    enemyHealth -= damageDealt;
                    System.out.println("You dealt " + damageDealt + " damage (max: " + totalAttack + "). Enemy health: " + enemyHealth);

                    if (enemyHealth > 0) {
                        enemyDamage = calculateRandomDamage(enemyAttack);
                        health -= enemyDamage;
                        System.out.println("Enemy dealt " + enemyDamage + " damage (max: " + enemyAttack + "). Your health: " + health);
                    }
                    break;
                case 2:
                    useItem();
                    enemyDamage = calculateRandomDamage(enemyAttack);
                    health -= enemyDamage;
                    System.out.println("While using item, enemy dealt " + enemyDamage + " damage. Your health: " + health);
                    break;
                case 3:
                    int runChance = (int) (Math.random() * 100);
                    if (runChance < 50) {
                        System.out.println("You successfully ran away!");
                        return;
                    } else {
                        System.out.println("You failed to escape!");
                        health -= enemyAttack;
                        System.out.println("Enemy dealt " + enemyAttack + " damage while you tried to flee. Your health: " + health);
                    }
                    break;
                default:
                    System.out.println("Invalid choice! Enemy attacks while you hesitate!");
                    enemyDamage = calculateRandomDamage(enemyAttack);
                    health -= enemyDamage;
                    System.out.println("Enemy dealt " + enemyAttack + " damage. Your health: " + health);
            }
        }

        if (health <= 0) {
            System.out.println();
            System.out.println("You died in combat...");
        } else {
            System.out.println();
            System.out.println("You defeated the " + enemyName + "!");
            randomItem();
        }

        tempAttackBoost = 0;
    }

    private void bossBattle() {
        System.out.println();
        System.out.println("=== FINAL BOSS BATTLE ===");
        int bossHealth = 200;
        int bossAttack = 30;
        int bossDamage;

        System.out.println("The final boss appears! Health: " + bossHealth + ", Attack: " + bossAttack);

        while (bossHealth > 0 && health > 0) {
            System.out.println();
            System.out.println("Boss health: " + bossHealth + " | Your health: " + health);
            System.out.print("Do you want to (1) attack, (2) use item? ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    int totalAttack = attack + tempAttackBoost + equippedSwordDamage;
                    int damageDealt = calculateRandomDamage(totalAttack);
                    bossHealth -= damageDealt;
                    System.out.println("You dealt " + damageDealt + " damage (max: " + totalAttack + "). Boss health: " + bossHealth);

                    if (bossHealth > 0) {
                        bossDamage = calculateRandomDamage(bossAttack);
                        health -= bossDamage;
                        System.out.println("Boss dealt " + bossDamage + " damage (max: " + bossAttack + "). Your health: " + health);
                    }
                    break;
                case 2:
                    useItem();
                    bossDamage = calculateRandomDamage(bossAttack);
                    health -= bossDamage;
                    System.out.println("While using item, boss dealt " + bossDamage + " damage. Your health: " + health);
                    break;
                default:
                    System.out.println("Invalid choice! Boss attacks while you hesitate!");
                    bossDamage = calculateRandomDamage(bossAttack);
                    health -= bossDamage;
                    System.out.println("Boss dealt " + bossAttack + " damage. Your health: " + health);
            }
        }

        if (health <= 0) {
            System.out.println("You were defeated by the boss...");
        } else {
            System.out.println("You defeated the final boss!");
        }

        tempAttackBoost = 0;
    }

    private int calculateRandomDamage(int maxDamage) {
        return (int) (Math.random() * maxDamage) + 1;
    }

    private void useItem() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }

        System.out.println();
        System.out.println("Which item do you want to use?");
        int displayNumber = 1;
        for (int i = 0; i < items.length; i++) {
            if (!items[i].contains("Sword") && inventory.contains(items[i])) {
                System.out.println(displayNumber + ". " + items[i] + " (Effect: " + getItemDescription(i) + ")");
                displayNumber++;
            }
        }

        if (displayNumber == 1) {
            System.out.println("No usable items in inventory!");
            return;
        }

        System.out.print("Enter item number (or 0 to cancel): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            System.out.println("Cancelled.");
            return;
        }

        displayNumber = 1;
        int selectedIndex = -1;
        for (int i = 0; i < items.length; i++) {
            if (!items[i].contains("Sword") && inventory.contains(items[i])) {
                if (displayNumber == choice) {
                    selectedIndex = i;
                }
                displayNumber++;
            }
        }

        if (selectedIndex == -1) {
            System.out.println("Invalid choice!");
            return;
        }

        String selectedItem = items[selectedIndex];
        applyItemEffect(selectedIndex);
        inventory.remove(selectedItem);
        System.out.println("Used " + selectedItem + "!");
    }

    private String getItemDescription(int index) {
        String itemName = items[index];
        int stat = itemStats[index];

        if (itemName.contains("Health")) {
            return "Heal " + stat + " HP";
        } else if (itemName.contains("Damage")) {
            return "+" + stat + " attack damage for 1 fight";
        }
        return "Unknown effect";
    }

    private void applyItemEffect(int index) {
        String itemName = items[index];
        int stat = itemStats[index];

        if (itemName.contains("Health")) {
            heal(stat);
            System.out.println("You healed " + stat + " HP! Current health: " + health);
        } else if (itemName.contains("Damage")) {
            tempAttackBoost += stat;
            System.out.println("Your attack increased by " + stat + " for one fight! Current bonus: +" + tempAttackBoost);
        }
    }

    private void forgeItem() {
        System.out.println();
        System.out.println("=== FORGE ===");
    }

    private void autoEquipBestSword() {
        String bestSword = "None";
        int bestDamage = 0;

        String[] allSwords = {"Iron Sword", "Steel Sword", "Diamond Sword"};
        int[] allSwordDamages = {8, 12, 16};

        for (int i = 0; i < allSwords.length; i++) {
            if (inventory.contains(allSwords[i]) && allSwordDamages[i] > bestDamage) {
                bestSword = allSwords[i];
                bestDamage = allSwordDamages[i];
            }
        }

        if (!bestSword.equals(equippedSword)) {
            String oldSword = equippedSword;
            equippedSword = bestSword;
            equippedSwordDamage = bestDamage;
            if (!oldSword.equals("None")) {
                System.out.println("Unequipped " + oldSword + " and equipped " + bestSword + "!");
            } else {
                System.out.println("Equipped " + bestSword + "!");
            }
        }
    }

    private void heal(int healAmount) {
        health += healAmount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    private int countItem(String itemName) {
        int count = 0;
        for (int j = 0; j < inventory.toArray().length; j++) {
            String currentItem = inventory.get(j);
            if (currentItem.equals(itemName)) {
                count++;
            }
        }
        return count;
    }
}