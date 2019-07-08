package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.SqlQuery;
import me.hamuel.cloud.aws.dropbox.UserService;

public class NewUser implements Command {
    /**
     * Create a new user to be use with the system
     * where the argument is "username password"
     * @param args
     */
    @Override
    public void apply(String args) {
        String[] argTokens = args.split(" ");
        String username = argTokens[0];
        String password = argTokens[1];

        UserService userService = new UserService(new SqlQuery());
        boolean isValid = userService.createNewUser(username, password);
        if(isValid){
            System.out.println("OK");
        }else{
            System.out.println("Cannot create new user");
        }
    }
}
