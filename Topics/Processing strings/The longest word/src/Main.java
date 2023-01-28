import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] words = line.split(" ");
        int longWord = 0;
        int dim = 0;
        int dimW = 0;
        for (int i = 0; i < words.length; i++) {
            dimW = words[i].length();
            if (dimW > dim) {
                dim = dimW;
                longWord = i;
            }
        }
        System.out.println(words[longWord]);
    }
}