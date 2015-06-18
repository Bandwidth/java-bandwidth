# Bandwidth Java SDK API

# Installing

Bandwidth Java SDK API is now using Maven.  At present the jars *are* available from a public [maven](http://maven.apache.org/download.html) repository.

Use the following dependency in your project:

       <dependency>
          <groupId>com.bandwidth.sdk</groupId>
          <artifactId>bandwidth-java-sdk</artifactId>
          <version>1.0</version>
          <scope>compile</scope>
       </dependency>

If you want to compile it yourself, here's how:

    $ git clone git@github.com:bandwidthcom/java-bandwidth.git
    $ cd java-bandwidth.git
    $ mvn install


# Set credentials

There are 3 ways to set these credentials:

1. Via Java VM System Properties, set as -D arguments on the VM command line:
    -Dcom.bandwidth.userId=<myUserId>
    -Dcom.bandwidth.apiToken=<myApiToken>
    -Dcom.bandwidth.apiSecret=<myApiSecret>
2. Via environment variables:
    export BANDWIDTH_USER_ID=<myUserId>
    export BANDWIDTH_API_TOKEN=<myApiToken>
    export BANDWIDTH_API_SECRET=<myApiSecret>
3. Directly by way of a method call on the BandwidthClient object
    BandwidthClient.getInstance().setCredentials(<myUserId>, <myApiToken>, <myApiSecret>)

Notice: if credentials are not set explicitly, the sdk will first look for VM properties. 
If those are not present, it will look for environments vars.
To override VM properties and env vars, use client's method as described on 3th option.