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
