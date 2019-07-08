package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.User;

public class Logout implements Command{
    /***
     * Log the user out from the system
     * @param args
     */
    @Override
    public void apply(String args) {
        if(User.isLoggedIn()){
            User.setUsername(null);
            System.out.println("Successfully Logout");
        }else {
            System.out.println("You are not logged in");
        }

    }
}
