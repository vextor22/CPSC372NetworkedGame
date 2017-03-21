
README
======

What you need to try this example:
* Java 7 JDK (also works with Java 8)
* Eclipse  
* You do not need Gradle installed, as I have included the Gradle wrapper in the project

Instructions
------------

1. Execute the following at a command prompt in the project directory:

        gradlew eclipse

    * Note that you should do step 1 even though the Eclipse .project and .classpath already exist 
      in the project. This command will download the required libraries and fix your Eclipse 
      .classpath to reference them.
     
2. Import the project into Eclipse (File > Import > Existing Projects Into Workspace)

3. Start the contact server:

    Right-click contactserver/src/cpsc3720/contactserver/ContactServer.java and choose Run As > Java Application

4. Run the contact client:

    Right-click contactclient/src/cpsc3720/contactclient/ContactClient.java and choose Run As > Java Application

    This will attempt to add a new Contact to the (initially empty) in-memory Contact database on the server
   
Now, try the following:

5. Modify the contact client:

    Edit ContactClient.java and change the details of the contact to be added.

    Run the ContactClient again. This time, there should be two contacts displayed.
   
6. Modify the contact client and server to support Delete capability:
    - Modify the ContactServer: Add a new method:

            int deleteContact(int position) { ... }

       Add the necessary annotations to mark it as a web service method

   - Modify the ContactService interface: Add the deleteContact method.

   - Modify the ContactClient to attempt to delete a contact 
