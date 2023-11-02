package jc;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random random = new SecureRandom();
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<List<Character>> disks = List.of(
            new ArrayList<>(List.of('Y', 'N', 'T', 'E', 'M', 'O', 'G', 'A', 'I', 'V', 'J', 'Q', 'F', 'S', 'L', 'U', 'C', 'H', 'K', 'Z', 'P', 'W', 'D', 'B', 'R', 'X')),
            new ArrayList<>(List.of('I', 'C', 'N', 'M', 'T', 'X', 'K', 'O', 'B', 'W', 'F', 'Q', 'A', 'P', 'V', 'Y', 'J', 'Z', 'E', 'L', 'U', 'R', 'S', 'H', 'D', 'G')),
            new ArrayList<>(List.of('A', 'Y', 'D', 'T', 'V', 'J', 'N', 'R', 'C', 'W', 'I', 'H', 'E', 'S', 'B', 'X', 'L', 'P', 'O', 'G', 'K', 'F', 'M', 'Z', 'Q', 'U')),
            new ArrayList<>(List.of('S', 'L', 'K', 'F', 'R', 'H', 'E', 'Z', 'T', 'M', 'Q', 'B', 'U', 'A', 'C', 'X', 'Y', 'I', 'J', 'N', 'D', 'V', 'P', 'G', 'O', 'W')),
            new ArrayList<>(List.of('G', 'D', 'M', 'R', 'Y', 'O', 'V', 'H', 'L', 'J', 'K', 'T', 'A', 'N', 'E', 'P', 'X', 'F', 'W', 'Q', 'Z', 'C', 'U', 'I', 'S', 'B')),
            new ArrayList<>(List.of('F', 'T', 'C', 'K', 'M', 'A', 'N', 'B', 'I', 'U', 'O', 'W', 'Y', 'P', 'Z', 'L', 'E', 'J', 'G', 'Q', 'R', 'S', 'H', 'V', 'D', 'X'))
    );

    public static void main(String[] args) {
        System.out.println("Do you want to encrypt or decrypt? [e/d]");
        String input = scanner.nextLine();

        char c = input.toCharArray()[0];
        if (c == 'e' || c == 'E') {
            encrypt();
        } else if (c == 'd' || c == 'D') {
            decrypt();
        } else {
            throw new RuntimeException("Invalid input");
        }
    }

    private static void encrypt() {
        showDisks();

        System.out.println("Enter message (maximum length " + disks.size() + " characters):");
        String message = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        List<Integer> orderOfDisks = getOrderOfDisks();

        List<Integer> rotations = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            int currentDiskIdx = orderOfDisks.get(i) - 1;
            List<Character> currentDisk = disks.get(currentDiskIdx);

            char m = message.charAt(i);
            rotations.add(currentDisk.indexOf(m));
        }

        StringBuilder encryption = new StringBuilder();
        int rgn = random.nextInt(0, 1000);
        for (int i = 0; i < rotations.size(); i++) {
            int currDskIdx = orderOfDisks.get(i) - 1;
            List<Character> currDsk = disks.get(currDskIdx);

            int n = rotations.get(i);
            char c = currDsk.get((n + rgn) % currDsk.size());
            encryption.append(c);
        }

        System.out.println(encryption);
    }

    private static void showDisks() {
        System.out.println("Disk configuration:");
        for (int i = 0; i < disks.size(); i++) {
            System.out.print((i + 1) + ":\t");
            List<Character> v = disks.get(i);
            for (char c : v) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private static void decrypt() {
        System.out.println("Enter an encrypted message (maximum length " + disks.size() + " characters):");
        String message = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        List<Integer> orderOfDisks = getOrderOfDisks();

        for (int i = 0; i < message.length(); i++) {
            int currDskIdx = orderOfDisks.get(i) - 1;
            List<Character> currDsk = disks.get(currDskIdx);

            char m = message.charAt(i);
            while (currDsk.get(0) != m) {
                char aux = currDsk.remove(0);
                currDsk.add(aux);
            }
        }
        showDisks();
    }

    private static List<Integer> getOrderOfDisks() {
        System.out.println("Enter the order of the disks (e.g. 1 2 3 4 5 6):");
        List<Integer> orderOfDisks = new ArrayList<>();
        while (orderOfDisks.size() < disks.size()) {
            int n = scanner.nextInt();
            if (orderOfDisks.contains(n)) {
                throw new RuntimeException("Cannot use a disk two times already contains (disk " + n + ")");
            } else {
                orderOfDisks.add(n);
            }
        }
        return orderOfDisks;
    }

    /**
     * Generates a random permutation of the alphabet, you can use this to add more disks
     */
    @SuppressWarnings("unused")
    private static void generatePermutationOfAlphabet() {
        List<Character> alphabet = new ArrayList<>();
        for (char i = 'A'; i <= 'Z'; i++)
            alphabet.add(i);


        StringBuilder sb = new StringBuilder();

        while (alphabet.size() > 1) {
            char ch = alphabet.remove(random.nextInt(0, alphabet.size()));

            sb.append("'").append(ch).append("',");
        }
        sb.append("'").append(alphabet.get(0)).append("'");

        System.out.println("{" + sb + "},");
    }
}