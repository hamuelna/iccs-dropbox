package me.hamuel.cloud.aws.dropbox;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="myDropbox_FileItem")
public class FileItem {
    private String username;
    private String s3key;

    @DynamoDBHashKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBRangeKey
    public String getS3key() {
        return s3key;
    }

    public void setS3key(String s3key) {
        this.s3key = s3key;
    }
}
