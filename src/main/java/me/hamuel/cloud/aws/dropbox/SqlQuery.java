package me.hamuel.cloud.aws.dropbox;

import java.sql.*;

public class SqlQuery {
    private static Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private final String url = ""; //change it to a valid mysql url (rds url)
    private final String username = ""; //sql username
    private final String password = ""; //sql password

    public SqlQuery() {
        try {
            if(connection == null){
                System.out.println("Connecting database...");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    enum UserTable {
        username, password
    }

    /**
     * Add user to the database plus hash the password
     * @param username
     * @param password
     * @return true if success false if fail
     */
    public boolean addNewUser(String username, String password){
        try {
            preparedStatement = connection.prepareStatement("insert into users values(DEFAULT, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, Hasher.genHashPass(password));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Check whether the user exists or the username and password
     * match or not
     * @param username
     * @param password
     * @return true if valid false if not valid
     */
    public boolean checkUser(String username, String password){
        try {
            preparedStatement = connection.prepareStatement("select username, password from users where username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                return resultSet.getString(UserTable.username.toString()).equals(username) &&
                        Hasher.checkPass(resultSet.getString(UserTable.password.toString()), password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExist(String username){
        try {
            preparedStatement = connection.prepareStatement("select username from users where username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                return resultSet.getString(UserTable.username.toString()).equals(username);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * close the mysql connection if is connected already do nothing
     */
    public void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
