package me.hamuel.cloud.aws.dropbox;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageService {

    private final String bucketName = ""; //change to your bucket name
    private final String delim = Config.delim();
    private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .build();
    private ShareService shareService = new ShareService();

    /**
     * put the file in the bucket
     * @param file
     * @return true if success false if fail
     */
    public boolean put(File file){
        try {
            // Block and wait for the upload to finish
            if (file.exists()){
                String encodedKey = encodeFilename(file.getName());
                s3Client.putObject(new PutObjectRequest(bucketName, encodedKey, file));
                FileItem fileItem = new FileItem();
                fileItem.setUsername(User.getUsername());
                fileItem.setS3key(encodeFilename(file.getName()));
                shareService.shareTo(fileItem); //share to myself
                System.out.println("successfully uploaded the file to bucket");
                return true;
            }else {
                System.out.println("The file does not exists");
                return false;
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Exception in service");
            System.out.println(ase.getMessage());
        } catch (AmazonClientException ace){
            System.out.println("Exception in client");
            System.out.println(ace.getMessage());
        }
        return false;
    }

    /**
     * view every object that the user own ONLY
     * we also include the metadata in each row
     * @return string of all the objects with the meta data
     */
    public String view(){
        StringBuilder sb = new StringBuilder();
        List<FileItem> allShareWith = shareService.getAllShareWith(User.getUsername());
        Set<String> keys = allShareWith.stream().map(fileItem -> {return fileItem.getS3key();}).collect(Collectors.toSet());
        s3Client.listObjectsV2(bucketName).getObjectSummaries()
                .stream()
                .forEach((s3ObjectSummary -> {
                    if(keys.contains(s3ObjectSummary.getKey())){
                        sb.append(decodeFileName(s3ObjectSummary.getKey()) +
                                "  " + s3ObjectSummary.getSize() + "  "
                                + s3ObjectSummary.getLastModified() + " "+
                                decodeUsername(s3ObjectSummary.getKey())+"\n" );
                    }
                }));
                
        return sb.toString();
    }

    /**
     * Download the file that the user own
     * @param filename
     * @return true if success false if fail
     */
    public boolean get(String filename) {
        S3Object object = s3Client.getObject(bucketName, encodeFilename(filename));
        try {
            FileUtils.copyInputStreamToFile(object.getObjectContent(), new File(filename));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean get(String filename, String owner){
        String key = owner + delim + filename;
        if(shareService.isShareWith(User.getUsername(), key)){
            S3Object object = s3Client.getObject(bucketName, key);
            try {
                FileUtils.copyInputStreamToFile(object.getObjectContent(), new File(filename));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public boolean isFileExist(String filename){
        return s3Client.listObjectsV2(bucketName).getObjectSummaries()
                .stream()
                .anyMatch(s3ObjectSummary -> decodeUsername(s3ObjectSummary.getKey()).equals(User.getUsername()) &&
                decodeFileName(s3ObjectSummary.getKey()).equals(filename));
    }

    /**
     * A function to store a file belonging to a particular user
     * @param filename
     * @return encoded string
     */
    private String encodeFilename(String filename){
        return User.getUsername() + delim + filename;
    }

    /**
     * A function to pull the filename from the encoded key
     * @param key
     * @return filename
     */
    private String decodeFileName(String key){
        return key.split(delim)[1];
    }

    /**
     * A function to pull the username from the encoded key
     * @param key
     * @return username
     */
    private String decodeUsername(String key){
        return key.split(delim)[0];
    }
}
