package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.StorageService;
import me.hamuel.cloud.aws.dropbox.User;
import org.apache.commons.lang3.StringUtils;

public class GetFile implements Command {
    /***
     * Get the file that the user own you need to put
     * the filename as an argument if there is you specified from which user
     * we will get the file you are shared with if not it will get the file
     * you own only
     * @param args
     */
    @Override
    public void apply(String args) {
        if (User.isLoggedIn()){
            String[] token = args.split(" ");
            StorageService storageService = new StorageService();
            if(token.length == 1){
                if(storageService.get(StringUtils.strip(args))){
                    System.out.println("file downloaded");
                }else {
                    System.out.println("fail to download file");
                }

            }else {
                String file = token[0];
                String owner = token[1];
                if(storageService.get(file, owner)){
                    System.out.println("file downloaded");
                }else {
                    System.out.println("fail to download file");
                }

            }

        }else {
            System.out.println("You are not logged into the system");
        }

    }
}
