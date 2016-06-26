# Overview

Bastion is a Java-based library for quickly creating tests for your HTTP APIs.
You can quickly define the request object to send as well as the expected response. Furthermore, 
Bastion supports keeping state between your different API calls so that you can even create 
fully-automated end-to-end tests for your APIs which contain more than one call (eg. create 
a user then login with *that* user).

# Usage

# Building

Use Maven to build Bastion and run the associated tests. After checking out the repository 
use the following command to build and test the source code.

    mvn install

# Contributing

Bastion is an open-source project which means that we will gladly accept contributions. Simply 
fork the repository and submit pull requests for review. Do use standard good coding practice 
in your submissions and read the [roadmap](https://github.com/KPull/Bastion/wiki/Roadmap) which 
is an indication of what we really need next.
