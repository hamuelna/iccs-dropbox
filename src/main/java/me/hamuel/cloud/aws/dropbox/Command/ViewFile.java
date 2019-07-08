package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.StorageService;
import me.hamuel.cloud.aws.dropbox.User;

public class ViewFile implements Command {
    /**
     * Display all the files that the user owned
     * @param args
     */
    @Override
    public void apply(String args) {
        if(User.isLoggedIn()){
            StorageService storageService = new StorageService();
            System.out.println("Listing all files for " + User.getUsername());
            System.out.printf(storageService.view());
        }else{
            System.out.println("Not logged in");
        }

    }
}
