import java.util.Scanner;

abstract class SocialNetwork {

    public void connect() {
        LogIn();
        PostAMessage();
        LogOut();
    }
    abstract void LogIn();
    abstract void PostAMessage();
    abstract void LogOut();
}

class Instagram extends SocialNetwork {
    @Override
    void LogIn() {
        System.out.println("Log into Instagram");
    }

    @Override
    void PostAMessage() {
        System.out.println("Post: Hello, Instagram!");
    }

    @Override
    void LogOut() {
        System.out.println("Log out of Instagram");
    }
}


class Facebook  extends SocialNetwork {
    @Override
    void LogIn() {
        System.out.println("Log into Facebook");
    }

    @Override
    void PostAMessage() {
        System.out.println("Post: Hello, Facebook!");
    }

    @Override
    void LogOut() {
        System.out.println("Log out of Facebook");
    }
    // write your code here ...
}

// Do not change the code below
class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String type = scanner.nextLine();
        scanner.close();
        SocialNetwork network = null;
        if ("facebook".equalsIgnoreCase(type)) {
            network = new Facebook();
        } else if ("instagram".equalsIgnoreCase(type)) {
            network = new Instagram();
        } else {
            System.out.println("Error!");
            System.exit(0);
        }
        network.connect();
    }
}