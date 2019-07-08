package me.hamuel.cloud.aws.dropbox;

import me.hamuel.cloud.aws.dropbox.Command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineParser {
    private Map<String, Command> commands = new HashMap<String, Command>(){{
        put("newuser", new NewUser());
        put("login", new Login());
        put("logout", new Logout());
        put("put", new PutFile());
        put("get", new GetFile());
        put("view", new ViewFile());
        put("quit", new Quit());
        put("whoami", new Who());
        put("share", new Share());
    }};

    /**
     * Run the main loop of the application
     */

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String equal = "===============================";
        System.out.printf("Welcome to myDropbox Application \n" +
        equal + "\n" + "Please input command (newuser username password password, login username password, put filename, \n " +
                "get filename, view, or logout). If you want to quit the program just type quit.\n" + equal + "\n");
        while (true){
            System.out.printf(">> ");
            String[] tokens = scanner.nextLine().split(" ");
            String cmd = tokens[0];
            String args = argsJoiner(tokens);
            if(commands.containsKey(cmd)){
                commands.get(cmd).apply(args);
            }else{
                System.out.println("Not supported command");
            }

        }
    }

    /**
     * Extract the argument from the tokens of commands
     * @param tokens
     * @return argument
     */
    private String argsJoiner(String[] tokens){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < tokens.length; i++){
            sb.append(tokens[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}
