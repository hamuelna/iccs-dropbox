package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.User;

public class Who implements Command {
    /**
     * Display the username of the current user
     * that is loggedIn
     * @param args
     */
    @Override
    public void apply(String args) {
        if(User.isLoggedIn()){
            System.out.println("You are not logged in");
        }else{
            System.out.println(User.getUsername());
        }
    }
}
