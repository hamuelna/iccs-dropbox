# myDropbox v2

* build the maven `mvn package`
* To run `java -jar target/dropbox-1.0-SNAPSHOT.jar`

Explanation of Each Java File

* Command package contain all of the commands as a class which
implement the Command interface 
* App the main class that run the whole thing
* CommandLineParser running the main loop of the program 
and help to parse all of the commands
* Config general static variable that is share across all 
of the app
* FileItem a representation of an Item in dynamodb this map
with the schema in dynamodb
* Hasher hash the password when create a new user and when
verifying the credential for the user
* ShareService provide function related to sharing using the
dynamodb client
* SqlQuery turn the sql command into managable function most of
it relate to user credential function
* StorageService relate to s3 object client and accessing my 
bucket to the do file manipulation
* User a singleton object for storing the current logged in user 
state
* UserService relate to all user credential functions