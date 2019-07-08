package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.SqlQuery;
import me.hamuel.cloud.aws.dropbox.User;
import me.hamuel.cloud.aws.dropbox.UserService;

public class Login implements Command {
    /**
     * authenticate the user into the system to use the service
     * the args must include "username password"
     * @param args
     */
    @Override
    public void apply(String args) {
        String[] argTokens = args.split(" ");
        if (argTokens.length != 2){
            System.out.println("missing argument");
        }else{
            UserService userService = new UserService(new SqlQuery());
            String username = argTokens[0];
            String password = argTokens[1];
            if(userService.isValidUser(username, password)){
                User.setUsername(username);
                System.out.println("Successfully login");
            }else{
                System.out.println("Invalid login and password");
            }
        }
    }
}
