# Bandwidth Java SDK API

# Installing

Bandwidth Java SDK API is now using Maven.  At present the jars *are* available from a public [maven](http://maven.apache.org/download.html) repository.

Use the following dependency in your project:

       <dependency>
          <groupId>com.bandwidth.sdk</groupId>
          <artifactId>bandwidth-java-sdk</artifactId>
          <version>1.10</version>
          <scope>compile</scope>
       </dependency>

If you want to compile it yourself, here's how:

    $ git clone git@github.com:bandwidthcom/java-bandwidth.git
    $ cd java-bandwidth.git
    $ mvn install


# Set credentials

There are 3 ways to set these credentials:

1. Via Java VM System Properties, set as -D arguments on the VM command line:  
       -Dcom.bandwidth.userId=my_User_Id  
       -Dcom.bandwidth.apiToken=my_Api_Token  
       -Dcom.bandwidth.apiSecret=my_Api_Secret  
2. Via environment variables:  
       $ export BANDWIDTH_USER_ID=my_User_Id  
       $ export BANDWIDTH_API_TOKEN=my_Api_Token  
       $ export BANDWIDTH_API_SECRET=my_Api_Secret  
3. Directly by way of a method call on the BandwidthClient object  
       BandwidthClient.getInstance().setCredentials(my_User_Id, my_Api_Token, my_Api_Secret)

Notice: if credentials are not set explicitly, the sdk will first look for VM properties. 
If those are not present, it will look for environments vars.
To override VM properties and env vars, use client's method as described on 3th option.
