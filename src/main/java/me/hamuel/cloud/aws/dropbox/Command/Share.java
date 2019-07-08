package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.*;

public class Share implements Command {
    @Override
    public void apply(String args) {
        if(User.isLoggedIn()){
            String[] tokens = args.split(" ");
            String filename = tokens[0];
            String shareTo = tokens[1];
            ShareService shareService = new ShareService();
            SqlQuery sqlQuery = new SqlQuery();
            UserService userService = new UserService(sqlQuery);
            StorageService storageService = new StorageService();

            if(userService.isUserExists(shareTo) && storageService.isFileExist(filename)){
                shareService.shareTo(shareTo, filename, User.getUsername());
                System.out.println("Successfully share the file");
            }else{
                System.out.println("Fail to share the file");
            }
        }else {
            System.out.println("You are not logged in");
        }

    }
}
