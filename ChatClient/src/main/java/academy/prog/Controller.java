package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Scanner scanner = new Scanner(System.in);
    private String login;
    private Users user;
    private String text;
    private int res;

    public void controlProcess(){
        try {
            System.out.println("Enter your login: ");
            login = scanner.nextLine();
            GetThread.setLogin(login);

            Thread th = new Thread(new GetThread());
            th.setDaemon(true);
            th.start();

            user = Users.createUserConstructor(login);
            user.addUsers(Utils.getURL() + "/users", login);

            System.out.println("Enter your message: ");
            while (true) {
                String answer = Users.getUsers(Utils.getURL() + "/users");
                text = scanner.nextLine();

                if (text.isEmpty()) break;

                if (text.equals("/list")) {
                    System.out.println("Users: [" + answer + "]");
                    continue;
                } else if (text.equals("/to")) {
                    to(answer);
//                } else if (text.equals("/file")) {
//                    file(answer);
                } else {
                    Message m = new Message(login, text);
                    res = m.send(Utils.getURL() + "/add");
                }
                if (res != 200) { // 200 OK
                    System.out.println("HTTP error occurred: " + res);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void to(String answer) throws IOException {
        System.out.println("Select user: [" + answer + "]");
        String toUser = scanner.nextLine();
        System.out.println("Enter your message to user " + toUser + ": ");
        text = scanner.nextLine();
        Message m = new Message(login, toUser, text);
        res = m.send(Utils.getURL() + "/add");
        m.setTo(null);
    }

//    private void file(String answer) throws IOException {
//        System.out.println("Select user: [" + answer + "]");
//        String toUser = scanner.nextLine();
//        System.out.println("Send link for file: ");
//        text = scanner.nextLine();
//        int lastIndex = text.lastIndexOf("/");
//        String file = text.substring(lastIndex + 1);
//        Message m = new Message(login, toUser, "File: " + file, file);
//        res = m.send(Utils.getURL() + "/add");
//    }
}
