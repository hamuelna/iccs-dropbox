package me.hamuel.cloud.aws.dropbox.Command;

public interface Command {
    /**
     * Run the command
     * @param args
     */
    void apply(String args);
}
