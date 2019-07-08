package me.hamuel.cloud.aws.dropbox.Command;

import me.hamuel.cloud.aws.dropbox.SqlQuery;

public class Quit implements Command {
    /**
     * quit the application no argument
     * is not required
     * @param args
     */
    @Override
    public void apply(String args) {
        System.out.println("===============================");
        System.out.println("Bye Bye Thank you for using myDropBox");
        System.exit(0);
        new SqlQuery().close();
    }
}
