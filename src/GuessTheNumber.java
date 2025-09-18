import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    // Scanner til at læse input fra brugeren
    static Scanner input = new Scanner(System.in);

    // ANSI escape codes (bruges til farver og tekst-styling i terminalen)
    static final String RESET = "\u001B[0m";
    static final String UNDERLINE = "\u001B[4m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String RED = "\u001B[31m";

    public static void main(String[] args) {
        printWelcomeMessage();

        boolean playAgain = true;   // styrer om spillet fortsætter
        int antalSpil = 0;          // tæller hvor mange spil der er spillet
        int totalAttempts = 0;      // tæller hvor mange gæt i alt

        // Spilløkke - kører så længe brugeren svarer "ja"
        while (playAgain) {
            int attempts = playGame();   // playGame returnerer antal gæt
            antalSpil++;
            totalAttempts += attempts;

            // Spørg om brugeren vil spille igen
            playAgain = handlePlayAgain();
            if (playAgain) {
                // Hvis ja → start en ny runde
                System.out.println("\n----------------------------------------------------");
                System.out.println("=== NY RUNDE ===");
                System.out.println("----------------------------------------------------");
            }
        }

        printFinalStats(antalSpil, totalAttempts);
        // Lukker scanner til allersidst
        input.close();
    }
    // --- Metode til at vælge sværhedsgrad ---
    // Returnerer det maksimale tal, der kan gættes på (10, 100, 1000)
    public static int chooseDifficulty() {
            System.out.println("\nVælg sværhedsgrad: ");
            System.out.println("1 = " + GREEN + "⬤ " + RESET + "Let (0-10)");
            System.out.println("2 = " + YELLOW + "⬤ " + RESET + "Middel (0-100)");
            System.out.println("3 = " + RED + "⬤ " + RESET + "Svær (0-1000)");

            while (true) {
                System.out.print("Indtast dit valg (1-3): ");

            if (input.hasNextInt()) {
                int choice = input.nextInt();

                if (choice == 1) return 10;
                else if (choice == 2) return 100;
                else if (choice == 3) return 1000;
                else System.out.println("\nUgyldigt valg, prøv igen! (vælg 1, 2 eller 3)");
            } else {
                // hvis brugeren skrev fx "ost"
                System.out.println("\nUgyldigt input\nDu skal indtaste et tal (vælg 1, 2 eller 3).");
                input.next(); // rydder det forkerte input
            }
        }
    }
    // --- Metode til at tjekke brugerens gæt ---
    // Returnerer en tekst: "for lavt", "for højt" eller "korrekt"
    public static String checkGuess(int guess, int target) {
        if (guess < target) {
            return "for lavt";
        } else if (guess > target) {
            return "for højt";
        } else {
            return "korrekt";
        }
    }
    // --- Selve spillet ---
    // Kører et spil, indtil brugeren har gættet det rigtige tal
    public static int playGame() {
        int max = chooseDifficulty(); // vælg sværhedsgrad
        Random rand = new Random();
        int target = rand.nextInt(max + 1); // vælg tilfældigt tal

        System.out.println("\nJeg har valgt et tal mellem " + GREEN + 0 + RESET + " og " + GREEN + max + RESET + ". Gæt hvilket!");
        System.out.print("Indtast dit gæt: ");

        boolean guessedCorrectly = false;
        int attempts = 0; // tæller antallet af gæt

        while (!guessedCorrectly) {
            if (input.hasNextInt()) {
                int guess = input.nextInt();
                attempts++; // hver gang brugeren gætter, tælles der et forsøg

                String result = checkGuess(guess, target);

                if (result.equals("for lavt")) {
                    System.out.println("Dit gæt er for lavt, prøv igen! (0-" + max + ")");
                    System.out.print("\nIndtast dit gæt: ");
                } else if (result.equals("for højt")) {
                    System.out.println("Dit gæt er for højt, prøv igen! (0-" + max + ")");
                    System.out.print("\nIndtast dit gæt: ");
                } else {
                    // Når svaret er korrekt:
                    System.out.println("\n----------------------------------------------------");
                    System.out.println("\n\uD83C\uDF89 Tillykke! Du gættede rigtigt på " + GREEN + UNDERLINE + attempts + RESET + " forsøg. \uD83C\uDF89\n");
                    System.out.println("----------------------------------------------------\n");
                    guessedCorrectly = true;
                }
            } else {
                // Hvis input ikke er et tal
                System.out.println("\nHov hov du, kun tal tak! Prøv igen.");
                System.out.print("Indtast dit gæt: ");
                input.next(); // rydder det forkerte input
            }
        }
        return attempts; // giver antallet af forsøg tilbage til main
    }
    // --- Spørger om man vil spille igen ---
    // Brugeren SKAL svare "ja" eller "nej"
    // .toLowerCase(); sørger for man kan skrive det småt eller stort.
    public static boolean handlePlayAgain() {
        while (true) {
            System.out.println("Vil du spille igen?");
            System.out.print("Indtast dit valg (ja/nej): ");
            String choice = input.next().toLowerCase();

            switch (choice) {
                case "ja":
                    return true;
                case "nej":
                    return false;
                default:
                    System.out.println("\n*Suk* Ugyldigt input...");
            }
        }
    }
        // --- Udskriver velkomstbesked ---
        public static void printWelcomeMessage() {
            System.out.println("\n=== Velkommen til Gæt et tal spillet! ===");
    }
        // --- Udskriver slutstatistik ---
        public static void printFinalStats(int antalSpil, int totalAttempts) {
            System.out.println("\n----------------------------------------------------\n");
            System.out.println("👋 Tak for spillet! 👋");
            System.out.println("Du spillede " + antalSpil + " runde(r).");
            System.out.println("Samlet antal gæt: " + totalAttempts);
            double gennemsnit = (double) totalAttempts / antalSpil;
            System.out.printf("Gennemsnitlige gæt pr. spil: %.2f%n", gennemsnit);
            System.out.println("\n----------------------------------------------------");
    }
}