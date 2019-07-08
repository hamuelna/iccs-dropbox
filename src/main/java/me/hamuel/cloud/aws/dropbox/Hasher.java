package me.hamuel.cloud.aws.dropbox;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    private static final String salt = BCrypt.gensalt();

    /**
     * Hash the password using bcrypt salt
     * @param password
     * @return hashedPassword
     */
    public static String genHashPass(String password){
        return BCrypt.hashpw(password, salt);
    }

    /***
     * Check if the hash password match with the real password
     * @param hashpassword
     * @param password
     * @return
     */
    public static boolean checkPass(String hashpassword, String password){
        return BCrypt.checkpw(password, hashpassword);
    }

}
