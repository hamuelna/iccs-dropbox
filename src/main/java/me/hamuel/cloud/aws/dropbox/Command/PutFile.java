package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.StorageService;
import me.hamuel.cloud.aws.dropbox.User;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class PutFile implements Command {
    /**
     * upload the file into the service
     * where the argument is path to where
     * you want to upload the file
     * @param args
     */
    @Override
    public void apply(String args) {
        if(StringUtils.isBlank(args)){
            System.out.println("The argument is blank");
            return;
        }else if (!User.isLoggedIn()){
            System.out.println("You are not logged in");
            return;
        }
        File file = new File(StringUtils.strip(args));
        if(file.exists()){
            StorageService storageService = new StorageService();
            storageService.put(file);
        }else {
            System.out.println("The file does not exists");
        }
    }
}
