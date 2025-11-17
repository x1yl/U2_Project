import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("What is your name? ");
        String name = scanner.nextLine();

        //Repeatedly as the player for their chosen difficulty until their input is "easy, medium, or hard"
        String difficulty = "";
        while (!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
            System.out.print("Choose your difficulty (easy, medium, hard): ");
            difficulty = scanner.nextLine();
        }

        Game game = new Game(name, difficulty, scanner);
        game.start();
    }
}
