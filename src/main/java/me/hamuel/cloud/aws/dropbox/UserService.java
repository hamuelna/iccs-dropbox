package me.hamuel.cloud.aws.dropbox;

public class UserService {
    private SqlQuery sqlQuery;

    public UserService(SqlQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public boolean createNewUser(String username, String password){
        return sqlQuery.addNewUser(username, password);
    }

    public boolean isValidUser(String username, String password){
        return sqlQuery.checkUser(username, password);
    }

    public boolean isUserExists(String username){
        return sqlQuery.isUserExist(username);
    }
}
