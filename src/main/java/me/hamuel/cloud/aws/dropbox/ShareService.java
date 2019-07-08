package me.hamuel.cloud.aws.dropbox;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareService {
    private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(new ProfileCredentialsProvider())
        .build();
    private DynamoDBMapper shareMapper = new DynamoDBMapper(client);

    /**
     * Share an object with another user
     * @param username
     * @param filename
     * @param owner
     */
    public void shareTo(String username, String filename, String owner){
        FileItem fileItem = new FileItem();
        fileItem.setUsername(username);
        fileItem.setS3key(owner + Config.delim() + filename);
        try{
            shareMapper.save(fileItem);
        } catch (AmazonServiceException ase) {
            System.out.println("Exception in service");
            System.out.println(ase.getMessage());
        } catch (AmazonClientException ace){
            System.out.println("Exception in client");
            System.out.println(ace.getMessage());
        }

    }

    public void shareTo(FileItem fileItem){
        shareMapper.save(fileItem);
    }

    /**
     * return all files you are shared with from dynamodb
     * @param username
     * @return list of fileItems or null if there is an error
     */

    public List<FileItem> getAllShareWith(String username){

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(username));

        DynamoDBQueryExpression<FileItem> queryExpression = new DynamoDBQueryExpression<FileItem>()
                .withKeyConditionExpression("username = :v1")
                .withExpressionAttributeValues(eav);

        try{
            return shareMapper.query(FileItem.class, queryExpression);
        }catch (AmazonServiceException ase) {
            System.out.println("Exception in service");
            System.out.println(ase.getMessage());
        } catch (AmazonClientException ace){
            System.out.println("Exception in client");
            System.out.println(ace.getMessage());
        }

        return null;



    }

    /**
     * Check if this file is share with a particular person
     * @param username
     * @param key
     * @return true if this file is share with you
     */
    public boolean isShareWith(String username, String key){
        FileItem fileItem = new FileItem();
        fileItem.setUsername(username);
        fileItem.setS3key(key);
        FileItem result = null;
        try {
            result = shareMapper.load(fileItem);
        }catch (AmazonServiceException ase) {
            System.out.println("Exception in service");
            System.out.println(ase.getMessage());
        } catch (AmazonClientException ace){
            System.out.println("Exception in client");
            System.out.println(ace.getMessage());
        }

        return result != null;
    }
}
